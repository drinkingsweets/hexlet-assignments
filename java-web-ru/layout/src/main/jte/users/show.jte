@import exercise.dto.users.UserPage
@param UserPage page

<h1>Профиль пользователя</h1>
<p><strong>Имя:</strong> ${page.getUser().getFirstName() + " " + page.getUser().getLastName()}</p>
<p><strong>Email:</strong> ${page.getUser().getEmail()}</p>
<a href="/users">Назад к списку пользователей</a>

