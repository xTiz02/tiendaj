package com.example.tiendaj.controlador;

import com.example.tiendaj.modelo.dao.InventarioDao;
import com.example.tiendaj.modelo.dao.PedidoDao;
import com.example.tiendaj.modelo.dao.impl.InventarioDaoImpl;
import com.example.tiendaj.modelo.dao.impl.PedidoDaoImpl;
import com.example.tiendaj.modelo.entidades.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "compraController", value = "/compra-controller")
public class CompraController extends HttpServlet {
    private int idBoleta;
    private PedidoDao dao;
    private InventarioDao inventarioDao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new PedidoDaoImpl();
        inventarioDao = new InventarioDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            resp.sendRedirect("Login.jsp");
        }else {
            System.out.println("Usuario: "+usuario);
            List<Carrito> carrito = (List<Carrito>) session.getAttribute("listaCarrito");
            double total = carrito.stream().mapToDouble(Carrito::getSubTotal).sum();
            double totalIgv = Math.round(total*0.18 * 100.0) / 100.0;
            total = Math.round((total+totalIgv) * 100.0) / 100.0;
            System.out.println("Total: "+total);
            req.setAttribute("total", total);
            req.getRequestDispatcher("Pago.jsp").forward(req, resp);
        }
        //Cliente cliente = (Cliente) session.getAttribute("cliente");


        //idBoleta = idBoleta+1;
        /*Boleta boleta = new Boleta(idBoleta, LocalDate.now(), usuario, carrito, total);
        //dao.guardarCompra(boleta);

        //generarBoletaFile(boleta);
        //vaciar carrito
        req.setAttribute("total", boleta.getTotal());
        //req.setAttribute("nombre", boleta.getCliente().getNombre());
        req.setAttribute("email", boleta.getCliente().getEmail());
        req.setAttribute("carrito",boleta.getCarrito());
        req.getRequestDispatcher("Boleta.jsp").forward(req, resp);
*/


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Carrito> carrito = (List<Carrito>) session.getAttribute("listaCarrito");
        int i =0;
        for(Carrito c: carrito){
           if(!(inventarioDao.stockProducto(c.getProducto().getId()) >= c.getCantidad())){
               i = 1;
           }
        }
        if(i!=0){
            String compra = "{ \"estado\": "+false+"}";
            resp.setContentType("application/json");
            resp.getWriter().write(compra);
        }else{
            for (Carrito c: carrito){
                inventarioDao.disminuirStock(c.getProducto().getId(), c.getCantidad());
                if(inventarioDao.stockProducto(c.getProducto().getId()) == 0){
                    inventarioDao.actualizarEstado(c.getProducto().getId(), 0);
                }
            }
            StringBuilder jsonCompleto = new StringBuilder();
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Cliente cliente = usuario.getCliente();

            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonCompleto.append(line);
                }
                System.out.println("Json de la compra:"+jsonCompleto);
            } catch (IOException e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            try {
                JsonObject jsonObject = JsonParser.parseString(jsonCompleto.toString()).getAsJsonObject();

                String id_capture = jsonObject.getAsJsonArray("purchase_units")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("payments")
                        .getAsJsonArray("captures")
                        .get(0)
                        .getAsJsonObject()
                        .get("id").getAsString();

                String id_paypal_cliente = jsonObject.getAsJsonObject("payer").get("payer_id").getAsString();

                double total = jsonObject.getAsJsonArray("purchase_units")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("amount").get("value").getAsDouble();

                String fullName = jsonObject.getAsJsonArray("purchase_units")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("shipping")
                        .getAsJsonObject("name")
                        .get("full_name").getAsString();

                String calle = jsonObject.getAsJsonArray("purchase_units")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("shipping")
                        .getAsJsonObject("address")
                        .get("address_line_1").getAsString();

                String direccion = "";
                if(jsonCompleto.toString().contains("address_line_2")){
                    direccion= jsonObject.getAsJsonArray("purchase_units")
                            .get(0)
                            .getAsJsonObject()
                            .getAsJsonObject("shipping")
                            .getAsJsonObject("address")
                            .get("address_line_2").getAsString();
                }

                String ciudad = jsonObject.getAsJsonArray("purchase_units")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("shipping")
                        .getAsJsonObject("address")
                        .get("admin_area_2").getAsString();

                String provincia = jsonObject.getAsJsonArray("purchase_units")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("shipping")
                        .getAsJsonObject("address")
                        .get("admin_area_1").getAsString();

                String postal_code = jsonObject.getAsJsonArray("purchase_units")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("shipping")
                        .getAsJsonObject("address")
                        .get("postal_code").getAsString();
                String email = jsonObject.getAsJsonObject("payer").get("email_address").getAsString();


                String stringFecha = jsonObject.get("update_time").getAsString();
                System.out.println("Fecha: "+stringFecha);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                // Parsear la cadena en un objeto ZonedDateTime
                LocalDateTime fechaPedido = LocalDateTime.parse(stringFecha, formatter);
                //menos 5 horas
                fechaPedido = fechaPedido.minusHours(5);



                Pedido pedido = new Pedido(0,cliente,null, id_paypal_cliente, id_capture, fullName, calle, direccion, ciudad, postal_code, provincia, email, total, fechaPedido, "Pendiente",null);
                System.out.println("Pedido: "+pedido);

                carrito = (List<Carrito>) session.getAttribute("listaCarrito");
                System.out.println(pedido);
                i = dao.guardarPedido(pedido, carrito);

                if(i!=0){
                    pedido.setId(i);
                    dao.generarReporte(pedido, carrito);
                    session.setAttribute("listaCarrito", null);
                    String compra = "{ \"estado\": "+true+"}";
                    resp.setContentType("application/json");
                    resp.getWriter().write(compra);
                }else{
                    String compra = "{ \"estado\": "+false+"}";
                    resp.setContentType("application/json");
                    resp.getWriter().write(compra);
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }


    }
}

