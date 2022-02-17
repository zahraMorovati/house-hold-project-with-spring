
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
<a href="/manager/newService">add service</a><br/><br/>
<a href="/manager/newSubService">add subservice</a><br/><br/>
<a href="/manager/save-specialist-to-subService">add specialist to subService</a><br/><br/>
<a href="/manager/listSpecialist?&name=&family=&email=">confirm specialists</a><br/><br/>
<a href="/manager/listCustomer?name=&family=&email=">confirm customers</a><br/><br/>
<a href="/manager/advanced-filter-specialists">advanced filter specialists</a><br/><br/>
<a href="/manager/advanced-filter-customers">advanced filter customers</a><br/><br/>
<a href="/manager/advanced-filter-orders">advanced filter orders</a><br/><br/>

</body>
</html>
