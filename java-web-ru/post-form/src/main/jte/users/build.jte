@template.layout.page(
    content = @`
    <h1>Регистрация пользователя</h1>
<form action="/users" method="post">
    <input type="text" name="firstName" placeholder="Введите имя" required>
    <input type="text" name="secondName" placeholder="Введите фамилию" required>
    <input type="email" name="email" placeholder="Введите почту" required>
    <input type="password" name="password" placeholder="Введите пароль" required>
    <button type="submit">Зарегистрироваться</button>
</form>

`
)