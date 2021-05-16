<#import "base.ftl" as base>

<@base.body "${title}">
    <div class="container">
        <h1>Congrats!!! You did it!</h1>
        <form id="logoutForm" action="/api/auth/logout" method="POST">
            <button type="submit">Logout</button>
        </form>
    </div>
    <script>
        $("#logoutForm").on("submit", function(e) {

            e.preventDefault(); // avoid to execute the actual submit of the form.

            var form = $(this);
            var url = form.attr('action');

            $.ajax({
                type: "POST",
                url: url,
                data: form.serialize(),
                success: function(data)
                {
                    console.log(data);
                    eraseCookie('Authorization', '/')
                    // delete_cookie('Authorization', '/')

                    // var url = "/customer/" + data["customer_id"];
                    // $(location).attr('href',url);
                },
                error: function (data) {
                    console.log(data);
                }
            });


        });
    </script>
</@base.body>