<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>customer account</title>
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

</head>
<body>
<div class="container" id="mainDIV">
    <h2>Customer account</h2>
    <form:form action="/login" method="post">
        <table border="0" cellpadding="5" >
            <tr>
                <td>Name: </td>
                <td>${userDto.name} ${testSession}</td>
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
        <br/>
        <p class="text-danger">${errorMaxReachedOrderNumber}</p>
        <p class="text-danger">${errorUserNotConfirmed}</p>
        <p class="text-danger">${errorBalanceIsNotEnough}</p>
        <br/><br/>
        <h2> customer order list</h2>
        <div class="table-responsive">
            <table class="table">
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
                    <th>suggestions</th>
                    <th>action</th>

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
                        <td><a href="/customer/viewSuggestions?orderCode=${each_one.orderCode}&email=${userDto.email}" >view suggestions</a></td>
                        <td>
                            <a href="/customer/newComment?orderCode=${each_one.orderCode}&email=${userDto.email}">add comment</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <br/><br/>
        <h2> payment </h2>
        <div class="table-responsive">
            <table class="table">
                <tr>
                    <th>order number</th>
                    <th>subService</th>
                    <th>suggestedPrice</th>
                    <th>specialist</th>
                    <th>order state</th>
                    <th>payment</th>


                </tr>
                <c:forEach items="${paymentOrders}" var="each_one">
                    <tr>
                        <td>${each_one.orderCode}</td>
                        <td>${each_one.subService}</td>
                        <td>${each_one.suggestedPrice}</td>
                        <td>${each_one.specialist}</td>
                        <td>${each_one.orderState}</td>
                        <td>
                            <th><a href="/customer/paymentByBalance?orderCode=${each_one.orderCode}&email=${userDto.email}">pay by balance</a></th>
                            <th>
                                <a href="/customer/paymentByCard?orderCode=${each_one.orderCode}&email=${userDto.email}">pay by card </a>
                            </th>
                        </td>

                    </tr>
                </c:forEach>
            </table>
        </div>
    </form:form>
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
