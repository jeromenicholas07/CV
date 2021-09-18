
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
            <a class="navbar-brand" href="#">Favorite Report</a>
        </nav>
        <br>
        <div class="container-fluid text-center">

            <!--Overall -->
            <div class="row justify-content-center">
                <div class="col-4">
                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="30"><h3 style="margin: .3rem;">Overall</h3></th>
                        </tr>
                        <tr>
                            <td>
                                ${overall.get("OO")}
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="col-4">
                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="30"><h3 style="margin: .3rem;">Toss</h3></th>
                        </tr>
                        <tr>
                            <td>
                                ${overall.get("OG")}
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <!--Head to head-->
            <div class="row justify-content-center">
                <div class="col-8">
                    <table class="table table-bordered">
                        <tr class="thead-dark">
                            <th colspan="30"><h3 style="margin: .3rem;">Head to head</h3></th>
                        </tr>
                        <tr class="table-secondary">
                            <c:forEach var="entry" items="${h2h}">
                                <th>
                                    ${entry.key}
                                </th>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="entry" items="${h2h}">
                                <td>
                                    ${entry.value}
                                </td>
                            </c:forEach>
                        </tr>
                    </table>
                </div>
            </div>


            <!--Form guide-->
            <jsp:include page="/renderFavTables.jsp">
                <jsp:param name="title" value="Form Guide" />
                <jsp:param name="A" value="FG_F" />
                <jsp:param name="B" value="FG_NF" />
                <jsp:param name="O" value="FG_O" />
            </jsp:include>

            <!-- BCW -->
            <jsp:include page="/renderFavTables.jsp">
                <jsp:param name="title" value="B/C winner" />
                <jsp:param name="A" value="BC_F" />
                <jsp:param name="B" value="BC_NF" />
                <jsp:param name="G" value="BC_G" />
                <jsp:param name="O" value="BC_O" />
            </jsp:include>


        </div>
    </body>
</html>
