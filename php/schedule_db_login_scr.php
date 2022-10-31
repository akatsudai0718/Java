<?php
    $error = '';

    if (isset($_GET['error'])) {
        $error = $_GET['error'];
    }
?>

<html>
    <head>
    </head>
    <body>
        <p><font size="5">ログイン画面</font></p>
        <?php if ($error) : ?>
            <p>ログイン失敗：<?=$error?></p>
        <?php endif; ?>
        <form action="./schedule_db_login_data.php" method="POST">
            <p>メールアドレスを入力</p>
            <input type="text" name="mail">
            <p>パスワードを入力</p>
            <input type="password" class="field" id="password" name="pass">
            <input type="checkbox" id="password-check">
            パスワードを表示する
            <p><button type="submit">ログイン</button></p>
        </form>
        <form action="./schedule_db_entry_scr.php" method="GET">
            <button type="submin" name="entry">ユーザー登録をする</button>
        </form>
        <script>
            const pwd = document.getElementById('password');
            const pwdCheck = document.getElementById('password-check');
            pwdCheck.addEventListener('change', function() {
                if(pwdCheck.checked) {
                    pwd.setAttribute('type', 'text');
                } else {
                    pwd.setAttribute('type', 'password');
                }
            }, false);
        </script>
    </body>
</html>
