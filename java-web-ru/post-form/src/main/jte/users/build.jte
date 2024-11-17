@template.layout.page(
    content = @`
    <h1>Add new user</h1>
        <form action="/users" method="post">
            <input type="text" required name="firstName" placeholder="Введите имя">
            <input type="text" required name="secondName" placeholder="Введите фамилию">
            <input type="email" required name="email" placeholder="Введите почту">
            <input type="password" required name="password" placeholder="Введите пароль">
            <button type="submit">Add user</button>
        </form>
`
)