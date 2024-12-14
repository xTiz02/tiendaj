<%--
  Created by IntelliJ IDEA.
  User: Piero
  Date: 21/09/2023
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="img-controller" method="post" enctype="multipart/form-data">
    <input type="file" name="archivos" multiple>
    <input type="submit" value="submit">
</form>
</body>
</html>
