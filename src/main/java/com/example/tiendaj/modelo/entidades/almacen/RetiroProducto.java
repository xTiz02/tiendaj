package com.example.tiendaj.modelo.entidades.almacen;

import com.example.tiendaj.modelo.entidades.Pedido;
import com.example.tiendaj.modelo.entidades.Producto;

import java.time.LocalDateTime;

public class RetiroProducto {
    private int id;
    private DetallePedido detallePedido;
    private ProductoUnidad productoUnidad;
    private LocalDateTime fechaRetiro;


    public RetiroProducto() {
    }

    public RetiroProducto(int id, DetallePedido detallePedido, ProductoUnidad productoUnidad, LocalDateTime fechaRetiro) {
        this.id = id;
        this.detallePedido = detallePedido;
        this.productoUnidad = productoUnidad;
        this.fechaRetiro = fechaRetiro;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public DetallePedido getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(DetallePedido detallePedido) {
        this.detallePedido = detallePedido;
    }

    public ProductoUnidad getProductoUnidad() {
        return productoUnidad;
    }

    public void setProductoUnidad(ProductoUnidad productoUnidad) {
        this.productoUnidad = productoUnidad;
    }

    public LocalDateTime getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(LocalDateTime fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    @Override
    public String toString() {
        return "RetiroProducto{" +
                "id=" + id +
                ", detallePedido=" + detallePedido +
                ", productoUnidad=" + productoUnidad +
                ", fechaRetiro=" + fechaRetiro +
                '}';
    }
}
