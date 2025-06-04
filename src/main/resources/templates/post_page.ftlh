<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <title>Пост</title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/postAndComments.css">
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
            </ul>
        </nav>
    </div>
</header>
<main>
    <div class="content">
        <div class="post">
            <div class="post">
                <div class="post-container">
                    <a href="/profile/${post.userId}">
                        <h2>${post.username}</h2>
                    </a>
                    <p>${post.text}</p>
                    <small>${post.date}</small>
                </div>
                <div class="utility-container">
                    <button onclick="toggleLike(${post.id}, this)" data-liked="${post.likedOrNo?string('true', 'false')}">
                        <span class="like-icon">
                            <#if post.likedOrNo>
                                <img src="/images/like2.png"
                                     alt="лайк"
                                     class="like-button like-icon">
                            <#else>
                                <img src="/images/empty-heart.png"
                                     alt="лайк"
                                     class="like-button like-icon">
                            </#if>
                        </span>
                    <span class="like-count">${post.likesCount}</span>
                    </button>
                </div>
            </div>
        </div>

        <h3>Добавить комментарий:</h3>
        <form action="/comment/${post.id}" method="post">
<#--            <input type="hidden" name="post_id" value="${post.id}">-->
            <textarea name="text" rows="10" cols="200" required></textarea><br>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button type="submit">Отправить</button>
        </form>

        <p>комментарии:</p>
        <#if comments?has_content>
            <#list comments as comment>
                <div class="comment">
                    <a href="/profile/${comment.userId}"><p>${comment.authorUsername}</p></a>
                    <p>${comment.text}</p>
                </div>
            </#list>
        </#if>
</main>
<script src="/like.js"></script>
</body>
</html>