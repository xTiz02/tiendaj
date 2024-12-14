package com.example.tiendaj.controlador.sistema;

import com.example.tiendaj.modelo.dao.InventarioDao;
import com.example.tiendaj.modelo.dao.PedidoDao;
import com.example.tiendaj.modelo.dao.ProductoUnidadDao;
import com.example.tiendaj.modelo.dao.RetiroDao;
import com.example.tiendaj.modelo.dao.impl.InventarioDaoImpl;
import com.example.tiendaj.modelo.dao.impl.PedidoDaoImpl;
import com.example.tiendaj.modelo.dao.impl.ProductoUnidadDaoImpl;
import com.example.tiendaj.modelo.dao.impl.RetiroDaoImpl;
import com.example.tiendaj.modelo.entidades.Pedido;
import com.example.tiendaj.modelo.entidades.almacen.DetallePedido;
import com.example.tiendaj.modelo.entidades.almacen.ProductoUnidad;
import com.example.tiendaj.modelo.entidades.almacen.RetiroProducto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "/pedidoController", value = "/pedido-controller")
public class PedidoController extends HttpServlet {
    private PedidoDao pedidoDao;
    private RetiroDao retiroDao;
    private ProductoUnidadDao productoUnidadDao;
    private InventarioDao inventarioDao;

    @Override
    public void init() throws ServletException {
        pedidoDao = new PedidoDaoImpl();
        retiroDao = new RetiroDaoImpl();
        productoUnidadDao = new ProductoUnidadDaoImpl();
        inventarioDao = new InventarioDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        switch (accion){
            case "listar":
                List<Pedido> listaPedidos = pedidoDao.listarPedidos();
                for (Pedido pedido : listaPedidos){
                    List<DetallePedido> lista = pedidoDao.listarDetallePedido(pedido.getId());
                    for (DetallePedido detallePedido : lista){
                        List<RetiroProducto> listaRetiro = retiroDao.listarPorDetallePedido(detallePedido.getId());
                        detallePedido.setListaRetiroProducto(listaRetiro);
                    }
                    pedido.setListaDetallePedido(lista);
                }
                req.setAttribute("listaPedidos",listaPedidos);
                req.getRequestDispatcher("/sistema/pedidos/ListaPedidos.jsp").forward(req,resp);
                break;
            case "ver":
                int idPedido = Integer.parseInt(req.getParameter("id"));
                Pedido pedido = pedidoDao.buscarPedido(idPedido);
                List<DetallePedido> lista = pedidoDao.listarDetallePedido(idPedido);
                pedido.setListaDetallePedido(lista);

                req.setAttribute("pedido",pedido);
                req.getRequestDispatcher("/sistema/pedidos/ListaDetPedido.jsp").forward(req,resp);
                break;
            default:
                req.setAttribute("pedidos",pedidoDao.listarPedidos());
                req.getRequestDispatcher("WEB-INF/jsp/sistema/pedido/listar.jsp").forward(req,resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        int i = 0;
        switch (accion){
            case "cambiar":
                List<RetiroProducto> listaRetiro = null;
                int idPedido = Integer.parseInt(req.getParameter("id"));
                Pedido pedido = pedidoDao.buscarPedido(idPedido);
                List<DetallePedido> lista = pedidoDao.listarDetallePedido(idPedido);
                pedido.setListaDetallePedido(lista);
                String[] codUnidDeftuosas = req.getParameterValues("seleccionados");
                if(codUnidDeftuosas!=null){
                    for (String codUnid : codUnidDeftuosas){
                        int idDetPedido = retiroDao.traerIdDetPedidoPorCod(codUnid);
                        pedidoDao.actualizarEstadoDetPedido(idDetPedido,"NG");
                        pedidoDao.actualizarEstadoPedido(idPedido,"Pendiente");
                        i = retiroDao.eliminarRetiroPorCodUnidad(codUnid);
                        if (i>0){
                            i=0;
                            i = productoUnidadDao.actualizarEstadoUnidad(codUnid,"Defectuoso");
                            ProductoUnidad productoUnidad = productoUnidadDao.buscarUnidad(codUnid);
                            if (inventarioDao.stockProducto(productoUnidad.getProducto().getId())>0) {
                                i = inventarioDao.disminuirStock(productoUnidad.getProducto().getId(),1);
                            }
                            if(i>0){
                                System.out.println("pedido actualizado");
                                resp.sendRedirect("pedido-controller?accion=listar");
                            }
                        }else {
                            System.out.println("pedido no actualizado");
                            resp.sendRedirect("pedido-controller?accion=listar");
                            break;
                        }
                    }
                }else {
                    System.out.println("Se anulo el pedido");
                    for(DetallePedido detallePedido : pedido.getListaDetallePedido()){
                        listaRetiro = retiroDao.listarPorDetallePedido(detallePedido.getId());
                        if(!listaRetiro.isEmpty()){
                            for (RetiroProducto retiroProducto : listaRetiro){
                                i = productoUnidadDao.actualizarEstadoUnidad(retiroProducto.getProductoUnidad().getCodigo(),"Stock");
                                i = retiroDao.eliminarRetiro(retiroProducto.getId());
                            }
                        }
                        //pone otra ve cada producto del detalle en stock
                        i = inventarioDao.aumentarStock(detallePedido.getProducto().getId(),detallePedido.getCantidad());
                    }
                    i = pedidoDao.actualizarEstadoPedido(idPedido,"Anulado");
                    resp.sendRedirect("pedido-controller?accion=listar");
                    break;
                }
                break;
            default:
                System.out.println("pedido cambiar");
                break;
        }
    }
}
