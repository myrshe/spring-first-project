<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/createPost.css">
    <script
            src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous"></script>
    <title>Создать пост</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
<header class="header">
        <div class="header">
                <nav class="nav-header">
                <ul>
                    <li>
                        <a class="top" href="">
                            <img src="/images/logoMini.png" alt="Логотип сайта">
                        </a>
                    </li>
                    <li><a href="/profile/${currentId}">мой профиль</a></li>
                    <li><a href="/globalFeed">глобальная лента</a></li>
                    <li><a href="/subscribeFeed">лента подписок</a></li>
                </ul>
            </nav>
        </div>
</header>
<main>
    <div class="content">
        <div class="form-container post">
            <h2>Создать новый пост</h2>
            <#if error??>
                <div class="error-message">${error}</div>
            </#if>
            <form action="/createPost" method="post" enctype="multipart/form-data">
                <label for="text">Введите текст:</label>
                <textarea id="text" name="text" placeholder="Напишите здесь текст..." required></textarea>

                <label for="file">Добавить файлы(не больше 10):</label>
                <input type="file" id="file" name="files" multiple accept="image/*,video/*,audio/*">

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="submit" value="Опубликовать пост">
            </form>
        </div>
    </div>
</main>
</body>
</html>