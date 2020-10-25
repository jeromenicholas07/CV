<%-- 
    Document   : groundBacktestRow
    Created on : Oct 19, 2020, 5:51:08 PM
    Author     : Jerome Nicholas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
        <c:set var="title" value="${param.title}" scope="page"></c:set>
        <c:set var="X_T_bt" value="${requestScope[param.X_T_bt]}" scope="page"></c:set>
        <c:set var="row_start" value="${param.row_start}" scope="page"></c:set>
        <c:set var="row_end" value="${param.row_end}" scope="page"></c:set>      
        
        <tr>
            <td colspan ="30">${title}</td>
        </tr>
        <tr>
            <td>
                NG
            </td>
            <c:forEach var="entry" items="${X_T_bt}" begin="${row_start}" end="${row_end}">
                <td>
                    ${fn:split(entry.key,'|')[1].trim()}
                </td>
            </c:forEach>
        </tr>
        <tr>
            <td>
                ${X_T_bt.get("NG")}
            </td>
            <c:forEach var="entry" items="${X_T_bt}" begin="${row_start}" end="${row_end}">
                <td class="bt-td">
                    <div class="bt-num">
                        ${entry.value}
                    </div>
                    <div class="bt-den">
                        ${X_T_bt.get("NG") - entry.value}
                    </div>
                </td>
            </c:forEach>
        </tr>