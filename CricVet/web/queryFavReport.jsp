<%-- 
    Document   : queryFavReport
    Created on : Dec 1, 2020, 9:34:00 PM
    Author     : Jerome Nicholas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Fav. Report</title>

        <!-- CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

        <!-- jQuery and JS bundle w/ Popper.js -->



        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>

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

    </head>
    <body class="text-center">
        <div class="outer">
            <div class="middle">
                <div class="inner">
                    <form action="getFavReport" method="POST">
                        <div class="form-group row">

                            <select class="form-control form-control-lg required" id="tourSelect" name="tournament" required>
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
                        <div class="form-group row">
                            <div class="input-group col-8 mx-auto">
                                <div class="input-group-prepend">
                                    <label class="input-group-text" for="sideSelect">Favourite is </label>
                                </div>
                                <select class="form-control form-control-lg" id="sideSelect" name="sideSelect">
                                    <option value="B" selected>Batting</option>
                                    <option value="C">Chasing</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="input-group mb-3 col-8 mx-auto">
                                <div class="input-group-prepend">
                                    <label class="input-group-text" for="tossSelect">Favourite </label>
                                </div>
                                <select class="form-control form-control-lg" id="tossSelect" name="tossSelect">
                                    <option value="W" selected>Won</option>
                                    <option value="L">Lost</option>
                                </select>
                                <div class="input-group-append">
                                    <label class="input-group-text" for="tossSelect">Toss</label>
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-dark btn-lg">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
