<#import "base.ftl" as base>

<@base.body "${title}">
    <div class="container">
        <form id="loginForm" class="form-signin" method="post" action="/api/auth/login">
            <h2 class="form-signin-heading">Login</h2>
            <p>
                <label for="email">Email</label>
                <input type="text" id="email" name="email" class="form-control" placeholder="email" required>
            </p>
            <p>
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
            </p>
            <div class="d-flex flex-row">
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                <a class="btn btn-lg btn-success btn-block" href="/customer/new">Sign up</a>
            </div>
        </form>
    </div>

    <script>
        $("#loginForm").on("submit", function(e) {



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

                    // set_cookie('Authorization', data['token'], null, null, null, '/', 'localhost')
                    // set_cookie('Authorization', data['token'], null, null, null, '/', 'offersystem.jelastic.regruhosting.ru')
                    // set_cookie('Authorization', data['token'], null, null, null, '/', 'customersystem.jelastic.regruhosting.ru')


                    set_cookie('Authorization', data['token'], null, null, null, '/')
                    window.location.replace('/customer')
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