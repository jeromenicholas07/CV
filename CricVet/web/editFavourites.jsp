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

        <div class="container col-6">
            <form action = "editFavourites" method = "POST">
                <div class="card mx-auto">
                    <div class="card-header">
                        <input type="hidden" name ="matchID" value ="${matchID}">
                        <input type="hidden" name ="redirUrl" value ="${redirUrl}">
                        <div class="input-group col">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="label3">Favourite Team</span>
                            </div>
                            <select type="text" class="form-control" aria-describedby="label3"
                                    name = "favTeam" value="${favTeam}" required>
                                <option value="" selected disabled>Select Fav. Team</option>
                                <option value="${team1}" <c:if test="${team1.equals(favTeam)}">selected</c:if> >${team1}</option>
                                <option value="${team2}" <c:if test="${team2.equals(favTeam)}">selected</c:if>>${team2}</option>
                                </select>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-3">
                                        <h1>Inning 1</h1>
                                    </div>
                                    <div class="col">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text" id="label2">Open</span>
                                            </div>
                                            <input type="number" class="form-control" aria-describedby="label2"
                                                   name = "open1" value="${open1}">
                                    </div>

                                    <br>

                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" id="label2">High</span>
                                        </div>
                                        <input type="number" class="form-control" aria-describedby="label2"
                                               name = "high1" value="${high1}">
                                    </div>

                                    <br>

                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" id="label2">Low</span>
                                        </div>
                                        <input type="number" class="form-control" aria-describedby="label2"
                                               name = "low1" value="${low1}">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-3">
                                    <h1>Inning 2</h1>
                                </div>
                                <div class="col">
                                    <div class="input-group ">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" id="label2">Open</span>
                                        </div>
                                        <input type="number" class="form-control" aria-describedby="label2"
                                               name = "open2" value="${open2}">
                                    </div>

                                    <br>

                                    <div class="input-group ">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" id="label2">High</span>
                                        </div>
                                        <input type="number" class="form-control" aria-describedby="label2"
                                               name = "high2" value="${high2}">
                                    </div>

                                    <br>

                                    <div class="input-group ">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" id="label2">Low</span>
                                        </div>
                                        <input type="number" class="form-control" aria-describedby="label2"
                                               name = "low2" value="${low2}">
                                    </div>
                                </div>
                            </div>


                        </div>
                    </div>
                </div>
                <br>
                <div class="input-group center-block col-12">
                    <input type="submit" class="btn btn-dark mx-auto col-md-4">
                </div>
            </form>
        </div>
    </body>
</html>
