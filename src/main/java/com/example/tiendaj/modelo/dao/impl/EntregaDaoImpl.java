package com.example.tiendaj.modelo.dao.impl;

import com.example.tiendaj.modelo.dao.EntregaDao;
import com.example.tiendaj.modelo.dao.ProductoDao;
import com.example.tiendaj.modelo.dao.ProveedorDao;
import com.example.tiendaj.modelo.entidades.Producto;
import com.example.tiendaj.modelo.entidades.almacen.DetalleEntrega;
import com.example.tiendaj.modelo.entidades.almacen.Entrega;
import com.example.tiendaj.modelo.entidades.almacen.Proveedor;
import com.example.tiendaj.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntregaDaoImpl implements EntregaDao {
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    @Override
    public List<Entrega> listarEntregas() {
        List<Entrega> entregas = new ArrayList<>();
        ProveedorDao proveedorDao = new ProveedorDaoImpl();
        try{
            con = Conexion.getConexion();
            String sql = "SELECT * FROM entrega";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Entrega entrega = new Entrega();
                Proveedor proveedor = proveedorDao.buscarProveedor(rs.getInt(2));

                entrega.setId(rs.getInt(1));
                entrega.setCantidad(rs.getInt(3));
                entrega.setTotal(rs.getDouble(4));
                entrega.setFechaEntrega(rs.getTimestamp(5).toLocalDateTime());//localDateTime
                entrega.setEstado(rs.getString(6));
                entrega.setProveedor(proveedor);
                entregas.add(entrega);
            }
rs.close();
            pst.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return entregas;
    }

    @Override
    public Entrega buscarEntrega(int id) {
        //buscar la entrega y llenar la lista de detalleEntrega con el id de la entrega
        Entrega entrega = new Entrega();
        Proveedor proveedor = null;
        ProveedorDao proveedorDao = new ProveedorDaoImpl();
        ProductoDao productoDao = new ProductoDaoImpl();
        try{
            con = Conexion.getConexion();
            String sql = "SELECT * FROM entrega WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            if(rs.next()){
                entrega.setId(rs.getInt(1));
                proveedor = proveedorDao.buscarProveedor(rs.getInt(2));
                entrega.setCantidad(rs.getInt(3));
                entrega.setTotal(rs.getDouble(4));
                entrega.setFechaEntrega(rs.getTimestamp(5).toLocalDateTime());//localDateTime
                entrega.setEstado(rs.getString(6));
                entrega.setProveedor(proveedor);
                entrega.setDetalleEntregaLista(listarDetalleEntrega(id));
            }
            rs.close();
            pst.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return entrega;
    }

    @Override
    public List<DetalleEntrega> listarDetalleEntrega(int idEntrega) {
        List<DetalleEntrega> detalleEntregas = new ArrayList<>();
        ProductoDao productoDao = new ProductoDaoImpl();
        try{
            con = Conexion.getConexion();
            String sql = "SELECT * FROM detalle_entrega WHERE id_entrega = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1,idEntrega);
            rs = pst.executeQuery();
            while(rs.next()){
                DetalleEntrega detalleEntrega = new DetalleEntrega();
                detalleEntrega.setId(rs.getInt(1));
                detalleEntrega.setProducto(productoDao.buscarProducto(rs.getInt(3)));
                detalleEntrega.setCantidad(rs.getInt(4));
                detalleEntrega.setPago(rs.getDouble(5));
                detalleEntrega.setEstado(rs.getString(6));

                detalleEntregas.add(detalleEntrega);
            }
            rs.close();
            pst.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return detalleEntregas;
    }

    @Override
    public int agregarEntrega(Entrega entrega) {
        int i = 0;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false); // Desactivar el modo de auto commit

            String sql = "INSERT INTO entrega (id_proveedor, cantidad, total, fechaEntrega, estado) VALUES (?,?,?,NOW(),?)";
            pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setInt(1, entrega.getProveedor().getId());
            pst.setInt(2, entrega.getCantidad());
            pst.setDouble(3, entrega.getTotal());
            pst.setString(4, entrega.getEstado());
            i = pst.executeUpdate();

            if (i != 0) {
                rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int idEntrega = rs.getInt(1);
                    for (DetalleEntrega detalleEntrega : entrega.getDetalleEntregaLista()) {
                        String sql1 = "INSERT INTO detalle_entrega (id_entrega, id_producto, cantidad, pago, estado) VALUES (?,?,?,?,?)";
                        pst = con.prepareStatement(sql1);
                        pst.setInt(1, idEntrega);
                        pst.setInt(2, detalleEntrega.getProducto().getId());
                        pst.setInt(3, detalleEntrega.getCantidad());
                        pst.setDouble(4, detalleEntrega.getPago());
                        pst.setString(5, detalleEntrega.getEstado());
                        i = pst.executeUpdate();
                    }
                }
            }

            if (i != 0) {
                con.commit(); // Commit si
            } else {
                con.rollback(); // Rollback si algo salió mal
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // Rollback en caso de excepción
                }
            } catch (SQLException ex) {
                System.out.println("Error al realizar rollback: " + ex);
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true); // Restaurar el modo de auto commit
                    con.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexiones: " + ex);
            }
        }

        return i;
    }

    @Override
    public DetalleEntrega buscarDetalleEntrega(int id) {
        ProductoDao productoDao = new ProductoDaoImpl();
        DetalleEntrega detalleEntrega = new DetalleEntrega();
        try {
            con = Conexion.getConexion();
            String sql = "SELECT * FROM detalle_entrega WHERE id = ?;";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                Entrega entrega = new Entrega();

                detalleEntrega.setId(rs.getInt(1));
                entrega.setId(rs.getInt(2));
                detalleEntrega.setEntrega(entrega);
                Producto producto = productoDao.buscarProducto(rs.getInt(3));
                detalleEntrega.setProducto(producto);
                detalleEntrega.setCantidad(rs.getInt(4));
                detalleEntrega.setPago(rs.getDouble(5));
                detalleEntrega.setEstado(rs.getString(6));
            }
            rs.close(); // Cierra el ResultSet después de utilizarlo
            pst.close(); // Luego cierra el PreparedStatement
            con.close(); // Finalmente, cierra la conexión
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detalleEntrega;
    }

    @Override
    public int editarEntrega(Entrega entrega) {
        int i = 0;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false); // Desactivar el modo de auto commit

            String sql = "UPDATE entrega SET id_proveedor = ?, cantidad = ?, total = ?, fechaEntrega = NOW(), estado = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, entrega.getProveedor().getId());
            pst.setInt(2, entrega.getCantidad());
            pst.setDouble(3, entrega.getTotal());
            pst.setString(4, entrega.getEstado());
            pst.setInt(5, entrega.getId());
            i = pst.executeUpdate();
            if (i != 0) {
                for (DetalleEntrega detalleEntrega : entrega.getDetalleEntregaLista()) {
                    String sql1 = "UPDATE detalle_entrega SET id_entrega = ?,id_producto = ?, cantidad = ?, pago = ?, estado = ? WHERE id = ?";
                    pst = con.prepareStatement(sql1);
                    pst.setInt(1, entrega.getId());
                    pst.setInt(2, detalleEntrega.getProducto().getId());
                    pst.setInt(3, detalleEntrega.getCantidad());
                    pst.setDouble(4, detalleEntrega.getPago());
                    pst.setString(5, detalleEntrega.getEstado());
                    pst.setInt(6, detalleEntrega.getId());

                    i = pst.executeUpdate();
                }
            }

            if (i != 0) {
                con.commit(); // Commit si
            } else {
                con.rollback(); // Rollback si algo salió mal
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // Rollback en caso de excepción
                }
            } catch (SQLException ex) {
                System.out.println("Error al realizar rollback: " + ex);
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true); // Restaurar el modo de auto commit
                    con.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexiones: " + ex);
            }
        }

        return i;
    }

    @Override
    public int cambiarEstadoDetEntrega(int id, String estado) {
        int i = 0;
        try{
            con = Conexion.getConexion();
            String sql = "UPDATE detalle_entrega SET estado = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, estado);
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
    public int cambiarEstadoEntrega(int id, String estado) {
        int i = 0;
        try {
            con = Conexion.getConexion();
            String sql = "UPDATE entrega SET estado = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, estado);
            pst.setInt(2, id);
            i = pst.executeUpdate();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int eliminarEntrega(int id) {
        int i = 0;
        try{
            con = Conexion.getConexion();
            String sql = "DELETE FROM entrega WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            i = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int eliminarDetallesEntregas(int id_entrega) {
        int i = 0;
        try{
            con = Conexion.getConexion();
            String sql = "DELETE FROM detalle_entrega WHERE id_entrega = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id_entrega);
            i = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
