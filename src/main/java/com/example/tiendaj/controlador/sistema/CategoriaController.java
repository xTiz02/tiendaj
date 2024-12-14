package com.example.tiendaj.controlador.sistema;

import com.example.tiendaj.modelo.dao.CategoriaDao;
import com.example.tiendaj.modelo.dao.impl.CategoriaDaoImpl;
import com.example.tiendaj.modelo.dao.impl.ProveedorDaoImpl;
import com.example.tiendaj.modelo.entidades.Categoria;
import com.example.tiendaj.modelo.entidades.almacen.Proveedor;
import com.example.tiendaj.modelo.entidades.almacen.TipoProveedor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet(name = "/categoriaController", value = "/categoria-controller")
public class CategoriaController extends HttpServlet {

    private CategoriaDao dao;

    @Override
    public void init() throws ServletException {
        dao = new CategoriaDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");


        switch (accion){
            case "listar":
                List<Categoria> categorias = dao.listarCategorias();
                req.setAttribute("listaCategorias", categorias);
                req.getRequestDispatcher("sistema/productos/ListaCategorias.jsp").forward(req, resp);
                break;
            case "agregar":
                cargarPagina(req,accion);
                req.getRequestDispatcher("sistema/productos/AgreEditCategorias.jsp").forward(req, resp);
                break;
            case "editar":

                int id = Integer.parseInt(req.getParameter("id"));
                Categoria c = dao.buscarCategoria(id);

                cargarPagina(req,accion);
                req.setAttribute("categoria", c);

                req.getRequestDispatcher("sistema/productos/AgreEditCategorias.jsp").forward(req, resp);

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
        String categoria, descripcion;
        int tipo,i,id;
        Categoria cat;
        switch (accion){
            case "agregar":
                categoria = req.getParameter("txtCategoria");
                descripcion = req.getParameter("txtDescripcion");

                cat = new Categoria();
                cat.setNombre(categoria);
                cat.setDesc(descripcion);
                cargarPagina(req,accion);
                i = dao.agregarCategoria(cat);
                if (i == 1){
                    req.setAttribute("mensaje", "<div class=\"alert alert-primary\" role=\"alert\">\n" +
                            "                        Categoría Registrada\n" +
                            "                    </div>");
                    req.getRequestDispatcher("sistema/productos/AgreEditCategorias.jsp").forward(req, resp);
                }else {
                    req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                            "                       Error al registrar categoría\n" +
                            "                    </div>");
                    req.getRequestDispatcher("sistema/productos/AgreEditCategorias.jsp").forward(req, resp);
                }
                break;
            case "editar":
                categoria = req.getParameter("txtCategoria");
                descripcion = req.getParameter("txtDescripcion");

                id = Integer.parseInt(req.getParameter("id"));
                cat = new Categoria();
                cat.setId(id);
                cat.setNombre(categoria);
                cat.setDesc(descripcion);
                i = dao.editarCategoria(cat);
                cargarPagina(req,accion);
                req.setAttribute("categoria", dao.buscarCategoria(id));

                if (i == 1){
                    req.setAttribute("mensaje", "<div class=\"alert alert-primary\" role=\"alert\">\n" +
                            "                        Categoría Editado\n" +
                            "                    </div>");
                    req.getRequestDispatcher("sistema/productos/AgreEditCategorias.jsp").forward(req, resp);
                }else {
                    req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                            "                       Error al editar categoría\n" +
                            "                    </div>");
                    req.getRequestDispatcher("sistema/productos/AgreEditCategorias.jsp").forward(req, resp);
                }

                break;
            case "eliminar":
                id = Integer.parseInt(req.getParameter("id"));
                i = dao.eliminarCategoria(id);

                resp.sendRedirect("categoria-controller?accion=listar");
                break;
            default:
                System.out.println("Error");
        }
    }
    public void cargarPagina(HttpServletRequest request,String accion) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("accion", accion);
        if (accion.equals("editar"))
            request.setAttribute("titulo", "Editar Categoría");
        else if (accion.equals("agregar")){
            request.setAttribute("titulo", "Agregar Categoría");
        }

    }
}
