<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>list subServices</title>
</head>
<body>
<div align="center">
    <h2>list specialists</h2>

    <form:form action="/manager/listSubService" method="post">
        <table border="1" cellpadding="5">
            <tr>
                <th>Name</th>
                <th>family</th>
                <th>email</th>

            </tr>
            <c:forEach items="${subServiceDtoList}" var="each_one">
                <tr>
                    <td>${each_one.service.serviceName}</td>
                    <td>${each_one.subServiceName}</td>
                    <td>${each_one.price}</td>
                    <td>${each_one.explanations}</td>
                    <td>
                        <a href="/manager/saveSubServiceWithSpecialist?email=${specialist.email}&subServiceName=${each_one.subServiceName}">add</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form:form>
</div>
</body>
</html>
