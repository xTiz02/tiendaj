package com.example.tiendaj.modelo.entidades.almacen;

import com.example.tiendaj.modelo.entidades.Pedido;
import com.example.tiendaj.modelo.entidades.Producto;

import java.util.List;

public class DetallePedido {
    private int id;
    private Pedido pedido;
    private Producto producto;
    private String nombre;
    private double precio;
    private int cantidad;
    private List<RetiroProducto> listaRetiroProducto;
    private String estado;

    public DetallePedido() {
    }

    public DetallePedido(int id, Pedido pedido, Producto producto, String nombre, double precio, int cantidad, List<RetiroProducto> listaRetiroProducto, String estado) {
        this.id = id;
        this.pedido = pedido;
        this.producto = producto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.listaRetiroProducto = listaRetiroProducto;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<RetiroProducto> getListaRetiroProducto() {
        return listaRetiroProducto;
    }

    public void setListaRetiroProducto(List<RetiroProducto> listaRetiroProducto) {
        this.listaRetiroProducto = listaRetiroProducto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "id=" + id +
                ", pedido=" + pedido +
                ", producto=" + producto +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}
