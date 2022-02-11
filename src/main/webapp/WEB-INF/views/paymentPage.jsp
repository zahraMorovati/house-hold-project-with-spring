<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>payment</title>

    <link rel="stylesheet" href="/static/css/payment.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="/static/js/countDownTimer.js"></script>
</head>
<body>

<div class="container p-0">
    <div class="card px-4">
        <p class="h8 py-3">Payment Details</p>

        <form:form action="/customer/savePaymentByCard?orderCode=${order.orderCode}&email=${userDto.email}" method="post" >
            <div>Registration closes in <span id="time">01:00</span> minutes!</div>
            <script>
                var timer = setTimeout(function() {
                    window.location='timeout?email=${userDto.email}'
                }, 1000*60);
            </script>
            <br/>
            <div class="row gx-3">
                <div class="col-12">
                    <div class="d-flex flex-column">
                        <p class="text mb-1">Person Name</p> <input class="form-control mb-3" type="text" placeholder="Name" value="${userDto.name} ${userDto.family}">
                    </div>
                </div>
                <div class="col-12">
                    <div class="d-flex flex-column">
                        <p class="text mb-1">Card Number</p> <input class="form-control mb-3" type="text" placeholder="1234 5678 435678">
                    </div>
                </div>
                <div class="col-6">
                    <div class="d-flex flex-column">
                        <p class="text mb-1">Expiry</p> <input class="form-control mb-3" type="date" placeholder="MM/YYYY">
                    </div>
                </div>
                <div class="col-6">
                    <div class="d-flex flex-column">
                        <p class="text mb-1">CVV/CVC</p> <input class="form-control mb-3 pt-2 " type="password" placeholder="***">
                    </div>
                </div>
                <div class="col-12">
                    <input class="btn btn-primary mb-3" type="submit" value="Pay ${order.suggestedPrice}"> <span class="fas fa-arrow-right"></span> </input>
                </div>
            </div>
        </form:form>
    </div>
</div>

</body>
</html>
