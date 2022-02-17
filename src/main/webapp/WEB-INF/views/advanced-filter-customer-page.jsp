<%--
  Created by IntelliJ IDEA.
  User: zahra
  Date: 2/16/2022
  Time: 5:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>advanced filter customers</title>
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
            search["name"] = $("#name").val();
            search["family"] = $("#family").val();
            search["email"] = $("#email").val();
            search["startDate"] = $("#startDate").val();
            search["endDate"] = $("#endDate").val();
            search["minOrderNumber"] = $("#minOrderNumber").val();
            search["maxOrderNumber"] = $("#maxOrderNumber").val();

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: window.location.origin + "/rest/advancedFilterCustomer",
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

            var name = $("#name").val();
            var family = $("#family").val();
            var email = $("#email").val();
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var minOrderNumber = $("#minOrderNumber").val();
            var maxOrderNumber = $("#maxOrderNumber").val();

            $.ajax({
                type: "GET",
                url: window.location.origin + "/rest/advancedFilterCustomer?name=" + name + "&family=" + family+ "&email=" + email+ "&startDate=" + startDate+ "&endDate=" + endDate+ "&minOrderNumber=" + minOrderNumber+ "&maxOrderNumber=" + maxOrderNumber,
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
            var table = document.getElementById('customerTbl');
            var rowCount = table.rows.length;
            for (var i = tableHeaderRowCount; i < rowCount; i++) {
                table.deleteRow(tableHeaderRowCount);
            }
            var trHTML = '';
            $.each(data, function (i, item) {
                console.log(item);
                trHTML += '<tr><td>' + item.name + '</td><td>' + item.family + '</td><td>'+ item.email + '</td><td>' + item.state + '</td><td>'+ item.registrationDate + '</td></tr>';
            });
            $('#customerTbl').append(trHTML);
        }
    </script>
</head>
<body>
<div class="container">
    <form id="search-form" class="m-5 p-5 text-center" style="width: 1200px">
        <table id="customerTbl" class="table table-striped table-hover">
            <tr>
                <td>
                    <label for="name">name: </label><input id="name" name="name" placeHolder="name"/>
                </td>
                <td>
                    <label for="family">family: </label><input id="family" name="family" placeHolder="family"/>
                </td>
                <td>
                    <label for="email">email: </label><input id="email" name="email" placeHolder="email"/>
                </td>
                <td>
                    <p>registration date: </p>
                    <input id="startDate" name="startDate" type="date"/>
                    <p>between</p>
                    <input id="endDate" name="endDate" type="date"/>
                </td>
                <td>
                    <p>order number: </p>
                    <input id="minOrderNumber" name="minOrderNumber" type="number"/>
                    <p>between</p>
                    <input id="maxOrderNumber" name="maxOrderNumber" type="number"/>

                </td>
                <td>
                    <button type="submit" id="bth-search">Search</button>
                </td>
            </tr>
            <tr>
                <th>name</th>
                <th>family</th>
                <th>email</th>
                <th>state</th>
                <th>registration date</th>
            </tr>
        </table>
        <div id="feedback"></div>
    </form>
</div>
</body>
</html>
