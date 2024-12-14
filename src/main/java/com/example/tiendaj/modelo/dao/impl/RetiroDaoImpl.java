package com.example.tiendaj.modelo.dao.impl;

import com.example.tiendaj.modelo.dao.PedidoDao;
import com.example.tiendaj.modelo.dao.ProductoUnidadDao;
import com.example.tiendaj.modelo.dao.RetiroDao;
import com.example.tiendaj.modelo.entidades.almacen.ProductoUnidad;
import com.example.tiendaj.modelo.entidades.almacen.RetiroProducto;
import com.example.tiendaj.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RetiroDaoImpl implements RetiroDao {
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    @Override
    public List<RetiroProducto> listarPorDetallePedido(int idDetallePedido) {
        ProductoUnidadDao productoUnidadDao = new ProductoUnidadDaoImpl();
        PedidoDao pedidoDao = new PedidoDaoImpl();
        List<RetiroProducto> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            String sql = "select * from retiro_producto where id_detalle_pedido = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, idDetallePedido);
            rs = pst.executeQuery();
            while (rs.next()) {
                RetiroProducto retiroProducto = new RetiroProducto();
                retiroProducto.setId(rs.getInt(1));
                retiroProducto.setDetallePedido(pedidoDao.buscarDetallePedido(rs.getInt(2)));
                retiroProducto.setProductoUnidad(productoUnidadDao.buscarUnidad(rs.getString(3)));
                retiroProducto.setFechaRetiro(rs.getTimestamp(4).toLocalDateTime());//cambiar
                lista.add(retiroProducto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public int agregarRetiro(RetiroProducto retiroProducto) {
        int i = 0;
        try {
            con = Conexion.getConexion();
            String sql = "insert into retiro_producto values(null,?,?,now())";//cambiar la fecha
            pst = con.prepareStatement(sql);
            pst.setInt(1, retiroProducto.getDetallePedido().getId());
            pst.setString(2, retiroProducto.getProductoUnidad().getCodigo());
            //fecha
            i = pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int cantidadRetiradaPorDetPedido(int idDetPedido) {
        int i = 0;
        try{
            con = Conexion.getConexion();
            String sql = "select count(*) from retiro_producto where id_detalle_pedido = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1,idDetPedido);
            rs = pst.executeQuery();
            if(rs.next()){
                i = rs.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int eliminarRetiro(int idRetiro) {
        try{
            con = Conexion.getConexion();
            String sql = "delete from retiro_producto where id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1,idRetiro);
            pst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int eliminarRetiroPorCodUnidad(String codUnidad) {
        int i = 0;
        try{
            con = Conexion.getConexion();
            String sql = "delete from retiro_producto where id_producto_unidad = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1,codUnidad);
            i = pst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int traerIdDetPedidoPorCod(String codUnidad) {
        int i = 0;
        try{
            con = Conexion.getConexion();
            String sql = "select id_detalle_pedido from retiro_producto where id_producto_unidad = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1,codUnidad);
            rs = pst.executeQuery();
            if(rs.next()){
                i = rs.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }
}
