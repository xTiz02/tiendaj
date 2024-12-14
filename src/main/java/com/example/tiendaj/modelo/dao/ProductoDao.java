package com.example.tiendaj.modelo.dao;

import com.example.tiendaj.modelo.entidades.Producto;

import java.util.List;

public interface ProductoDao {
    public List<Producto> flitrarPorDescuento(List<Producto> l, int cantidad);
    public List<Producto> flitrarPorNombre(List<Producto> l, String caracteres);
    public List<Producto> listarProductosActivos();
    public Producto buscarProductoPorId(List<Producto> l,int id);
    public Producto buscarProducto(int id);
    public List<Producto> filtrarPorCategoria(List<Producto> lista,String[] cat, double precio);




}
