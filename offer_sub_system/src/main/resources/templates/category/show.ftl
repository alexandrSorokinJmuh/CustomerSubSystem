<#import "../base.ftl" as base>

<@base.body "${title}">
    <div class="text-muted">
        <p>
            <span class="text-body">Id:</span> ${category.category_id}
        </p>
        <p>
            <span class="text-body">CategoryName:</span> ${category.name}
        </p>
        <div class="flex-box flex-row">

            <a class="btn btn-info" href="/category/${category.category_id}/edit">Edit</a>
            <a class="btn btn-light" href="/category">Back</a>
        </div>
        <div>
            <form id="deleteForm" method="post" action="/category/${category.category_id}">
                <input type="hidden" name="_method" value="delete">
                <input class="btn btn-danger mt-3" type="submit" value="Delete"/>
            </form>
            <script>
                $("#deleteForm").submit(function (e) {

                    e.preventDefault(); // avoid to execute the actual submit of the form.

                    var form = $(this);
                    var url = form.attr('action');

                    $.ajax({
                        type: "DELETE",
                        url: url,
                        data: form.serialize(), // serializes the form's elements.
                        success: function (data) {
                            console.log(data); // show response from the php script.
                            var url = "/category";
                            $(location).attr('href', url);
                        }
                    });


                });
            </script>
        </div>
    </div>
</@base.body>