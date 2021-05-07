<#import "../base.ftl" as base>

<@base.body "${title}">
    <form id="addForm" method="POST" action="/offer">
        <div>
            <label for="name">Enter name: </label>
            <input type="text" name="name" id="name"/>
        </div>
        <div>
            <label for="price">Enter price: </label>
            <input type="number" placeholder="1.0" step="0.01" min="0" name="price" id="price"/>
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
                    var url = "/offer/" + data["offer_id"];
                    $(location).attr('href', url);
                }
            });


        });
    </script>
</@base.body>