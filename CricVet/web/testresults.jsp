<%-- 
    Document   : results
    Created on : Mar 3, 2019, 12:25:47 AM
    Author     : DELL
--%>

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
            <h1 class="text-center">Home Team: ${hometeam}</h1>
            <h1 class="text-center">Batting team ${teamOne} v/s Bowling Team ${teamTwo}</h1>
            <div class="row">
                <div class="col-12">
                    <h5 class="card-title">Head to head</h5>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(hth)}">${teamOne} v/s ${teamTwo}</th>
                            <th>Odds</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${hth}">
                                <td name="-Left">
                                    ${in.getTotalruns()}
                                </td>
                            </c:forEach>

                            <td>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="-Left" id="-Left">@</span>
                                    </div>
                                    <input type="number" class="form-control" name="-Left">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${hth}">
                                <td name="-LeftOdd">

                                </td>
                            </c:forEach>
                            
                            <td colspan="2"></td>
                        </tr>
                        

                        
                        <tr>
                            <c:forEach var="in" items="${hthmatch}">
                                <td name="-Left">
                                    ${in.getResult()}
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
                            <th colspan="${fn:length(t_oneBatFirstY)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(t_twoBowlFirstY)}">${teamTwo}</th>
                            <th>

                            <th>Odds</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${t_oneBatFirstY}">
                                <td name="-1Left">
                                    ${in.getTotalruns()}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="in" items="${t_twoBowlFirstY}">
                                <td name="-1Left">
                                    ${in.getTotalruns()}
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
                            <c:forEach var="in" items="${t_oneBatFirstY}">
                                <td name="-1LeftOdd">

                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="in" items="${t_twoBowlFirstY}">
                                <td name="-1LeftOdd">

                                </td>
                            </c:forEach>
                            <td colspan="2"></td>

                        </tr>
                        <tr>
                            <c:forEach var="in" items="${t_oneBatFirstY}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>
                            <c:forEach var="in" items="${t_twoBowlFirstY}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>

                            <td>
                            </td>


                        </tr>
                    </table>

                </div>
            </div>
                <div class="row" >
                <div class ="col-12">
                    <h5 class="card-title">Total Fours</h5>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(onetotal)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(twototal)}">${teamTwo}</th>
                            <th>

                            <th>Odds</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${onetotal}">
                                <td name="A">
                                    ${in.getFours()}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="in" items="${twototal}">
                                <td name="A">
                                    ${in.getFours()}
                                </td>
                            </c:forEach>
                            <td></td>
                            
                            <td>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="A" id="A">@</span>
                                    </div>
                                    <input type="number" class="form-control" name="A">
                                </div>

                            </td>
                            
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${onetotal}">
                                <td name="AOdd">

                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="in" items="${twototal}">
                                <td name="AOdd">

                                </td>
                            </c:forEach>
                            <td colspan="2"></td>

                        </tr>


                    </table>

                </div>
            </div>
                            <div class="row" >
                <div class ="col-12">
                    <h5 class="card-title">Total Sixes</h5>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(onetotal)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(twototal)}">${teamTwo}</th>
                            <th>

                            <th>Odds</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${onetotal}">
                                <td name="B">
                                    ${in.getSixes()}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="in" items="${twototal}">
                                <td name="B">
                                    ${in.getSixes()}
                                </td>
                            </c:forEach>
                            <td></td>
                            <td>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="B" id="B">@</span>
                                    </div>
                                    <input type="number" class="form-control" name="B">
                                </div>

                            </td>


                        </tr>
                        <tr>
                            <c:forEach var="in" items="${onetotal}">
                                <td name="BOdd">

                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="in" items="${twototal}">
                                <td name="BOdd">

                                </td>
                            </c:forEach>
                            <td colspan="2"></td>

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
                                        <th colspan="${fn:length(t_teamoneBatFirst)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirst)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1X)}">${t_groundName} First Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="C">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="C">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1X}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                            <td></td>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="C" id="C">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="C">
                                            </div>
                                        </td>


                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="COdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="COdd">

                                            </td>
                                        </c:forEach>
                                            
                                            


                                    </tr>
                                    <tr>
                            <c:forEach var="in" items="${t_teamoneBatFirst}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>
                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                                <td></td>
                                <c:forEach var="in" items="${t_groundFirst1X}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                                
                                
                                 </tr>
                                    

                                </table>

                            </div>
                        </div>
                    </c:when>
                    
                   
                    <c:when test="${loop.index == 3}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatFirst)}">${teamOne} Batting First Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirst)}">${teamTwo} Bowling First inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1X)}">${t_groundName} First Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="D">
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="D">
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1X}">
                                            <td>
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="D" id="D">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="D">
                                            </div>
                                        </td>


                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="DOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="DOdd">

                                            </td>
                                        </c:forEach>



                                    </tr>
                                </table>

                            </div>
                        </div>

                    </c:when>
                    <c:when test="${loop.index==4}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatFirst)}">${teamOne} Batting First Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirst)}">${teamTwo} Bowling First Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1X)}">${t_groundName} First Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="E">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="E">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1X}">
                                            <td>
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="E" id="E">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="E">
                                            </div>
                                        </td>


                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="EOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="EOdd">

                                            </td>
                                        </c:forEach>



                                    </tr>

                                    
                                </table>

                            </div>
                        </div>

                    </c:when>
                    
                </c:choose>
            </c:forEach>
            
            
            <h2>Second Inning</h2>
             <c:forEach var="h" begin="0" end="6" varStatus="loop">
                <c:choose>
                    <c:when test="${loop.index == 0}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamtwoBatSecond)}">${teamTwo} Batting Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlSecond)}">${teamOne} Bowling Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond1X)}">${t_groundName} Second Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatSecond}">
                                            <td name="F">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlSecond}">
                                            <td name="F">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond1X}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="F" id="F">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="F">
                                            </div>
                                            <!--<input type="number" id="${loop.index}Left" >-->
                                        </td>


                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatSecond}">
                                            <td name="FOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlSecond}">
                                            <td name="FOdd">

                                            </td>
                                        </c:forEach>


                                    </tr>
                                    <tr>
                            <c:forEach var="in" items="${t_teamtwoBatSecond}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>
                            <c:forEach var="in" items="${t_teamoneBowlSecond}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                                <td></td>
                                <c:forEach var="in" items="${t_groundSecond1X}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            
                                 </tr>
                                </table>

                            </div>
                        </div>
                    </c:when>
                    
                    
                    <c:when test="${loop.index == 3}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamtwoBatSecond)}">${teamTwo} Batting Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlSecond)}">${teamOne} Bowling Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond1X)}">${t_groundName} Second Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatSecond}">
                                            <td name="G">
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlSecond}">
                                            <td name="G">
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond1X}">
                                            <td>
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="G" id="G">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="G">
                                            </div>
                                        </td>


                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatSecond}">
                                            <td name="GOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlSecond}">
                                            <td name="GOdd">

                                            </td>
                                        </c:forEach>



                                    </tr>
                                </table>

                            </div>
                        </div>

                    </c:when>
                    <c:when test="${loop.index==4}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamtwoBatSecond)}">${teamTwo} Batting Second</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlSecond)}">${teamOne} Bowling Second</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond1X)}">${t_groundName} Second Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatSecond}">
                                            <td name="H">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlSecond}">
                                            <td name="H">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond1X}">
                                            <td>
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="H" id="H">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="H">
                                            </div>
                                        </td>


                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatSecond}">
                                            <td name="HOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlSecond}">
                                            <td name="HOdd">

                                            </td>
                                        </c:forEach>

                                    </tr>

                                    
                                </table>

                            </div>
                        </div>

                    </c:when>
                    
                </c:choose>
            </c:forEach>
            
            <h2>Third Inning</h2>
            <c:forEach var="h" begin="0" end="6" varStatus="loop">
                <c:choose>
                    <c:when test="${loop.index == 0}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatThird)}">${teamOne} Batting Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlThird)}">${teamTwo} Bowling Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst2X)}">${t_groundName} Third</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="I">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="I">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst2X}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="I" id="I">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="I">
                                            </div>
                                        </td>


                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="IOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="IOdd">

                                            </td>
                                        </c:forEach>


                                    </tr>
                                    <tr>
                            <c:forEach var="in" items="${t_teamoneBatThird}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>
                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>
                            
                                <c:forEach var="in" items="${t_groundFirst2X}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>

                            <td>
                            </td>


                                 </tr>
                                </table>

                            </div>
                        </div>
                    </c:when>
                    
                    <c:when test="${loop.index == 3}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatThird)}">${teamOne} Batting Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlThird)}">${teamTwo} Bowling Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst2X)}">${t_groundName} Third</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="J">
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="J">
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst2X}">
                                            <td>
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="J" id="J">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="J">
                                            </div>
                                        </td>


                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="JOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="JOdd">

                                            </td>
                                        </c:forEach>



                                    </tr>
                                </table>

                            </div>
                        </div>

                    </c:when>
                    <c:when test="${loop.index==4}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatThird)}">${teamOne} Batting Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlThird)}">${teamTwo} Bowling Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst2X)}">${t_groundName} Third</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="K">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="K">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst2X}">
                                            <td>
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="K" id="K">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="K">
                                            </div>
                                            <!--<input type="number" id="K" >-->
                                        </td>


                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="KOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="KOdd">

                                            </td>
                                        </c:forEach>



                                    </tr>

                                    
                                </table>

                            </div>
                        </div>

                    </c:when>
                    
                </c:choose>
            </c:forEach>
            
            <h2>Fourth Inning</h2>
            <c:forEach var="h" begin="0" end="6" varStatus="loop">
                <c:choose>
                    <c:when test="${loop.index == 0}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamtwoBatFourth)}">${teamTwo} Batting Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlFourth)}">${teamOne} Bowling Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond2X)}">${t_groundName} Fourth</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatFourth}">
                                            <td name="L">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlFourth}">
                                            <td name="L">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond2X}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="L" id="L">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="L">
                                            </div>
                                            <!--<input type="number" id="L" >-->
                                        </td>


                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatFourth}">
                                            <td name="LOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlFourth}">
                                            <td name="LOdd">

                                            </td>
                                        </c:forEach>


                                    </tr>
                                    <tr>
                            <c:forEach var="in" items="${t_teamtwoBatFourth}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>
                            <c:forEach var="in" items="${t_teamoneBowlFourth}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                                <td></td>
                            
                                <c:forEach var="in" items="${t_groundSecond2X}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            
                                 </tr>
                                </table>

                            </div>
                        </div>
                    </c:when>
                    
                    
                    <c:when test="${loop.index == 3}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamtwoBatFourth)}">${teamTwo} Batting Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlFourth)}">${teamOne} Bowling Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond2X)}">${t_groundName} Fourth</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatFourth}">
                                            <td name="M">
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlFourth}">
                                            <td name="M">
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond2X}">
                                            <td>
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="M" id="M">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="M">
                                            </div>
                                            <!--<input type="number" id="M" >-->
                                        </td>


                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatFourth}">
                                            <td name="MOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlFourth}">
                                            <td name="MOdd">

                                            </td>
                                        </c:forEach>



                                    </tr>
                                </table>

                            </div>
                        </div>

                    </c:when>
                    <c:when test="${loop.index==4}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamtwoBatFourth)}">${teamTwo} Batting Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlFourth)}">${teamOne} Bowling Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond2X)}">${t_groundName} Fourth</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatFourth}">
                                            <td name="N">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlFourth}">
                                            <td name="N">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond2X}">
                                            <td>
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="N" id="N">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="N">
                                            </div>
                                            <!--<input type="number" id="N" >-->
                                        </td>


                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatFourth}">
                                            <td name="NOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlFourth}">
                                            <td name="NOdd">

                                            </td>
                                        </c:forEach>



                                    </tr>

                                    
                                </table>

                            </div>
                        </div>

                    </c:when>
                    
                </c:choose>
            </c:forEach>
        </div>





        <script type="text/javascript">
            $(document).ready(function () {

                var classes = ["-Left","-1Left","A","B","C","D","E","F","G","H","I","J","K","L","M","N" ];

                var i;
                for (i = 0; i < classes.length; i++) {
                    var nam = classes[i];

                    var ind = 0;

                    $('td[name="' + nam + 'Odd"]').each(function (i, obj) {
                        var val = parseInt($('td[name="' + nam + '"]').eq(i).text());




                        var num = 0;
                        var den = 0;

                        $('td[name="' + nam + '"]').each(function () {
                            if (parseInt($(this).text()) === -1) {

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
                    var den = 0;

                    $('td[name="' + name + '"]').each(function () {
                        if (parseInt($(this).text()) === -1) {

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
                    var den = 0;

                    $('td[name="' + name + '"]').each(function () {
                        if (parseInt($(this).text()) === -1) {

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
