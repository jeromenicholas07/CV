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
             
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">Fours</h3>
                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(FST_A)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(FST_B)}">${teamTwo}</th>
                            <th>
                            <th colspan="${fn:length(Gr_First)}">${groundName}</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${FST_A}">
                                <td >
                                    ${in.getParams().get(4)}
                                </td>
                            </c:forEach>
                            <td>
                                <c:forEach var="in" items="${FST_B}">
                                <td>
                                    ${in.getParams().get(4)}
                                </td>
                            </c:forEach>
                            <td>
                                <c:forEach var="in" items="${Gr_First}">
                                <td >
                                    ${in.getParams().get(4)}
                                </td>
                            </c:forEach>
                        </tr>

                        <tr class="thead-dark">
                            <th colspan="12">Sorted:</th>
                            <th colspan="20">

                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="4Left" 
                                              id="4Left">Score calc.</span>
                                    </div>
                                    <input type="number" class="form-control" name="4Left">
                                </div>
                                <!--<input type="number" id="4Left" >-->
                            </th>
                        </tr>
                        <tr>
                        <%

                        if(true){

                            List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("FST_A"));
                            Collections.sort(As, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(4)) 
                                            - Integer.parseInt(o2.getParams().get(4));
                                }
                            });
                            pageContext.setAttribute("As", As);

                            List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("FST_B"));
                            Collections.sort(Bs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(4)) 
                                            - Integer.parseInt(o2.getParams().get(4));
                                }
                            });
                            pageContext.setAttribute("Bs", Bs);

                            List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("Gr_First"));
                            Collections.sort(Gs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(4)) 
                                            - Integer.parseInt(o2.getParams().get(4));
                                }
                            });
                            pageContext.setAttribute("Gs", Gs);

                            List<Inning> ASs = new ArrayList<Inning>();
                            ASs.addAll(As);
                            ASs.addAll(Bs);
                            Collections.sort(ASs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(4)) 
                                            - Integer.parseInt(o2.getParams().get(4));
                                }
                            });
                            pageContext.setAttribute("ASs", ASs);

                        }
                        %>
                            <c:forEach var="in" items="${As}">
                                <td name="4Left" class="A">
                                    ${in.getParams().get(4)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Bs}">
                                <td name="4Left" class="B">
                                    ${in.getParams().get(4)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Gs}">
                                <td name="4Left" class="G">
                                    ${in.getParams().get(4)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(As)}" name="4LeftOdd" class="A">

                            </td>
                            <td>
                            <td colspan="${fn:length(Bs)}" name="4LeftOdd" class="B">

                            </td>

                            <td>
                            <td colspan="${fn:length(Gs)}" name="4LeftOdd" class="G">

                            </td>

                        </tr>
                        <tr>
                            <c:forEach var="in" items="${ASs}">
                                <td name="4Left">
                                    ${in.getParams().get(4)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(ASs)}" name="4LeftOdd" class="T"></td>
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
                            <th colspan="${fn:length(foursA_bt)}">${teamOne}</th>
                            <th>
                            <th colspan="${fn:length(foursB_bt)}">${teamTwo}</th>
                            <th>
                            <th colspan="${fn:length(foursG_bt)}">${groundName}</th>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${foursA_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${foursB_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${foursG_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${foursA_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${foursB_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${foursG_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                    </table>

                    <table class="table table-bordered">
                        <tr>
                            <th colspan="30">${teamOne}</th>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${foursTA_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${foursTA_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${foursTA_bt}">
                                <td>
                                    ${foursTA_bt.get("N") - entry.value}
                                </td>
                            </c:forEach>
                        </tr>

                        <tr>
                            <td colspan="30"></td>
                        </tr>

                        <tr>
                            <th colspan="30">${teamTwo}</th>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${foursTB_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${foursTB_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${foursTB_bt}">
                                <td>
                                    ${foursTB_bt.get("N") - entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                   </table>
                </div>
            </div>
                            
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">Sixes</h3>
                        <table class="table table-bordered">
                            <tr class="thead-dark">
                                <th colspan="${fn:length(FST_A)}">${teamOne}</th>
                                <th>
                                <th colspan="${fn:length(FST_B)}">${teamTwo}</th>
                                <th>
                                <th colspan="${fn:length(Gr_First)}">${groundName}</th>
                            </tr>
                            <tr>
                                <c:forEach var="in" items="${FST_A}">
                                    <td >
                                        ${in.getParams().get(5)}
                                    </td>
                                </c:forEach>
                                <td>
                                    <c:forEach var="in" items="${FST_B}">
                                    <td>
                                        ${in.getParams().get(5)}
                                    </td>
                                </c:forEach>
                                <td>
                                    <c:forEach var="in" items="${Gr_First}">
                                    <td >
                                        ${in.getParams().get(5)}
                                    </td>
                                </c:forEach>
                            </tr>

                            <tr class="thead-dark">
                                <th colspan="12">Sorted:</th>
                                <th colspan="20">

                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" name="5Left" 
                                                  id="5Left">Score calc.</span>
                                        </div>
                                        <input type="number" class="form-control" name="5Left">
                                    </div>
                                    <!--<input type="number" id="5Left" >-->
                                </th>
                            </tr>
                            <tr>
                            <%

                            if(true){

                                List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("FST_A"));
                                Collections.sort(As, new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(5)) 
                                                - Integer.parseInt(o2.getParams().get(5));
                                    }
                                });
                                pageContext.setAttribute("As", As);

                                List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("FST_B"));
                                Collections.sort(Bs, new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(5)) 
                                                - Integer.parseInt(o2.getParams().get(5));
                                    }
                                });
                                pageContext.setAttribute("Bs", Bs);

                                List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("Gr_First"));
                                Collections.sort(Gs, new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(5)) 
                                                - Integer.parseInt(o2.getParams().get(5));
                                    }
                                });
                                pageContext.setAttribute("Gs", Gs);

                                List<Inning> ASs = new ArrayList<Inning>();
                                ASs.addAll(As);
                                ASs.addAll(Bs);
                                Collections.sort(ASs, new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(5)) 
                                                - Integer.parseInt(o2.getParams().get(5));
                                    }
                                });
                                pageContext.setAttribute("ASs", ASs);

                            }
                            %>
                                <c:forEach var="in" items="${As}">
                                    <td name="5Left" class="A">
                                        ${in.getParams().get(5)}
                                    </td>
                                </c:forEach>
                                <td>
                                <c:forEach var="in" items="${Bs}">
                                    <td name="5Left" class="B">
                                        ${in.getParams().get(5)}
                                    </td>
                                </c:forEach>
                                <td>
                                <c:forEach var="in" items="${Gs}">
                                    <td name="5Left" class="G">
                                        ${in.getParams().get(5)}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <td colspan="${fn:length(As)}" name="5LeftOdd" class="A">

                                </td>
                                <td>
                                <td colspan="${fn:length(Bs)}" name="5LeftOdd" class="B">

                                </td>

                                <td>
                                <td colspan="${fn:length(Gs)}" name="5LeftOdd" class="G">

                                </td>

                            </tr>
                            <tr>
                                <c:forEach var="in" items="${ASs}">
                                    <td name="5Left">
                                        ${in.getParams().get(5)}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <td colspan="${fn:length(ASs)}" name="5LeftOdd" class="T"></td>
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
                                <th colspan="${fn:length(sixesA_bt)}">${teamOne}</th>
                                <th>
                                <th colspan="${fn:length(sixesB_bt)}">${teamTwo}</th>
                                <th>
                                <th colspan="${fn:length(sixesG_bt)}">${groundName}</th>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${sixesA_bt}">
                                    <td>
                                        ${entry.key}
                                    </td>
                                </c:forEach>

                                <td></td>

                                <c:forEach var="entry" items="${sixesB_bt}">
                                    <td>
                                        ${entry.key}
                                    </td>
                                </c:forEach>

                                <td></td>

                                <c:forEach var="entry" items="${sixesG_bt}">
                                    <td>
                                        ${entry.key}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${sixesA_bt}">
                                    <td>
                                        ${entry.value}
                                    </td>
                                </c:forEach>

                                <td></td>

                                <c:forEach var="entry" items="${sixesB_bt}">
                                    <td>
                                        ${entry.value}
                                    </td>
                                </c:forEach>

                                <td></td>

                                <c:forEach var="entry" items="${sixesG_bt}">
                                    <td>
                                        ${entry.value}
                                    </td>
                                </c:forEach>
                            </tr>
                        </table>

                        <table class="table table-bordered">
                            <tr>
                                <th colspan="30">${teamOne}</th>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${sixesTA_bt}">
                                    <td>
                                        ${entry.key}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${sixesTA_bt}">
                                    <td>
                                        ${entry.value}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${sixesTA_bt}">
                                    <td>
                                        ${sixesTA_bt.get("N") - entry.value}
                                    </td>
                                </c:forEach>
                            </tr>

                            <tr>
                                <td colspan="30"></td>
                            </tr>

                            <tr>
                                <th colspan="30">${teamTwo}</th>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${sixesTB_bt}">
                                    <td>
                                        ${entry.key}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${sixesTB_bt}">
                                    <td>
                                        ${entry.value}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${sixesTB_bt}">
                                    <td>
                                        ${sixesTB_bt.get("N") - entry.value}
                                    </td>
                                </c:forEach>
                            </tr>
                       </table>
                </div>
            </div>
                        
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">Total Boundaries</h3>
                        <table class="table table-bordered">
                            <tr class="thead-dark">
                                <th colspan="${fn:length(FST_A)}">${teamOne}</th>
                                <th>
                                <th colspan="${fn:length(FST_B)}">${teamTwo}</th>
                                <th>
                                <th colspan="${fn:length(Gr_First)}">${groundName}</th>
                            </tr>
                            <tr>
                                <c:forEach var="in" items="${FST_A}">
                                    <td >
                                        ${in.getParams().get(8)}
                                    </td>
                                </c:forEach>
                                <td>
                                    <c:forEach var="in" items="${FST_B}">
                                    <td>
                                        ${in.getParams().get(8)}
                                    </td>
                                </c:forEach>
                                <td>
                                    <c:forEach var="in" items="${Gr_First}">
                                    <td >
                                        ${in.getParams().get(8)}
                                    </td>
                                </c:forEach>
                            </tr>

                            <tr class="thead-dark">
                                <th colspan="12">Sorted:</th>
                                <th colspan="20">

                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" name="bLeft" 
                                                  id="bLeft">Score calc.</span>
                                        </div>
                                        <input type="number" class="form-control" name="bLeft">
                                    </div>
                                    <!--<input type="number" id="bLeft" >-->
                                </th>
                            </tr>

                            <tr>
                            <%

                            if(true){

                                List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("FST_A"));
                                Collections.sort(As, new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(8)) 
                                                - Integer.parseInt(o2.getParams().get(8));
                                    }
                                });
                                pageContext.setAttribute("As", As);

                                List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("FST_B"));
                                Collections.sort(Bs, new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(8)) 
                                                - Integer.parseInt(o2.getParams().get(8));
                                    }
                                });
                                pageContext.setAttribute("Bs", Bs);

                                List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("Gr_First"));
                                Collections.sort(Gs, new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(8)) 
                                                - Integer.parseInt(o2.getParams().get(8));
                                    }
                                });
                                pageContext.setAttribute("Gs", Gs);

                                List<Inning> ASs = new ArrayList<Inning>();
                                ASs.addAll(As);
                                ASs.addAll(Bs);
                                Collections.sort(ASs, new Comparator<Inning>() {
                                    @Override
                                    public int compare(Inning o1, Inning o2) {
                                        return Integer.parseInt(o1.getParams().get(8)) 
                                                - Integer.parseInt(o2.getParams().get(8));
                                    }
                                });
                                pageContext.setAttribute("ASs", ASs);

                            }
                            %>
                                <c:forEach var="in" items="${As}">
                                    <td name="bLeft" class="A">
                                        ${in.getParams().get(8)}
                                    </td>
                                </c:forEach>
                                <td>
                                <c:forEach var="in" items="${Bs}">
                                    <td name="bLeft" class="B">
                                        ${in.getParams().get(8)}
                                    </td>
                                </c:forEach>
                                <td>
                                <c:forEach var="in" items="${Gs}">
                                    <td name="bLeft" class="G">
                                        ${in.getParams().get(8)}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <td colspan="${fn:length(As)}" name="bLeftOdd" class="A">

                                </td>
                                <td>
                                <td colspan="${fn:length(Bs)}" name="bLeftOdd" class="B">

                                </td>

                                <td>
                                <td colspan="${fn:length(Gs)}" name="bLeftOdd" class="G">

                                </td>

                            </tr>
                            <tr>
                                <c:forEach var="in" items="${ASs}">
                                    <td name="bLeft">
                                        ${in.getParams().get(8)}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <td colspan="${fn:length(ASs)}" name="bLeftOdd" class="T"></td>
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
                                <th colspan="${fn:length(boundariesA_bt)}">${teamOne}</th>
                                <th>
                                <th colspan="${fn:length(boundariesB_bt)}">${teamTwo}</th>
                                <th>
                                <th colspan="${fn:length(boundariesG_bt)}">${groundName}</th>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${boundariesA_bt}">
                                    <td>
                                        ${entry.key}
                                    </td>
                                </c:forEach>

                                <td></td>

                                <c:forEach var="entry" items="${boundariesB_bt}">
                                    <td>
                                        ${entry.key}
                                    </td>
                                </c:forEach>

                                <td></td>

                                <c:forEach var="entry" items="${boundariesG_bt}">
                                    <td>
                                        ${entry.key}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${boundariesA_bt}">
                                    <td>
                                        ${entry.value}
                                    </td>
                                </c:forEach>

                                <td></td>

                                <c:forEach var="entry" items="${boundariesB_bt}">
                                    <td>
                                        ${entry.value}
                                    </td>
                                </c:forEach>

                                <td></td>

                                <c:forEach var="entry" items="${boundariesG_bt}">
                                    <td>
                                        ${entry.value}
                                    </td>
                                </c:forEach>
                            </tr>
                        </table>

                        <table class="table table-bordered">
                            <tr>
                                <th colspan="30">${teamOne}</th>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${boundariesTA_bt}">
                                    <td>
                                        ${entry.key}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${boundariesTA_bt}">
                                    <td>
                                        ${entry.value}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${boundariesTA_bt}">
                                    <td>
                                        ${boundariesTA_bt.get("N") - entry.value}
                                    </td>
                                </c:forEach>
                            </tr>

                            <tr>
                                <td colspan="30"></td>
                            </tr>

                            <tr>
                                <th colspan="30">${teamTwo}</th>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${boundariesTB_bt}">
                                    <td>
                                        ${entry.key}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${boundariesTB_bt}">
                                    <td>
                                        ${entry.value}
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach var="entry" items="${boundariesTB_bt}">
                                    <td>
                                        ${boundariesTB_bt.get("N") - entry.value}
                                    </td>
                                </c:forEach>
                            </tr>
                       </table>

                </div>
            </div>
                           

        <h2>First Inning</h2>
            
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
                    <h3 class="card-title">${headers.get(0)}</h3>
                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(FO_A)}">${teamOne} Batting First</th>
                            <th>
                            <th colspan="${fn:length(FO_B)}">${teamTwo} Bowling First</th>
                            <th>
                            <th colspan="${fn:length(Gr_First)}">${groundName} First</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${FO_A}">
                                <td>
                                    ${in.getParams().get(0)}
                                </td>
                            </c:forEach>
                            <td>
                                <c:forEach var="in" items="${FO_B}">
                                <td>
                                    ${in.getParams().get(0)}
                                </td>
                            </c:forEach>
                            <td>
                                <c:forEach var="in" items="${Gr_First}">
                                <td>
                                    ${in.getParams().get(0)}
                                </td>
                            </c:forEach>

                        </tr>


                        <tr class="thead-dark">
                            <th colspan="12">Sorted:</th>
                            <th colspan="20">

                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="${0}Left" 
                                              id="${0}Left">Score calc.</span>
                                    </div>
                                    <input type="number" class="form-control" name="${0}Left">
                                </div>
                                <!--<input type="number" id="${0}Left" >-->
                            </th>
                        </tr>
                        <tr>
                        <%

                        if(true){
                            final int ind = 0;
                            List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("FO_A"));
                            Collections.sort(As, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("As", As);

                            List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("FO_B"));
                            Collections.sort(Bs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Bs", Bs);

                            List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("Gr_First"));
                            Collections.sort(Gs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Gs", Gs);

                            List<Inning> ASs = new ArrayList<Inning>();
                            ASs.addAll(As);
                            ASs.addAll(Bs);
                            Collections.sort(ASs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("ASs", ASs);

                        }
                        %>
                            <c:forEach var="in" items="${As}">
                                <td name="${0}Left" class="A">
                                    ${in.getParams().get(0)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Bs}">
                                <td name="${0}Left" class="B">
                                    ${in.getParams().get(0)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Gs}">
                                <td name="${0}Left" class="G">
                                    ${in.getParams().get(0)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(As)}" name="${0}LeftOdd" class="A">

                            </td>
                            <td>
                            <td colspan="${fn:length(Bs)}" name="${0}LeftOdd" class="B">

                            </td>

                            <td>
                            <td colspan="${fn:length(Gs)}" name="${0}LeftOdd" class="G">

                            </td>

                        </tr>
                        <tr>
                            <c:forEach var="in" items="${ASs}">
                                <td name="${0}Left">
                                    ${in.getParams().get(0)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(ASs)}" name="${0}LeftOdd" class="T"></td>
                        </tr>
                    </table>

                </div>
            </div>
                         
            <!-- Last 5/10 Over -->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">${headers.get(2)}</h3>
                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(LO_A)}">${teamOne} Batting First</th>
                            <th>
                            <th colspan="${fn:length(LO_B)}">${teamTwo} Bowling First</th>
                            <th>
                            <th colspan="${fn:length(LO_G)}">${groundName} First</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${LO_A}">
                                <td>
                                    ${in.getParams().get(2)}
                                </td>
                            </c:forEach>
                            <td>
                                <c:forEach var="in" items="${LO_B}">
                                <td >
                                    ${in.getParams().get(2)}
                                </td>
                            </c:forEach>
                            <td>
                                <c:forEach var="in" items="${LO_G}">
                                <td >
                                    ${in.getParams().get(2)}
                                </td>
                            </c:forEach>


                        </tr>


                        <tr class="thead-dark">
                            <th colspan="12">Sorted:</th>
                            <th colspan="30">

                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="${2}Left" id="${2}Left">Score calc.</span>
                                    </div>
                                    <input type="number" class="form-control" name="${2}Left">
                                </div>
                                <!--<input type="number" id="${2}Left" >-->
                            </th>
                        </tr>
                        <tr>
                        <%

                        if(true){
                            final int ind = 2;
                            List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("LO_A"));
                            Collections.sort(As, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("As", As);

                            List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("LO_B"));
                            Collections.sort(Bs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Bs", Bs);

                            List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("LO_G"));
                            Collections.sort(Gs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Gs", Gs);

                            List<Inning> ASs = new ArrayList<Inning>();
                            ASs.addAll(As);
                            ASs.addAll(Bs);
                            Collections.sort(ASs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("ASs", ASs);

                        }
                        %>
                            <c:forEach var="in" items="${As}">
                                <td  name="${2}Left" class="A">
                                    ${in.getParams().get(2)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Bs}">
                                <td name="${2}Left" class="B">
                                    ${in.getParams().get(2)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Gs}">
                                <td name="${2}Left" class="G">
                                    ${in.getParams().get(2)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(As)}" name="${2}LeftOdd" class="A"></td>
                            <td>
                            <td colspan="${fn:length(Bs)}" name="${2}LeftOdd" class="B"></td>
                            <td>
                            <td colspan="${fn:length(Gs)}" name="${2}LeftOdd" class="G"></td>

                        </tr>
                        <tr>
                            <c:forEach var="in" items="${ASs}">
                                <td name="${2}Left">
                                    ${in.getParams().get(2)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(ASs)}" name="${2}LeftOdd" class="T"></td>
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
                            <th colspan="${fn:length(LO_A_bt)}">${teamOne} Batting First</th>
                            <th>
                            <th colspan="${fn:length(LO_B_bt)}">${teamTwo} Bowling First</th>
                            <th>
                            <th colspan="${fn:length(LO_G_bt)}">${groundName} First</th>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${LO_A_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${LO_B_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${LO_G_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${LO_A_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${LO_B_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${LO_G_bt}">
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
                            <c:forEach var="entry" items="${LO_TA_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${LO_TA_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${LO_TA_bt}">
                                <td>
                                    ${LO_TA_bt.get("N") - entry.value}
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
                            <c:forEach var="entry" items="${LO_TB_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${LO_TB_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${LO_TB_bt}">
                                <td>
                                    ${LO_TB_bt.get("N") - entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                   </table>

                </div>
            </div>
                        
            <!-- First Wicket -->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">${headers.get(3)}</h3>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(FW_A)}">${teamOne} Batting First</th>
                            <th>
                            <th colspan="${fn:length(FW_B)}">${teamTwo} Bowling First</th>
                            <th>
                            <th colspan="${fn:length(Gr_First)}">${groundName} First</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${FW_A}">
                                <td >
                                    ${in.getParams().get(3)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${FW_B}">
                                <td >
                                    ${in.getParams().get(3)} 
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Gr_First}">
                                <td >
                                    ${in.getParams().get(3)}
                                </td>
                            </c:forEach>

                        </tr>



                        <tr class="thead-dark">
                            <th colspan="12">Sorted:</th>
                            <th colspan="20">

                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="${3}Left" id="${3}Left">Score calc.</span>
                                    </div>
                                    <input type="number" class="form-control" name="${3}Left">
                                </div>
                                <!--<input type="number" id="${3}Left" >-->
                            </th>
                        </tr>
                        <tr>
                        <%

                        if(true){
                            final int ind = 3;
                            List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("FW_A"));
                            As.removeIf(new Predicate<Inning>() {
                                @Override
                                public boolean test(Inning t) {
                                    return t.getParams().get(ind).contains("D/L");
                                }
                            });
                            Collections.sort(As, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("As", As);

                            List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("FW_B"));
                            Bs.removeIf(new Predicate<Inning>() {
                                @Override
                                public boolean test(Inning t) {
                                    return t.getParams().get(ind).contains("D/L");
                                }
                            });
                            Collections.sort(Bs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Bs", Bs);

                            List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("Gr_First"));
                            Gs.removeIf(new Predicate<Inning>() {
                                @Override
                                public boolean test(Inning t) {
                                    return t.getParams().get(ind).contains("D/L");
                                }
                            });
                            Collections.sort(Gs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Gs", Gs);

                            List<Inning> ASs = new ArrayList<Inning>();
                            ASs.addAll(As);
                            ASs.addAll(Bs);
                            Collections.sort(ASs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("ASs", ASs);
                        }
                        %>
                            <c:forEach var="in" items="${As}">
                                <td name="${3}Left" class="A">
                                    ${in.getParams().get(3)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Bs}">
                                <td name="${3}Left" class="B">
                                    ${in.getParams().get(3)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Gs}">
                                <td name="${3}Left" class="G">
                                    ${in.getParams().get(3)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                                <td colspan="${fn:length(As)}" name="${3}LeftOdd" class="A">

                                </td>
                            <td>
                                <td colspan="${fn:length(Bs)}" name="${3}LeftOdd" class="B">

                                </td>
                            <td>
                                <td colspan="${fn:length(Gs)}" name="${3}LeftOdd" class="G">

                                </td>

                        </tr>
                        <tr>
                            <c:forEach var="in" items="${ASs}">
                                <td name="${3}Left">
                                    ${in.getParams().get(3)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(ASs)}" name="${3}LeftOdd" class="T"></td>
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
                            <th colspan="${fn:length(FW_A_bt)}">${teamOne} Batting First</th>
                            <th>
                            <th colspan="${fn:length(FW_B_bt)}">${teamTwo} Bowling First</th>
                            <th>
                            <th colspan="${fn:length(FW_G_bt)}">${groundName} First</th>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FW_A_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FW_B_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FW_G_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FW_A_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FW_B_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FW_G_bt}">
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
                            <c:forEach var="entry" items="${FW_TA_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FW_TA_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FW_TA_bt}">
                                <td>
                                    ${FW_TA_bt.get("N") - entry.value}
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
                            <c:forEach var="entry" items="${FW_TB_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FW_TB_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FW_TB_bt}">
                                <td>
                                    ${FW_TB_bt.get("N") - entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                   </table>




                </div>
            </div>

            <!-- Total runs -->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">${headers.get(6)}</h3>

                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(TR_A)}">${teamOne} Batting First</th>
                            <th>
                            <th colspan="${fn:length(TR_B)}">${teamTwo} Bowling First</th>
                            <th>
                            <th colspan="${fn:length(TR_G)}">${groundName} First</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${TR_A}">
                                <td >
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${TR_B}">
                                <td >
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${TR_G}">
                                <td >
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>

                        </tr>

                        <tr class="thead-dark">
                            <th colspan="12">Sorted:</th>
                            <th colspan="20">

                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="${6}Left" id="${6}Left">Score calc.</span>
                                    </div>
                                    <input type="number" class="form-control" name="${6}Left">
                                </div>
                                <!--<input type="number" id="${6}Left" >-->
                            </th>
                        </tr>
                        <tr>
                        <%

                        if(true){
                            final int ind = 6;
                            List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("TR_A"));
                            As.removeIf(new Predicate<Inning>() {
                                @Override
                                public boolean test(Inning t) {
                                    return t.getParams().get(ind).contains("D/L");
                                }
                            });
                            Collections.sort(As, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("As", As);

                            List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("TR_B"));
                            Bs.removeIf(new Predicate<Inning>() {
                                @Override
                                public boolean test(Inning t) {
                                    return t.getParams().get(ind).contains("D/L");
                                }
                            });
                            Collections.sort(Bs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Bs", Bs);

                            List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("TR_G"));
                            Gs.removeIf(new Predicate<Inning>() {
                                @Override
                                public boolean test(Inning t) {
                                    return t.getParams().get(ind).contains("D/L");
                                }
                            });
                            Collections.sort(Gs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Gs", Gs);

                            List<Inning> ASs = new ArrayList<Inning>();
                            ASs.addAll(As);
                            ASs.addAll(Bs);
                            Collections.sort(ASs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("ASs", ASs);
                        }
                        %>
                            <c:forEach var="in" items="${As}">
                                <td name="${6}Left" class="A">
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Bs}">
                                <td name="${6}Left" class="B">
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Gs}">
                                <td name="${6}Left" class="G">
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                                <td colspan="${fn:length(As)}" name="${6}LeftOdd" class="A">

                                </td>
                            <td>
                                <td colspan="${fn:length(Bs)}" name="${6}LeftOdd" class="B">

                                </td>
                            <td>
                                <td colspan="${fn:length(Gs)}" name="${6}LeftOdd" class="G">

                                </td>

                        </tr>
                        <tr>
                            <c:forEach var="in" items="${ASs}">
                                <td name="${6}Left">
                                    ${in.getParams().get(6)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(ASs)}" name="${6}LeftOdd" class="T"></td>
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
                            <th colspan="${fn:length(TR_A_bt)}">${teamOne} Batting First</th>
                            <th>
                            <th colspan="${fn:length(TR_B_bt)}">${teamTwo} Bowling First</th>
                            <th>
                            <th colspan="${fn:length(TR_G_bt)}">${groundName} First</th>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${TR_A_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${TR_B_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${TR_G_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${TR_A_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${TR_B_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${TR_G_bt}">
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
                            <c:forEach var="entry" items="${TR_TA_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${TR_TA_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${TR_TA_bt}">
                                <td>
                                    ${TR_TA_bt.get("N") - entry.value}
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
                            <c:forEach var="entry" items="${TR_TB_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${TR_TB_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${TR_TB_bt}">
                                <td>
                                    ${TR_TB_bt.get("N") - entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                   </table>

                </div>
            </div>

            <!-- First 6/10 Overs -->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">${headers.get(1)}</h3>
                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(FX_A)}">${teamOne} Batting First </th>
                            <th>
                            <th colspan="${fn:length(FX_B)}">${teamTwo} Bowling First</th>
                            <th>
                            <th colspan="${fn:length(Gr_First)}">${groundName} First</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${FX_A}">
                                <td >
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>
                            <td>
                                <c:forEach var="in" items="${FX_B}">
                                <td >
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>
                            <td>
                                <c:forEach var="in" items="${Gr_First}">
                                <td >
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>



                        </tr>


                        <tr class="thead-dark">
                            <th colspan="12">Sorted:</th>
                            <th colspan="20">

                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="${1}Left" 
                                              id="${1}Left">Score calc.</span>
                                    </div>
                                    <input type="number" class="form-control" name="${1}Left">
                                </div>
                                <!--<input type="number" id="${1}Left" >-->
                            </th>
                        </tr>
                        <tr>
                        <%

                        if(true){
                            final int ind = 1;
                            List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("FX_A"));
                            Collections.sort(As, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("As", As);

                            List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("FX_B"));
                            Collections.sort(Bs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Bs", Bs);

                            List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("Gr_First"));
                            Collections.sort(Gs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Gs", Gs);

                            List<Inning> ASs = new ArrayList<Inning>();
                            ASs.addAll(As);
                            ASs.addAll(Bs);
                            Collections.sort(ASs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("ASs", ASs);

                        }
                        %>
                            <c:forEach var="in" items="${As}">
                                <td name="${1}Left" class="A">
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Bs}">
                                <td name="${1}Left" class="B">
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Gs}">
                                <td name="${1}Left" class="G">
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(As)}" name="${1}LeftOdd" class="A">

                            </td>
                            <td>
                            <td colspan="${fn:length(Bs)}" name="${1}LeftOdd" class="B">

                            </td>

                            <td>
                            <td colspan="${fn:length(Gs)}" name="${1}LeftOdd" class="G">

                            </td>

                        </tr>
                        <tr>
                            <c:forEach var="in" items="${ASs}">
                                <td name="${1}Left">
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(ASs)}" name="${1}LeftOdd" class="T"></td>
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
                            <th colspan="${fn:length(FX_A_bt)}">${teamOne} Batting First</th>
                            <th>
                            <th colspan="${fn:length(FX_B_bt)}">${teamTwo} Bowling First</th>
                            <th>
                            <th colspan="${fn:length(FX_G_bt)}">${groundName} First</th>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FX_A_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FX_B_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FX_G_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FX_A_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FX_B_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FX_G_bt}">
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
                            <c:forEach var="entry" items="${FX_TA_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FX_TA_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FX_TA_bt}">
                                <td>
                                    ${FX_TA_bt.get("N") - entry.value}
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
                            <c:forEach var="entry" items="${FX_TB_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FX_TB_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FX_TB_bt}">
                                <td>
                                    ${FX_TB_bt.get("N") - entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                   </table>



                </div>
            </div>
     
                        
            <h2>Second Inning</h2>

            <!-- First 6/10 Overs -->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">${headers.get(1)}</h3>
                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(FXS_A)}">${teamTwo} Batting Second</th>
                            <th>
                            <th colspan="${fn:length(FXS_B)}">${teamOne} Bowling Second</th>
                            <th>
                            <th colspan="${fn:length(FXS_G)}">${groundName} Second</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${FXS_A}">
                                <td >
                                    ${in.getParams().get(1)}
                                </td>


                            </c:forEach>
                            <td>
                                <c:forEach var="in" items="${FXS_B}">
                                <td >
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>
                            <td>
                                <c:forEach var="in" items="${FXS_G}">
                                <td >
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>



                        </tr>


                        <tr class="thead-dark">
                            <th colspan="12">Sorted:</th>
                            <th colspan="30">

                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="${1}Left2" id="${1}Left2">@</span>
                                    </div>
                                    <input type="number" class="form-control" name="${1}Left2">
                                </div>
                                <!--<input type="number" id="${1}Left" >-->
                            </th>
                        </tr>
                        <tr>
                        <%

                        if(true){
                            final int ind = 1;
                            List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("FXS_A"));
                            Collections.sort(As, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("As", As);

                            List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("FXS_B"));
                            Collections.sort(Bs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Bs", Bs);

                            List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("FXS_G"));
                            Collections.sort(Gs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Gs", Gs);

                            List<Inning> ASs = new ArrayList<Inning>();
                            ASs.addAll(As);
                            ASs.addAll(Bs);
                            Collections.sort(ASs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("ASs", ASs);

                        }
                        %>
                            <c:forEach var="in" items="${As}">
                                <td name="${1}Left2" class="A">
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Bs}">
                                <td name="${1}Left2" class="B">
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Gs}">
                                <td name="${1}Left2"  class="G">
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(As)}" name="${1}Left2Odd"  class="A">

                            </td>
                            <td>
                            <td colspan="${fn:length(Bs)}" name="${1}Left2Odd" class="B">

                            </td>

                            <td>
                            <td colspan="${fn:length(Gs)}" name="${1}Left2Odd"  class="G">

                            </td>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${ASs}">
                                <td name="${1}Left2">
                                    ${in.getParams().get(1)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(ASs)}" name="${1}Left2Odd"  class="T">
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
                            <th colspan="${fn:length(FXS_A_bt)}">${teamTwo} Batting Second</th>
                            <th>
                            <th colspan="${fn:length(FXS_B_bt)}">${teamOne} Bowling Second</th>
                            <th>
                            <th colspan="${fn:length(FXS_G_bt)}">${groundName} Second</th>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FXS_A_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FXS_B_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FXS_G_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FXS_A_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FXS_B_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FXS_G_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                    </table>

                    <table class="table table-bordered">
                        <tr>
                            <th colspan="30">${teamTwo} Batting Second</th>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FXS_TA_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FXS_TA_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FXS_TA_bt}">
                                <td>
                                    ${FXS_TA_bt.get("N") - entry.value}
                                </td>
                            </c:forEach>
                        </tr>

                        <tr>
                            <td colspan="30"></td>
                        </tr>

                        <tr>
                            <th colspan="30">${teamOne} Bowling Second</th>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FXS_TB_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FXS_TB_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FXS_TB_bt}">
                                <td>
                                    ${FXS_TB_bt.get("N") - entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                   </table>

                        </div>
            </div>
            
            <!-- First Wicket -->
            <div class="row" >
                <div class ="col-12">
                    <h3 class="card-title">${headers.get(3)}</h3>
                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="${fn:length(FWS_A)}">${teamTwo} Batting Second</th>
                            <th>
                            <th colspan="${fn:length(FWS_B)}">${teamOne} Bowling Second</th>
                            <th>
                            <th colspan="${fn:length(FWS_G)}">${groundName} Second</th>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${FWS_A}">
                                <td >
                                    ${in.getParams().get(3)}
                                </td>


                            </c:forEach>
                            <td>
                                <c:forEach var="in" items="${FWS_B}">
                                <td >
                                    ${in.getParams().get(3)}
                                </td>
                            </c:forEach>
                            <td>
                                <c:forEach var="in" items="${FWS_G}">
                                <td >
                                    ${in.getParams().get(3)}
                                </td>
                            </c:forEach>



                        </tr>

                        <tr class="thead-dark">
                            <th colspan="12">Sorted:</th>
                            <th colspan="30">

                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" name="${3}Left2" id="${3}Left2">@</span>
                                    </div>
                                    <input type="number" class="form-control" name="${3}Left2">
                                </div>
                                <!--<input type="number" id="${3}Left" >-->
                            </th>
                        </tr>
                        <tr>
                        <%

                        if(true){
                            final int ind = 3;
                            List<Inning> As = new ArrayList<Inning>((List<Inning>)request.getAttribute("FWS_A"));
                            Collections.sort(As, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("As", As);

                            List<Inning> Bs = new ArrayList<Inning>((List<Inning>)request.getAttribute("FWS_B"));
                            Collections.sort(Bs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Bs", Bs);

                            List<Inning> Gs = new ArrayList<Inning>((List<Inning>)request.getAttribute("FWS_G"));
                            Collections.sort(Gs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("Gs", Gs);

                            List<Inning> ASs = new ArrayList<Inning>();
                            ASs.addAll(As);
                            ASs.addAll(Bs);
                            Collections.sort(ASs, new Comparator<Inning>() {
                                @Override
                                public int compare(Inning o1, Inning o2) {
                                    return Integer.parseInt(o1.getParams().get(ind)) 
                                            - Integer.parseInt(o2.getParams().get(ind));
                                }
                            });
                            pageContext.setAttribute("ASs", ASs);

                        }
                        %>
                            <c:forEach var="in" items="${As}">
                                <td name="${3}Left2" class="A">
                                    ${in.getParams().get(3)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Bs}">
                                <td name="${3}Left2" class="B">
                                    ${in.getParams().get(3)}
                                </td>
                            </c:forEach>
                            <td>
                            <c:forEach var="in" items="${Gs}">
                                <td name="${3}Left2"  class="G">
                                    ${in.getParams().get(3)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(As)}" name="${3}Left2Odd"  class="A">

                            </td>
                            <td>
                            <td colspan="${fn:length(Bs)}" name="${3}Left2Odd" class="B">

                            </td>

                            <td>
                            <td colspan="${fn:length(Gs)}" name="${3}Left2Odd"  class="G">

                            </td>
                        </tr>
                        <tr>
                            <c:forEach var="in" items="${ASs}">
                                <td name="${3}Left2">
                                    ${in.getParams().get(3)}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td colspan="${fn:length(ASs)}" name="${3}Left2Odd"  class="T">
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
                            <th colspan="${fn:length(FWS_A_bt)}">${teamTwo} Batting Second</th>
                            <th>
                            <th colspan="${fn:length(FWS_B_bt)}">${teamOne} Bowling Second</th>
                            <th>
                            <th colspan="${fn:length(FWS_G_bt)}">${groundName} Second</th>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FWS_A_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FWS_B_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FWS_G_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FWS_A_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FWS_B_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>

                            <td></td>

                            <c:forEach var="entry" items="${FWS_G_bt}">
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
                            <c:forEach var="entry" items="${FWS_TA_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FWS_TA_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FWS_TA_bt}">
                                <td>
                                    ${FWS_TA_bt.get("N") - entry.value}
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
                            <c:forEach var="entry" items="${FWS_TB_bt}">
                                <td>
                                    ${entry.key}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FWS_TB_bt}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${FWS_TB_bt}">
                                <td>
                                    ${FWS_TB_bt.get("N") - entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                   </table>
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
