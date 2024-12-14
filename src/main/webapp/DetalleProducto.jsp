<%@ page import="java.io.File" %>
<%@ page import="com.example.tiendaj.modelo.dao.ProductoDao" %>
<%@ page import="com.example.tiendaj.modelo.dao.impl.ProductoDaoImpl" %>
<%@ page import="com.example.tiendaj.modelo.entidades.Producto" %>
<%@ page import="com.example.tiendaj.modelo.entidades.Carrito" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%
    int cantidad;
    HttpSession session1 = request.getSession();
    List<Carrito> carrito = (List<Carrito>) session1.getAttribute("listaCarrito");
    if(carrito == null){
        cantidad = 0;
    }else {
        cantidad = carrito.size();
    }
%>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Nintendo Switch</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <link rel="stylesheet" href="css/detalleProducto.css">


</head>
<body class="body">

<!--BARRA DE NAVEGACION-->
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


<!--section------------------------->

<div class="total_producto"> <!--INCLUYE A CARRUSEL Y PRODUCTO-->

    <!---------------------------------------------------------------->
    <div class = "product-div-left">
        <div class = "img-container">
            <img src = "${producto.imagenes[0]}" alt = "watch">
        </div>
        <div class = "hover-container">
            <c:forEach items="${producto.imagenes}" var="p">
            <div>
                <img src ="${p}">
            </div>
            </c:forEach>
        </div>

    </div>

    <!------------------------------------------------------------------>
    <div class="total_producto__producto"> <!--INLCLYE TITULO Y BOX 1-->

        <div class="titulo">
            <h1>${producto.nombre}</h1>
        </div>

        <div class="BOX1"> <!-- INCLUYE A BOX 2 Y BOX 3 (ES PRECIO Y CANTIDAD)-->

            <div class="BOX2"> <!--BOX 2 INCLUYE A CARACTERISTICAS_DES Y OPCIONES_COMPRA-->
                <div class="caracteristicas_des">

                        <p><b>Características destacadas</b></p>
                        <ul>
                            <c:forEach items="${caracteristicas}" var="c" >
                            <li>${c}</li>
                            </c:forEach>
                        </ul>
                    <a href="#especificaciones">Ver más sobre el producto</a>
                </div>

                <div class="opciones_compra">
                    <div class="opciones_compra__1">
                        <div><img src="img/envio.JPG"></div>
                        <div class="opciones_compra__1__contenido">
                            <p class="texto"><b>Despacho a domicilio</b></p>
                            <a href="">Revisar Disponibilidad</a>
                        </div>
                    </div>

                    <div class="opciones_compra__2">
                        <div><img src="img/recojo.JPG"></div>
                        <div class="opciones_compra__1__contenido">
                            <p class="texto"><b>Despacho a domicilio</b></p>
                            <a href="">Selecciona una tienda</a>
                        </div>
                    </div>
                </div>

            </div>

            <div class="BOX3">
                <c:choose>
                    <c:when test="${producto.descuento != 0}">
                        <p style="
                        font-size: 15px;
                        color: #929292;
                        text-decoration: line-through;"><span>$ ${producto.precio}</span></p>
                        <h2 class="BOX3_precio">
                            ${producto.precio- (producto.precio*producto.descuento/100)}<br>
                            <small class="text-success">${producto.descuento}% descuento</small>
                        </h2>
                    </c:when>
                    <c:otherwise>
                        <p class="BOX3_precio">${producto.precio}</p>
                    </c:otherwise>
                </c:choose>

                <input type="hidden" id="idpro" value="${producto.id}">
                <button type="button" class="compra">Agregar al carrito</button>
            </div>

        </div>

    </div>

</div>

<div class="especificaciones_publicidad">
    <div id="especificaciones">
        <h4>DESCRIPCIÓN</h4>

        <ul>
        <c:forEach items="${descripcion}" var="d">
            <li>${d}</li>
        </c:forEach>
        </ul>
        <div class="caracteristicas">
            <h4>Especificaciones Tecnicas</h4>
            <table>
                <c:forEach items="${especificaciones}" var="e">
                <tr><td>${e[0]}<td>${e[1]}</tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <div class="publicidad">
        <img class="publi_1" src="https://cdn.domestika.org/c_limit,dpr_auto,f_auto,q_auto,w_820/v1582118706/content-items/003/753/080/1a0fd052510583.5aa4ae5695b28-original.png?1582118706" >
        <img class="publi_2" src="https://www.lg.com/es/img/promos%20hero/lg_bienvenida_lg.com_mv_768x1200.jpg">
    </div>
</div>


<!---FOOTER------------------------------------->
<footer>
    <div class="contenedor">
        <div class="sobre-mi">
            <h2 class="titulos-footer">QUIENES SOMOS :</h2>
            <p>SOMOS UNA EMPRESA QUE BUSCA MEJORAR Y BRINDAR UN GRAN SERVICIO A TODOS NUESTROS CLIENTES EN LO QUE SON COMPRAS DE COMPUTADORAS , MOUSE , TECLADO , ETC ; SIEMPRE BRINDANDO UNA GRAN EXPERIENCIA PARA CADA UNO DE ELLOS </p>
            <ul class="redes-sociales">
                <li><a href="#"><i class="fa-brands fa-facebook"></i></a></li>
                <li><a href="#"><i class="fa-brands fa-youtube"></i></a></li>
                <li><a href="#"><i class="fa-brands fa-instagram"></i></a></li>
                <li><a href="#"><i class="fa-brands fa-whatsapp"></i></a></li>
                <li><a href="#"><i class="fa-brands fa-twitter"></i></a></li>
            </ul>
        </div>

        <div class="navegacion">
            <h2 class="titulos-footer">MENU DE NAVEGACIÓN : </h2>
            <ul>
                <li><a href="#">Inicio</a></li>
                <li><a href="#">Productos</a></li>
                <li><a href="#">Recomendaciones</a></li>
                <li><a href="#">Contactos</a></li>
            </ul>
        </div>

        <div class="contactanos">
            <h2 class="titulos-footer">CONTÁCTANOS :</h2>
            <ul class="informacion-contacto">
                <li>
                    <span><i class="fa-solid fa-location-dot"></i></span>
                    <p>Príncipe de Vergara 36, 5º derecha, 28001<br> Madrid, España</p>
                </li>

                <li>
                    <span><i class="fa-solid fa-phone"></i></span>
                    <p>+51 912 880 509<br> +51 913 249 474<br>+51 934 707 678<br>+51 923 501 889<br>+51 934 269 714<br>+51 988 997 105</p>
                </li>

                <li>
                    <span><i class="fa-solid fa-envelope"></i></span>
                    <p>W-SHOP@gmail.com</p>
                </li>
            </ul>
        </div>
    </div>
    <div class="copy">
        Copyright © 2023 Proyecto desarrollado por el grupo 4
    </div>
</footer>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
<script type="text/javascript" src="js/detalle.js"></script>
</body>
</html>
