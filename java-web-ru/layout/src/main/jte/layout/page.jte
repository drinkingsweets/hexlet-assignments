<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>@(pageTitle)</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        header, footer {
            background-color: #f4f4f4;
            padding: 10px;
            text-align: center;
        }

        main {
            padding: 20px;
        }

        a {
            margin: 0 10px;
            text-decoration: none;
            color: #007BFF;
        }

        a:hover {
            text-decoration: underline;
        }

        footer {
            font-size: 0.8em;
        }
    </style>
</head>
<body>
<header>
    <nav>
        <a href="/">Главная</a>
        <a href="/users">Список пользователей</a>
    </nav>
</header>
<main>
    @param content
    @(content)
</main>
<footer>
    <p>&copy; 2024 | <a href="https://gitlab.com/ваш-профиль" target="_blank">Мой профиль на GitLab</a></p>
</footer>
</body>
</html>
