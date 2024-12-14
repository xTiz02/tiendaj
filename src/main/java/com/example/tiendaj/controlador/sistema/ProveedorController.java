package com.example.tiendaj.controlador.sistema;

import com.example.tiendaj.modelo.dao.ProveedorDao;
import com.example.tiendaj.modelo.dao.impl.ProveedorDaoImpl;
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


@WebServlet(name = "/proveedorController", value = "/proveedor-controller")
public class ProveedorController extends HttpServlet {
    private ProveedorDao dao;

    @Override
    public void init() throws ServletException {
        dao = new ProveedorDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");


        switch (accion){
            case "listar":
                List<Proveedor> proveedores = dao.listarProveedores();
                req.setAttribute("listaProveedores", proveedores);
                req.getRequestDispatcher("sistema/proveedores/ListaProveedores.jsp").forward(req, resp);
                break;
            case "agregar":
                cargarPagina(req,accion);
                req.getRequestDispatcher("sistema/proveedores/AgreEditProveedores.jsp").forward(req, resp);
                break;
            case "editar":

                int id = Integer.parseInt(req.getParameter("id"));
                Proveedor p = dao.buscarProveedor(id);

                cargarPagina(req,accion);
                req.setAttribute("proveedor", p);

                req.getRequestDispatcher("sistema/proveedores/AgreEditProveedores.jsp").forward(req, resp);

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
        String contacto, ubicacion, distrito, ruc, nombre, direccion, telefono, correo;
        int tipo,i,id;
        Proveedor prov;
        switch (accion){
            case "agregar":
                contacto = req.getParameter("txtContacto");
                ubicacion = req.getParameter("txtUbicacion");
                distrito = req.getParameter("txtDistrito");
                ruc = req.getParameter("txtRuc");
                nombre = req.getParameter("txtNombre");
                direccion = req.getParameter("txtDireccion");
                telefono = req.getParameter("txtTelefono");
                correo = req.getParameter("txtCorreo");
                tipo = Integer.parseInt(req.getParameter("tipo"));
                prov = new Proveedor();
                prov.setContacto(contacto);
                prov.setUbicacion(ubicacion);
                prov.setDistrito(distrito);
                prov.setRuc(ruc);
                prov.setNombre(nombre);
                prov.setDireccion(direccion);
                prov.setTelefono(telefono);
                prov.setCorreo(correo);
                prov.setTipo(dao.buscarTipo(tipo));
                prov.setEstado(1);
                cargarPagina(req,accion);
                i = dao.agregarProveedor(prov);
                if (i == 1){
                    req.setAttribute("mensaje", "<div class=\"alert alert-primary\" role=\"alert\">\n" +
                            "                        Proveedor Registrado\n" +
                            "                    </div>");
                    req.getRequestDispatcher("sistema/proveedores/AgreEditProveedores.jsp").forward(req, resp);
                }else {
                    req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                            "                       Error al registrar proveedor\n" +
                            "                    </div>");
                    req.getRequestDispatcher("sistema/proveedores/AgreEditProveedores.jsp").forward(req, resp);
                }
                break;
            case "editar":
                contacto = req.getParameter("txtContacto");
                ubicacion = req.getParameter("txtUbicacion");
                distrito = req.getParameter("txtDistrito");
                ruc = req.getParameter("txtRuc");
                nombre = req.getParameter("txtNombre");
                direccion = req.getParameter("txtDireccion");
                telefono = req.getParameter("txtTelefono");
                correo = req.getParameter("txtCorreo");

                tipo = Integer.parseInt(req.getParameter("tipo"));
                id = Integer.parseInt(req.getParameter("id"));
                prov = new Proveedor(id,dao.buscarTipo(tipo),nombre,ruc,ubicacion,distrito,direccion,telefono,correo,contacto,0);

                i = dao.editarProveedor(prov);
                cargarPagina(req,accion);
                req.setAttribute("proveedor", dao.buscarProveedor(id));

                if (i == 1){
                    req.setAttribute("mensaje", "<div class=\"alert alert-primary\" role=\"alert\">\n" +
                            "                        Proveedor Editado\n" +
                            "                    </div>");
                    req.getRequestDispatcher("sistema/proveedores/AgreEditProveedores.jsp").forward(req, resp);
                }else {
                    req.setAttribute("mensaje", "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                            "                       Error al editar proveedor\n" +
                            "                    </div>");
                    req.getRequestDispatcher("sistema/proveedores/AgreEditProveedores.jsp").forward(req, resp);
                }

                break;
            case "eliminar":
                id = Integer.parseInt(req.getParameter("id"));
                //Pi = dao.eliminarProveedor(id);
                Proveedor p = dao.buscarProveedor(id);
                if (p.getEstado() == 1) {
                    i = dao.cambiarEstado(id, 0);
                }
                if (p.getEstado() == 0) {
                    i = dao.cambiarEstado(id, 1);
                }

                resp.sendRedirect("proveedor-controller?accion=listar");
                break;
            default:
                System.out.println("Error");
        }
    }


    public void cargarPagina(HttpServletRequest request,String accion) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        List<TipoProveedor> tipos = dao.listarTipos();
        request.setAttribute("listaTipos", tipos);
        request.setAttribute("accion", accion);
        if (accion.equals("editar"))
            request.setAttribute("titulo", "Editar Proveedor");
        else if (accion.equals("agregar")){
            request.setAttribute("titulo", "Agregar Proveedor");
        }

    }
}
