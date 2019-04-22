<%-- 
    Document   : groundResults
    Created on : Apr 12, 2019, 11:12:58 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ground Results</title>
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
                <tr><th colspan="20"><h2>${ground}</h2></tr>
                <tr>
                    <th rowspan="2">Date</th>
                    <th rowspan="2">Home</th>
                    <th rowspan="2">Away</th>
                    <th rowspan="2">Toss</th>
                    <th colspan="7">First Innings</th>
                    <th colspan="7">Second Innings</th>
                    <th rowspan="2">Result</th>
                </tr>
                <tr>
                    <c:forEach begin='0' end='6'varStatus="loop">
                        <th>${inningHeaders.get(loop.index)}
                        </c:forEach>
                        <c:forEach begin='0' end='6'varStatus="loop">
                        <th>${inningHeaders.get(loop.index)}
                        </c:forEach>
                </tr>
            </thead>
            <c:forEach var="match" items="${matches}" varStatus="m">
                <tr>
                    <td>${match.getMatchDate()}
                    <td>${match.getHomeTeam()}
                    <td>${match.getAwayTeam()}
                    <td>${match.getTossWinner()}
                    
                        <c:forEach begin='0' end='6'varStatus="loop">
                        <td>${match.getInningOne().getParams().get(loop.index)}
                        </c:forEach>
                        <c:forEach begin='0' end='6'varStatus="loop">
                        <td>${match.getInningTwo().getParams().get(loop.index)}
                        </c:forEach>
                    
                    <td>${match.getResult()}
                </tr>
            </c:forEach>
        </table>
    </body>
</html>