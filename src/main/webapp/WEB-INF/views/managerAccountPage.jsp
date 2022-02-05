
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>manager account</title>
</head>
<body>
<h1>manager account</h1>
${userDto.name}<br/><br/>
${userDto.family}<br/><br/>
${userDto.email}<br/><br/>
<a href="/manager/newService?email=${userDto.email}">add service</a><br/><br/>
<a href="/manager/newSubService?email=${userDto.email}">add subservice</a><br/><br/>
<a href="/manager/listSpecialist?mEmail=${userDto.email}&name=&family=&email=">list specialists</a><br/><br/>
<a href="/manager/listCustomer?mEmail=${userDto.email}&name=&family=&email=">list customers</a>

</body>
</html>
