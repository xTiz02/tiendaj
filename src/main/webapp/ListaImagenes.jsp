<%--
  Created by IntelliJ IDEA.
  User: Piero
  Date: 01/10/2023
  Time: 0:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%

    ServletContext context = request.getServletContext();
    String ruta = context.getRealPath("/");
%>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Punto de Venta</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/css/admin/sb-admin-2.min.css" rel="stylesheet">


</head>
<%@ include file="sistema/header.jsp"%>

<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- bootstrap image gallery 1 -->


        <div class="jumbotron">
            <h1 class="text-center">Galería</h1>
            <p class="text-center">Imágenes del producto: ${producto.nombre}</p>
        </div>

        <div class="row">
            <div class="col-sm-6 col-md-4 col-lg-3">
                <figure>
                    <img src = "${producto.imagenes[0]}" class="img-thumbnail grayscale">
                    <!--<figcaption><?php echo $nombrePrincipal?></figcaption>-->

                </figure>
                <button><a href="">Eliminar</a></button>
            </div>

            <c:forEach items="${producto.imagenes}" var="p" begin="1">
                <div class="col-sm-6 col-md-4 col-lg-3">
                    <figure>
                        <img src="${p}" class="img-thumbnail grayscale">
                        <!--<figcaption><?php echo $nombreAll?></figcaption>-->
                    </figure>
                    <button><a href="">Cambiar principal</a></button>
                    <button><a href="">Eliminar</a></button>
                </div>
            </c:forEach>
        </div>
    <a href="${pageContext.request.contextPath}/inventario-controller?accion=listar" class="btn btn-danger">Regresar</a>


</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->




<%@ include file="sistema/footer.jsp"%>
</html>
