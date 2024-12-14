package com.example.tiendaj.modelo.dao;

import com.example.tiendaj.modelo.entidades.Usuario;

import java.util.List;

public interface UsuarioDao {
    public Usuario validarUsuario(String email, String ps,String rol);
    public int crearUsuario(Usuario u);
    public List<Usuario> listarUsuarios();
    public Usuario buscarUsuario(int id);
    public int cambiarEstado(int idCliente, int estado);
}
