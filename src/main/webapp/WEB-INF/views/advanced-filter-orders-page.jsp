<%--
  Created by IntelliJ IDEA.
  User: zahra
  Date: 2/16/2022
  Time: 11:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>advanced filter orders</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script>
        jQuery(document).ready(function ($) {

            $("#search-form").submit(function (event) {

                console.log("submitted");
                // Disble the search button
                enableSearchButton(false);

                // Prevent the form from submitting via the browser.
                event.preventDefault();

                searchGetViaAjax();

            });

        });

        function searchViaAjax() {

            var search = {}
            search["startDate"] = $("#startDate").val();
            search["endDate"] = $("#endDate").val();
            search["orderState"] = $("#orderState").val();
            search["service"] = $("#service").val();
            search["subService"] = $("#subService").val();

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: window.location.origin + "/rest/advancedFilterOrders",
                data: JSON.stringify(search),
                dataType: 'json',
                timeout: 100000,
                success: function (data) {
                    console.log("SUCCESS: ", data);
                    display(data);
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                    display(e);
                },
                done: function (e) {
                    console.log("DONE");
                    enableSearchButton(true);
                }
            });

        }

        function searchGetViaAjax() {

            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var orderState = $("#orderState").val();
            var service = $("#service").val();
            var subService = $("#subService").val();

            $.ajax({
                type: "GET",
                url: window.location.origin + "/rest/advancedFilterOrders?startDate=" + startDate + "&endDate=" + endDate+ "&orderState=" + orderState+ "&service=" + service+ "&subService=" + subService,
                success: function (data) {
                    console.log("SUCCESS: ", data);
                    display(data);
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                    display(e);
                },
                done: function (e) {
                    console.log("DONE");
                    enableSearchButton(true);
                }
            });

        }

        function enableSearchButton(flag) {
            $("#btn-search").prop("disabled", flag);
        }

        function display(data) {
            console.log(document.referrer)
            var tableHeaderRowCount = 2;
            var table = document.getElementById('orderTbl');
            var rowCount = table.rows.length;
            for (var i = tableHeaderRowCount; i < rowCount; i++) {
                table.deleteRow(tableHeaderRowCount);
            }
            var trHTML = '';
            $.each(data, function (i, item) {
                console.log(item);
                trHTML += '<tr><td>' + item.orderCode + '</td><td>' + item.subService + '</td><td>'+ item.suggestedPrice + '</td><td>' + item.explanations + '</td><td>' + item.registrationDate+'</td><td>' + item.orderState+ '</td><td>'+ item.specialist+ '</td><td>'+ item.customer+ '</td><td>'+ item.comment + '</td></tr>';
            });
            $('#orderTbl').append(trHTML);
        }
    </script>
</head>
<body>
<div class="container">
    <form id="search-form" class="m-5 p-5 text-center" style="width: 1200px">

        <tr>
            <td>
                <label for="service">service: </label><input id="service" name="service" placeHolder="service"/>
            </td>
            <td>
                <label for="subService">sub service: </label><input id="subService" name="subService" placeHolder="subService"/>
            </td>
            <td>
                <label for="orderState">order state: </label><input id="orderState" name="orderState" placeHolder="orderState"/>
            </td>
            <td>
                <p>registration date: </p>
                <input id="startDate" name="startDate" type="date"/>
                <p>between</p>
                <input id="endDate" name="endDate" type="date"/>
            </td>
            <td>
                <button type="submit" id="bth-search">Search</button>
            </td>
        </tr>

        <table id="orderTbl" class="table table-striped table-hover">
            <tr>
                <th>order number</th>
                <th>sub service</th>
                <th>suggested price</th>
                <th>explanations</th>
                <th>start date</th>
                <th>order state</th>
                <th>specialist</th>
                <th>customer</th>
                <th>comment</th>

            </tr>
        </table>
        <div id="feedback"></div>
    </form>
</div>
</body>
</html>
