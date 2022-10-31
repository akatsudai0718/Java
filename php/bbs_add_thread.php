<?php
    include("./Session.php");
    include("./ConnectDB.php");
    include("./InsertDB.php");
    include("./bbs_functions.php");

    $session = new Session();

    if (!isset($_POST['token']) || $_POST['token'] !== $_SESSION['token']) {

        $session->destroy();

        header('Location:./bbs_display_login.php');
        exit();
    }

    if (trim_preg($_POST['thread_name']) == '') {
        $message_error = 'エラー：タイトルが入力されていません。';
        header('Location:./bbs_home.php?message_error='.$message_error);
        exit;
    }

    $db = new InsertDB();
    $sql = "INSERT INTO thread (user_id, name) VALUES (:user_id, :name)";
    $binds = array(array(':user_id', $_SESSION['user_id'], PDO::PARAM_INT),
                   array(':name', trim_preg($_POST['thread_name']), PDO::PARAM_STR));
    $db->insertQuery($sql, $binds);

    $message_done = 'スレッド「' . trim_preg($_POST['thread_name']) . '」を作成しました。';

    header('Location:./bbs_home.php?message_done='.$message_done);
    exit();
?>