<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.tiendaj.modelo.entidades.Carrito" %>
<%@ page import="com.example.tiendaj.modelo.dao.ProductoDao" %>
<%@ page import="com.example.tiendaj.modelo.dao.impl.ProductoDaoImpl" %>
<%@ page import="com.example.tiendaj.modelo.entidades.Producto" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<%
    //cambiar esta parte
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
    <title>Home</title>
    <link rel="stylesheet" href="css/homeStyle.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <style>
        .search-container {
            position: relative;
            display: inline-block;
        }
        .search-input {
            width: 300px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .search-results {
            position: absolute;
            top: 100%;
            left: 0;
            right: 0;
            background-color: #fff;
            border: 1px solid #ccc;
            border-top: none;
            border-radius: 0 0 5px 5px;
            max-height: 150px;
            overflow-y: auto;
        /*display: none;*/
        }
        .search-item {
            padding: 10px;
            border-bottom: 1px solid #ccc;
            cursor: pointer;
        }
        .search-item:last-child {
            border-bottom: none;
        }
    </style>

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

<!--Carrusel-->
<div class="cont-carrusel">
    <div class="cont-targeta" id="imagen01">
        <div class="targeta">
            <img src="https://http2.mlstatic.com/D_NQ_969066-MLA54968344071_042023-OO.webphttps://i.linio.com/cms/7fd9a2d6-e085-11ed-a095-526beac7d230.webp" alt="">
        </div>
        <div class="flechas">
            <a href="#imagen03">
                <i class="fas fa-chevron-left"></i>
            </a>
            <a href="#imagen02">
                <i class="fas fa-chevron-right"></i>
            </a>
        </div>
    </div>
    <div class="cont-targeta" id="imagen02">
        <div class="targeta">
            <img src="https://i.linio.com/cms/7fd9a2d6-e085-11ed-a095-526beac7d230.webp" alt="">
        </div>
        <div class="flechas">
            <a href="#imagen01">
                <i class="fas fa-chevron-left"></i>
            </a>
            <a href="#imagen03">
                <i class="fas fa-chevron-right"></i>
            </a>
        </div>
    </div>
    <div class="cont-targeta" id="imagen03">
        <div class="targeta">
            <img src="https://juntozstgsrvproduction.blob.core.windows.net/cms-images/1940x490_hs_audio_309606.webp" alt="">
        </div>
        <div class="flechas">
            <a href="#imagen02">
                <i class="fas fa-chevron-left"></i>
            </a>
            <a href="#imagen01">
                <i class="fas fa-chevron-right"></i>
            </a>
        </div>
    </div>
</div>

<!--Titulo ¡Descuentos-->
<div class="desc-content">
    <div>¡Descuentos exclusivos de Verano!</div>
    <div><a href=""><button class="desc-button">Ir a todo</button></a></div>
</div>

<!--Productos-->
<div class="box-container">
    <c:forEach items="${listaProductos}" var="p">


    <div class="box">

        <div class="img-producto">
            <a href="catalogo-controller?accion=ver&id=${p.id}"><img src="${p.imagenes[0]}" alt="nop"></a>
        </div>
        <div class="info">
            <h3 class="title"> ${p.nombre}</h3>
            <div class="subInfo">
                <strong class="price">S/${p.precio-(p.descuento*p.precio/100)}/-<span>S/${p.precio}/-</span> </strong>
                <div class="addToCar">
                    <button class="compra" data-id="1"><i href="Carrito.jsp" style="--i:1;" class="fas fa-shopping-cart"></i></button>
                </div>
            </div>
        </div>
    </div>
    </c:forEach>
</div>

<!--Footer-->
<hr style="margin-bottom: 50px;">
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
    /*
    $(document).ready(function() {
        $("#search-input").on("input", function() {
            var searchTerm = $(this).val();
            var url = "catalogo-controller"; // Cambia la URL al controlador correspondiente
            console.log("Buscando productos que contengan: " + searchTerm);
            $.ajax({
                type: 'GET', // Utiliza GET para buscar productos
                url: url,
                data: {
                    nombre: searchTerm,
                    accion: 'buscar'
                },
                success: function(data) {
                    var results = $("#search-results");
                    results.empty();
                    console.log(data);

                    // Itera sobre los productos y muestra los resultados en la sublista
                    $.each(data, function(index, producto) {
                        console.log(producto);
                        var id = producto.id;
                        var item = $('<div class="search-item"> <a href="catalogo-controller?accion=ver&id='+id+'">' + producto.nombre + '</a></div>');
                        results.append(item);
                    });
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("Error en la búsqueda de productos");
                }
            });
        });
    });*/
</script>
</body>
</html>
