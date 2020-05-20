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
                    <h5 class="card-title">Head to head</h5>



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
                    <h5 class="card-title">Form Guide</h5>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(oneBatFirstY)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(twoBowlFirstY)}">${teamTwo}</th>
                            <th>

                            <th>Odds</th>
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
                            <td></td>
                            <td>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="-1Left" id="-1Left">@</span>
                                    </div>
                                    <input type="number" class="form-control" name="-1Left">
                                </div>

                            </td>


                        </tr>












                        <tr>
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

                        </tr>




























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
                            <td></td>

                            <td>
                            </td>


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
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(oneBatFirstY)}">${teamOne}</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirstY)}">${teamTwo}</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirst)}">${groundName} First</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${oneBatFirstY}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoBowlFirstY}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundFirst}">
                                            <td name="${loop.index}Gr">
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
                                        <c:forEach var="in" items="${oneBatFirstY}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoBowlFirstY}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                            
                                        <td>
                                            <c:forEach var="in" items="${groundFirst}">
                                            <td name="${loop.index}GrOdd">
                                                
                                            </td>
                                        </c:forEach>


                                    </tr>
                                </table>

                            </div>
                        </div>
                    </c:when>
                    <c:when test="${loop.index == 4 || loop.index == 5}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(oneFS)}">${teamOne}</th>
                                        <th>
                                        <th colspan="${fn:length(twoFS)}">${teamTwo}</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirst)}">${groundName}</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${oneFS}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoFS}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundFirst}">
                                            <td name="${loop.index}Gr">
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
                                        <c:forEach var="in" items="${oneFS}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoFS}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundFirst}">
                                            <td name="${loop.index}GrOdd">
                                                
                                            </td>
                                        </c:forEach>
                                            

                                    </tr>
                                </table>

                            </div>
                        </div>
                    </c:when>
                    <c:when test="${loop.index == 2}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(oneBatFirstX)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirstX)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirstX)}">${groundName} First</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${oneBatFirstX}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoBowlFirstX}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundFirstX}">
                                            <td name="${loop.index}Gr">
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
                                        <c:forEach var="in" items="${oneBatFirstX}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoBowlFirstX}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>

                                        <td>
                                            <c:forEach var="in" items="${groundFirstX}">
                                            <td name="${loop.index}GrOdd">
                                                
                                            </td>
                                        </c:forEach>


                                    </tr>
                                    
                                    <tr>
                                        <td>Sorted:
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
                                        <c:forEach var="in" items="${ASs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
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
                                <h5 class="card-title">${headers.get(loop.index)}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(oneBatFirstZ)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirstZ)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirstZ)}">${groundName} First</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${oneBatFirstZ}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoBowlFirstZ}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundFirstZ}">
                                            <td name="${loop.index}Gr">
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
                                        <c:forEach var="in" items="${oneBatFirstZ}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoBowlFirstZ}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                            
                                        <td>
                                            <c:forEach var="in" items="${groundFirstZ}">
                                            <td name="${loop.index}GrOdd">
                                                
                                            </td>
                                        </c:forEach>



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
                                    
                                    <tr>
                                        <td>Sorted:
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
                                        <c:forEach var="in" items="${ASs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
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
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(oneBatFirst)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirst)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirst)}">${groundName} First</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${oneBatFirst}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoBowlFirst}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundFirst}">
                                            <td name="${loop.index}Gr">
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
                                        <c:forEach var="in" items="${oneBatFirst}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${twoBowlFirst}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                            
                                        <td>
                                            <c:forEach var="in" items="${groundFirst}">
                                            <td name="${loop.index}GrOdd">
                                                
                                            </td>
                                        </c:forEach>

                                    </tr>
                                    
                                    <tr>
                                        <td>Sorted:
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
                                        <c:forEach var="in" items="${ASs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>

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
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(twoBatSecond)}">${teamTwo} Batting Second</th>
                                        <th>
                                        <th colspan="${fn:length(oneBowlSecond)}">${teamOne} Bowling Second</th>
                                        <th>
                                        <th colspan="${fn:length(groundSecond)}">${groundName} Second</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${twoBatSecond}">
                                            <td name="${loop.index}Left2">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${oneBowlSecond}">
                                            <td name="${loop.index}Left2">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${groundSecond}">
                                            <td name="${loop.index}Gr2">
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
                                        <c:forEach var="in" items="${twoBatSecond}">
                                            <td name="${loop.index}Left2Odd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${oneBowlSecond}">
                                            <td name="${loop.index}Left2Odd">

                                            </td>
                                        </c:forEach>

                                        <td>
                                            <c:forEach var="in" items="${groundSecond}">
                                            <td name="${loop.index}Gr2Odd">
                                                
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td>Sorted:
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
                                        <c:forEach var="in" items="${ASs}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>

                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>
            </c:forEach>

        </div>





        <script type="text/javascript">
            $(document).ready(function () {

                var classes = ["-Left","-1Left", "0Left", "1Left", "2Left", "3Left", "4Left", "5Left", "6Left", 
                    "1Left2", "3Left2", "0Gr", "1Gr", "2Gr", "3Gr", "4Gr", "5Gr", "6Gr", "1Gr2", "3Gr2"];

                var i;
                for (i = 0; i < classes.length; i++) {
                    var nam = classes[i];

                    var ind = 0;

                    $('td[name="' + nam + 'Odd"]').each(function (i, obj) {
                        
                        if($('td[name="' + nam + '"]').eq(i).text().includes("D/L")){
                            return;
                        }
                        
                        var val = parseInt($('td[name="' + nam + '"]').eq(i).text());




                        var num = 0;
                        var den = 1;

                        $('td[name="' + nam + '"]').each(function () {
                            if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {

                            } else if (parseInt($(this).text()) >= parseInt(val)) {
                                num++;
                            } else if (parseInt($(this).text()) < parseInt(val)) {
//                                alert($(this).text() +" :: "+ val);
                                den++;
                            }

                        });

                        $(this).text(den.toString() + "/" + num.toString());





                    });

                }
            });


            $("input").change(function () {

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

                } else {

                    var num = 0;
                    var den = 1;

                    $('td[name="' + name + '"]').each(function () {
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

                }
            });

            $("input").keyup(function () {

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

                } else {
//                    var tds = $('td[name="' + name + '"]');
//
//                    var noOfTds = $('td[name="' + name + '"]');

                    var num = 0;
                    var den = 1;

                    $('td[name="' + name + '"]').each(function () {
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

                }
            });
        </script>

    </body>
</html>
