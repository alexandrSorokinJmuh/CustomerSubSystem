<#import "../base.ftl" as base>

<@base.body "${title}">
    <form id="addForm" method="POST" action="/customer">
        <label for="email">Enter email: </label>
        <input type="email" name="email" id="email"/>

        <label for="pass">Enter password: </label>
        <input type="password" name="pass" id="pass"/>

        <br>
        <label for="firstName">Enter firstName: </label>
        <input type="text" name="firstName" id="firstName"/>
        <label for="lastName">Enter lastName: </label>
        <input type="text" name="lastName" id="lastName"/>
        <br>

        <label for="phone">Enter phone: </label>
        <input type="text" name="phone" id="phone"/>

        <input type="submit" value="Create!"/>
    </form>
    <script>
        $("#addForm").submit(function(e) {

            e.preventDefault(); // avoid to execute the actual submit of the form.

            var form = $(this);
            var url = form.attr('action');

            $.ajax({
                type: "POST",
                url: url,
                data: form.serialize(), // serializes the form's elements.
                success: function(data)
                {
                    console.log(data); // show response from the php script.
                    var url = "/customer/" + data["customer_id"];
                    $(location).attr('href',url);
                }
            });


        });
    </script>
</@base.body>