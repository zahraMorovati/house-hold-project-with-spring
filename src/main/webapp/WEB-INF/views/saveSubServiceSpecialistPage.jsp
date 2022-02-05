<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div align="center">
    <h2>list specialists</h2>

    <form:form action="/specialist/newSubService" method="post">
        <table border="1" cellpadding="5">
            <tr>
                <th>serviceName</th>
                <th>subServiceName</th>
                <th>price</th>
                <th>explanations</th>
                <th></th>

            </tr>
            <c:forEach items="${subServiceDtoList}" var="each_one">
                <tr>
                    <td>${each_one.service.serviceName}</td>
                    <td>${each_one.subServiceName}</td>
                    <td>${each_one.price}</td>
                    <td>${each_one.explanations}</td>
                    <td>
                        <a href="/specialist/saveSubService?email=${userDto.email}&subServiceName=${each_one.subServiceName}">select</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form:form>
</div>

</body>
</html>
