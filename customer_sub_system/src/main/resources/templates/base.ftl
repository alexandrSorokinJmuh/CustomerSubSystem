<#include "security.ftl">
<#macro body title>
    <!DOCTYPE html>

    <html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <!-- Latest compiled and minified CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
                crossorigin="anonymous"></script>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
        <link href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet">

        <title>${title}</title>
    </head>
    <body>

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">CustomerSubSystem</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/customer">Customers</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/offer">Offers</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/category">Categories</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/characteristic">Characteristics</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/order">Orders</a>
                    </li>

                </ul>
                <#if known>
                    <form id="logoutForm" action="/api/auth/logout" method="POST">
                        <button class="btn btn-light" type="submit">Logout</button>
                    </form>
                <#else>
                    <a href="/auth/login" class="btn btn-primary">Sign in</a>
                </#if>

            </div>
        </div>
    </nav>
    <div class="container mt-3">
        <#nested>
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
                    eraseCookie('JSESSIONID', '/')
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

    <script>
        console.log(document.cookie)

        function set_cookie(name, value, exp_y, exp_m, exp_d, path, domain, secure) {
            var cookie_string = name + "=" + escape(value);

            if (exp_y) {
                var expires = new Date(exp_y, exp_m, exp_d);
                cookie_string += "; expires=" + expires.toGMTString();
            }

            if (path)
                cookie_string += "; path=" + escape(path);

            if (domain)
                cookie_string += "; domain=" + escape(domain);

            if (secure)
                cookie_string += "; secure";

            document.cookie = cookie_string;
        }

        function eraseCookie(name, path, domain) {
            console.log(get_cookie(name))
            document.cookie = name + "=" +
                ((path) ? ";path=" + path : "") +
                ((domain) ? ";domain=" + domain : "") +
                ";Max-Age=-99999999;";

        }

        function delete_cookie(cookie_name, path, domain) {
            console.log(get_cookie('Authorization'))
            var cookie_date = new Date();  // Текущая дата и время
            cookie_date.setTime(cookie_date.getTime() - 1);
            document.cookie = name + "=" +
                ((path) ? ";path=" + path : "") +
                ((domain) ? ";domain=" + domain : "") +
                ";expires=Thu, 01 Jan 1970 00:00:01 GMT";

        }

        function get_cookie(cookie_name) {
            var results = document.cookie.match('(^|;) ?' + cookie_name + '=([^;]*)(;|$)');

            if (results)
                return (unescape(results[2]));
            else
                return null;
        }
    </script>
    </body>
    </html>
</#macro>