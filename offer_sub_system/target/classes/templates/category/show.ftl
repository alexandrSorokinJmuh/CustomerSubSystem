<#import "../base.ftl" as base>

<@base.body "${title}">
    <p>
        <span>Id:</span> ${category.category_id}
    </p>
    <p>
        <span>CategoryName:</span> ${category.name}
    </p>

    <div>
        <a href="/category">Back</a>
    </div>
    <div>
        <a href="/category/${category.category_id}/edit">Edit</a>
    </div>
    <div>
        <form id="deleteForm" method="post" action="/category/${category.category_id}">
            <input type="hidden" name="_method" value="delete">
            <input type="submit" value="Delete"/>
        </form>
        <script>
            $("#deleteForm").submit(function(e) {

                e.preventDefault(); // avoid to execute the actual submit of the form.

                var form = $(this);
                var url = form.attr('action');

                $.ajax({
                    type: "DELETE",
                    url: url,
                    data: form.serialize(), // serializes the form's elements.
                    success: function(data)
                    {
                        console.log(data); // show response from the php script.
                        var url = "/category";
                        $(location).attr('href',url);
                    }
                });


            });
        </script>

    </div>
</@base.body>