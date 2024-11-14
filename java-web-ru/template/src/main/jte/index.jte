@import java.util.List
@import exercise.model.User
@param List<User> USERS
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Пользователи</title>
</head>
<body>
    <h1>Список пользователей</h1>

    <!-- Проверка на наличие пользователей -->
    @if(USERS.isEmpty())
        <p>Пока не добавлено ни одного пользователя</p>
    @else
        <ul>
            <!-- Перебор пользователей и вывод их данных -->
            @for(var user : USERS)
                <li>
                    <h2>${user.getFirstName()}</h2>
                    <p>Email: ${user.getEmail()}</p>
                    <p>Возраст: ${user.getLastName()}</p>
                </li>
            @endfor
        </ul>
    @endif

</body>
</html>
