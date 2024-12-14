package com.example.tiendaj.controlador.sistema;

import com.example.tiendaj.modelo.dao.*;
import com.example.tiendaj.modelo.dao.impl.*;
import com.example.tiendaj.modelo.entidades.almacen.DetalleEntrega;
import com.example.tiendaj.modelo.entidades.almacen.Entrega;
import com.example.tiendaj.modelo.entidades.almacen.ProductoUnidad;
import com.example.tiendaj.modelo.entidades.almacen.Proveedor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "/entregaController", value = "/entrega-controller")
public class EntregaController extends HttpServlet {
    private ProveedorDao proveedorDao;
    private EntregaDao entregaDao;
    private ProductoDao productoDao;
    private InventarioDao invDao;
    private ProductoUnidadDao productoUnidadDaoDao;

    @Override
    public void init() throws ServletException {
        proveedorDao = new ProveedorDaoImpl();
        entregaDao = new EntregaDaoImpl();
        productoDao = new ProductoDaoImpl();
        invDao = new InventarioDaoImpl();
        productoUnidadDaoDao = new ProductoUnidadDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        switch (accion){
            case "listar":
                req.setAttribute("listaEntregas", entregaDao.listarEntregas());
                req.getRequestDispatcher("sistema/entrega/ListaEntregas.jsp").forward(req, resp);
                break;
            case "ver":
                int id = Integer.parseInt(req.getParameter("id"));
                Entrega entrega = entregaDao.buscarEntrega(id);
                int i = 0;
                for (DetalleEntrega det: entrega.getDetalleEntregaLista()){
                    if(det.getEstado().equals("G")){
                        i++;
                    }
                }
                if(i==entrega.getDetalleEntregaLista().size()) {
                    entregaDao.cambiarEstadoEntrega(id, "G");
                }else{
                    entregaDao.cambiarEstadoEntrega(id, "NG");
                }
                req.setAttribute("entrega", entrega);
                req.getRequestDispatcher("sistema/entrega/ListaDetEntrega.jsp").forward(req, resp);
                break;
            case "agregar":
                cargarPagina(req, accion);
                req.getRequestDispatcher("sistema/entrega/AgreEditEntregas.jsp").forward(req, resp);
                break;
            case "editar":
                int idEditar = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("entrega", entregaDao.buscarEntrega(idEditar));
                cargarPagina(req, accion);
                req.getRequestDispatcher("sistema/entrega/AgreEditEntregas.jsp").forward(req, resp);
                break;
            default:
                System.out.println("Error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        int idEntrega;
        switch (accion){
            case "agregar":
                int cantidadTotal = 0;
                double pagoTotal = 0;
                List<DetalleEntrega> listaDetalles = new ArrayList<>();
                int idProveedor = Integer.parseInt(req.getParameter("proveedor"));
                Proveedor proveedor = proveedorDao.buscarProveedor(idProveedor);
                String productosSeleccionados = req.getParameter("productosSeleccionados");

                if (productosSeleccionados != null && !productosSeleccionados.isEmpty()) {
                    // Divide la cadena en elementos separados por ';'
                    String[] productosArray = productosSeleccionados.split(";");

                    // Ahora puedes trabajar con los elementos individuales
                    for (String productoInfo : productosArray) {
                        // Divide cada elemento en ID, Cantidad y Precio
                        String[] partes = productoInfo.split(":");
                        if (partes.length == 3) {
                            String id = partes[0];
                            String cantidad = partes[1];
                            String precio = partes[2];
                            // Convierte las cadenas a números (si es necesario)
                            int idProducto = Integer.parseInt(id);
                            int cantidadProducto = Integer.parseInt(cantidad);
                            double pago = Double.parseDouble(precio);
                            cantidadTotal += cantidadProducto;
                            pagoTotal += pago;
                            listaDetalles.add(new DetalleEntrega(0,null,productoDao.buscarProducto(idProducto), cantidadProducto, pago,"NG"));
                            //System.out.println("ID del Producto: " + idProducto + ", Cantidad: " + cantidadProducto + ", Precio: " + precioProducto);
                        }
                    }
                }
                Entrega entrega = new Entrega(0,proveedor,cantidadTotal,null,listaDetalles,pagoTotal,"NG");
                int i = entregaDao.agregarEntrega(entrega);
                if(i!=0){
                    req.setAttribute("mensaje", "<div class=\"alert alert-primary\" role=\"alert\">\n" +
                            "                        Entrega Agregada\n" +
                            "                    </div>");
                    cargarPagina(req, accion);

                    req.getRequestDispatcher("sistema/entrega/AgreEditEntregas.jsp").forward(req, resp);
                    break;
                }else {
                    req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                            "                       Error en la Entrega\n" +
                            "                    </div>");
                    cargarPagina(req, accion);
                    req.getRequestDispatcher("sistema/entrega/AgreEditEntregas.jsp").forward(req, resp);
                    break;
                }

            case "editar":
                idEntrega = Integer.parseInt(req.getParameter("id"));
                Entrega entregaEdit = entregaDao.buscarEntrega(idEntrega);
                int cantidadTotalEdit = 0;
                double pagoTotalEdit = 0;
                List<DetalleEntrega> listaDetallesEdit = new ArrayList<>();
                int idProveedorEdit = Integer.parseInt(req.getParameter("proveedor"));
                Proveedor proveedorEdit = proveedorDao.buscarProveedor(idProveedorEdit);
                //name="producto1" name="producto2" name="producto3" ...
                for (int i1 = 1; i1 <= 10; i1++) {
                    if(req.getParameter("producto"+i1)!=null){
                        int idProducto = Integer.parseInt(req.getParameter("producto"+i1));
                        int cantidadProducto = Integer.parseInt(req.getParameter("numero"+i1));
                        double pago = Double.parseDouble(req.getParameter("pago"+i1));
                        cantidadTotalEdit += cantidadProducto;
                        pagoTotalEdit += pago;
                        DetalleEntrega detalleEntrega = entregaEdit.getDetalleEntregaLista().get(i1-1);
                        detalleEntrega.setProducto(productoDao.buscarProducto(idProducto));
                        detalleEntrega.setCantidad(cantidadProducto);
                        detalleEntrega.setPago(pago);
                        listaDetallesEdit.add(detalleEntrega);

                    }
                }
                entregaEdit.setProveedor(proveedorEdit);
                entregaEdit.setCantidad(cantidadTotalEdit);
                entregaEdit.setTotal(pagoTotalEdit);
                entregaEdit.setDetalleEntregaLista(listaDetallesEdit);
                int i2 = entregaDao.editarEntrega(entregaEdit);
                if(i2!=0){
                    Entrega entregaEditada = entregaDao.buscarEntrega(idEntrega);
                    for(DetalleEntrega det:entregaEditada.getDetalleEntregaLista()){
                        Integer[] cantidades = productoUnidadDaoDao.numerosUnidadesPorDetEntrega(det.getId());
                        if(!cantidades[1].equals(cantidades[0])){
                            i = entregaDao.cambiarEstadoDetEntrega(det.getId(), "NG");
                            i = entregaDao.cambiarEstadoEntrega(idEntrega, "NG");
                        }else {
                            i = entregaDao.cambiarEstadoDetEntrega(det.getId(), "G");
                        }
                    }
                    req.setAttribute("entrega", entregaEditada);
                    cargarPagina(req, accion);
                    req.setAttribute("mensaje", "<div class=\"alert alert-primary\" role=\"alert\">\n" +
                            "                        Entrega Editada\n" +
                            "                    </div>");

                    req.getRequestDispatcher("sistema/entrega/AgreEditEntregas.jsp").forward(req, resp);

                }else {
                    req.setAttribute("entrega", entregaDao.buscarEntrega(idEntrega));
                    cargarPagina(req, accion);
                    req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                            "                       Error en la Entrega\n" +
                            "                    </div>");

                    req.getRequestDispatcher("sistema/entrega/AgreEditEntregas.jsp").forward(req, resp);

                }
                break;
            case "eliminar":
                i = 0;
                idEntrega = Integer.parseInt(req.getParameter("id"));
                List<DetalleEntrega> listaDetallesEliminar = entregaDao.listarDetalleEntrega(idEntrega);
                for(DetalleEntrega det: listaDetallesEliminar){
                    List<ProductoUnidad> listaUnidades = productoUnidadDaoDao.listarUnidadesPorDetEntrega(det.getId());
                    boolean estaComrado = listaUnidades.stream().anyMatch(unidad -> unidad.getEstado().equals("Comprado"));
                    if (estaComrado){
                        i = 1;
                        break;
                    }
                }
                if (i != 1){
                    for (DetalleEntrega det: listaDetallesEliminar){
                        List<ProductoUnidad> listaUnidades = productoUnidadDaoDao.listarUnidadesPorDetEntrega(det.getId());
                        if (!listaUnidades.isEmpty()){
                            for(ProductoUnidad unidad: listaUnidades){
                                if(unidad.getEstado().equals("Stock")){
                                    i = productoUnidadDaoDao.eliminarUnidad(unidad.getCodigo());
                                    i = invDao.disminuirStock(unidad.getProducto().getId(), 1);
                                    System.out.println("Eliminado y disminuido");
                                }else {
                                    i = productoUnidadDaoDao.eliminarUnidad(unidad.getCodigo());
                                    System.out.println("Eliminado");
                                }
                            }
                        }
                    }
                    i = entregaDao.eliminarDetallesEntregas(idEntrega);
                    i = entregaDao.eliminarEntrega(idEntrega);
                    req.setAttribute("listaEntregas", entregaDao.listarEntregas());
                    req.getRequestDispatcher("sistema/entrega/ListaEntregas.jsp").forward(req, resp);

                }else {
                    req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                            "                       No se puede eliminar la entrega\n" +
                            "                    </div>");
                    req.setAttribute("listaEntregas", entregaDao.listarEntregas());
                    req.getRequestDispatcher("sistema/entrega/ListaEntregas.jsp").forward(req, resp);
                    break;
                }
                break;
            default:
                System.out.println("Error");
        }
    }
    public void cargarPagina(HttpServletRequest req, String accion) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("accion", accion);

        req.setAttribute("listaProveedores", proveedorDao.listaProveedoresActivos());
        req.setAttribute("listaInventario", invDao.listarInventario());
        switch (accion){
            case "editar":
                req.setAttribute("titulo", "Editar Entrega");
                break;
            case "agregar":
                req.setAttribute("titulo", "Agregar Entrega");
                break;
        }

    }
}
