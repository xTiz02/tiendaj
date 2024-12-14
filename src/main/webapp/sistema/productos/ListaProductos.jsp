<%--
  Created by IntelliJ IDEA.
  User: Piero
  Date: 27/09/2023
  Time: 0:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        <h1 class="h3 mb-0 text-gray-800">Productos</h1>
        <a href="${pageContext.request.contextPath}/inventario-controller?accion=agregar" class="btn btn-primary">Nuevo</a>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="table-responsive">
                <table class="table table-striped table-bordered" id="table">
                    <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>NOMBRE</th>
                        <th>TIPO</th>
                        <th>STOCK</th>
                        <th>PRECIO($)</th>
                        <th>DESC(%)</th>
                        <th>ACTIVO</th>
                        <th>ACCIONES()</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listaInventario}" var="inventario" >
                        <tr>
                            <td>${inventario.id}</td>
                            <td style="max-width: 200px;">${inventario.producto.nombre}</td>
                            <td>${inventario.producto.categoria.nombre}</td>
                            <td>${inventario.stock}</td>
                            <td>${inventario.producto.precio}</td>
                            <td>${inventario.producto.descuento}</td>
                            <td>${inventario.activo}</td>
                            <td>
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalProducto${inventario.id}"><i class="fa fa-eye" aria-hidden="true"></i></button>

                                <!-- Ventana modal -->
                                <div class="modal fade" id="modalProducto${inventario.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Detalles del Producto</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <h3>${inventario.producto.nombre}</h3>
                                                <div class="modal-product">

                                                    <div class="modal-left">
                                                        <!-- Detalles del producto en la parte izquierda -->
                                                        <h6>*Proveedor:</h6>
                                                        <p>${inventario.producto.proveedor.nombre}</p>
                                                        <h6>*Stock:</h6>
                                                        <p>${inventario.stock}</p>
                                                        <h6>*Características:</h6>
                                                        <ul>
                                                            <c:forEach items="${fn:split(inventario.producto.caracteristicas, ';')}" var="caract">
                                                                <li>${caract}</li>
                                                            </c:forEach>
                                                        </ul>
                                                        <h6>*Precio: </h6><p>$${inventario.producto.precio}</p>
                                                        <h6>*Descuento: </h6><p>${inventario.producto.descuento}%</p>
                                                        <h6>*Especificaciones:</h6>
                                                        <ul>
                                                            <c:forEach items="${fn:split(inventario.producto.especificaciones, ';')}" var="espe">
                                                                <li>${espe}</li>
                                                            </c:forEach>
                                                        </ul>
                                                    </div>
                                                    <div class="modal-right">
                                                        <h6>*Categoría:</h6>
                                                        <p>${inventario.producto.categoria.nombre}</p>
                                                        <h6>*Modificado:</h6>
                                                        <c:choose>
                                                            <c:when test="${inventario.fechaModificado==null}">
                                                                <p>No modificado</p>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <p>${inventario.fechaModificado}</p>
                                                            </c:otherwise>
                                                        </c:choose>

                                                        <!-- Detalles del producto en la parte derecha -->
                                                        <h6>*Descripción:</h6>
                                                        <ul>
                                                            <c:forEach items="${fn:split(inventario.producto.descripcion, ';')}" var="caract">
                                                                <li>${caract}</li>
                                                            </c:forEach>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                                <a href="${pageContext.request.contextPath}/inventario-controller?accion=imagenes&id=${inventario.id}" class="btn btn-primary">Ver Imágenes</a>
                                                <a href="${pageContext.request.contextPath}/productoUnidad-controller?accion=listar&idProducto=${inventario.producto.id}" class="btn btn-primary">Ver Unidades</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <form action="${pageContext.request.contextPath}/inventario-controller?accion=activar&valor=${inventario.activo}&id=${inventario.id}" method="post" class="confirmar d-inline">
                                    <button class="btn btn-danger" type="submit"><i class="fa fa-check-square" aria-hidden="true"></i> </button>
                                </form>
                                <a href="${pageContext.request.contextPath}/inventario-controller?accion=editar&id=${inventario.id}" class="btn btn-success"><i class='fas fa-edit'></i></a>

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

