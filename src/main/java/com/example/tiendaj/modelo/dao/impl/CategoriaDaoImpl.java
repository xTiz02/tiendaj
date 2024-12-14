package com.example.tiendaj.modelo.dao.impl;

import com.example.tiendaj.modelo.entidades.Categoria;
import com.example.tiendaj.modelo.dao.CategoriaDao;
import com.example.tiendaj.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDaoImpl implements CategoriaDao {
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    @Override
    public List<Categoria> listarCategorias() {
        List<Categoria> lista= new ArrayList<>();
        try {
            String sql = "SELECT * FROM producto_categoria;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("categoria"));
                c.setDesc(rs.getString("descripcion"));
                lista.add(c);
                //System.out.println(c);
            }
            rs.close();
            pst.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Categoria buscarCategoria(int id) {
        Categoria c= new Categoria();
        try {
            String sql = "SELECT * FROM producto_categoria where id=?;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            if(rs.next()){
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("categoria"));
                c.setDesc(rs.getString("descripcion"));
            }
            rs.close();
            pst.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public int agregarCategoria(Categoria p) {
        int i  =0;
        try {
            String sql = "INSERT INTO producto_categoria(categoria,descripcion) VALUES (?,?);";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setString(1,p.getNombre());
            pst.setString(2,p.getDesc());
            i = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int eliminarCategoria(int id) {
        int i = 0;
        try {
            String sql = "DELETE FROM producto_categoria WHERE id=?;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setInt(1,id);
            i = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int editarCategoria(Categoria p) {
        int i = 0;
        try {
            String sql = "UPDATE producto_categoria SET categoria=?,descripcion=? WHERE id=?;";
            con = Conexion.getConexion();
            pst = con.prepareStatement(sql);
            pst.setString(1,p.getNombre());
            pst.setString(2,p.getDesc());
            pst.setInt(3,p.getId());
            i = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}
