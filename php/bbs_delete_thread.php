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

    $db = new SelectDB();
    $sql = "SELECT id,name FROM thread WHERE id = :thread_id AND user_id = :user_id";
    $binds = array(array(':thread_id', $_POST['thread_id'], PDO::PARAM_INT),
                   array(':user_id', $_SESSION['user_id'], PDO::PARAM_INT));
    $threads = $db->selectQuery($sql, $binds);
    if (empty($threads[0])) {
        header('Location:./bbs_home.php');
        exit();
    }
    $thread = $threads[0];

    $db = new DeleteDB();
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
                thread.id = :thread_id AND thread.user_id = :user_id";
    $binds = array(array(':thread_id', $thread['id'], PDO::PARAM_INT),
                   array(':user_id', $_SESSION['user_id'], PDO::PARAM_INT));
    $db->deleteQuery($sql, $binds);

    $message_done = 'スレッド「' . $thread['name'] . '」を削除しました。';

    header('Location:./bbs_home.php?message_done='.$message_done);
    exit();
?>