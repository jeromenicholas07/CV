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
        
        <%
            filtering_shit:{
                final int pIndex = Integer.parseInt(request.getParameter("pIndex"));
                List<Inning> X_A = new ArrayList<Inning>((List<Inning>) pageContext.getAttribute("X_A"));
                List<Inning> X_B = new ArrayList<Inning>((List<Inning>) pageContext.getAttribute("X_B"));
                List<Inning> X_G = new ArrayList<Inning>((List<Inning>) pageContext.getAttribute("X_G"));

                X_A.removeIf(i -> i.getParams().get(pIndex).contains("-1"));
                X_B.removeIf(i -> i.getParams().get(pIndex).contains("-1"));
                X_G.removeIf(i -> i.getParams().get(pIndex).contains("-1"));

                X_A = X_A.subList(0, Math.min(5, X_A.size()));
                X_B = X_B.subList(0, Math.min(5, X_B.size()));
                X_G = X_G.subList(0, Math.min(5, X_G.size()));

                pageContext.setAttribute("X_A", X_A);
                pageContext.setAttribute("X_B", X_B);
                pageContext.setAttribute("X_G", X_G);
            }

        %>
        
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
                sorting:{
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
        <c:forEach var="in" items="${As}" varStatus="loop">
            <td name="${className}" class="A" data-count="${loop.count}">
                ${in.getParams().get(pIndex) }
            </td>
        </c:forEach>
        <td>
        <c:forEach var="in" items="${Bs}"varStatus="loop">
            <td name="${className}" class="B" data-count="${loop.count}">
                ${in.getParams().get(pIndex) }
            </td>
        </c:forEach>
        <td>
        <c:forEach var="in" items="${Gs}" varStatus="loop">
            <td name="${className}" class="G" data-count="${loop.count}">
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
    <c:forEach var="in" items="${ASs}" varStatus="loop">
        <td name="${className}" class="T" data-count="${loop.count}">
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
