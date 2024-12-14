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
                <form action="${pageContext.request.contextPath}/categoria-controller?accion=${accion}" method="post" class="card-body p-2" accept-charset="UTF-8">
                    ${mensaje}
                    <div class="form-group">
                        <label for="nombre">NOMBRE</label>
                        <input type="text" placeholder="Ingrese categoría(O)" name="txtCategoria" id="nombre" class="form-control" value="${categoria.nombre}" required>
                    </div>
                    <div class="form-group">
                        <label for="ruc">DESCRIPCIÓN</label>
                        <input type="text" placeholder="Ingrese descripcíon" name="txtDescripcion" id="ruc" class="form-control" value="${categoria.desc}">
                    </div>

                    <input type="hidden" name="id" value="${categoria.id}">

                    <input type="submit" value="Guardar Categoría" class="btn btn-primary">
                    <a href="${pageContext.request.contextPath}/categoria-controller?accion=listar" class="btn btn-danger">Regresar</a>
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

