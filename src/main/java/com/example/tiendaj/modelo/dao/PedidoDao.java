package com.example.tiendaj.modelo.dao;

import com.example.tiendaj.modelo.entidades.Carrito;
import com.example.tiendaj.modelo.entidades.Pedido;
import com.example.tiendaj.modelo.entidades.almacen.DetallePedido;

import java.util.List;

public interface PedidoDao {
    public int guardarPedido(Pedido pedido, List<Carrito> lista);
    public List<Pedido> listarPedidos();
    public List<Pedido> listarPedidosPorCliente(int idCliente);
    public List<DetallePedido> listarDetallePedido(int idPedido);
    public Pedido buscarPedido(int idPedido);
    public DetallePedido buscarDetallePedido(int idDetallePedido);
    public int actualizarEstadoDetPedido(int idDetPedido, String estado);
    public int actualizarEstadoPedido(int idPedido, String estado);
    public void generarReporte(Pedido pedido, List<Carrito> lista);

}
