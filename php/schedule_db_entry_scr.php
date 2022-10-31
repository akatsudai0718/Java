<?php
    $error = '';

    if (!isset($_GET['entry'])) {
        header('Location:./schedule_db_login_scr.php');
        exit();
    }

    if (isset($_GET['error'])) {
        $error = $_GET['error'];
    }
?>

<html>
    <head>
    </head>
    <body>
        <p class="fontSize6" style="font-size: 5px">ユーザー登録画面</p>
        <?php if ($error) : ?>
            <p>登録失敗：<?=$error?></p>
        <?php endif; ?>
        <form action="./schedule_db_entry_data.php" method="POST">
            <p>ユーザー名を入力</p>
            <input type="text" name="user_name">
            <p>メールアドレスを入力</p>
            <input type="text" name="mail">
            <p>パスワードを入力</p>
            <input type="password" class="field" id="password" name="pass">
            <input type="checkbox" id="password-check">
            パスワードを表示する
            <p>
                <button type="submit">登録</button>
                <button type="submit" formaction="./schedule_db_login_scr.php">戻る</button>
            </p>
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
