<#import "../base.ftl" as base>

<@base.body "${title}">
    <p>
        <span>Id:</span> ${offer.offer_id}
    </p>
    <p>
        <span>Offer name:</span> ${offer.name}
    </p>
    <p>
        <span>Price:</span> ${offer.price}
    </p>
    <#if offer.category??>
        <p>
            <span>Category:</span> ${offer.category.name}
        </p>
    </#if>
    <#list allPaidTypes?keys as paidType>
        <div>
            ${paidType} ${allPaidTypes[paidType]}
        </div>
    </#list>
    <#if offer.paidTypes??>

        <p>
            <span>Paid Type: </span> <#list offer.paidTypes as paidType><span>${allPaidTypes[paidType.paidTypeId?string]}</span><#sep>, </#sep></#list>
        </p>
    </#if>
    <div>
        <a href="/offer">Back</a>
    </div>
    <div>
        <a href="/offer/${offer.offer_id}/edit">Edit</a>
    </div>
    <div>
        <form id="deleteForm" method="post" action="/offer/${offer.offer_id}">
            <input type="hidden" name="_method" value="delete">
            <input type="submit" value="Delete"/>
        </form>
        <script>
            $("#deleteForm").submit(function(e) {

                e.preventDefault(); // avoid to execute the actual submit of the form.

                var form = $(this);
                var url = form.attr('action');

                $.ajax({
                    type: "DELETE",
                    url: url,
                    data: form.serialize(), // serializes the form's elements.
                    success: function(data)
                    {
                        console.log(data); // show response from the php script.
                        var url = "/offer";
                        $(location).attr('href',url);
                    }
                });


            });
        </script>

    </div>
</@base.body>