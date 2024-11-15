@import exercise.model.User
@import java.util.List
@param List<User> USERS
@param String term

@template.layout.page(
    content = @`
        <div class="mx-auto p-4 py-md-5">
            <form action="/users" method="get">
                <input type="search" required name="term" value="${term}">
                <input type="submit" value="Искать">
            </form>

            <main>
                <h1>Users: ${USERS.size()}</h1>
                @for (User user: USERS)
                    <li>${user.getFirstName()} ${user.getLastName()}</li>
                @endfor
            </main>
        </div>
`
)
