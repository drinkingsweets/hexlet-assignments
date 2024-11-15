@import exercise.model.User
@import java.util.List
@param List<User> USERS
<h1>Список пользователей</h1>
<ul>
    @for(User user: USERS)
        <li><a href="/users/${user.getId()}">${user.getFirstName() + " " + user.getLastName()}</a></li>

    @endfor
</ul>
