@import exercise.dto.LoginPage
@import exercise.util.NamedRoutes
@param LoginPage page

@template.layout.page(
    content = @`
    <h2>Войти в аккаунт</h2>
    @if (page != null && page.getError() != "")
        <h4>${page.getError()}</h4>
    @endif
    <form action="${NamedRoutes.loginPath()}" method="post">
    <input type="name" required name="name" placeholder="Имя">
    <input type="password" required name="password" placeholder="Пароль">
    <input type="submit" value="Войти">
</form>
    `
)