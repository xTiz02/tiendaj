package com.example.tiendaj.modelo.entidades;

import java.time.LocalDateTime;

public class Usuario {

    private int id;
    private Cliente cliente;
    private String usuario;

    private byte[] password;
    private String rol;

    public Usuario(){

    }

    public Usuario(int id, Cliente cliente, String usuario, byte[] password, String rol) {
        this.id = id;
        this.cliente = cliente;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
