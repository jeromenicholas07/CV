<%-- 
    Document   : results
    Created on : Mar 3, 2019, 12:25:47 AM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>

        <title>Results Page</title>
    </head>
    <body >
        <style>
            .mar{
                margin-top: 15px;
                margin-bottom: 15px;
            }
        </style>
        <div class="container-fluid text-center">
            <div class="row">
                <div class="col-6">
                    <h1>${teamOne}</h1>
                </div>
                <div class="col-6">
                    <h1>${teamTwo}</h1>
                </div>
            </div>
            <c:forEach var="h" items="${headers}" varStatus="loop">
                <div class="row" >
                    <div class ="col-6">
                        <div class="card mar">
                            <div class="card-body ">
                                <h5 class="card-title">${h}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="5">${teamOne} Batting First</th>
                                        <th colspan="5">${teamTwo} Batting Second</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${oneBatFirst}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <c:forEach var="in" items="${twoBatSecond}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>

                                </table>
                            </div>
                        </div>
                    </div>

                    <div class="col-6">
                        <div class="card mar">
                            <div class="card-body ">
                                <h5 class="card-title">${h}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="5">${teamTwo} Batting First</th>
                                        <th colspan="5">${teamOne} Batting Second</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${twoBatFirst}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <c:forEach var="in" items="${oneBatSecond}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>

                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

    </body>
</html>
