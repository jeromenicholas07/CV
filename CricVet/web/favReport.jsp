
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Favorites Report</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
    </head>
    <body>
        <style>
            .badge{
                font-size: 20px;
                text-align: right;
            }
        </style>
        <nav class="navbar sticky-top navbar-dark bg-dark">
            <a class="navbar-brand">Favorites Report</a>
        </nav>
        <br>
        <div class="container-fluid text-center">
            <div class="row">
                <div class="col">
                    <div class="card" style="width: 18rem;">
                        <div class="card-header">
                            Master
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                Favorite Batting W/L
                                <span class="badge badge-light" >${masterBatting}</span>
                            </li>
                            <li class="list-group-item">
                                Favorite Chasing W/L
                                <span class="badge badge-light">${masterChasing}</span>
                            </li>
                        </ul>
                    </div>
                </div>

                <div class="col">
                    <div class="card" style="width: 18rem;">
                        <div class="card-header">
                            Fav. Toss Won
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                Favorite Batting W/L
                                <span class="badge badge-light">${tossWB}</span>
                            </li>
                            <li class="list-group-item">
                                Favorite Chasing W/L
                                <span class="badge badge-light">${tossWC}</span>
                            </li>
                        </ul>
                    </div>
                </div>

                <div class="col">
                    <div class="card" style="width: 18rem;">
                        <div class="card-header">
                            Fav. Toss Lost
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                Favorite Batting W/L
                                <span class="badge badge-light">${tossLB}</span>
                            </li>
                            <li class="list-group-item">
                                Favorite Chasing W/L
                                <span class="badge badge-light">${tossLC}</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
