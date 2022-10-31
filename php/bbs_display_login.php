<?php
?>

<html>
    <head>
        <meta charset="UTF-8">
        <title>ログイン画面</title>
        <link rel="stylesheet" href="bbs_style.css">
    </head>
    <body>
        <p class="page_title">ログイン画面</p>
        <?php if (isset($_GET['message_error'])) : ?>
            <p class="error"><?= $_GET['message_error'] ?></p>
        <?php endif; ?>
        <form action="./bbs_check_login.php" method="POST">
            <p>メールアドレスを入力</p>
            <input type="text" name="mail">
            <p>パスワードを入力</p>
            <p><input type="password" class="field" id="password" name="password"></p>
            <p><button type="submit">ログイン</button></p>
        </form>
        <form action="./bbs_display_entry.php" method="GET">
            <button type="submit">新規登録をする</button>
        </form>
    </body>
</html>