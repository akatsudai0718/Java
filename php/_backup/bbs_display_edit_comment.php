<?php
    session_start();

    $token_byte = openssl_random_pseudo_bytes(16);
    $token = bin2hex($token_byte);
    $_SESSION['token'] = $token;

    if (!isset($_SESSION['user_id'])) {
        header('Location:./bbs_display_login.php');
        exit();
    }

    if (!isset($_GET['comment_id'])) {
        header('Location:./bbs_home.php');
        exit();
    }

    include("./database.php");

    $sql = "SELECT * FROM comment WHERE id = :id AND user_id = :user_id";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':id', $_GET['comment_id'], PDO::PARAM_INT);
    $prepare -> bindValue(':user_id', $_SESSION['user_id'], PDO::PARAM_INT);

    $prepare -> execute();
    $comment = $prepare -> fetch();
?>

<html>
    <head>
        <meta charset="UTF-8">
        <title>コメント編集画面</title>
        <link rel="stylesheet" href="bbs_style.css">
    </head>
    <body>
        <p class="page_title">コメント編集画面</p>
        <?php if (isset($_GET['message_error'])) : ?>
            <p class="error"><?= htmlspecialchars($_GET['message_error'], ENT_QUOTES) ?></p>
        <?php endif; ?>
        <form action="./bbs_edit_comment.php" method="POST">
            <input type="hidden" name="token" value="<?= $token ?>">
            <input type="hidden" name="comment_id" value="<?=$comment["id"] ?>">
            <textarea name="content"><?= $comment["content"] ?></textarea>
            <button type="submit">コメント編集</button>
        </form>
        <button type="submit">
            <a href="./bbs_display_thread.php?thread_id=<?= $comment["thread_id"] ?>">スレッドに戻る</a>
        </button>
    </body>
</html>