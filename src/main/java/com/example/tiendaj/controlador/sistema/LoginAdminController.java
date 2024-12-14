package com.example.tiendaj.controlador.sistema;

import com.example.tiendaj.modelo.dao.UsuarioDao;
import com.example.tiendaj.modelo.dao.impl.UsuarioDaoImpl;
import com.example.tiendaj.modelo.entidades.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "loginAdminController", value = "/loginAdmin-controller")
public class LoginAdminController extends HttpServlet {
    private UsuarioDao usuarioDao;

    @Override
    public void init() throws ServletException {

        usuarioDao = new UsuarioDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        switch (accion){
            case "cambiar":
                req.getRequestDispatcher("sistema/LoginAdmin.jsp").forward(req,resp);
                break;
            case "salir":
                HttpSession session = req.getSession();
                session.removeAttribute("admin");
                resp.sendRedirect("sistema/LoginAdmin.jsp");
                break;
            default:
                System.out.println("error logincontroller");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String accion = req.getParameter("accion");
        switch (accion){
            case "login"://cambiar esta parte
                String user = req.getParameter("txtUsuario");
                String pass = req.getParameter("txtPassword");
                Usuario usuario = usuarioDao.validarUsuario(user,pass,"admin");
                if(usuario!=null){
                    HttpSession session = req.getSession();
                    session.setAttribute("admin",usuario);
                    resp.sendRedirect("sistema/Admin.jsp");

                }else {
                    String mensaje = "<div class=\"alert alert-danger\" role=\"alert\">\n" +
                            "                       Credenciales incorrectas\n" +
                            "                    </div>";
                    req.setAttribute("mensaje",mensaje);
                    req.getRequestDispatcher("sistema/LoginAdmin.jsp").forward(req,resp);
                }
                break;
            default:
                System.out.println("error logincontroller");
        }
    }
}
