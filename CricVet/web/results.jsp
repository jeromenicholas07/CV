<%-- 
    Document   : results
    Created on : Mar 3, 2019, 12:25:47 AM
    Author     : DELL
--%>

<%@page import="com.sun.javafx.scene.control.skin.VirtualFlow.ArrayLinkedList"%>
<%@page import="java.util.function.Predicate"%>
<%@page import="javax.servlet.jsp.jstl.core.LoopTagStatus"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
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
            .table th{
                vertical-align: middle
            }
            .grey-div{
                background-color: lightgray
            }
        </style>
        <div class="container-fluid text-center">
            <h1 class="text-center">${teamOne} v/s ${teamTwo}</h1>
            
            <!-- Head to head -->
            <div class="row">
                <div class="col-12">
                    <h3 class="card-title">Head to head</h3>
                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(hth)}">${teamOne} v/s ${teamTwo}</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${hth}">
                                <td>
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${hth}">
                                <td>
                                    ${in.getParams().get(7)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <%
                                if(true){
                                    List<Inning> hth = new ArrayList<Inning>((List<Inning>)request.getAttribute("hth"));
                                    
                                    int num = 0;
                                    int den = 0;
                                    for(Inning i : hth){
                                        String op = i.getParams().get(7);                                       
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

            <!-- Form Guide -->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">Form Guide</h3>
                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(FormGuide_A)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(FormGuide_B)}">${teamTwo}</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${FormGuide_A}">
                                <td name="-1Left">
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="in" items="${FormGuide_B}">
                                <td name="-1Left">
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${FormGuide_A}">
                                <td name="-2Left">
                                    ${in.getParams().get(7)}
                                </td>
                            </c:forEach>
                            <td></td>
                            <c:forEach var="in" items="${FormGuide_B}">
                                <td name="-2Left">
                                    ${in.getParams().get(7)}
                                </td>
                            </c:forEach>

                        </tr>
                        <%
                                if(true){
                                    List<Inning> a = new ArrayList<Inning>((List<Inning>)request.getAttribute("FormGuide_A"));
                                    List<Inning> b = new ArrayList<Inning>((List<Inning>)request.getAttribute("FormGuide_B"));
                                    
                                    int num = 0;
                                    int den = 0;
                                    int numA = 0;
                                    int denA = 0;
                                    for(Inning i : a){
                                        String op = i.getParams().get(7);
                                        
                                        if(op.contains("W")){
                                            numA++;
                                            
                                        }
                                        else if(op.contains("L")){
                                            denA++;
                                        }
                                    }
                                    request.setAttribute("fg_a", numA+"/"+denA);
                                    
                                    
                                    int numB = 0;
                                    int denB = 0;
                                    for(Inning i : b){
                                        String op = i.getParams().get(7);
                                        
                                        if(op.contains("W")){
                                            numB++;
                                        }
                                        else if(op.contains("L")){
                                            denB++;
                                        }
                                    }
                                    request.setAttribute("fg_b", numB+"/"+denB);
                                    num = numA + denB;
                                    den = denA + numB;
                                    
                                    
                                    request.setAttribute("fg_tot", num+"/"+den);
                                    
                                
                                }%>
                         
                        <tr>
                            <td colspan="${fn:length(FormGuide_A)}">
                                ${fg_a}
                            </td>
                            <td>
                            <td colspan="${fn:length(FormGuide_B)}">
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
             
            <!--Fours-->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">Total Fours</h3>
                    
                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FST_A" />
                        <jsp:param name="B" value="FST_B" />
                        <jsp:param name="G" value="Gr_First" />
                        <jsp:param name="pIndex" value="4" />
                        <jsp:param name="className" value="Fours" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="foursA_bt" />
                        <jsp:param name="TA_bt" value="foursTA_bt" />
                        <jsp:param name="B_bt" value="foursB_bt" />
                        <jsp:param name="TB_bt" value="foursTB_bt" />
                        <jsp:param name="G_bt" value="foursG_bt" />
                    </jsp:include>

                </div>
            </div>
            
            <!--Sixes-->      
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">Total Sixes</h3>
                    
                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FST_A" />
                        <jsp:param name="B" value="FST_B" />
                        <jsp:param name="G" value="Gr_First" />
                        <jsp:param name="pIndex" value="5" />
                        <jsp:param name="className" value="Sixes" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="sixesA_bt" />
                        <jsp:param name="TA_bt" value="sixesTA_bt" />
                        <jsp:param name="B_bt" value="sixesB_bt" />
                        <jsp:param name="TB_bt" value="sixesTB_bt" />
                        <jsp:param name="G_bt" value="sixesG_bt" />
                    </jsp:include>
                                    
                </div>
            </div>
               
            <!--Boundaries-->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">Total Boundaries</h3>
                    
                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FST_A" />
                        <jsp:param name="B" value="FST_B" />
                        <jsp:param name="G" value="Gr_First" />
                        <jsp:param name="pIndex" value="8" />
                        <jsp:param name="className" value="TotalBoundaries" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="boundariesA_bt" />
                        <jsp:param name="TA_bt" value="boundariesTA_bt" />
                        <jsp:param name="B_bt" value="boundariesB_bt" />
                        <jsp:param name="TB_bt" value="boundariesTB_bt" />
                        <jsp:param name="G_bt" value="boundariesG_bt" />
                    </jsp:include>
                    
                </div>
            </div>
                           

        <h2>First Inning</h2>
            <!--B/C Winner-->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">B/C Winner</h3>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(BCW_A)}">${teamOne} Batting First</th>
                            <th>
                            <th colspan="${fn:length(BCW_B)}">${teamTwo} Bowling First</th>
                            <th>
                            <th colspan="${fn:length(BCW_G)}">${groundName} First</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${BCW_A}">
                                <td >
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${BCW_B}">
                                <td >
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${BCW_G}">
                                <td >
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>

                        </tr>
                        <tr>
                            <c:forEach var="in" items="${BCW_A}">
                                <td >
                                    ${in.getParams().get(7)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${BCW_B}">
                                <td >
                                    ${in.getParams().get(7)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${BCW_G}">
                                <td >
                                    ${in.getParams().get(7)}
                                </td>
                            </c:forEach>

                        </tr>
                        <tr>
                            <%

                            if(true){
                                List<Inning> Ab = new ArrayList<Inning>
                                        ((List<Inning>)request.getAttribute("BCW_A"));
                                List<Inning> Bb = new ArrayList<Inning>
                                        ((List<Inning>)request.getAttribute("BCW_B"));
                                List<Inning> Gb = new ArrayList<Inning>
                                        ((List<Inning>)request.getAttribute("BCW_G"));

                                int num = 0;
                                int den = 0;
                                for(Inning ti : Ab){

                                    if(ti.getParams().get(7).trim().charAt(0) == 'B'){
                                        num++;
                                    }
                                    else if(ti.getParams().get(7).trim().charAt(0) == 'C'){
                                        den++;
                                    }
                                }
                                pageContext.setAttribute("Ab", num+"/"+den);

                                num = 0;
                                den = 0;
                                for(Inning ti : Bb){

                                    if(ti.getParams().get(7).trim().charAt(0) == 'B'){
                                        num++;
                                    }
                                    else if(ti.getParams().get(7).trim().charAt(0) == 'C'){
                                        den++;
                                    }
                                }
                                pageContext.setAttribute("Bb", num+"/"+den);

                                num = 0;
                                den = 0;
                                for(Inning ti : Gb){

                                    if(ti.getParams().get(7).trim().charAt(0) == 'B'){
                                        num++;
                                    }
                                    else if(ti.getParams().get(7).trim().charAt(0) == 'C'){
                                        den++;
                                    }
                                }
                                pageContext.setAttribute("Gb", num+"/"+den);

                                num = 0;
                                den = 0;
                                List<Inning> Tb = new ArrayList<Inning>();
                                Tb.addAll(Ab);
                                Tb.addAll(Bb);
                                for(Inning ti : Tb){

                                    if(ti.getParams().get(7).trim().charAt(0) == 'B'){
                                        num++;
                                    }
                                    else if(ti.getParams().get(7).trim().charAt(0) == 'C'){
                                        den++;
                                    }
                                }
                                pageContext.setAttribute("Tb", num+"/"+den);
                            }


                         %>
                            <td colspan="${fn:length(BCW_A)}">
                             ${Ab}
                         </td>
                         <td></td>
                         <td colspan="${fn:length(BCW_B)}">
                             ${Bb}
                         </td>
                         <td></td>
                         <td colspan="${fn:length(BCW_G)}">
                             ${Gb}
                         </td>
                     </tr>
                     <tr>
                         <td colspan="${fn:length(BCW_A)+fn:length(BCW_B)+1}">
                             ${Tb}
                         </td>

                     </tr>

                    </table>
                </div>
            </div>
                         
            <!-- First Over -->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">First Over</h3>
                    
                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FO_A" />
                        <jsp:param name="B" value="FO_B" />
                        <jsp:param name="G" value="Gr_First" />
                        <jsp:param name="pIndex" value="0" />
                        <jsp:param name="className" value="FirstOver" />
                    </jsp:include>
                    
                </div>
            </div>
            
            <!-- First Wicket -->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">First Wicket</h3>

                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FW_A" />
                        <jsp:param name="B" value="FW_B" />
                        <jsp:param name="G" value="Gr_First" />
                        <jsp:param name="pIndex" value="3" />
                        <jsp:param name="className" value="FirstWicket" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="FW_A_bt" />
                        <jsp:param name="TA_bt" value="FW_TA_bt" />
                        <jsp:param name="B_bt" value="FW_B_bt" />
                        <jsp:param name="TB_bt" value="FW_TB_bt" />
                        <jsp:param name="G_bt" value="FW_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>

            <!-- First 6/10 Overs -->
            <div class="row" >
                <div class ="col-12">
                    <c:if test='${matchType.equals("2")}'>
                        <h3 class="card-title">First 10 Overs</h3>
                    </c:if>
                    <c:if test='${!matchType.equals("2")}'>
                        <h3 class="card-title">First 6 Overs</h3>
                    </c:if>
                         
                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FX_A" />
                        <jsp:param name="B" value="FX_B" />
                        <jsp:param name="G" value="Gr_First" />
                        <jsp:param name="pIndex" value="1" />
                        <jsp:param name="className" value="FirstXOvers" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="FX_A_bt" />
                        <jsp:param name="TA_bt" value="FX_TA_bt" />
                        <jsp:param name="B_bt" value="FX_B_bt" />
                        <jsp:param name="TB_bt" value="FX_TB_bt" />
                        <jsp:param name="G_bt" value="FX_G_bt" />
                    </jsp:include>
                        
                </div>
            </div>
     
            <!-- Last 5/10 Over -->
            <div class="row" >
                <div class ="col-12">
                    <c:if test='${matchType.equals("2")}'>
                        <h3 class="card-title">Last 10 Overs</h3>
                    </c:if>
                    <c:if test='${!matchType.equals("2")}'>
                        <h3 class="card-title">Last 5 Overs</h3>
                    </c:if>
                         
                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="LO_A" />
                        <jsp:param name="B" value="LO_B" />
                        <jsp:param name="G" value="LO_G" />
                        <jsp:param name="pIndex" value="2" />
                        <jsp:param name="className" value="LastXOvers" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="LO_A_bt" />
                        <jsp:param name="TA_bt" value="LO_TA_bt" />
                        <jsp:param name="B_bt" value="LO_B_bt" />
                        <jsp:param name="TB_bt" value="LO_TB_bt" />
                        <jsp:param name="G_bt" value="LO_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>
            
            <!-- Total runs -->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">Total Runs</h3>
                    
                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="TR_A" />
                        <jsp:param name="B" value="TR_B" />
                        <jsp:param name="G" value="TR_G" />
                        <jsp:param name="pIndex" value="6" />
                        <jsp:param name="className" value="TotalRuns" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="TR_A_bt" />
                        <jsp:param name="TA_bt" value="TR_TA_bt" />
                        <jsp:param name="B_bt" value="TR_B_bt" />
                        <jsp:param name="TB_bt" value="TR_TB_bt" />
                        <jsp:param name="G_bt" value="TR_G_bt" />
                    </jsp:include>

                </div>
            </div>
                        
        <h2>Second Inning</h2>

            <!-- First Wicket -->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">${headers.get(3)}</h3>
                    
                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FWS_A" />
                        <jsp:param name="B" value="FWS_B" />
                        <jsp:param name="G" value="FWS_G" />
                        <jsp:param name="pIndex" value="3" />
                        <jsp:param name="className" value="FirstWicketSecond" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="FWS_A_bt" />
                        <jsp:param name="TA_bt" value="FWS_TA_bt" />
                        <jsp:param name="B_bt" value="FWS_B_bt" />
                        <jsp:param name="TB_bt" value="FWS_TB_bt" />
                        <jsp:param name="G_bt" value="FWS_G_bt" />
                    </jsp:include>
                    
                </div>
            </div>

            <!-- First 6/10 Overs -->
            <div class="row" >
                <div class ="col-12">
                    <c:if test='${matchType.equals("2")}'>
                        <h3 class="card-title">First 10 Overs</h3>
                    </c:if>
                    <c:if test='${!matchType.equals("2")}'>
                        <h3 class="card-title">First 6 Overs</h3>
                    </c:if>
                         
                    <jsp:include page="/displayTestHeader.jsp">
                        <jsp:param name="A" value="FXS_A" />
                        <jsp:param name="B" value="FXS_B" />
                        <jsp:param name="G" value="FXS_G" />
                        <jsp:param name="pIndex" value="1" />
                        <jsp:param name="className" value="FirstXOversSecond" />
                    </jsp:include>
                    
                    <jsp:include page="/displayBacktests.jsp">
                        <jsp:param name="A_bt" value="FXS_A_bt" />
                        <jsp:param name="TA_bt" value="FXS_TA_bt" />
                        <jsp:param name="B_bt" value="FXS_B_bt" />
                        <jsp:param name="TB_bt" value="FXS_TB_bt" />
                        <jsp:param name="G_bt" value="FXS_G_bt" />
                    </jsp:include>
                        
                </div>
            </div>
            
        </div>





        <script type="text/javascript">

            $(document).ready(function () {
                $("td, th").not('.A').not('.B').not('.G').not('.T').each(function(){
                    var inp = $(this).text().trim();
                    if(inp === ""){
                        $(this).addClass("grey-div");
                    }
                });
                
                $('div.btn').click();
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
            
//            $("input").keyup(function () {
//
//                var inp = $(this).val();
//                
//                var name = $(this).attr("name");
//
//                $('td[name="' + name + '"]').each(function () {
//                    $(this).removeClass("greater");
//                    $(this).removeClass("lower");
//                    $('#' + name).text("@");
//                });
//
//
//                if (inp.length === 0) {
//                    $('td[name="' + name + '"]').each(function () {
//                        $(this).removeClass("greater");
//                        $(this).removeClass("lower");
//                        $('#' + name).text("@");
//                    });
//
//                } else {
////                    var tds = $('td[name="' + name + '"]');
////
////                    var noOfTds = $('td[name="' + name + '"]');
//
//                    var num = 0;
//                    var den = 1;
//
//                    $('td[name="' + name + '"]').each(function () {
//                        if (parseInt($(this).text()) === -1 || $(this).text().includes("(D/L)")) {
//
//                        } else if (parseInt($(this).text()) >= parseInt(inp)) {
//
//                            $(this).addClass("greater");
//                            num++;
//                        } else if (parseInt($(this).text()) < parseInt(inp)) {
////                            alert($(this).text() +" :: "+ inp);
//                            $(this).addClass("lower");
//                            den++;
//                        }
//
//                    });
//
//                    $('#' + name).text(den.toString() + "/" + num.toString());
//
//                }
//            });
        </script>

    </body>
</html>
