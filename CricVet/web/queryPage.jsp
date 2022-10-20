<%-- 
    Document   : queryPage
    Created on : Mar 2, 2019, 4:26:32 PM
    Author     : DELL
--%>

<%@page import="Database.CricDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


        <title>Query Page</title>
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
                width: 500px;
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
                    <form method="POST" action="getData" id="form">
                        <div class="container form-query">
                            <div class="form-group">
                                <!--<label for="teamSelect">Select Tournament</label>-->
                                <div class="col-12">
                                    <select class="form-control form-control-lg required" id="tourSelect" name="tournament">
                                        <option value="-1" name="-1">Select Tournament</option>
                                        <option value="2" name="2">One Day International</option>
                                        <option value="3" name="3">T20 Internationals</option>
                                        <option value="117" name="117">Indian Premier League</option>
                                        <option value="158" name="158">Big Bash League</option>
                                        <option value="159" name="159">Bangladesh Premier League</option>
                                        <option value="748" name="748">Caribbean Premier League</option>
                                        <option value="205" name="205">Pakistan Super League</option>
                                        <option value="1" name="1">Test Matches</option>

                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-6 teams">
                                    <select class="form-control form-control required" id="teamSelect1" name="teamName1">
                                        <option value="-1">Batting Team</option>
                                    </select>
                                </div>
                                <div class="col-6 teams">
                                    <select class="form-control form-control required" id="teamSelect2" name="teamName2">
                                        <option value="-1">Chasing Team</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-12 teams">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <label class="input-group-text" for="favSelect">Favorite : </label>
                                        </div>
                                        <select class="form-control form-control required" id="favSelect" name="favSelect">
                                            <option value="B" default selected>Batting team</option>
                                            <option value="C">Chasing team</option>
                                        </select>
                                    </div>
                                    
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <label class="input-group-text" for="biasSelect">Bias : </label>
                                        </div>
                                        <select class="form-control form-control required" id="biasSelect" name="biasSelect">
                                            <option value="f" default selected>Favorite</option>
                                            <option value="nf">Non-Favorite</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="form-group row">
                                <div class="col-12 teams">
                                    <select class="form-control form-control required" id="groundSelect" name="groundName">
                                        <option value="-1">Select Ground</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-12 teams">
                                    <input type="datetime-local" class="form-control form-control-sm" id="backDate" name="backDate">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col teams">
                                    <div class="custom-control custom-switch">
                                        <input class="custom-control-input" type="checkbox" id="backtestEnabled" name="backtestEnabled">
                                        <label class="custom-control-label" for="backtestEnabled">Backtests</label>
                                    </div>
                                </div>
                                
                            </div>
                           
                            <div class="form-group row">
                                <div class="col align-items-center teams">
                                    <div class="card">
                                        <div class="card-header" style="padding: 8px">
                                          <div class="input-group">
                                              <div class="input-group-prepend">
                                                <span class="input-group-text">Open filter</span>
                                              </div>
                                              <input type="text" class="form-control" disabled value="Greater than or equal">
                                              <input type="text" class="form-control" disabled value="Less than or equal">
                                          </div>
                                        </div>
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item"  style="padding: 8px">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                      <span class="input-group-text">First Wkt.</span>
                                                    </div>
                                                    <input name="FW-min" type="number" class="form-control">
                                                    <input name="FW-max" type="number" class="form-control">
                                                </div>
                                            </li>
                                            <li class="list-group-item"  style="padding: 8px">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                      <span class="input-group-text">First 6/10 Ov.</span>
                                                    </div>
                                                    <input name="FX-min" type="number" class="form-control">
                                                    <input name="FX-max" type="number" class="form-control">
                                                </div>
                                            </li>
                                            <li class="list-group-item"  style="padding: 8px">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                      <span class="input-group-text">Last 5/10 Ov.</span>
                                                    </div>
                                                    <input name="LX-min" type="number" class="form-control">
                                                    <input name="LX-max" type="number" class="form-control">
                                                </div>
                                            </li>
                                            <li class="list-group-item"  style="padding: 8px">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                      <span class="input-group-text">Inn. 1 Total</span>
                                                    </div>
                                                    <input name="TR-min" type="number" class="form-control" >
                                                    <input name="TR-max" type="number" class="form-control" >
                                                </div>
                                            </li>
                                            <li class="list-group-item"  style="padding: 8px">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                      <span class="input-group-text">Inn. 2 First Wkt</span>
                                                    </div>
                                                    <input name="FWS-min" type="number" class="form-control" >
                                                    <input name="FWS-max" type="number" class="form-control" >
                                                </div>
                                            </li>
                                            <li class="list-group-item"  style="padding: 8px">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                      <span class="input-group-text">Inn. 2 First 6/10 Ov.</span>
                                                    </div>
                                                    <input name="FXS-min" type="number" class="form-control">
                                                    <input name="FXS-max" type="number" class="form-control">
                                                </div>
                                            </li>
                                            
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <br>

                            <div class="form-group row">
                                <div class="col-12 teams">
                                    <button type="submit" class="btn btn-outline-success">Search</button>
                                </div>
                            </div>
                        </div>
                    </form>


                    <script>

                        $(document).ready(function () {
                            $("#tourSelect").children('[value="-1"]').attr('selected', true);
                        });

                        $("#tourSelect").change(function () {
                            $('#teamSelect1').empty().append('<option selected="selected" value="-1">Batting Team</option>');
                            $('#teamSelect2').empty().append('<option selected="selected" value="-1">Chasing Team</option>');
                            $('#groundSelect').empty().append('<option selected="selected" value="-1">Select Ground</option>');
                            var id = $(this).children(":selected").attr("value");

//                            var t1 = document.getElementById('tourSelect');
//                            
//                            if(t1.value == '-1'){
//                                $('.teams').hide();
//                            }
                            //                alert(id);
                            $.ajax({
                                url: "/CricVet/getTeams", //servlet URL that gets first option as parameter and returns JSON of to-be-populated options
                                type: "POST", //request type, can be GET
                                cache: false, //do not cache returned data
                                data: {matchType: id}, //data to be sent to the server
                                dataType: "json"//type of data returned
                            }).done(function (data) {
                                $('.teams').show();
                                //                    alert(data);
                                var team1 = $("#teamSelect1");
                                var team2 = $("#teamSelect2");

                                $.each(data, function (index, value) {
                                    team1.append($("<option />").val(value).text(value));
                                    team2.append($("<option />").val(value).text(value));
                                    //                        alert(index +" : " + value);
                                });
                            });

                            $.ajax({
                                url: "/CricVet/getGrounds", //servlet URL that gets first option as parameter and returns JSON of to-be-populated options
                                type: "POST", //request type, can be GET
                                cache: false, //do not cache returned data
                                data: {matchType: id}, //data to be sent to the server
                                dataType: "json"//type of data returned
                            }).done(function (data) {
                                $('.teams').show();
                                //                    alert(data);
                                var gs = $("#groundSelect");
                                $.each(data, function (index, value) {
                                    gs.append($("<option />").val(value).text(value));
                                    //                        alert(index +" : " + value);
                                });
                            });
                        });

//                        function validate() {
//                            var t1 = document.getElementById('teamSelect1');
//                            $('#teamSelect1').addClass('is-invalid')
//                            return !(t1.value == -1);
//                        }
                    </script>








                </div>
            </div>
        </div>
    </body>
</html>
