<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">

    <title>шутка!</title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/joke.css">
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
                <li><a href="/topAuthors">топ авторов</a></li>
                <li><a href="/tag/demo">демки</a></li>
                <li><a href="/randomJoke">шуточка</a></li>
            </ul>
        </nav>
    </div>
</header>
<main>
    <div class="random-joke-div">
    <h1>Случайная шутка</h1>

    <div class="joke-container">
        <p>${joke}</p>
        <p class="category">категория: ${category}</p>
    </div>

    <button onclick="window.location.reload()">Ещё шутку!</button>
    </div>
</main>
</body>
</html>