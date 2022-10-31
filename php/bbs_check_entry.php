<?php
    include("./ConnectDB.php");
    include("./SelectDB.php");
    include("./InsertDB.php");
    include("bbs_functions.php");

    if (!isset($_POST['name']) || !isset($_POST['mail']) || !isset($_POST['password'])) {
        header('Location:./bbs_display_entry.php');
        exit();
    }

    if (trim_preg($_POST['name']) == '') $message_error .= 'ユーザー名が入力されていません。';
    if (trim_preg($_POST['mail']) == '') {
        $message_error .= 'メールアドレスが入力されていません。';
    } else if(!validation_mail($_POST['mail'])) {
        $message_error .= '不正な形式のメールアドレスです。';
    }
    if (!validation_password($_POST['password'])) {
        $message_error .= 'パスワードは8文字以上24文字以下の半角英数字です。';
    } else if ($_POST['password'] != $_POST['password2']) {
        $message_error .= 'パスワードが一致しません。';
    }

    if ($message_error) {
        $message_error = '登録失敗：' . $message_error;
        header('Location:./bbs_display_entry.php?message_error='.$message_error);
        exit;
    }

    $db = new SelectDB();
    $sql = "SELECT * FROM user WHERE name = :name OR mail = :mail";
    $binds = array(array(':name', trim_preg($_POST['name']), PDO::PARAM_STR),
                   array(':mail', trim_preg($_POST['mail']), PDO::PARAM_STR));
    $users = $db->selectQuery($sql, $binds);
    if (!empty($users[0])) {
        $message_error = '登録失敗：同じ名前またはメールアドレスのユーザーが存在します。';
        header('Location:./bbs_display_entry.php?message_error='.$message_error);
        exit;
    }

    $db = new InsertDB();
    $sql = "INSERT INTO user (name, mail, password) VALUES (:name, :mail, :password)";
    $binds = array(array(':name', trim_preg($_POST['name']), PDO::PARAM_STR),
                   array(':mail', trim_preg($_POST['mail']), PDO::PARAM_STR),
                   array(':password', password_hash($_POST['password'], PASSWORD_DEFAULT), PDO::PARAM_STR));
    $db->insertQuery($sql, $binds);

    $message_done = 'ユーザーの登録を行いました。';

    header('Location:./bbs_display_entry.php?message_done='.$message_done);
    exit();
?>