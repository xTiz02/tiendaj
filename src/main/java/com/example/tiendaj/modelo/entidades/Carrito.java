package com.example.tiendaj.modelo.entidades;

public class Carrito {
    private int id;
    private Producto producto;
    private String nombres;
    private double precioCompra;
    private int cantidad;
    private double subTotal;
    public Carrito() {
    }

    public Carrito(int id, Producto producto, String nombres,double precioCompra, int cantidad, double subTotal) {
        this.id = id;
        this.producto = producto;
        this.nombres = nombres;
        this.precioCompra = precioCompra;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }



    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "Carrito{" +
                "id=" + id +
                ", producto=" + producto +
                ", nombres='" + nombres + '\'' +
                ", precioCompra=" + precioCompra +
                ", cantidad=" + cantidad +
                ", subTotal=" + subTotal +
                '}';
    }
}
