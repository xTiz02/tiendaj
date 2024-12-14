<%--
  Created by IntelliJ IDEA.
  User: Piero
  Date: 31/08/2023
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>CUENTA</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link rel="stylesheet" href="css/loginReg.css">
</head>
<body>

<p> ${mensaje}</p>


<h1>Login</h1>
<span>o <a href="Registro.jsp">registrar</a></span>

<form action="login-controller" method="POST" id="loginForm">
    <input name="txtUsuario" type="text" placeholder="Ingrese su usuario" id="username">
    <input name="txtPassword" type="password" placeholder="Ingrese su contrasena" id="password">
    <input type="hidden" name="accion" value="login">
    <div class="captcha-container" id="captchaTextContainer"></div>
    <input type="text" id="captcha" placeholder="Ingrese el CAPTCHA">
    <input type="submit" id="submitButton" value="Ingresar" >
</form>
<script>
    function generarCaptcha() {
    var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var captcha = '';
    for (var i = 0; i < 4; i++) {
    var randomIndex = Math.floor(Math.random() * characters.length);
    captcha += characters.charAt(randomIndex);
    }
    return captcha;
    }

    // Inicializar el CAPTCHA al cargar la página
    var captchaText = generarCaptcha();
    document.getElementById('captchaTextContainer').innerText = captchaText;

    // Función para validar el CAPTCHA y habilitar el botón de ingresar
    function validarCaptcha() {
    var captchaValue = document.getElementById('captcha').value.toUpperCase();
    var expectedCaptcha = captchaText;

    var submitButton = document.getElementById('submitButton');
    submitButton.disabled = !(captchaValue === expectedCaptcha);
    }
    document.getElementById('captcha').addEventListener('input', validarCaptcha);
    document.getElementById('username').addEventListener('input', validarCaptcha);
    document.getElementById('password').addEventListener('input', validarCaptcha);

    document.getElementById('loginForm').addEventListener('submit', function (event) {
        event.preventDefault();  // Evitar la recarga de la página
        if (document.getElementById('submitButton').disabled) {
            alert('Por favor, complete todos los campos y resuelva el CAPTCHA correctamente.');
        } else {
            // Enviar el formulario al controlador
            this.submit();
        }
    });
</script>
</body>
</html>

