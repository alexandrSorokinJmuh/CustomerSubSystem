<#import "../base.ftl" as base>

<@base.body "${title}">
    <p>
        <span>Id:</span> ${order.order_id}
    </p>
    <p>
        <span>Order name:</span> ${order.name}
    </p>
    <p>
        <span>Status:</span> ${order.status}
    </p>
    <#if order.offer_id??>
        <p>
            <span>Offer:</span> ${order.offer_id}
        </p>
    </#if>
    <p>
        <span>Is paid:</span>
        <#if order.paid?? && order.paid>
            yes
            <#else>
            no
        </#if>
    </p>
    <div>
        <a href="/order">Back</a>
    </div>
    <div>
        <a href="/order/${order.order_id}/edit">Edit</a>
    </div>
    <div>
        <form id="deleteForm" method="post" action="/order/${order.order_id}">
            <input type="hidden" name="_method" value="delete">
            <input type="submit" value="Delete"/>
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
</@base.body>