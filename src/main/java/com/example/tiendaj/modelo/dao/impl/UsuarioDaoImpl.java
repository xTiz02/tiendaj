package com.example.tiendaj.modelo.dao.impl;

import com.example.tiendaj.modelo.entidades.Cliente;
import com.example.tiendaj.modelo.entidades.Usuario;
import com.example.tiendaj.modelo.dao.UsuarioDao;
import com.example.tiendaj.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoImpl implements UsuarioDao {

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    @Override
    public Usuario validarUsuario(String us, String ps, String roll) {
        Usuario usuario = null;
        Cliente cliente;
        byte[] passBytes = ps.getBytes();
        try{
            con = Conexion.getConexion();
            String sql = "SELECT   u.id, u.id_cliente, cli.dni, cli.nombres, cli.apellidos, cli.email, cli.telefono, cli.estado, cli.fechaCreado, u.usuario, u.password, u.rol \n"+
                    " FROM usuarios AS u \n" +
                    "INNER JOIN clientes AS cli ON u.id_cliente = cli.id \n" +
                    "WHERE u.usuario = ? AND u.password = ? AND u.rol = ? AND cli.estado = 1";

            pst = con.prepareStatement(sql);
            pst.setString(1,us);
            pst.setBytes(2,passBytes);
            pst.setString(3,roll);
            rs = pst.executeQuery();
            if(rs.next()){
                usuario = new Usuario();
                cliente = new Cliente();
                usuario.setId(rs.getInt(1));
                usuario.setUsuario(rs.getString(10));
                cliente.setDni(rs.getString(3));
                cliente.setId(rs.getInt(2));
                usuario.setPassword(rs.getBytes(11));//
                usuario.setRol(rs.getString(12));
                cliente.setNombre(rs.getString(4));
                cliente.setApellido(rs.getString(5));
                cliente.setEmail(rs.getString(6));
                cliente.setTelefono(rs.getString(7));
                cliente.setEstado(rs.getInt(8));
                cliente.setFechaCreado(rs.getTimestamp(9).toLocalDateTime());//cambia la fecha(falta corregir)
                usuario.setCliente(cliente);
                }

            pst.close();
            rs.close();
            con.close();
            System.out.println("usuario despues de validar"+usuario);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

    @Override
    public int crearUsuario(Usuario u) {
        int i = 0;
        int idCliente=0;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false); // Deshabilita el modo autocommit

            String sql = "INSERT INTO clientes (dni, nombres, apellidos, email, telefono, estado, fechaCreado) VALUES (?, ?, ?, ?, ?, ?, now())";
            pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, u.getCliente().getDni());
            pst.setString(2, u.getCliente().getNombre());
            pst.setString(3, u.getCliente().getApellido());
            pst.setString(4, u.getCliente().getEmail());
            pst.setString(5, u.getCliente().getTelefono());
            pst.setInt(6, u.getCliente().getEstado());
            i = pst.executeUpdate();
            if (i == 1) {
                i = 0;
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    idCliente = rs.getInt(1);

                }

                String sql2 = "INSERT INTO usuarios (id_cliente, usuario, password,rol) VALUES (?, ?, ?,?)";
                pst = con.prepareStatement(sql2);
                pst.setInt(1, idCliente);
                pst.setString(2, u.getUsuario());
                pst.setBytes(3, u.getPassword());
                pst.setString(4, u.getRol());
                i = pst.executeUpdate();
            }


            // Si todo salió bien, hacemos commit
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                // En caso de error, hacemos rollback para deshacer los cambios
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.setAutoCommit(true); // Habilita el modo autocommit nuevamente
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return i;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();

        try{
            String sql = "SELECT * FROM usuarios u INNER JOIN clientes c ON u.id_cliente = c.id";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Usuario usuario= new Usuario();
                Cliente cliente = new Cliente();
                usuario.setId(rs.getInt(1));
                cliente.setId(rs.getInt(2));
                usuario.setUsuario(rs.getString(3));
                usuario.setPassword(rs.getBytes(4));
                usuario.setRol(rs.getString(5));
                cliente.setDni(rs.getString(7));
                cliente.setNombre(rs.getString(8));
                cliente.setApellido(rs.getString(9));
                cliente.setEmail(rs.getString(10));
                cliente.setTelefono(rs.getString(11));
                cliente.setEstado(rs.getInt(12));
                cliente.setFechaCreado(rs.getTimestamp(13).toLocalDateTime());
                usuario.setCliente(cliente);
                lista.add(usuario);
            }
            pst.close();
            rs.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Usuario buscarUsuario(int id) {
        Usuario usuario = null;
        try{
            con = Conexion.getConexion();
            String sql = "SELECT * FROM usuarios u INNER JOIN clientes c ON u.id_cliente = c.id WHERE u.id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            if(rs.next()){
                usuario = new Usuario();
                Cliente cliente = new Cliente();
                usuario.setId(rs.getInt(1));
                cliente.setId(rs.getInt(2));
                usuario.setUsuario(rs.getString(3));
                usuario.setPassword(rs.getBytes(4));
                usuario.setRol(rs.getString(5));
                cliente.setDni(rs.getString(7));
                cliente.setNombre(rs.getString(8));
                cliente.setApellido(rs.getString(9));
                cliente.setEmail(rs.getString(10));
                cliente.setTelefono(rs.getString(11));
                cliente.setEstado(rs.getInt(12));
                cliente.setFechaCreado(rs.getTimestamp(13).toLocalDateTime());
                usuario.setCliente(cliente);
            }
            pst.close();
            rs.close();
            con.close();
        }catch (Exception e) {
             e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public int cambiarEstado(int idCliente, int estado) {
        int i = 0;
        try{
            con = Conexion.getConexion();
            String sql = "UPDATE clientes SET estado = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1,estado);
            pst.setInt(2,idCliente);
            i = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}
