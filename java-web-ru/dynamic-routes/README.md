## Javalin

### Ссылки

* [Методы работы с контекстом](https://javalin.io/documentation#context)
* [Исключение NotFoundResponse](https://javalin.io/documentation#notfoundresponse), которое фреймворк обработает как ошибку *404 Not Found*

## src/main/java/exercise/App.java

В приложении реализуйте обработчик для динамического маршрута */companies/{id}*.

При GET-запросе на адрес */companies/{id}* приложение должно отдавать представление компании в формате json.

Данные о компаниях находятся в константе *COMPANIES* в виде списка `List`. Каждая компания — это словарь `Map<String, String>` с ключом `id`:

```java
// Гипотетический пример
Map<String, String> company = Map.of(
    "id", "3",
    "name", "Google"
    "telephone", "12345678"
)
```

Если компания с таким идентификатором не существует, приложение должно возвращать страницу с HTTP-кодом 404 и текстом *Company not found*.

### Подсказки

* Изучите методы по работе с контекстом и найдите тот, который позволяет сериализовать тело ответа в JSON
* Чтобы вернуть страницу с кодом 404, вам понадобится выбросить [исключение NotFoundResponse](https://javadoc.io/doc/io.javalin/javalin/latest/io/javalin/http/NotFoundResponse.html). Javalin сам обработает его, и вернет ответ с кодом 404
