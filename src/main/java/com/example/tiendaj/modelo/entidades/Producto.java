package com.example.tiendaj.modelo.entidades;

import com.example.tiendaj.modelo.entidades.almacen.Proveedor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Producto {
    @Expose
    @SerializedName("id")
    private int id;
    private Proveedor proveedor;
    private Categoria categoria;
    @Expose
    @SerializedName("id")
    private String nombre;
    private String caracteristicas;
    private String descripcion;
    private String especificaciones;
    private double precio;
    private double descuento;
    private List<String> imagenes;

    public Producto(){}

    public Producto(int id,Proveedor proveedor, Categoria categoria, String nombre, String caracteristicas, String descripcion, String especificaciones, double precio, double descuento, List<String> imagenes) {
        this.proveedor = proveedor;
        this.id = id;
        this.categoria = categoria;
        this.nombre = nombre;
        this.caracteristicas = caracteristicas;
        this.descripcion = descripcion;
        this.especificaciones = especificaciones;
        this.precio = precio;
        this.descuento = descuento;
        this.imagenes = imagenes;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", proveedor=" + proveedor +
                ", categoria=" + categoria +
                ", nombre='" + nombre + '\'' +
                ", caracteristicas='" + caracteristicas + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", especificaciones='" + especificaciones + '\'' +
                ", precio=" + precio +
                ", descuento=" + descuento +
                '}';
    }
}
