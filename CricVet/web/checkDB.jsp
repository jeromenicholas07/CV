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


        <title>Check Database</title>
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
            .test-side{
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
                    <form method="POST" action="getDB" id="form">
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
                                <div class="col-12 teams">
                                    <select class="form-control form-control required" id="teamSelect1" name="teamName1" >
                                        <option value="-1">Select Team</option>
                                    </select>
                                </div>
                                
                            </div>
                            
                            <div class="form-group row">
                                <div class="col-12 test-side">
                                    <select class="form-control form-control-sm" id="homeorawaySelect" name="homeoraway">
                                        <option value="Home" default>Home</option>
                                        <option value="Away">Away</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-12 teams">
                                    <button type="submit" class="btn btn-outline-success">Search</button>
                                </div>
                            </div>
                        </div>
                    </form>

                    
                    
                    <script>
                        
                        $(document).ready(function(){
                            $("#tourSelect").children('[value="-1"]').attr('selected', true);
                        });

                        $("#tourSelect").change(function () {
                            $('#teamSelect1').empty().append('<option selected="selected" value="-1">Select Team</option>');
                            
                            var id = $(this).children(":selected").attr("value");
                            if(id !== "1"){
                                $('.test-side').hide();
                            }
                                
                            $.ajax({
                                url: "/CricVet/getTeams", //servlet URL that gets first option as parameter and returns JSON of to-be-populated options
                                type: "POST", //request type, can be GET
                                cache: false, //do not cache returned data
                                data: {matchType: id}, //data to be sent to the server
                                dataType: "json"//type of data returned
                            }).done(function (data) {
                                $('.teams').show();
                                if(id === "1"){
                                    $('.test-side').show();
                                }
                                var team1 = $("#teamSelect1");
                                
                                $.each(data, function (index, value) {
                                    team1.append($("<option />").val(value).text(value));
                                    
                                });
                            });
                            
                            
//                            var sel = document.getElementById('homeorawaySelect');
//                            var opt1 = document.createElement('option');
//                            opt1.appendChild( document.createTextNode('Home') );
//                            opt1.value = 'Home'; 
//                            sel.appendChild(opt1);
//                            var opt2 = document.createElement('option');
//                            opt2.appendChild( document.createTextNode('Away') );
//                            opt2.value = 'Away'; 
//                            sel.appendChild(opt2);

                            
                        });

//                        function validate() {
//                            var t1 = document.getElementById('teamSelect1');
//                            $('#teamSelect1').addClass('is-invalid')
//                            return !(t1.value == -1);
//                        }
                    </script>








                </div>
            </div>
            
            <form>
                <div style="position: fixed;right: 0;bottom: 0;">
                    <button type="submit" class="btn btn-lg btn-danger" onclick="confirmDelete()">DELETE ALL DATABASES</button>
                </div>
            </form>
            
        </div>
        
        <script>
            function confirmDelete(){
                if(confirm("Are you sure you want to DELETE all databases?")){
                    location.href = '/CricVet/deleteDB';
                }
            }
        </script>
        
    </body>
</html>
