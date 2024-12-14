<%--
  Created by IntelliJ IDEA.
  User: Piero
  Date: 31/08/2023
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>SignUp</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link rel="stylesheet" href="css/loginReg.css">
</head>
<body>
<p> ${mensaje}</p>
<h1>Registrarse</h1>
<span>o <a href="Login.jsp">Iniciar sesion</a></span>
<!-- El email,usuario deben ser unico validar con ajax-->
<form action="login-controller" method="POST" onsubmit="return validarContraseñas()">

    <input name="txtNombres" type="text" placeholder="Nombre" required>

    <input name="txtApellidos" type="text" placeholder="Apellidos o RS" required>

    <input name="txtDni" type="text" placeholder="Dni o RUC" required>

    <input name="txtEmail" type="email" placeholder="Email" required>

    <input name="txtTelefono" type="tel" placeholder="Telefono" required>

    <input name="txtUsuario" type="text" placeholder="Usuario" required>

    <input name="txtPassword" type="password" placeholder="Ingrese su contraseña" required>

    <input name="confirm_password" type="password" placeholder="Confirmar contraseña" required>
    <p id="mensajeError" style="color: red;"></p>
    <input type="hidden" name="accion" value="registro">
    <input type="submit" value="REGISTRARSE">
</form>


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
</body>
</html>

