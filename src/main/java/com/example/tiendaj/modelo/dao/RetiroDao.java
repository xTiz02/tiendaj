package com.example.tiendaj.modelo.dao;

import com.example.tiendaj.modelo.entidades.almacen.RetiroProducto;

import java.util.List;

public interface RetiroDao {
    public List<RetiroProducto> listarPorDetallePedido(int idDetallePedido);
    public int agregarRetiro(RetiroProducto retiroProducto);
    public int cantidadRetiradaPorDetPedido(int idDetPedido);
    public int eliminarRetiro(int idRetiro);
    public int eliminarRetiroPorCodUnidad(String codUnidad);
    public int traerIdDetPedidoPorCod(String codUnidad);
}
