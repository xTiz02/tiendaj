package com.example.tiendaj.modelo.entidades.almacen;

import com.example.tiendaj.modelo.entidades.Producto;

public class DetalleEntrega {
    private  int id;
    private Entrega entrega;
    private Producto producto;
    private int cantidad;
    private double pago;
    private String estado;

    public DetalleEntrega() {
    }

    public DetalleEntrega(int id, Entrega entrega, Producto producto, int cantidad, double pago,String estado) {
        this.id = id;
        this.estado = estado;
        this.entrega = entrega;
        this.producto = producto;
        this.cantidad = cantidad;
        this.pago = pago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    @Override
    public String toString() {
        return "DetalleEntrega{" +
                "id=" + id +
                ", entrega=" + entrega +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", pago=" + pago +
                ", estado='" + estado + '\'' +
                '}';
    }
}
