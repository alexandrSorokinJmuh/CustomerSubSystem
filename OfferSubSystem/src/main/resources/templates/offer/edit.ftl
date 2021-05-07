<#import "../base.ftl" as base>

<#function string_to_int s >
    <#local a = s?replace(",", ".") >
    <#return a/>
</#function>

<@base.body "${title}">
    <form id="editForm" method="post" action="/offer/${offer.offer_id}">
        <!--    <form id="editForm" method="post">-->
        <input type="hidden" name="_method" value="put">
        <input type="hidden" value="${offer.offer_id}" name="offer_id"/>
        <div>
            <label for="name">Enter offer name: </label>
            <input type="text" value="${offer.name}" name="name" id="name"/>
        </div>
        <div>
            <label for="price">Enter price: </label>
            <input type="number" placeholder="1.0" step="0.01" min="0" value="${string_to_int(offer.price?string["0.00"])}" name="price" id="price"/>
        </div>

        <table class="table">
            <tr>
                <th>Selected</th>
                <th>ID</th>
                <th>Name of category</th>
            </tr>

            <#list allPaidTypes?keys as paidType>
                <tr>
                    <td><label>
                            <input type="checkbox" name="paidTypes" value="${paidType}"
                                   <#if offerPaidTypes?? && offerPaidTypes?seq_contains(paidType?number)>checked</#if>>
                        </label></td>
                    <td>${paidType}</td>
                    <td>${allPaidTypes[paidType]}</td>

                </tr>
            </#list>
        </table>

        <table class="table">
            <tr>
                <th>Selected</th>
                <th>ID</th>
                <th>Name of category</th>
            </tr>

            <#list allCategories as category>
                <tr>
                    <td><label>
                            <input type="radio" name="category" value="${category.category_id }"
                                   <#if offer.category?? && offer.category.category_id == category.category_id>checked</#if>>
                        </label></td>
                    <td>${category.category_id}</td>
                    <td>${category.name}</td>

                </tr>
            </#list>
        </table>
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
                    var url = "/offer/" + data["offer_id"];
                    $(location).attr('href', url);
                }
            });


        });
    </script>
</@base.body>