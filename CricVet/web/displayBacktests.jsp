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

        <c:set var="X_G_bt" value="${requestScope[param.G_bt]}" scope="page"></c:set>
        <c:set var="X_TA_bt" value="${requestScope[param.TA_bt]}" scope="page"></c:set>


        <c:choose>
            <c:when test="${ !isFavChasing }">
                <c:set var="T1" value="${teamOne}" scope="page"></c:set>
                <c:set var="T2" value="${teamTwo}" scope="page"></c:set>

                <c:set var="X_A_bt" value="${requestScope[param.A_bt]}" scope="page"></c:set>
                <c:set var="X_B_bt" value="${requestScope[param.B_bt]}" scope="page"></c:set>
            </c:when>
            <c:when test="${ isFavChasing }">
                <c:set var="T1" value="${teamTwo}" scope="page"></c:set>
                <c:set var="T2" value="${teamOne}" scope="page"></c:set>

                <c:set var="X_A_bt" value="${requestScope[param.B_bt]}" scope="page"></c:set>
                <c:set var="X_B_bt" value="${requestScope[param.A_bt]}" scope="page"></c:set>
            </c:when>
        </c:choose>

        <c:set var="bt5_size" value="6" scope="page"></c:set>

        <c:set var="bt5_r2_start" value="7" scope="page"></c:set>
        <c:set var="bt5_r2_end" value="${bt5_r2_start + bt5_size - 1}" scope="page"></c:set>

        <c:set var="bt5_r3_start" value="${bt5_r2_end + 1}" scope="page"></c:set>
        <c:set var="bt5_r3_end" value="${bt5_r3_start + bt5_size - 1}" scope="page"></c:set>

        <c:set var="bt10_count" value="14" scope="page"></c:set>

        <c:set var="row1_end" value="${bt10_count+1}" scope="page"></c:set>

        <c:set var="row2_start" value="${row1_end + 1}" scope="page"></c:set>
        <c:set var="row2_end" value="${row2_start + bt10_count}" scope="page"></c:set>

        <c:set var="row3_start" value="${row2_end + 1}" scope="page"></c:set>
        <c:set var="row3_end" value="${row3_start + bt10_count}" scope="page"></c:set>

        <c:set var="row4_start" value="${row3_end + 2}" scope="page"></c:set> <!-- Ground start -- NG value-->
        <c:set var="row4_end" value="${row4_start + bt10_count}" scope="page"></c:set>

        <c:set var="row5_start" value="${row4_end + 1}" scope="page"></c:set>
        <c:set var="row5_end" value="${row5_start + bt10_count}" scope="page"></c:set>

        <c:set var="row6_start" value="${row5_end + 1}" scope="page"></c:set>
        <c:set var="row6_end" value="${row6_start + bt10_count}" scope="page"></c:set>

        <c:set var="row7_start" value="${row6_end + 1}" scope="page"></c:set>
        <c:set var="row7_end" value="${row7_start + bt10_count}" scope="page"></c:set>

        <c:set var="row8_start" value="${row7_end + 1}" scope="page"></c:set>
        <c:set var="row8_end" value="${row8_start + bt10_count}" scope="page"></c:set>

        <c:set var="row9_start" value="${row8_end + 1}" scope="page"></c:set>
        <c:set var="row9_end" value="${row9_start + bt10_count}" scope="page"></c:set>



            <table class="table table-bordered BT5">
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
                    <th colspan="${bt5_size + 1}">Batting</th>
                <th>
                <th colspan="${bt5_size + 1}">Chasing</th>
                <th>
                <th colspan="${bt5_size + 1}">Ground</th>
            </tr>
            <tr>
                <td>N: ${X_G_bt.get("N")}</td>
                <c:forEach var="entry" items="${X_A_bt}" begin="1" end="${bt5_size}">
                    <td>
                        ${entry.key.split("---")[1]}
                    </td>
                </c:forEach>

                <td></td>

                <td>N: ${X_B_bt.get("N")}</td>
                <c:forEach var="entry" items="${X_B_bt}" begin="1" end="${bt5_size}">
                    <td>
                        ${entry.key.split("---")[1]}
                    </td>
                </c:forEach>

                <td></td>

                <td>N: ${X_G_bt.get("N")}</td>
                <c:forEach var="entry" items="${X_G_bt}" begin="1"  end="${bt5_size}">
                    <td>
                        ${entry.key.split("---")[1]}
                    </td>
                </c:forEach>
            </tr>
            <tr>
                <td > open </td>
                <c:forEach var="entry" items="${X_A_bt}" begin="1" end="${bt5_size}">
                    <td class="bt-td">
                        ${entry.value}
                    </td>
                </c:forEach>

                <td></td>

                <td>open</td>
                <c:forEach var="entry" items="${X_B_bt}" begin="1" end="${bt5_size}">
                    <td class="bt-td">
                        ${entry.value}
                    </td>
                </c:forEach>

                <td></td>

                <td>open</td>
                <c:forEach var="entry" items="${X_G_bt}" begin="1" end="${bt5_size}">
                    <td class="bt-td">
                        ${entry.value}
                    </td>
                </c:forEach>
            </tr>

            <tr>
                <td>high</td>
                <c:forEach var="entry" items="${X_A_bt}" begin="${bt5_r2_start}" end="${bt5_r2_end}">
                    <td class="bt-td">
                        ${entry.value}
                    </td>
                </c:forEach>

                <td></td>

                <td>high</td>
                <c:forEach var="entry" items="${X_B_bt}" begin="${bt5_r2_start}" end="${bt5_r2_end}">
                    <td class="bt-td">
                        ${entry.value}
                    </td>
                </c:forEach>

                <td></td>

                <td>high</td>
                <c:forEach var="entry" items="${X_G_bt}" begin="${bt5_r2_start}" end="${bt5_r2_end}">
                    <td class="bt-td">
                        ${entry.value}
                    </td>
                </c:forEach>
            </tr>
            
            <tr>
                <td>low</td>
                <c:forEach var="entry" items="${X_A_bt}" begin="${bt5_r3_start}" end="${bt5_r3_end}">
                    <td class="bt-td">
                        ${entry.value}
                    </td>
                </c:forEach>

                <td></td>

                <td>low</td>
                <c:forEach var="entry" items="${X_B_bt}" begin="${bt5_r3_start}" end="${bt5_r3_end}">
                    <td class="bt-td">
                        ${entry.value}
                    </td>
                </c:forEach>

                <td></td>

                <td>low</td>
                <c:forEach var="entry" items="${X_G_bt}" begin="${bt5_r3_start}" end="${bt5_r3_end}">
                    <td class="bt-td">
                        ${entry.value}
                    </td>
                </c:forEach>
            </tr>
            
        </table>

        <table class="table table-bordered BT10">
            <tr>
                <th colspan="30">All</th>
            </tr>
            <colgroup>
                <col>
                <c:forEach var="entry" items="${X_TA_bt}" end="${row1_end}">
                    <col>
                </c:forEach>
            </colgroup>
            <tr>
                <td></td>
                <td>N: ${X_TA_bt.get("N")}</td>
                <c:forEach var="entry" items="${X_TA_bt}" end="${row1_end}" begin="1">
                    <td>
                        ${entry.key.split("---")[1]}
                    </td>
                </c:forEach>
            </tr>
            <tr>
                <td></td>
                <td>open</td>
                <c:forEach var="entry" items="${X_TA_bt}" end="${row1_end}" begin="1">
                    <td class="bt-td">
                        ${entry.value}
                    </td>
                </c:forEach>
            </tr>
            <tr>
                <td></td>
                <td>high</td>
                <c:forEach var="entry" items="${X_TA_bt}" end="${row2_end}" begin="${row2_start}">
                    <td class="bt-td">
                        ${entry.value}
                    </td>
                </c:forEach>
            </tr>
            <tr>
                <td></td>
                <td>low</td>
                <c:forEach var="entry" items="${X_TA_bt}" end="${row3_end}" begin="${row3_start}">
                    <td class="bt-td">
                        ${entry.value}
                    </td>
                </c:forEach>
            </tr>

            <jsp:include page="/groundBacktestRow.jsp">
                <jsp:param name="title" value="Ground 0/5" />
                <jsp:param name="X_T_bt" value="${param.TA_bt}" />
                <jsp:param name="row_start" value="${row4_start}" />
                <jsp:param name="row_end" value="${row4_end}" />
            </jsp:include>

            <jsp:include page="/groundBacktestRow.jsp">
                <jsp:param name="title" value="Ground 1/4" />
                <jsp:param name="X_T_bt" value="${param.TA_bt}" />
                <jsp:param name="row_start" value="${row5_start}" />
                <jsp:param name="row_end" value="${row5_end}" />
            </jsp:include>

            <jsp:include page="/groundBacktestRow.jsp">
                <jsp:param name="title" value="Ground 2/3" />
                <jsp:param name="X_T_bt" value="${param.TA_bt}" />
                <jsp:param name="row_start" value="${row6_start}" />
                <jsp:param name="row_end" value="${row6_end}" />
            </jsp:include>

            <jsp:include page="/groundBacktestRow.jsp">
                <jsp:param name="title" value="Ground 3/2" />
                <jsp:param name="X_T_bt" value="${param.TA_bt}" />
                <jsp:param name="row_start" value="${row7_start}" />
                <jsp:param name="row_end" value="${row7_end}" />
            </jsp:include>

            <jsp:include page="/groundBacktestRow.jsp">
                <jsp:param name="title" value="Ground 4/1" />
                <jsp:param name="X_T_bt" value="${param.TA_bt}" />
                <jsp:param name="row_start" value="${row8_start}" />
                <jsp:param name="row_end" value="${row8_end}" />
            </jsp:include>

            <jsp:include page="/groundBacktestRow.jsp">
                <jsp:param name="title" value="Ground 5/0" />
                <jsp:param name="X_T_bt" value="${param.TA_bt}" />
                <jsp:param name="row_start" value="${row9_start}" />
                <jsp:param name="row_end" value="${row9_end}" />
            </jsp:include>


            <tr>
                <td colspan="30"></td>
            </tr>

        </table>
    </body>
</html>
