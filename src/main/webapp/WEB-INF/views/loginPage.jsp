<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>

    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />

    <!-- Bootstrap CSS -->
    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
            integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
            crossorigin="anonymous"
    />
    <link rel="stylesheet" href="/static/css/style.css" />
</head>
<body>

<div class="container">

    <div class="center">
        <div style="background-color: white; height: 450px; width: 500px; border-radius: 5%;" >

            <div class=" p-5 justify-content-center m-4">
                <h4 class="text-center" style="margin-bottom: 30px; font-size: 30px" >login</h4>

                <form:form action="/user/login" method="post" >
                    <table border="0" cellpadding="5">
                        <tr>
                            <td>email: </td>
                            <td><input name="email" type="email" class="form-control space"/></td>
                        </tr>
                        <tr>
                            <td>password: </td>
                            <td><input name="password" type="password" class="form-control space"/></td>
                        </tr>
                        <tr>
                            <td>user type:</td>
                            <td><select name="userType" class="space btn btn-light dropdown-toggle">
                                <option value="customer" class="dropdown-item">customer</option>
                                <option value="specialist" class="dropdown-item">specialist </option>
                                <option value="manager" class="dropdown-item">manager </option>
                            </select></td>
                        </tr>
                        <tr>
                            <td colspan="2"><input type="submit" value="login" class="btn btn-style button_style"></td>
                        </tr>
                    </table>
                </form:form>

            </div>
        </div>
    </div>

</div>
<script
        src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"
></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF"
        crossorigin="anonymous"
></script>
</body>
</html>
