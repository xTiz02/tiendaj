<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.tiendaj.modelo.dao.ProductoDao" %>
<%@ page import="com.example.tiendaj.modelo.dao.impl.ProductoDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.tiendaj.modelo.entidades.Categoria" %>
<%@ page import="com.example.tiendaj.modelo.entidades.Producto" %>
<%@ page import="com.example.tiendaj.modelo.dao.CategoriaDao" %>
<%@ page import="com.example.tiendaj.modelo.dao.impl.CategoriaDaoImpl" %>
<%@ page import="java.io.File" %>
<%@ page import="com.example.tiendaj.modelo.entidades.Carrito" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
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
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catalogo</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">


    <link rel="stylesheet" href="css/catalogo.css">
</head>
<body>

<!--Barra de navegacion-->
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
                    <li><a href="catalogo-controller?accion=index"></a></li>
                    <li><a href="catalogo-controller" id="selected">Productos</a>
                        <ul>
                            <li><a href="#">Teclados</a></li>
                            <li><a href="#">Accesorios</a></li>
                            <li><a href="#">Audifonos</a></li>
                            <li><a href="#">Mouse</a></li>
                        </ul>
                    </li>

                    <li><a href="">Nosotros</a></li>
                    <li><a href="Registro.jsp">Registrate</a></li>

                </ul>
            </nav>
        </div>

    </div>
</header>



<!--Catalogo de productos y paginacion-->

<div class="main-contenedor">
    <div class="filtro-contenedor">
        <form action="catalogo-controller" method="post">
            <div class="row mx-auto container">
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <div class="main-titulos">
                        <div>Filtrar productos:</div>
                    </div>
                    <p>Categorias</p>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="todos" name="categoria[]" >
                        <label class="form-check-label" >
                            Todos
                        </label>
                    </div>
                    <c:forEach items="${listaCategorias}" var="categoria" >

                    <div class="form-check">

                        <input class="form-check-input" type="checkbox" value="${categoria.id}" name="categoria[]">
                        <label class="form-check-label">
                            ${categoria.nombre}
                        </label>
                    </div>
                    </c:forEach>
                </div>
            </div>
            <div class="row mx-auto container mt-5" style="width: 80%; float:left; padding-bottom:20px;">
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <p>Precio</p>
                    <input type="range" class="form-range" name="precio" value="10000" min="1" max="10000" id="rangoInput" oninput="actualizarTexto()" step="1"> <br>
                    <span style="float: left;">1</span>
                    <span style="float: right;">10000</span><br><br>
                    Valor: <input type="text" id="numeroInput" oninput="actualizarRango()">
                </div>
            </div>

            <div class="form-group my-3 mx-3">
                <br><br>
                <input type="submit" name="search" value="search" class="btn btn-primary">

            </div>
        </form>
    </div>
    <div class="catalogo-productos">
        <div class="main-titulos">
            <div>Catalogo de productos:</div>
        </div>
        <div class="box-container" id="box-container">
            <c:forEach items="${listaProductos}" var="p">
            <div class="box">
                <div class="img-producto">
                    <a href="catalogo-controller?accion=ver&id=${p.id}"><img src="${p.imagenes[0]}" alt="nop"></a>
                </div>
                <div class="info">
                    <h3 class="title">${p.nombre}</h3>

                    <div class="subInfo">
                        <c:choose>
                            <c:when test="${p.descuento != 0}">
                                <strong class="price">S/${p.precio - (p.precio*p.descuento/100)}/<span>S/${p.precio}</span></strong>
                            </c:when>
                            <c:otherwise>
                                <strong class="price">S/${p.precio}</strong>
                            </c:otherwise>
                        </c:choose>
                        <div class="addToCar">
                            <input type="hidden" id="idpro" value="${p.id}">
                            <button type="button" class="compra" data-id="1"><i style="--i:1;" class="fas fa-shopping-cart"></i></button>
                        </div>

                    </div>
                </div>
            </div>
            </c:forEach>
        </div>
        <!-- Paginacion-->
        <!--<nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-id">
                    <a class="page-link" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li class="page-id"><a class="page-link" href="CatalogoProductos.php?nume=1">1</a></li>
                <li class="page-id"><a class="page-link" href="CatalogoProductos.php?nume=2">2</a></li>
                <li class="page-id"><a class="page-link" href="CatalogoProductos.php?nume=3">3</a></li>
                <li class="page-id">
                    <a class="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>-->
    </div>
</div>
<!--Footer-->
<hr style="margin: 50px 0;">
<footer>
    <div class="contenedor-footer">
        <div class="sobre-mi">
            <h2 class="titulos-footer">QUIENES SOMOS :</h2>
            <p>Somos una empresa dedicada a la venta de productos de tecnología, con el objetivo de satisfacer las necesidades de nuestros clientes, ofreciendo productos de calidad y con garantía.</p>
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
                    <span><i class="fa-solid fa-location-dot" style="color: white;"></i></span>
                    <p>Príncipe de Vergara 36, 5º derecha, 28001<br> Madrid, España</p>
                </li>

                <li>
                    <span><i class="fa-solid fa-phone" style="color: white;"></i></span>
                    <p>+51 912 880 509<br> +51 913 249 474<br>+51 934 707 678<br>+51 923 501 889<br>+51 934 269 714<br>+51 988 997 105</p>
                </li>

                <li>
                    <span><i class="fa-solid fa-envelope" style="color: white;"></i></span>
                    <p>W-SHOP@gmail.com</p>
                </li>
            </ul>
        </div>
    </div>
    <div class="copy">
        Copyright © 2023 Proyecto desarrollado por el grupo 4.
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script type="text/javascript" src="js/carrito.js"></script>

</body>

</html>
