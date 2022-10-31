<?php
?>

<html>
    <head>
        <meta charset="UTF-8">
        <title>新規登録画面</title>
        <link rel="stylesheet" href="bbs_style.css">
    </head>
    <body>
        <p class="page_title">新規登録画面</p>
        <?php if (isset($_GET['message_error'])) : ?>
            <p class="error"><?= $_GET['message_error'] ?></p>
        <?php endif; ?>
        <?php if (isset($_GET['message_done'])) : ?>
            <p class="done"><?= $_GET['message_done'] ?></p>
        <?php endif; ?>
        <form action="./bbs_check_entry.php" method="POST">
            <p>ユーザー名を入力</p>
            <input type="text" name="name">
            <p>メールアドレスを入力</p>
            <input type="text" name="mail">
            <p>パスワードを入力（半角英数字8文字以上24文字以下）</p>
            <p><input type="password" class="field" id="password" name="password"></p>
            <p>パスワードをもう一度入力</p>
            <p><input type="password" class="field" id="password" name="password2"></p>
            <button type="submit">ユーザーを登録する</button>
        </form>
        <button type="submit">
            <a href="./bbs_display_login.php">ログイン画面に戻る</a>
        </button>
    </body>
</html>