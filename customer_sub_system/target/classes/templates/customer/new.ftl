<#import "../base.ftl" as base>

<@base.body "${title}">
    <form id="addForm" method="POST" action="/customer/new">
        <div class="form-control">
            <label for="email" class="col-2">Enter email: </label>
            <input type="email" class="col-2" name="email" id="email"/>
        </div>
        <div class="form-control">
            <label for="password" class="col-2">Enter password: </label>
            <input type="password" class="col-2" name="password" id="password"/>
        </div>
        <br>
        <div class="form-control">
            <div>
                <label for="firstName" class="col-2">Enter firstName: </label>
                <input type="text" class="col-2" name="firstName" id="firstName"/>
            </div>
            <div class="mt-3">
                <label for="lastName" class="col-2">Enter lastName: </label>
                <input type="text" class="col-2" name="lastName" id="lastName"/>
            </div>
        </div>
        <br>
        <div class="form-control">
            <label for="phone" class="col-2">Enter phone: </label>
            <input type="text" class="col-2" name="phone" id="phone"/>
        </div>

        <br>

        <div class="d-flex flex-row">
            <input class="btn btn-success" type="submit" value="Create!"/>
            <a class="btn btn-light" href="/customer">Back</a>

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
                    var url = "/customer/" + data["customer_id"];
                    $(location).attr('href', url);
                }
            });


        });
    </script>
</@base.body>