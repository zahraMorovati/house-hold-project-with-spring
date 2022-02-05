<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
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
    <h2>New order</h2>

    <form:form action="/customer/saveOrder" method="post" modelAttribute="orderDto">
        <table border="0" cellpadding="5">
            <p class="text-danger">${errorSuggestedPrice}</p>
            <tr hidden>
                <td>${orderDto.customer}
                    <form:hidden path="customer"/>
                </td>
            </tr>
            <tr>
                <td>subService:</td>
                <td><form:select path="subService" items="${subServices}"/></td>
            </tr>
            <tr>
                <td>suggestedPrice:</td>
                <td><form:input path="suggestedPrice"/></td>
            </tr>
            <tr>
                <td>explanations:</td>
                <td><form:input path="explanations"/></td>
            </tr>
            <tr>
                <td>startDate:</td>
                <td><input name="date" type="date"/></td>
            </tr>
            <tr>
                <td colspan="2"> address:</td>
            </tr>
            <tr>
                <td>city:</td>
                <td><form:input path="city"/></td>
            </tr>
            <tr>
                <td>city state:</td>
                <td><form:input path="cityState"/></td>
            </tr>
            <tr>
                <td>plaque:</td>
                <td><form:input path="plaque"/></td>
            </tr>
            <tr>
                <td>explanations:</td>
                <td><form:input path="addressExplanations"/></td>
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
