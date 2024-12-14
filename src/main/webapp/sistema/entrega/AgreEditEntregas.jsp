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

<%
    int i = 0;
%>
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
                <form action="${pageContext.request.contextPath}/entrega-controller?accion=${accion}" method="post" class="card-body p-2" accept-charset="UTF-8">
                    ${mensaje}
                    <!-- Lista de tipo proveedor-->
                    <div class="form-group">
                        <label for="proveedor">PROVEEDOR:</label>
                        <select id="proveedor" name="proveedor" required>
                            <c:forEach items="${listaProveedores}" var="prov" >
                                <c:choose>
                                    <c:when test="${prov.id==entrega.proveedor.id}">
                                        <option value="${prov.id}" selected>${prov.nombre}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${prov.id}">${prov.nombre}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                        <!------- AGREGAR-->

                    <c:if test="${accion == 'agregar'}" >
                    <div class="form-group">
                        <label for="productos">Productos Disponibles:</label>
                        <select id="productos">
                            <c:forEach var="inv" items="${listaInventario}">
                                <option value="${inv.producto.id}">${inv.producto.nombre}</option>
                            </c:forEach>
                        </select>
                    </div> <br>
                    <div class="form-group">
                        <label for="cantidad">Cantidad:</label>
                        <input type="number" id="cantidad" name="cantidad" min="1">
                    </div>
                    <div class="form-group">
                        <label for="precio">Precio:</label>
                        <input type="number" id="precio" min="0.01" step="0.01">
                        <button type="button" id="agregarProducto" class="btn btn-secondary">Agregar Producto</button>
                    </div>
                    <input type="hidden" id="productosSeleccionados" name="productosSeleccionados">
                    <div class="form-group">
                        <label for="">Productos seleccionados:</label>
                        <ul id="listaProductos" class="list-group-item-info"></ul>
                    </div>
                        <div class="alert alert-primary" role=alert>
                            Nota:  En caso de equivocación actualizar inmediatamente la entrega.
                        </div>
                        <input type="submit" value="Guardar Entrega" class="btn btn-primary">
                        <a href="${pageContext.request.contextPath}/entrega-controller?accion=listar" class="btn btn-danger">Regresar</a>

                    </c:if>

                        <!-------EDITAR -->
                    <c:if test="${accion == 'editar'}" >
                        <!-- Lista de tipo productos-->
                        <div class="form-group">
                            <label for="productos">PRODUCTOS:</label> <br>
                            <hr>
                            <c:forEach items="${entrega.detalleEntregaLista}" var="det" >
                                <%i++;%>
                                <select id="productos" name="producto<%=i%>"> <br>
                                    <c:forEach items="${listaInventario}" var="inv" >
                                        <c:choose>
                                            <c:when test="${inv.producto.id==det.producto.id}">
                                                <option value="${det.producto.id}" selected>${inv.producto.nombre}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${det.producto.id}">${inv.producto.nombre}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select><br><br>
                                <div class="form-group">
                                <label for="numero">CANTIDAD:</label>
                                <input type="tel" name="numero<%=i%>" id="numero" value="${det.cantidad}">
                                </div>
                                <div class="form-group">
                                    <label for="pago">PAGO:</label>
                                    <input type="text" name="pago<%=i%>" id="pago" value="${det.pago}">
                                </div>
                                <hr>
                                </c:forEach>
                        </div>
                        <div class="alert alert-primary" role=alert>
                            Nota:  En caso de equivocación actualizar inmediatamente la entrega.
                        </div>
                        <input type="hidden" name="id" value="${entrega.id}">
                        <input type="submit" value="Guardar Entrega" class="btn btn-primary">
                        <a href="${pageContext.request.contextPath}/entrega-controller?accion=ver&id=${entrega.id}" class="btn btn-danger">Regresar</a>

                    </c:if>
                        <br>

                    </form>
            </div>
        </div>
    </div>


</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->
<%@ include file="../footer.jsp"%>
<script>
    const agregarProductoBtn = document.getElementById('agregarProducto');
    const productosSelect = document.getElementById('productos');
    const cantidadInput = document.getElementById('cantidad');
    const precioInput = document.getElementById('precio');
    const productosSeleccionadosInput = document.getElementById('productosSeleccionados');
    const productosSeleccionadosDiv = document.getElementById('listaProductos');
    let listaProductos = [];

    agregarProductoBtn.addEventListener('click', function () {
        const productoSeleccionado = productosSelect.value;
        const cantidad = cantidadInput.value;
        let precio = precioInput.value;

        if (productoSeleccionado && cantidad) {
            // Si no se ingresó un precio, asigna 0 como valor predeterminado
            if (!precio) {
                precio = 0;
            }

            const producto = {
                id: productoSeleccionado,
                cantidad: cantidad,
                precio: precio
            };

            listaProductos.push(producto);

            const listItem = document.createElement('li');
            listItem.textContent = 'Producto: ' + producto.id + ', Cantidad: ' + producto.cantidad + ', Precio: $' + producto.precio;
            productosSeleccionadosDiv.appendChild(listItem);

            // Actualiza el campo oculto con la lista de productos
            productosSeleccionadosInput.value = listaProductos.map(function (p) {
                return p.id + ':' + p.cantidad + ':' + p.precio;
            }).join(';');

            // Limpia los campos
            productosSelect.selectedIndex = -1;
            cantidadInput.value = '';
            precioInput.value = '';
        }
    });
</script>
</html>

