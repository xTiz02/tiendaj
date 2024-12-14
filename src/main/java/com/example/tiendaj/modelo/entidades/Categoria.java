package com.example.tiendaj.modelo.entidades;

public class Categoria {
    private int id;
    private String nombre;
    private String desc;
    public Categoria(){

    }

    public Categoria(int id,String nombre,String desc) {
        this.id = id;
        this.nombre = nombre;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
