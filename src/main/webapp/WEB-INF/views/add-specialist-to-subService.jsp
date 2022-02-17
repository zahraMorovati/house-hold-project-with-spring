<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>list subServices</title>
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

                var table = document.getElementById('table');

                for(var i = 1; i < table.rows.length; i++)
                {
                    table.rows[i].onclick = function()
                    {
                        document.getElementById("email").value = this.cells[2].innerHTML;
                    };
                }


        });
    </script>
</head>
<body>
<div align="center">
    <h2>list specialists</h2>
    <p style="color: #ee2222">${duplicatedSubService}</p>
    <p style="color: #ee2222">${errors}</p>
    <p style="color: #2fef2f">${specialistAddedSuccessfully}</p>
    <form:form action="/manager/add-specialist-to-subService">
        <table>
            <tr>
                <td>
                    service:
                </td>
                <td><select id="comboboxService" style="width:200px">
                    <c:forEach var="service" items="${services}">
                        <option value="${service.value}">${service.value}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td>sub service:</td>
                <td>
                    <select name="subService" id="comboboxSubService" style="width: 200px"></select>
                </td>
            </tr>
            <tr>
                <td>customer:</td>
                <td>
                    <input name="email" id="email" style="width: 200px" >
                </td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="save" style="width: 200px"></td>
            </tr>

        </table>
    </form:form>
    <br/>
    <table border="1" cellpadding="5" id="table">
        <tr>
            <th>name</th>
            <th>family</th>
            <th>email</th>
            <th>state</th>
            <th>registration date</th>

        </tr>
        <c:forEach items="${specialists}" var="each_one">
            <tr>

                <td>${each_one.name}</td>
                <td>${each_one.family}</td>
                <td>${each_one.email}</td>
                <td>${each_one.state}</td>
                <td>${each_one.registrationDate}</td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>
