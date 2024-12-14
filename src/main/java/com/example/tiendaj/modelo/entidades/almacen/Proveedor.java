package com.example.tiendaj.modelo.entidades.almacen;

public class Proveedor {
    private int id;
    private TipoProveedor tipo;
    private String nombre;
    private String ruc;
    private String ubicacion;
    private String distrito;
    private String direccion;
    private String telefono;
    private String correo;
    private String contacto;
    private int estado;

    public Proveedor() {
    }

    public Proveedor(int id, TipoProveedor tipo, String nombre, String ruc, String ubicacion, String distrito, String direccion, String telefono, String correo, String contacto, int estado) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.ruc = ruc;
        this.ubicacion = ubicacion;
        this.distrito = distrito;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.contacto = contacto;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoProveedor getTipo() {
        return tipo;
    }

    public void setTipo(TipoProveedor tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", nombre='" + nombre + '\'' +
                ", ruc='" + ruc + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", distrito='" + distrito + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", contacto='" + contacto + '\'' +
                '}';
    }
}
