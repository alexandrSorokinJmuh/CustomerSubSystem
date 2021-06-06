<#import "../base.ftl" as base>

<@base.body "${title}">
    <form id="addForm" method="POST" action="/offer">
        <div class="form-control">
            <label for="name" class="col-2">Enter name: </label>
            <input type="text" class="col-2" name="name" id="name"/>
        </div>
        <div class="form-control">
            <label for="price" class="col-2">Enter price: </label>
            <input type="number" class="col-2" placeholder="1.0" step="0.01" min="0" name="price" id="price"/>
        </div>
        <div class="d-flex form-control">
            <input class="btn btn-success" type="submit" value="Create!"/>
            <a class="btn btn-light" href="/offer">Back</a>
        </div>
    </form>
    <script>
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
                    var url = "/offer/" + data["offer_id"];
                    $(location).attr('href', url);
                }
            });


        });
    </script>
</@base.body>