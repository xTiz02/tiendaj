<%@ page import="com.example.tiendaj.modelo.dao.EntregaDao" %>
<%@ page import="com.example.tiendaj.modelo.dao.impl.EntregaDaoImpl" %><%--
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
<%
    EntregaDao entregaDao = new EntregaDaoImpl();
%>
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
        <h1 class="h3 mb-0 text-gray-800">Detalle de la Entrega</h1>
        <!--<input type="text" name="buscar" placeholder="Buscar" class="in">-->

        <a href="${pageContext.request.contextPath}/entrega-controller?accion=listar" class="btn btn-danger">Regresar</a>

    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="table-responsive">
                <table class="table table-striped table-bordered" id="table">
                    <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>PROVEEDOR</th>
                        <th>PRODUCTO</th>
                        <th style="width: 70px;">CANTIDAD</th>
                        <th>PAGO</th>
                        <th style="width: 70px;">ESTADO</th>
                        <th>ACCIONES()</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${entrega.detalleEntregaLista}" var="detalle" >
                        <tr>
                            <td>${detalle.id}</td>
                            <td style="max-width: 80px;">${entrega.proveedor.nombre}</td>
                            <td style="max-width: 150px;">${detalle.producto.nombre}</td>
                            <td style="width: 70px;">${detalle.cantidad}</td>
                            <td style="max-width: 40px;">${detalle.pago}</td>
                            <td style="width: 70px;">${detalle.estado}</td>
                            <td>
                                <c:if test="${detalle.estado == 'NG'}">
                                <a href="${pageContext.request.contextPath}/productoUnidad-controller?accion=agregar&id=${detalle.id}" class="btn btn-circle"><i class="fa fa-plus" aria-hidden="true"></i></a>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/productoUnidad-controller?accion=listar&id=${detalle.id}" class="btn btn-primary"><i class="fa fa-eye" aria-hidden="true"></i></a>
                                <a href="${pageContext.request.contextPath}/entrega-controller?accion=editar&id=${entrega.id}" class="btn btn-success"><i class='fas fa-edit'></i></a>
                            </td>
                        </tr>
                    </c:forEach> <br>


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

