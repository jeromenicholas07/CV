<%-- 
    Document   : displayBacktests
    Created on : Sep 9, 2020, 2:53:26 AM
    Author     : Jerome Nicholas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Inning"%>
<%@page import="java.util.List"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BackTest displayer</title>
    </head>
    <body>
        <c:set var="X_A_bt" value="${requestScope[param.A_bt]}" scope="page"></c:set>
        <c:set var="X_TA_bt" value="${requestScope[param.TA_bt]}" scope="page"></c:set>
        <c:set var="X_B_bt" value="${requestScope[param.B_bt]}" scope="page"></c:set>
        <c:set var="X_TB_bt" value="${requestScope[param.TB_bt]}" scope="page"></c:set>
        <c:set var="X_G_bt" value="${requestScope[param.G_bt]}" scope="page"></c:set>
        
        
        <table class="table table-bordered">
            <tr class="thead-light">
                <th colspan="30">Back testing:
                    <div class="btn btn-light" onclick="collapseSwitch(this)">
                        <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                        <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                        <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                        </svg>
                    </div>
            </tr>
            <tr>
                <th colspan="${fn:length(X_A_bt)}">${teamOne}</th>
                <th>
                <th colspan="${fn:length(X_B_bt)}">${teamTwo}</th>
                <th>
                <th colspan="${fn:length(X_G_bt)}">${groundName}</th>
            </tr>
            <tr>
            <c:forEach var="entry" items="${X_A_bt}">
                <td>
                    ${entry.key}
                </td>
            </c:forEach>

            <td></td>

            <c:forEach var="entry" items="${X_B_bt}">
                <td>
                    ${entry.key}
                </td>
            </c:forEach>

            <td></td>

            <c:forEach var="entry" items="${X_G_bt}">
                <td>
                    ${entry.key}
                </td>
            </c:forEach>
        </tr>
        <tr>
        <c:forEach var="entry" items="${X_A_bt}">
            <td>
                ${entry.value}
            </td>
        </c:forEach>

        <td></td>

        <c:forEach var="entry" items="${X_B_bt}">
            <td>
                ${entry.value}
            </td>
        </c:forEach>

        <td></td>

        <c:forEach var="entry" items="${X_G_bt}">
            <td>
                ${entry.value}
            </td>
        </c:forEach>
    </tr>
</table>

<table class="table table-bordered">
    <tr>
        <th colspan="30">${teamOne}</th>
    </tr>
    <tr>
    <c:forEach var="entry" items="${X_TA_bt}">
        <td>
            ${entry.key}
        </td>
    </c:forEach>
</tr>
<tr>
<c:forEach var="entry" items="${X_TA_bt}">
    <td>
        ${entry.value}
    </td>
</c:forEach>
</tr>
<tr>
<c:forEach var="entry" items="${X_TA_bt}">
    <td>
        ${X_TA_bt.get("N") - entry.value}
    </td>
</c:forEach>
</tr>

<tr>
    <td colspan="30"></td>
</tr>

<tr>
    <th colspan="30">${teamTwo}</th>
</tr>
<tr>
<c:forEach var="entry" items="${X_TB_bt}">
    <td>
        ${entry.key}
    </td>
</c:forEach>
</tr>
<tr>
<c:forEach var="entry" items="${X_TB_bt}">
    <td>
        ${entry.value}
    </td>
</c:forEach>
</tr>
<tr>
<c:forEach var="entry" items="${X_TB_bt}">
    <td>
        ${X_TB_bt.get("N") - entry.value}
    </td>
</c:forEach>
</tr>
</table>
<script type="text/javascript">
    function collapseSwitch(butt){ 
        butt = $(butt);
        var t1 = butt.parents('table');

        var t1_rows = t1.find('tr').not('.thead-light');

        var t2 = t1.next();

        t1_rows.fadeToggle(1000);
        t2.fadeToggle(1000);
    }
</script>
</body>
</html>
