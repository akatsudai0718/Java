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

    if (!isset($_SESSION['user_id'])) {
        header('Location:./bbs_display_login.php');
        exit();
    }

    if (!isset($_POST['content'])) {
        header('Location:./bbs_home.php');
        exit();
    }

    if (trim_preg($_POST['content']) == '') {
        $message_error .= 'エラー：メッセージが入力されていません。';
        header('Location:./bbs_display_thread.php?message_error='.$message_error.'&thread_id='.$_POST['thread_id']);
        exit;
    }

    $db = new InsertDB();

    $sql = "INSERT INTO comment (user_id, thread_id, content) VALUES (:user_id, :thread_id, :content)";
    $binds = array(array(':user_id', $_SESSION['user_id'], PDO::PARAM_INT),
                   array(':thread_id', $_POST['thread_id'], PDO::PARAM_INT),
                   array(':content', trim_preg($_POST['content']), PDO::PARAM_STR));
    $db->insertQuery($sql, $binds);

    header('Location:./bbs_display_thread.php?thread_id='.$_POST['thread_id']);
    exit;
?>