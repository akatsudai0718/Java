<?php
    if (isset($_POST['user_name']) && isset($_POST['mail']) && isset($_POST['pass'])) {
        if ($_POST['user_name'] == '') $error .= 'ユーザー名が入力されていません。';
        if ($_POST['mail'] == '') $error .= 'メールアドレスが入力されていません。';
        if ($_POST['pass'] == '') $error .= 'パスワードが入力されていません。';

        if ($error) {
            header('Location:./schedule_db_entry_scr.php?error='.$error.'&entry='.'');
            exit;
        }

        $db = new PDO('mysql:dbname=mysql;host=localhost;charset=utf8','root','');

        $sql = "SELECT * FROM test_user WHERE user_name = :user_name";
        $prepare = $db -> prepare($sql);
        $prepare -> bindValue(':user_name', $_POST['user_name'], PDO::PARAM_STR);

        $prepare -> execute();
        $user = $prepare -> fetch();

        if ($user) {
            $error = '同じ名前のユーザーが存在します。';
            header('Location:./schedule_db_entry_scr.php?error='.$error.'&entry='.'');
            exit;
        }

        $sql = "SELECT * FROM test_user WHERE mail = :mail";
        $prepare = $db -> prepare($sql);
        $prepare -> bindValue(':mail', $_POST['mail'], PDO::PARAM_STR);

        $prepare -> execute();
        $user = $prepare -> fetch();

        if ($user) {
            $error = '同じメールアドレスのユーザーが存在します。';
            header('Location:./schedule_db_entry_scr.php?error='.$error.'&entry='.'');
            exit;
        }

        $sql = "INSERT INTO test_user (user_name, mail, pass) VALUES (:user_name, :mail, :pass)";
        $prepare = $db -> prepare($sql);
        $prepare -> bindValue(':user_name', $_POST['user_name'], PDO::PARAM_STR);
        $prepare -> bindValue(':mail', $_POST['mail'], PDO::PARAM_STR);
        $prepare -> bindValue(':pass', $_POST['pass'], PDO::PARAM_STR);

        $prepare -> execute();

        header('Location:./schedule_db_login_scr.php');
        exit();
    }
    
    header('Location:./schedule_db_login_scr.php');
    exit();
?>