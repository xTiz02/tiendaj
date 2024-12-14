<%@ page import="com.example.tiendaj.modelo.dao.RetiroDao" %>
<%@ page import="com.example.tiendaj.modelo.dao.impl.RetiroDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.tiendaj.modelo.entidades.almacen.RetiroProducto" %><%--
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
<%
    RetiroDao retiroDao = new RetiroDaoImpl();

%>

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
        <h1 class="h3 mb-0 text-gray-800">Pedidos</h1>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="table-responsive">
                <table class="table table-striped table-bordered" id="table">
                    <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>CLIENTE</th>
                        <th>TRANSACCIÓN</th>
                        <th>TOTAL</th>
                        <th>FECHA</th>
                        <th>ESTADO</th>
                        <th>ACCIONES</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listaPedidos}" var="pedido" >
                        <tr>
                            <td>${pedido.id}</td>
                            <td>${pedido.cliente.nombre}</td>
                            <td>${pedido.id_transaccion}</td>
                            <td>${pedido.total}</td>
                            <td>${pedido.fechaPedido}</td>
                            <td>${pedido.estado}</td>
                            <td>
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalProducto${pedido.id}"><i class="fa fa-eye" aria-hidden="true"></i></button>

                                <!-- Ventana modal -->
                                <div class="modal fade" id="modalProducto${pedido.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Detalle Cliente</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <h3>Nombre paypal: ${pedido.nombre_paypal_cliente}</h3>
                                                <div class="modal-product">

                                                    <div class="modal-left">
                                                        <h6>*Ciudad:</h6>
                                                        <p>${pedido.ciudad_paypal_cliente}</p>
                                                        <h6>*Dirección:</h6>
                                                        <p>${pedido.direccion_paypal_cliente}</p>
                                                        <h6>*Provincia:</h6>
                                                        <p>${pedido.provincia_paypal_cliente}</p>
                                                    </div>
                                                    <div class="modal-right">
                                                        <h6>*Calle:</h6>
                                                        <p>${pedido.calle_paypal_cliente}</p>
                                                        <h6>*Codigo postal:</h6>
                                                        <p>${pedido.codPostal_paypal_cliente}</p>
                                                        <h6>*Email paypal:</h6>
                                                        <p style="max-width: 300px;">${pedido.email_paypal_cliente}</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <a href="${pageContext.request.contextPath}/pedido-controller?accion=ver&id=${pedido.id}" class="btn btn-success"><i class="fa fa-list" aria-hidden="true"></i></a>
                                <!-- Modal para cambiar el estado-->
                                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modalProducto${pedido.id_transaccion}"><i class='fas fa-edit'></i></button>

                                <!-- Ventana modal -->
                                <div class="modal fade" id="modalProducto${pedido.id_transaccion}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabelB" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabelB">Cambiar pedido</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <h3>Tipo: Anular/Gestionar</h3>
                                                <div class="modal-product">
                                                    <div class="alert alert-danger" role=alert>
                                                        <p>Los productos seleccionados pasarán a estar inactivos.</p>
                                                        <p>Si no selecciona ningún producto se anulará el pedido</p>
                                                    </div>
                                                    <form action="${pageContext.request.contextPath}/pedido-controller?accion=cambiar&id=${pedido.id}" method="post">
                                                    <c:forEach items="${pedido.listaDetallePedido}" var="det">
                                                        <div class="form-group">
                                                            <c:if test="${fn:length(det.listaRetiroProducto) > 0}">
                                                                <label for="">Nombre: ${det.producto.nombre}:</label>
                                                                <br>
                                                                <c:forEach items="${det.listaRetiroProducto}" var="ret">
                                                                    <input type="checkbox" name="seleccionados" value="${ret.productoUnidad.codigo}"> ${ret.productoUnidad.codigo}
                                                                    <!-- Agrega más checkboxes según sea necesario -->
                                                                </c:forEach>
                                                            </c:if>
                                                        </div>
                                                   </c:forEach>
                                                        <div class="alert alert-primary" role=alert>
                                                            Nota:  Puede cambiar luego el estado de los productos.
                                                        </div>
                                                        <br>
                                                        <button type="submit" value="Cambiar" class="btn btn-danger">Cambiar</button>
                                                    </form>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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

