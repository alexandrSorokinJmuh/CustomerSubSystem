<#import "../base.ftl" as base>

<@base.body "${title}">
    <div class="text-muted">
        <p>
            <span class="text-body">Id:</span> ${offer.offer_id}
        </p>
        <p>
            <span class="text-body">Offer name:</span> ${offer.name}
        </p>
        <p>
            <span class="text-body">Price:</span> ${offer.price}
        </p>
        <#if offer.category??>
            <p>
                <span class="text-body">Category:</span> ${offer.category.name}
            </p>
        </#if>
        <#if offer.paidTypes??>
            <p>
                <span class="text-body">Paid Type: </span> <#list paidTypes as paidType>

                    <span>${paidType.name}</span><#sep>, </#sep></#list>
            </p>
        </#if>
        <#if offer.offerCharacteristics??>
            <div class="text-body">Characteristic:</div>
            <#list offer.offerCharacteristics as characteristic>
                <div>${characteristic.characteristic.name}: ${characteristic.characteristicValue.value}</div>
            </#list>
        </#if>
        <div>

            <a class="btn btn-info" href="/offer/${offer.offer_id}/edit">Edit</a>
            <a class="btn btn-light" href="/offer">Back</a>
        </div>
        <div>
            <form id="deleteForm" method="post" action="/offer/${offer.offer_id}">
                <input type="hidden" name="_method" value="delete">
                <input class="btn- btn-danger mt-3" type="submit" value="Delete"/>
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
                            var url = "/offer";
                            $(location).attr('href', url);
                        }
                    });


                });
            </script>
        </div>
    </div>
</@base.body>