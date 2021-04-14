<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Index</title>
</head>
<body>
<#list allCustomers as customer>
    <div >
        <a href="/customer/${customer.customer_id}">${customer.firstName} ${customer.lastName} --- ${customer.email}</a>

    </div>
</#list>
<br/>
<hr/>

<a href="/customer/new">Create new customer</a>

</body>
</html>