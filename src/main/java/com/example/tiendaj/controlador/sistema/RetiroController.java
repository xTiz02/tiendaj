package com.example.tiendaj.controlador.sistema;

import com.example.tiendaj.modelo.dao.PedidoDao;
import com.example.tiendaj.modelo.dao.ProductoUnidadDao;
import com.example.tiendaj.modelo.dao.RetiroDao;
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
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet(name = "/retiroController", value = "/retiro-controller")
public class RetiroController extends HttpServlet   {
    private RetiroDao retiroDao;
    private PedidoDao pedidoDao;
    private ProductoUnidadDao proUniDao;

    @Override
    public void init() throws ServletException {
        retiroDao = new RetiroDaoImpl();
        pedidoDao = new PedidoDaoImpl();
        proUniDao = new ProductoUnidadDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        int idDetPedido;
        int i= 0;
        List<RetiroProducto> lista;
        DetallePedido detallePedido;
        switch (accion){
            case "listar":
                //req.setAttribute("listaRetiros",retiroDao.listarRetiros());
                req.getRequestDispatcher("/sistema/retiros/ListaRetiros.jsp").forward(req,resp);
                break;
            case "ver":
                idDetPedido = Integer.parseInt(req.getParameter("id"));
                detallePedido = pedidoDao.buscarDetallePedido(idDetPedido);
                lista = retiroDao.listarPorDetallePedido(idDetPedido);
                detallePedido.setListaRetiroProducto(lista);
                List<DetallePedido> listaDetalles = pedidoDao.listarDetallePedido(detallePedido.getPedido().getId());
                for(DetallePedido det: listaDetalles){
                    if(det.getEstado().equals("G")){
                        i++;
                    }
                }
                if (i==listaDetalles.size()){
                    i = pedidoDao.actualizarEstadoPedido(detallePedido.getPedido().getId(),"En curso");
                }else {
                    i = pedidoDao.actualizarEstadoPedido(detallePedido.getPedido().getId(),"Pendiente");
                }
                req.setAttribute("detallePedido",detallePedido);
                req.getRequestDispatcher("/sistema/pedidos/ListaProductoRetiro.jsp").forward(req,resp);
                break;
            case"agregar":
                idDetPedido = Integer.parseInt(req.getParameter("id"));
                detallePedido = pedidoDao.buscarDetallePedido(idDetPedido);
                List<ProductoUnidad> listaUnidades = proUniDao.listarUnidadesPorProducto(detallePedido.getProducto().getId(),"Stock");
                cargarPagina(req,accion);
                req.setAttribute("listaUnidades",listaUnidades);
                req.setAttribute("detallePedido",detallePedido);
                req.getRequestDispatcher("/sistema/pedidos/AgreEditRetiro.jsp").forward(req,resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        int idDetPedido,i;
        String codUnidad;
        switch (accion){
            case "agregar":
                idDetPedido = Integer.parseInt(req.getParameter("idDetalle"));
                codUnidad = req.getParameter("unidad");
                RetiroProducto retiroProducto = new RetiroProducto();
                DetallePedido detallePedido = pedidoDao.buscarDetallePedido(idDetPedido);
                ProductoUnidad productoUnidad = proUniDao.buscarUnidad(codUnidad);
                retiroProducto.setDetallePedido(detallePedido);
                retiroProducto.setProductoUnidad(productoUnidad);
                //fecha
                i = retiroDao.agregarRetiro(retiroProducto);
                if(i!=0){
                    //cambiar estado del producto_unidad a comprado
                    i = proUniDao.actualizarEstadoUnidad(codUnidad,"Comprado");
                    if(detallePedido.getCantidad()!=retiroDao.cantidadRetiradaPorDetPedido(idDetPedido)){
                        i = pedidoDao.actualizarEstadoDetPedido(idDetPedido,"NG");
                        req.setAttribute("mensaje", "<div class=\"alert alert-primary\" role=\"alert\">\n" +
                                "                        Retiro Registrado Correctamente\n" +
                                "                    </div>");
                        List<ProductoUnidad> listaUnidades = proUniDao.listarUnidadesPorProducto(detallePedido.getProducto().getId(),"Stock");
                        cargarPagina(req,accion);
                        req.setAttribute("listaUnidades",listaUnidades);
                        req.setAttribute("detallePedido", detallePedido);
                        System.out.println(1);
                        req.getRequestDispatcher("sistema/pedidos/AgreEditRetiro.jsp").forward(req, resp);

                    }else {
                        i = pedidoDao.actualizarEstadoDetPedido(idDetPedido,"G");
                        System.out.println(2);
                        resp.sendRedirect("pedido-controller?accion=ver&id="+detallePedido.getPedido().getId());
                        break;
                    }
                }else {
                    req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                            "                       Error al registrar Retiro\n" +
                            "                    </div>");
                    List<ProductoUnidad> listaUnidades = proUniDao.listarUnidadesPorProducto(detallePedido.getProducto().getId(),"Stock");
                    cargarPagina(req,accion);
                    req.setAttribute("listaUnidades",listaUnidades);
                    req.setAttribute("detallePedido", detallePedido);
                    req.getRequestDispatcher("sistema/pedidos/AgreEditRetiro.jsp").forward(req, resp);
                }
                break;
            default:
                System.out.println("default retiro");
                break;
        }
    }

    public void cargarPagina(HttpServletRequest req, String accion) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("accion", accion);
        switch (accion){
            case "editar":
                req.setAttribute("titulo", "Editar Retiro");
                break;
            case "agregar":
                req.setAttribute("titulo", "Agregar Retiro");
                break;
        }

    }
}
