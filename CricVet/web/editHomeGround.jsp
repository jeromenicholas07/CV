<%-- 
    Document   : editHomeGround
    Created on : Nov 30, 2020, 9:17:05 PM
    Author     : Jerome Nicholas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Home-Ground</title>


        <!-- CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

        <!-- jQuery and JS bundle w/ Popper.js -->



        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
    </head>
    <body >
        <br>
        <br>
        <div class="form-group">
            <!--<label for="teamSelect">Select Tournament</label>-->
            <div class="col-3 mx-auto">
                <select class="form-control form-control-lg required" id="tourSelect" name="tournament">
                    <option value="-1" name="-1" selected disabled>Select Tournament</option>
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
        <br>
        <br>
        <table class="table table-bordered text-center col-6 mx-auto">
            <thead class="thead-dark">
                <tr>
                    <th>Ground Name</th>
                    <th>Home team</th>
                    <th><svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-trash-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5a.5.5 0 0 0-1 0v7a.5.5 0 0 0 1 0v-7z"/>
                        </svg></th>
                </tr>
            </thead>
            <tbody id="gr-list">

            </tbody>
        </table>

        <div class="form-group">
            <div class="row">
                <div class="col-4"></div>
                <div class="col-2">
                    <select class="form-control form-control required" id="groundSelect" name="groundName">
                        <option value="-1">Select Ground</option>
                    </select>
                </div>
                <div class="col-2">
                    <select class="form-control form-control required" id="teamSelect" name="teamName1">
                        <option value="-1">Select Team</option>
                    </select>
                </div>
                <div class="col-2">
                    <button class="btn btn-dark" onclick="addEntry()">
                        <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-arrow-90deg-up" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M4.854 1.146a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L4 2.707V12.5A2.5 2.5 0 0 0 6.5 15h8a.5.5 0 0 0 0-1h-8A1.5 1.5 0 0 1 5 12.5V2.707l3.146 3.147a.5.5 0 1 0 .708-.708l-4-4z"/>
                        </svg>
                        &nbsp;&nbsp;Update
                    </button>
                </div>

            </div>
        </div>

        <div style="display: none">
            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-trash-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5a.5.5 0 0 0-1 0v7a.5.5 0 0 0 1 0v-7z"/>
            </svg>
        </div>

        <script>
            $(document).ready(function () {
                let selectedVal = sessionStorage.getItem("selectedVal");
                if (selectedVal) {
                    $("#tourSelect").val(selectedVal).change();
                }
            });

            $("#tourSelect").change(function () {
                $('#teamSelect').empty().append('<option selected="selected" value="-1">Select Team</option>');
                $('#groundSelect').empty().append('<option selected="selected" value="-1">Select Ground</option>');

                let tbody = $('#gr-list');
                $(tbody).empty();
                let selectedVal = $(this).children(":selected").attr("value");
                let matchType = parseInt(selectedVal);
                sessionStorage.setItem("selectedVal", matchType);

                //fill home ground table
                $.ajax({
                    url: "/CricVet/getHomeGrounds", //servlet URL that gets first option as parameter and returns JSON of to-be-populated options
                    type: "POST", //request type, can be GET
                    cache: false, //do not cache returned data
                    data: {matchType: matchType}, //data to be sent to the server
                    dataType: "json"//type of data returned
                }).done(function (data) {
                    $.each(data, function (grName, teamName) {

                        let tr = document.createElement('tr');

                        let td1 = document.createElement('td');
                        td1.appendChild(document.createTextNode(grName));
                        tr.appendChild(td1);

                        let td2 = document.createElement('td');
                        td2.appendChild(document.createTextNode(teamName));
                        tr.appendChild(td2);

                        let td3 = document.createElement('td');
                        td3.setAttribute("class", "px-0");
                        let delButton = document.createElement('button');
                        delButton.setAttribute("class", "btn btn-light mx-0");
                        delButton.setAttribute("data-matchtype", matchType);
                        delButton.setAttribute("data-grname", grName);
                        delButton.setAttribute("data-teamname", teamName);
                        delButton.onclick = clearHomeGround;
                        let delButtSvg = document.getElementsByClassName('bi-trash-fill')[0].cloneNode(true);
                        delButton.appendChild(delButtSvg);
                        td3.appendChild(delButton);

                        tr.appendChild(td3);

                        tbody.append(tr);

//                       alert(grName +" : " + teamName);
                    });
                });


                //fill teams dropdown
                $.ajax({
                    url: "/CricVet/getTeams", //servlet URL that gets first option as parameter and returns JSON of to-be-populated options
                    type: "POST", //request type, can be GET
                    cache: false, //do not cache returned data
                    data: {matchType: matchType}, //data to be sent to the server
                    dataType: "json"//type of data returned
                }).done(function (data) {
                    var team1 = $("#teamSelect");

                    $.each(data, function (index, value) {
                        team1.append($("<option />").val(value).text(value));
                    });
                });

                //fill ground dropdown
                $.ajax({
                    url: "/CricVet/getGrounds", //servlet URL that gets first option as parameter and returns JSON of to-be-populated options
                    type: "POST", //request type, can be GET
                    cache: false, //do not cache returned data
                    data: {matchType: matchType}, //data to be sent to the server
                    dataType: "json"//type of data returned
                }).done(function (data) {
                    var gs = $("#groundSelect");
                    $.each(data, function (index, value) {
                        gs.append($("<option />").val(value).text(value));
                    });
                });
            });

            function clearHomeGround() {
                let matchType = $(this).attr('data-matchtype');
                let grName = $(this).attr('data-grname');
                let teamName = $(this).attr('data-teamname');


                $.ajax({
                    url: "/CricVet/deleteHomeGround", //servlet URL that gets first option as parameter and returns JSON of to-be-populated options
                    type: "POST", //request type, can be GET
                    cache: false, //do not cache returned data
                    data: {matchType: matchType, groundName: grName, teamName: teamName}, //data to be sent to the server
                    dataType: "json"//type of data returned
                }).done(function (data) {
                    alert("Entry deleted!");
                    location.reload();
                });
            }

            function addEntry() {
                let matchType = parseInt($('#tourSelect').children(":selected").attr("value"));
                let groundName = $('#groundSelect').children(":selected").attr("value");
                let teamName = $('#teamSelect').children(":selected").attr("value");
                console.log(matchType);
                console.log(groundName);
                console.log(teamName);

                if (matchType == '-1' || groundName == '-1' || teamName == '-1') {
                    alert("Invalid Entry!!!");
                } else {
                    $.ajax({
                        url: "/CricVet/addHomeGround", //servlet URL that gets first option as parameter and returns JSON of to-be-populated options
                        type: "POST", //request type, can be GET
                        cache: false, //do not cache returned data
                        data: {matchType: matchType, groundName: groundName, teamName: teamName}, //data to be sent to the server
                        dataType: "json"//type of data returned
                    }).done(function (data) {
                        if (data) {
                            alert("Entry Added");
                        } else {
                            alert("failed to add entry");
                        }
                        location.reload();
                    });
                }

            }
        </script>


        <div style="position: fixed;right: 0;bottom: 0;">
            <button type="submit" class="btn btn-lg btn-danger" onclick="confirmReset()">RESET International Homegrounds</button>
        </div>

        <script>
            function confirmReset() {
                if (confirm("Are you sure you want to RESET all Test/T20I/ODI HomeGrounds?")) {
                    $.ajax({
                        url: "/CricVet/resetHomeGround", //servlet URL that gets first option as parameter and returns JSON of to-be-populated options
                        type: "POST", //request type, can be GET
                        cache: false, //do not cache returned data
                        data: {}, //data to be sent to the server
                        dataType: "json"//type of data returned
                    }).done(function (data) {
                        alert("Done!");
                        location.reload();
                    });
                }
            }
        </script>
    </body>
</html>
