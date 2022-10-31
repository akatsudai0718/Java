<?php
    session_start();

    if (!isset($_POST['token']) || $_POST['token'] !== $_SESSION['token']) {

        session_destroy();

        header('Location:./bbs_display_login.php');
        exit();
    }

    include("bbs_functions.php");

    if (trim_preg($_POST['thread_name']) == '') {
        $message_error = 'エラー：タイトルが入力されていません。';
        header('Location:./bbs_home.php?message_error='.$message_error);
        exit;
    }

    include("./database.php");
    $sql = "INSERT INTO thread (user_id, name) VALUES (:user_id, :name)";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':user_id', $_SESSION['user_id'], PDO::PARAM_INT);
    $prepare -> bindValue(':name', trim_preg($_POST['thread_name']), PDO::PARAM_STR);

    $prepare -> execute();

    $message_done = 'スレッド「' . trim_preg($_POST['thread_name']) . '」を作成しました。';

    header('Location:./bbs_home.php?message_done='.$message_done);
    exit();
?>