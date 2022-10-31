<?php
    include("./Session.php");
    include("./SelectDB.php");

    $session = new Session();

    $db = new SelectDB();
    $sql = "SELECT * FROM user WHERE mail = :mail";
    $binds = array(array(':mail', $_POST['mail'], PDO::PARAM_STR));
    $users = $db->selectQuery($sql, $binds);
    if (empty($users[0])) {
        $message_error = 'ログイン失敗：メールアドレスとパスワードを正しく入力してください。';
        header('Location:./bbs_display_login.php?message_error='.$message_error);
        exit();
    }
    $user = $users[0];

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