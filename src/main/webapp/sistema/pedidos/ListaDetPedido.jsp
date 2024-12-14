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

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Detalle del Pedido</h1>
        <a href="${pageContext.request.contextPath}/pedido-controller?accion=listar" class="btn btn-danger">Regresar</a>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="table-responsive">
                <table class="table table-striped table-bordered" id="table">
                    <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>CLIENTE</th>
                        <th>PRODUCTO</th>
                        <th>PRECIO(unidad)</th>
                        <th>CANTIDAD</th>
                        <th>ESTADO</th>
                        <th>ACCIONES</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pedido.listaDetallePedido}" var="det" >
                        <tr>
                            <td>${det.id}</td>
                            <td>${pedido.cliente.nombre}</td>
                            <td>${det.producto.nombre}</td>
                            <td>${det.precio}</td>
                            <td>${det.cantidad}</td>
                            <td>${det.estado}</td>
                            <td>
                                <!-- Poner if si el estado cambia-->
                                <c:if test="${det.estado == 'NG' && pedido.estado != 'Anulado'}">
                                    <a href="${pageContext.request.contextPath}/retiro-controller?accion=agregar&id=${det.id}" class="btn btn-circle"><i class="fa fa-plus" aria-hidden="true"></i></a>
                                </c:if>
                                <!-- Si el pedido no esta anulado se muestra-->
                                <c:if test="${pedido.estado != 'Anulado'}">
                                    <a href="${pageContext.request.contextPath}/retiro-controller?accion=ver&id=${det.id}" class="btn btn-primary"><i class="fa fa-expand" aria-hidden="true"></i></a>
                                </c:if>
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

