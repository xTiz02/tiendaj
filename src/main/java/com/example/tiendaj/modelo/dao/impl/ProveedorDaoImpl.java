package com.example.tiendaj.modelo.dao.impl;

import com.example.tiendaj.modelo.dao.ProveedorDao;
import com.example.tiendaj.modelo.entidades.almacen.Proveedor;
import com.example.tiendaj.modelo.entidades.almacen.TipoProveedor;
import com.example.tiendaj.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDaoImpl implements ProveedorDao {

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    @Override
    public List<Proveedor> listarProveedores() {
        List<Proveedor> lista = new ArrayList<>();
        try{
            con = Conexion.getConexion();
            String sql = "select * from proveedor as pro inner join tipo_proveedor as tipo on pro.id_tipo_proveedor = tipo.id;";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Proveedor p = new Proveedor();
                TipoProveedor tp = new TipoProveedor();
                p.setId(rs.getInt(1));
                tp.setId(rs.getInt(2));
                p.setNombre(rs.getString(3));
                p.setRuc(rs.getString(4));
                p.setUbicacion(rs.getString(5));
                p.setDistrito(rs.getString(6));
                p.setDireccion(rs.getString(7));
                p.setTelefono(rs.getString(8));
                p.setCorreo(rs.getString(9));
                p.setContacto(rs.getString(10));
                p.setEstado(rs.getInt(11));
                //11 es el id del tipo
                tp.setTipo(rs.getString(13));
                tp.setDescripcion(rs.getString(14));
                p.setTipo(tp);
                lista.add(p);
            }
            rs.close();
            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<TipoProveedor> listarTipos() {
        List<TipoProveedor> lista = new ArrayList<>();
        try{
            con = Conexion.getConexion();
            String sql = "select * from tipo_proveedor;";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                TipoProveedor tp = new TipoProveedor();
                tp.setId(rs.getInt(1));
                tp.setTipo(rs.getString(2));
                tp.setDescripcion(rs.getString(3));
                lista.add(tp);
            }
            rs.close();
            pst.close();
            con.close();
        }catch (Exception e){
                e.printStackTrace();
        }
        return lista;
    }

    @Override
    public TipoProveedor buscarTipo(int id) {
        TipoProveedor tp = null;
        try{
            con = Conexion.getConexion();
            String sql = "select * from tipo_proveedor where id = ?;";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                tp = new TipoProveedor();
                tp.setId(rs.getInt(1));
                tp.setTipo(rs.getString(2));
                tp.setDescripcion(rs.getString(3));
            }
            rs.close();
            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return tp;
    }

    @Override
    public int agregarProveedor(Proveedor p) {
        int i = 0;
        try{
            con = Conexion.getConexion();
            String sql = "insert into proveedor values(null,?,?,?,?,?,?,?,?,?,?);";
            pst = con.prepareStatement(sql);
            pst.setInt(1, p.getTipo().getId());
            pst.setString(2, p.getNombre());
            pst.setString(3, p.getRuc());
            pst.setString(4, p.getUbicacion());
            pst.setString(5, p.getDistrito());
            pst.setString(6, p.getDireccion());
            pst.setString(7, p.getTelefono());
            pst.setString(8, p.getCorreo());
            pst.setString(9, p.getContacto());
            pst.setInt(10, p.getEstado());
            i = pst.executeUpdate();
            pst.close();
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public Proveedor buscarProveedor(int id) {
        Proveedor p = null;
        try{
            con = Conexion.getConexion();
            String sql = "select * from proveedor as pro inner join tipo_proveedor as tipo on pro.id_tipo_proveedor = tipo.id where pro.id = ?;";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                p = new Proveedor();
                p.setId(rs.getInt(1));
                TipoProveedor tp = new TipoProveedor();
                tp.setId(rs.getInt(2));//12
                tp.setTipo(rs.getString(13));
                tp.setDescripcion(rs.getString(14));
                p.setTipo(tp);
                p.setNombre(rs.getString(3));
                p.setRuc(rs.getString(4));
                p.setUbicacion(rs.getString(5));
                p.setDistrito(rs.getString(6));
                p.setDireccion(rs.getString(7));
                p.setTelefono(rs.getString(8));
                p.setCorreo(rs.getString(9));
                p.setContacto(rs.getString(10));
                p.setEstado(rs.getInt(11));
            }
            rs.close();
            pst.close();
            con.close();

        }catch (Exception e){
                e.printStackTrace();
        }
        return p;
    }

    @Override
    public int cambiarEstado(int id,int estado) {
        int i=0;
        try{
            con = Conexion.getConexion();
            String sql = "update proveedor set estado = ? where id = ?;";
            pst = con.prepareStatement(sql);
            pst.setInt(1, estado);
            pst.setInt(2, id);
            i = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int editarProveedor(Proveedor p) {
        int i = 0;
        try {
            con = Conexion.getConexion();
            String sql = "update proveedor set id_tipo_proveedor = ?, nombre = ?, ruc = ?, ubicacion = ?, distrito = ?, direccion = ?, telefono = ?, correo = ?, contacto = ? where id = ?;";
            pst = con.prepareStatement(sql);
            pst.setInt(1, p.getTipo().getId());
            pst.setString(2, p.getNombre());
            pst.setString(3, p.getRuc());
            pst.setString(4, p.getUbicacion());
            pst.setString(5, p.getDistrito());
            pst.setString(6, p.getDireccion());
            pst.setString(7, p.getTelefono());
            pst.setString(8, p.getCorreo());
            pst.setString(9, p.getContacto());
            pst.setInt(10, p.getId());
            i = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public List<Proveedor> listaProveedoresActivos() {
        List<Proveedor> lista = new ArrayList<>();
        try{
            con = Conexion.getConexion();
            String sql = "select * from proveedor as pro inner join tipo_proveedor as tipo on pro.id_tipo_proveedor = tipo.id where pro.estado = 1;";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Proveedor p = new Proveedor();
                TipoProveedor tp = new TipoProveedor();
                p.setId(rs.getInt(1));
                tp.setId(rs.getInt(2));
                p.setNombre(rs.getString(3));
                p.setRuc(rs.getString(4));
                p.setUbicacion(rs.getString(5));
                p.setDistrito(rs.getString(6));
                p.setDireccion(rs.getString(7));
                p.setTelefono(rs.getString(8));
                p.setCorreo(rs.getString(9));
                p.setContacto(rs.getString(10));
                p.setEstado(rs.getInt(11));
                //11 es el id del tipo
                tp.setTipo(rs.getString(13));
                tp.setDescripcion(rs.getString(14));
                p.setTipo(tp);
                lista.add(p);
            }
            rs.close();
            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return lista;
    }
}
