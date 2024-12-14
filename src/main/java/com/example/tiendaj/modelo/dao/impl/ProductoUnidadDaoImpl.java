package com.example.tiendaj.modelo.dao.impl;

import com.example.tiendaj.modelo.dao.EntregaDao;
import com.example.tiendaj.modelo.dao.InventarioDao;
import com.example.tiendaj.modelo.dao.ProductoDao;
import com.example.tiendaj.modelo.dao.ProductoUnidadDao;
import com.example.tiendaj.modelo.entidades.Producto;
import com.example.tiendaj.modelo.entidades.almacen.DetalleEntrega;
import com.example.tiendaj.modelo.entidades.almacen.ProductoUnidad;
import com.example.tiendaj.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoUnidadDaoImpl implements ProductoUnidadDao {
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    @Override
    public List<ProductoUnidad> listarUnidades() {
        ProductoDao productoDao = new ProductoDaoImpl();
        EntregaDao entregaDao = new EntregaDaoImpl();
        List<ProductoUnidad> lista = new ArrayList<>();
        try{

            String sql = "SELECT * FROM producto_unidad";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()){
                ProductoUnidad productoUnidad = new ProductoUnidad();
                productoUnidad.setCodigo(rs.getString(1));

                productoUnidad.setEstado(rs.getString(3));
                DetalleEntrega det = entregaDao.buscarDetalleEntrega(rs.getInt(4));
                productoUnidad.setDetalleEntrega(det);
                productoUnidad.setProducto(det.getProducto());
                lista.add(productoUnidad);
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
    public List<ProductoUnidad> listarUnidadesPorEstado(String estado) {
        EntregaDao entregaDao = new EntregaDaoImpl();
        List<ProductoUnidad> lista = new ArrayList<>();
        try{
            String sql = "SELECT * FROM producto_unidad WHERE estado = ?";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setString(1,estado);
            rs = pst.executeQuery();
            while (rs.next()){
                ProductoUnidad productoUnidad = new ProductoUnidad();
                productoUnidad.setCodigo(rs.getString(1));
                DetalleEntrega det = entregaDao.buscarDetalleEntrega(rs.getInt(4));
                productoUnidad.setProducto(det.getProducto());
                productoUnidad.setEstado(rs.getString(3));
                productoUnidad.setDetalleEntrega(det);
                lista.add(productoUnidad);
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
    public List<ProductoUnidad> listarUnidadesPorProducto(int productoId,String estado) {
        EntregaDao entregaDao = new EntregaDaoImpl();
        List<ProductoUnidad> lista = new ArrayList<>();
        try{
            String sql = "SELECT * FROM producto_unidad WHERE id_producto = ? AND estado = ?";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,productoId);
            pst.setString(2,estado);
            rs = pst.executeQuery();
            while (rs.next()){
                ProductoUnidad productoUnidad = new ProductoUnidad();
                productoUnidad.setCodigo(rs.getString(1));
                DetalleEntrega det = entregaDao.buscarDetalleEntrega(rs.getInt(4));
                productoUnidad.setProducto(det.getProducto());
                productoUnidad.setEstado(rs.getString(3));
                productoUnidad.setDetalleEntrega(det);
                lista.add(productoUnidad);
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
    public int registrarUnidad(ProductoUnidad productoUnidad) {
        InventarioDao invDao = new InventarioDaoImpl();
        int i = 0;
        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false); // Deshabilitar la confirmación automática

            String sql = "INSERT INTO producto_unidad VALUES(?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, productoUnidad.getCodigo());
            pst.setInt(2, productoUnidad.getProducto().getId());
            pst.setString(3, productoUnidad.getEstado());
            pst.setInt(4, productoUnidad.getDetalleEntrega().getId());
            i = pst.executeUpdate();

            if (i == 1 && productoUnidad.getEstado().equals("Stock")) {
                i=0;
                i=invDao.aumentarStock(productoUnidad.getProducto().getId(), 1);
            }

            pst.close();

            // Confirmar la transacción
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // En caso de error, realizar un rollback para deshacer la transacción
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true); // Restaurar la confirmación automática
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return i;
    }


    @Override
    public int actualizarUnidad(ProductoUnidad productoUnidad,int n) {
        InventarioDao invDao = new InventarioDaoImpl();
        int i = 0;
        try{
            String sql = "UPDATE producto_unidad SET estado = ? WHERE codigo = ?";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setString(1,productoUnidad.getEstado());
            pst.setString(2,productoUnidad.getCodigo());
            i = pst.executeUpdate();
            switch (n){
                case 0:
                    break;
                case 1:
                    i = invDao.aumentarStock(productoUnidad.getProducto().getId(),1);
                    break;
                case 2:
                    i = invDao.disminuirStock(productoUnidad.getProducto().getId(),1);
                    break;
                default:
                    System.out.println("Error en actualizarUnidad");
                    break;
            }
            pst.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int actualizarEstadoUnidad(String codigo, String estado) {
        int i = 0;
        try{
            String sql = "UPDATE producto_unidad SET estado = ? WHERE codigo = ?";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setString(1,estado);
            pst.setString(2,codigo);
            i = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int eliminarUnidad(String codigo) {
        int i = 0;
        try{
            String sql = "DELETE FROM producto_unidad WHERE codigo = ?";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setString(1,codigo);
            i = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public ProductoUnidad buscarUnidad(String codigo) {
        ProductoDao productoDao = new ProductoDaoImpl();
        EntregaDao entregaDao = new EntregaDaoImpl();
        ProductoUnidad productoUnidad = null;
        try{
            String sql = "SELECT * FROM producto_unidad WHERE codigo = ?";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setString(1,codigo);
            rs = pst.executeQuery();
            if(rs.next()){
                productoUnidad = new ProductoUnidad();
                productoUnidad.setCodigo(rs.getString(1));
                DetalleEntrega det = entregaDao.buscarDetalleEntrega(rs.getInt(4));
                productoUnidad.setProducto(det.getProducto());
                productoUnidad.setEstado(rs.getString(3));
                productoUnidad.setDetalleEntrega(det);
                rs.close();
                pst.close();
                con.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return productoUnidad;
    }


    @Override
    public Integer[] numerosUnidadesPorDetEntrega(int detEntregaId) {
        Integer[] numeros= null;
        try{
            numeros = new Integer[2];
            //numero de unidades entregadas registradas en la bd
            String sql = "SELECT COUNT(*) FROM producto_unidad WHERE id_detalle_entrega = ?";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,detEntregaId);
            rs = pst.executeQuery();
            if(rs.next()){
                numeros[0] = rs.getInt(1);

            }
            //numero de unidades que se entregaron en el detalle de entrega
            String sql2 = "SELECT cantidad FROM detalle_entrega WHERE id = ?";
            pst = con.prepareStatement(sql2);
            pst.setInt(1,detEntregaId);
            rs = pst.executeQuery();
            if(rs.next()){
                numeros[1] = rs.getInt(1);
            }
            rs.close();
            pst.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return numeros;
    }

    @Override
    public List<ProductoUnidad> listarUnidadesPorDetEntrega(int detEntregaId) {
        ProductoDao productoDao = new ProductoDaoImpl();
        EntregaDao entregaDao = new EntregaDaoImpl();
        List<ProductoUnidad> lista = new ArrayList<>();
        try{
            String sql = "SELECT * FROM producto_unidad WHERE id_detalle_entrega = ?";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,detEntregaId);
            rs = pst.executeQuery();
            while (rs.next()){
                ProductoUnidad productoUnidad = new ProductoUnidad();
                productoUnidad.setCodigo(rs.getString(1));
                DetalleEntrega det = entregaDao.buscarDetalleEntrega(rs.getInt(4));
                productoUnidad.setProducto(det.getProducto());
                productoUnidad.setEstado(rs.getString(3));
                productoUnidad.setDetalleEntrega(det);
                lista.add(productoUnidad);
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
    public List<ProductoUnidad> listarUnidadesDeProducto(int idProducto) {
        ProductoDao productoDao = new ProductoDaoImpl();
        EntregaDao entregaDao = new EntregaDaoImpl();
        List<ProductoUnidad> lista = new ArrayList<>();
        try{
            String sql = "SELECT * FROM producto_unidad WHERE id_producto = ?";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,idProducto);
            rs = pst.executeQuery();
            while (rs.next()){
                ProductoUnidad productoUnidad = new ProductoUnidad();
                productoUnidad.setCodigo(rs.getString(1));
                DetalleEntrega det = entregaDao.buscarDetalleEntrega(rs.getInt(4));
                productoUnidad.setProducto(det.getProducto());
                productoUnidad.setEstado(rs.getString(3));
                productoUnidad.setDetalleEntrega(det);
                lista.add(productoUnidad);
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
