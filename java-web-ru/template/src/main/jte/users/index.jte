@import exercise.model.User
@import java.util.List
@param List<User> USERS

<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
          crossorigin="anonymous">
    <title>Users</title>
  </head>
  <body>
    <div class="mx-auto p-4 py-md-5">
      <main>
        <a href="/users">Пользователи</a>
      </main>
      @if(USERS != null)
        @for(User user : USERS)
           <div>
               <h2>
                 <a href="/users/${user.getId()}">
                   ${user.getFirstName()} ${user.getLastName()}<br>
                 </a>
               </h2>
           </div>
        @endfor
      @else
        <p>No users available.</p>
      @endif
    </div>
  </body>
</html>
