package com.example.tiendaj.controlador.sistema;

import com.example.tiendaj.modelo.dao.EntregaDao;
import com.example.tiendaj.modelo.dao.InventarioDao;
import com.example.tiendaj.modelo.dao.ProductoDao;
import com.example.tiendaj.modelo.dao.ProductoUnidadDao;
import com.example.tiendaj.modelo.dao.impl.EntregaDaoImpl;
import com.example.tiendaj.modelo.dao.impl.InventarioDaoImpl;
import com.example.tiendaj.modelo.dao.impl.ProductoUnidadDaoImpl;
import com.example.tiendaj.modelo.entidades.almacen.DetalleEntrega;
import com.example.tiendaj.modelo.entidades.almacen.ProductoUnidad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet(name = "/productoUnidadController", value = "/productoUnidad-controller")
public class ProductoUnidadController extends HttpServlet {

    private ProductoUnidadDao productoUnidadDao;
    private InventarioDao invDao;
    private EntregaDao entregaDao;

    @Override
    public void init(){
        productoUnidadDao = new ProductoUnidadDaoImpl();
        entregaDao = new EntregaDaoImpl();
        invDao = new InventarioDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");

        switch (accion){
            case "listar":
                String idDetEntrega = req.getParameter("id");
                String idProducto = req.getParameter("idProducto");
                if(idDetEntrega!=null){
                    List<ProductoUnidad> lista = productoUnidadDao.listarUnidadesPorDetEntrega(Integer.parseInt(idDetEntrega));
                    req.setAttribute("listaUnidades", lista);
                    req.getRequestDispatcher("sistema/unidades/ListaUnidades.jsp").forward(req, resp);
                }else{
                    if (idProducto!=null){
                        int id = Integer.parseInt(idProducto);
                        List<ProductoUnidad> lista = productoUnidadDao.listarUnidadesDeProducto(id);
                        req.setAttribute("listaUnidades", lista);
                        req.getRequestDispatcher("sistema/unidades/ListaUnidades.jsp").forward(req, resp);
                        break;
                    }else {
                        List<ProductoUnidad> lista = productoUnidadDao.listarUnidades();
                /*for (ProductoUnidad p: lista) {
                    p.getDetalleEntrega().setEntrega(entregaDao.buscarEntrega(p.getDetalleEntrega().getEntrega().getId()));
                }*/
                        req.setAttribute("listaUnidades", lista);
                        req.getRequestDispatcher("sistema/unidades/ListaUnidades.jsp").forward(req, resp);
                        break;
                    }
                }
                break;

            case "agregar":
                int id = Integer.parseInt(req.getParameter("id"));
                DetalleEntrega detalleEntrega = entregaDao.buscarDetalleEntrega(id);
                cargarPagina(req, accion);
                req.setAttribute("detalleEntrega", detalleEntrega);
                req.getRequestDispatcher("sistema/unidades/AgreEditUnidades.jsp").forward(req, resp);
                break;
            case "editar":
                String idUnidad = req.getParameter("id");
                cargarPagina(req, accion);

                req.setAttribute("unidad", productoUnidadDao.buscarUnidad(idUnidad));
                req.getRequestDispatcher("sistema/unidades/AgreEditUnidades.jsp").forward(req, resp);
                break;
            default:
                req.getRequestDispatcher("sistema/unidades/AgreEditUnidades.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        int i=0;
        switch (accion){
            case "agregar":
                int idDetalle = Integer.parseInt(req.getParameter("idDetalle"));
                String estado = req.getParameter("estado");
                String codigo = req.getParameter("txtCodigo");
                ProductoUnidad productoUnidad = new ProductoUnidad();
                productoUnidad.setCodigo(codigo);
                productoUnidad.setEstado(estado);
                DetalleEntrega detalleEntrega = entregaDao.buscarDetalleEntrega(idDetalle);
                productoUnidad.setDetalleEntrega(detalleEntrega);
                productoUnidad.setProducto(detalleEntrega.getProducto());
                i = productoUnidadDao.registrarUnidad(productoUnidad);
                if(i!=0){
                    Integer[] cantidades = productoUnidadDao.numerosUnidadesPorDetEntrega(idDetalle);
                    if(!cantidades[1].equals(cantidades[0])){
                        i = entregaDao.cambiarEstadoDetEntrega(idDetalle, "NG");
                        req.setAttribute("mensaje", "<div class=\"alert alert-primary\" role=\"alert\">\n" +
                                "                        Unidad Registrada Correctamente\n" +
                                "                    </div>");
                        cargarPagina(req, accion);
                        req.setAttribute("detalleEntrega", detalleEntrega);
                        //System.out.println(1);
                        req.getRequestDispatcher("sistema/unidades/AgreEditUnidades.jsp").forward(req, resp);
                    }else {
                        i = entregaDao.cambiarEstadoDetEntrega(idDetalle, "G");
                        //System.out.println(2);
                        resp.sendRedirect("entrega-controller?accion=ver&id="+detalleEntrega.getEntrega().getId());
                        break;
                    }
                }
                else {
                    req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                            "                       Error al registrar Unidad\n" +
                            "                    </div>");
                    cargarPagina(req, accion);
                    req.setAttribute("detalleEntrega", entregaDao.buscarDetalleEntrega(idDetalle));
                    req.getRequestDispatcher("sistema/unidades/AgreEditUnidades.jsp").forward(req, resp);
                }
                break;
            case "editar":
                String idUnidad = req.getParameter("txtCodigo");
                String estadoEdit = req.getParameter("estado");

                ProductoUnidad productoUnidadEdit = productoUnidadDao.buscarUnidad(idUnidad);

                switch (estadoEdit){
                    case "Stock":
                        if(productoUnidadEdit.getEstado().equals("Stock")){

                            break;
                        }else {

                            productoUnidadEdit.setEstado(estadoEdit);
                            i = productoUnidadDao.actualizarUnidad(productoUnidadEdit,1);//aumenta
                        }
                        break;
                    case "Defectuoso":
                        if (productoUnidadEdit.getEstado().equals("Defectuoso")) {
                            break;
                        }else {
                            if(productoUnidadEdit.getEstado().equals("Stock")){
                                productoUnidadEdit.setEstado(estadoEdit);
                                if(invDao.stockProducto(productoUnidadEdit.getProducto().getId())>0) {
                                    i = productoUnidadDao.actualizarUnidad(productoUnidadEdit,2);//disminuir
                                }else{
                                    i = productoUnidadDao.actualizarUnidad(productoUnidadEdit,0);//nada
                                }

                            }
                            if (productoUnidadEdit.getEstado().equals("Comprado")){
                                productoUnidadEdit.setEstado(estadoEdit);
                                i = productoUnidadDao.actualizarUnidad(productoUnidadEdit,0);//nada
                            }
                        }
                        break;
                    case "Comprado":
                        if (productoUnidadEdit.getEstado().equals("Comprado") || productoUnidadEdit.getEstado().equals("Defectuoso")) {
                            productoUnidadEdit.setEstado(estadoEdit);
                            i = productoUnidadDao.actualizarUnidad(productoUnidadEdit,0);
                        }else {

                            productoUnidadEdit.setEstado(estadoEdit);
                            i = productoUnidadDao.actualizarUnidad(productoUnidadEdit,2);//disminuir
                        }
                        break;
                    default:
                        System.out.println("Error");
                        break;
                }
                if (i!=0){
                    req.setAttribute("mensaje", "<div class=\"alert alert-primary\" role=\"alert\">\n" +
                            "                        Unidad Actualizada Correctamente\n" +
                            "                    </div>");
                    cargarPagina(req, accion);
                    req.setAttribute("unidad", productoUnidadEdit);
                    req.getRequestDispatcher("sistema/unidades/AgreEditUnidades.jsp").forward(req, resp);

                }else {
                    req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                            "                        Error al Actualizar Unidad\n" +
                            "                    </div>");
                    cargarPagina(req, accion);
                    req.setAttribute("unidad", productoUnidadEdit);
                    req.getRequestDispatcher("sistema/unidades/AgreEditUnidades.jsp").forward(req, resp);
                }
                break;
            case "eliminar":
                String id = req.getParameter("id");
                ProductoUnidad p = productoUnidadDao.buscarUnidad(id);
                i = productoUnidadDao.eliminarUnidad(id);
                if(i!=0){
                    if (p.getEstado().equals("Stock")){
                        if(invDao.stockProducto(p.getProducto().getId())>0){
                            i = invDao.disminuirStock(p.getProducto().getId(), 1);
                        }
                    }
                    int idDet = p.getDetalleEntrega().getId();
                     Integer[] cantidades = productoUnidadDao.numerosUnidadesPorDetEntrega(idDet);
                    if(!cantidades[1].equals(cantidades[0])){
                        i = entregaDao.cambiarEstadoDetEntrega(idDet, "NG");
                        i = entregaDao.cambiarEstadoEntrega(p.getDetalleEntrega().getEntrega().getId(), "NG");
                    }else {
                        i = entregaDao.cambiarEstadoDetEntrega(idDet, "G");
                    }
                }
                resp.sendRedirect("productoUnidad-controller?accion=listar");
                break;
            default:
                 break;
        }
    }
    public void cargarPagina(HttpServletRequest req, String accion) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("accion", accion);
        switch (accion){
            case "editar":
                req.setAttribute("titulo", "Editar Unidad");
                break;
            case "agregar":
                req.setAttribute("titulo", "Agregar Unidad");
                break;
        }

    }
}
