<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Show</title>
</head>
<body>
<p>
    <span>Id:</span> ${customer.getCustomer_id()}
</p>
<p>
    <span>FirstName:</span> ${customer.getFirstName()}
</p>
<p>
    <span>LastName:</span> ${customer.getLastName()}
</p>
<p>
    <span>Email:</span> ${customer.getEmail()}
</p>

<p>
    <span>Address:</span>
    <#if customer.address??>
        ${customer.address.city}, ${customer.address.country}, ${customer.address.state}

    <#else>
        Customer have not address
    </#if>
</p>
<p>
    <span>Paid types:</span>

    <#list customer.paidTypes as paidType>
        <span>${paidType.name}<#sep>,</#sep></span>
    <#else>
        <span>Customer have not paid types</span>
    </#list>
</p>
<br>
<hr>
<div>
    <a href="/customer">Back</a>
</div>
<div>
    <a href="/customer/${customer.customer_id}/edit">Edit</a>
</div>
<div>
    <form method="post" action="/customer/${customer.customer_id}">
        <input type="hidden" name="_method" value="delete">
        <input type="submit" value="Delete"/>
    </form>
</div>
</body>
</html>