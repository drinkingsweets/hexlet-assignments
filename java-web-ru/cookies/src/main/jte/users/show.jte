@import exercise.dto.users.UserPage
@param UserPage page

@template.layout.page(
    content = @`
        <div class="mx-auto p-4 py-md-5">
            <div class="mb-3">
                Имя: ${page.getUser().getFirstName()}
            </div>
            <div class="mb-3">
                Фамилия: ${page.getUser().getLastName()}
            </div>
            <div class="mb-3">
                Email: ${page.getUser().getEmail()}
            </div>
        </div>
    `
)
