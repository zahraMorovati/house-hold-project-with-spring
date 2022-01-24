<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>list customers</title>
</head>
<body>
<div align="center">
    <h2>list customers</h2>

    <form:form action="/customer/list" method="post">
        <table border="1" cellpadding="5">
            <tr>
                <th>Name</th>
                <th>family</th>
                <th>email</th>

            </tr>
            <c:forEach items="${listCustomers}" var="each_one">
                <tr>
                    <td>${each_one.name}</td>
                    <td>${each_one.family}</td>
                    <td>${each_one.email}</td>
                </tr>
            </c:forEach>
        </table>
    </form:form>
</div>
</body>
</html>
