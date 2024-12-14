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
                <form action="${pageContext.request.contextPath}/inventario-controller?accion=${accion}" method="post" class="card-body p-2" accept-charset="UTF-8" enctype="multipart/form-data">
                    ${mensaje}
                    <div class="form-group">
                        <label for="nombre">NOMBRE:</label>
                        <input type="text" placeholder="Ingrese nombre(O)" name="txtNombre" id="nombre" class="form-control" value="${inventario.producto.nombre}" required>
                    </div><br>
                    <!-- Lista de tipo proveedor-->
                    <div class="form-group">
                        <label for="proveedor">PROVEEDOR:</label>
                        <select id="proveedor" name="proveedor" required>
                            <c:forEach items="${listaProveedores}" var="prov" >
                                <c:choose>
                                    <c:when test="${prov.id==inventario.producto.proveedor.id}">
                                        <option value="${prov.id}" selected>${prov.nombre}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${prov.id}">${prov.nombre}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div><br>
                    <div class="form-group">
                        <label for="categoria">CATEGORÍA:</label>
                        <select id="categoria" name="categoria" required>
                            <c:forEach items="${listaCategorias}" var="cat" >
                                <c:choose>
                                    <c:when test="${cat.id==inventario.producto.categoria.id}">
                                        <option value="${cat.id}" selected>${cat.nombre}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${cat.id}">${cat.nombre}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div><br>
                    <div class="form-group">
                        <label for="caracteristicas">CARACTERÍSTICAS:</label>
                        <textarea placeholder="Ingrese Características" cols="20" rows="5" name="txtCaracteristicas" id="caracteristicas" class="form-control" >${inventario.producto.caracteristicas}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="descripcion">DESCRIPCIÓN:</label>
                        <textarea placeholder="Ingrese Descripción" cols="20" rows="5" name="txtDescripcion" id="descripcion" class="form-control"> ${inventario.producto.descripcion}</textarea>
                    </div>

                    <div class="form-group">
                        <label for="especificaciones">ESPECIFICACIONES:</label>
                        <textarea placeholder="Ingrese Especificaciones" cols="20" rows="5" name="txtEspecificaciones" id="especificaciones" class="form-control" >${inventario.producto.especificaciones} </textarea>
                    </div>
                    <div class="form-group">
                        <label for="precio">PRECIO:</label>
                        <input type="text" placeholder="Ingrese Precio(O)" name="txtPrecio" id="precio" class="form-control" value="${inventario.producto.precio}" required>
                    </div>
                    <div class="form-group">
                        <label for="descuento">DESCUENTO:</label>
                        <input type="text" placeholder="Ingrese Descuento(O)" name="txtDescuento" id="descuento" class="form-control" value="${inventario.producto.descuento}" required>
                    </div>
                        <!-- Agregar imagenes -->

                    <c:choose>
                        <c:when test="${accion=='agregar'}">
                            <div class="form-group">
                                <label for="imagenes">IMÁGENES(Obligatorio):</label>
                                <input type="file" name="imagenes" id="imagenes" class="form-control" value="${inventario.producto.imagen}" multiple required>
                            </div><br>
                        </c:when>
                    </c:choose>

                    <input type="hidden" name="id" value="${inventario.id}">

                    <input type="submit" value="Guardar Producto" class="btn btn-primary">
                    <a href="${pageContext.request.contextPath}/inventario-controller?accion=listar" class="btn btn-danger">Regresar</a>
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

