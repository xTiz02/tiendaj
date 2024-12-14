package com.example.tiendaj.controlador.sistema;

import com.example.tiendaj.modelo.dao.PedidoDao;
import com.example.tiendaj.modelo.dao.UsuarioDao;
import com.example.tiendaj.modelo.dao.impl.PedidoDaoImpl;
import com.example.tiendaj.modelo.dao.impl.UsuarioDaoImpl;
import com.example.tiendaj.modelo.entidades.Pedido;
import com.example.tiendaj.modelo.entidades.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "/usuarioController", value = "/usuario-controller")
public class UsuarioController extends HttpServlet {
    private UsuarioDao uDao;
    private PedidoDao pDao;

    @Override
    public void init() throws ServletException {

        uDao = new UsuarioDaoImpl();
        pDao = new PedidoDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        int i;
        switch (accion) {
            case "listar":
                List<Usuario> lista = uDao.listarUsuarios();
                req.setAttribute("listaUsuarios", lista);
                req.getRequestDispatcher("sistema/clientes/ListaClientes.jsp").forward(req, resp);
                break;
            case "ver":
                int idUsuario = Integer.parseInt(req.getParameter("id"));
                Usuario u = uDao.buscarUsuario(idUsuario);
                List<Pedido> listaPedidos = pDao.listarPedidosPorCliente(u.getCliente().getId());
                req.setAttribute("listaPedidos", listaPedidos);
                req.getRequestDispatcher("/sistema/pedidos/ListaPedidos.jsp").forward(req,resp);
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
        int i;
        switch (accion){
            case "cambiar":
                int idUsuario = Integer.parseInt(req.getParameter("id"));
                Usuario u = uDao.buscarUsuario(idUsuario);
                if(u.getCliente().getEstado() == 1){
                    i = uDao.cambiarEstado(idUsuario, 0);
                }
                if (u.getCliente().getEstado() == 0){
                    i = uDao.cambiarEstado(idUsuario, 1);
                }
                resp.sendRedirect("usuario-controller?accion=listar");
                break;
        }

    }
}
