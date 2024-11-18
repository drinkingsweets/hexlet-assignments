@import exercise.dto.articles.BuildArticlePage
@param BuildArticlePage pageBuild

@template.layout.page(
    content = @`
    @if (pageBuild != null && pageBuild.getErrors() != null)
        @for(var validation: pageBuild.getErrors().values())
            @for (var error: validation)
                <li>${error.getMessage()}</li>
            @endfor
     @endfor
    @endif
    <form action="/articles" method="post">
    <label for="title">Название статьи:</label>
    <input type="text" id="title" name="title" value="${pageBuild != null ? pageBuild.getTitle() : ""}" required />
    <br />

    <label for="content">Содержимое: </label>
    <textarea id="content" name="content" value="${pageBuild != null ? pageBuild.getContent() : ""}" required></textarea>
    <br />

    <button type="submit">Добавить статью</button>
`
)