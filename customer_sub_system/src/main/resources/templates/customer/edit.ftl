<#import "../base.ftl" as base>

<@base.body "${title}">
    <form id="editForm" method="post" action="/customer/${customer.customer_id}">
        <!--    <form id="editForm" method="post">-->
        <input type="hidden" name="_method" value="put">
        <input type="hidden" value="${customer.customer_id}" name="customer_id"/>
        <div class="form-control">
            <label for="email" class="col-2">Enter email: </label>
            <input type="email" value="${customer.email}" name="email" class="col-2" id="email"/>
        </div>
        <div class="form-control">
            <label for="pass" class="col-2">Enter password: </label>
            <input type="password" value="" name="pass" class="col-2" id="pass"/>
        </div>
        <br>
        <div class="form-control">
            <label for="firstName" class="col-2">Enter firstName: </label>
            <input type="text" value="${customer.firstName}" class="col-2" name="firstName" id="firstName"/>
        </div>
        <div class="form-control">
            <label for="lastName" class="col-2">Enter lastName: </label>
            <input type="text" value="${customer.lastName}" class="col-2" name="lastName" id="lastName"/>
        </div>
        <br>
        <div class="form-control">
            <label for="phone" class="col-2">Enter phone: </label>
            <input type="text" value="${customer.phone}" class="col-2" name="phone" id="phone"/>
        </div>
        <br>
        <div class="form-control">
            <label for="address" class="col-2">Enter address: </label>
            <select name="addressId" class="col-2" id="address">
                <option value="">-----</option>
                <#list allAddress as address>
                    <option <#if customer.address?? && customer.address.address_id == address.address_id>selected</#if>
                            value="${address.address_id}">${address.city}, ${address.country}
                        , ${address.state}</option>
                </#list>
            </select>
        </div>
        <table class="table">
            <tr class="table-success">
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

        </table>

        <div class="flex-box flex-row">
            <input class="btn btn-primary" type="submit" value="Update!"/>
            <a class="btn btn-light" href="/customer">Back</a>

        </div>

    </form>

    <script>
        $("#editForm").on("submit", function (e) {

            e.preventDefault(); // avoid to execute the actual submit of the form.

            var form = $(this);
            var url = form.attr('action');

            $.ajax({
                type: "PUT",
                url: url,
                data: form.serialize(), // serializes the form's elements.
                success: function (data) {

                    console.log(data); // show response from the php script.
                    var url = "/customer/" + data["customer_id"];
                    $(location).attr('href', url);
                }
            });


        });
    </script>
</@base.body>