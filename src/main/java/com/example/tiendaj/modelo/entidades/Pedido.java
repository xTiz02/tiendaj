package com.example.tiendaj.modelo.entidades;

import com.example.tiendaj.modelo.entidades.almacen.DetallePedido;
import com.example.tiendaj.modelo.entidades.almacen.Empleado;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private Empleado empleado;
    private String id_paypal_cliente;
    private String id_transaccion;
    private String nombre_paypal_cliente;
    private String calle_paypal_cliente;
    private String direccion_paypal_cliente;
    private String ciudad_paypal_cliente;
    private String codPostal_paypal_cliente;
    private String provincia_paypal_cliente;

    private String email_paypal_cliente;
    private double total;
    private LocalDateTime fechaPedido;
    private String estado;
    private List<DetallePedido> listaDetallePedido;

    public Pedido() {
    }

    public Pedido(int id, Cliente cliente, Empleado empleado, String id_paypal_cliente, String id_transaccion, String nombre_paypal_cliente, String calle_paypal_cliente, String direccion_paypal_cliente, String ciudad_paypal_cliente, String codPostal_paypal_cliente, String provincia_paypal_cliente, String email_paypal_cliente, double total, LocalDateTime fechaPedido, String estado, List<DetallePedido> listaDetallePedido) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.id_paypal_cliente = id_paypal_cliente;
        this.id_transaccion = id_transaccion;
        this.nombre_paypal_cliente = nombre_paypal_cliente;
        this.calle_paypal_cliente = calle_paypal_cliente;
        this.direccion_paypal_cliente = direccion_paypal_cliente;
        this.ciudad_paypal_cliente = ciudad_paypal_cliente;
        this.codPostal_paypal_cliente = codPostal_paypal_cliente;
        this.provincia_paypal_cliente = provincia_paypal_cliente;
        this.email_paypal_cliente = email_paypal_cliente;
        this.total = total;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.listaDetallePedido = listaDetallePedido;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getId_paypal_cliente() {
        return id_paypal_cliente;
    }

    public void setId_paypal_cliente(String id_paypal_cliente) {
        this.id_paypal_cliente = id_paypal_cliente;
    }

    public String getId_transaccion() {
        return id_transaccion;
    }

    public void setId_transaccion(String id_transaccion) {
        this.id_transaccion = id_transaccion;
    }

    public String getNombre_paypal_cliente() {
        return nombre_paypal_cliente;
    }

    public void setNombre_paypal_cliente(String nombre_paypal_cliente) {
        this.nombre_paypal_cliente = nombre_paypal_cliente;
    }

    public String getCalle_paypal_cliente() {
        return calle_paypal_cliente;
    }

    public void setCalle_paypal_cliente(String calle_paypal_cliente) {
        this.calle_paypal_cliente = calle_paypal_cliente;
    }

    public String getDireccion_paypal_cliente() {
        return direccion_paypal_cliente;
    }

    public void setDireccion_paypal_cliente(String direccion_paypal_cliente) {
        this.direccion_paypal_cliente = direccion_paypal_cliente;
    }

    public String getCiudad_paypal_cliente() {
        return ciudad_paypal_cliente;
    }

    public void setCiudad_paypal_cliente(String ciudad_paypal_cliente) {
        this.ciudad_paypal_cliente = ciudad_paypal_cliente;
    }

    public String getCodPostal_paypal_cliente() {
        return codPostal_paypal_cliente;
    }

    public void setCodPostal_paypal_cliente(String codPostal_paypal_cliente) {
        this.codPostal_paypal_cliente = codPostal_paypal_cliente;
    }

    public String getProvincia_paypal_cliente() {
        return provincia_paypal_cliente;
    }

    public void setProvincia_paypal_cliente(String provincia_paypal_cliente) {
        this.provincia_paypal_cliente = provincia_paypal_cliente;
    }



    public String getEmail_paypal_cliente() {
        return email_paypal_cliente;
    }

    public void setEmail_paypal_cliente(String email_paypal_cliente) {
        this.email_paypal_cliente = email_paypal_cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetallePedido> getListaDetallePedido() {
        return listaDetallePedido;
    }

    public void setListaDetallePedido(List<DetallePedido> listaDetallePedido) {
        this.listaDetallePedido = listaDetallePedido;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", empleado=" + empleado +
                ", id_paypal_cliente='" + id_paypal_cliente + '\'' +
                ", id_transaccion='" + id_transaccion + '\'' +
                ", nombre_paypal_cliente='" + nombre_paypal_cliente + '\'' +
                ", calle_paypal_cliente='" + calle_paypal_cliente + '\'' +
                ", direccion_paypal_cliente='" + direccion_paypal_cliente + '\'' +
                ", ciudad_paypal_cliente='" + ciudad_paypal_cliente + '\'' +
                ", codPostal_paypal_cliente='" + codPostal_paypal_cliente + '\'' +
                ", provincia_paypal_cliente='" + provincia_paypal_cliente + '\'' +
                ", email_paypal_cliente='" + email_paypal_cliente + '\'' +
                ", total=" + total +
                ", fechaPedido=" + fechaPedido +
                ", estado='" + estado + '\'' +
                '}';
    }
}

