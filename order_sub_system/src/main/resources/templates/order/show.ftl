<#import "../base.ftl" as base>

<@base.body "${title}">
    <div>
        <p>
            <span class="text-dark">Id:</span> ${order.order_id}
        </p>
        <p>
            <span class="text-dark">Order name:</span> ${order.name}
        </p>
        <p>
            <span class="text-dark">Status:</span> ${order.status}
        </p>
        <#if offer??>
            <p>
                <span class="text-dark">Offer:</span> ${offer.name}
            </p>
        </#if>

        <#if customer??>
            <p>
                <span class="text-dark">Customer:</span> ${customer.email}
            </p>
        </#if>

        <#if order.deliveryTime??>
            <p>
                <span class="text-dark">Delivery time:</span> ${order.deliveryTime?string["dd.MM.yyyy"]}
            </p>
        </#if>

        <p>
            <span class="text-dark">Is paid:</span>
            <#if order.paid?? && order.paid>
                yes
            <#else>
                no
            </#if>
        </p>
        <div class="d-flex flex-row">
            <a class="btn btn-info" href="/order/${order.order_id}/edit">Edit</a>
            <a class="btn btn-light" href="/order">Back</a>
        </div>
        <div>
            <form id="deleteForm" method="post" action="/order/${order.order_id}">
                <input type="hidden" name="_method" value="delete">
                <input class="btn btn-danger mt-3" type="submit" value="Delete"/>
            </form>
            <script>
                $("#deleteForm").submit(function (e) {

                    e.preventDefault(); // avoid to execute the actual submit of the form.

                    var form = $(this);
                    var url = form.attr('action');

                    $.ajax({
                        type: "DELETE",
                        url: url,
                        data: form.serialize(), // serializes the form's elements.
                        success: function (data) {
                            console.log(data); // show response from the php script.
                            var url = "/order";
                            $(location).attr('href', url);
                        }
                    });


                });
            </script>
        </div>
    </div>
</@base.body>