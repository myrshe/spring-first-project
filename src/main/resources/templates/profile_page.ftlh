<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <title>Профиль ${username}</title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/post.css">
    <link rel="stylesheet" href="/css/profile.css">
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
    <main class="main">
        <div class="container">
            <#if isOwner><#--авторизованный-->
                <div class="div-name"><h1>Добро пожаловать, ${username}</h1></div>
                <div class="div-email"><h2>Email: ${email}</h2></div>
                <div class="count-followers">
                    <p>${countFollowers} подписчики</p>
                </div>
                <div class="count-following">
                    <p>${countFollowing} подписки</p>
                </div>
                <div class="div-createPost">
                    <h4><a href="/createPost">+ Создать пост</a></h4>
                </div>
                <div class="div-posts"><h3>Мои посты:</h3></div>
            <#else><#--не авторизованный-->
                <div class="div-name"><h1>${username}</h1></div>
                <div class="count-followers">
                    <a href="/profile/${userId}/followers">${countFollowers} подписчики</a>
                </div>
                <div class="count-following">
                    <a href="/profile/${userId}/followings">${countFollowing} подписки</a>
                </div>
                <form action="/profile/${userId}/follow" method="post">
                    <button type="submit">
                        <#if checkFollowing>Отписаться<#else>Подписаться</#if>
                    </button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                </form>
                <div class="div-posts"><h3>посты ${username}:</h3></div>
            </#if>
            <#if posts?has_content>
                <#list posts as post>
                    <div class="post">
                        <div class="post-container">
                            <a href="/profile/${post.userId}">
                                <h2>${post.username}</h2>
                            </a>
                            <p>${post.text}</p>
                            <#if post.tags?has_content>
                                <#list post.tags as tag>
                                    <a href="/tag/${tag.name}">#${tag.name} </a>
                                </#list>
                            </#if>
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