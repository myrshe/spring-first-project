<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Регистрация</title>
    <link rel="stylesheet" href="/css/signUp.css">
</head>
<body>
<img src="/images/logoMini.png" alt="логотип">
<div class="form-container">
    <h2>Регистрация</h2>
    <form action="/signUp" method="POST">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>

        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password:</label>
        <input type="text" id="password" name="password" required>
        <p><a href="/signIn" class="auth-link">авторизоваться</a></p>
        <button type="submit">Зарегистрироваться</button>
    </form>
</div>
</body>
</html>
