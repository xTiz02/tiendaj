package com.example.tiendaj.modelo.entidades.almacen;

import com.example.tiendaj.modelo.entidades.Producto;

public class ProductoUnidad {
    private String codigo;
    private Producto producto;
    private String estado;
    private DetalleEntrega detalleEntrega;
public ProductoUnidad(){

}
    public ProductoUnidad(String codigo, Producto producto, String estado, DetalleEntrega detalleEntrega) {
        this.codigo = codigo;
        this.producto = producto;
        this.estado = estado;
        this.detalleEntrega = detalleEntrega;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DetalleEntrega getDetalleEntrega() {
        return detalleEntrega;
    }

    public void setDetalleEntrega(DetalleEntrega detalleEntrega) {
        this.detalleEntrega = detalleEntrega;
    }

    @Override
    public String toString() {
        return "ProductoUnidad{" +
                "codigo='" + codigo + '\'' +
                ", producto=" + producto +
                ", estado='" + estado + '\'' +
                ", detalleEntrega=" + detalleEntrega +
                '}';
    }
}
