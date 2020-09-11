<%-- 
    Document   : edit
    Created on : Aug 29, 2020, 5:00:02 PM
    Author     : ferdi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit DB</title>
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
    
        <form action = "editDB" method = "POST">
         <input type="hidden" name ="match" value = ${match}>
           <h3>MATCH DETAILS:</h3>
        Match ID <input type = "text" name = "matchID" value="${match.getMatchId()}">
         <br />
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
         GROUDNAME : <input type = "text" name = "groundName" value="${match.getGroundName()}">
         <br/> <br /> 
         
         <h3>FIRST INNING:</h3> 
         First Over <input type = "text" name = "firstOver1" value = ${match.getInningOne().getParams().get(0)}> 
         <br />
         First six Overs <input type = "text" name = "first61" value = ${match.getInningOne().getParams().get(1)}>
         <br />
         Last five Overs <input type = "text" name = "last51" value = ${match.getInningOne().getParams().get(2)}>
         <br />
         First Wicket <input type = "text" name = "firstWicket1" value = ${match.getInningOne().getParams().get(3)}>
         <br />
         Fours <input type = "text" name = "fours1" value = ${match.getInningOne().getParams().get(4)}>
         <br />
         Sixes <input type = "text" name = "sixes1" value = ${match.getInningOne().getParams().get(5)} >
         <br />
         Total Runs <input type = "text" name = "totalRuns1" value = ${match.getInningOne().getParams().get(6)}>
         <br />
         Winner <input type = "text" name = "winner1" value = ${match.getInningOne().getParams().get(7)}>
         <br /> <br /> 
         
         <h3>SECOND INNING:</h3>
         First Over <input type = "text" name = "firstOver2" value = ${match.getInningTwo().getParams().get(0)}>
         <br />
         First six Overs <input type = "text" name = "first62" value = ${match.getInningTwo().getParams().get(1)}>
         <br />
         Last five Overs <input type = "text" name = "last52" value = ${match.getInningTwo().getParams().get(2)}>
         <br />
         First Wicket <input type = "text" name = "firstWicket2" value = ${match.getInningTwo().getParams().get(3)}>
         <br />
         Fours <input type = "text" name = "fours2" value = ${match.getInningTwo().getParams().get(4)}>
         <br />
         Sixes <input type = "text" name = "sixes2" value = ${match.getInningTwo().getParams().get(5)} >
         <br />
         Total Runs <input type = "text" name = "totalRuns2" value = ${match.getInningTwo().getParams().get(6)}>
         <br />
         Winner <input type = "text" name = "winner" value = ${match.getInningTwo().getParams().get(7)}>
         <br /> <br>
         
         <center> <input type="submit"> </center>
         
         
         
         
      </form>
         
         <script>
             var date = ${match.getMatchDate()};
             var res = date.split(" ");
             System.out.println(res);
             console.log(res);
             </script>
         
         

      
    
</html>
