<%@ page import="com.example.tiendaj.modelo.entidades.Usuario" %><%--
  Created by IntelliJ IDEA.
  User: Piero
  Date: 19/09/2023
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    HttpSession session1 = request.getSession();
    Usuario user = (Usuario) session1.getAttribute("usuario");

%>
<head>
    <meta charset="utf-8">
    <title>Usuario</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link rel="stylesheet" href="css/loginReg.css">

</head>
<body>
    <?php require '../partials/header.php' ?>

    <%
        if(user!=null){
    %>
    <br> BIENVENIDO. <?= $user['email']; ?>
    <br>Has iniciado sesión correctamente
    <a href="login-controller?accion=cerrar">
        Cerrar sesión
    </a> o
    <a href="index.jsp">Regresar inicio</a>
    <%}else{%>
    <h1>Por favor ingresa o regístrate</h1>

    <a href="Login.jsp">Iniciar sesión</a>,
    <a href="Registro.jsp">Registrarse</a>,
    <a href="index.jsp">Regresar inicio</a>
    <%}%>
</body>
</html>
