
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
    <p style="color: #21ea21">${success}</p>

<form action="/manager/listSpecialist" method="get">
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
    <input type="hidden" name="mEmail" value="${managerEmail} ">
    <br/><br/>
    <table border="1" cellpadding="5">
        <tr>
            <th>Name</th>
            <th>family</th>
            <th>email</th>
            <th>state</th>
            <th>registrationDate</th>
            <th>add to subService</th>
            <th>confirmation</th>

        </tr>
        <c:forEach items="${listSpecialists}" var="each_one">
            <tr>
                <td>${each_one.name}</td>
                <td>${each_one.family}</td>
                <td>${each_one.email}</td>
                <td>${each_one.state}</td>
                <td>${each_one.registrationDate}</td>

                <td>
                    <a href="/manager/addToSubService?email=${each_one.email}">add to subServices</a>
                </td>
                <td><a href="/manager/confirmSpecialist?s=${each_one.email}&m=${managerEmail}">confirm user</a></td>
            </tr>
        </c:forEach>
    </table>
</form>
</div>
</body>
</html>
