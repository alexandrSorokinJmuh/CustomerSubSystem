
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>New person</title>
</head>
<body>


<form method="POST" action="/customer">
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


</body>
</html>