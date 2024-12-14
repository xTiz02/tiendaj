<%@ page import="com.example.tiendaj.modelo.entidades.Usuario" %><%--
  Created by IntelliJ IDEA.
  User: Piero
  Date: 26/09/2023
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- location header
<@ include file="header.jsp"%>-->
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
<%@include file="header.jsp" %>
            <!-- HAsta aqui el header-->
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-0 text-gray-800">Panel de Administración</h1>
                </div>

                <!-- Content Row -->
                <div class="row">

                    <!-- Earnings (Monthly) Card Example -->
                    <a class="col-xl-3 col-md-6 mb-4" href="lista_usuarios.php">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Entregas</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800"></div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-user fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>

                    <!-- Earnings (Monthly) Card Example -->
                    <a class="col-xl-3 col-md-6 mb-4" href="lista_cliente.php">
                        <div class="card border-left-success shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-success text-uppercase mb-1">Clientes</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800"></div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-users fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>

                    <!-- Earnings (Monthly) Card Example -->
                    <a class="col-xl-3 col-md-6 mb-4" href="lista_productos.php">
                        <div class="card border-left-info shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-info text-uppercase mb-1">Productos</div>
                                        <div class="row no-gutters align-items-center">
                                            <div class="col-auto">
                                                <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800"></div>
                                            </div>
                                            <div class="col">
                                                <div class="progress progress-sm mr-2">
                                                    <div class="progress-bar bg-info" role="progressbar" style="width: 50%" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>

                    <!-- Pending Requests Card Example -->
                    <a class="col-xl-3 col-md-6 mb-4" href="ventas.php">
                        <div class="card border-left-warning shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">Pedidos</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800"></div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-center mb-4">
                    <h1 class="h3 mb-0 text-gray-800">Configuración</h1>
                </div>
                <div class="row">

                    <div class="col-lg-6">
                        <div class="card">
                            <div class="card-header bg-primary text-white">
                                Información Personal
                            </div>
                            <div class="card-body">
                                <div class="form-group">
                                    <label>Nombre: <strong><%=usuario.getCliente().getNombre()%></strong></label>
                                </div>
                                <div class="form-group">
                                    <label>Correo: <strong><%=usuario.getCliente().getEmail()%></strong></label>
                                </div>
                                <div class="form-group">
                                    <label>Rol: <strong><%=usuario.getRol()%></strong></label>
                                </div>
                                <div class="form-group">
                                    <label>Usuario: <strong><%=usuario.getUsuario()%></strong></label>
                                </div>
                                <ul class="list-group">
                                    <li class="list-group-item active">Cambiar Contraseña</li>
                                    <form action="${pageContext.request.contextPath}/loginAdmin-controller?accion=cambiar&id=<%=usuario.getId()%>" method=" post" name="frmChangePass" id="frmChangePass" class="p-3">
                                        <div class="form-group">
                                            <label>Nueva Contraseña</label>
                                            <input type="password" name="txtPassword" id="nueva" placeholder="Nueva Clave" required class="form-control">
                                        </div>
                                        <div class="form-group">
                                            <label>Confirmar Contraseña</label>
                                            <input type="password" name="confirm_password" id="confirmar" placeholder="Confirmar clave" required class="form-control">
                                        </div>
                                        <div class="alertChangePass" style="display:none;">
                                        </div>
                                        <p id="mensajeError" style="color: red;"></p>
                                        <div>
                                            <button type="submit" class="btn btn-primary btnChangePass">Cambiar Contraseña</button>
                                        </div>

                                    </form>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-6">
                        <div class="card">
                            <div class="card-header bg-primary text-white">
                                Datos de la Empresa
                            </div>
                            <div class="card-body">
                                <form action="empresa.php" method="post" id="frmEmpresa" class="p-3">
                                    <div class="form-group">
                                        <label>Ruc:</label>
                                        <input type="number" name="txtDni" value="918237198" id="txtDni" placeholder="Dni de la Empresa" required class="form-control">
                                    </div>
                                    <div class="form-group">
                                        <label>Nombre:</label>
                                        <input type="text" name="txtNombre" class="form-control" value="W-SHOP" id="txtNombre" placeholder="Nombre de la Empresa" required class="form-control">
                                    </div>
                                    <div class="form-group">
                                        <label>Razon Social:</label>
                                        <input type="text" name="txtRSocial" class="form-control" value="W-SHOP Electrónicos S.A.C" id="txtRSocial" placeholder="Razon Social de la Empresa">
                                    </div>
                                    <div class="form-group">
                                        <label>Teléfono:</label>
                                        <input type="number" name="txtTelEmpresa" class="form-control" value="913249464" id="txtTelEmpresa" placeholder="teléfono de la Empresa" required>
                                    </div>
                                    <div class="form-group">
                                        <label>Correo Electrónico:</label>
                                        <input type="email" name="txtEmailEmpresa" class="form-control" value="w-shopCC@gmail.com" id="txtEmailEmpresa" placeholder="Correo de la Empresa" required>
                                    </div>
                                    <div class="form-group">
                                        <label>Dirección:</label>
                                        <input type="text" name="txtDirEmpresa" class="form-control" value="Av Giron Nuevo" id="txtDirEmpresa" placeholder="Dirreción de la Empresa" required>
                                    </div>


                                </form>
                            </div>
                        </div>
                    </div>

                </div>


            </div>
            <!-- /.container-fluid -->

        </div>
<script>
    function validarContraseñas() {
        var contraseña = document.getElementsByName("txtPassword")[0].value;
        var confirmarContraseña = document.getElementsByName("confirm_password")[0].value;
        var mensajeError = document.getElementById("mensajeError");

        if (contraseña !== confirmarContraseña) {
            mensajeError.innerHTML = "Las contraseñas no coinciden. Por favor, inténtalo de nuevo.";
            mensajeError.style.color = "red";
            return false;
        } else {
            mensajeError.innerHTML = "";
        }


        return true;}
</script>
        <!-- End of Main Content hasta auiel main luego el footeer -->
        <%@include file="footer.jsp" %>

</html>

