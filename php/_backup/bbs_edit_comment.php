<?php
    session_start();

    if (!isset($_POST['token']) || $_POST['token'] !== $_SESSION['token']) {

        session_destroy();

        header('Location:./bbs_display_login.php');
        exit();
    }

    include("bbs_functions.php");

    if (trim_preg($_POST['content']) == '') {
        $message_error = 'エラー：コメントが入力されていません。';
        header('Location:./bbs_display_edit_comment.php?comment_id='.$_POST['comment_id'].'&message_error='.$message_error);
        exit();
    }

    include("./database.php");
    $sql = "SELECT id,thread_id,content FROM comment WHERE id = :comment_id AND user_id = :user_id";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':comment_id', $_POST['comment_id'], PDO::PARAM_INT);
    $prepare -> bindValue(':user_id', $_SESSION['user_id'], PDO::PARAM_INT);

    $prepare -> execute();
    $comment = $prepare -> fetch();

    $sql = "UPDATE comment SET content = :content WHERE id = :comment_id";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':content', trim_preg($_POST['content']), PDO::PARAM_STR);
    $prepare -> bindValue(':comment_id', $comment['id'], PDO::PARAM_INT);

    $prepare -> execute();

    if ($comment['content'] != trim_preg($_POST['content'])) {
        $message_done = 'コメントを１件編集しました。';
        header('Location:./bbs_display_thread.php?thread_id='.$comment['thread_id'].'&message_done='.$message_done);
        exit();
    }

    header('Location:./bbs_display_thread.php?thread_id='.$comment['thread_id']);
    exit();
?>