<%-- 
    Document   : edit.jsp
    Created on : 02-mar-2015, 12:25:14
    Author     : Cristian
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Editar huésped - ${huesped.NIF}</title>
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
                        Añadir nuevo huésped
                    </h3>

                    <form method='POST' action='' role="form" class="form-horizontal">

                        <c:if test="${errors != null}">
                            <div class="alert alert-dismissable alert-warning">
                                <ul>
                                    <c:forEach items="${errors}" var="e"> 
                                        <li>${e}</li>
                                        </c:forEach>
                                </ul>
                            </div>
                        </c:if>
                        <fieldset>
                            <input type="hidden" name="huesped.id" value='${oldNIF}' />
                            <div class="form-group">
                                <label for="huesped.NIF" class="col-sm-2 control-label">NIF: </label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="huesped.NIF" value='${huesped.NIF}' required />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="huesped.nombre" class="col-sm-2 control-label">Nombre:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="huesped.nombre" value='${huesped.nombre}' required />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="huesped.apellidos" class="col-sm-2 control-label">Apellidos: </label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="huesped.apellidos" value='${huesped.apellidos}' required />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="huesped.apellidos" class="col-sm-2 control-label">Fecha de nacimiento: </label>
                                <div class="col-sm-10">
                                    <fmt:formatDate value="${huesped.nacimiento}" var="formattedDate" type="date" pattern="yyyy-MM-dd" />
                                    <input type='date' name='huesped.fechaNacimiento' value='${formattedDate}' required /></p>
                                </div>
                            </div>
                            <div class="form-group" title="Domicilio">
                                <div class="form-group">
                                    <label for="huesped.apellidos" class="col-sm-2 control-label">Dirección: </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="huesped.direccion" value='${huesped.domicilio.direccion}' required />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="huesped.apellidos" class="col-sm-2 control-label">Localidad: </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="huesped.localidad" value='${huesped.domicilio.localidad}' required />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="huesped.apellidos" class="col-sm-2 control-label">Provincia: </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="huesped.provincia" value='${huesped.domicilio.provincia}' required />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="huesped.apellidos" class="col-sm-2 control-label">Código postal: </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="huesped.codigoPostal" value='${huesped.domicilio.codigoPostal}' required />
                                    </div>
                                </div>
                            </div>
                            <div>
                                <br/>
                                <h4>Datos opcionales</h4>
                                <div class="form-group" title="Opcional">
                                    <div class="form-group">
                                        <label for="huesped.telefonoFijo" class="col-sm-2 control-label">Teléfono Fijo: </label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" name="huesped.telefonoFijo" value='${huesped.telefonoFijo}' />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="huesped.telefonoMovil" class="col-sm-2 control-label">Teléfono Móvil: </label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" name="huesped.telefonoMovil" value='${huesped.telefonoMovil}' />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="huesped.email" class="col-sm-2 control-label">Email: </label>
                                        <div class="col-sm-10">
                                            <input type="email" class="form-control" name="huesped.email" value='${huesped.email}' />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-default">Editar</button>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>