<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <title>админка</title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/comments.css">
    <link rel="stylesheet" href="/css/global.css">
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
                <#if role>
                    <li><a href="/allComments">админка</a></li>
                </#if>
                <li><a href="/topAuthors">топ авторов</a></li>
                <li><a href="/tag/demo">демки</a></li>
                <li>
                    <form action="/logout" method="post" style="display:inline;">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <button type="submit">выйти</button>
                    </form>
                </li>
            </ul>
        </nav>
    </div>
</header>
<main>
    <div class="content">
        <p>комментарии:</p>
        <#list comments as comment>
            <div class="comment">
                <a href="/profile/${comment.userId}"><p>${comment.authorUsername}</p></a>
                <p>${comment.text}</p>
            </div>
            <form action="/allComments" method="post">
                <input type="hidden" name="commentId" value="${comment.id}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <button class="button-delete" type="submit">delete</button>
            </form>
        </#list>
    </div>
</main>
</body>
</html>