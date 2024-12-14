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
        <h1 class="h3 mb-0 text-gray-800">Proveedores</h1>
        <a href="${pageContext.request.contextPath}/proveedor-controller?accion=agregar" class="btn btn-primary">Nuevo</a>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="table-responsive">
                <table class="table table-striped table-bordered" id="table">
                    <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>RUC</th>
                        <th>NOMBRE</th>
                        <th>TIPO</th>
                        <th>UBICACION</th>
                        <th>DISTRITO</th>
                        <th>TELEFONO</th>
                        <th>ACCIONES</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listaProveedores}" var="proveedor" >
                        <tr>
                            <td>${proveedor.id}</td>
                            <td>${proveedor.ruc}</td>
                            <td>${proveedor.nombre}</td>
                            <td>${proveedor.tipo.tipo}</td>
                            <td>${proveedor.ubicacion}</td>
                            <td>${proveedor.distrito}</td>
                            <td>${proveedor.telefono}</td>
                            <td>
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalProducto${proveedor.id}"><i class="fa fa-eye" aria-hidden="true"></i></button>

                                <!-- Ventana modal -->
                                <div class="modal fade" id="modalProducto${proveedor.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Detalle Proveedor</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <h3>Nombre: ${proveedor.nombre}</h3>
                                                <div class="modal-product">

                                                    <div class="modal-left">
                                                        <h6>*Dirección:</h6>
                                                        <p>${proveedor.direccion}</p>
                                                        <h6>*Contacto:</h6>
                                                        <p>${proveedor.contacto}</p>
                                                    </div>
                                                    <div class="modal-right">
                                                        <h6>*Correo:</h6>
                                                        <p>${proveedor.correo}</p>
                                                        <h6>*Tipo:</h6>
                                                        <p>${proveedor.tipo.tipo}</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <a href="${pageContext.request.contextPath}/proveedor-controller?accion=editar&id=${proveedor.id}" class="btn btn-success"><i class='fas fa-edit'></i></a>
                                <form action="${pageContext.request.contextPath}/proveedor-controller?accion=eliminar&id=${proveedor.id}" method="post" class="confirmar d-inline">
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

