package com.example.tiendaj.modelo.entidades;

import java.time.LocalDate;
import java.util.List;

public class Boleta {
    private int id;
    private LocalDate fecha;
    private Cliente cliente;
    private List<Carrito> carrito;
    private double total;

    public Boleta(){
    }

    public Boleta(int id, LocalDate fecha, Cliente cliente, List<Carrito> carrito, double total) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.carrito = carrito;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fechaEmision) {
        this.fecha = fechaEmision;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Carrito> getCarrito() {
        return carrito;
    }

    public void setProductos(List<Carrito> carrito) {
        this.carrito = carrito;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Boleta{" +
                "id=" + id +
                ", fechaEmision=" + fecha +
                ", cliente=" + cliente +
                ", productos=" + carrito +
                ", total=" + total +
                '}';
    }
}
