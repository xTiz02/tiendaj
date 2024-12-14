package com.example.tiendaj.controlador;

import com.example.tiendaj.modelo.dao.CategoriaDao;
import com.example.tiendaj.modelo.dao.impl.CategoriaDaoImpl;
import com.example.tiendaj.modelo.entidades.Categoria;
import com.example.tiendaj.modelo.entidades.Producto;
import com.example.tiendaj.modelo.dao.ProductoDao;
import com.example.tiendaj.modelo.dao.impl.ProductoDaoImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "catalogoController", value = "/catalogo-controller")
public class CatalogoController extends HttpServlet {
    private ProductoDao proDao;
    private CategoriaDao catDao;

    //cuando inicie el servlet
    @Override
    public void init() throws ServletException {

        proDao = new ProductoDaoImpl();
        catDao = new CategoriaDaoImpl();


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        if (accion == null) {
            req.setAttribute("listaProductos", proDao.listarProductosActivos());
            req.setAttribute("listaCategorias", catDao.listarCategorias());
            req.getRequestDispatcher("Catalogo.jsp").forward(req, resp);
        } else {
            switch (accion) {
                case "index":
                    //carga las imagenes de los productos

                    List<Producto> lista = proDao.flitrarPorDescuento(proDao.listarProductosActivos(), 4);//filtra los productos que tienen descuento
                    req.setAttribute("listaProductos", lista);
                    req.getRequestDispatcher("index.jsp").forward(req, resp);
                    break;
                case "ver":
                    int id = Integer.parseInt(req.getParameter("id"));
                    Producto producto = proDao.buscarProductoPorId(proDao.listarProductosActivos(), id);
                    String[] caracteristicas = producto.getCaracteristicas().split(";");
                    String[] descripcion = producto.getDescripcion().split(";");
                    String[] especificaciones = producto.getEspecificaciones().split(";");
                    List<String[]> listaEspecificaciones = new ArrayList<>();
                    for (String s : especificaciones) {
                        String[] primer = s.split(":");
                        listaEspecificaciones.add(primer);
                    }

                    req.setAttribute("producto", producto);
                    req.setAttribute("caracteristicas", caracteristicas);
                    req.setAttribute("descripcion", descripcion);
                    req.setAttribute("especificaciones", listaEspecificaciones);
                    req.getRequestDispatcher("DetalleProducto.jsp").forward(req, resp);
                    break;
                    case "buscar":
                        String nombre = req.getParameter("buscar");

                        List<Producto> listaProductos = proDao.flitrarPorNombre(proDao.listarProductosActivos(), nombre);

                        req.setAttribute("listaProductos", listaProductos);
                        req.setAttribute("listaCategorias", catDao.listarCategorias());
                        req.getRequestDispatcher("Catalogo.jsp").forward(req, resp);
                        break;
                default:
                    System.out.println("entro al default");
            }
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("carrito");

        if (accion == null) {
            String[] categoriasId = req.getParameterValues("categoria[]");//si no escoge cat es nulo
            double precio = Double.parseDouble(req.getParameter("precio"));

            List<Producto> listaFiltrada = proDao.filtrarPorCategoria(proDao.listarProductosActivos(), categoriasId, precio);
            req.setAttribute("listaProductos", listaFiltrada);
            req.setAttribute("listaCategorias", catDao.listarCategorias());
            req.getRequestDispatcher("Catalogo.jsp").forward(req, resp);
        } else {
            System.out.println("entro al carrito");
            int id = Integer.parseInt(req.getParameter("idp"));

            req.setAttribute("listaProductos", proDao.listarProductosActivos());
            req.setAttribute("id", id);
            req.setAttribute("accion", "agregar");
            req.getRequestDispatcher("/carrito-controller").forward(req, resp);
        }

    }
}
