<%--
  Created by IntelliJ IDEA.
  User: Piero
  Date: 17/09/2023
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f2f2f2;
    }

    header {
      background-color: #333;
      color: #fff;
      text-align: center;
      padding: 20px 0;
    }

    main {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
      background-color: #fff;
      box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
    }

    .confirmation-message {
      text-align: center;
      margin-bottom: 20px;
    }

    .order-details {
      border: 1px solid #ddd;
      padding: 10px;
      background-color: #f9f9f9;
    }

    footer {
      text-align: center;
      background-color: #333;
      color: #fff;
      padding: 10px 0;
    }
  </style>
</head>
<body>
<header>
  <h1>Compra Completada</h1>
</header>

<main>
  <div class="confirmation-message">
    <p>¡Gracias por tu compra!</p>
    <p>Tu pago con PayPal se ha procesado correctamente.</p>
    Volver a la <a href="index.jsp">página principal</a>.
  </div>

</main>

<footer>
  <p>&copy; 2023 Tu Tienda en Línea W-SHOP</p>
</footer>
</body>
</html>
