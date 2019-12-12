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
                            <c:forEach var="in" items="${hth}">
                                <td name="-Left">
                                    ${in.getWinner()}
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
                                <td name="-2Left">
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>
                            <c:forEach var="in" items="${t_twoBowlFirstY}">
                                <td name="-2Left">
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
                                        <th colspan="${fn:length(t_groundFirst1)}">${t_groundName} First Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="${loop.index}Left">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="${loop.index}Left">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getTotalruns()}
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
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>


                                    </tr>
                                </table>

                            </div>
                        </div>
                    </c:when>
                    <c:when test="${loop.index == 1}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatFirst)}">${teamOne} Batting First Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirst)}">${teamTwo} Bowling First Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1)}">${t_groundName}</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="${loop.index}Left">
                                                ${in.getSixes()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="${loop.index}Left">
                                                ${in.getSixes()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getSixes()}
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
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_teamoneBatFirst)}">${teamOne} Batting First Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirst)}">${teamTwo} Bowling First Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1)}">${t_groundName}</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="${loop.index}Left">
                                                ${in.getFours()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="${loop.index}Left">
                                                ${in.getFours()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getFours()}
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
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="${loop.index}LeftOdd">

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
                                            <td name="${loop.index}Left">
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="${loop.index}Left">
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
                                                    <span class="input-group-text" name="${loop.index}Left" id="${loop.index}Left">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="${loop.index}Left">
                                            </div>
                                            <!--<input type="number" id="${loop.index}Left" >-->
                                        </td>


                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_groundFirst1)}">${t_groundName} First Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="${loop.index}Left">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="${loop.index}Left">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getRuns5wicket()}
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
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_teamoneBatSecond)}">${teamOne} Batting Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlSecond)}">${teamTwo} Bowling Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond1)}">${t_groundName} Secong Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatSecond}">
                                            <td name="${loop.index}Left">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlSecond}">
                                            <td name="${loop.index}Left">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond1}">
                                            <td>
                                                ${in.getTotalruns()}
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
                                        <c:forEach var="in" items="${t_teamoneBatSecond}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlSecond}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>


                                    </tr>
                                </table>

                            </div>
                        </div>
                    </c:when>
                    <c:when test="${loop.index == 1}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatSecond)}">${teamOne} Batting Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlSecond)}">${teamTwo} Bowling Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond1)}">${t_groundName} Second Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatSecond}">
                                            <td name="${loop.index}Left">
                                                ${in.getSixes()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlSecond}">
                                            <td name="${loop.index}Left">
                                                ${in.getSixes()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond1}">
                                            <td>
                                                ${in.getSixes()}
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
                                        <c:forEach var="in" items="${t_teamoneBatSecond}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlSecond}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_teamoneBatSecond)}">${teamOne} Batting Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlSecond)}">${teamTwo} BowlingSecond Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond1)}">${t_groundName} Second Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatSecond}">
                                            <td name="${loop.index}Left">
                                                ${in.getFours()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlSecond}">
                                            <td name="${loop.index}Left">
                                                ${in.getFours()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getFours()}
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
                                        <c:forEach var="in" items="${t_teamoneBatSecond}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlSecond}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_teamoneBatSecond)}">${teamOne} Batting Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlSecond)}">${teamTwo} Bating Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond1X)}">${t_groundName} Second Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatSecond}">
                                            <td name="${loop.index}Left">
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlSecond}">
                                            <td name="${loop.index}Left">
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
                                                    <span class="input-group-text" name="${loop.index}Left" id="${loop.index}Left">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="${loop.index}Left">
                                            </div>
                                            <!--<input type="number" id="${loop.index}Left" >-->
                                        </td>


                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatSecond}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlSecond}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_teamoneBatSecond)}">${teamOne} Batting Second</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlSecond)}">${teamTwo} Bowling Second</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond1)}">${t_groundName} Second Inning</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatSecond}">
                                            <td name="${loop.index}Left">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlSecond}">
                                            <td name="${loop.index}Left">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond1}">
                                            <td>
                                                ${in.getRuns5wicket()}
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
                                        <c:forEach var="in" items="${t_teamoneBatSecond}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlSecond}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_groundFirst1)}">${t_groundName} Third</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="${loop.index}Left">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="${loop.index}Left">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getTotalruns()}
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
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>


                                    </tr>
                                </table>

                            </div>
                        </div>
                    </c:when>
                    <c:when test="${loop.index == 1}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatThird)}">${teamOne}</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlThird)}">${teamTwo}</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1)}">${t_groundName}</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="${loop.index}Left">
                                                ${in.getSixes()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="${loop.index}Left">
                                                ${in.getSixes()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getSixes()}
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
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_teamoneBatThird)}">${teamOne} batting Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlThird)}">${teamTwo} Bowling Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1)}">${t_groundName} Third</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="${loop.index}Left">
                                                ${in.getFours()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="${loop.index}Left">
                                                ${in.getFours()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getFours()}
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
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_teamoneBatThird)}">${teamOne} Batting Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlThird)}">${teamTwo} Bowling Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1X)}">${t_groundName} Third</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="${loop.index}Left">
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="${loop.index}Left">
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
                                                    <span class="input-group-text" name="${loop.index}Left" id="${loop.index}Left">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="${loop.index}Left">
                                            </div>
                                            <!--<input type="number" id="${loop.index}Left" >-->
                                        </td>


                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_groundFirst1)}">${t_groundName} Third</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="${loop.index}Left">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="${loop.index}Left">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getRuns5wicket()}
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
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_teamoneBatFourth)}">${teamOne} Batting Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFourth)}">${teamTwo} Bowling Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1)}">${t_groundName} Fourth</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFourth}">
                                            <td name="${loop.index}Left">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFourth}">
                                            <td name="${loop.index}Left">
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getTotalruns()}
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
                                        <c:forEach var="in" items="${t_teamoneBatFourth}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFourth}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>


                                    </tr>
                                </table>

                            </div>
                        </div>
                    </c:when>
                    <c:when test="${loop.index == 1}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatFourth)}">${teamOne} Batting Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFourth)}">${teamTwo} Bowling Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1)}">${t_groundName} Fourth</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFourth}">
                                            <td name="${loop.index}Left">
                                                ${in.getSixes()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFourth}">
                                            <td name="${loop.index}Left">
                                                ${in.getSixes()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getSixes()}
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
                                        <c:forEach var="in" items="${t_teamoneBatFourth}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFourth}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_teamoneBatFourth)}">${teamOne} Batting Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFourth)}">${teamTwo} Bowling Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1)}">${t_groundName} Fourth</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFourth}">
                                            <td name="${loop.index}Left">
                                                ${in.getFours()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFourth}">
                                            <td name="${loop.index}Left">
                                                ${in.getFours()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getFours()}
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
                                        <c:forEach var="in" items="${t_teamoneBatFourth}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFourth}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_teamoneBatFourth)}">${teamOne} Batting Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFourth)}">${teamTwo} Bowling Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1X)}">${t_groundName} Fourth</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFourth}">
                                            <td name="${loop.index}Left">
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFourth}">
                                            <td name="${loop.index}Left">
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
                                                    <span class="input-group-text" name="${loop.index}Left" id="${loop.index}Left">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="${loop.index}Left">
                                            </div>
                                            <!--<input type="number" id="${loop.index}Left" >-->
                                        </td>


                                    </tr>

                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFourth}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFourth}">
                                            <td name="${loop.index}LeftOdd">

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
                                        <th colspan="${fn:length(t_teamoneBatFourth)}">${teamOne} Batting Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFourth)}">${teamTwo} Bowling Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1)}">${t_groundName} Fourth</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFourth}">
                                            <td name="${loop.index}Left">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFourth}">
                                            <td name="${loop.index}Left">
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1}">
                                            <td>
                                                ${in.getRuns5wicket()}
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
                                        <c:forEach var="in" items="${t_teamoneBatFourth}">
                                            <td name="${loop.index}LeftOdd">

                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFourth}">
                                            <td name="${loop.index}LeftOdd">

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

                var classes = ["-Left","-1Left", "0Left", "1Left", "2Left", "3Left", "4Left", "5Left", "6Left", "1Left2", "3Left2"];

                var i;
                for (i = 0; i < classes.length; i++) {
                    var nam = classes[i];

                    var ind = 0;

                    $('td[name="' + nam + 'Odd"]').each(function (i, obj) {
                        var val = parseInt($('td[name="' + nam + '"]').eq(i).text());




                        var num = 0;
                        var den = 0;

                        $('td[name="' + nam + '"]').each(function () {
                            if (parseInt($(this).text()) == -1) {

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
                    $(this).removeClass("greater")
                    $(this).removeClass("lower")
                    $('#' + name).text("@");
                });


                if (inp.length == 0) {
                    $('td[name="' + name + '"]').each(function () {
                        $(this).removeClass("greater");
                        $(this).removeClass("lower");
                        $('#' + name).text("@");
                    });

                } else {

                    var num = 0;
                    var den = 0;

                    $('td[name="' + name + '"]').each(function () {
                        if (parseInt($(this).text()) == -1) {

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
                    $(this).removeClass("greater")
                    $(this).removeClass("lower")
                    $('#' + name).text("@");
                });


                if (inp.length == 0) {
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
                        if (parseInt($(this).text()) == -1) {

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
