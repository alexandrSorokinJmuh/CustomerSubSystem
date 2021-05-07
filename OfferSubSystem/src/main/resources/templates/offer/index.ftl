<#import "../base.ftl" as base>

<@base.body "${title}">
    <#list allOffers as offer>
        <div>
            <a href="/offer/${offer.offer_id}">${offer.name}</a>
            <span>${offer.price}</span>
        </div>
    </#list>
    <br/>
    <hr/>

    <a href="/offer/new">Create new offer</a>

</@base.body>