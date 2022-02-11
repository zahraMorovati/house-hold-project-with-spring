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

</head>
<body>
<form>
    <table>
        <tr>
            <td>service</td>
            <td>
                <select id="comboboxService" style="width:200px" onchange="getServiceElements()">
                    <c:forEach var="service" items="${services }">
                        <option value="${service.name}">${service.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>subService</td>
            <td>
                <select id="comboboxSubService" ></select>
            </td>
        </tr>

    </table>

    <table>
        <tr>
            <th>
                name
            </th>
        </tr>
    </table>
</form>

<%--<script src="${pageContext.request.contextPath}/static/js/controler.js"></script>
<script src="${pageContext.request.contextPath}/static/js/sevice.js"></script>
<script src="${pageContext.request.contextPath}/static/js/logic.js"></script>--%>
<script>

    var select = document.getElementById('comboboxService');
    var service = select.options[select.selectedIndex].value;

    alert(service);
    function getServiceElements(){
        $.ajax({
            type: 'GET',
            url: '/loadService/' + service,
            success: function(result) {
                var result = JSON.parse(result);
                var s = '';
                for(var i = 0; i < result.length; i++) {
                    s += '<option value="' + result[i].name + '">' + result[i].name + '</option>';
                }
                $('#comboboxSubService').html(s);
            }
        });
    }
    $(document).ready(function(){

        $('#comboboxService').on('change', function(){

        });
    });
</script>
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
