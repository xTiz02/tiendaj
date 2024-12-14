<%--
  Created by IntelliJ IDEA.
  User: Piero
  Date: 27/09/2023
  Time: 0:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Punto de Venta</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/css/admin/sb-admin-2.min.css" rel="stylesheet">


</head>
<%@ include file="../header.jsp"%>



<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Categorías</h1>
        <a href="${pageContext.request.contextPath}/categoria-controller?accion=agregar" class="btn btn-primary">Nuevo</a>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="table-responsive">
                <table class="table table-striped table-bordered" id="table">
                    <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>CATEGORÍA</th>
                        <th>DESCRIPCIÓN</th>
                        <th>ACCIONES</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listaCategorias}" var="categoria" >
                        <tr>
                            <td>${categoria.id}</td>
                            <td>${categoria.nombre}</td>
                            <td>${categoria.desc}</td>
                            <td>
                                <!-- activar/des--> <a href="agregar_categoria.jsp?id=${categoria.id}" class="btn btn-primary"><i class='fas fa-audio-description'></i></a>
                                <a href="${pageContext.request.contextPath}/categoria-controller?accion=editar&id=${categoria.id}" class="btn btn-success"><i class='fas fa-edit'></i></a>
                                <form action="${pageContext.request.contextPath}/categoria-controller?accion=eliminar&id=${categoria.id}" method="post" class="confirmar d-inline">
                                    <button class="btn btn-danger" type="submit"><i class='fas fa-trash-alt'></i> </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>


                    </tbody>

                </table>
            </div>

        </div>
    </div>

</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->




<%@ include file="../footer.jsp"%>
</html>

