<#import "../base.ftl" as base>

<@base.body "${title}">
    <div class="text-muted">
        <p>
            <span class="text-body">Id:</span> ${customer.getCustomer_id()}
        </p>
        <p>
            <span class="text-body">FirstName:</span> ${customer.getFirstName()}
        </p>
        <p>
            <span class="text-body">LastName:</span> ${customer.getLastName()}
        </p>
        <p>
            <span class="text-body">Email:</span> ${customer.getEmail()}
        </p>

        <p>
            <span class="text-body">Address:</span>
            <#if customer.address??>
                ${customer.address.city}, ${customer.address.country}, ${customer.address.state}

            <#else>
                Customer have not address
            </#if>
        </p>
        <p>
            <span class="text-body">Paid types:</span>

            <#list customer.paidTypes as paidType>
                <span>${paidType.name}<#sep>,</#sep></span>
            <#else>
                <span>Customer have not paid types</span>
            </#list>
        </p>
        <br>
        <hr>
        <div class="flex-box flex-row">
            <a class="btn btn-info" href="/customer/${customer.customer_id}/edit">Edit</a>
            <a class="btn btn-light" href="/customer">Back</a>
        </div>
        <div>
            <form id="deleteForm" method="post" action="/customer/${customer.customer_id}">
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
                            var url = "/customer";
                            $(location).attr('href', url);
                        }
                    });


                });
            </script>
        </div>
    </div>
</@base.body>