<%@ page import="java.util.Date" %>
<%@ page import="com.example.tiendaj.modelo.entidades.Usuario" %><%--
  Created by IntelliJ IDEA.
  User: Piero
  Date: 27/09/2023
  Time: 0:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
    Date fecha = new Date();
    String fechaActual = fecha.toString();

    Usuario usuario = (Usuario) session.getAttribute("admin");
    if (usuario== null) {
        response.sendRedirect("sistema/LoginAdmin.jsp"); //esto es para que no se pueda acceder a la pagina sin logearse
    }else {


%>
<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/sistema/Admin.jsp">
            <div class="sidebar-brand-icon rotate-n-15">
                <img src="${pageContext.request.contextPath}/img/logoWeb2.png" class="img-thumbnail">
            </div>
            <div class="sidebar-brand-text mx-3">Sistema w-shop </div>
        </a>

        <!-- Divider -->
        <hr class="sidebar-divider my-0">

        <!-- Divider -->
        <hr class="sidebar-divider">

        <!-- Heading -->
        <div class="sidebar-heading">
            Interface
        </div>

        <!-- Nav Item - Pages Collapse Menu -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                <i class="fa fa-motorcycle" aria-hidden="true"></i>
                <span>Pedidos</span>
            </a>
            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <a class="collapse-item" href="${pageContext.request.contextPath}/pedido-controller?accion=listar">Pedidos</a>
                </div>
            </div>
        </li>

        <!-- Nav Item - Productos Collapse Menu -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities" aria-expanded="true" aria-controls="collapseUtilities">
                <i class="fa fa-shopping-basket" aria-hidden="true"></i>
                <span>Productos</span>
            </a>
            <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <a class="collapse-item" href="${pageContext.request.contextPath}/inventario-controller?accion=agregar">Nuevo Producto</a>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/inventario-controller?accion=listar">Productos</a>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/categoria-controller?accion=agregar">Nueva Categoria</a>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/categoria-controller?accion=listar">Categorias</a>
                </div>
            </div>
        </li>

        <!-- Nav Item - Clientes Collapse Menu -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseClientes" aria-expanded="true" aria-controls="collapseUtilities">
                <i class="fas fa-users"></i>
                <span>Clientes</span>
            </a>
            <div id="collapseClientes" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <a class="collapse-item" href="${pageContext.request.contextPath}/usuario-controller?accion=listar">Clientes</a>
                </div>
            </div>
        </li>
        <!-- Nav Item - Utilities Collapse Menu -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseProveedor" aria-expanded="true" aria-controls="collapseUtilities">
                <i class="fas fa-hospital"></i>
                <span>Proveedor</span>
            </a>
            <div id="collapseProveedor" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <a class="collapse-item" href="${pageContext.request.contextPath}/proveedor-controller?accion=agregar">Nuevo Proveedor</a>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/proveedor-controller?accion=listar">Proveedores</a>

                </div>
            </div>
        </li>
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUnidad" aria-expanded="true" aria-controls="collapseUtilities">
                <i class="fa fa-archive" aria-hidden="true"></i>
                <span>Unidades</span>

            </a>
            <div id="collapseUnidad" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <a class="collapse-item" href="${pageContext.request.contextPath}/productoUnidad-controller?accion=agregar">Nueva Unidad</a>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/productoUnidad-controller?accion=listar">Unidades</a>
                </div>
            </div>
        </li>

        <!-- Nav Item - Entregas Collapse Menu -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUsuarios" aria-expanded="true" aria-controls="collapseUtilities">
                <i class="fa fa-truck" aria-hidden="true"></i>
                <span>Entrega</span>
            </a>
            <div id="collapseUsuarios" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <a class="collapse-item" href="${pageContext.request.contextPath}/entrega-controller?accion=agregar">Nueva Entrega</a>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/entrega-controller?accion=listar">Entregas</a>

                </div>
            </div>
        </li>


    </ul>
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">
            <!-- Topbar -->
            <nav class="navbar navbar-expand navbar-light bg-primary text-white topbar mb-4 static-top shadow">

                <!-- Sidebar Toggle (Topbar) -->
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>
                <div class="input-group">
                    <h6>Sistema de Venta</h6>
                    <p class="ml-auto"><strong>Perú</strong></p>
                </div>

                <!-- Topbar Navbar -->
                <ul class="navbar-nav ml-auto">

                    <div class="topbar-divider d-none d-sm-block">

                    </div>

                    <!-- Nav Item - User Information -->
                    <li class="nav-item dropdown no-arrow">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="mr-2 d-none d-lg-inline small text-white"> <%=usuario.getCliente().getNombre()%></span>
                        </a>
                        <!-- Dropdown - User Information -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                            <a class="dropdown-item" href="#">
                                <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                <%=usuario.getCliente().getEmail()%>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/loginAdmin-controller?accion=salir">
                                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                Salir
                            </a>
                        </div>
                    </li>

                </ul>

            </nav>
