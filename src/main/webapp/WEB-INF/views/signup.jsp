
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sign up</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>

<form:form cssClass="p-1 my-5 mx-5"  modelAttribute="userDto"
           action="/user/signup" method="post" enctype="multipart/form-data">
    <table class="table table-bordered table-striped text-dark">
        <tr>
            <td>name:</td>
            <td><form:input path="name" /></td>
        </tr>

        <tr>
            <td>family:</td>
            <td><form:input path="family" /></td>
        </tr>

        <tr>
            <td>email:</td>
            <td><form:input path="email" /></td>
        </tr>

        <tr>
            <td>password:</td>
            <td><form:input path="password" /></td>
        </tr>

        <tr>
            <td>
                user type:
            </td>
            <td>
                <form:select path="userType" id="userType" onchange="updateDropDownList()">
                    <form:option value="CUSTOMER" label="customer"/>
                    <form:option value="SPECIALIST" label="specialist"/>
                </form:select>
            </td>

        </tr>

        <tr id="imageSection">
            <td>
                image:
            </td>
            <td>
                <input type="file" id="image" name="image">
            </td>
        </tr>

        <tr>
            <td>
                <input type="submit" value="sign up">
            </td>
        </tr>

    </table>
</form:form>

<script>
    const imageFile = document.getElementById("image");

    imageFile.onchange = function () {
        const maxAllowedSize = 100 * 1024;
        if (this.files[0].size > maxAllowedSize) {
            alert("Image File is too big!");
            this.value = "";
        }
    }

    function updateDropDownList() {
        const select = document.getElementById('userType');
        const option = select.options[select.selectedIndex];

        if(option.value === "CUSTOMER"){
            document.getElementById('imageSection').style.visibility="hidden";
        }else {
            document.getElementById('imageSection').style.visibility = 'visible';
        }
    }



</script>
</body>
</html>
