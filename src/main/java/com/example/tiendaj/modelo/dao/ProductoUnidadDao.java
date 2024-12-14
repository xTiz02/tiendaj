package com.example.tiendaj.modelo.dao;

import com.example.tiendaj.modelo.entidades.almacen.ProductoUnidad;

import java.util.List;

public interface ProductoUnidadDao {
    public List<ProductoUnidad> listarUnidades();
    public List<ProductoUnidad> listarUnidadesPorEstado(String estado);
    public List<ProductoUnidad> listarUnidadesPorProducto(int productoId,String estado);
    public int registrarUnidad(ProductoUnidad productoUnidad);
    public int actualizarUnidad(ProductoUnidad productoUnidad,int n);
    public int actualizarEstadoUnidad(String codigo,String estado);
    public int eliminarUnidad(String codigo);
    public ProductoUnidad buscarUnidad(String codigo);
    public Integer[] numerosUnidadesPorDetEntrega(int detEntregaId);
    public List<ProductoUnidad> listarUnidadesPorDetEntrega(int detEntregaId);
    public List<ProductoUnidad> listarUnidadesDeProducto(int idProducto);


}
