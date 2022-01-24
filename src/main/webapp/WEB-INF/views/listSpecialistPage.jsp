
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>list specialists</title>
</head>
<body>
<div align="center">
    <h2>list specialists</h2>

<form:form action="/spcialist/list" method="post">
    <table border="1" cellpadding="5">
        <tr>
            <th>Name</th>
            <th>family</th>
            <th>email</th>

        </tr>
        <c:forEach items="${listSpecialists}" var="each_one">
            <tr>
                <td>${each_one.name}</td>
                <td>${each_one.family}</td>
                <td>${each_one.email}</td>
                <td>
                    <a href="addToSubService?email=${each_one.email}">add to subServices</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</form:form>
</div>
</body>
</html>
