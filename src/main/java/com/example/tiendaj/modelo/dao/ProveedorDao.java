package com.example.tiendaj.modelo.dao;

import com.example.tiendaj.modelo.entidades.almacen.Proveedor;
import com.example.tiendaj.modelo.entidades.almacen.TipoProveedor;

import java.util.List;

public interface ProveedorDao {
    public List<Proveedor> listarProveedores();
    public List<TipoProveedor> listarTipos();
    public TipoProveedor buscarTipo(int id);
    public int agregarProveedor(Proveedor p);
    public Proveedor buscarProveedor(int id);
    public int cambiarEstado(int idProveedor, int estado);
    public int editarProveedor(Proveedor p);
    public List<Proveedor> listaProveedoresActivos();

}
