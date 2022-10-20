<%-- 
    Document   : edit
    Created on : Aug 29, 2020, 5:00:02 PM
    Author     : ferdi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit match</title>
        <style>
            .vertical-center {
                min-height: 100%;  /* Fallback for browsers do NOT support vh unit */
                min-height: 100vh; /* These two lines are counted as one :-)       */

                display: flex;
                align-items: center;
            }
            .form-query{

                width: 100%;
                max-width: 500px;
                padding: 15px;
                margin: auto;

            }
            .teams{
                display: none;
            }

            .outer {
                display: table;
                position: absolute;
                top: 0;
                left: 0;
                height: 100%;
                width: 100%;
            }

            .middle {
                display: table-cell;
                vertical-align: middle;
            }

            .inner {
                margin-left: auto;
                margin-right: auto;
                width: 400px;
                /*whatever width you want*/
            }
        </style>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
    </head>

    <body class="text-center">
        <nav class="navbar sticky-top navbar-dark bg-dark">
            <a class="navbar-brand" href="#">Edit Match</a>
        </nav>

        <div class="container-fluid">

            <form action = "editDB" method = "POST">
                <div class="row">
                    <input type="hidden" name ="match" value = ${match}>
                    <!--<h3>MATCH DETAILS:</h3>-->
                </div>
                <br>
                <br>
                <div class="row">
                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label1">Match ID</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label1" 
                               name = "matchID" value="${match.getMatchId()}" <c:if test="${not empty match.getMatchId()}"> readonly</c:if>>
                        <c:if test="${not empty match.getMatchId()}">
                            <div class="input-group-append">
                                <span class="input-group-text">
                                    <a href="http://stats.espncricinfo.com/ci/engine/match/${match.getMatchId()}.html" target="_blank">
                                        <svg width="1.2em" height="1.2em" viewBox="0 0 16 16" class="bi bi-link" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M6.354 5.5H4a3 3 0 0 0 0 6h3a3 3 0 0 0 2.83-4H9c-.086 0-.17.01-.25.031A2 2 0 0 1 7 10.5H4a2 2 0 1 1 0-4h1.535c.218-.376.495-.714.82-1z"/>
                                        <path d="M9 5.5a3 3 0 0 0-2.83 4h1.098A2 2 0 0 1 9 6.5h3a2 2 0 1 1 0 4h-1.535a4.02 4.02 0 0 1-.82 1H12a3 3 0 1 0 0-6H9z"/>
                                        </svg>
                                    </a>
                                </span>
                            </div>
                        </c:if>
                    </div>

                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label2">Match Type</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label2"
                               name = "matchType" value="${match.getMatchType()}" required>
                    </div>

                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label3">Batting team</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label3"
                               name = "homeTeam" value="${match.getHomeTeam()}" required>
                    </div>

                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label4">Bowling team</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label4"
                               name = "awayTeam" value="${match.getAwayTeam()}" required>
                    </div>

                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label5">Date</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label5"
                               name = "matchDate" value="${match.getMatchDate()}" required>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="input-group col-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label6">Toss</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label6"
                               name = "tossWinner" value="${match.getTossWinner()}" required>
                    </div>

                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label7">B/C Winner</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label7"
                               name = "BCW" value="${match.getBCW()}" required>
                    </div>

                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label8">Home score</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label8"
                               name = "homeScore" value="${match.getHomeScore()}" required>
                    </div>

                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label9">Away score</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label9"
                               name = "awayScore" value="${match.getAwayScore()}" required>
                    </div>

                    <div class="input-group col-4">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label10">Ground</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label10"
                               name = "groundName" value="${match.getGroundName()}" required>
                    </div>
                </div>
                <br>
                <div class="row">

                    <div class="input-group col-8 mx-auto">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label11">Result</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label11"
                               name = "result" value="${match.getResult()}" required>
                    </div>

                </div>
                <br>
                <br>
                <div class="card">
                    <div class="card-header">
                        <h4>First Inning</h4>
                    </div>
                    <div class="card-body">
                        <div class="row">

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label12">First Over</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label12"
                                       name = "firstOver1" value = ${match.getInningOne().getParams().get(0)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <c:choose>
                                        <c:when test="${match.getMatchType() == 2}">
                                            <span class="input-group-text" id="label13">First 10 Overs</span>
                                        </c:when>
                                        <c:when test="${empty match.getMatchType()}">
                                            <span class="input-group-text" id="label13">First 6/10 Overs</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="input-group-text" id="label13">First 6 Overs</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label13"
                                       name = "first61" value = ${match.getInningOne().getParams().get(1)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <c:choose>
                                        <c:when test="${match.getMatchType() == 2}">
                                            <span class="input-group-text" id="label14">Last 10 Overs</span>
                                        </c:when>
                                        <c:when test="${empty match.getMatchType()}">
                                            <span class="input-group-text" id="label14">Last 5/10 Overs</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="input-group-text" id="label14">Last 5 Overs</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label14"
                                       name = "last51" value = ${match.getInningOne().getParams().get(2)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label15">First Wicket</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label15"
                                       name = "firstWicket1" value = ${match.getInningOne().getParams().get(3)}>
                            </div>

                        </div>
                        <br>
                        <div class="row">
                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label16">Fours</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label16"
                                       name = "fours1" value = ${match.getInningOne().getParams().get(4)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label17">Sixes</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label17"
                                       name = "sixes1" value = ${match.getInningOne().getParams().get(5)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label18">Total Runs</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label18"
                                       name = "totalRuns1" value = ${match.getInningOne().getParams().get(6)}>
                            </div>
                        </div>
                    </div>
                </div>

                <br>
                <br>

                <div class="card">
                    <div class="card-header">
                        <h4>Second Inning</h4>
                    </div>
                    <div class="card-body">
                        <div class="row">

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label19">First Over</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label19"
                                       name = "firstOver2" value = ${match.getInningTwo().getParams().get(0)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <c:choose>
                                        <c:when test="${match.getMatchType() == 2}">
                                            <span class="input-group-text" id="label20">First 10 Overs</span>
                                        </c:when>
                                        <c:when test="${empty match.getMatchType()}">
                                            <span class="input-group-text" id="label20">First 6/10 Overs</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="input-group-text" id="label20">First 6 Overs</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label20"
                                       name = "first62" value = ${match.getInningTwo().getParams().get(1)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <c:choose>
                                        <c:when test="${match.getMatchType() == 2}">
                                            <span class="input-group-text" id="label21">Last 10 Overs</span>
                                        </c:when>
                                        <c:when test="${empty match.getMatchType()}">
                                            <span class="input-group-text" id="label21">Last 5/10 Overs</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="input-group-text" id="label21">Last 5 Overs</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label21"
                                       name = "last52" value = ${match.getInningTwo().getParams().get(2)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label22">First Wicket</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label22"
                                       name = "firstWicket2" value = ${match.getInningTwo().getParams().get(3)}>
                            </div>

                        </div>
                        <br>
                        <div class="row">

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label23">Fours</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label23"
                                       name = "fours2" value = ${match.getInningTwo().getParams().get(4)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label24">Sixes</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label24"
                                       name = "sixes2" value = ${match.getInningTwo().getParams().get(5)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label25">Total Runs</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label25"
                                       name = "totalRuns2" value = ${match.getInningTwo().getParams().get(6)}>
                            </div>
                        </div>
                    </div>
                </div>

                <br>
                <br>

                <center>
                    <c:choose>
                        <c:when test="${empty match.getResult()}">
                            <button type="submit" class="btn btn-lg btn-primary" onclick="">Add Match</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-lg btn-primary">Edit Match</button>
                        </c:otherwise>
                    </c:choose>
                </center>

                <br>
                <br>

            </form>

        </div>
    </body>    
</html>
