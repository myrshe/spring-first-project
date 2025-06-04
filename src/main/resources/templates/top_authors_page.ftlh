<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Top authors</title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/topAuthors.css">
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
            </ul>
        </nav>
    </div>
</header>
<main>
    <div class="top-authors">
        <#list authors as author>
            <div class="author-entry">
                <a href="/profile/${author.id}">
                    <#if author_index < 3>
                        <img src="/images/${author_index + 1}.png"
                         class="avatar-img">
                    </#if>
                    ${author.username}
                </a>
            </div>
        </#list>
    </div>
</main>
</body>
</html>