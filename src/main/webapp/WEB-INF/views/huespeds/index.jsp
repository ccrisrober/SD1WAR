<%-- 
    Document   : index
    Created on : 02-mar-2015, 12:09:11
    Author     : Cristian
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Bootstrap 3, from LayoutIt!</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
        <!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
        <!--script src="js/less-1.3.3.min.js"></script-->
        <!--append ‘#!watch’ to the browser URL, then refresh the page. -->

        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="js/html5shiv.js"></script>
        <![endif]-->

        <!-- Fav and touch icons -->
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/img/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/img/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/img/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/img/apple-touch-icon-57-precomposed.png">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.png">

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/scripts.js"></script>
    </head>

    <body>
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <nav class="navbar navbar-default" role="navigation">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button> <a class="navbar-brand" href=".">SSDD</a>
                        </div>

                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav">
                                <li class="">
                                    <a href=".">Home</a>
                                </li>
                                <li class="active dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Huéspeds<strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="./AniadirHuesped">Añadir</a>
                                        </li>
                                        <li>
                                            <a href="./Huespeds">Listado</a>
                                        </li>
                                    </ul>
                                </li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Reservas<strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="./AniadirReserva">Añadir</a>
                                        </li>
                                        <li>
                                            <a href="./Reservas">Listado</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </nav>
                    <h3>
                        HUESPEDS
                    </h3>
                    <c:if test="${ok != null}">
                        <c:if test="${ok == true}">
                            <div class="alert alert-success alert-dismissable">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                <p>${msg}</p>
                            </div>
                        </c:if>
                        <c:if test="${ok == false}">
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                <p>${msg}</p>
                            </div>
                        </c:if>
                    </c:if>
                    <table class="table table-striped">
                        <tr>
                            <th>NIF</th>
                            <th>Nombre</th>         
                            <th>Apellidos</th>
                            <th colspan='3'></th>
                        </tr>
                        <c:set var="index" value="0" />
                        <c:forEach var="h" items="${huespeds}">
                            <tr class="active">
                                <td>${h.NIF}</td>
                                <td>${h.nombre}</td>
                                <td>${h.apellidos}</td>
                                <td><a class="btn btn-success btn-info btn-block" href='./VerHuesped?NIF=${h.NIF}'>Ver</td>
                                <td><a class="btn btn-success btn-warning btn-block" href='./EditarHuesped?NIF=${h.NIF}'>Editar</td>
                                <td>
                                    <form action="./EliminarHuesped" method="post">
                                        <input type="hidden" value="${h.NIF}" name="NIF" />
                                        <button type="submit" class="btn btn-success btn-danger btn-block">Eliminar</button>
                                    </form>
                                </td>
                            <tr>
                            </c:forEach>
                    </table>
                    <a href='./AniadirHuesped' class="btn btn-success btn-default btn-block">Añadir nuevo</a>
                    <br/>
                    <hr/>
                    <h2>Buscar</h2>
                    <form class="form-horizontal" action="./BuscarHuesped" method="GET">
                        <div class="form-group">
                            <div class="">
                                <label for="type" class="col-sm-2 control-label">Tipo: </label>
                                <div class="col-sm-4">
                                    <select class="form-control" name="type">
                                        <option value="nombre">Nombre</option>
                                        <option value="apellidos">Apellidos</option>
                                    </select>
                                </div>
                            </div>
                            <div class="">
                                <label for="value" class="col-sm-2 control-label">Valor: </label>
                                <div class="col-sm-4">
                                <input class="form-control" type="text" name="value" />
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-default btn-default btn-block">Buscar</button>
                        <a href="./Huespeds" class="btn btn-warning btn-default btn-block">Reset</a>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
