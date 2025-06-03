<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <title>Авторизация</title>
</head>
<body>
<#--<img src="/images/logoMini.png" alt="логотип">-->
<div class="form-container">
    <h1>virtuoz</h1>
    <span>вход</span>
    <form action="/signIn" method="POST">

        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <#if RequestParameters.error??>
            <div class="error">Неверный логин или пароль</div>
        </#if>
        <p><a href="/signUp" class="auth-link">зарегистрироваться</a></p>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button type="submit">войти</button>

    </form>
</div>
</body>
</html>
