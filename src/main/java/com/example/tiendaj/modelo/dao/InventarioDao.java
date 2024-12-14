package com.example.tiendaj.modelo.dao;

import com.example.tiendaj.modelo.entidades.almacen.Inventario;

import javax.servlet.http.Part;
import java.util.Collection;
import java.util.List;

public interface InventarioDao {
    public List<Inventario> listarInventario();
    public Inventario buscarInventario(List<Inventario> lista,int id);
    public int agregarInventario(Inventario inv);
    public void guardarImagenes(int idProducto, Collection<Part> archivos);
    public int actualizarInventario(Inventario inv);
    public int aumentarStock(int idProducto, int cantidad);
    public int disminuirStock(int idProducto, int cantidad);
    public int stockProducto(int id);
    public int actualizarStock(int id, int cantidad);
    public int actualizarEstado(int idProducto, int estado);
    public int activarProducto(int idInventario);
    public int desactivarProducto(int idInventario);
    public Inventario buscarInventarioPorId(int id);
}
