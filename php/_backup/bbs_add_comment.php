<?php
    session_start();

    if (!isset($_POST['token']) || $_POST['token'] !== $_SESSION['token']) {

        session_destroy();

        header('Location:./bbs_display_login.php');
        exit();
    }

    include("bbs_functions.php");

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

    include("./database.php");
    $sql = "INSERT INTO comment (user_id, thread_id, content) VALUES (:user_id, :thread_id, :content)";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':user_id', $_SESSION['user_id'], PDO::PARAM_INT);
    $prepare -> bindValue(':thread_id', $_POST['thread_id'], PDO::PARAM_INT);
    $prepare -> bindValue(':content', trim_preg($_POST['content']), PDO::PARAM_STR);

    $prepare -> execute();

    header('Location:./bbs_display_thread.php?thread_id='.$_POST['thread_id']);
    exit;
?>