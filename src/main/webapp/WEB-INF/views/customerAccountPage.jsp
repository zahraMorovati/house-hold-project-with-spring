<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>customer account</title>
</head>
<body>
<div align="center">
    <h2>Customer account</h2>
    <form:form action="/user/login" method="post">
        <table border="0" cellpadding="5">
            <tr>
                <td>Name: </td>
                <td>${userDto.name}</td>
            </tr>
            <tr>
                <td>family: </td>
                <td>${userDto.family}</td>
            </tr>
            <tr>
                <td>balance: </td>
                <td>${userDto.balance}</td>
            </tr>
        </table>
        <br/> <br/>
        <a href="/customer/addOrder?email=${userDto.email}">add order</a>
        <br/> <br/>
        <h2> customer order list</h2>
        <table border="1" cellpadding="5">
            <tr>
                <th>order number</th>
                <th>subService</th>
                <th>suggestedPrice</th>
                <th>explanations</th>
                <th>registrationDate</th>
                <th>startDate</th>
                <th>specialist</th>
                <th>order state</th>
                <th>address</th>
                <th>comment</th>
                <th>point</th>

            </tr>
            <c:forEach items="${orders}" var="each_one">
                <tr>
                    <td>${each_one.orderCode}</td>
                    <td>${each_one.subService}</td>
                    <td>${each_one.suggestedPrice}</td>
                    <td>${each_one.explanations}</td>
                    <td>${each_one.registrationDate}</td>
                    <td>${each_one.startDate}</td>
                    <td>${each_one.specialist}</td>
                    <td>${each_one.orderState}</td>
                    <td>${each_one.address}</td>
                    <td>${each_one.comment}</td>
                    <td>${each_one.point}</td>

                </tr>
            </c:forEach>
        </table>
    </form:form>
</div>

</body>
</html>
