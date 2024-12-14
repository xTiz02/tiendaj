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
        <h1 class="h3 mb-0 text-gray-800">Clientes</h1>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="table-responsive">
                <table class="table table-striped table-bordered" id="table">
                    <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>DNI</th>
                        <th>NOMBRE</th>
                        <th>APELLIDO</th>
                        <th>EMAIL</th>
                        <th>TELÉFONO</th>
                        <th>ACCIONES</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listaUsuarios}" var="usuario" >
                        <tr>
                            <td>${usuario.cliente.id}</td>
                            <td>${usuario.cliente.dni}</td>
                            <td>${usuario.cliente.nombre}</td>
                            <td>${usuario.cliente.apellido}</td>
                            <td>${usuario.cliente.email}</td>
                            <td>${usuario.cliente.telefono}</td>
                            <td>
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalProducto${usuario.id}"><i class="fa fa-eye" aria-hidden="true"></i></button>

                                <!-- Ventana modal -->
                                <div class="modal fade" id="modalProducto${usuario.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Detalle Cliente</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <h3>Nombre: ${usuario.cliente.nombre}</h3>
                                                <div class="modal-product">

                                                    <div class="modal-left">
                                                        <h6>*Usuario:</h6>
                                                        <p>${usuario.usuario}</p>
                                                        <h6>*Fecha Creado:</h6>
                                                        <p>${usuario.cliente.fechaCreado}</p>
                                                    </div>
                                                    <div class="modal-right">
                                                        <h6>*Password:</h6>
                                                        <p>${usuario.password}</p>
                                                        <h6>*Estado:</h6>
                                                        <p>${usuario.cliente.estado}</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <a href="${pageContext.request.contextPath}/usuario-controller?accion=ver&id=${usuario.id}" class="btn btn-secondary"><i class="fa fa-shopping-cart" aria-hidden="true"></i></a>
                                <form action="${pageContext.request.contextPath}/usuario-controller?accion=cambiar&id=${usuario.id}" method="post" class="confirmar d-inline">
                                    <button class="btn btn-danger" type="submit"><i class="fa fa-check-square" aria-hidden="true"></i> </button>
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

