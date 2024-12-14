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
                <form action="${pageContext.request.contextPath}/productoUnidad-controller?accion=${accion}" method="post" class="card-body p-2" accept-charset="UTF-8">
                    ${mensaje}


                        <c:if test="${accion == 'agregar'}">
                        <div class="form-group">
                            <label for="nombre">CÓDIGO</label>
                            <input type="text" placeholder="Ingrese codigo(O)" name="txtCodigo" id="nombre" class="form-control" value="" required>
                        </div>
                        <div class="form-group">
                            <label for="tipos">PRODUCTO:</label>
                            <input type="text" name="" value="${detalleEntrega.producto.nombre}" readonly class="form-control">

                        </div><br>
                        <div class="form-group">
                            <label for="estado">ESTADO:</label>
                            <select id="estado" name="estado">
                                <option value="Stock">Stock</option>
                                <option value="Defectuoso">Defectuoso</option>
                            </select>
                        </div>
                            <div class="alert alert-primary" role=alert>
                                Nota:  En caso de equivocación actualizar inmediatamente la unidad.
                                Nota:  Verificar que la entrega sea correcta.
                            </div>
                            <input type="hidden" name="idDetalle" value="${detalleEntrega.id}">
                            <input type="submit" value="Guardar Unidad" class="btn btn-primary">
                            <a href="${pageContext.request.contextPath}/entrega-controller?accion=ver&id=${detalleEntrega.entrega.id}" class="btn btn-danger">Regresar</a>

                        </c:if>
                        <c:if test="${accion == 'editar'}">
                            <div class="form-group">
                                <label for="nombre">CÓDIGO</label>
                                <input type="text" placeholder="Ingrese codigo(O)" name="txtCodigo" id="nombre" class="form-control" value="${unidad.codigo}" required>
                            </div>
                            <div class="form-group">
                                <label for="producto">PRODUCTO:</label>
                                <input type="text" id="producto" value="${unidad.producto.nombre}" readonly class="form-control">
                            </div><br>
                            <div class="form-group">
                                <label for="estado">ESTADO:</label>
                                <select id="estado" name="estado">
                                    <c:choose>
                                        <c:when test="${unidad.estado=='Stock' || 'Defectuoso'}">

                                            <c:if test="${unidad.estado=='Stock'}">
                                                <option value="${unidad.estado}" selected>${unidad.estado}</option>
                                                <option value="Defectuoso">Defectuoso</option>
                                            </c:if>
                                            <c:if test="${unidad.estado=='Defectuoso'}">
                                                <option value="${unidad.estado}" selected>${unidad.estado}</option>
                                                <option value="Stock">Stock</option>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="Stock">Stock</option>
                                            <option value="Defectuoso">Defectuoso</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </div>
                            <div class="alert alert-primary" role=alert>
                                Nota:  En caso de equivocación actualizar inmediatamente la unidad.
                            </div>
                            <input type="submit" value="Editar Unidad" class="btn btn-primary">
                            <a href="${pageContext.request.contextPath}/productoUnidad-controller?accion=listar" class="btn btn-danger">Regresar</a>

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

