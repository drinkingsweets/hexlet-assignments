@import exercise.model.Post
@param Post page

@template.layout.page(
    content = @`
         <form action="/posts/${page.getId()}" method="post">
            <div class="mb-3">
                    <label class="form-label">
                        Название
                        <input type="text" class="form-control" name="name" value="${page.getName()}" />
                    </label>
                </div>
                <div class="mb-3">
                    <label class="form-label">Содержание</label>
                    <textarea class="form-control" rows="20" cols="70" type="text" name="body">${page.getBody()}</textarea>
                </div>
                <input type="submit" class="btn btn-primary" value="Сохранить" />
            </form>

`

)
