<%-- 
    Document   : results
    Created on : Mar 3, 2019, 12:25:47 AM
    Author     : DELL
--%>

<%@page import="com.sun.javafx.scene.control.skin.VirtualFlow.ArrayLinkedList"%>
<%@page import="java.util.function.Predicate"%>
<%@page import="javax.servlet.jsp.jstl.core.LoopTagStatus"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Inning"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
            .greater{
                background-color: #99ff99
            }
            .lower{
                background-color: #ff9999
            }
        </style>
        <div class="container-fluid text-center">
            <h1 class="text-center">${teamOne} v/s ${teamTwo}</h1>
            <div class="row">
                <div class="col-12">
                    <h3 class="card-title">Head to head</h3>



                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(hth)}">${teamOne} v/s ${teamTwo}</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${hth}">
                                <td name="-Left">
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>

                            
                        </tr>
                        
                        <tr>
                            <c:forEach var="in" items="${hth}">
                                <td name="-Left">
                                    ${in.getParams().get(7)}
                                </td>
                            </c:forEach>
                            
                        </tr>
                    </table>
                </div>
            </div>




            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">Form Guide</h3>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(oneBatFirstY)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(twoBowlFirstY)}">${teamTwo}</th>
<!--                            <th>

                            <th>Score</th>-->
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${oneBatFirstY}">
                                <td name="-1Left">
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="in" items="${twoBowlFirstY}">
                                <td name="-1Left">
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>
<!--                            <td></td>
                            <td>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="-1Left" id="-1Left">@</span>
                                    </div>
                                    <input type="number" class="form-control" name="-1Left">
                                </div>

                            </td>-->


                        </tr>












<!--                        <tr>
                            <c:forEach var="in" items="${oneBatFirstY}">
                                <td name="-1LeftOdd">

                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="in" items="${twoBowlFirstY}">
                                <td name="-1LeftOdd">

                                </td>
                            </c:forEach>
                            <td colspan="2"></td>

                        </tr>-->







                        <tr>
                            <c:forEach var="in" items="${oneBatFirstY}">
                                <td name="-2Left">
                                    ${in.getParams().get(7)}
                                </td>
                            </c:forEach>
                            <td></td>
                            <c:forEach var="in" items="${twoBowlFirstY}">
                                <td name="-2Left">
                                    ${in.getParams().get(7)}
                                </td>
                            </c:forEach>
<!--                            <td></td>

                            <td>
                            </td>
