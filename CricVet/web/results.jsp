<%-- 
    Document   : results
    Created on : Mar 3, 2019, 12:25:47 AM
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
        </style>
        <div class="container-fluid text-center">
            <!--            <div class="row">
                            <div class="col-">
                                <h1>${teamOne} Batting</h1>
                            </div>
                            <div class="col-6">
                                <h1>${teamTwo} Batting</h1>
                            </div>
                        </div>-->
            <h1 class="text-center">${teamOne} v/s ${teamTwo}</h1>
            <c:forEach var="h" items="${headers}" varStatus="loop">
                <div class="row" >
                    <div class ="col-12">
                        <div class="card mar">
                            <div class="card-body ">
                                <h5 class="card-title">${h}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(oneBatFirst)}">${teamOne} Batting First</th>
                                        <th>
                                        <th colspan="${fn:length(twoBowlFirst)}">${teamTwo} Bowling First</th>
                                        <th>
                                        <th colspan="${fn:length(groundFirst)}">${groundName} First</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${oneBatFirst}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                            <td>
                                        <c:forEach var="in" items="${twoBowlFirst}">
                                            <td name="${loop.index}Left">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                            <td>
                                        <c:forEach var="in" items="${groundFirst}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="${loop.index}Left" id="${loop.index}Left">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="${loop.index}Left">
                                            </div>
                                            <!--<input type="number" id="${loop.index}Left" >-->
                                        </td>


                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!--                    <div class="col-6">
                                            <div class="card mar">
                                                <div class="card-body ">
                                                    <h5 class="card-title">${h}</h5>
                    
                                                    <table class="table table-bordered">
                                                        <tr class="thead-dark">
                                                            <th colspan="${fn:length(twoBatFirst)}">${teamTwo} Batting First</th>
                                                            <th colspan="${fn:length(oneBowlFirst)}">${teamOne} Bowling First</th>
                                                            <th colspan="${fn:length(groundFirst)}">${groundName} First</th>
                                                            <th>Odds</th>
                                                        </tr>
                                                        <tr>
                    <c:forEach var="in" items="${twoBatFirst}">
                        <td name="${loop.index}Right">
                        ${in.getParams().get(loop.index)}
                    </td>
                    </c:forEach>
                    <c:forEach var="in" items="${oneBowlFirst}">
                        <td name="${loop.index}Right">
                        ${in.getParams().get(loop.index)}
                    </td>
                    </c:forEach>
                    <c:forEach var="in" items="${groundFirst}">
                        <td>
                        ${in.getParams().get(loop.index)}
                    </td>
                    </c:forEach>
                    <td>
                        <input type="number" id="${loop.index}Right" >
                    </td>
                </tr>

            </table>
        </div>
    </div>
</div>-->
                </div>
            </c:forEach>
            <h1>Second Inning</h1>
            <c:forEach var="h" items="${headers}" varStatus="loop">
                <div class="row" >
                    <div class ="col-12">
                        <div class="card mar">
                            <div class="card-body ">
                                <h5 class="card-title">${h}</h5>

                                <table class="table table-bordered">
                                    <tr class="thead-dark">
                                        <th colspan="${fn:length(twoBatSecond)}">${teamTwo} Batting Second</th>
                                        <th>
                                        <th colspan="${fn:length(oneBowlSecond)}">${teamOne} Bowling Second</th>
                                        <th>
                                        <th colspan="${fn:length(groundSecond)}">${groundName} Second</th>
                                        <th>Odds</th>
                                    </tr>
                                    <tr>
                                        <c:forEach var="in" items="${twoBatSecond}">
                                            <td name="${loop.index}Left2">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                            <td>
                                        <c:forEach var="in" items="${oneBowlSecond}">
                                            <td name="${loop.index}Left2">
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                            <td>
                                        <c:forEach var="in" items="${groundSecond}">
                                            <td>
                                                ${in.getParams().get(loop.index)}
                                            </td>
                                        </c:forEach>
                                        <td>

                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" name="${loop.index}Left2" id="${loop.index}Left2">@</span>
                                                </div>
                                                <input type="number" class="form-control" name="${loop.index}Left2">
                                            </div>

                                        </td>

                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!--                    <div class="col-6">
                                            <div class="card mar">
                                                <div class="card-body ">
                                                    <h5 class="card-title">${h}</h5>
                    
                                                    <table class="table table-bordered">
                                                        <tr class="thead-dark">
                                                            <th colspan="${fn:length(oneBatSecond)}">${teamTwo} Batting Second</th>
                                                            <th colspan="${fn:length(twoBowlSecond)}">${teamOne} Bowling Second</th>
                                                            <th colspan="${fn:length(groundSecond)}">${groundName} Second</th>
                                                        </tr>
                                                        <tr>
                    <c:forEach var="in" items="${oneBatSecond}">
                        <td>
                        ${in.getParams().get(loop.index)}
                    </td>
                    </c:forEach>
                    <c:forEach var="in" items="${twoBowlSecond}">
                        <td>
                        ${in.getParams().get(loop.index)}
                    </td>
                    </c:forEach>
                    <c:forEach var="in" items="${groundSecond}">
                        <td>
                        ${in.getParams().get(loop.index)}
                    </td>
                    </c:forEach>
                </tr>
            </table>
        </div>
    </div>
</div>-->
                </div>
            </c:forEach>
        </div>

        <script type="text/javascript">
            $("input").keyup(function () {

                var inp = $(this).val();
                var name = $(this).attr("name");

                $('td[name="' + name + '"]').each(function () {
                    $(this).removeClass("greater")
                    $(this).removeClass("lower")
                    $('#' + name).text("@");
                });


                if (inp.length == 0) {
                    $('td[name="' + name + '"]').each(function () {
                        $(this).removeClass("greater");
                        $(this).removeClass("lower");
                        $('#' + name).text("@");
                    });

                } else {    
//                    var tds = $('td[name="' + name + '"]');
//
//                    var noOfTds = $('td[name="' + name + '"]');

                    var num = 0;
                    var den = 0;

                    $('td[name="' + name + '"]').each(function () {
                        if (parseInt($(this).text()) >= parseInt(inp)) {

                            $(this).addClass("greater");
                            num++;
                        } else if (parseInt($(this).text()) < parseInt(inp)) {
//                            alert($(this).text() +" :: "+ inp);
                            $(this).addClass("lower");
                            den++;
                        }

                    });

                    $('#' + name).text(num.toString() + "/" + den.toString());

                }
            });
        </script>

    </body>
</html>
