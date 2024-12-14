package com.example.tiendaj.modelo.dao;

import com.example.tiendaj.modelo.entidades.Categoria;
import com.example.tiendaj.modelo.entidades.almacen.Proveedor;
import com.example.tiendaj.modelo.entidades.almacen.TipoProveedor;

import java.util.List;

public interface CategoriaDao {
    public List<Categoria> listarCategorias();
    public Categoria buscarCategoria(int id);
    public int agregarCategoria(Categoria p);
    public int eliminarCategoria(int id);
    public int editarCategoria(Categoria p);
}
