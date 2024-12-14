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
                <form action="${pageContext.request.contextPath}/proveedor-controller?accion=${accion}" method="post" class="card-body p-2" accept-charset="UTF-8">
                    ${mensaje}
                    <div class="form-group">
                        <label for="nombre">NOMBRE</label>
                        <input type="text" placeholder="Ingrese nombre(O)" name="txtNombre" id="nombre" class="form-control" value="${proveedor.nombre}" required>
                    </div>
                    <div class="form-group">
                        <label for="ruc">Proveedor</label>
                        <input type="text" placeholder="Ingrese ruc(O)" name="txtRuc" id="ruc" class="form-control" value="${proveedor.ruc}" required>
                    </div>
                        <!-- Lista de tipo proveedor-->
                    <div class="form-group">
                        <label for="tipos">TIPO DE PROVEEDOR:</label>
                        <select id="tipos" name="tipo">
                        <c:forEach items="${listaTipos}" var="tipo" >
                            <c:choose>
                                <c:when test="${tipo.id==proveedor.tipo.id}">
                                    <option value="${tipo.id}" selected>${tipo.tipo}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${tipo.id}">${tipo.tipo}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="ubicacion">UBICACION</label>
                        <input type="text" placeholder="Ingrese Ubicacion" name="txtUbicacion" id="ubicacion" class="form-control" value="${proveedor.ubicacion}" required>
                    </div>
                    <div class="form-group">
                        <label for="distrito">DISTRITO</label>
                        <input type="text" placeholder="Ingrese Distrito" name="txtDistrito" id="distrito" class="form-control" value="${proveedor.distrito}" required>
                    </div>
                    <div class="form-group">
                        <label for="direccion">DIRECCIÓN</label>
                        <input type="text" placeholder="Ingrese Direccion(O)" name="txtDireccion" id="direccion" class="form-control" value="${proveedor.direccion}" required>
                    </div>
                    <div class="form-group">
                        <label for="telefono">TELÉFONO</label>
                        <input type="number" placeholder="Ingrese teléfono(O)" name="txtTelefono" id="telefono" class="form-control" value="${proveedor.telefono}" required>
                    </div>
                    <div class="form-group">
                        <label for="correo">CORREO</label>
                        <input type="text" placeholder="Ingrese Direccíon(O)" name="txtCorreo" id="correo" class="form-control" value="${proveedor.correo}" required>
                    </div>
                    <div class="form-group">
                        <label for="contacto">CONTACTO</label>
                        <input type="text" placeholder="Ingrese contacto" name="txtContacto" id="contacto" class="form-control" value="${proveedor.contacto}">
                    </div>

                    <input type="hidden" name="id" value="${proveedor.id}">

                    <input type="submit" value="Guardar Proveedor" class="btn btn-primary">
                    <a href="${pageContext.request.contextPath}/proveedor-controller?accion=listar" class="btn btn-danger">Regresar</a>
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

