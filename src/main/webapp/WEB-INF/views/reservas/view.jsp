<%-- 
    Document   : view
    Created on : 04-mar-2015, 19:57:41
    Author     : Cristian
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <fmt:formatDate value="${reserva.fechaEntrada}" var="formattedDate" type="date" pattern="yyyy-MM-dd" />
    <fmt:formatDate value="${reserva.fechaSalida}" var="formattedDate2" type="date" pattern="yyyy-MM-dd" />
    <head>
        <meta charset="utf-8">
        <title>Ver Reserva ~ ${reserva.NIF} - ${formattedDate}</title>
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
                                <li class="dropdown">
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
                                <li class="active dropdown">
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
                    <fmt:formatDate value="${reserva.fechaEntrada}" var="formattedDate3" type="date" pattern="yyyy-MM-dd" />
                    <h3>
                        RESERVA (${reserva.NIF} - ${formattedDate})
                    </h3>
                    <fieldset>
                        <dl class="dl-horizontal">
                            <dt>
                            NIF:
                            </dt>
                            <dd>
                                ${reserva.NIF}
                            </dd>
                            <dt>
                            Habitación:
                            </dt>
                            <dd>
                                ${reserva.habitacion}
                            </dd>
                            <dt>
                            Fecha Entrada:
                            </dt>
                            <dd>
                                ${formattedDate}
                            </dd>
                            <dt>
                            Fecha Salida:
                            </dt>
                            <dd>
                                ${formattedDate2}
                            </dd>
                        </dl>
                    </fieldset>
                </div>
                <div class="col-md-6 col-xs-6 column">
                    <a class="btn btn-success btn-warning btn-block" href='./EditarReserva?habitacion=${reserva.habitacion}&fechaInicio=${formattedDate3}'>Editar</a>
                </div>
                <div class="col-md-6 col-xs-6 column">
                    <a class="btn btn-success btn-danger btn-block" href='./EliminarReserva?habitacion=${reserva.habitacion}&fechaInicio=${formattedDate3}'>Eliminar</a>
                </div>
            </div>
        </div>
    </body>
</html>