<%@ page import="com.example.tiendaj.modelo.entidades.Carrito" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.tiendaj.modelo.entidades.Cliente" %>
<%@ page import="com.example.tiendaj.modelo.entidades.Usuario" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--
  Created by IntelliJ IDEA.
  User: Piero
  Date: 30/08/2023
  Time: 9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%
    HttpSession session1 = request.getSession();
    Usuario usuario = (Usuario) session1.getAttribute("usuario");
    List<Carrito> listaCarrito = (List<Carrito>) session1.getAttribute("listaCarrito");
    double total = 0;
    int cantidad=0;
    if(usuario == null){
        response.sendRedirect("LoginAdmin.jsp");
    }
    if(listaCarrito == null){
        response.sendRedirect("Carrito.jsp");
    }else {
        cantidad = listaCarrito.size();
    }

%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrito</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <link rel="stylesheet" href="css/carrito.css">
</head>
<body>
<header>
    <div class="header_sup">
        <div class="logo">
            <img src="img/logoWeb2.png" alt="">
        </div>
        <form class="search" action="catalogo-controller" method="GET">
            <input type="text" name="buscar" placeholder="¿Qué desea buscar?">
            <input type="hidden" name="accion" value="buscar">
            <input type="submit" value="Buscar" style="width: 70px;">
        </form>
        <div class="iconos_sup">
            <div>
                <a href="Carrito.jsp"><i class="fa-solid fa-cart-shopping"></i><span id="num_cart" class="badge bg-secondary" style="font-size: 12px;"><%=cantidad%></span></a>
            </div>
            <div>
                <a href="Usuario.jsp"><i class="fa-solid fa-user"></i></a></div>
        </div>
    </div>

    <div class="container_menu">

        <div class="menu">
            <input type="checkbox" id="check_menu">
            <label for="check_menu" id="label_check">
                <i class="fas fa-bars icon_menu"></i>
            </label>
            <nav>
                <ul>
                    <li><a href="catalogo-controller?accion=index" id="selected"></a></li>
                    <li><a href="catalogo-controller">Productos</a>
                        <ul>
                            <li><a href="#">Teclados</a></li>
                            <li><a href="#">Accesorios</a></li>
                            <li><a href="#">Audifonos</a></li>
                            <li><a href="#">Mouse</a></li>
                        </ul>
                    </li>
                    <li><a href="Nosotros.jsp">Nosotros</a></li>
                    <li><a href="Registro.jsp">Registrate</a></li>


                </ul>
            </nav>
        </div>

    </div>

</header>
<main>
    <div class="container">
        <div class="col-6">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Subtotal</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <?php
                    <%
                        if(listaCarrito == null){
                    %>
                        <tr><td colspan="5" class="text-center"><b>No hay productos en el carrito</b></td></tr>
                    <%}else{
                        for(Carrito c:listaCarrito){
                            total = total + c.getSubTotal();
                    %>
                    <tr>
                        <td><%=c.getNombres()%></td>
                        <td>
                            <div id="" name="subtotal[]"><%=c.getSubTotal()%></div>
                        </td>
                    </tr>
                    <%}%>
                    <%
                        double totalIgv = Math.round(total*0.18 * 100.0) / 100.0;
                        double total2 = Math.round((total+totalIgv) * 100.0) / 100.0;

                    %>
                    <tr>
                        <td>
                            IGV
                        </td>
                        <td>
                            <div id="k" name=""><%=totalIgv%></div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <p class="h3 text-end" id="total">S/<%=total2%></p>
                        </td>
                    </tr>
                    </tbody>
                    <%}%>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-md-5 offseft-md-7 d-grid gap-2">
               <!-- <a href="compra-controller?total=" class="btn btn-primary btn-lg">Realizar pago</a>
           -->
                <div id="paypal-button-container"></div>
            </div>
        </div>
    </div>
    </div>

</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="https://www.paypal.com/sdk/js?client-id=Acpr5cAvL_lR9RtLIKoeX6-hDTgSuoOUG3nE7m9PMlC4otY-o8ro_SiTU5tmfllN8-M9YfMAOGimZ-1o&currency=USD"></script>

<script>
    paypal.Buttons({
        style: {
            color:'blue',
            shape:'pill',
            label:'pay'
        },
        createOrder: function(data, actions) {
            return actions.order.create({
                "purchase_units": [
                    {
                        "amount": {
                            "value": "${total}"
                        }
                    }
                ] //se puede añadir la lista de productos a comprar
            });
        },
        onApprove: function(data, actions) {
                actions.order.capture().then(function(orderData) {
                console.log('Capture result',orderData, JSON.stringify(orderData, null, 2));
                console.log(JSON.stringify(orderData, null, 2));
                //window.location.href = 'http://localhost:8080/tiendaJ_war_exploded/Completado.jsp';
                //alert('Transaction completed by ' + orderData.payer.name.given_name);
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify(orderData, null, 2),
                        url: 'compra-controller',
                        success: function (data) {
                            if (data.estado) {
                                window.location.href = 'http://localhost:8080/tiendaJ_war_exploded/Completado.jsp';
                            }else{
                                alert('No se guardo la compra')
                                console.log(data.toString()+"no se guardo la compra");
                            }
                        },
                        error: function (error) {
                            console.error(error);
                            console.log("error ajax pago");
                        }
                    });
            });
        },
        onCancel: function(data){
            alert('Pago cancelado');
            console.log(data);
        }
    }).render('#paypal-button-container');
</script>
</body>
</html>
