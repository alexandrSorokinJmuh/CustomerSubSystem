<#import "../base.ftl" as base>

<@base.body "${title}">
    <form id="editForm" method="post" action="/category/${category.category_id}">
        <!--    <form id="editForm" method="post">-->
        <input type="hidden" name="_method" value="put">
        <input type="hidden" value="${category.category_id}" name="category_id"/>
        <div class="form-control">
            <label for="name" class="col-2">Enter category name: </label>
            <input type="text" class="col-2" value="${category.name}" name="name" id="name"/>
        </div>
        <div class="d-flex flex-row">
            <input class="btn btn-primary"  type="submit" value="Update!"/>
            <a class="btn btn-light" href="/category">Back</a>
        </div>
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
                    var url = "/category/" + data["category_id"];
                    $(location).attr('href', url);
                }
            });


        });
    </script>
</@base.body>