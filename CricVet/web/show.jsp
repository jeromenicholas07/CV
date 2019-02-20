<%-- 
    Document   : show
    Created on : Feb 19, 2019, 11:02:45 PM
    Author     : DELL
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result</title>
    </head>
    <body>
        <h1></h1>
 
        <table align="center">
            <thead align="center"><h3>${teamName1}<h3></thead>
            <tr> 
        <table> <th>Batting First Stats</th>
            <th>Chasing Stats</th>
            <tr>
                <td>
        <table border="1px">
          
            <tr>
                <td>First Over</td>
        <c:forEach var="temp" items="${batRecords_In1_1}">
            <td>${temp.getFirstOver()}</td>
        </c:forEach>
             </tr>
        <tr>
                <td>First Five Overs</td>
        <c:forEach var="temp" items="${batRecords_In1_1}">
            <td>${temp.getFirstFiveOvers()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>First Wicket Fall</td>
        <c:forEach var="temp" items="${batRecords_In1_1}">
            <td>${temp.getFirstWicket()}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Last FIve Overs</td>
        <c:forEach var="temp" items="${batRecords_In1_1}">
            <td>${temp.getLastFiveOvers()}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Total Runs</td>
        <c:forEach var="temp" items="${runScoredBat_In1_1}">
            <td>${temp}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Fours</td>
        <c:forEach var="temp" items="${batRecords_In1_1}">
            <td>${temp.getFours()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>Sixes</td>
        <c:forEach var="temp" items="${batRecords_In1_1}">
            <td>${temp.getSixes()}</td>
        </c:forEach>
             </tr>   
          
        </table>
        </td>
              <td>
        <table border="1px">
          
            <tr>
                <td>First Over</td>
        <c:forEach var="temp" items="${batRecords_In2_1}">
            <td>${temp.getFirstOver()}</td>
        </c:forEach>
             </tr>
        <tr>
                <td>First Five Overs</td>
        <c:forEach var="temp" items="${batRecords_In2_1}">
            <td>${temp.getFirstFiveOvers()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>First Wicket Fall</td>
        <c:forEach var="temp" items="${batRecords_In2_1}">
            <td>${temp.getFirstWicket()}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Last FIve Overs</td>
        <c:forEach var="temp" items="${batRecords_In2_1}">
            <td>${temp.getLastFiveOvers()}</td>
        </c:forEach>
             </tr>
             <tr>
             <td>Total Runs</td>
        <c:forEach var="temp" items="${runScoredBat_In2_1}">
            <td>${temp}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Fours</td>
        <c:forEach var="temp" items="${batRecords_In2_1}">
            <td>${temp.getFours()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>Sixes</td>
        <c:forEach var="temp" items="${batRecords_In2_1}">
            <td>${temp.getSixes()}</td>
        </c:forEach>
             </tr>   
          
        </table>
        </td>
          
            </tr>
        </table>
            </tr>
            <tr> 
        <table> <th>Bowling First Stats</th>
            <th>Defending Stats</th>
            <tr>
                <td>
        <table border="1px">
          
            <tr>
                <td>First Over</td>
        <c:forEach var="temp" items="${bowlRecords_In1_1}">
            <td>${temp.getFirstOver()}</td>
        </c:forEach>
             </tr>
        <tr>
                <td>First Five Overs</td>
        <c:forEach var="temp" items="${bowlRecords_In1_1}">
            <td>${temp.getFirstFiveOvers()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>First Wicket Fall</td>
        <c:forEach var="temp" items="${bowlRecords_In1_1}">
            <td>${temp.getFirstWicket()}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Last FIve Overs</td>
        <c:forEach var="temp" items="${bowlRecords_In1_1}">
            <td>${temp.getLastFiveOvers()}</td>
        </c:forEach>
             </tr>
             <tr>
             <td>Total Runs</td>
        <c:forEach var="temp" items="${runGivenBowl_In1_1}">
            <td>${temp}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Fours</td>
        <c:forEach var="temp" items="${bowlRecords_In1_1}">
            <td>${temp.getFours()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>Sixes</td>
        <c:forEach var="temp" items="${bowlRecords_In1_1}">
            <td>${temp.getSixes()}</td>
        </c:forEach>
             </tr>   
          
        </table>
        </td>
              <td>
        <table border="1px">
          
            <tr>
                <td>First Over</td>
        <c:forEach var="temp" items="${bowlRecords_In2_1}">
            <td>${temp.getFirstOver()}</td>
        </c:forEach>
             </tr>
        <tr>
                <td>First Five Overs</td>
        <c:forEach var="temp" items="${bowlRecords_In2_1}">
            <td>${temp.getFirstFiveOvers()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>First Wicket Fall</td>
        <c:forEach var="temp" items="${bowlRecords_In2_1}">
            <td>${temp.getFirstWicket()}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Last FIve Overs</td>
        <c:forEach var="temp" items="${bowlRecords_In2_1}">
            <td>${temp.getLastFiveOvers()}</td>
        </c:forEach>
             </tr>
             <tr>
             <td>Total Runs</td>
        <c:forEach var="temp" items="${runGivenBowl_In2_1}">
            <td>${temp}</td>
        </c:forEach>
             </tr>
             
             <tr>
                <td>Fours</td>
        <c:forEach var="temp" items="${bowlRecords_In2_1}">
            <td>${temp.getFours()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>Sixes</td>
        <c:forEach var="temp" items="${bowlRecords_In2_1}">
            <td>${temp.getSixes()}</td>
        </c:forEach>
             </tr>   
          
        </table>
        </td>
          
            </tr>
        </table>
            </tr>
         </table>
           
            
            <br>
            <br>

          
            
        <table align="center">
            <thead align="center"><h3>${teamName2}<h3></thead>
            <tr> 
        <table> <th>Batting First Stats</th>
            <th>Chasing Stats</th>
            <tr>
                <td>
        <table border="1px">
          
            <tr>
                <td>First Over</td>
        <c:forEach var="temp" items="${batRecords_In1_2}">
            <td>${temp.getFirstOver()}</td>
        </c:forEach>
             </tr>
        <tr>
                <td>First Five Overs</td>
        <c:forEach var="temp" items="${batRecords_In1_2}">
            <td>${temp.getFirstFiveOvers()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>First Wicket Fall</td>
        <c:forEach var="temp" items="${batRecords_In1_2}">
            <td>${temp.getFirstWicket()}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Last FIve Overs</td>
        <c:forEach var="temp" items="${batRecords_In1_2}">
            <td>${temp.getLastFiveOvers()}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Total Runs</td>
        <c:forEach var="temp" items="${runScoredBat_In1_2}">
            <td>${temp}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Fours</td>
        <c:forEach var="temp" items="${batRecords_In1_2}">
            <td>${temp.getFours()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>Sixes</td>
        <c:forEach var="temp" items="${batRecords_In1_2}">
            <td>${temp.getSixes()}</td>
        </c:forEach>
             </tr>   
          
        </table>
        </td>
              <td>
        <table border="1px">
          
            <tr>
                <td>First Over</td>
        <c:forEach var="temp" items="${batRecords_In2_2}">
            <td>${temp.getFirstOver()}</td>
        </c:forEach>
             </tr>
        <tr>
                <td>First Five Overs</td>
        <c:forEach var="temp" items="${batRecords_In2_2}">
            <td>${temp.getFirstFiveOvers()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>First Wicket Fall</td>
        <c:forEach var="temp" items="${batRecords_In2_2}">
            <td>${temp.getFirstWicket()}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Last FIve Overs</td>
        <c:forEach var="temp" items="${batRecords_In2_2}">
            <td>${temp.getLastFiveOvers()}</td>
        </c:forEach>
             </tr>
             <tr>
             <td>Total Runs</td>
        <c:forEach var="temp" items="${runScoredBat_In2_2}">
            <td>${temp}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Fours</td>
        <c:forEach var="temp" items="${batRecords_In2_2}">
            <td>${temp.getFours()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>Sixes</td>
        <c:forEach var="temp" items="${batRecords_In2_2}">
            <td>${temp.getSixes()}</td>
        </c:forEach>
             </tr>   
          
        </table>
        </td>
          
            </tr>
        </table>
            </tr>
            <tr> 
        <table> <th>Bowling First Stats</th>
            <th>Defending Stats</th>
            <tr>
                <td>
        <table border="1px">
          
            <tr>
                <td>First Over</td>
        <c:forEach var="temp" items="${bowlRecords_In1_2}">
            <td>${temp.getFirstOver()}</td>
        </c:forEach>
             </tr>
        <tr>
                <td>First Five Overs</td>
        <c:forEach var="temp" items="${bowlRecords_In1_2}">
            <td>${temp.getFirstFiveOvers()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>First Wicket Fall</td>
        <c:forEach var="temp" items="${bowlRecords_In1_2}">
            <td>${temp.getFirstWicket()}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Last FIve Overs</td>
        <c:forEach var="temp" items="${bowlRecords_In1_2}">
            <td>${temp.getLastFiveOvers()}</td>
        </c:forEach>
             </tr>
             <tr>
             <td>Total Runs</td>
        <c:forEach var="temp" items="${runGivenBowl_In1_2}">
            <td>${temp}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Fours</td>
        <c:forEach var="temp" items="${bowlRecords_In1_2}">
            <td>${temp.getFours()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>Sixes</td>
        <c:forEach var="temp" items="${bowlRecords_In1_2}">
            <td>${temp.getSixes()}</td>
        </c:forEach>
             </tr>   
          
        </table>
        </td>
              <td>
        <table border="1px">
          
            <tr>
                <td>First Over</td>
        <c:forEach var="temp" items="${bowlRecords_In2_2}">
            <td>${temp.getFirstOver()}</td>
        </c:forEach>
             </tr>
        <tr>
                <td>First Five Overs</td>
        <c:forEach var="temp" items="${bowlRecords_In2_2}">
            <td>${temp.getFirstFiveOvers()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>First Wicket Fall</td>
        <c:forEach var="temp" items="${bowlRecords_In2_2}">
            <td>${temp.getFirstWicket()}</td>
        </c:forEach>
             </tr>
             <tr>
                <td>Last FIve Overs</td>
        <c:forEach var="temp" items="${bowlRecords_In2_2}">
            <td>${temp.getLastFiveOvers()}</td>
        </c:forEach>
             </tr>
             <tr>
             <td>Total Runs</td>
        <c:forEach var="temp" items="${runGivenBowl_In2_2}">
            <td>${temp}</td>
        </c:forEach>
             </tr>
             
             <tr>
                <td>Fours</td>
        <c:forEach var="temp" items="${bowlRecords_In2_2}">
            <td>${temp.getFours()}</td>
        </c:forEach>
             </tr>
         <tr>
                <td>Sixes</td>
        <c:forEach var="temp" items="${bowlRecords_In2_2}">
            <td>${temp.getSixes()}</td>
        </c:forEach>
             </tr>   
          
        </table>
        </td>
          
            </tr>
        </table>
            </tr>
         </table>
           
            
            
            
    </body>
</html>
