<?php
    if (!isset($_POST['comment_id'])) {
        header('Location:./bbs_home.php');
        exit();
    }

    include("./database.php");
    $sql = "SELECT id,thread_id FROM comment WHERE id = :comment_id";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':comment_id', $_POST['comment_id'], PDO::PARAM_INT);

    $prepare -> execute();
    $comment = $prepare -> fetch();

    $sql = "DELETE FROM comment WHERE id = :comment_id";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':comment_id', $comment['id'], PDO::PARAM_INT);

    $prepare -> execute();

    $message_done = 'コメントを１件削除しました。';

    header('Location:./bbs_display_thread.php?thread_id='.$comment['thread_id'].'&message_done='.$message_done);
    exit();
?>