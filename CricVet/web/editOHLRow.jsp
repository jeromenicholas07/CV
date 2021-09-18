<%-- 
    Document   : editOHLRow
    Created on : Jan 27, 2021, 3:06:46 AM
    Author     : Jerome Nicholas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Servlets.editFavourites" %>
<!DOCTYPE html>
<div class="row">
    <div class="card col-12">
        <div class="card-body">
            <div class="row">
                <div class="col-4">
                    <h3>${param.ohl_title}</h3>
                </div>
                <div class="col">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label2">Open</span>
                        </div>
                        <input type="number" class="form-control" aria-describedby="label2"
                               name = "${param.ohl_pre}_O" value='<%=((int) Double.parseDouble(request.getParameter("open").concat("0")))%>'>
                    </div>
                </div>

                <br>

                <div class="col">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label2">High</span>
                        </div>
                        <input type="number" class="form-control" aria-describedby="label2"
                               name = "${param.ohl_pre}_H" value='<%=((int) Double.parseDouble(request.getParameter("high").concat("0")))%>'>
                    </div>
                </div>

                <br>
                <div class="col">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="label2">Low</span>
                        </div>
                        <input type="number" class="form-control" aria-describedby="label2"
                               name = "${param.ohl_pre}_L" value="<%=((int) Double.parseDouble(request.getParameter("low").concat("0")))%>">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
