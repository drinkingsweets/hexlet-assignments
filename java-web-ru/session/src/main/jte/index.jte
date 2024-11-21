@importexercise.util.NamedRoutes
@param String currentUser

@template.layout.page(
    content = @`
    @if (currentUser == null)
        <a href="${NamedRoutes.buildSessionPath()}">Войти</a>
    @else
        ${currentUser}, <form action="/sessions/delete" method="post">
            <button type="submit">Выйти</button>
        </form>

    @endif
    `
)