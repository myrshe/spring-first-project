<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Лента подписок</title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/feed.css">
    <link rel="stylesheet" href="/css/post.css">
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
<main class="main">
    <div class="container">
        <#if posts?has_content>
            <#list posts as post>
                <div class="post">
                    <div class="post-container">
                        <a href="/profile/${post.userId}">
                            <h2>${post.username}</h2>
                        </a>
                        <p>${post.text}</p>

                        <div class="post-files">
                            <#list post.files as file>
                                <div class="post-file">
                                    <#if file.type?starts_with("image/")>
                                        <figure class="post-image">
                                            <img src="/files/${file.storageFileName}"
                                                 alt="${file.originalFileName}"
                                                 loading="lazy">
                                        </figure>
                                    <#elseif file.type?starts_with("video/")>
                                        <video controls class="post-video">
                                            <source src="/files/${file.storageFileName}" type="${file.type}">
                                        </video>
                                    <#elseif file.type?starts_with("audio/")>
                                        <audio controls class="post-audio">
                                            <source src="/files/${file.storageFileName}" type="${file.type}">
                                        </audio>
                                    </#if>
                                </div>
                            </#list>
                        </div>
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
                        <a href="/post/${post.id}">
                            <img src="/images/comment.png" alt="Комментарии">
                        </a>
                    </div>
                </div>
            </#list>
        <#else>
            <p>Пока постов нет</p>
        </#if>
    </div>
</main>
<script src="/like.js"></script>
</body>
</html>