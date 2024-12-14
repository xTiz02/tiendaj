package com.example.tiendaj.modelo.dao;

import com.example.tiendaj.modelo.entidades.almacen.DetalleEntrega;
import com.example.tiendaj.modelo.entidades.almacen.Entrega;

import java.util.List;

public interface EntregaDao {
    public List<Entrega> listarEntregas();
    public Entrega buscarEntrega(int id);

    public List<DetalleEntrega> listarDetalleEntrega(int id);
    public int agregarEntrega(Entrega entrega);

    public DetalleEntrega buscarDetalleEntrega(int id);
    public int editarEntrega(Entrega entrega);
    public int cambiarEstadoDetEntrega(int id, String estado);
    public int cambiarEstadoEntrega(int id, String estado);
    public int eliminarEntrega(int id);
    public int eliminarDetallesEntregas(int id);

}
