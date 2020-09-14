<%-- 
    Document   : displayHeader
    Created on : Sep 9, 2020, 1:04:18 AM
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
        <title>Fills in the values to a table for the passed header</title>
    </head>
    <body>
        <c:set var="X_A" value="${requestScope[param.A]}" scope="page"></c:set>
        <c:set var="X_B" value="${requestScope[param.B]}" scope="page"></c:set>
        <c:set var="X_G" value="${requestScope[param.G]}" scope="page"></c:set>
        <c:set var="pIndex" value="${Integer.parseInt(param.pIndex)}" scope="page"></c:set>
        <c:set var="className" value="${param.className}" scope="page"></c:set>
        
        <table class="table table-bordered">
            <tr class="thead-dark">
                <th colspan="${fn:length(X_A)}">${teamOne}</th>
                <th>
                <th colspan="${fn:length(X_B)}">${teamTwo}</th>
                <th>
                <th colspan="${fn:length(X_G)}">${groundName}</th>
            </tr>
            <tr>
            <c:forEach var="in" items="${X_A}">
                <td>
                    ${in.getParams().get(pIndex)}
                </td>
            </c:forEach>

            <td></td>

            <c:forEach var="in" items="${X_B}">
                <td>
                    ${in.getParams().get(pIndex)}
                </td>
            </c:forEach>

            <td></td>

            <c:forEach var="in" items="${X_G}">
                <td>
                    ${in.getParams().get(pIndex)}
                </td>
            </c:forEach>


        </tr>

        <tr class="thead-dark">
            <th colspan="${fn:length(X_A) + fn:length(X_B) + 1 }">Sorted:
            <th colspan="20">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" name="${className}" id="${className}">@</span>
                    </div>
                    <input type="number" class="form-control" name="${className}">
                </div>

            </th>
        </tr>
        <tr>
            <%
                if (true) {
                    final int pIndex = Integer.parseInt(request.getParameter("pIndex"));
                    Comparator comp = new Comparator<Inning>() {
                        @Override
                        public int compare(Inning o1, Inning o2) {
                            return Integer.parseInt(o1.getParams().get(pIndex))
                                    - Integer.parseInt(o2.getParams().get(pIndex));
                        }
                    };
                    
                    List<Inning> As = new ArrayList<Inning>((List<Inning>) pageContext.getAttribute("X_A"));
                    Collections.sort(As, comp);
                    pageContext.setAttribute("As", As);

                    List<Inning> Bs = new ArrayList<Inning>((List<Inning>) pageContext.getAttribute("X_B"));
                    Collections.sort(Bs, comp);
                    pageContext.setAttribute("Bs", Bs);

                    List<Inning> Gs = new ArrayList<Inning>((List<Inning>) pageContext.getAttribute("X_G"));
                    Collections.sort(Gs, comp);
                    pageContext.setAttribute("Gs", Gs);

                    List<Inning> ASs = new ArrayList<Inning>();
                    ASs.addAll(As);
                    ASs.addAll(Bs);
                    Collections.sort(ASs, comp);
                    pageContext.setAttribute("ASs", ASs);
                }
            %>
        <c:forEach var="in" items="${As}">
            <td name="${className}" class="A">
                ${in.getParams().get(pIndex) }
            </td>
        </c:forEach>
        <td>
        <c:forEach var="in" items="${Bs}">
            <td name="${className}" class="B">
                ${in.getParams().get(pIndex) }
            </td>
        </c:forEach>
        <td>
        <c:forEach var="in" items="${Gs}">
            <td name="${className}" class="G">
                ${in.getParams().get(pIndex) }
            </td>
        </c:forEach>
    </tr>
    <tr>
        <td colspan="${fn:length(As)}" name="${className}Odd" class="A">

        </td>
        <td>
        <td colspan="${fn:length(Bs)}" name="${className}Odd" class="B">

        </td>

        <td>
        <td colspan="${fn:length(Gs)}" name="${className}Odd" class="G">

        </td>
    </tr>
    <tr>
    <c:forEach var="in" items="${ASs}">
        <td name="${className}">
            ${in.getParams().get(pIndex) }
        </td>
    </c:forEach>
</tr>
<tr>
    <td colspan="${fn:length(ASs)}" name="${className}Odd" class="T">
</tr>


</table>
</body>
</html>