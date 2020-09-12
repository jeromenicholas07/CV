<%-- 
    Document   : edittest
    Created on : Sep 3, 2020, 5:35:59 PM
    Author     : ferdi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EDIT TEST</title>
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
    </head>
 
        <body class="text-center">
        <div class="outer">
            <div class="middle">
                <div class="inner">
        <form action = "editDB" method = "POST">
            <div class="container form-query">
                            <div class="form-group">
                                <div class="col-12">
         <input type="hidden" name ="match" value = ${match}>
         <input type="hidden" name ="matchType" value = 1>
         
             <h3>MATCH DETAILS:<br></h3>
         Match ID <input type = "text" name = "matchID" value="${match.getMatchId()}">
         <br />
                                </div>
         MATCHTYPE <input type = "text" name = "matchType" value="${match.getMatchType()}">
         <br />
         HOMETEAM : <input type = "text" name = "homeTeam" value="${match.getHomeTeam()}">
         <br/>
         AWAYTEAM : <input type = "text" name = "awayTeam" value="${match.getAwayTeam()}">
         <br/>
         MATCHDATE : <input type = "text" name = "matchDate" value="${match.getMatchDate()}">
         <br/>
         TOSSWINNER : <input type = "text" name = "tossWinner" value="${match.getTossWinner()}">
         <br/>
         BATTINGFIRST : <input type = "text" name = "battingFirst" value="${match.getBattingFirst()}">
         <br/>
         HOMESCORE : <input type = "text" name = "homeScore" value="${match.getHomeScore()}">
         <br/>
         AWAYSCORE : <input type = "text" name = "awayScore" value="${match.getAwayScore()}">
         <br/>
         RESULT : <input type = "text" name = "result" value="${match.getResult()}">
         <br/>
         TEAMATHOME : <input type = "text" name = "teamathome" value="${match.getteamathome()}">
         <br/>
         TEAMATAWAY : <input type = "text" name = "teamataway" value="${match.getteamataway()}">
         <br/>
         GROUDNAME : <input type = "text" name = "groundName" value="${match.getGroundName()}">
         <br/>
         
         <h3>Team One First INNING: <br></h3>
         Total Runs <input type = "text" name = "totalRuns11" value = ${match.getInningOne1().getTotalruns()}> 
         <br />
         Sixes <input type = "text" name = "sixes11" value = ${match.getInningOne1().getSixes()}>
         <br />
         Fours <input type = "text" name = "fours11" value = ${match.getInningOne1().getFours()}>
         <br />
         First Wicket <input type = "text" name = "firstWicket11" value = ${match.getInningOne1().getFirstwicket()}>
         <br />
         Runs 5 wicket <input type = "text" name = "runs511" value = ${match.getInningOne1().getRuns5wicket()}>
         <br />
         Winner <input type = "text" name = "winner11" value = ${match.getInningOne1().getWinner()} >
         <br />
         
         
         <h3>Team 2 First INNING: <br></h3>
         Total Runs <input type = "text" name = "totalRuns21" value = ${match.getInningTwo1().getTotalruns()}> 
         <br />
         Sixes <input type = "text" name = "sixes21" value = ${match.getInningTwo1().getSixes()}>
         <br />
         Fours <input type = "text" name = "fours21" value = ${match.getInningTwo1().getFours()}>
         <br />
         First Wicket <input type = "text" name = "firstWicket21" value = ${match.getInningTwo1().getFirstwicket()}>
         <br />
         Runs 5 wicket <input type = "text" name = "runs521" value = ${match.getInningTwo1().getRuns5wicket()}>
         <br />
         Winner <input type = "text" name = "winner21" value = ${match.getInningTwo1().getWinner()} >
         <br />
         
         <h3>Team One Second INNING:<br></h3>
         Total Runs <input type = "text" name = "totalRuns12" value = ${match.getInningOne2().getTotalruns()}> 
         <br />
         Sixes <input type = "text" name = "sixes12" value = ${match.getInningOne2().getSixes()}>
         <br />
         Fours <input type = "text" name = "fours12" value = ${match.getInningOne2().getFours()}>
         <br />
         First Wicket <input type = "text" name = "firstWicket12" value = ${match.getInningOne2().getFirstwicket()}>
         <br />
         Runs 5 wicket <input type = "text" name = "runs512" value = ${match.getInningOne2().getRuns5wicket()}>
         <br />
         Winner <input type = "text" name = "winner12" value = ${match.getInningOne2().getWinner()} >
         <br />
         
         <h3>Team Two Second INNING:<br> </h3>
         Total Runs <input type = "text" name = "totalRuns22" value = ${match.getInningTwo2().getTotalruns()}> 
         <br />
         Sixes <input type = "text" name = "sixes22" value = ${match.getInningTwo2().getSixes()}>
         <br />
         Fours <input type = "text" name = "fours22" value = ${match.getInningTwo2().getFours()}>
         <br />
         First Wicket <input type = "text" name = "firstWicket22" value = ${match.getInningTwo2().getFirstwicket()}>
         <br />
         Runs 5 wicket <input type = "text" name = "runs522" value = ${match.getInningTwo2().getRuns5wicket()}>
         <br />
         Winner <input type = "text" name = "winner22" value = ${match.getInningTwo2().getWinner()} >
         <br />
         <br>
         <center><input type="submit"></center>
         
         
         
         
         </div>
                            </div>
                        </div>
            </div>
        </div>
      </form>
    </body>
</html>
