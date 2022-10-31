<?php
    include("./Session.php");
    include("./ConnectDB.php");
    include("./SelectDB.php");
    include("./UpdateDB.php");
    include("./bbs_functions.php");

    $session = new Session();

    if (!isset($_POST['token']) || $_POST['token'] !== $_SESSION['token']) {

        $session->destroy();

        header('Location:./bbs_display_login.php');
        exit();
    }

    if (trim_preg($_POST['content']) == '') {
        $message_error = 'エラー：コメントが入力されていません。';
        header('Location:./bbs_display_edit_comment.php?comment_id='.$_POST['comment_id'].'&message_error='.$message_error);
        exit();
    }

    $db = new SelectDB();
    $sql = "SELECT id,thread_id,content FROM comment WHERE id = :comment_id AND user_id = :user_id";
    $binds = array(array(':comment_id', $_POST['comment_id'], PDO::PARAM_INT),
                   array(':user_id', $_SESSION['user_id'], PDO::PARAM_INT));
    $comments = $db->selectQuery($sql, $binds);
    if (empty($comments[0])) {
        header('Location:./bbs_home.php');
        exit();
    }
    $comment = $comments[0];

    $db = new UpdateDB();
    $sql = "UPDATE comment SET content = :content WHERE id = :comment_id";
    $binds = array(array(':content', trim_preg($_POST['content']), PDO::PARAM_STR),
                   array(':comment_id', $comment['id'], PDO::PARAM_INT));
    $db->updateQuery($sql, $binds);

    if ($comment['content'] != trim_preg($_POST['content'])) {
        $message_done = 'コメントを１件編集しました。';
        header('Location:./bbs_display_thread.php?thread_id='.$comment['thread_id'].'&message_done='.$message_done);
        exit();
    }

    header('Location:./bbs_display_thread.php?thread_id='.$comment['thread_id']);
    exit();
?>