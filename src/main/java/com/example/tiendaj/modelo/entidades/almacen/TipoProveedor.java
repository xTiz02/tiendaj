package com.example.tiendaj.modelo.entidades.almacen;

public class TipoProveedor {
    private int id;
    private String tipo;
    private String descripcion;
    public TipoProveedor(){

    }
    public TipoProveedor(int id,String tipo, String descripcion) {
        this.tipo = tipo;
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "TipoProveedor{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
