<%-- 
    Document   : editFavourites
    Created on : Nov 24, 2020, 11:37:25 PM
    Author     : Jerome Nicholas
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Favourites</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
    </head>
    <body>
        <nav class="navbar sticky-top navbar-dark bg-dark">
            <a class="navbar-brand" href="#">Edit Favourites Data of [${matchID}]</a>
        </nav>
        <br>
        <br>

        <div class="container">
            <form action = "editFavourites" method = "POST">
                <div class="row">
                    <div class="card mx-auto">
                        <div class="card-header">
                            <input type="hidden" name ="matchID" value ="${matchID}">
                            <input type="hidden" name ="redirUrl" value ="${redirUrl}">
                            <input type="hidden" name ="submitted" value ="true">
                            <div class="input-group col">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="label3">Favourite Team</span>
                                </div>
                                <select type="text" class="form-control" aria-describedby="label3"
                                        name = "favTeam" value="${favTeam}">
                                    <option value="" selected>Select Fav. Team</option>
                                    <option value="${team1}" <c:if test="${team1.equals(favTeam)}">selected</c:if> >${team1}</option>
                                    <option value="${team2}" <c:if test="${team2.equals(favTeam)}">selected</c:if>>${team2}</option>
                                    </select>
                                </div>
                            </div>

                            <div class="card-header">
                                <div class="input-group col">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" id="label3">Bias</span>
                                    </div>
                                    <select type="text" class="form-control" aria-describedby="label3"
                                            name = "bias" value="${bias}">
                                    <option value="" selected>Select Bias</option>
                                    <option value="f" <c:if test='${bias.equals("f")}'>selected</c:if> >Favorite</option>
                                    <option value="nf" <c:if test='${bias.equals("nf")}'>selected</c:if>>Non-Favorite</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>

                    <br>

                <jsp:include page="/editOHLRow.jsp">
                    <jsp:param name="ohl_title" value="Overall Inning 1" />
                    <jsp:param name="ohl_pre" value="O1" />
                    <jsp:param name="open" value="${O_OHL.getInning1().getOpen()}" />
                    <jsp:param name="high" value="${O_OHL.getInning1().getHigh()}" />
                    <jsp:param name="low" value="${O_OHL.getInning1().getLow()}" />
                </jsp:include>

                <jsp:include page="/editOHLRow.jsp">
                    <jsp:param name="ohl_title" value="Overall Inning 2" />
                    <jsp:param name="ohl_pre" value="O2" />
                    <jsp:param name="open" value="${O_OHL.getInning2().getOpen()}" />
                    <jsp:param name="high" value="${O_OHL.getInning2().getHigh()}" />
                    <jsp:param name="low" value="${O_OHL.getInning2().getLow()}" />
                </jsp:include>

                <jsp:include page="/editOHLRow.jsp">
                    <jsp:param name="ohl_title" value="First Wicket" />
                    <jsp:param name="ohl_pre" value="FW" />
                    <jsp:param name="open" value="${OHL.getFW().getOpen()}" />
                    <jsp:param name="high" value="${OHL.getFW().getHigh()}" />
                    <jsp:param name="low" value="${OHL.getFW().getLow()}" />
                </jsp:include>

                <jsp:include page="/editOHLRow.jsp">
                    <jsp:param name="ohl_title" value="First 6/10 overs" />
                    <jsp:param name="ohl_pre" value="FX" />
                    <jsp:param name="open" value="${OHL.getFX().getOpen()}" />
                    <jsp:param name="high" value="${OHL.getFX().getHigh()}" />
                    <jsp:param name="low" value="${OHL.getFX().getLow()}" />
                </jsp:include>

                <jsp:include page="/editOHLRow.jsp">
                    <jsp:param name="ohl_title" value="Last 5/10 overs" />
                    <jsp:param name="ohl_pre" value="LX" />
                    <jsp:param name="open" value="${OHL.getLX().getOpen()}" />
                    <jsp:param name="high" value="${OHL.getLX().getHigh()}" />
                    <jsp:param name="low" value="${OHL.getLX().getLow()}" />
                </jsp:include>

                <jsp:include page="/editOHLRow.jsp">
                    <jsp:param name="ohl_title" value="Total runs" />
                    <jsp:param name="ohl_pre" value="T" />
                    <jsp:param name="open" value="${OHL.getT().getOpen()}" />
                    <jsp:param name="high" value="${OHL.getT().getHigh()}" />
                    <jsp:param name="low" value="${OHL.getT().getLow()}" />
                </jsp:include>

                <jsp:include page="/editOHLRow.jsp">
                    <jsp:param name="ohl_title" value="First Wicket(Second Inning)" />
                    <jsp:param name="ohl_pre" value="FW2" />
                    <jsp:param name="open" value="${OHL.getFW2().getOpen()}" />
                    <jsp:param name="high" value="${OHL.getFW2().getHigh()}" />
                    <jsp:param name="low" value="${OHL.getFW2().getLow()}" />
                </jsp:include>

                <jsp:include page="/editOHLRow.jsp">
                    <jsp:param name="ohl_title" value="First 5/10(Second Inning)" />
                    <jsp:param name="ohl_pre" value="FX2" />
                    <jsp:param name="open" value="${OHL.getFX2().getOpen()}" />
                    <jsp:param name="high" value="${OHL.getFX2().getHigh()}" />
                    <jsp:param name="low" value="${OHL.getFX2().getLow()}" />
                </jsp:include>


                <br>
                <div class="input-group center-block col-12">
                    <input type="submit" class="btn btn-dark mx-auto col-md-4">
                </div>
            </form>
        </div>
    </body>
</html>
