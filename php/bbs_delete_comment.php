<?php
    include("./Session.php");
    include("./ConnectDB.php");
    include("./SelectDB.php");
    include("./DeleteDB.php");
    include("./bbs_functions.php");
    
    $session = new Session();

    if (!isset($_POST['token']) || $_POST['token'] !== $_SESSION['token']) {

        $session->destroy();

        header('Location:./bbs_display_login.php');
        exit();
    }

    if (!isset($_POST['comment_id'])) {
        header('Location:./bbs_home.php');
        exit();
    }

    $db = new SelectDB();
    $sql = "SELECT id,thread_id FROM comment WHERE id = :comment_id";
    $binds = array(array(':comment_id', $_POST['comment_id'], PDO::PARAM_INT));
    $comments = $db->selectQuery($sql, $binds);
    if (empty($comments[0])) {
        header('Location:./bbs_home.php');
        exit();
    }
    $comment = $comments[0];

    $db = new DeleteDB();
    $sql = "DELETE FROM comment WHERE id = :comment_id AND user_id = :user_id";
    $binds = array(array(':comment_id', $comment['id'], PDO::PARAM_INT),
                   array(':user_id', $_SESSION['user_id'], PDO::PARAM_INT));
    $db->deleteQuery($sql, $binds);

    $message_done = 'コメントを１件削除しました。';

    header('Location:./bbs_display_thread.php?thread_id='.$comment['thread_id'].'&message_done='.$message_done);
    exit();
?>