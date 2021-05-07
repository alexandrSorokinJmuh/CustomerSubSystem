<#import "../base.ftl" as base>

<@base.body "${title}">
    <#list allCharacteristic as characteristic>
        <div>
            <a href="/characteristic/${characteristic.characteristic_id}">${characteristic.name}</a>
            <span>${characteristic.description}</span>
        </div>
    </#list>
    <br/>
    <hr/>

    <a href="/characteristic/new">Create new characteristic</a>

</@base.body>