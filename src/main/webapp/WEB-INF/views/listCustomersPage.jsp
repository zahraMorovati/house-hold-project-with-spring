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

    <p style="color: #21ea21">${success}</p>
    <form method="get" action="/manager/listCustomer<%--?mEmail=${managerEmail}--%>">
        <tr>
            <th>name:</th>
            <th> <input type="text" name="name" /></th>
        </tr>
        <tr>
            <th>family:</th>
            <th>
            <input type="text" name="family" />
            </th>
        </tr>
        <tr>
            <th>email:</th>
            <th>
                <input type="text" name="email" />
            </th>
        </tr>
        <tr>
            <th colspan="2"><input type="submit" value="Search"/></th>
        </tr>
        <br/><br/>
        <%--<input type="hidden" name="mEmail" value="${managerEmail} ">--%>
        <table border="1" cellpadding="5">
            <tr>
                <th>Name</th>
                <th>family</th>
                <th>email</th>
                <th>state</th>
                <th>registrationDate</th>
                <th>action</th>

            </tr>
            <c:forEach items="${listCustomers}" var="each_one">
                <tr>
                    <td>${each_one.name}</td>
                    <td>${each_one.family}</td>
                    <td>${each_one.email}</td>
                    <td>${each_one.state}</td>
                    <td>${each_one.registrationDate}</td>
                    <td><a href="/manager/confirmCustomer?c=${each_one.email}<%--&m=${managerEmail}--%>">confirm user</a></td>
                </tr>
            </c:forEach>
        </table>

        <br/>
        <a href="/manager/dashbord/">back to dashbord</a>
    </form>
</div>
</body>
</html>
