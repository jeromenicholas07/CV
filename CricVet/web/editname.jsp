<%-- 
    Document   : editname
    Created on : Sep 19, 2020, 7:47:12 PM
    Author     : ferdi
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="Database.CricDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


        <title>Edit Page</title>
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
                    
                <table class='table table-bordered text-center'>
                    <thead class='thead-dark'>
                        <tr>
                            <form method="POST" id="db">
                                <button colspan="2" class="col-12 btn btn-lg btn-warning" type="submit" value = "editNameDB" onclick="form.action='editNameDB';">
                                    Apply on Database
                                </button>
                            </form>
                        </tr>
                        <tr>
                            <th rowspan='2'>Old Name</th>
                            <th rowspan='2'>New Name</th>
                        </tr>
                    </thead>
                    <tbody>
                            
                    <%
                        CricDB db = new CricDB();
                        db.initDB();
                        Map<String, String> names = db.getEditTeamNameDB();
                        
                        for(String key : names.keySet()){
                            String value = names.get(key);
                            out.println("<tr>");    
                            out.println("<td>"+key);
                            out.println("<td>"+value+"<br>");
                            out.println("</tr>");
                        }
                    %>
                    
                    </tbody>
                </table>
                    <form method="POST" id="form">
                        <div class="container form-query">
                            <div class="form-group">
                                <div class="col-12">
                                    <select class="form-control form-control-lg required" id="tourSelect" name="tournament">
                                        <option value="-1" name="-1">Select Tournament</option>
                                        <option value="117" name="117">Indian Premier League</option>
                                        <option value="158" name="158">Big Bash League</option>
                                        <option value="159" name="159">Bangladesh Premier League</option>
                                        <option value="748" name="748">Caribbean Premier League</option>
                                        <option value="205" name="205">Pakistan Super League</option>

                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-6 teams">
                                    <select class="form-control form-control required" id="teamSelect" name="oldName">
                                        <option value="-1" disabled="true" selected>Old Team</option>
                                    </select>
                                </div>
                                <div class="col-6 teams">
                                    <select class="form-control form-control required" id="teamSelectNew" name="newName">
                                        <option value="-1" disabled="true" selected>New Team</option>
                                    </select>
                                </div>
                            </div>
                            

                            <div class="form-group row">
                                <div class="col-12 teams">
                                    <button type="submit" value ="editNameTable" class="btn btn-success" onclick="form.action='editNameTable';">Add entry</button>
                                </div>
                                <div class="col-12 teams">
                                    
                                </div>
                            </div>
                        </div>
                    </form>


                    <script>
                        
                        $(document).ready(function(){
                            $("#tourSelect").children('[value="-1"]').attr('selected', true);
                        });

                        $("#tourSelect").change(function () {
                            $('#teamSelect1').empty().append('<option selected="selected" value="-1">Batting Team</option>');

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
                                var team1 = $("#teamSelect");
                                var team2 = $("#teamSelectNew");

                                $.each(data, function (index, value) {
                                    team1.append($("<option />").val(value).text(value));
                                    team2.append($("<option />").val(value).text(value));
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
