<#import "../base.ftl" as base>

<@base.body "${title}">
    <form id="addForm" method="POST" action="/order">
        <div class="form-control">
            <label for="name" class="col-2">Enter order name: </label>
            <input type="text" class="col-2" name="name" id="name"/>
        </div>
        <div class="customer form-control">
            <label for="input_customer" class="col-2">Customer: </label>
            <input type="text" class="col-2" id="input_customer"
            >
            <input type="hidden" name="customer_id" id="customer_id"
            >
        </div>

        <div class="offer form-control">
            <label for="input_offer" class="col-2">Offer: </label>
            <input type="text" class="col-2" id="input_offer"
            >
            <input type="hidden" name="offer_id" id="offer_id"
            >
        </div>
        <div class="d-flex form-control">
            <input class="btn btn-success" type="submit" value="Create!"/>
            <a class="btn btn-light" href="/order">Back</a>
        </div>
    </form>
    <script>
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
        $("#addForm").submit(function (e) {

            e.preventDefault(); // avoid to execute the actual submit of the form.

            var form = $(this);
            var url = form.attr('action');

            $.ajax({
                type: "POST",
                url: url,
                data: form.serialize(), // serializes the form's elements.
                success: function (data) {
                    console.log(data); // show response from the php script.
                    var url = "/order/" + data["order_id"];
                    $(location).attr('href', url);
                }
            });


        });
    </script>
</@base.body>