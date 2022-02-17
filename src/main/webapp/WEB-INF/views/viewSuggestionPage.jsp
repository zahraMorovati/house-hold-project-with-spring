<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div align="center">
    <h2>list suggestions</h2>

    <form:form action="/specialist/viewSuggestions" method="post">
        <table border="1" cellpadding="5">
            <tr>
                <th>suggestion code</th>
                <th>specialist</th>
                <th>start time</th>
                <th>work hour</th>
                <th>suggested price</th>
                <th></th>

            </tr>
            <c:forEach items="${suggestions}" var="each_one">
                <tr>
                    <td>${each_one.suggestionCode}</td>
                    <td>${each_one.specialistName}</td>
                    <td>${each_one.timeStart}</td>
                    <td>${each_one.workHour}</td>
                    <td>${each_one.suggestedPrice}</td>
                    <td><a href="/customer/selectSuggestion?suggestionCode=${each_one.suggestionCode}<%--&email=${email}--%>&orderCode=${each_one.orderCode}">select suggestion</a></td>
                </tr>
            </c:forEach>
        </table>
    </form:form>
</div>
</body>
</html>
