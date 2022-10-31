<?php
    session_start();

    if (!isset($_POST['token']) || $_POST['token'] !== $_SESSION['token']) {

        session_destroy();

        header('Location:./bbs_display_login.php');
        exit();
    }

    include("./database.php");
    $sql = "SELECT id,name FROM thread WHERE id = :thread_id";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':thread_id', $_POST['thread_id'], PDO::PARAM_INT);

    $prepare -> execute();
    $thread = $prepare -> fetch();

    $sql = "DELETE
                thread
                ,comment
            FROM
                thread
            LEFT JOIN
                comment
                ON
                thread.id = comment.thread_id
            WHERE
                thread.id = :thread_id";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':thread_id', $thread['id'], PDO::PARAM_INT);

    $prepare -> execute();

    $message_done = 'スレッド「' . $thread['name'] . '」を削除しました。';

    header('Location:./bbs_home.php?message_done='.$message_done);
    exit();
?>