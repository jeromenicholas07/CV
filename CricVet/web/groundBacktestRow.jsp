<%-- 
    Document   : groundBacktestRow
    Created on : Oct 19, 2020, 5:51:08 PM
    Author     : Jerome Nicholas
--%>

<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
        <c:set var="title" value="${param.title}" scope="page"></c:set>
        <c:set var="X_T_bt" value="${requestScope[param.X_T_bt]}" scope="page"></c:set>
        <c:set var="row_start" value="${param.row_start}" scope="page"></c:set>
        <c:set var="row_end" value="${param.row_end}" scope="page"></c:set>      
        
<!--        <tr>
            
            <td>
                NG
            </td>
            <c:forEach var="entry" items="${X_T_bt}" begin="${row_start}" end="${row_end}">
                <td>
                    ${fn:split(entry.key,'|')[1].trim()}
                </td>
            </c:forEach>
        </tr>-->
        <tr>
            <td style="border-left-style: hidden">
                ${title}
            </td>
            <td>
                ${X_T_bt.get("NG")}
            </td>
            <c:forEach var="entry" items="${X_T_bt}" begin="${row_start}" end="${row_end}">
                <c:choose>
                    <c:when test="${entry.value == -42}">
                        <td></td>
                    </c:when>
                    <c:otherwise>
                        <%
                            int val = ((Map.Entry<String, Integer>) pageContext.getAttribute("entry")).getValue();
                            int num = val & 0xFFFF;
                            int den = (val >> 16) & 0xFFFF;
                            
                            pageContext.setAttribute("num", num);
                            pageContext.setAttribute("den", den);
                        %>
                        <td class="bt-td">
                            <div class="bt-num">
                                ${num}
                            </div>
                            <div class="bt-den">
                                ${den}
                            </div>
                        </td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>