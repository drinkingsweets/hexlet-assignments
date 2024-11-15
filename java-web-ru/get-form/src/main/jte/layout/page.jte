@import exercise.model.User
@import gg.jte.Content
@import java.util.List
@param List<User> USERS
@param String term
@param String content

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
          crossorigin="anonymous">
    <title>Hello Hexlet!</title>
</head>
<form action="/users" method="get">
    <input type="search" required name="term" value="${term}">
    <input type="submit" value="Искать">
</form>
<body>
<div class="mx-auto p-4 py-md-5">
    <main>
        <h1>Users: ${USERS.size()}</h1>
        @for (User user: USERS)
            <li>${user.getFirstName() + " " + user.getLastName()}</li>
        @endfor
        ${content}
    </main>
</div>
</body>
</html>
