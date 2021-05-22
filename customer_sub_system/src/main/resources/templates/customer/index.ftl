<#import "../base.ftl" as base>

<@base.body "${title}">
    <#list allCustomers as customer>
        <div>
            <a class="text-dark" href="/customer/${customer.customer_id}">${customer.firstName} ${customer.lastName}
                --- ${customer.email}</a>

        </div>
    </#list>
    <br/>
    <hr/>

    <a class="btn btn-info" href="/customer/new">Create new customer</a>

</@base.body>