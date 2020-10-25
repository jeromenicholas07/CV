<%-- 
    Document   : edittest
    Created on : Sep 3, 2020, 5:35:59 PM
    Author     : ferdi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Test match</title>
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
            <a class="navbar-brand" href="#">Edit Test Match</a>
        </nav>

        <div class="container-fluid">
            <form action = "editDB" method = "POST">
                <div class="row">
                    <input type="hidden" name ="match" value = ${match}>
                    <input type="hidden" name ="matchType" value = 1>
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
                            <span class="input-group-text" id="label3">Home team</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label3"
                               name = "homeTeam" value="${match.getHomeTeam()}">
                    </div>

                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label4">Away team</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label4"
                               name = "awayTeam" value="${match.getAwayTeam()}">
                    </div>

                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label5">Date</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label5"
                               name = "matchDate" value="${match.getMatchDate()}">
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="input-group col-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label6">Toss</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label6"
                               name = "tossWinner" value="${match.getTossWinner()}">
                    </div>

                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label7">B/C Winner</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label7"
                               name = "BCW" value="${match.getBCW()}">
                    </div>

                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label8">Home score</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label8"
                               name = "homeScore" value="${match.getHomeScore()}">
                    </div>

                    <div class="input-group col">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label9">Away score</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label9"
                               name = "awayScore" value="${match.getAwayScore()}">
                    </div>

                    <div class="input-group col-4">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label10">Ground</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label10"
                               name = "groundName" value="${match.getGroundName()}">
                    </div>
                </div>
                <br>
                <div class="row">

                    <div class="input-group col mx-auto">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label11">Home-Ground team</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label11"
                               name = "teamathome" value="${match.getteamathome()}">
                    </div>

                    <div class="input-group col-8 mx-auto">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label11">Result</span>
                        </div>
                        <input type="text" class="form-control" aria-describedby="label11"
                               name = "result" value="${match.getResult()}">
                    </div>

                </div>
                <br>
                <br>
                <div class="card">
                    <div class="card-header">
                        <h4>Home First Inning</h4>
                    </div>
                    <div class="card-body">
                        <div class="row">

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label15">First Wicket</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label15"
                                       name = "firstWicket11" value = ${match.getInningOne1().getParams().get(3)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label16">Fours</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label16"
                                       name = "fours11" value = ${match.getInningOne1().getParams().get(1)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label17">Sixes</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label17"
                                       name = "sixes11" value = ${match.getInningOne1().getParams().get(2)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label12">After 5th wicket runs</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label12"
                                       name = "runs511" value = ${match.getInningOne1().getParams().get(4)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label18">Total Runs</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label18"
                                       name = "totalRuns11" value = ${match.getInningOne1().getParams().get(0)}>
                            </div>

                        </div>
                    </div>
                </div>

                <br>
                <div class="card">
                    <div class="card-header">
                        <h4>Away First Inning</h4>
                    </div>
                    <div class="card-body">
                        <div class="row">

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label15">First Wicket</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label15"
                                       name = "firstWicket21" value = ${match.getInningTwo1().getParams().get(3)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label16">Fours</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label16"
                                       name = "fours21" value = ${match.getInningTwo1().getParams().get(1)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label17">Sixes</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label17"
                                       name = "sixes21" value = ${match.getInningTwo1().getParams().get(2)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label12">After 5th wicket runs</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label12"
                                       name = "runs521" value = ${match.getInningTwo1().getParams().get(4)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label18">Total Runs</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label18"
                                       name = "totalRuns21" value = ${match.getInningTwo1().getParams().get(0)}>
                            </div>

                        </div>
                    </div>
                </div>

                <br>
                <div class="card">
                    <div class="card-header">
                        <h4>Home Second Inning</h4>
                    </div>
                    <div class="card-body">
                        <div class="row">

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label15">First Wicket</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label15"
                                       name = "firstWicket12" value = ${match.getInningOne2().getParams().get(3)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label16">Fours</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label16"
                                       name = "fours12" value = ${match.getInningOne2().getParams().get(1)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label17">Sixes</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label17"
                                       name = "sixes12" value = ${match.getInningOne2().getParams().get(2)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label12">After 5th wicket runs</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label12"
                                       name = "runs512" value = ${match.getInningOne2().getParams().get(4)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label18">Total Runs</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label18"
                                       name = "totalRuns12" value = ${match.getInningOne2().getParams().get(0)}>
                            </div>

                        </div>
                    </div>
                </div>

                <br>
                <div class="card">
                    <div class="card-header">
                        <h4>Away Second Inning</h4>
                    </div>
                    <div class="card-body">
                        <div class="row">

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label15">First Wicket</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label15"
                                       name = "firstWicket22" value = ${match.getInningTwo2().getParams().get(3)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label16">Fours</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label16"
                                       name = "fours22" value = ${match.getInningTwo2().getParams().get(1)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label17">Sixes</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label17"
                                       name = "sixes22" value = ${match.getInningTwo2().getParams().get(2)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label12">After 5th wicket runs</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label12"
                                       name = "runs522" value = ${match.getInningTwo2().getParams().get(4)}>
                            </div>

                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label18">Total Runs</span>
                                </div>
                                <input type="number" class="form-control" aria-describedby="label18"
                                       name = "totalRuns22" value = ${match.getInningTwo2().getParams().get(0)}>
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