/*
            {
  "id": "9U581475NE912042W",
  "intent": "CAPTURE",
  "status": "COMPLETED",
  "purchase_units": [
    {
      "reference_id": "default",
      "amount": {
        "currency_code": "USD",
        "value": "19.60"
      },
      "payee": {
        "email_address": "sb-47md4r25772318@business.example.com",
        "merchant_id": "GQHDR9GBTLEWC"
      },
      "shipping": {
        "name": {
          "full_name": "John Doe"
        },
        "address": {
          "address_line_1": "Free Trade Zone",
          "admin_area_2": "Lima",
          "admin_area_1": "Lima",
          "postal_code": "07001",
          "country_code": "PE"
        }
      },
      "payments": {
        "captures": [
          {
            "id": "5EL57399TX1295726",
            "status": "COMPLETED",
            "amount": {
              "currency_code": "USD",
              "value": "19.60"
            },
            "final_capture": true,
            "seller_protection": {
              "status": "ELIGIBLE",
              "dispute_categories": [
                "ITEM_NOT_RECEIVED",
                "UNAUTHORIZED_TRANSACTION"
              ]
            },
            "create_time": "2023-09-18T18:04:20Z",
            "update_time": "2023-09-18T18:04:20Z"
          }
        ]
      }
    }
  ],
  "payer": {
    "name": {
      "given_name": "John",
      "surname": "Doe"
    },
    "email_address": "sb-t66qt25888371@personal.example.com",
    "payer_id": "D2PSLUUR4NEAQ",
    "address": {
      "country_code": "PE"
    }
  },
  "create_time": "2023-09-18T18:04:04Z",
  "update_time": "2023-09-18T18:04:20Z",
  "links": [
    {
      "href": "https://api.sandbox.paypal.com/v2/checkout/orders/9U581475NE912042W",
      "rel": "self",
      "method": "GET"
    }
  ]
}




Json de la compra targeta:
{
  "id": "5PW994054W7824310",
  "intent": "CAPTURE",
  "status": "COMPLETED",
  "purchase_units": [
    {
      "reference_id": "default",
      "amount": {
        "currency_code": "USD",
        "value": "19.60"
      },
      "payee": {
        "email_address": "sb-47md4r25772318@business.example.com",
        "merchant_id": "GQHDR9GBTLEWC"
      },
      "soft_descriptor": "PAYPAL *TEST STORE",
      "shipping": {
        "name": {
          "full_name": "Piero Rodriguez"
        },
        "address": {
          "address_line_1": "Calle Nevado",
          "address_line_2": "Cordillera Occidental MZ A5, Chorrillos",
          "admin_area_2": "Lima",
          "admin_area_1": "Lima",
          "postal_code": "15057",
          "country_code": "PE"
        }
      },
      "payments": {
        "captures": [
          {
            "id": "6GY43456Y78610906",
            "status": "COMPLETED",
            "amount": {
              "currency_code": "USD",
              "value": "19.60"
            },
            "final_capture": true,
            "seller_protection": {
              "status": "NOT_ELIGIBLE"
            },
            "create_time": "2023-09-20T01:59:36Z",
            "update_time": "2023-09-20T01:59:36Z"
          }
        ]
      }
    }
  ],
  "payer": {
    "name": {
      "given_name": "Piero",
      "surname": "Rodriguez"
    },
    "email_address": "piero323@outlook.com",
    "payer_id": "XFBL5Q99KRLHQ",
    "address": {
      "country_code": "PE"
    }
  },
  "create_time": "2023-09-20T01:29:03Z",
  "update_time": "2023-09-20T01:59:36Z",
  "links": [
    {
      "href": "https://api.sandbox.paypal.com/v2/checkout/orders/5PW994054W7824310",
      "rel": "self",
      "method": "GET"
    }
  ]
}
            * */
