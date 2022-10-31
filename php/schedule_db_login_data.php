<?php
    session_start();
    
    $db = new PDO('mysql:dbname=mysql;host=localhost;charset=utf8','root','');

    $sql = "SELECT * FROM test_user WHERE mail = :mail AND pass = :pass";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':mail', $_POST['mail'], PDO::PARAM_STR);
    $prepare -> bindValue(':pass', $_POST['pass'], PDO::PARAM_STR);

    $prepare -> execute();
    $user = $prepare -> fetch();

    if(!isset($user['user_id'])) {
        $error = 'メールアドレスとパスワードを正しく入力してください。';
        header('Location:./schedule_db_login_scr.php?error='.$error);
        exit();
    }

    session_regenerate_id(TRUE);
    $_SESSION['login_id'] = $user['user_id'];
    $_SESSION['login_name'] = $user['user_name'];

    header('Location:./schedule_db.php');
    exit();
?>