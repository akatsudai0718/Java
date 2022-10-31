<?php
    session_start();

    include("./database.php");
    $sql = "SELECT * FROM user WHERE mail = :mail";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':mail', $_POST['mail'], PDO::PARAM_STR);

    $prepare -> execute();
    $user = $prepare -> fetch();

    if (!password_verify($_POST['password'], $user['password'])) {
        $message_error = 'ログイン失敗：メールアドレスとパスワードを正しく入力してください。';

        header('Location:./bbs_display_login.php?message_error='.$message_error);
        exit();
    }

    session_regenerate_id(TRUE);
    $_SESSION['user_id'] = $user['id'];
    $_SESSION['user_name'] = $user['name'];

    header('Location:./bbs_home.php');
    exit();
?>