-->

                        </tr>
                    </table>

                </div>
            </div>

            <h2>First Inning</h2>
            <c:forEach var="h" begin="0" end="6" varStatus="loop">
                <c:choose>
                    <c:when test="${loop.index == 0}">
                        <div class="row" >
                            <div class ="col-12">
                                <h3 class="card-title">${headers.get(loop.index)}</h3>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(oneBatFirstY)}">${teamOne}</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirstY)}">${teamTwo}</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirst)}">${groundName} First</th>
                                        <th>Score</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${oneBatFirstY}">
                                            <td name="${loop.index}Left" class="A">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoBowlFirstY}">
                                            <td name="${loop.index}Left" class="B">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundFirst}">
                                            <td name="${loop.index}Left" class="G">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="${loop.index}Left" id="${loop.index}Left">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="${loop.index}Left">
                                            </div>
                                            <!--<input type="number" id="${loop.index}Left" >-->
                                        </td>


                                    </tr>
                                    <tr>
                                        <%--<c:forEach var="in" items="${oneBatFirstY}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>--%>
                                        <td name="${loop.index}LeftOdd" class="A" colspan="${fn:length(oneBatFirstY)}">
                                        
                                        </td>
                                        <td></td>
                                        <td name="${loop.index}LeftOdd" class="B" colspan="${fn:length(twoBowlFirstY)}">
                                        
                                        </td>
                                        <%--<c:forEach var="in" items="${twoBowlFirstY}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>--%>
                                            
                                        <td></td>
                                        <td name="${loop.index}LeftOdd" class="G" colspan="${fn:length(groundFirst)}">
                                        
                                        </td>
                                        <%--<c:forEach var="in" items="${groundFirst}">
                                            <td name="${loop.index}GrOdd">
                                                
                                            </td>
                                        </c:forEach>--%>


                                    </tr>
                                </table>

                            </div>
                        </div>
                    </c:when>
                    <c:when test="${loop.index == 4 || loop.index == 5}">
                        <div class="row" >
                            <div class ="col-12">
                                <h3 class="card-title">${headers.get(loop.index)}</h3>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(oneFS)}">${teamOne}</th>
                                        <th>
                                        <th colspan="${fn:length(twoFS)}">${teamTwo}</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirst)}">${groundName}</th>
                                        <th>Score</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${oneFS}">
                                            <td name="${loop.index}Left" class="A">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoFS}">
                                            <td name="${loop.index}Left" class="B">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundFirst}">
                                            <td name="${loop.index}Left" class="G">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="${loop.index}Left" id="${loop.index}Left">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="${loop.index}Left">
                                            </div>
                                            <!--<input type="number" id="${loop.index}Left" >-->
                                        </td>


                                    </tr>

                                    <tr>
                                        <%--<c:forEach var="in" items="${oneFS}">--%>
                                            <td colspan="${fn:length(oneFS)}" name="${loop.index}LeftOdd" class="A">

                                            </td>
                                        <%--</c:forEach>--%>
                                        <td>
                                            <%--<c:forEach var="in" items="${twoFS}">--%>
                                            <td colspan="${fn:length(twoFS)}" name="${loop.index}LeftOdd" class="B">

                                            </td>
                                        <%--</c:forEach>--%>
                                        <td>
                                            <%--<c:forEach var="in" items="${groundFirst}">--%>
                                            <td colspan="${fn:length(groundFirst)}" name="${loop.index}LeftOdd" class="G">
                                                
                                            </td>   
                                        <%--</c:forEach>--%>
                                            

                                    </tr>
                                </table>

                            </div>
                        </div>
                    </c:when>
                    <c:when test="${loop.index == 2}">
                        <div class="row" >
                            <div class ="col-12">
                                <h3 class="card-title">${headers.get(loop.index)}</h3>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(oneBatFirstX)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirstX)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirstX)}">${groundName} First</th>
                                        <th>Score</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${oneBatFirstX}">
                                            <td name="${loop.index}Left" class="A">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoBowlFirstX}">
                                            <td name="${loop.index}Left" class="B">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundFirstX}">
                                            <td name="${loop.index}Left" class="G">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="${loop.index}Left" id="${loop.index}Left">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="${loop.index}Left">
                                            </div>
                                            <!--<input type="number" id="${loop.index}Left" >-->
                                        </td>


                                    </tr>

                                    <tr>
                                        <%--<c:forEach var="in" items="${oneBatFirstX}">--%>
                                            <td colspan="${fn:length(oneBatFirstX)}" name="${loop.index}LeftOdd" class="A">

                                            </td>
                                        <%--</c:forEach>--%>
                                        <td>
                                            <%--<c:forEach var="in" items="${twoBowlFirstX}">--%>
                                            <td colspan="${fn:length(twoBowlFirstX)}" name="${loop.index}LeftOdd" class="B">

                                            </td>
                                        <%--</c:forEach>--%>

                                        <td>
                                            <%--<c:forEach var="in" items="${groundFirstX}">--%>
                                            <td colspan="${fn:length(groundFirstX)}" name="${loop.index}LeftOdd" class="G">
                                                
                                            </td>
                                        <%--</c:forEach>--%>


                                    </tr>
                                    <tr class="thead-dark">
                                        <th>Sorted:
                                    </tr>
                                    <tr>
                                    <%
                                        
                                    if(true){
                                        final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                        List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("oneBatFirstX"));
                                        Collections.sort(As, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("As", As);
                                        
                                        List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("twoBowlFirstX"));
                                        Collections.sort(Bs, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("Bs", Bs);

                                        List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("groundFirstX"));
                                        Collections.sort(Gs, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("Gs", Gs);
                                        
                                        List<Inning> ASs = new ArrayList<Inning>();
                                        ASs.addAll(As);
                                        ASs.addAll(Bs);
                                        Collections.sort(ASs, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("ASs", ASs);
                                        
                                    }
                                    %>
                                        <c:forEach var="in" items="${As}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Bs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Gs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td colspan="30">
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${ASs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                        
                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(oneBatFirstX_bt)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirstX_bt)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirstX_bt)}">${groundName} First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneBatFirstX_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twoBowlFirstX_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundFirstX_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneBatFirstX_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twoBowlFirstX_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundFirstX_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                               <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twoA_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twoA_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twoA_bt}">
                                            <td>
                                                ${twoA_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twoB_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twoB_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twoB_bt}">
                                            <td>
                                                ${twoB_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>
                                
                            </div>
                        </div>

                    </c:when>
                    <c:when test="${loop.index==6}">
                        <div class="row" >
                            <div class ="col-12">
                                <h3 class="card-title">${headers.get(loop.index)}</h3>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(oneBatFirstZ)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirstZ)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirstZ)}">${groundName} First</th>
                                        <th>Score</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${oneBatFirstZ}">
                                            <td name="${loop.index}Left" class="A">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${twoBowlFirstZ}">
                                            <td name="${loop.index}Left" class="B">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${groundFirstZ}">
                                            <td name="${loop.index}Left" class="G">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="${loop.index}Left" id="${loop.index}Left">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="${loop.index}Left">
                                            </div>
                                            <!--<input type="number" id="${loop.index}Left" >-->
                                        </td>


                                    </tr>

                                    <tr>
                                        <%--<c:forEach var="in" items="${oneBatFirstZ}">--%>
                                            <td colspan="${fn:length(oneBatFirstZ)}" name="${loop.index}LeftOdd" class="A">

                                            </td>
                                        <%--</c:forEach>--%>
                                        <td>
                                            <%--<c:forEach var="in" items="${twoBowlFirstZ}">--%>
                                            <td colspan="${fn:length(twoBowlFirstZ)}" name="${loop.index}LeftOdd" class="B">

                                            </td>
                                        <%--</c:forEach>--%>
                                            
                                        <td>
                                            <%--<c:forEach var="in" items="${groundFirstZ}">--%>
                                            <td colspan="${fn:length(groundFirstZ)}" name="${loop.index}LeftOdd" class="G">
                                                
                                            </td>
                                        <%--</c:forEach>--%>



                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${oneBatFirstZ}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index +1)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoBowlFirstZ}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index +1)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundFirstZ}">
                                            <td>
                                                ${in.getParams().get(loop.index +1)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                        </td>


                                    </tr>
                                    
                                    <tr class="thead-dark">
                                        <th>Sorted:
                                    </tr>
                                    <tr>
                                    <%
                                        
                                    if(true){
                                        final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                        List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("oneBatFirstZ"));
                                        As.removeIf(new Predicate<Inning>() {
                                            @Override
                                            public boolean test(Inning t) {
                                                return t.getParams().get(ind).contains("D/L");
                                            }
                                        });
                                        Collections.sort(As, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("As", As);
                                        
                                        List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("twoBowlFirstZ"));
                                        Bs.removeIf(new Predicate<Inning>() {
                                            @Override
                                            public boolean test(Inning t) {
                                                return t.getParams().get(ind).contains("D/L");
                                            }
                                        });
                                        Collections.sort(Bs, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("Bs", Bs);

                                        List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("groundFirstZ"));
                                        Gs.removeIf(new Predicate<Inning>() {
                                            @Override
                                            public boolean test(Inning t) {
                                                return t.getParams().get(ind).contains("D/L");
                                            }
                                        });
                                        Collections.sort(Gs, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("Gs", Gs);
                                        
                                        List<Inning> ASs = new ArrayList<Inning>();
                                        ASs.addAll(As);
                                        ASs.addAll(Bs);
                                        Collections.sort(ASs, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("ASs", ASs);
                                    }
                                    %>
                                        <c:forEach var="in" items="${As}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Bs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Gs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td colspan="30">
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${ASs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                        
                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(oneBatFirstZ_bt)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirstZ_bt)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirstZ_bt)}">${groundName} First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneBatFirstZ_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twoBowlFirstZ_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundFirstZ_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneBatFirstZ_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twoBowlFirstZ_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundFirstZ_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${totA_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${totA_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${totA_bt}">
                                            <td>
                                                ${totA_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${totB_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${totB_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${totB_bt}">
                                            <td>
                                                ${totB_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>

                    </c:when>
                    <c:otherwise>
                        <div class="row" >
                            <div class ="col-12">
                                <h3 class="card-title">${headers.get(loop.index)}</h3>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(oneBatFirst)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirst)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirst)}">${groundName} First</th>
                                        <th>Score</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${oneBatFirst}">
                                            <td name="${loop.index}Left" class="A">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoBowlFirst}">
                                            <td name="${loop.index}Left" class="B">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundFirst}">
                                            <td name="${loop.index}Left" class="G">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="${loop.index}Left" id="${loop.index}Left">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="${loop.index}Left">
                                            </div>
                                            <!--<input type="number" id="${loop.index}Left" >-->
                                        </td>


                                    </tr>
                                    <tr>
                                        <%--<c:forEach var="in" items="${oneBatFirst}">--%>
                                            <td colspan="${fn:length(oneBatFirst)}" name="${loop.index}LeftOdd" class="A">

                                            </td>
                                        <%--</c:forEach>--%>
                                        <td>
                                            <%--<c:forEach var="in" items="${twoBowlFirst}">--%>
                                            <td colspan="${fn:length(twoBowlFirst)}" name="${loop.index}LeftOdd" class="B">

                                            </td>
                                        <%--</c:forEach>--%>
                                            
                                        <td>
                                            <%--<c:forEach var="in" items="${groundFirst}">--%>
                                            <td colspan="${fn:length(groundFirst)}" name="${loop.index}LeftOdd" class="G">
                                                
                                            </td>
                                        <%--</c:forEach>--%>

                                    </tr>
                                    
                                    <tr class="thead-dark">
                                        <th>Sorted:
                                    </tr>
                                    <tr>
                                    <%
                                        
                                    if(true){
                                        final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                        List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("oneBatFirst"));
                                        Collections.sort(As, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("As", As);
                                        
                                        List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("twoBowlFirst"));
                                        Collections.sort(Bs, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("Bs", Bs);

                                        List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("groundFirst"));
                                        Collections.sort(Gs, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("Gs", Gs);
                                        
                                        List<Inning> ASs = new ArrayList<Inning>();
                                        ASs.addAll(As);
                                        ASs.addAll(Bs);
                                        Collections.sort(ASs, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("ASs", ASs);
                                        
                                    }
                                    %>
                                        <c:forEach var="in" items="${As}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Bs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Gs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${ASs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                            <c:if test="${loop.index == 1}">
                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(oneBatFirst1_bt)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirst1_bt)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirst1_bt)}">${groundName} First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneBatFirst1_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twoBowlFirst1_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundFirst1_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneBatFirst1_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twoBowlFirst1_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundFirst1_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneA_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneA_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneA_bt}">
                                            <td>
                                                ${oneA_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneB_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneB_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneB_bt}">
                                            <td>
                                                ${oneB_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>
                            </c:if>
                                        
                            <c:if test="${loop.index == 3}">
                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(oneBatFirst3_bt)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirst3_bt)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirst3_bt)}">${groundName} First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneBatFirst3_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twoBowlFirst3_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundFirst3_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneBatFirst3_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twoBowlFirst3_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundFirst3_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${threeA_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${threeA_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${threeA_bt}">
                                            <td>
                                                ${threeA_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${threeB_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${threeB_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${threeB_bt}">
                                            <td>
                                                ${threeB_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>
                            </c:if>

                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <h2>Second Inning</h2>




















            <c:forEach var="h" begin="0" end="6" varStatus="loop">
                <c:choose>
                    <c:when test="${loop.index == 1 || loop.index == 3}">
                        <div class="row" >
                            <div class ="col-12">
                                <h3 class="card-title">${headers.get(loop.index)}</h3>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(twoBatSecond)}">${teamTwo} Batting Second</th>
                                        <th>
                                        <th colspan="${fn:length(oneBowlSecond)}">${teamOne} Bowling Second</th>
                                        <th>
                                        <th colspan="${fn:length(groundSecond)}">${groundName} Second</th>
                                        <th>Score</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${twoBatSecond}">
                                            <td name="${loop.index}Left2" class="A">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${oneBowlSecond}">
                                            <td name="${loop.index}Left2" class="B">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundSecond}">
                                            <td name="${loop.index}Left2"  class="G">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="${loop.index}Left2" id="${loop.index}Left2">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="${loop.index}Left2">
                                            </div>
                                            <!--<input type="number" id="${loop.index}Left" >-->
                                        </td>


                                    </tr>
                                    <tr>
                                        <%--<c:forEach var="in" items="${twoBatSecond}">--%>
                                            <td colspan="${fn:length(twoBatSecond)}" name="${loop.index}Left2Odd"  class="A">

                                            </td>
                                        <%--</c:forEach>--%>
                                        <td>
                                            <%--<c:forEach var="in" items="${oneBowlSecond}">--%>
                                            <td colspan="${fn:length(oneBowlSecond)}" name="${loop.index}Left2Odd" class="B">

                                            </td>
                                        <%--</c:forEach>--%>

                                        <td>
                                            <%--<c:forEach var="in" items="${groundSecond}">--%>
                                            <td colspan="${fn:length(groundSecond)}" name="${loop.index}Left2Odd"  class="G">
                                                
                                            </td>
                                        <%--</c:forEach>--%>
                                    </tr>
                                    
                                    <tr class="thead-dark">
                                        <th >Sorted:
                                    </tr>
                                    <tr>
                                    <%
                                        
                                    if(true){
                                        final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                        List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("twoBatSecond"));
                                        Collections.sort(As, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("As", As);
                                        
                                        List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("oneBowlSecond"));
                                        Collections.sort(Bs, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("Bs", Bs);

                                        List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("groundSecond"));
                                        Collections.sort(Gs, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("Gs", Gs);
                                        
                                        List<Inning> ASs = new ArrayList<Inning>();
                                        ASs.addAll(As);
                                        ASs.addAll(Bs);
                                        Collections.sort(ASs, new Comparator<Inning>() {
                                            @Override
                                            public int compare(Inning o1, Inning o2) {
                                                return Integer.parseInt(o1.getParams().get(ind)) 
                                                        - Integer.parseInt(o2.getParams().get(ind));
                                            }
                                        });
                                        pageContext.setAttribute("ASs", ASs);
                                        
                                    }
                                    %>
                                        <c:forEach var="in" items="${As}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Bs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Gs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${ASs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>

                                        
                                        
                            <c:if test="${loop.index == 1}">
                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(twoBatSecond1_bt)}">${teamTwo} Batting Second</th>
                                        <th>
                                        <th colspan="${fn:length(oneBowlSecond1_bt)}">${teamOne} Bowling Second</th>
                                        <th>
                                        <th colspan="${fn:length(groundSecond1_bt)}">${groundName} Second</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twoBatSecond1_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${oneBowlSecond1_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundSecond1_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twoBatSecond1_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${oneBowlSecond1_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundSecond1_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamTwo} Batting Second</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneSecondA_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneSecondA_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneSecondA_bt}">
                                            <td>
                                                ${oneSecondA_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamOne} Bowling Second</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneSecondB_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneSecondB_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneSecondB_bt}">
                                            <td>
                                                ${oneSecondB_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>
                            </c:if>
                                        
                            <c:if test="${loop.index == 3}">
                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(twoBatSecond3_bt)}">${teamTwo} Batting Second</th>
                                        <th>
                                        <th colspan="${fn:length(oneBowlSecond3_bt)}">${teamOne} Bowling Second</th>
                                        <th>
                                        <th colspan="${fn:length(groundSecond3_bt)}">${groundName} Second</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twoBatSecond3_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${oneBowlSecond3_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundSecond3_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twoBatSecond3_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${oneBowlSecond3_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundSecond3_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${threeSecondA_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${threeSecondA_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${threeSecondA_bt}">
                                            <td>
                                                ${threeSecondA_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${threeSecondB_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${threeSecondB_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${threeSecondB_bt}">
                                            <td>
                                                ${threeSecondB_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>
                            </c:if>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>
            </c:forEach>

        </div>





        <script type="text/javascript">
//            $(document).ready(function () {
//
//                var classes = ["-Left","-1Left", "0Left", "1Left", "2Left", "3Left", "4Left", "5Left", "6Left", 
//                    "1Left2", "3Left2", "0Gr", "1Gr", "2Gr", "3Gr", "4Gr", "5Gr", "6Gr", "1Gr2", "3Gr2"];
//
//                var i;
//                for (i = 0; i < classes.length; i++) {
//                    var nam = classes[i];
//
//                    var ind = 0;
//
//                    $('td[name="' + nam + 'Odd"]').each(function (i, obj) {
//                        
//                        if($('td[name="' + nam + '"]').eq(i).text().includes("D/L")){
//                            return;
//                        }
//                        
//                        var val = parseInt($('td[name="' + nam + '"]').eq(i).text());
//
//
//
//
//                        var num = 0;
//                        var den = 1;
//
//                        $('td[name="' + nam + '"]').each(function () {
//                            if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {
//
//                            } else if (parseInt($(this).text()) >= parseInt(val)) {
//                                num++;
//                            } else if (parseInt($(this).text()) < parseInt(val)) {
////                                alert($(this).text() +" :: "+ val);
//                                den++;
//                            }
//
//                        });
//
//                        $(this).text(den.toString() + "/" + num.toString());
//
//
//
//
//
//                    });
//
//                }
//            });


            $("input").on('input', function () {

                var inp = $(this).val();
                
                var name = $(this).attr("name");

                $('td[name="' + name + '"]').each(function () {
                    $(this).removeClass("greater");
                    $(this).removeClass("lower");
                    $('#' + name).text("@");
                });


                if (inp.length === 0) {
                    $('td[name="' + name + '"]').each(function () {
                        $(this).removeClass("greater");
                        $(this).removeClass("lower");
                        $('#' + name).text("@");
                    });
                    $('td[name="' + name + 'Odd"][class="A"]').text("");
                    $('td[name="' + name + 'Odd"][class="B"]').text("");
                    $('td[name="' + name + 'Odd"][class="G"]').text("");

                } else {

                    var num = 0;
                    var den = 1;

                    $('td[name="' + name + '"].A,td[name="' + name + '"].B').each(function () {
                        if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {

                        } else if (parseInt($(this).text()) >= parseInt(inp)) {
                            $(this).addClass("greater");
                            num++;
                        } else if (parseInt($(this).text()) < parseInt(inp)) {
//                            alert($(this).text() +" :: "+ inp);
                            $(this).addClass("lower");
                            den++;
                        }

                    });

                    $('#' + name).text(den.toString() + "/" + num.toString());
                    
                    num = 0;
                    den = 1;

                    $('td[name="' + name + '"].A').each(function () {
                        if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {

                        } else if (parseInt($(this).text()) >= parseInt(inp)) {
                            num++;
                        } else if (parseInt($(this).text()) < parseInt(inp)) {
                            den++;
                        }

                    });

                    $('td[name="' + name + 'Odd"][class="A"]').text(den.toString() + "/" + num.toString());
                    
                    
                    
                    num = 0;
                    den = 1;

                    $('td[name="' + name + '"].B').each(function () {
                        if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {

                        } else if (parseInt($(this).text()) >= parseInt(inp)) {
                            num++;
                        } else if (parseInt($(this).text()) < parseInt(inp)) {
                            den++;
                        }

                    });

                    $('td[name="' + name + 'Odd"][class="B"]').text(den.toString() + "/" + num.toString());
                    
                    
                    
                    
                    num = 0;
                    den = 1;

                    $('td[name="' + name + '"][class="G"]').each(function () {
                        if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {

                        } else if (parseInt($(this).text()) >= parseInt(inp)) {
                            num++;
                        } else if (parseInt($(this).text()) < parseInt(inp)) {
                            den++;
                        }

                    });

                    $('td[name="' + name + 'Odd"][class="G"]').text(den.toString() + "/" + num.toString());

                }
            });

//            $("input").keyup(function () {
//
//                var inp = $(this).val();
//                
//                var name = $(this).attr("name");
//
//                $('td[name="' + name + '"]').each(function () {
//                    $(this).removeClass("greater");
//                    $(this).removeClass("lower");
//                    $('#' + name).text("@");
//                });
//
//
//                if (inp.length === 0) {
//                    $('td[name="' + name + '"]').each(function () {
//                        $(this).removeClass("greater");
//                        $(this).removeClass("lower");
//                        $('#' + name).text("@");
//                    });
//
//                } else {
////                    var tds = $('td[name="' + name + '"]');
////
////                    var noOfTds = $('td[name="' + name + '"]');
//
//                    var num = 0;
//                    var den = 1;
//
//                    $('td[name="' + name + '"]').each(function () {
//                        if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {
//
//                        } else if (parseInt($(this).text()) >= parseInt(inp)) {
//
//                            $(this).addClass("greater");
//                            num++;
//                        } else if (parseInt($(this).text()) < parseInt(inp)) {
////                            alert($(this).text() +" :: "+ inp);
//                            $(this).addClass("lower");
//                            den++;
//                        }
//
//                    });
//
//                    $('#' + name).text(den.toString() + "/" + num.toString());
//
//                }
//            });
        </script>

    </body>
</html>
