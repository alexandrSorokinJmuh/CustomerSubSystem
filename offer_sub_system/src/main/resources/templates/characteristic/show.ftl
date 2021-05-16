<#import "../base.ftl" as base>

<@base.body "${title}">
    <p>
        <span>Id:</span> ${characteristic.characteristic_id}
    </p>
    <p>
        <span>Characteristic name:</span> ${characteristic.name}
    </p>
    <p>
        <span>Characteristic description:</span> ${characteristic.description}
    </p>
    <div>
        <a href="/characteristic">Back</a>
    </div>
    <div>
        <a href="/characteristic/${characteristic.characteristic_id}/edit">Edit</a>
    </div>
    <div>
        <form id="deleteForm" method="post" action="/characteristic/${characteristic.characteristic_id}">
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
                        var url = "/characteristic";
                        $(location).attr('href',url);
                    }
                });


            });
        </script>

    </div>
</@base.body>