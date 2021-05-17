<#import "../base.ftl" as base>


<@base.body "${title}">
    <form id="editForm" method="post" action="/order/${order.order_id}">
        <!--    <form id="editForm" method="post">-->
        <input type="hidden" name="_method" value="put">
        <input type="hidden" value="${order.order_id}" name="order_id"/>
        <div>
            <label for="name">Enter order name: </label>
            <input type="text" value="${order.name}" name="name" id="name"/>
        </div>
        <div class="customer">
            <label for="input_customer">Customer: </label>
            <input type="text" id="input_customer"
                   <#if customer??>value="${customer.email}"</#if>
            >
            <input type="hidden" name="customer_id" id="customer_id"
                   <#if customer??>value="${customer.customer_id}"</#if>
            >
        </div>

        <div class="offer">
            <label for="input_offer">Offer: </label>
            <input type="text" id="input_offer"
                   <#if offer??>value="${offer.name}"</#if>
            >
            <input type="hidden" name="offer_id" id="offer_id"
                   <#if offer??>value="${offer.offer_id}"</#if>
            >
        </div>
        <div class="deliveryTime">
            <label for="deliveryTime">Delivery Time: </label>
            <input type="date" id="deliveryTime" name="deliveryTime"
                   <#if order.deliveryTime??> value="${order.deliveryTime?string["yyyy-MM-dd"]}" </#if>
            >
        </div>

        <table class="table">
            <tr>
                <th>Selected</th>
                <th>Name of status</th>
            </tr>

            <#list allStatus as status>
                <tr>
                    <td><label>
                            <input type="radio" name="status" value="${status.name() }"
                                   <#if order.status?? && order.status.name() == status.name()>checked</#if>>
                        </label></td>
                    <td>${status.name()}</td>

                </tr>
            </#list>
        </table>
        <div>
            <label for="paid">Is paid: </label>
            <input type="checkbox" name="paid" id="paid"
                    <#if order.paid?? && order.paid> checked </#if>>
        </div>

        <input type="submit" value="Update!"/>
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
                    var url = "/order/" + data["order_id"];
                    $(location).attr('href', url);
                }
            });
        });

        $("#input_offer").autocomplete({
                source: "getOfferByTerm",
                maxHeight: 400,
                maxWidth: $(this).width,
                minLength: 0,
                select: function (event, ui) {
                    $("#input_offer").val(ui.item.label)

                    $("#offer_id").val(ui.item.value);

                    return false;
                }
            })
        $("#input_customer").autocomplete({
            source: "getCustomerByTerm",
            maxHeight: 400,
            maxWidth: $(this).width,
            minLength: 0,
            select: function (event, ui) {
                $("#input_customer").val(ui.item.label)

                $("#customer_id").val(ui.item.value);

                return false;
            }
        })
    </script>
</@base.body>