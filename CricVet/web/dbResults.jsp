<%-- 
    Document   : dbResults
    Created on : Mar 13, 2019, 12:23:54 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DB results Page</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>

        <style>
            .al{
                text-align: center;
                text-align: center;
                position: relative;
                top: 50%;
                -ms-transform: translateY(-50%);
                -webkit-transform: translateY(-50%);
                transform: translateY(-50%);


            }
        </style>
    </head>
    <body>

        <table class="table table-bordered text-center">
            <thead class="thead-dark">
                <tr><th colspan="200"><h2>${team}</h2></tr>
                <tr>

                    <th rowspan="2">Match ID </th>
                    <th rowspan="2">Date</th>
                    <th rowspan="2">Team</th>
                    <th rowspan="2">Opp.</th>
                    <th rowspan="2">Toss</th>
                    <th rowspan="2">B/C</th>
                    <th rowspan="2">Result</th>
                    <th colspan="7">Batting</th>
                    <th colspan="7">Bowling</th>
                    <th rowspan="2">Tot. Sixes</th>
                    <th rowspan="2">Delete Match</th>
                    <th rowspan="2"></th>
                    <th rowspan="2">Edit Fav/OHL</th>
                    <th rowspan="2">Favourite </th>
                    <th colspan="3">First Wicket</th>
                    <th colspan="3">First 5/10 overs</th>
                    <th colspan="3">Last 5/10 overs</th>
                    <th colspan="3">Total</th>
                    <th colspan="3">First wicket(Second Inning)</th>
                    <th colspan="3">First 5/10 overs(Second Inning)</th>
                </tr>
                <tr>
                    <c:forEach begin='0' end='6'varStatus="loop">
                        <th>${inningHeaders.get(loop.index)}
                        </c:forEach>
                        <c:forEach begin='0' end='6'varStatus="loop">
                        <th>${inningHeaders.get(loop.index)}
                        </c:forEach>

                        <!--               Number of OHL headers-->
                        <c:forEach begin='0' end='5'varStatus="loop">  
                        <th>Open </th>
                        <th>High </th>
                        <th>Low </th>
                        </c:forEach>
                </tr>
            </thead>
            <c:forEach var="match" items="${matches}" varStatus="m">
                <tr>
                    <%--                  
                                   <td>
                                        <form action="edit.jsp" method ="GET">
                                        <input type="hidden" name ="matchID" value = ${match.getMatchId()}>
                                        <input type="submit" value = ${match.getMatchId()} >
                                    </form> </td>
                    --%>

                    <td>
                        <form action="editMatch" method ="POST">
                            <input type="hidden" name ="matchID" value = ${match.getMatchId()}>
                            <button type="submit" onclick="confirmEdit(this)">${match.getMatchId()}</button>
                        </form>
                    </td>

                    <td>${match.getMatchDate()}
                    <td>${match.getTeam()}
                    <td>${match.getOppTeam()}
                    <td>${match.getToss()}
                    <td>${match.getBorC()}
                    <td>${match.getResult()}
                        <c:forEach begin='0' end='6'varStatus="loop">
                        <td>${match.getOne().getParams().get(loop.index)}
                        </c:forEach>
                        <c:forEach begin='0' end='6'varStatus="loop">
                        <td>${match.getTwo().getParams().get(loop.index)}
                        </c:forEach>
                    <td>${match.getTotalSixes()}
                    <td>
                        <form action="deleteMatch" method ="POST">
                            <input type="hidden" name ="matchID" value = ${match.getMatchId()}>
                            <input type="hidden" name ="isTest" value="false">
                            <input type="hidden" name ="redirUrl" value="hmm">
                            <button type="submit" class="btn btn-light" onclick="confirmDelete(this)">
                                <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-trash-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd" d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5a.5.5 0 0 0-1 0v7a.5.5 0 0 0 1 0v-7z"/>
                                </svg>
                            </button>
                        </form>
                    </td>
                    <th></th>
                    <!--  fav part  -->

                    <td>
                        <form action="editFavourites" method ="POST">
                            <input type="hidden" name ="matchID" value = ${match.getMatchId()}>
                            <input type="hidden" name ="team1" value ="${match.getTeam()}">
                            <input type="hidden" name ="team2" value ="${match.getOppTeam()}">
                            <input type="hidden" name ="redirUrl" value="hmm">

                            <button type="submit" class="btn btn-light">
                                <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-pencil-square" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                </svg>
                            </button>
                        </form>
                    </td>
                    <td>${match.getFavTeam()}

                        <c:if test="${match.getOhl() != null}">

                        <td>${match.getOhl().getFW().getOpen()}
                        <td>${match.getOhl().getFW().getHigh()}
                        <td>${match.getOhl().getFW().getLow()}

                        <td>${match.getOhl().getFX().getOpen()}
                        <td>${match.getOhl().getFX().getHigh()}
                        <td>${match.getOhl().getFX().getLow()}

                        <td>${match.getOhl().getLX().getOpen()}
                        <td>${match.getOhl().getLX().getHigh()}
                        <td>${match.getOhl().getLX().getLow()}

                        <td>${match.getOhl().getT().getOpen()}
                        <td>${match.getOhl().getT().getHigh()}
                        <td>${match.getOhl().getT().getLow()}

                        <td>${match.getOhl().getFW2().getOpen()}
                        <td>${match.getOhl().getFW2().getHigh()}
                        <td>${match.getOhl().getFW2().getLow()}

                        <td>${match.getOhl().getFX2().getOpen()}
                        <td>${match.getOhl().getFX2().getHigh()}
                        <td>${match.getOhl().getFX2().getLow()}

                        </c:if>
                </tr>
            </c:forEach>
        </table>

        <script>
            $(document).ready(function () {
                $('input[name="redirUrl"]').val(location.href);
            });


            function confirmEdit(form) {
                $("<input />").attr("type", "hidden")
                        .attr("name", "isTest")
                        .attr("value", "false")
                        .appendTo(form);
                form.action = 'editMatch';
            }

            function confirmDelete() {
                if (confirm("Are you sure you want to DELETE this match?")) {
                    form.action = 'deleteMatch';
                }
            }
        </script>
    </body>
</html>
