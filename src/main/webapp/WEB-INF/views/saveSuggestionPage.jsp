<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />

    <!-- Bootstrap CSS -->
    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
            integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
            crossorigin="anonymous"
    />
</head>
<body>
<div align="center">
    <h2>save suggestion</h2>

    <form:form action="/specialist/saveSuggestion" method="post" modelAttribute="suggestionDto" >
        <table border="0" cellpadding="5">
            <p class="text-danger">${error}</p>
            <p class="text-danger">${saveSuggestionErrors}</p>
            <p style="color: #2fef2f">${suggestionSaved}</p>
            <a href="/specialist/dashbord/">back to dashbord</a>
            <%--<tr hidden>
                <td>${suggestionDto.specialistEmail}
                    <form:hidden path="specialistEmail"/>
                </td>
            </tr>--%>
            <tr hidden>
                <td>${suggestionDto.orderCode}
                    <form:hidden path="orderCode"/>
                </td>
            </tr>
            <tr>
                <td>startTime: </td>
                <td>
                    <input name="timePicker" type="time"/>
                </td>
            </tr>
            <tr>
                <td>workHour: </td>
                <td><form:input path="workHour"/></td>
            </tr>
            <tr>
                <td>suggestedPrice: </td>
                <td><form:input path="suggestedPrice"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"
></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF"
        crossorigin="anonymous"
></script>
</body>
</html>
