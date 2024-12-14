package com.example.tiendaj.modelo.entidades.almacen;

import com.example.tiendaj.modelo.entidades.Producto;

import java.time.LocalDateTime;

public class Inventario {
    private int id;
    private Producto producto;
    private int stock;
    private LocalDateTime fechaModificado;
    private int activo;
    public Inventario(){}
    public Inventario(int id,Producto producto, int stock, LocalDateTime fechaModificado, int activo) {
        this.producto = producto;
        this.id = id;
        this.stock = stock;
        this.fechaModificado = fechaModificado;
        this.activo = activo;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDateTime getFechaModificado() {
        return fechaModificado;
    }

    public void setFechaModificado(LocalDateTime fechaModificado) {
        this.fechaModificado = fechaModificado;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "id=" + id +
                ", producto=" + producto +
                ", stock=" + stock +
                ", fechaModificado=" + fechaModificado +
                ", activo=" + activo +
                '}';
    }
}
