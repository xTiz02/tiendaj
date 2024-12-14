package com.example.tiendaj.controlador;

import com.example.tiendaj.modelo.entidades.Cliente;
import com.example.tiendaj.modelo.entidades.Usuario;
import com.example.tiendaj.modelo.dao.UsuarioDao;
import com.example.tiendaj.modelo.dao.impl.UsuarioDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "loginController", value = "/login-controller")
public class LoginController extends HttpServlet {

    private UsuarioDao dao;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");
        switch (accion){
            case "cerrar":
                HttpSession session = req.getSession();
                session.setAttribute("usuario",null);
                resp.sendRedirect("Usuario.jsp");
                break;
            default:
                System.out.println("error logincontroller");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dao = new UsuarioDaoImpl();
        String accion = req.getParameter("accion");
        String nombre,email,apellido,usuario,dni,telefono,pass;
        switch (accion){
            case "login":
                usuario = req.getParameter("txtUsuario");
                pass = req.getParameter("txtPassword");
                Usuario user = dao.validarUsuario(usuario,pass,"cliente");
                if(user!=null){
                    HttpSession session = req.getSession();
                    session.setAttribute("usuario",user);
                    resp.sendRedirect("Carrito.jsp");
                }else {
                    String mensaje = "Credenciales incorrectas.";
                    req.setAttribute("mensaje",mensaje);
                    req.getRequestDispatcher("Login.jsp").forward(req,resp);
                }
                break;
            case "registro":

                nombre = req.getParameter("txtNombres");
                email = req.getParameter("txtEmail");
                apellido = req.getParameter("txtApellidos");
                dni = req.getParameter("txtDni");
                usuario = req.getParameter("txtUsuario");
                telefono = req.getParameter("txtTelefono");
                pass = req.getParameter("txtPassword");
                Cliente cliente = new Cliente(dni,nombre,apellido,email,telefono,1, LocalDateTime.now());
                byte[] passBytes = pass.getBytes();
                user = new Usuario(0,cliente,usuario,passBytes,"cliente");
                System.out.println("Usuraio despues del registro"+user);
                int i = dao.crearUsuario(user);
                if (i==1){
                    String mensaje = "Usuario creado exitosamente!";
                    req.setAttribute("mensaje",mensaje);
                    req.getRequestDispatcher("Registro.jsp").forward(req,resp);
                    break;
                }else{
                    String mensaje = "Algo salio mal!";
                    req.setAttribute("mensaje",mensaje);
                    req.getRequestDispatcher("Registro.jsp").forward(req,resp);
                    break;
                }


            default:
                System.out.println("error logincontroller");
        }

    }
}
