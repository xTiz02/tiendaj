<%--
  Created by IntelliJ IDEA.
  User: Piero
  Date: 27/09/2023
  Time: 0:56
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
    <style>
        /* Estilos personalizados */
        .modal-product {
            display: flex;
            justify-content: space-between;
            background-color: #e0fcdb; /* Color verde de fondo */
        }
        .modal-content {
            width: 100%;

        }
        .modal-left {
            width: 60%;
            padding: 15px;
        }

        .modal-right {
            width: 60%;
            padding: 15px;
        }
    </style>

</head>
<%@ include file="../header.jsp"%>
<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Content Row -->
    <div class="row">
        <div class="col-lg-6 m-auto">
            <div class="card-header bg-primary text-white">
                ${titulo}
            </div>
            <div class="card">
                <form action="${pageContext.request.contextPath}/retiro-controller?accion=${accion}" method="post" class="card-body p-2" accept-charset="UTF-8">
                    ${mensaje}


                    <c:if test="${accion == 'agregar'}">
                        <div class="form-group">

                            <label for="">PRODUCTO:</label>
                            <h6>${detallePedido.producto.nombre}</h6>
                            <label for="unidad">UNIDAD:</label>
                            <select id="unidad" name="unidad" class="form-control">
                                <c:forEach items="${listaUnidades}" var="unid">
                                    <option value="${unid.codigo}">${unid.codigo}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="alert alert-primary" role=alert>
                            Nota:  Verificar el estado actual del producto antes de realizar el retiro.
                        </div>


                        <input type="hidden" name="idDetalle" value="${detallePedido.id}">
                        <input type="submit" value="Guardar Unidad" class="btn btn-primary">
                        <a href="${pageContext.request.contextPath}/pedido-controller?accion=ver&id=${detallePedido.pedido.id}" class="btn btn-danger">Regresar</a>

                    </c:if>

                </form>
            </div>
        </div>
    </div>


</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->
<%@ include file="../footer.jsp"%>
</html>

