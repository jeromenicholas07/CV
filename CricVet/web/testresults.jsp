<%-- 
    Document   : results
    Created on : Mar 3, 2019, 12:25:47 AM
    Author     : DELL
--%>

<%@page import="models.testInning"%>
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
                                    ${in.getTotalruns()}
                                </td>
                            </c:forEach>

                           
                        </tr>
                        
                        <tr>
                            <c:forEach var="in" items="${hthmatch}">
                                <td name="-Left">
                                    ${in.getResult()}
                                </td>
                            </c:forEach>
                            
                        </tr>
                        
                        <tr colspan="${fn:length(hth)}">
                            <%
                                if(true){
                                    List<testInning> hth = new ArrayList<testInning>((List<testInning>)request.getAttribute("hth"));
                                    
                                    int num = 0;
                                    int den = 0;
                                    for(testInning i : hth){
                                        String op = i.getWinner();
                                        if(op.contains("D/L")){
                                            continue;
                                        }
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
    


            <div class="row" >
                <div class ="col-12">
                    <h5 class="card-title">Form Guide</h5>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(t_oneBatFirstY)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(t_twoBowlFirstY)}">${teamTwo}</th>

                        </tr>
                        <tr>
                            <c:forEach var="in" items="${t_oneBatFirstY}">
                                <td name="-1Left">
                                    ${in.getTotalruns()}
                                </td>
                            </c:forEach>

                            <td class="grey-div"></td>

                            <c:forEach var="in" items="${t_twoBowlFirstY}">
                                <td name="-1Left">
                                    ${in.getTotalruns()}
                                </td>
                            </c:forEach>
                            
                        </tr>




                        <tr>
                            <c:forEach var="in" items="${t_oneBatFirstY}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td  class="grey-div"></td>
                            <c:forEach var="in" items="${t_twoBowlFirstY}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            


                        </tr>
                        <%
                                if(true){
                                    List<testInning> a = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_oneBatFirstY"));
                                    List<testInning> b = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_twoBowlFirstY"));
                                    
                                    int num = 0;
                                    int den = 0;
                                    for(testInning i : a){
                                        String op = i.getWinner();
                                        if(op.contains("D/L")){
                                            continue;
                                        }
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
                                    for(testInning i : b){
                                        String op = i.getWinner();
                                        if(op.contains("D/L")){
                                            continue;
                                        }
                                        if(op.contains("W")){
                                            numB++;
                                        }
                                        else if(op.contains("L")){
                                            denB++;
                                        }
                                    }
                                    request.setAttribute("fg_b", numB+"/"+denB);
                                    request.setAttribute("fg_tot", (num+numB)+"/"+(den+denB));
                                    
                                
                                }%>
                         
                        <tr>
                            <td colspan="${fn:length(t_oneBatFirstY)}">
                                ${fg_a}
                            </td>
                            <td>
                            <td colspan="${fn:length(t_twoBowlFirstY)}">
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
                <div class="row" >
                <div class ="col-12">
                    <h5 class="card-title">Total Fours</h5>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(onetotal)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(twototal)}">${teamTwo}</th>
                            <th>
                            <th colspan="${fn:length(groundtotal)}">${t_groundName}</th>
<!--                            <th>

                            <th>Score</th>-->
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${onetotal}">
                                <td>
                                    ${in.getFours()}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="in" items="${twototal}">
                                <td>
                                    ${in.getFours()}
                                </td>
                            </c:forEach>
                                
                            <td></td>

                            <c:forEach var="in" items="${groundtotal}">
                                <td>
                                    ${in.getFours()}
                                </td>
                            </c:forEach>
                            
                            
                        </tr>


                        <tr class="thead-dark">
                            <th colspan="8">Sorted:
                            <th colspan="20">
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="A" id="A">@</span>
                                    </div>
                                    <input type="number" class="form-control" name="A">
                                </div>

                            </th>
                        </tr>
                        <tr>
                        <%

                        if(true){
                            List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("onetotal"));
                            Collections.sort(As, new Comparator<testInning>() {
                                @Override
                                public int compare(testInning o1, testInning o2) {
                                    return o1.getFours()
                                            - o2.getFours();
                                }
                            });
                            pageContext.setAttribute("As", As);

                            List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("twototal"));
                            Collections.sort(Bs, new Comparator<testInning>() {
                                @Override
                                public int compare(testInning o1, testInning o2) {
                                    return o1.getFours()
                                            - o2.getFours();
                                }
                            });
                            pageContext.setAttribute("Bs", Bs);
                            
                            List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("groundtotal"));
                                    Collections.sort(Gs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getFours()
                                                    - o2.getFours();
                                        }
                                    });
                            pageContext.setAttribute("Gs", Gs);

                            List<testInning> ASs = new ArrayList<testInning>();
                            ASs.addAll(As);
                            ASs.addAll(Bs);
                            Collections.sort(ASs, new Comparator<testInning>() {
                                @Override
                                public int compare(testInning o1, testInning o2) {
                                    return o1.getFours()
                                            - o2.getFours();
                                }
                            });
                            pageContext.setAttribute("ASs", ASs);

                        }
                        %>
                            <c:forEach var="in" items="${As}">
                                <td name="A" class="A">
                                    ${in.getFours() }
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Bs}">
                                <td name="A" class="B">
                                   ${in.getFours() }
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Gs}">
                                <td name="A" class="G">
                                   ${in.getFours() }
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(As)}" name="AOdd" class="A">

                            </td>
                            <td>
                            <td colspan="${fn:length(Bs)}" name="AOdd" class="B">

                            </td>

                            <td>
                            <td colspan="${fn:length(Gs)}" name="AOdd" class="G">

                            </td>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${ASs}">
                                <td name="A">
                                    ${in.getFours() }
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(ASs)}" name="AOdd" class="T">
                        </tr>


                    </table>
                    <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(onefours_bt)}">${teamOne} Fours</th>
                                        <th>
                                        <th colspan="${fn:length(twofours_bt)}">${teamTwo} Fours</th>
                                        <th>
                                        <th colspan="${fn:length(groundfours_bt)}">${t_groundName} Fours</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onefours_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twofours_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundfours_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onefours_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twofours_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundfours_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>

                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Fours</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onetotalfours_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onetotalfours_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onetotalfours_bt}">
                                            <td>
                                                ${onetotalfours_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Fours</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twototalfours_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twototalfours_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twototalfours_bt}">
                                            <td>
                                                ${twototalfours_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>


                </div>
            </div>


                <div class="row" >
                <div class ="col-12">
                    <h5 class="card-title">Total Sixes</h5>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(onetotal)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(twototal)}">${teamTwo}</th>
                            <th>
                            <th colspan="${fn:length(groundtotal)}">${t_groundName}</th>
<!--                            <th>

                            <th>Score</th>-->
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${onetotal}">
                                <td>
                                    ${in.getSixes()}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="in" items="${twototal}">
                                <td>
                                    ${in.getSixes()}
                                </td>
                            </c:forEach>
                            
                            <td></td>

                            <c:forEach var="in" items="${groundtotal}">
                                <td>
                                    ${in.getSixes()}
                                </td>
                            </c:forEach>
                        </tr>
                        
                        
                        <tr class="thead-dark">
                            <th colspan="6">Sorted:
                            <th colspan="20">
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="B" id="B">@</span>
                                    </div>
                                    <input type="number" class="form-control" name="B">
                                </div>
                            </th>
                        </tr>
                        <tr>
                        <%

                        if(true){
                            List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("onetotal"));
                            Collections.sort(As, new Comparator<testInning>() {
                                @Override
                                public int compare(testInning o1, testInning o2) {
                                    return o1.getSixes()
                                            - o2.getSixes();
                                }
                            });
                            pageContext.setAttribute("As", As);

                            List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("twototal"));
                            Collections.sort(Bs, new Comparator<testInning>() {
                                @Override
                                public int compare(testInning o1, testInning o2) {
                                    return o1.getSixes()
                                            - o2.getSixes();
                                }
                            });
                            pageContext.setAttribute("Bs", Bs);
                            
                            List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("groundtotal"));
                                    Collections.sort(Gs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getSixes()
                                                    - o2.getSixes();
                                        }
                                    });
                            pageContext.setAttribute("Gs", Gs);

                            List<testInning> ASs = new ArrayList<testInning>();
                            ASs.addAll(As);
                            ASs.addAll(Bs);
                            Collections.sort(ASs, new Comparator<testInning>() {
                                @Override
                                public int compare(testInning o1, testInning o2) {
                                    return o1.getSixes()
                                            - o2.getSixes();
                                }
                            });
                            pageContext.setAttribute("ASs", ASs);

                        }
                        %>
                            <c:forEach var="in" items="${As}">
                                <td name="B" class="A">
                                    ${in.getSixes() }
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Bs}">
                                <td name="B" class="B">
                                   ${in.getSixes() }
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Gs}">
                                <td name="B" class="G">
                                   ${in.getSixes() }
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(As)}" name="BOdd" class="A">

                            </td>
                            <td>
                            <td colspan="${fn:length(Bs)}" name="BOdd" class="B">

                            </td>
                            <td>
                            <td colspan="${fn:length(Gs)}" name="BOdd" class="G">

                            </td>

                        </tr>
                        <tr>
                            <c:forEach var="in" items="${ASs}">
                                <td name="B">
                                    ${in.getSixes() }
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(ASs)}" name="BOdd" class="T">
                        </tr>


                    </table>

                    <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(onesixes_bt)}">${teamOne} Sixes</th>
                                        <th>
                                        <th colspan="${fn:length(twosixes_bt)}">${teamTwo} Sixes</th>
                                        <th>
                                        <th colspan="${fn:length(groundsixes_bt)}">${t_groundName} Sixes</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onesixes_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twosixes_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundsixes_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onesixes_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twosixes_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundsixes_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>

                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Sixes</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onetotalsixes_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onetotalsixes_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onetotalsixes_bt}">
                                            <td>
                                                ${onetotalsixes_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Sixes</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twototalsixes_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twototalsixes_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twototalsixes_bt}">
                                            <td>
                                                ${twototalsixes_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                </div>
            </div>
            
                <div class="row" >
                <div class ="col-12">
                    <h5 class="card-title">Total Boundaries</h5>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(onetotalboundaries)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(twototalboundaries)}">${teamTwo}</th>
                            <th>
                            <th colspan="${fn:length(groundtotalboundaries)}">${t_groundName}</th>
