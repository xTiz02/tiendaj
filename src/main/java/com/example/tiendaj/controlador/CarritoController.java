package com.example.tiendaj.controlador;

import com.example.tiendaj.modelo.entidades.Carrito;
import com.example.tiendaj.modelo.entidades.Producto;
import com.example.tiendaj.modelo.dao.ProductoDao;
import com.example.tiendaj.modelo.dao.impl.ProductoDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "carritoController", value = "/carrito-controller")
public class CarritoController extends HttpServlet {

    private ProductoDao dao;
    private int idCarrito;
    private List<Producto> listaProductos;




    @Override
    public void init() throws ServletException {
        super.init();
        dao = new ProductoDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String accion =  req.getParameter("accion")!=null ? req.getParameter("accion") : req.getAttribute("accion").toString();
        String json;
        boolean estado = true;
        List<Carrito> carrito= (List<Carrito>)session.getAttribute("listaCarrito");

        switch (accion){
            case "agregar":
                int id = (Integer) req.getAttribute("id");
                listaProductos = (List<Producto>) req.getAttribute("listaProductos");
                if (carrito!=null) {
                    for(Carrito c: carrito){//busca si ya esta en el carrito
                        if(c.getProducto().getId() == id){
                            int cantidad = c.getCantidad();
                            cantidad++;

                            c.setCantidad(cantidad);

                            c.setSubTotal(Math.round(c.getPrecioCompra()*cantidad * 100.0) / 100.0);
                            estado = false;
                            break;
                        }
                    }
                }else{
                    carrito = new ArrayList<>();
                }

                if(estado){//si no esta en el carrito
                    double precio;

                    Producto prod = dao.buscarProductoPorId(listaProductos,id);
                    idCarrito = idCarrito +1;
                    if(prod.getDescuento()>0){
                        precio = prod.getPrecio() - (prod.getPrecio()* prod.getDescuento()/100);
                    }else {
                        precio = prod.getPrecio();
                    }
                    carrito.add(new Carrito(idCarrito,prod,prod.getNombre(),precio,1,precio));
                }

                session.setAttribute("listaCarrito", carrito);//guarda el carrito en la sesion
                json = "{ \"numero\": "+carrito.size()+"}";
                resp.setContentType("application/json"); // Cambiar el tipo de contenido a JSON
                resp.getWriter().write(json);
                break;
            case "eliminar":
                int idc = Integer.parseInt(req.getParameter("idcar"));
                //System.out.println(idc);
                for(Carrito c : carrito){
                    //System.out.println(c.getId()+","+c.getCantidad());
                    if(c.getId()==idc) {
                        carrito.remove(c);
                        if(carrito.isEmpty()){
                            carrito = null;
                        }
                        //si llega a cero elementos queda un array vacio y se gurada como nulo
                        session.setAttribute("listaCarrito", carrito);
                        break;
                    }
                }
                json = "{ \"ok\": "+true+"}";
                resp.setContentType("application/json"); // Cambiar el tipo de contenido a JSON
                resp.getWriter().write(json);
                break;
            case "actualizar":
                int idcar = Integer.parseInt(req.getParameter("idcar"));
                int cantidad = Integer.parseInt(req.getParameter("cantidad"));
                double subtotal = 0;
                for(Carrito c:carrito){
                    if(c.getId()==idcar){
                        c.setCantidad(cantidad);
                        c.setSubTotal(Math.round(c.getPrecioCompra()*cantidad * 100.0) / 100.0);
                        subtotal = c.getSubTotal();
                    }
                }
                session.setAttribute("listaCarrito",carrito);
                double total=0;
                for (Carrito c : carrito){
                    total= total+c.getSubTotal();
                }

                json = "{ \"subtotal\": "+subtotal+", \"totalc\": "+total+"}";
                resp.setContentType("application/json"); // Cambiar el tipo de contenido a JSON
                resp.getWriter().write(json);
                break;
            default:
                System.out.println("error controlador");
        }


    }

    public void destroy() {
    }
}
