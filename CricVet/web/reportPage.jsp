<%-- 
    Document   : reportPage
    Created on : Sep 12, 2020, 10:10:28 PM
    Author     : Jerome Nicholas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report Page</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>

    </head>
    <body>
        <nav class="navbar sticky-top navbar-dark bg-dark">
            <a class="navbar-brand" href="#">Report</a>
        </nav>
        <br>
        <div class="container-fluid text-center">
            <div class="row">
                <div class="col-3">
                    <ul class="list-group">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Total checked
                            <span class="badge badge-primary badge-pill">${reportSize}</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Loaded
                            <span class="badge badge-primary badge-pill">${fn:length(loaded)}</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Not loaded
                            <span class="badge badge-primary badge-pill">${fn:length(notLoaded)}</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Misc.
                            <span class="badge badge-primary badge-pill">${fn:length(misc)}</span>
                        </li>
                    </ul>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-5">
                    <table class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th colspan="10">Not loaded</th>
                            </tr>
                            <tr>
                                <th scope="col" style="width: 15%">Match Id</th>
                                <th scope="col">Link</th>
                                <th scope="col">Error</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="m" items="${notLoaded}">
                                <tr>
                                    <td>${m.getMatchId()}
                                    <td><a href="${m.getMatchLink()}">Link</a>
                                        <c:choose>
                                            <c:when test="${m.hasErrorLink()}">
                                            <td><a href="${m.getErrorLink()}">${m.getErrorMessage()}</a></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td data-toggle="popover" data-container="body" data-placement="right" title="${m.getErrorMessage()}" data-content="${m.getErrorStackTrace()}">${m.getErrorMessage()}</td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <div class="col-5">
                    <table class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th colspan="10">Loaded</th>
                            </tr>
                            <tr>
                                <th scope="col" style="width: 15%">Match Id</th>
                                <th scope="col">Link</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="m" items="${loaded}">
                                <tr>
                                    <td>${m.getMatchId()}
                                    <td><a href="${m.getMatchLink()}">Link</a>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <c:if test="${fn:length(misc) > 0}">
            <div class="row">
                <div class="col-5">
               `    <table class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th colspan="10">Misc.</th>
                            </tr>
                            <tr>
                                <th scope="col" style="width: 15%">Link</th>
                                <th scope="col">Error</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="m" items="${misc}">
                                <tr>
                                    <td><a href="${m.getMatchLink()}">Link</a>
                                    <td data-toggle="popover" data-container="body" data-placement="right" title="${m.getErrorMessage()}" data-content="${m.getErrorStackTrace()}">${m.getErrorMessage()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table> 
                </div>
            </div>
            </c:if>
        </div>
        <script>
            $(document).ready(function () {
                $('[data-toggle="popover"]').popover();
            });
        </script>
    </body>
</html>
