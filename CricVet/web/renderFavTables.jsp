<%-- 
    Document   : renderFavTables
    Created on : 29 May, 2021, 5:45:24 PM
    Author     : j
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <c:set var="title" value="${param.title}" scope="page"></c:set>
        <c:set var="A" value="${requestScope[param.A]}" scope="page"></c:set>
        <c:set var="B" value="${requestScope[param.B]}" scope="page"></c:set>
        <c:set var="G" value="${requestScope[param.G]}" scope="page"></c:set>
        <c:set var="O" value="${requestScope[param.O]}" scope="page"></c:set>


            <div class="row justify-content-center">
                <div class="col-8">
                    <table class="table table-bordered" style="margin-bottom: 0px">
                        <tr class="thead-dark">
                            <th colspan="30"><h3 style="margin: .3rem;">${title}</h3></th>
                    </tr>
                    <tr class="thead-dark">
                        <th colspan="${fn:length(A)}">Favorite</th>
                        <th></th>
                        <th colspan="${fn:length(B)}">Non-Favorite</th>
                            <c:if test="${G != null}">
                            <th></th>
                            <th colspan="${fn:length(G)}">Ground</th>
                            </c:if>
                    </tr>
                    <tr class="table-secondary">
                        <c:forEach var="entry" items="${A}">
                            <th>
                                ${entry.key}
                            </th>
                        </c:forEach>
                        <td></td>
                        <c:forEach var="entry" items="${B}">
                            <th>
                                ${entry.key}
                            </th>
                        </c:forEach>

                        <c:if test="${G != null}">
                            <td></td>
                            <c:forEach var="entry" items="${G}">
                                <th>
                                    ${entry.key}
                                </th>
                            </c:forEach>
                        </c:if>
                    </tr>
                    <tr>
                        <c:forEach var="entry" items="${A}">
                            <td>
                                ${entry.value}
                            </td>
                        </c:forEach>
                        <td></td>
                        <c:forEach var="entry" items="${B}">
                            <td>
                                ${entry.value}
                            </td>
                        </c:forEach>

                        <c:if test="${G != null}">
                            <td></td>
                            <c:forEach var="entry" items="${G}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </c:if>
                    </tr>
                </table>
            </div>
        </div>

        <div class="row justify-content-center">
            <div class="col-8">
                <table class="table table-bordered">
                    <tr class="thead-dark">
                        <th colspan="30">Overall</th>
                    </tr>
                    <tr class="table-secondary">
                        <c:forEach var="entry" items="${O}">
                            <th>
                                ${entry.key}
                            </th>
                        </c:forEach>
                    </tr>
                    <tr>
                        <c:forEach var="entry" items="${O}">
                            <td>
                                ${entry.value}
                            </td>
                        </c:forEach>
                    </tr>
                </table>
            </div>
        </div>


    </body>
</html>
