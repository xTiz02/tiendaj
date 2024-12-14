package com.example.tiendaj.controlador.sistema;

import com.example.tiendaj.modelo.dao.CategoriaDao;
import com.example.tiendaj.modelo.dao.InventarioDao;
import com.example.tiendaj.modelo.dao.ProductoDao;
import com.example.tiendaj.modelo.dao.ProveedorDao;
import com.example.tiendaj.modelo.dao.impl.CategoriaDaoImpl;
import com.example.tiendaj.modelo.dao.impl.InventarioDaoImpl;
import com.example.tiendaj.modelo.dao.impl.ProductoDaoImpl;
import com.example.tiendaj.modelo.dao.impl.ProveedorDaoImpl;
import com.example.tiendaj.modelo.entidades.Categoria;
import com.example.tiendaj.modelo.entidades.Producto;
import com.example.tiendaj.modelo.entidades.almacen.Inventario;
import com.example.tiendaj.modelo.entidades.almacen.Proveedor;
import com.example.tiendaj.modelo.entidades.almacen.TipoProveedor;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

@MultipartConfig
@WebServlet(name = "/inventarioController", value = "/inventario-controller")
public class InventarioController extends HttpServlet {
    private InventarioDao invDao;
    private ProveedorDao provDao;
    private CategoriaDao catDao;

