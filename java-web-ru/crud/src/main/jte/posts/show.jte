@import exercise.model.Post
@param Post post

@template.layout.page(
    content = @`
    <h1>${post.getName()}</h1>
    <body>
    ${post.getBody()}
    </body>
`
)
