package com.example.tiendaj.modelo.entidades.almacen;

import java.time.LocalDateTime;
import java.util.List;

public class Entrega {
    private int id;
    private Proveedor proveedor;
    private int cantidad;
    private LocalDateTime fechaEntrega;

    private List<DetalleEntrega> detalleEntregaLista;
    private double total;
    private String estado;
    public Entrega() {
    }

    public Entrega(int id, Proveedor proveedor, int cantidad, LocalDateTime fechaEntrega, List<DetalleEntrega> detalleEntregaLista, double total, String estado) {
        this.id = id;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
        this.fechaEntrega = fechaEntrega;
        this.detalleEntregaLista = detalleEntregaLista;
        this.total = total;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public List<DetalleEntrega> getDetalleEntregaLista() {
        return detalleEntregaLista;
    }

    public void setDetalleEntregaLista(List<DetalleEntrega> detalleEntregaLista) {
        this.detalleEntregaLista = detalleEntregaLista;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Entrega{" +
                "id=" + id +
                ", proveedor=" + proveedor +
                ", cantidad=" + cantidad +
                ", fechaEntrega=" + fechaEntrega +
                ", detalleEntregaLista=" + detalleEntregaLista +
                ", total=" + total +
                ", estado='" + estado + '\'' +
                '}';
    }
}
