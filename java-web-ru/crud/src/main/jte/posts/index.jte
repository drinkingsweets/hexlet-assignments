@import exercise.dto.posts.PostsPage
@import exercise.model.Post
@param PostsPage page
@param int currentPage


@template.layout.page(
    content = @`
    @for(Post post: page.getPosts())
        <li><a href="/posts/${post.getId()}">${post.getName()}</a></li>
    @endfor

    <nav>
        @if (currentPage > 1)
            <a href="?page=${currentPage - 1}">Назад</a>
        @endif

            <a href="?page=${currentPage + 1}">Вперед</a>
    </nav>
`

)