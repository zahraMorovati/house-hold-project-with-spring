<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div align="center">
    <h2>New order</h2>

    <form:form action="/customer/saveOrder?email=${userDto.email}" method="post" modelAttribute="orderDto" >
        <table border="0" cellpadding="5">

            <tr>
                <td>subService: </td>
                <td><form:select path="subService" items="${subServices}"/></td>
            </tr>
            <tr>
                <td>suggestedPrice: </td>
                <td><form:input path="suggestedPrice"/></td>
            </tr>
            <tr>
                <td>explanations: </td>
                <td><form:input path="explanations"/></td>
            </tr>
            <tr>
                <td>startDate: </td>
                <td><input path="startDate" type="date"/></td>
            </tr>
            <tr>
                <td colspan="2"><td>address: </td>
            </tr>
            <tr>
                <td>city: </td>
                <td><form:input path="addressDto.city"/></td>
            </tr>
            <tr>
                <td>city state: </td>
                <td><form:input path="addressDto.cityState"/></td>
            </tr>
            <tr>
                <td>plaque: </td>
                <td><form:input path="addressDto.plaque"/></td>
            </tr>
            <tr>
                <td>explanations: </td>
                <td><form:input path="addressDto.explanations"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
