<#import "../base.ftl" as base>

<@base.body "${title}">
    <form id="addForm" method="POST" action="/category">
        <div>
            <label for="name">Enter name: </label>
            <input type="text" name="name" id="name"/>
        </div>
        <input type="submit" value="Create!"/>
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
                    var url = "/category/" + data["category_id"];
                    $(location).attr('href', url);
                }
            });


        });
    </script>
</@base.body>