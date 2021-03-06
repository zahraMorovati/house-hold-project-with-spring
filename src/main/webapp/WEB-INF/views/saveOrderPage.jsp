<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
            crossorigin="anonymous"
    />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script>

        $(document).ready(function(){

            $('#comboboxService').on('change', function(){
                var service = $(this).val();
                $.ajax({
                    type: 'GET',
                    url: window.location.origin+'/loadService/' + service,
                    success: function(result) {
                        var result = JSON.parse(result);
                        var s = '';
                        for(var i = 0; i < result.length; i++) {
                            s += '<option value="' + result[i].name + '">' + result[i].name + '</option>';
                        }
                        $('#comboboxSubService').html(s);
                    }
                });
            });

        });
    </script>

</head>
<body>
<div align="center">
    <h2>New order</h2>

    <form:form action="/customer/saveOrder" method="post" modelAttribute="orderDto">
        <table border="0" cellpadding="5">
            <p class="text-danger">${errorSuggestedPrice}</p>
            <p class="text-danger">${saveOrderErrors}</p>
            <tr hidden>
                <td>${orderDto.customer}
                    <form:hidden path="customer"/>
                </td>
            </tr>
            <tr>
                <td>service:</td>
                <td><select id="comboboxService" style="width:200px">
                    <c:forEach var="service" items="${services }">
                        <option value="${service.value}">${service.value}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td>sub service:</td>
                <td>
                        <form:select path="subService" id="comboboxSubService" cssStyle="width: 200px"></form:select>
                </td>
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
        src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF"
        crossorigin="anonymous"
></script>
</body>
</html>
