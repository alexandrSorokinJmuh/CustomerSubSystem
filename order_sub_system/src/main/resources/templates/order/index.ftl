<#import "../base.ftl" as base>

<@base.body "${title}">
    <#list allOrders as order>
        <div>
            <a class="text-dark" href="/order/${order.order_id}">${order.name}</a>
            <span>${order.status}</span>
        </div>
    </#list>
    <br/>
    <hr/>

    <a class="btn btn-primary" href="/order/new">Create new order</a>

</@base.body>