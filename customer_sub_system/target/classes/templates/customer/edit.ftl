<#import "../base.ftl" as base>

<@base.body "${title}">
    <form id="editForm" method="post" action="/customer/${customer.customer_id}">
<!--    <form id="editForm" method="post">-->
        <input type="hidden" name="_method" value="put">
        <input type="hidden" value="${customer.customer_id}" name="customer_id"/>
        <div>
            <label for="email">Enter email: </label>
            <input type="email" value="${customer.email}" name="email" id="email"/>
        </div>
        <div>
            <label for="pass">Enter password: </label>
            <input type="password" value="${customer.getPassword}" name="pass" id="pass"/>
        </div>
        <br>
        <div>
            <label for="firstName">Enter firstName: </label>
            <input type="text" value="${customer.firstName}" name="firstName" id="firstName"/>
        </div>
        <div>
            <label for="lastName">Enter lastName: </label>
            <input type="text" value="${customer.lastName}" name="lastName" id="lastName"/>
        </div>
        <br>

        <label for="phone">Enter phone: </label>
        <input type="text" value="${customer.phone}" name="phone" id="phone"/>
        <br>
        <div>
            <label for="address">Enter address: </label>
            <div>
                <select name="addressId" id="address">
                    <option value="">-----</option>
                    <#list allAddress as address>
                        <option <#if customer.address?? && customer.address.address_id == address.address_id>selected</#if>
                                value="${address.address_id}">${address.city}, ${address.country}
                            , ${address.state}</option>
                    </#list>

                </select>
            </div>
        </div>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name of paid type</th>
                <th>Is using</th>
            </tr>
            <#list allPaidType as paidType>
                <tr>

                    <td>${paidType.getPaid_type_id()}</td>
                    <td>${paidType.getName()}</td>
                    <td><label>
                            <input type="checkbox" name="paidTypesId" value="${paidType.paid_type_id }"
                                   <#if customer.paidTypes?seq_contains(paidType)>checked</#if>>
                        </label></td>

                </tr>
            </#list>



        <input type="submit" value="Update!"/>


    </form>

    <script>
        $("#editForm").on("submit", function(e) {

            e.preventDefault(); // avoid to execute the actual submit of the form.

            var form = $(this);
            var url = form.attr('action');

            $.ajax({
                type: "PUT",
                url: url,
                data: form.serialize(), // serializes the form's elements.
                success: function(data)
                {

                    console.log(data); // show response from the php script.
                    var url = "/customer/" + data["customer_id"];
                    $(location).attr('href',url);
                }
            });


        });
    </script>
</@base.body>