<%@ page import="com.example.tiendaj.modelo.entidades.Carrito" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.tiendaj.modelo.entidades.Usuario" %>
<%@ page import="com.example.tiendaj.modelo.dao.ProductoDao" %>
<%@ page import="com.example.tiendaj.modelo.dao.impl.ProductoDaoImpl" %>
<%@ page import="com.example.tiendaj.modelo.dao.InventarioDao" %>
<%@ page import="com.example.tiendaj.modelo.dao.impl.InventarioDaoImpl" %><%--
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
    List<Carrito> listaCarrito = (List<Carrito>) session1.getAttribute("listaCarrito");
    InventarioDao productoDao = new InventarioDaoImpl();
    int cantidad;
    double total=0;

    if(listaCarrito == null){
        cantidad = 0;
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
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
                <a href="Usuario.jsp"><i class="fa-solid fa-user"></i></a>
            </div>
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
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>Producto</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Subtotal</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <?php
                <%
                    if(listaCarrito == null){
                    %>
                        <tr>
                            <td colspan="5" class="text-center"><b>No hay productos en el carrito</b></td>
                        </tr>
                <% }else {
                        for(Carrito c: listaCarrito){
                            total = total + c.getSubTotal();
                %>
                <tr>
                    <td><%=c.getProducto().getNombre()%></td>
                    <td>S/<%=c.getPrecioCompra()%></td>
                    <td>
                        <input type="number" min="1" max="<%=productoDao.stockProducto(c.getProducto().getId())%>" step="1" value="<%=c.getCantidad()%>" size="5" class="cantidad"><!-- this.value,<?php echo $_id;?>-->
                    </td>
                    <td>
                        <input type="hidden" id="idcar" value="<%=c.getId()%>">
                        <div id="subtotal"><%=c.getSubTotal()%></div>
                    </td>
                    <td><a href="#" class="btn btn-warning btn-sm eliminar" data-bs-id="<%=c.getId()%>" data-bs-toggle="modal" data-bs-target="#eliminaModal">Eliminar</a></td>
                </tr>
                    <%}%>
                <%
                    if(total!=0){
                %>
                <tr>
                    <td colspan="3"></td>
                    <td colspan="2">
                        <p class="h3" id="total">S/<%=total%></p>
                    </td>
                </tr>
                    <%}%>
                </tbody>
                <%}%>
            </table>
        </div>
        <%
            if(listaCarrito!=null){
        %>
        <div class="row">
            <div class="col-md-5 offseft-md-7 d-grid gap-2">
                <a href="compra-controller" class="btn btn-primary btn-1g">Realizar pago</a>
            </div>
        </div>

        <%}%>
    </div>

</main>
<!-- Modal -->
<div class="modal fade" id="eliminaModal" tabindex="-1" aria-labelledby="eliminaModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="eliminaModalLabel">Alerta</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ¿Desea eliminar el producto de la lista?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                <button id="btn-elimina" type="button" class="btn btn-danger">Eliminar</button>
            </div>
        </div>
    </div>
</div>
<script>
    let eliminaModal = document.getElementById('eliminaModal')
    eliminaModal.addEventListener('show.bs.modal', function(event){
        let button = event.relatedTarget
        let id = button.getAttribute('data-bs-id')
        let buttonElimina = eliminaModal.querySelector('.modal-footer #btn-elimina')
        buttonElimina.value = id;

    })
    /*
    function eliminar(){
        let botonElimina = document.getElementById('btn-elimina')
        let id = botonElimina.value
        let url = 'Actualizar_carrito.php'
        let formData = new FormData()
        formData.append('action', 'eliminar')
        formData.append('id', id)
        fetch(url, {
            method: 'POST',
            body: formData,
            mode: 'cors'
        }).then (response => response. json())
            .then (data => {
                if(data.ok){
                    location.reload()
                }
            })

    }
    function actualizarCantidad(cantidad, id){
        let url = 'Actualizar_carrito.php'
        let formData = new FormData()
        formData.append('action', 'agregar')
        formData.append('id', id)
        formData.append('cantidad', cantidad)
        fetch(url, {
            method: 'POST',
            body: formData,
            mode: 'cors'
        }).then (response => response. json())
            .then (data => {
                if(data.ok){
                    let divsubtotal = document.getElementById('subtotal_' + id)
                    divsubtotal.innerHTML = data.sub

                    let total = 0.00;
                    let list = document.getElementsByName('subtotal[]')
                    for (let i = 0; i < list. length; i++) {
                        total += parseFloat(list[i].innerHTML.replace(/[$,]/g, ''))
                    }
                    total = new Intl.NumberFormat('en-Us', {
                        minimumFractionDigits: 2
                    }).format (total)
                    document.getElementById('total').innerHTML =total
                }
            })
    }*/
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous">
</script>
<script type="text/javascript" src="js/carrito.js"></script>
</body>
</html>