    //cuando inicie el servlet
    @Override
    public void init() throws ServletException {
        invDao = new InventarioDaoImpl();
        provDao = new ProveedorDaoImpl();
        catDao = new CategoriaDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        int id;
        Inventario inv;


        switch (accion) {
            case "listar":

                req.setAttribute("listaInventario", invDao.listarInventario());

                req.getRequestDispatcher("sistema/productos/ListaProductos.jsp").forward(req, resp);
                break;
            case "agregar":

                cargarPagina(req, accion);
                req.getRequestDispatcher("sistema/productos/AgreEditProductos.jsp").forward(req, resp);
                break;
            case "editar":

                id = Integer.parseInt(req.getParameter("id"));
                inv = invDao.buscarInventario(invDao.listarInventario(), id);

                cargarPagina(req, accion);
                req.setAttribute("inventario", inv);
                req.getRequestDispatcher("sistema/productos/AgreEditProductos.jsp").forward(req, resp);

                break;
            case "imagenes":
                List<Inventario> listaInventario = invDao.listarInventario();
                id = Integer.parseInt(req.getParameter("id"));
                inv = invDao.buscarInventario(listaInventario, id);
                req.setAttribute("producto", inv.getProducto());
                req.getRequestDispatcher("ListaImagenes.jsp").forward(req, resp);
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
        String nombre, caracteristicas, descripcion, especificaciones;
        int idCat,idProv,n,idProducto,idInv;
        n=0;
        double precio,descuento;
        Inventario inv;
        switch (accion) {
            case "agregar":
                nombre = req.getParameter("txtNombre");
                caracteristicas = req.getParameter("txtCaracteristicas");
                descripcion = req.getParameter("txtDescripcion");
                especificaciones = req.getParameter("txtEspecificaciones");
                precio = Double.parseDouble(req.getParameter("txtPrecio"));
                descuento = Double.parseDouble(req.getParameter("txtDescuento"));
                idCat = Integer.parseInt(req.getParameter("categoria"));
                idProv = Integer.parseInt(req.getParameter("proveedor"));
                Collection<Part> archivos = req.getParts();
                cargarPagina(req, accion);
                for (int i = 8; i < archivos.size(); i++){
                    Part archivo = (Part) archivos.toArray()[i];


                    if(!(archivo.getContentType().equals("image/jpeg") || archivo.getContentType().equals("image/png"))){
                        n=1;
                        break;
                    }
                    if(i==(archivos.size()-2)){
                        break;
                    }
                }
                if(n==1){
                req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                        "                       Error al registrar las imágenes\n" +
                        "                    </div>");
                req.getRequestDispatcher("sistema/productos/AgreEditProductos.jsp").forward(req, resp);
                break;
                }
                for (Part archivo: archivos){
                    if (archivo.getContentType() != null){
                        Proveedor proveedor = new Proveedor();
                        proveedor.setId(idProv);
                        Categoria categoria = new Categoria();
                        categoria.setId(idCat);

                        inv = new Inventario();
                        inv.setProducto(new Producto(0,proveedor,categoria,nombre,caracteristicas,descripcion,especificaciones,precio,descuento,null));
                        inv.setStock(0);
                        inv.setFechaModificado(null);
                        inv.setActivo(0);
                        idProducto = invDao.agregarInventario(inv);


                        if (idProducto > 0){
                            invDao.guardarImagenes(idProducto,archivos);



                            req.setAttribute("mensaje", "<div class=\"alert alert-primary\" role=\"alert\">\n" +
                                    "                        Producto Registrado\n" +
                                    "                    </div>");
                            req.getRequestDispatcher("sistema/productos/AgreEditProductos.jsp").forward(req, resp);
                        }else {
                            req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                                    "                       Error al registrar producto\n" +
                                    "                    </div>");
                            req.getRequestDispatcher("sistema/productos/AgreEditProductos.jsp").forward(req, resp);
                        }
                        break;
                    }
                }
                break;
            case "editar":
                nombre = req.getParameter("txtNombre");
                caracteristicas = req.getParameter("txtCaracteristicas");
                descripcion = req.getParameter("txtDescripcion");
                especificaciones = req.getParameter("txtEspecificaciones");
                precio = Double.parseDouble(req.getParameter("txtPrecio"));
                descuento = Double.parseDouble(req.getParameter("txtDescuento"));
                idCat = Integer.parseInt(req.getParameter("categoria"));
                idProv = Integer.parseInt(req.getParameter("proveedor"));
                idInv = Integer.parseInt(req.getParameter("id"));
                Inventario inventario = invDao.buscarInventario(invDao.listarInventario(),idInv);
                Proveedor proveedor = new Proveedor();
                proveedor.setId(idProv);
                Categoria categoria = new Categoria();
                categoria.setId(idCat);
                n = invDao.actualizarInventario(new Inventario(idInv,new Producto(inventario.getProducto().getId(),proveedor,categoria,nombre,caracteristicas,descripcion,especificaciones,precio,descuento,null),0,null,0));

                if(n>0){
                    cargarPagina(req, accion);
                    req.setAttribute("inventario", invDao.buscarInventario(invDao.listarInventario(),idInv));
                    req.setAttribute("mensaje", "<div class=\"alert alert-primary\" role=\"alert\">\n" +
                            "                        Producto Actualizado\n" +
                            "                    </div>");
                    req.getRequestDispatcher("sistema/productos/AgreEditProductos.jsp").forward(req, resp);
                }else {
                    cargarPagina(req, accion);
                    req.setAttribute("inventario", invDao.buscarInventario(invDao.listarInventario(),idInv));
                    req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                            "                       Error al actualizar producto\n" +
                            "                    </div>");
                    req.getRequestDispatcher("sistema/productos/AgreEditProductos.jsp").forward(req, resp);
                }
                break;
            case "activar":
                int idInventario = Integer.parseInt(req.getParameter("id"));
                int activo = Integer.parseInt(req.getParameter("valor"));
                if (activo == 1){
                    n = invDao.desactivarProducto(idInventario);
                }
                if (activo == 0){
                    n = invDao.activarProducto(idInventario);
                }
                resp.sendRedirect("inventario-controller?accion=listar");

                break;
            default:
                System.out.println("Error");
        }
    }
    public void cargarPagina(HttpServletRequest request,String accion) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("accion", accion);
        List<Proveedor> proveedores = provDao.listaProveedoresActivos();
        List<Categoria> categorias = catDao.listarCategorias();
        request.setAttribute("listaProveedores", proveedores);
        request.setAttribute("listaCategorias", categorias);
        if (accion.equals("editar"))
            request.setAttribute("titulo", "Editar Productos");
        else if (accion.equals("agregar")){
            request.setAttribute("titulo", "Agregar Productos");
        }

    }

}
