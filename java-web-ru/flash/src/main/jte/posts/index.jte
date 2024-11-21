@import exercise.model.Post
@import exercise.util.NamedRoutes
@import java.util.List
@param List<Post> posts
@param String flash

@template.layout.page(
content = @`
    <h1>Все посты</h1>
    <a href="${NamedRoutes.buildPostPath()}">Добавить пост</a>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    @if (flash != "")
        <div class="alert alert-success" role="alert">
            ${flash}
        </div>
    @endif
    @if (posts.size() != 0)
        @for(Post post: posts)
            <li><a href="${NamedRoutes.postPath(post.getId())}">${post.getName()}</a></li>
        @endfor
    @else

        <p>Пока не было добавлено постов</p>
    @endif
`
)