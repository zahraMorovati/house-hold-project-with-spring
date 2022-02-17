
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>save subService</title>
</head>
<body>
<form:form action="/manager/saveSubService" method="post" modelAttribute="newSubServiceObject" enctype="multipart/form-data">
    <p style="color: #ee2222">${duplicatedSubService}</p>
    <p style="color: #35e835">${subServiceSaved}</p>
    <br/>
    <table border="0" cellpadding="5">
        <tr>
            <td>service: </td>
            <td><form:select path="service.serviceName" items="${services}" id="service"/></td>
        </tr>
        <tr>
            <td>name subService: </td>
            <td><form:input path="subServiceName" /></td>
        </tr>
        <tr>
            <td>price: </td>
            <td><form:input path="price" id="price" /></td>
        </tr>
        <tr>
            <td>explanations: </td>
            <td><form:input path="explanations" /></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Save"></td>
        </tr>
    </table>
</form:form>
</body>
</html>
