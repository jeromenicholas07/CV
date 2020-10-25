<%-- 
    Document   : results
    Created on : Mar 3, 2019, 12:25:47 AM
    Author     : DELL
--%>

<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@page import="javax.servlet.jsp.jstl.core.LoopTagStatus"%>
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
            .grey-div{
                background-color: lightgray
            }
        </style>
        <div class="container-fluid text-center">
            <h1 class="text-center">Home Team: ${hometeam}</h1>
            <h1 class="text-center">Batting team ${teamOne} v/s Bowling Team ${teamTwo}</h1>
            
            <!-- Head to head -->
            <div class="row">
                <div class="col-12">
                    <h5 class="card-title">Head to head</h5>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(hth)}">${teamOne} v/s ${teamTwo}</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${hth}">
                                <td name="-Left">
                                    ${in.getParams().get(0)}
                                </td>
                            </c:forEach>                           
                        </tr>
                        
                        <tr>
                            <c:forEach var="in" items="${hth}">
                                <td name="-Left">
                                    ${in.getParams().get(5)}
                                </td>
                            </c:forEach>
                        </tr>
                        
                        <tr colspan="${fn:length(hth)}">
                            <%
                                if(true){
                                    List<Inning> hth = new ArrayList<Inning>((List<Inning>)request.getAttribute("hth"));
                                    
                                    int num = 0;
                                    int den = 0;
                                    for(Inning i : hth){
                                        String op = i.getParams().get(5);
                                        if(op.contains("W")){
                                            num++;
                                        }
                                        else if(op.contains("L")){
                                            den++;
                                        }
                                    }
                                    request.setAttribute("hth_wl", num+"/"+den);
                                
                                }%>        
                                <td colspan="30">
                                    ${hth_wl}
                                </td>
                        </tr>
                    </table>
                </div>
            </div>

            <!-- Form guide -->
            <div class="row" >
                <div class ="col-12">
                    <h5 class="card-title">Form Guide</h5>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(FG_A)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(FG_B)}">${teamTwo}</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${FG_A}">
                                <td name="-1Left">
                                    ${in.getParams().get(0)}
                                </td>
                            </c:forEach>

                            <td class="grey-div"></td>

                            <c:forEach var="in" items="${FG_B}">
                                <td name="-1Left">
                                    ${in.getParams().get(0)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${FG_A}">
                                <td>
                                    ${in.getParams().get(5)}
                                </td>
                            </c:forEach>
                            <td  class="grey-div"></td>
                            <c:forEach var="in" items="${FG_B}">
                                <td>
                                    ${in.getParams().get(5)}
                                </td>
                            </c:forEach>

                        </tr>
                        <%
                                if(true){
                                    List<Inning> a = new ArrayList<Inning>((List<Inning>)request.getAttribute("FG_A"));
                                    List<Inning> b = new ArrayList<Inning>((List<Inning>)request.getAttribute("FG_B"));
                                    
                                    int num = 0;
                                    int den = 0;
                                    for(Inning i : a){
                                        String op = i.getParams().get(5);
                                        if(op.contains("W")){
                                            num++;
                                        }
                                        else if(op.contains("L")){
                                            den++;
                                        }
                                    }
                                    request.setAttribute("fg_a", num+"/"+den);
                                    
                                    int numB = 0;
                                    int denB = 0;
                                    for(Inning i : b){
                                        String op = i.getParams().get(5);
                                        if(op.contains("W")){
                                            numB++;
                                        }
                                        else if(op.contains("L")){
                                            denB++;
                                        }
                                    }
                                    request.setAttribute("fg_b", numB+"/"+denB);
                                    request.setAttribute("fg_tot", (num+denB)+"/"+(den+numB));
                                    
                                
                                }%>
                         
                        <tr>
                            <td colspan="${fn:length(FG_A)}">
                                ${fg_a}
                            </td>
                            <td>
                            <td colspan="${fn:length(FG_B)}">
                                ${fg_b}
                            </td>
                        </tr>
                        <tr>
                            <td colspan="30">
                                ${fg_tot}
                            </td>
                        </tr>
                    </table>

                </div>
            </div>
                            
            <!-- Total Fours -->
            <div class="row" >
                <div class ="col-12">
                    <h5 class="card-title">Total Fours</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FST_A" />
                        <jsp:param name="B" value="FST_B" />
                        <jsp:param name="G" value="FST_G" />
                        <jsp:param name="pIndex" value="1" />
                        <jsp:param name="className" value="Fours" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="F_A_bt" />
                        <jsp:param name="TA_bt" value="F_TA_bt" />
                        <jsp:param name="B_bt" value="F_B_bt" />
                        <jsp:param name="TB_bt" value="F_TB_bt" />
                        <jsp:param name="G_bt" value="F_G_bt" />
                    </jsp:include>
                </div>
            </div>

            <!-- Total Sixes -->
            <div class="row" >
                <div class ="col-12">
                    <h5 class="card-title">Total Sixes</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FST_A" />
                        <jsp:param name="B" value="FST_B" />
                        <jsp:param name="G" value="FST_G" />
                        <jsp:param name="pIndex" value="2" />
                        <jsp:param name="className" value="Sixes" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="S_A_bt" />
                        <jsp:param name="TA_bt" value="S_TA_bt" />
                        <jsp:param name="B_bt" value="S_B_bt" />
                        <jsp:param name="TB_bt" value="S_TB_bt" />
                        <jsp:param name="G_bt" value="S_G_bt" />
                    </jsp:include>
                   
                </div>
            </div>
            
            <!-- Total Boundaries --> 
            <div class="row" >
                <div class ="col-12">
                    <h5 class="card-title">Total Boundaries</h5>
                    
                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FST_A" />
                        <jsp:param name="B" value="FST_B" />
                        <jsp:param name="G" value="FST_G" />
                        <jsp:param name="pIndex" value="6" />
                        <jsp:param name="className" value="Boundaries" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="T_A_bt" />
                        <jsp:param name="TA_bt" value="T_TA_bt" />
                        <jsp:param name="B_bt" value="T_B_bt" />
                        <jsp:param name="TB_bt" value="T_TB_bt" />
                        <jsp:param name="G_bt" value="T_G_bt" />
                    </jsp:include>

                </div>
            </div>

        <h2>First Inning</h2>
        
            <!-- First Total Runs -->
            <div class="row">
                <div class ="col-12">
                    <h5 class="card-title">Total Runs</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FX_A" />
                        <jsp:param name="B" value="FX_B" />
                        <jsp:param name="G" value="FX_G" />
                        <jsp:param name="pIndex" value="0" />
                        <jsp:param name="className" value="FirstTotal" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="FTR_A_bt" />
                        <jsp:param name="TA_bt" value="FTR_TA_bt" />
                        <jsp:param name="B_bt" value="FTR_B_bt" />
                        <jsp:param name="TB_bt" value="FTR_TB_bt" />
                        <jsp:param name="G_bt" value="FTR_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
            
            <!-- First First Wicket -->
            <div class="row">
                <div class ="col-12">
                    <h5 class="card-title">First Wicket</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FX_A" />
                        <jsp:param name="B" value="FX_B" />
                        <jsp:param name="G" value="FX_G" />
                        <jsp:param name="pIndex" value="3" />
                        <jsp:param name="className" value="FirstFirstWicket" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="FFW_A_bt" />
                        <jsp:param name="TA_bt" value="FFW_TA_bt" />
                        <jsp:param name="B_bt" value="FFW_B_bt" />
                        <jsp:param name="TB_bt" value="FFW_TB_bt" />
                        <jsp:param name="G_bt" value="FFW_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
            
            <!-- First After 5th Wicket -->
            <div class="row">
                <div class ="col-12">
                    <h5 class="card-title">After 5th Wicket</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="F5_A" />
                        <jsp:param name="B" value="F5_B" />
                        <jsp:param name="G" value="F5_G" />
                        <jsp:param name="pIndex" value="4" />
                        <jsp:param name="className" value="FirstFiveWicket" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="F5_A_bt" />
                        <jsp:param name="TA_bt" value="F5_TA_bt" />
                        <jsp:param name="B_bt" value="F5_B_bt" />
                        <jsp:param name="TB_bt" value="F5_TB_bt" />
                        <jsp:param name="G_bt" value="F5_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
                       
        <h2>Second Inning</h2>
        
            <!-- Second Total Runs -->
            <div class="row">
                <div class ="col-12">
                    <h5 class="card-title">Total Runs</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="SX_A" />
                        <jsp:param name="B" value="SX_B" />
                        <jsp:param name="G" value="SX_G" />
                        <jsp:param name="pIndex" value="0" />
                        <jsp:param name="className" value="SecondTotal" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="STR_A_bt" />
                        <jsp:param name="TA_bt" value="STR_TA_bt" />
                        <jsp:param name="B_bt" value="STR_B_bt" />
                        <jsp:param name="TB_bt" value="STR_TB_bt" />
                        <jsp:param name="G_bt" value="STR_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
            
            <!-- Second First Wicket -->
            <div class="row">
                <div class ="col-12">
                    <h5 class="card-title">First Wicket</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="SX_A" />
                        <jsp:param name="B" value="SX_B" />
                        <jsp:param name="G" value="SX_G" />
                        <jsp:param name="pIndex" value="3" />
                        <jsp:param name="className" value="SecondFirstWicket" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="SFW_A_bt" />
                        <jsp:param name="TA_bt" value="SFW_TA_bt" />
                        <jsp:param name="B_bt" value="SFW_B_bt" />
                        <jsp:param name="TB_bt" value="SFW_TB_bt" />
                        <jsp:param name="G_bt" value="SFW_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
            
            <!-- Second After 5th Wicket -->
            <div class="row">
                <div class ="col-12">
                    <h5 class="card-title">After 5th Wicket</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="S5_A" />
                        <jsp:param name="B" value="S5_B" />
                        <jsp:param name="G" value="S5_G" />
                        <jsp:param name="pIndex" value="4" />
                        <jsp:param name="className" value="SecondFiveWicket" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="S5_A_bt" />
                        <jsp:param name="TA_bt" value="S5_TA_bt" />
                        <jsp:param name="B_bt" value="S5_B_bt" />
                        <jsp:param name="TB_bt" value="S5_TB_bt" />
                        <jsp:param name="G_bt" value="S5_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
                       
            
        <h2>Third Inning</h2>
        
            <!-- Third Total Runs -->
            <div class="row">
                <div class ="col-12">
                    <h5 class="card-title">Total Runs</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="TX_A" />
                        <jsp:param name="B" value="TX_B" />
                        <jsp:param name="G" value="TX_G" />
                        <jsp:param name="pIndex" value="0" />
                        <jsp:param name="className" value="ThirdTotal" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="TTR_A_bt" />
                        <jsp:param name="TA_bt" value="TTR_TA_bt" />
                        <jsp:param name="B_bt" value="TTR_B_bt" />
                        <jsp:param name="TB_bt" value="TTR_TB_bt" />
                        <jsp:param name="G_bt" value="TTR_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
            
            <!-- First First Wicket -->
            <div class="row">
                <div class ="col-12">
                    <h5 class="card-title">First Wicket</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="TX_A" />
                        <jsp:param name="B" value="TX_B" />
                        <jsp:param name="G" value="TX_G" />
                        <jsp:param name="pIndex" value="3" />
                        <jsp:param name="className" value="ThirdFirstWicket" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="TFW_A_bt" />
                        <jsp:param name="TA_bt" value="TFW_TA_bt" />
                        <jsp:param name="B_bt" value="TFW_B_bt" />
                        <jsp:param name="TB_bt" value="TFW_TB_bt" />
                        <jsp:param name="G_bt" value="TFW_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
            
            <!-- First After 5th Wicket -->
            <div class="row">
                <div class ="col-12">
                    <h5 class="card-title">After 5th Wicket</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="T5_A" />
                        <jsp:param name="B" value="T5_B" />
                        <jsp:param name="G" value="T5_G" />
                        <jsp:param name="pIndex" value="4" />
                        <jsp:param name="className" value="ThirdFiveWicket" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="T5_A_bt" />
                        <jsp:param name="TA_bt" value="T5_TA_bt" />
                        <jsp:param name="B_bt" value="T5_B_bt" />
                        <jsp:param name="TB_bt" value="T5_TB_bt" />
                        <jsp:param name="G_bt" value="T5_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
            
        <h2>Fourth Inning</h2>
            
            <!-- Fourth Total Runs -->
            <div class="row">
                <div class ="col-12">
                    <h5 class="card-title">Total Runs</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="QX_A" />
                        <jsp:param name="B" value="QX_B" />
                        <jsp:param name="G" value="QX_G" />
                        <jsp:param name="pIndex" value="0" />
                        <jsp:param name="className" value="FourthTotal" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="QTR_A_bt" />
                        <jsp:param name="TA_bt" value="QTR_TA_bt" />
                        <jsp:param name="B_bt" value="QTR_B_bt" />
                        <jsp:param name="TB_bt" value="QTR_TB_bt" />
                        <jsp:param name="G_bt" value="QTR_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
            
            <!-- Fourth First Wicket -->
            <div class="row">
                <div class ="col-12">
                    <h5 class="card-title">First Wicket</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="QX_A" />
                        <jsp:param name="B" value="QX_B" />
                        <jsp:param name="G" value="QX_G" />
                        <jsp:param name="pIndex" value="3" />
                        <jsp:param name="className" value="FourthFirstWicket" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="QFW_A_bt" />
                        <jsp:param name="TA_bt" value="QFW_TA_bt" />
                        <jsp:param name="B_bt" value="QFW_B_bt" />
                        <jsp:param name="TB_bt" value="QFW_TB_bt" />
                        <jsp:param name="G_bt" value="QFW_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
            
            <!-- Fourth After 5th Wicket -->
            <div class="row">
                <div class ="col-12">
                    <h5 class="card-title">After 5th Wicket</h5>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="Q5_A" />
                        <jsp:param name="B" value="Q5_B" />
                        <jsp:param name="G" value="Q5_G" />
                        <jsp:param name="pIndex" value="4" />
                        <jsp:param name="className" value="FourthFiveWicket" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="Q5_A_bt" />
                        <jsp:param name="TA_bt" value="Q5_TA_bt" />
                        <jsp:param name="B_bt" value="Q5_B_bt" />
                        <jsp:param name="TB_bt" value="Q5_TB_bt" />
                        <jsp:param name="G_bt" value="Q5_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
            
        </div>



        <script type="text/javascript">

            $(document).ready(function () {
                $("td,th").not('.A').not('.B').not('.G').not('.T').each(function(){
                    var inp = $(this).text().trim();
                    if(inp === ""){
                        $(this).addClass("grey-div");
                    }
                });
                $('div.btn').click();
                
                // highlight backtests
                $('td.bt-td').each(function(){
                   let num = parseInt($(this).children('.bt-num').first().text());
                   let den = parseInt($(this).children('.bt-den').first().text());
                   
                   let ratio = num/den;
                   if(ratio < 2){
                       $(this).addClass('red');
                   }
                   else if(ratio >= 3){
                       $(this).addClass('green');
                   }
                   else{
                       $(this).addClass('yellow');
                   }
                });
            }); 
            $("input").on('input', function () {

                var inp = $(this).val();
                
                var name = $(this).attr("name");

                $('td[name="' + name + '"]').each(function () {
                    $(this).removeClass("greater");
                    $(this).removeClass("lower");
                });


                if (inp.length === 0) {
                    $('td[name="' + name + '"]').each(function () {
                        $(this).removeClass("greater");
                        $(this).removeClass("lower");
                    });
                    $('td[name="' + name + 'Odd"][class="A"]').text("");
                    $('td[name="' + name + 'Odd"][class="B"]').text("");
                    $('td[name="' + name + 'Odd"][class="G"]').text("");
                    $('td[name="' + name + 'Odd"][class="T"]').text("");

                } else {
                    
                    $('td[name="' + name + '"]').each(function () {
                        if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {

                        } else if (parseInt($(this).text()) >= parseInt(inp)) {
                            $(this).addClass("greater");
//                            num++;
                        } else if (parseInt($(this).text()) < parseInt(inp)) {
//                            alert($(this).text() +" :: "+ inp);
                            $(this).addClass("lower");
//                            den++;
                        }

                    });

                    var num = 0;
                    var den = 0;

                    $('td[name="' + name + '"].A,td[name="' + name + '"].B').each(function () {
                        if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {

                        } else if (parseInt($(this).text()) >= parseInt(inp)) {
//                            $(this).addClass("greater");
                            num++;
                        } else if (parseInt($(this).text()) < parseInt(inp)) {
//                            alert($(this).text() +" :: "+ inp);
//                            $(this).addClass("lower");
                            den++;
                        }

                    });

                    $('td[name="' + name + 'Odd"][class="T"]').text(den.toString() + "/" + num.toString());
                    
                    num = 0;
                    den = 0;

                    $('td[name="' + name + '"].A').each(function () {
                        if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {

                        } else if (parseInt($(this).text()) >= parseInt(inp)) {
                            num++;
                        } else if (parseInt($(this).text()) < parseInt(inp)) {
                            den++;
                        }

                    });

                    $('td[name="' + name + 'Odd"][class="A"]').text(den.toString() + "/" + num.toString());
                    
                    
                    
                    num = 0;
                    den = 0;

                    $('td[name="' + name + '"].B').each(function () {
                        if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {

                        } else if (parseInt($(this).text()) >= parseInt(inp)) {
                            num++;
                        } else if (parseInt($(this).text()) < parseInt(inp)) {
                            den++;
                        }

                    });

                    $('td[name="' + name + 'Odd"][class="B"]').text(den.toString() + "/" + num.toString());
                    
                    
                    
                    
                    num = 0;
                    den = 0;

                    $('td[name="' + name + '"].G').each(function () {
                        if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {

                        } else if (parseInt($(this).text()) >= parseInt(inp)) {
                            num++;
                        } else if (parseInt($(this).text()) < parseInt(inp)) {
                            den++;
                        }

                    });

                    $('td[name="' + name + 'Odd"][class="G"]').text(den.toString() + "/" + num.toString());

                }
            });
            
            function collapseSwitch(butt){ 
                butt = $(butt);
                var t1 = butt.parents('table');
                
                var t1_rows = t1.find('tr').not('.thead-light');
                
                var t2 = t1.next();
                
                t1_rows.fadeToggle(1000);
                t2.fadeToggle(1000);
            }
        </script>

    </body>
</html>
