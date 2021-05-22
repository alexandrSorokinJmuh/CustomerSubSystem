<#import "../base.ftl" as base>

<@base.body "${title}">
    <form id="addForm" method="POST" action="/customer">
        <div class="form-control">
            <label for="email">Enter email: </label>
            <input type="email" name="email" id="email"/>
        </div>
        <div class="form-control">
            <label for="password">Enter password: </label>
            <input type="password" name="password" id="password"/>
        </div>
        <br>
        <div class="form-control">
            <div>
                <label for="firstName">Enter firstName: </label>
                <input type="text" name="firstName" id="firstName"/>
            </div>
            <div class="mt-3">
                <label for="lastName">Enter lastName: </label>
                <input type="text" name="lastName" id="lastName"/>
            </div>
        </div>
        <br>
        <div class="form-control">
            <label for="phone">Enter phone: </label>
            <input type="text" name="phone" id="phone"/>
        </div>

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