<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<form:form action="/user/login" method="post" >
    <table border="0" cellpadding="5">
        <tr>
            <td>email: </td>
            <td><input name="email" type="email"/></td>
        </tr>
        <tr>
            <td>password: </td>
            <td><input name="password" type="password" /></td>
        </tr>
        <tr>
            <select name="userType">
                <option value="customer">customer</option>
                <option value="specialist">specialist </option>
                <option value="manager">manager </option>
            </select>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="login"></td>
        </tr>
    </table>
</form:form>

</body>
</html>
