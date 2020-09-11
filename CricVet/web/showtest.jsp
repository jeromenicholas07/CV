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

        <table class=table table-striped>
            
                    <tr class=thead-dark>
                    <th colspan=4>
                    <th colspan=5>1st Inning  
                    <th colspan=5>2nd Inning  
                    <th colspan=5>3rd Inning  
                        <th colspan=5>4th Inning  
                    <th colspan=4> Result 
                    <th colspan="2">Home/Away
                    </tr>
                                <tr class=thead-dark >
                                    <th> ID
                                    <th>Date
                                    <th>Home
                                    <th>Away
                                    <th>Toss
                    
                    
                                    <th>Inning runs 
                                    <th>Fours 
                                    <th>Sixes
                                    <th>1st Wicket
                                    <th>Runs after 5th Wicket
                    
                    
                   
                                    <th>Inning runs
                                    <th>Fours
                                    <th>Sixes
                                    <th>1st Wicket
                                    <th>Runs after 5th Wicket
                    
                  
                                    <th>Inning runs
                                    <th>Fours
                                    <th>Sixes
                                    <th>1st Wicket
                                    <th>Runs after 5th Wicket
                    
                    
                                    <th>Inning runs  
                                    <th>Fours
                                    <th>Sixes
                                    <th>1st Wicket
                                    <th>Runs after 5th Wicket
                    
                    
                    
                                    <th>Home Score
                                    <th>Away Score
                    
                                    
                                    <th>Win/Lose Margin
                                    <th>Ground
                                        
                                    <th>Team at Home
                                    <th>Team at Away
                    
                                </tr>
                <c:forEach var="match" items="${matches}" varStatus="m">
                <tr>
                    <td>
                    <form action="testmatchidDB" method ="POST">
                    <input type="hidden" name ="matchID" value = ${match.getMatchId()}>
                    <input type="submit" value = ${match.getMatchId()} >
                </form> </td>
                    
                    <td>${match.getMatchDate()}
                    <td>${match.getHomeTeam()}
                    <td>${match.getAwayTeam()}
                    <td>${match.getTossWinner()}
                    <td>${match.getInningOne1().getTotalruns()}
                    <td>${match.getInningOne1().getFours()}
                    <td>${match.getInningOne1().getSixes()}
                    <td>${match.getInningOne1().getFirstwicket()}
                    <td>${match.getInningOne1().getRuns5wicket()}
                        
                    <td>${match.getInningTwo1().getTotalruns()}
                    <td>${match.getInningOne1().getFours()}
                    <td>${match.getInningTwo1().getSixes()}
                    <td>${match.getInningTwo1().getFirstwicket()}
                    <td>${match.getInningTwo1().getRuns5wicket()}
                        
                    <td>${match.getInningOne2().getTotalruns()}
                    <td>${match.getInningOne1().getFours()}
                    <td>${match.getInningOne2().getSixes()}
                    <td>${match.getInningOne2().getFirstwicket()}
                    <td>${match.getInningOne2().getRuns5wicket()}
                        
                    <td>${match.getInningTwo2().getTotalruns()}
                    <td>${match.getInningOne1().getFours()}
                    <td>${match.getInningTwo2().getSixes()}
                    <td>${match.getInningTwo2().getFirstwicket()}
                    <td>${match.getInningTwo2().getRuns5wicket()}
                        
                    <td>${match.getHomeScore()}
                    <td>${match.getAwayScore()}
                    <td>${match.getResult()}
                    <td>${match.getGroundName()}

                    <td>${match.getteamathome()}
                    <td>${match.getteamataway()}
                    
                </tr>
                </c:forEach>
        </table>
    </body>
</html>