<!--                            <th>

                            <th>Score</th>-->
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${onetotalboundaries}">
                                <td>
                                    ${in.getFours()}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="in" items="${twototalboundaries}">
                                <td>
                                    ${in.getFours()}
                                </td>
                            </c:forEach>
                                
                            <td></td>

                            <c:forEach var="in" items="${groundtotalboundaries}">
                                <td>
                                    ${in.getFours()}
                                </td>
                            </c:forEach>
                            
                            
                        </tr>


                        <tr class="thead-dark">
                            <th colspan="8">Sorted:
                            <th colspan="20">
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="O" id="O">@</span>
                                    </div>
                                    <input type="number" class="form-control" name="O">
                                </div>

                            </th>
                        </tr>
                        <tr>
                        <%

                        if(true){
                            List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("onetotalboundaries"));
                            Collections.sort(As, new Comparator<testInning>() {
                                @Override
                                public int compare(testInning o1, testInning o2) {
                                    return o1.getFours()
                                            - o2.getFours();
                                }
                            });
                            pageContext.setAttribute("As", As);

                            List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("twototalboundaries"));
                            Collections.sort(Bs, new Comparator<testInning>() {
                                @Override
                                public int compare(testInning o1, testInning o2) {
                                    return o1.getFours()
                                            - o2.getFours();
                                }
                            });
                            pageContext.setAttribute("Bs", Bs);
                            
                            List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("groundtotalboundaries"));
                                    Collections.sort(Gs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getFours()
                                                    - o2.getFours();
                                        }
                                    });
                            pageContext.setAttribute("Gs", Gs);

                            List<testInning> ASs = new ArrayList<testInning>();
                            ASs.addAll(As);
                            ASs.addAll(Bs);
                            Collections.sort(ASs, new Comparator<testInning>() {
                                @Override
                                public int compare(testInning o1, testInning o2) {
                                    return o1.getFours()
                                            - o2.getFours();
                                }
                            });
                            pageContext.setAttribute("ASs", ASs);

                        }
                        %>
                            <c:forEach var="in" items="${As}">
                                <td name="O" class="A">
                                    ${in.getFours() }
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Bs}">
                                <td name="O" class="B">
                                   ${in.getFours() }
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Gs}">
                                <td name="O" class="G">
                                   ${in.getFours() }
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(As)}" name="OOdd" class="A">

                            </td>
                            <td>
                            <td colspan="${fn:length(Bs)}" name="OOdd" class="B">

                            </td>

                            <td>
                            <td colspan="${fn:length(Gs)}" name="OOdd" class="G">

                            </td>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${ASs}">
                                <td name="O">
                                    ${in.getFours() }
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(ASs)}" name="OOdd" class="T">
                        </tr>


                    </table>
                    <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(oneboundaries_bt)}">${teamOne} Fours</th>
                                        <th>
                                        <th colspan="${fn:length(twoboundaries_bt)}">${teamTwo} Fours</th>
                                        <th>
                                        <th colspan="${fn:length(groundboundaries_bt)}">${t_groundName} Fours</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneboundaries_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twoboundaries_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundboundaries_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${oneboundaries_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${twoboundaries_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${groundboundaries_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>

                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Boundaries</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onetotalboundaries_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onetotalboundaries_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${onetotalboundaries_bt}">
                                            <td>
                                                ${onetotalboundaries_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} boundaries</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twototalboundaries_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twototalboundaries_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${twototalboundaries_bt}">
                                            <td>
                                                ${twototalboundaries_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>


                </div>
            </div>



            <h2>First Inning</h2>
            <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">Fours</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatFirst)}">${teamOne} Batting First Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirst)}">${teamTwo} Bowling First inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1X)}">${t_groundName} First Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td>
                                                ${in.getFours()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td >
                                                ${in.getFours()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1X}">
                                            <td>
                                                ${in.getFours()}
                                            </td>
                                        </c:forEach>
                                        


                                    </tr>

                                    
                                    
                                    
                                    <tr class="thead-dark">
                                        <th colspan="12">Sorted:</th>
                                        <th colspan="30">

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="P" id="P">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="P">
                                            </div>
                                        </th>
                                    </tr>
                                    <tr>
                                    <%

                                    if(true){
                                        
                                        List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBatFirst"));
                                        Collections.sort(As, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFours()
                                                        - o2.getFours();
                                            }
                                        });
                                        pageContext.setAttribute("As", As);

                                        List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBowlFirst"));
                                        Collections.sort(Bs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFours()
                                                        - o2.getFours();
                                            }
                                        });
                                        pageContext.setAttribute("Bs", Bs);

                                        List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundFirst1X"));
                                        Collections.sort(Gs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFours()
                                                        - o2.getFours();
                                            }
                                        });
                                        pageContext.setAttribute("Gs", Gs);

                                        List<testInning> ASs = new ArrayList<testInning>();
                                        ASs.addAll(As);
                                        ASs.addAll(Bs);
                                        Collections.sort(ASs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFours()
                                                        - o2.getFours();
                                            }
                                        });
                                        pageContext.setAttribute("ASs", ASs);

                                    }
                                    %>
                                        <c:forEach var="in" items="${As}">
                                            <td name="P" class="A">
                                                ${in.getFours() }
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Bs}">
                                            <td name="P" class="B">
                                               ${in.getFours() }
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Gs}">
                                            <td name="P" class="G">
                                                ${in.getFours() }
                                            </td>
                                        </c:forEach>
                                    </tr>

                                    <tr>
                                        <td colspan="${fn:length(As)}" name="POdd" class="A">

                                        </td>
                                        <td>
                                        <td colspan="${fn:length(Bs)}" name="POdd" class="B">

                                        </td>
                                        <td>
                                        <td colspan="${fn:length(Gs)}" name="POdd" class="G">

                                        </td>
                                        
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${ASs}">
                                            <td name="P">
                                                ${in.getFours() }
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td colspan="${fn:length(ASs)}" name="POdd" class="T"></td>
                                    </tr>
                                    
                                    
                                    
                                </table>

                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamoneBatFirstfours_bt)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirstfours_bt)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirstInningfours_bt)}">${t_groundName} First Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatFirstfours_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlFirstfours_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFirstInningfours_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatFirstfours_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlFirstfours_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFirstInningfours_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirstfours_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirstfours_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirstfours_bt}">
                                            <td>
                                                ${ABatFirstfours_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirstfours_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirstfours_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirstfours_bt}">
                                            <td>
                                                ${BBowlFirstfours_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>
                        
                                    <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">Sixes</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatFirst)}">${teamOne} Batting First Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirst)}">${teamTwo} Bowling First inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1X)}">${t_groundName} First Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td>
                                                ${in.getSixes()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td >
                                                ${in.getSixes()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1X}">
                                            <td>
                                                ${in.getSixes()}
                                            </td>
                                        </c:forEach>
                                        


                                    </tr>

                                    
                                    
                                    
                                    <tr class="thead-dark">
                                        <th colspan="12">Sorted:</th>
                                        <th colspan="30">

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="D" id="D">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="D">
                                            </div>
                                        </th>
                                    </tr>
                                    <tr>
                                    <%

                                    if(true){
                                        
                                        List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBatFirst"));
                                        Collections.sort(As, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getSixes()
                                                        - o2.getSixes();
                                            }
                                        });
                                        pageContext.setAttribute("As", As);

                                        List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBowlFirst"));
                                        Collections.sort(Bs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getSixes()
                                                        - o2.getSixes();
                                            }
                                        });
                                        pageContext.setAttribute("Bs", Bs);

                                        List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundFirst1X"));
                                        Collections.sort(Gs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getSixes()
                                                        - o2.getSixes();
                                            }
                                        });
                                        pageContext.setAttribute("Gs", Gs);

                                        List<testInning> ASs = new ArrayList<testInning>();
                                        ASs.addAll(As);
                                        ASs.addAll(Bs);
                                        Collections.sort(ASs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getSixes()
                                                        - o2.getSixes();
                                            }
                                        });
                                        pageContext.setAttribute("ASs", ASs);

                                    }
                                    %>
                                        <c:forEach var="in" items="${As}">
                                            <td name="D" class="A">
                                                ${in.getSixes() }
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Bs}">
                                            <td name="D" class="B">
                                               ${in.getSixes() }
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Gs}">
                                            <td name="D" class="G">
                                                ${in.getSixes() }
                                            </td>
                                        </c:forEach>
                                    </tr>

                                    <tr>
                                        <td colspan="${fn:length(As)}" name="DOdd" class="A">

                                        </td>
                                        <td>
                                        <td colspan="${fn:length(Bs)}" name="DOdd" class="B">

                                        </td>
                                        <td>
                                        <td colspan="${fn:length(Gs)}" name="DOdd" class="G">

                                        </td>
                                        
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${ASs}">
                                            <td name="D">
                                                ${in.getSixes() }
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td colspan="${fn:length(ASs)}" name="DOdd" class="T"></td>
                                    </tr>
                                    
                                    
                                    
                                </table>

                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamoneBatFirstsixes_bt)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirstsixes_bt)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirstInningsixes_bt)}">${t_groundName} First Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatFirstsixes_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlFirstsixes_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFirstInningsixes_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatFirstsixes_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlFirstsixes_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFirstInningsixes_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirstsixes_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirstsixes_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirstsixes_bt}">
                                            <td>
                                                ${ABatFirstsixes_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirstsixes_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirstsixes_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirstsixes_bt}">
                                            <td>
                                                ${BBowlFirstsixes_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>
                                    
            <c:forEach var="h" begin="0" end="6" varStatus="loop">
                <c:choose>
                    <c:when test="${loop.index == 0}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatFirst)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirst)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1X)}">${t_groundName} First Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1X}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        


                                    </tr>
                                    
                                    <tr>
                            <c:forEach var="in" items="${t_teamoneBatFirst}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>
                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                                <td></td>
                                <c:forEach var="in" items="${t_groundFirst1X}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                                
                                
                                 </tr>
                                 <tr>
                                     <%
                                     
                                        if(true){
                                            List<testInning> Ab = new ArrayList<testInning>
                                                    ((List<testInning>)request.getAttribute("t_teamoneBatFirst"));
                                            List<testInning> Bb = new ArrayList<testInning>
                                                    ((List<testInning>)request.getAttribute("t_teamtwoBowlFirst"));
                                            List<testInning> Gb = new ArrayList<testInning>
                                                    ((List<testInning>)request.getAttribute("t_groundFirst1X"));
                                            
                                            int num = 0;
                                            int den = 0;
                                            for(testInning ti : Ab){
                                                if(ti.getWinner().trim().contains("B/W") || ti.getWinner().trim().contains("C/L")){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().contains("B/L") || ti.getWinner().trim().contains("C/W")){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Ab", num+"/"+den);
                                            
                                            num = 0;
                                            den = 0;
                                            for(testInning ti : Bb){
                                                if(ti.getWinner().trim().contains("B/W") || ti.getWinner().trim().contains("C/L")){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().contains("B/L") || ti.getWinner().trim().contains("C/W")){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Bb", num+"/"+den);
                                            
                                            num = 0;
                                            den = 0;
                                            for(testInning ti : Gb){
                                                if(ti.getWinner().trim().charAt(0) == 'B'){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().charAt(0) == 'C'){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Gb", num+"/"+den);
                                            
                                            num = 0;
                                            den = 0;
                                            List<testInning> Tb = new ArrayList<testInning>();
                                            Tb.addAll(Ab);
                                            Tb.addAll(Bb);
                                            for(testInning ti : Tb){
                                                if(ti.getWinner().trim().contains("B/W") || ti.getWinner().trim().contains("C/L")){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().contains("B/L") || ti.getWinner().trim().contains("C/W")){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Tb", num+"/"+den);
                                        }
                                     
                                     
                                     %>
                                     
                                     <td colspan="${fn:length(t_teamoneBatFirst)}">
                                         ${Ab}
                                     </td>
                                     <td></td>
                                     <td colspan="${fn:length(t_teamtwoBowlFirst)}">
                                         ${Bb}
                                     </td>
                                     <td></td>
                                     <td colspan="${fn:length(t_groundFirst1X)}">
                                         ${Gb}
                                     </td>
                                 </tr>
                                 <tr>
                                     <td colspan="${fn:length(t_teamoneBatFirst)+fn:length(t_teamtwoBowlFirst)+1}">
                                         ${Tb}
                                     </td>
                                 </tr>
                                 
                                 <tr class="thead-dark">
                                     <th colspan="12">Sorted:</th>
                                     <th colspan="30">

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="C" id="C">Score calc.</span>
                                                </div>
                                                <input type="number" class="form-control" name="C">
                                            </div>
                                        </th>
                                </tr>
                                <tr>
                                <%

                                if(true){
                                    final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                    List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBatFirst"));
                                    Collections.sort(As, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("As", As);

                                    List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBowlFirst"));
                                    Collections.sort(Bs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("Bs", Bs);

                                    List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundFirst1X"));
                                    Collections.sort(Gs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("Gs", Gs);

                                    List<testInning> ASs = new ArrayList<testInning>();
                                    ASs.addAll(As);
                                    ASs.addAll(Bs);
                                    Collections.sort(ASs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("ASs", ASs);

                                }
                                %>
                                    <c:forEach var="in" items="${As}">
                                        <td name="C" class="A">
                                            ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Bs}">
                                        <td name="C" class="B">
                                           ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Gs}">
                                        <td name="C" class="G">
                                            ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(As)}" name="COdd" class="A">

                                    </td>
                                    <td>
                                    <td colspan="${fn:length(Bs)}" name="COdd" class="B">

                                    </td>

                                    <td>
                                    <td colspan="${fn:length(Gs)}" name="COdd" class="G">

                                    </td>
                                </tr>
                                <tr>
                                    <c:forEach var="in" items="${ASs}">
                                        <td name="C">
                                            ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(ASs)}" name="COdd" class="T"></td>
                                </tr>
                                    
                                </table>
                                    
                                    
                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamoneBatFirsttotruns_bt)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirsttotruns_bt)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirstInningtotruns_bt)}">${t_groundName} First Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatFirsttotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlFirsttotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFirstInningtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatFirsttotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlFirsttotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFirstInningtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirsttotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirsttotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirsttotruns_bt}">
                                            <td>
                                                ${ABatFirsttotruns_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirsttotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirsttotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirsttotruns_bt}">
                                            <td>
                                                ${BBowlFirsttotruns_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>
                                    

                            </div>
                        </div>
                    </c:when>
                    
                   
                    <c:when test="${loop.index == 3}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatFirst)}">${teamOne} Batting First Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirst)}">${teamTwo} Bowling First inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1X)}">${t_groundName} First Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td>
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td >
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1X}">
                                            <td>
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        


                                    </tr>

                                    
                                    
                                    
                                    <tr class="thead-dark">
                                        <th colspan="12">Sorted:</th>
                                        <th colspan="30">

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="Q" id="Q">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="Q">
                                            </div>
                                        </th>
                                    </tr>
                                    <tr>
                                    <%

                                    if(true){
                                        final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                        List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBatFirst"));
                                        Collections.sort(As, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFirstwicket()
                                                        - o2.getFirstwicket();
                                            }
                                        });
                                        pageContext.setAttribute("As", As);

                                        List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBowlFirst"));
                                        Collections.sort(Bs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFirstwicket()
                                                        - o2.getFirstwicket();
                                            }
                                        });
                                        pageContext.setAttribute("Bs", Bs);

                                        List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundFirst1X"));
                                        Collections.sort(Gs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFirstwicket()
                                                        - o2.getFirstwicket();
                                            }
                                        });
                                        pageContext.setAttribute("Gs", Gs);

                                        List<testInning> ASs = new ArrayList<testInning>();
                                        ASs.addAll(As);
                                        ASs.addAll(Bs);
                                        Collections.sort(ASs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFirstwicket()
                                                        - o2.getFirstwicket();
                                            }
                                        });
                                        pageContext.setAttribute("ASs", ASs);

                                    }
                                    %>
                                        <c:forEach var="in" items="${As}">
                                            <td name="Q" class="A">
                                                ${in.getFirstwicket() }
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Bs}">
                                            <td name="Q" class="B">
                                               ${in.getFirstwicket() }
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Gs}">
                                            <td name="Q" class="G">
                                                ${in.getFirstwicket() }
                                            </td>
                                        </c:forEach>
                                    </tr>

                                    <tr>
                                        <td colspan="${fn:length(As)}" name="QOdd" class="A">

                                        </td>
                                        <td>
                                        <td colspan="${fn:length(Bs)}" name="QOdd" class="B">

                                        </td>
                                        <td>
                                        <td colspan="${fn:length(Gs)}" name="QOdd" class="G">

                                        </td>
                                        
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${ASs}">
                                            <td name="Q">
                                                ${in.getFirstwicket() }
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td colspan="${fn:length(ASs)}" name="QOdd" class="T"></td>
                                    </tr>
                                    
                                    
                                    
                                </table>

                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamoneBatFirst1wicket_bt)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirst1wicket_bt)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirstInning1wicket_bt)}">${t_groundName} First Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatFirst1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlFirst1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFirstInning1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatFirst1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlFirst1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFirstInning1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirst1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirst1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirst1wicket_bt}">
                                            <td>
                                                ${ABatFirst1wicket_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirst1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirst1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirst1wicket_bt}">
                                            <td>
                                                ${BBowlFirst1wicket_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>

                    </c:when>
                    <c:when test="${loop.index==4}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatFirst)}">${teamOne} Batting First Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirst)}">${teamTwo} Bowling First Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst1X)}">${t_groundName} First Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatFirst}">
                                            <td >
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlFirst}">
                                            <td >
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst1X}">
                                            <td >
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>

                                    </tr>

                                    
                                    <tr  class="thead-dark">
                                        <th colspan="12">Sorted:</th>
                                        <th colspan="20">

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="E" id="E">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="E">
                                            </div>
                                        </th>
                                </tr>
                                <tr>
                                <%

                                if(true){
                                    final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                    List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBatFirst"));
                                    Collections.sort(As, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("As", As);

                                    List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBowlFirst"));
                                    Collections.sort(Bs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("Bs", Bs);

                                    List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundFirst1X"));
                                    Collections.sort(Gs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("Gs", Gs);

                                    List<testInning> ASs = new ArrayList<testInning>();
                                    ASs.addAll(As);
                                    ASs.addAll(Bs);
                                    Collections.sort(ASs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("ASs", ASs);

                                }
                                %>
                                    <c:forEach var="in" items="${As}">
                                        <td name="E" class="A">
                                            ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Bs}">
                                        <td name="E" class="B">
                                           ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Gs}">
                                        <td name="E" class="G">
                                            ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(As)}" name="EOdd" class="A">

                                    </td>
                                    <td>
                                    <td colspan="${fn:length(Bs)}" name="EOdd" class="B">

                                    </td>

                                    <td>
                                    <td colspan="${fn:length(Gs)}" name="EOdd" class="G">

                                    </td>
                                </tr>
                                <tr>
                                    <c:forEach var="in" items="${ASs}">
                                        <td name="E">
                                            ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(ASs)}" name="EOdd" class="T">

                                    </td>
                                </tr>

                                    
                                </table>

                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamoneBatFirstruns5_bt)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlFirstruns5_bt)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirstInningruns5_bt)}">${t_groundName} First Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatFirstruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlFirstruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFirstInningruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatFirstruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlFirstruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFirstInningruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirstruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirstruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatFirstruns5_bt}">
                                            <td>
                                                ${ABatFirst1wicket_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling First</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirstruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirstruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlFirstruns5_bt}">
                                            <td>
                                                ${BBowlFirstruns5_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>

                    </c:when>
                    
                </c:choose>
            </c:forEach>
            
            
            <h2>Second Inning</h2>
             <c:forEach var="h" begin="0" end="6" varStatus="loop">
                <c:choose>
                    <c:when test="${loop.index == 0}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamtwoBatSecond)}">${teamTwo} Batting Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlSecond)}">${teamOne} Bowling Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond1X)}">${t_groundName} Second Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatSecond}">
                                            <td >
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlSecond}">
                                            <td >
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond1X}">
                                            <td >
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        
                                    </tr>
                                    
                                    <tr>
                            <c:forEach var="in" items="${t_teamtwoBatSecond}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>
                            <c:forEach var="in" items="${t_teamoneBowlSecond}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                                <td></td>
                                <c:forEach var="in" items="${t_groundSecond1X}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            
                                 </tr>
                                 <tr>
                                     <%
                                     
                                        if(true){
                                            List<testInning> Ab = new ArrayList<testInning>
                                                    ((List<testInning>)request.getAttribute("t_teamtwoBatSecond"));
                                            List<testInning> Bb = new ArrayList<testInning>
                                                    ((List<testInning>)request.getAttribute("t_teamoneBowlSecond"));
                                            List<testInning> Gb = new ArrayList<testInning>
                                                    ((List<testInning>)request.getAttribute("t_groundSecond1X"));
                                            
                                            int num = 0;
                                            int den = 0;
                                            for(testInning ti : Ab){
                                                if(ti.getWinner().trim().contains("B/W") || ti.getWinner().trim().contains("C/L")){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().contains("B/L") || ti.getWinner().trim().contains("C/W")){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Ab", num+"/"+den);
                                            
                                            num = 0;
                                            den = 0;
                                            for(testInning ti : Bb){
                                                if(ti.getWinner().trim().contains("B/W") || ti.getWinner().trim().contains("C/L")){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().contains("B/L") || ti.getWinner().trim().contains("C/W")){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Bb", num+"/"+den);
                                            
                                            num = 0;
                                            den = 0;
                                            for(testInning ti : Gb){
                                                if(ti.getWinner().trim().charAt(0) == 'B'){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().charAt(0) == 'C'){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Gb", num+"/"+den);
                                            
                                            num = 0;
                                            den = 0;
                                            List<testInning> Tb = new ArrayList<testInning>();
                                            Tb.addAll(Ab);
                                            Tb.addAll(Bb);
                                            for(testInning ti : Tb){
                                                if(ti.getWinner().trim().contains("B/W") || ti.getWinner().trim().contains("C/L")){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().contains("B/L") || ti.getWinner().trim().contains("C/W")){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Tb", num+"/"+den);
                                        }
                                     
                                     
                                     %>
                                     
                                     <td colspan="${fn:length(t_teamtwoBatSecond)}">
                                         ${Ab}
                                     </td>
                                     <td></td>
                                     <td colspan="${fn:length(t_teamoneBowlSecond)}">
                                         ${Bb}
                                     </td>
                                     <td></td>
                                     <td colspan="${fn:length(t_groundSecond1X)}">
                                         ${Gb}
                                     </td>
                                 </tr>
                                 <tr>
                                     <td colspan="${fn:length(t_teamtwoBatSecond)+fn:length(t_teamoneBowlSecond)+1}">
                                         ${Tb}
                                     </td>
                                 </tr>
                                 
                                 
                                 <tr  class="thead-dark">
                                     <th colspan="12">Sorted:</th>
                                     <th colspan="20">
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text" name="F" id="F">@</span>
                                            </div>
                                            <input type="number" class="form-control" name="F">
                                        </div>
                                        <!--<input type="number" id="${loop.index}Left" >-->
                                    </th>
                               </tr>
                                <tr>
                                <%

                                if(true){
                                    final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                    List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBatSecond"));
                                    Collections.sort(As, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("As", As);

                                    List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBowlSecond"));
                                    Collections.sort(Bs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("Bs", Bs);

                                    List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundSecond1X"));
                                    Collections.sort(Gs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("Gs", Gs);

                                    List<testInning> ASs = new ArrayList<testInning>();
                                    ASs.addAll(As);
                                    ASs.addAll(Bs);
                                    Collections.sort(ASs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("ASs", ASs);

                                }
                                %>
                                    <c:forEach var="in" items="${As}">
                                        <td name="F" class="A">
                                            ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Bs}">
                                        <td name="F" class="B">
                                           ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                    <td >
                                    <c:forEach var="in" items="${Gs}">
                                        <td name="F" class="G">
                                            ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                        <td colspan="${fn:length(As)}" name="FOdd" class="A">

                                        </td>
                                    <td>
                                        <td colspan="${fn:length(Bs)}" name="FOdd" class="B">

                                        </td>

                                    <td>
                                        <td colspan="${fn:length(Gs)}" name="FOdd" class="G">

                                        </td>


                                </tr>
                                <tr>
                                    <c:forEach var="in" items="${ASs}">
                                        <td name="F">
                                            ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(ASs)}" name="FOdd" class="T"></td>
                                </tr>
                                
                                </table>
                                    
                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamtwoBatSecondtotruns_bt)}">${teamTwo} Batting Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlSecondtotruns_bt)}">${teamOne} Bowling Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecondInningtotruns_bt)}">${t_groundName} Second Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamtwoBatSecondtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamoneBowlSecondtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundSecondInningtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamtwoBatSecondtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamoneBowlSecondtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundSecondInningtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamTwo} Batting Second Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatSecondtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatSecondtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatSecondtotruns_bt}">
                                            <td>
                                                ${BBatSecondtotruns_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamOne} Bowling Second Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlSecondtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlSecondtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlSecondtotruns_bt}">
                                            <td>
                                                ${ABowlSecondtotruns_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>
                    </c:when>
                    
                    
                    <c:when test="${loop.index == 3}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamtwoBatSecond)}">${teamTwo} Batting Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlSecond)}">${teamOne} Bowling Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond1X)}">${t_groundName} Second Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatSecond}">
                                            <td >
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlSecond}">
                                            <td >
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond1X}">
                                            <td >
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        


                                    </tr>

                                    
                                    
                                    
                                    <tr class="thead-dark">
                                        <th colspan="12">Sorted:</th>
                                        <th colspan="20">
                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="G" id="G">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="G">
                                            </div>
                                        </th>
                                    </tr>
                                    <tr>
                                    <%

                                    if(true){
                                        final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                        List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBatSecond"));
                                        Collections.sort(As, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFirstwicket()
                                                        - o2.getFirstwicket();
                                            }
                                        });
                                        pageContext.setAttribute("As", As);

                                        List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBowlSecond"));
                                        Collections.sort(Bs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFirstwicket()
                                                        - o2.getFirstwicket();
                                            }
                                        });
                                        pageContext.setAttribute("Bs", Bs);

                                        List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundSecond1X"));
                                        Collections.sort(Gs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFirstwicket()
                                                        - o2.getFirstwicket();
                                            }
                                        });
                                        pageContext.setAttribute("Gs", Gs);

                                        List<testInning> ASs = new ArrayList<testInning>();
                                        ASs.addAll(As);
                                        ASs.addAll(Bs);
                                        Collections.sort(ASs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFirstwicket()
                                                        - o2.getFirstwicket();
                                            }
                                        });
                                        pageContext.setAttribute("ASs", ASs);

                                    }
                                    %>
                                        <c:forEach var="in" items="${As}">
                                            <td name="G" class="A">
                                                ${in.getFirstwicket() }
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Bs}">
                                            <td name="G" class="B">
                                               ${in.getFirstwicket() }
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Gs}">
                                            <td name="G" class="G">
                                                ${in.getFirstwicket() }
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td colspan="${fn:length(As)}" name="GOdd" class="A">

                                        </td>
                                        <td>
                                        <td colspan="${fn:length(Bs)}" name="GOdd" class="B">

                                        </td>
                                            
                                        <td>
                                        <td colspan="${fn:length(Gs)}" name="GOdd" class="G">

                                        </td>



                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${ASs}">
                                            <td name="G">
                                                ${in.getFirstwicket() }
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td colspan="${fn:length(ASs)}" name="GOdd" class="T"></td>
                                    </tr>
                                    
                                    
                                </table>

                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamtwoBatSecond1wicket_bt)}">${teamTwo} Batting Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlSecond1wicket_bt)}">${teamOne} Bowling Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecondInning1wicket_bt)}">${t_groundName} Second Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamtwoBatSecond1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamoneBowlSecond1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundSecondInning1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamtwoBatSecond1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamoneBowlSecond1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundSecondInning1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamTwo} Batting Second Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatSecond1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatSecond1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatSecond1wicket_bt}">
                                            <td>
                                                ${BBatSecond1wicket_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamOne} Bowling Second Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlSecond1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlSecond1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlSecond1wicket_bt}">
                                            <td>
                                                ${ABowlSecond1wicket_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>

                    </c:when>
                    <c:when test="${loop.index==4}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamtwoBatSecond)}">${teamTwo} Batting Second</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlSecond)}">${teamOne} Bowling Second</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond1X)}">${t_groundName} Second Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatSecond}">
                                            <td>
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlSecond}">
                                            <td>
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond1X}">
                                            <td>
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        
                                    </tr>

                                    
                                    <tr class="thead-dark">
                                        <th colspan="12">Sorted:
                                        <th colspan="20">
                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="H" id="H">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="H">
                                            </div>
                                        </th>
                                </tr>
                                <tr>
                                <%

                                if(true){
                                    final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                    List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBatSecond"));
                                    Collections.sort(As, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("As", As);

                                    List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBowlSecond"));
                                    Collections.sort(Bs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("Bs", Bs);

                                    List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundSecond1X"));
                                    Collections.sort(Gs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("Gs", Gs);

                                    List<testInning> ASs = new ArrayList<testInning>();
                                    ASs.addAll(As);
                                    ASs.addAll(Bs);
                                    Collections.sort(ASs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("ASs", ASs);

                                }
                                %>
                                    <c:forEach var="in" items="${As}">
                                        <td name="H" class="A">
                                            ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Bs}">
                                        <td name="H" class="B">
                                           ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Gs}">
                                        <td name="H" class="G">
                                            ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(As)}" name="HOdd" class="A">

                                    </td>
                                    <td>
                                    <td colspan="${fn:length(Bs)}" name="HOdd" class="B">

                                    </td>
                                    
                                    <td>
                                    <td colspan="${fn:length(Gs)}" name="HOdd" class="G">

                                    </td>

                                </tr>
                                <tr>
                                    <c:forEach var="in" items="${ASs}">
                                        <td name="H">
                                            ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr >
                                    <td colspan="${fn:length(ASs)}" name="HOdd" class="T">
                                </tr>
                                    
                                    
                                </table>

                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamtwoBatSecondruns5_bt)}">${teamTwo} Batting Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlSecondruns5_bt)}">${teamOne} Bowling Second Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecondInningruns5_bt)}">${t_groundName} Second Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamtwoBatSecondruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamoneBowlSecondruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundSecondInningruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamtwoBatSecondruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamoneBowlSecondruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundSecondInningruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamTwo} Batting Second Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatSecondruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatSecondruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatSecondruns5_bt}">
                                            <td>
                                                ${BBatSecondruns5_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamOne} Bowling Second Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlSecondruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlSecondruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlSecondruns5_bt}">
                                            <td>
                                                ${ABowlSecondruns5_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>

                    </c:when>
                    
                </c:choose>
            </c:forEach>
            
            <h2>Third Inning</h2>
            <c:forEach var="h" begin="0" end="6" varStatus="loop">
                <c:choose>
                    <c:when test="${loop.index == 0}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatThird)}">${teamOne} Batting Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlThird)}">${teamTwo} Bowling Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst2X)}">${t_groundName} Third</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst2X}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        


                                    </tr>
                                    
                                    <tr>
                            <c:forEach var="in" items="${t_teamoneBatThird}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>
                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>
                            
                                <c:forEach var="in" items="${t_groundFirst2X}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>


                                 </tr>
                                 <tr>
                                     <%
                                     
                                        if(true){
                                            List<testInning> Ab = new ArrayList<testInning>
                                                    ((List<testInning>)request.getAttribute("t_teamoneBatThird"));
                                            List<testInning> Bb = new ArrayList<testInning>
                                                    ((List<testInning>)request.getAttribute("t_teamtwoBowlThird"));
                                            List<testInning> Gb = new ArrayList<testInning>
                                                    ((List<testInning>)request.getAttribute("t_groundFirst2X"));
                                            
                                            int num = 0;
                                            int den = 0;
                                            for(testInning ti : Ab){
                                                if(ti.getWinner().trim().contains("B/W") || ti.getWinner().trim().contains("C/L")){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().contains("B/L") || ti.getWinner().trim().contains("C/W")){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Ab", num+"/"+den);
                                            
                                            num = 0;
                                            den = 0;
                                            for(testInning ti : Bb){
                                                if(ti.getWinner().trim().contains("B/W") || ti.getWinner().trim().contains("C/L")){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().contains("B/L") || ti.getWinner().trim().contains("C/W")){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Bb", num+"/"+den);
                                            
                                            num = 0;
                                            den = 0;
                                            for(testInning ti : Gb){
                                                if(ti.getWinner().trim().charAt(0) == 'B'){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().charAt(0) == 'C'){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Gb", num+"/"+den);
                                            
                                            num = 0;
                                            den = 0;
                                            List<testInning> Tb = new ArrayList<testInning>();
                                            Tb.addAll(Ab);
                                            Tb.addAll(Bb);
                                            for(testInning ti : Tb){
                                                if(ti.getWinner().trim().contains("B/W") || ti.getWinner().trim().contains("C/L")){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().contains("B/L") || ti.getWinner().trim().contains("C/W")){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Tb", num+"/"+den);
                                        }
                                     
                                     
                                     %>
                                     
                                     <td colspan="${fn:length(t_teamoneBatThird)}">
                                         ${Ab}
                                     </td>
                                     <td></td>
                                     <td colspan="${fn:length(t_teamtwoBowlThird)}">
                                         ${Bb}
                                     </td>
                                     <td></td>
                                     <td colspan="${fn:length(t_groundFirst2X)}">
                                         ${Gb}
                                     </td>
                                 </tr>
                                 <tr>
                                     <td colspan="${fn:length(t_teamoneBatThird)+fn:length(t_teamtwoBowlThird)+1}">
                                         ${Tb}
                                     </td>
                                 </tr>
                                 
                                 
                                 <tr class="thead-dark">
                                    <th colspan="12">Sorted:
                                    <th colspan="20">

                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text" name="I" id="I">@</span>
                                            </div>
                                            <input type="number" class="form-control" name="I">
                                        </div>
                                    </th>
                                </tr>
                                <tr>
                                <%

                                if(true){
                                    final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                    List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBatThird"));
                                    Collections.sort(As, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("As", As);

                                    List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBowlThird"));
                                    Collections.sort(Bs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("Bs", Bs);

                                    List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundFirst2X"));
                                    Collections.sort(Gs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("Gs", Gs);

                                    List<testInning> ASs = new ArrayList<testInning>();
                                    ASs.addAll(As);
                                    ASs.addAll(Bs);
                                    Collections.sort(ASs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("ASs", ASs);

                                }
                                %>
                                    <c:forEach var="in" items="${As}">
                                        <td name="I" class="A">
                                            ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Bs}">
                                        <td name="I" class="B">
                                           ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Gs}">
                                        <td name="I" class="G">
                                            ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(As)}" name="IOdd" class="A">

                                    </td>
                                    <td>
                                    <td colspan="${fn:length(Bs)}" name="IOdd" class="B">

                                    </td>
                                    <td>
                                    <td colspan="${fn:length(Gs)}" name="IOdd" class="G">

                                    </td>
                                </tr>
                                <tr>
                                    <c:forEach var="in" items="${ASs}">
                                        <td name="I">
                                            ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(ASs)}" name="IOdd" class="T">
                                </tr>
                                 
                                </table>
                                    
                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamoneBatThirdtotruns_bt)}">${teamOne} Batting Third Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlThirdtotruns_bt)}">${teamTwo} Bowling Third Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundThirdInningtotruns_bt)}">${t_groundName} Third Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatThirdtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlThirdtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundThirdInningtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatThirdtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlThirdtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundThirdInningtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting Third</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatThirdtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatThirdtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatThirdtotruns_bt}">
                                            <td>
                                                ${ABatThirdtotruns_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling Third</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlThirdtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlThirdtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlThirdtotruns_bt}">
                                            <td>
                                                ${BBowlThirdtotruns_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>
                    </c:when>
                    
                    <c:when test="${loop.index == 3}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatThird)}">${teamOne} Batting Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlThird)}">${teamTwo} Bowling Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst2X)}">${t_groundName} Third</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td>
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td  >
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst2X}">
                                            <td>
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        
                                    </tr>

                                    
                                    <tr class="thead-dark">
                                        <th colspan="12">Sorted:
                                        <th colspan="20">

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="J" id="J">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="J">
                                            </div>
                                        </th>
                                    </tr>
                                    <tr>
                                    <%

                                    if(true){
                                        final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                        List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBatThird"));
                                        Collections.sort(As, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFirstwicket()
                                                        - o2.getFirstwicket();
                                            }
                                        });
                                        pageContext.setAttribute("As", As);

                                        List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBowlThird"));
                                        Collections.sort(Bs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFirstwicket()
                                                        - o2.getFirstwicket();
                                            }
                                        });
                                        pageContext.setAttribute("Bs", Bs);

                                        List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundFirst2X"));
                                        Collections.sort(Gs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFirstwicket()
                                                        - o2.getFirstwicket();
                                            }
                                        });
                                        pageContext.setAttribute("Gs", Gs);

                                        List<testInning> ASs = new ArrayList<testInning>();
                                        ASs.addAll(As);
                                        ASs.addAll(Bs);
                                        Collections.sort(ASs, new Comparator<testInning>() {
                                            @Override
                                            public int compare(testInning o1, testInning o2) {
                                                return o1.getFirstwicket()
                                                        - o2.getFirstwicket();
                                            }
                                        });
                                        pageContext.setAttribute("ASs", ASs);

                                    }
                                    %>
                                        <c:forEach var="in" items="${As}">
                                            <td name="J" class="A">
                                                ${in.getFirstwicket() }
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Bs}">
                                            <td name="J" class="B">
                                               ${in.getFirstwicket() }
                                            </td>
                                        </c:forEach>
                                        <td>
                                        <c:forEach var="in" items="${Gs}">
                                            <td name="J" class="G">
                                                ${in.getFirstwicket() }
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td colspan="${fn:length(As)}" name="JOdd" class="A">

                                        </td>
                                        <td>
                                        <td colspan="${fn:length(Bs)}" name="JOdd" class="B">

                                        </td>
                                            
                                        <td>
                                        <td colspan="${fn:length(Gs)}" name="JOdd" class="G">

                                        </td>
                                        
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${ASs}">
                                            <td name="J">
                                                ${in.getFirstwicket() }
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <td colspan="${fn:length(ASs)}" name="JOdd" class="T">
                                    </tr>
                                    
                                    
                                </table>

                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamoneBatThird1wicket_bt)}">${teamOne} Batting Third Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlThird1wicket_bt)}">${teamTwo} Bowling Third Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundThirdInning1wicket_bt)}">${t_groundName} Third Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatThird1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlThird1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundThirdInning1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatThird1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlThird1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundThirdInning1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting Third</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatThird1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatThird1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatThird1wicket_bt}">
                                            <td>
                                                ${ABatThird1wicket_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling Third</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlThird1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlThird1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlThird1wicket_bt}">
                                            <td>
                                                ${BBowlThird1wicket_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>

                    </c:when>
                    <c:when test="${loop.index==4}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamoneBatThird)}">${teamOne} Batting Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlThird)}">${teamTwo} Bowling Third</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFirst2X)}">${t_groundName} Third</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamoneBatThird}">
                                            <td>
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamtwoBowlThird}">
                                            <td>
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundFirst2X}">
                                            <td>
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        
                                    </tr>
                                    <tr class="thead-dark">
                                    <th colspan="12">Sorted:
                                    <th colspan="20">
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text" name="K" id="K">@</span>
                                            </div>
                                            <input type="number" class="form-control" name="K">
                                        </div>
                                        <!--<input type="number" id="K" >-->
                                    </th>
                                </tr>
                                <tr>
                                <%

                                if(true){
                                    final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                    List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBatThird"));
                                    Collections.sort(As, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("As", As);

                                    List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBowlThird"));
                                    Collections.sort(Bs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("Bs", Bs);

                                    List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundFirst2X"));
                                    Collections.sort(Gs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("Gs", Gs);

                                    List<testInning> ASs = new ArrayList<testInning>();
                                    ASs.addAll(As);
                                    ASs.addAll(Bs);
                                    Collections.sort(ASs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("ASs", ASs);

                                }
                                %>
                                    <c:forEach var="in" items="${As}">
                                        <td name="K" class="A">
                                            ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Bs}">
                                        <td name="K" class="B">
                                           ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Gs}">
                                        <td name="K" class="G">
                                            ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(As)}" name="KOdd" class="A">

                                    </td>
                                    <td>
                                    <td colspan="${fn:length(Bs)}" name="KOdd" class="B">

                                    </td>
                                    <td>
                                    <td colspan="${fn:length(Gs)}" name="KOdd" class="G">

                                    </td>

                                </tr>
                                <tr>
                                    <c:forEach var="in" items="${ASs}">
                                        <td name="K">
                                            ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(ASs)}" name="KOdd" class="T">
                                </tr>
                                    
                                </table>

                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamoneBatThirdruns5_bt)}">${teamOne} Batting Third Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamtwoBowlThirdruns5_bt)}">${teamTwo} Bowling Third Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundThirdInningruns5_bt)}">${t_groundName} Third Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatThirdruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlThirdruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundThirdInningruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamoneBatThirdruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamtwoBowlThirdruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundThirdInningruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamOne} Batting Third</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatThirdruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatThirdruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABatThirdruns5_bt}">
                                            <td>
                                                ${ABatThirdruns5_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamTwo} Bowling Third</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlThirdruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlThirdruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBowlThirdruns5_bt}">
                                            <td>
                                                ${BBowlThirdruns5_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>

                    </c:when>
                    
                </c:choose>
            </c:forEach>
            
            <h2>Fourth Inning</h2>
            <c:forEach var="h" begin="0" end="6" varStatus="loop">
                <c:choose>
                    <c:when test="${loop.index == 0}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamtwoBatFourth)}">${teamTwo} Batting Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlFourth)}">${teamOne} Bowling Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond2X)}">${t_groundName} Fourth</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatFourth}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlFourth}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond2X}">
                                            <td>
                                                ${in.getTotalruns()}
                                            </td>
                                        </c:forEach>

                                    </tr>
                                    
                                    <tr>
                            <c:forEach var="in" items="${t_teamtwoBatFourth}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            <td></td>
                            <c:forEach var="in" items="${t_teamoneBowlFourth}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                                <td></td>
                            
                                <c:forEach var="in" items="${t_groundSecond2X}">
                                <td>
                                    ${in.getWinner()}
                                </td>
                            </c:forEach>
                            
                                 </tr>
                                 <tr>
                                     <%
                                     
                                        if(true){
                                            List<testInning> Ab = new ArrayList<testInning>
                                                    ((List<testInning>)request.getAttribute("t_teamtwoBatFourth"));
                                            List<testInning> Bb = new ArrayList<testInning>
                                                    ((List<testInning>)request.getAttribute("t_teamoneBowlFourth"));
                                            List<testInning> Gb = new ArrayList<testInning>
                                                    ((List<testInning>)request.getAttribute("t_groundSecond2X"));
                                            
                                            int num = 0;
                                            int den = 0;
                                            for(testInning ti : Ab){
                                                if(ti.getWinner().trim().contains("B/W") || ti.getWinner().trim().contains("C/L")){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().contains("B/L") || ti.getWinner().trim().contains("C/W")){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Ab", num+"/"+den);
                                            
                                            num = 0;
                                            den = 0;
                                            for(testInning ti : Bb){
                                                if(ti.getWinner().trim().contains("B/W") || ti.getWinner().trim().contains("C/L")){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().contains("B/L") || ti.getWinner().trim().contains("C/W")){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Bb", num+"/"+den);
                                            
                                            num = 0;
                                            den = 0;
                                            for(testInning ti : Gb){
                                                if(ti.getWinner().trim().charAt(0) == 'B'){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().charAt(0) == 'C'){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Gb", num+"/"+den);
                                            
                                            num = 0;
                                            den = 0;
                                            List<testInning> Tb = new ArrayList<testInning>();
                                            Tb.addAll(Ab);
                                            Tb.addAll(Bb);
                                            for(testInning ti : Tb){
                                                if(ti.getWinner().trim().contains("B/W") || ti.getWinner().trim().contains("C/L")){
                                                    num++;
                                                }
                                                else if(ti.getWinner().trim().contains("B/L") || ti.getWinner().trim().contains("C/W")){
                                                    den++;
                                                }
                                            }
                                            pageContext.setAttribute("Tb", num+"/"+den);
                                        }
                                     
                                     
                                     %>
                                     
                                     <td colspan="${fn:length(t_teamtwoBatFourth)}">
                                         ${Ab}
                                     </td>
                                     <td></td>
                                     <td colspan="${fn:length(t_teamoneBowlFourth)}">
                                         ${Bb}
                                     </td>
                                     <td></td>
                                     <td colspan="${fn:length(t_groundSecond2X)}">
                                         ${Gb}
                                     </td>
                                 </tr>
                                 <tr>
                                     <td colspan="${fn:length(t_teamtwoBatFourth)+fn:length(t_teamoneBowlFourth)+1}">
                                         ${Tb}
                                     </td>
                                 </tr>
                                 
                                 
                                 <tr class="thead-dark">
                                    <th colspan="12">Sorted:
                                    <th colspan="20">

                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text" name="L" id="L">@</span>
                                            </div>
                                            <input type="number" class="form-control" name="L">
                                        </div>
                                        <!--<input type="number" id="L" >-->
                                    </th>
                                </tr>
                                <tr>
                                <%

                                if(true){
                                    final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                    List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBatFourth"));
                                    Collections.sort(As, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("As", As);

                                    List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBowlFourth"));
                                    Collections.sort(Bs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("Bs", Bs);

                                    List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundSecond2X"));
                                    Collections.sort(Gs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("Gs", Gs);

                                    List<testInning> ASs = new ArrayList<testInning>();
                                    ASs.addAll(As);
                                    ASs.addAll(Bs);
                                    Collections.sort(ASs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getTotalruns()
                                                    - o2.getTotalruns();
                                        }
                                    });
                                    pageContext.setAttribute("ASs", ASs);

                                }
                                %>
                                    <c:forEach var="in" items="${As}">
                                        <td name="L" class="A">
                                            ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Bs}">
                                        <td name="L" class="B">
                                           ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Gs}">
                                        <td name="L" class="G">
                                            ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr> 
                                    <td colspan="${fn:length(As)}" name="LOdd" class="A">

                                    </td> 
                                    <td> 
                                    <td colspan="${fn:length(Bs)}" name="LOdd" class="B">

                                    </td> 
                                    <td> 
                                    <td colspan="${fn:length(Gs)}" name="LOdd" class="G">

                                    </td> 

                                </tr>
                                <tr>
                                    <c:forEach var="in" items="${ASs}">
                                        <td name="L">
                                            ${in.getTotalruns() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(ASs)}" name="LOdd" class="T">
                                </tr>
                                 
                                 
                                </table>
                                    
                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamtwoBatFourthtotruns_bt)}">${teamTwo} Batting Fourth Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlFourthtotruns_bt)}">${teamOne} Bowling Fourth Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFourthInningtotruns_bt)}">${t_groundName} Fourth Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamtwoBatFourthtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamoneBowlFourthtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFourthInningtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamtwoBatFourthtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamoneBowlFourthtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFourthInningtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamTwo} Batting Fourth Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatFourthtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatFourthtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatFourthtotruns_bt}">
                                            <td>
                                                ${BBatFourthtotruns_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamOne} Bowling Fourth Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlFourthtotruns_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlFourthtotruns_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlFourthtotruns_bt}">
                                            <td>
                                                ${ABowlFourthtotruns_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>
                    </c:when>
                    
                    
                    <c:when test="${loop.index == 3}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>
                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamtwoBatFourth)}">${teamTwo} Batting Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlFourth)}">${teamOne} Bowling Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond2X)}">${t_groundName} Fourth</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatFourth}">
                                            <td>
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlFourth}">
                                            <td >
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond2X}">
                                            <td>
                                                ${in.getFirstwicket()}
                                            </td>
                                        </c:forEach>
                                        
                                    </tr>

                                    <tr class="thead-dark">
                                    <th colspan="12">Sorted:
                                        <th colspan="20">

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="M" id="M">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="M">
                                            </div>
                                            <!--<input type="number" id="M" >-->
                                        </th>
                                </tr>
                                <tr>
                                <%

                                if(true){
                                    final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                    List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBatFourth"));
                                    Collections.sort(As, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getFirstwicket()
                                                    - o2.getFirstwicket();
                                        }
                                    });
                                    pageContext.setAttribute("As", As);

                                    List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBowlFourth"));
                                    Collections.sort(Bs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getFirstwicket()
                                                    - o2.getFirstwicket();
                                        }
                                    });
                                    pageContext.setAttribute("Bs", Bs);

                                    List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundSecond2X"));
                                    Collections.sort(Gs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getFirstwicket()
                                                    - o2.getFirstwicket();
                                        }
                                    });
                                    pageContext.setAttribute("Gs", Gs);

                                    List<testInning> ASs = new ArrayList<testInning>();
                                    ASs.addAll(As);
                                    ASs.addAll(Bs);
                                    Collections.sort(ASs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getFirstwicket()
                                                    - o2.getFirstwicket();
                                        }
                                    });
                                    pageContext.setAttribute("ASs", ASs);

                                }
                                %>
                                    <c:forEach var="in" items="${As}">
                                        <td name="M" class="A">
                                            ${in.getFirstwicket() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Bs}">
                                        <td name="M" class="B">
                                           ${in.getFirstwicket() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Gs}">
                                        <td name="M" class="G">
                                            ${in.getFirstwicket() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(As)}" name="MOdd" class="A">

                                    </td>
                                    <td>
                                    <td colspan="${fn:length(Bs)}" name="MOdd" class="B">

                                    </td>
                                    <td>
                                    <td colspan="${fn:length(Gs)}" name="MOdd" class="G">

                                    </td>

                                </tr>
                                <tr>
                                    <c:forEach var="in" items="${ASs}">
                                        <td name="M">
                                            ${in.getFirstwicket() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(ASs)}" name="MOdd" class="T">
                                </tr>
                                    
                                    
                                </table>

                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamtwoBatFourth1wicket_bt)}">${teamTwo} Batting Fourth Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlFourth1wicket_bt)}">${teamOne} Bowling Fourth Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFourthInning1wicket_bt)}">${t_groundName} Fourth Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamtwoBatFourth1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamoneBowlFourth1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFourthInning1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamtwoBatFourth1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamoneBowlFourth1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFourthInning1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamTwo} Batting Fourth Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatFourth1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatFourth1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatFourth1wicket_bt}">
                                            <td>
                                                ${BBatFourth1wicket_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamOne} Bowling Fourth Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlFourth1wicket_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlFourth1wicket_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlFourth1wicket_bt}">
                                            <td>
                                                ${ABowlFourth1wicket_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>

                    </c:when>
                    <c:when test="${loop.index==4}">
                        <div class="row" >
                            <div class ="col-12">
                                <h5 class="card-title">${headers.get(loop.index)}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(t_teamtwoBatFourth)}">${teamTwo} Batting Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlFourth)}">${teamOne} Bowling Fourth</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundSecond2X)}">${t_groundName} Fourth</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${t_teamtwoBatFourth}">
                                            <td >
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_teamoneBowlFourth}">
                                            <td>
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        <td>
                                            <c:forEach var="in" items="${t_groundSecond2X}">
                                            <td>
                                                ${in.getRuns5wicket()}
                                            </td>
                                        </c:forEach>
                                        
                                    </tr>

                                    <tr class="thead-dark">
                                    <th colspan="12">Sorted:
                                        <th colspan="20">

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="N" id="N">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="N">
                                            </div>
                                            <!--<input type="number" id="N" >-->
                                        </th>
                                </tr>
                                <tr>
                                <%

                                if(true){
                                    final int ind = ((LoopTagStatus)pageContext.getAttribute("loop")).getIndex();
                                    List<testInning> As = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamtwoBatFourth"));
                                    Collections.sort(As, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("As", As);

                                    List<testInning> Bs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_teamoneBowlFourth"));
                                    Collections.sort(Bs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("Bs", Bs);

                                    List<testInning> Gs = new ArrayList<testInning>((List<testInning>)request.getAttribute("t_groundSecond2X"));
                                    Collections.sort(Gs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("Gs", Gs);

                                    List<testInning> ASs = new ArrayList<testInning>();
                                    ASs.addAll(As);
                                    ASs.addAll(Bs);
                                    Collections.sort(ASs, new Comparator<testInning>() {
                                        @Override
                                        public int compare(testInning o1, testInning o2) {
                                            return o1.getRuns5wicket()
                                                    - o2.getRuns5wicket();
                                        }
                                    });
                                    pageContext.setAttribute("ASs", ASs);

                                }
                                %>
                                    <c:forEach var="in" items="${As}">
                                        <td name="N" class="A">
                                            ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Bs}">
                                        <td name="N" class="B">
                                           ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                    <td>
                                    <c:forEach var="in" items="${Gs}">
                                        <td name="N" class="G">
                                            ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(As)}" name="NOdd" class="A">

                                    </td>
                                    <td>
                                    <td colspan="${fn:length(Bs)}" name="NOdd" class="B">

                                    </td>
                                    <td>
                                    <td colspan="${fn:length(Gs)}" name="NOdd" class="G">

                                    </td>

                                </tr>
                                <tr>
                                    <c:forEach var="in" items="${ASs}">
                                        <td name="N">
                                            ${in.getRuns5wicket() }
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <td colspan="${fn:length(ASs)}" name="NOdd" class="T">
                                </tr>
                                    
                                </table>

                                <table class="table table-bordered">
                                    <tr class="thead-light">
                                        <th colspan="30">Back testing:
                                            <div class="btn btn-light" onclick="collapseSwitch(this)">
                                            <svg class="bi bi-arrow-down-up" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M11 3.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V4a.5.5 0 0 1 .5-.5z"/>
                                            <path fill-rule="evenodd" d="M10.646 2.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L11 3.707 8.354 6.354a.5.5 0 1 1-.708-.708l3-3zm-9 7a.5.5 0 0 1 .708 0L5 12.293l2.646-2.647a.5.5 0 1 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 0-.708z"/>
                                            <path fill-rule="evenodd" d="M5 2.5a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-1 0V3a.5.5 0 0 1 .5-.5z"/>
                                          </svg>
                                        </div>
                                    </tr>
                                    <tr>
                                        <th colspan="${fn:length(t_teamtwoBatFourthruns5_bt)}">${teamTwo} Batting Fourth Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_teamoneBowlFourthruns5_bt)}">${teamOne} Bowling Fourth Inning</th>
                                        <th>
                                        <th colspan="${fn:length(t_groundFourthInningruns5_bt)}">${t_groundName} Fourth Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamtwoBatFourthruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamoneBowlFourthruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFourthInningruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${t_teamtwoBatFourthruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_teamoneBowlFourthruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                            
                                        <td></td>
                                        
                                        <c:forEach var="entry" items="${t_groundFourthInningruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                                    
                                
                                <table class="table table-bordered">
                                    <tr>
                                        <th colspan="30">${teamTwo} Batting Fourth Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatFourthruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatFourthruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${BBatFourthruns5_bt}">
                                            <td>
                                                ${BBatFourthruns5_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    
                                    <tr>
                                        <td colspan="30"></td>
                                    </tr>
                                    
                                    <tr>
                                        <th colspan="30">${teamOne} Bowling Fourth Inning</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlFourthruns5_bt}">
                                            <td>
                                                ${entry.key}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlFourthruns5_bt}">
                                            <td>
                                                ${entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <tr>
                                        <c:forEach var="entry" items="${ABowlFourthruns5_bt}">
                                            <td>
                                                ${ABowlFourthruns5_bt.get("N") - entry.value}
                                            </td>
                                        </c:forEach>
                                    </tr>
                               </table>

                            </div>
                        </div>

                    </c:when>
                    
                </c:choose>
            </c:forEach>
        </div>





        <script type="text/javascript">
//            $(document).ready(function () {
//
//                var classes = ["-Left","-1Left","A","B","C","D","E","F","G","H","I","J","K","L","M","N",
//                    "CGr","DGr","EGr","FGr","GGr","HGr","IGr","JGr","KGr","LGr","MGr","NGr"];
//
//                var i;
//                for (i = 0; i < classes.length; i++) {
//                    var nam = classes[i];
//
//                    var ind = 0;
//
//                    $('td[name="' + nam + 'Odd"]').each(function (i, obj) {
//                        var val = parseInt($('td[name="' + nam + '"]').eq(i).text());
//
//
//
//
//                        var num = 0;
//                        var den = 1;
//
//                        $('td[name="' + nam + '"]').each(function () {
//                            if (parseInt($(this).text()) === -1) {
//
//                            } else if (parseInt($(this).text()) >= parseInt(val)) {
//                                num++;
//                            } else if (parseInt($(this).text()) < parseInt(val)) {
////                                alert($(this).text() +" :: "+ val);
//                                den++;
//                            }
//
//                        });
//
//                        $(this).text(den.toString() + "/" + num.toString());
//
//
//
//
//
//                    });
//
//                }
//            });

            $(document).ready(function () {
                $("td,th").not('.A').not('.B').not('.G').not('.T').each(function(){
                    var inp = $(this).text().trim();
                    if(inp === ""){
                        $(this).addClass("grey-div");
                    }
                });
                $('div.btn').click()
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
//                        if (parseInt($(this).text()) === -1) {
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
