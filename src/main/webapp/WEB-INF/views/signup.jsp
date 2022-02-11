<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sign up</title>

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
        <div style="background-color: white; height: 600px; width: 500px; border-radius: 5%;" id="mainDiv">
            <div class=" p-5 justify-content-center m-4">
                <h4 class="text-center" style="margin-bottom: 30px; font-size: 30px" >Sign up</h4>
                <p class="text-danger">${error}</p>
                <p class="text-danger">${emailNotValid}</p>
                <p class="text-danger" id="signupErrors">${signupErrors}</p>

                <form:form modelAttribute="userDto" action="/doSignup" method="post" enctype="multipart/form-data">
                    <table border="0" cellpadding="5">
                        <tr>
                            <td>name:</td>
                            <td>
                                <form:input path="name" cssClass=" form-control space" id="name" title="name:"/>
                            </td>
                        </tr>

                        <tr>
                            <td>family:</td>
                            <td><form:input path="family" cssClass=" form-control space"/></td>
                        </tr>

                        <tr>
                            <td>email:</td>
                            <td><form:input path="email" cssClass="form-control space" /></td>
                        </tr>

                        <tr>
                            <td>password:</td>
                            <td><form:input path="password" cssClass=" form-control space"/></td>
                        </tr>

                        <tr>
                            <td>
                                user type:
                            </td>
                            <td>
                                <form:select path="userType" id="userType" onchange="updateDropDownList()" cssClass="space btn btn-light dropdown-toggle">
                                    <form:option value="SPECIALIST" label="specialist" cssClass="dropdown-item"/>
                                    <form:option value="CUSTOMER" label="customer" cssClass="dropdown-item"/>
                                </form:select>
                            </td>

                        </tr>

                        <tr id="imageSection">
                            <td>
                                image:
                            </td>
                            <td>
                                <div class="custom-file space">
                                    <input type="file" class="custom-file-input" id="image" lang="es" name="image">
                                    <label class="custom-file-label" for="image">select image</label>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="2"><input type="submit" value="sign up" class="btn btn-style button_style" id="button"><td/>
                        </tr>

                    </table>
                </form:form>

            </div>
        </div>
    </div>

</div>
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
            document.getElementById("signupErrors").innerText="";
            document.getElementById('mainDiv').style.height="550px";
            document.getElementById('button').style.marginTop="-50px";
        }else {
            document.getElementById('imageSection').style.visibility = 'visible';
            document.getElementById('mainDiv').style.height="650px";
            document.getElementById('button').style.marginTop="50px";
            document.getElementById("signupErrors").innerText="";
        }
    }

    window.onload = function () {
        const errors = document.getElementById("signupErrors").innerText;
        if(errors.length>0){
            document.getElementById('mainDiv').style.height="720px";
        }
    }

</script>
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
