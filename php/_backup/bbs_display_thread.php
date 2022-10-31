<?php
    session_start();

    $token_byte = openssl_random_pseudo_bytes(16);
    $token = bin2hex($token_byte);
    $_SESSION['token'] = $token;

    if (!isset($_SESSION['user_id'])) {
        header("Location:./bbs_display_login.php");
        exit();
    }

    include("./database.php");
    $sql = "SELECT * FROM thread WHERE id = :thread_id";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':thread_id', $_GET['thread_id'], PDO::PARAM_INT);

    $prepare -> execute();
    $thread = $prepare -> fetch();

    if (!$thread) {
        $message_error = 'お探しのスレッドは存在しません。';

        header('Location:./bbs_home.php?message_error='.$message_error);
        exit();
    }

    $sql = "SELECT
                user.name AS user_name
                ,comment.id AS comment_id
                ,comment.user_id AS user_id
                ,comment.thread_id AS thread_id
                ,comment.created_at AS created_at
                ,comment.updated_at AS updated_at
                ,comment.content AS content
                ,thread.name AS thread_name
            FROM
                comment
            INNER JOIN
                thread
                ON
                comment.thread_id = thread.id
            INNER JOIN
                user
                ON
                comment.user_id = user.id
            WHERE
                thread.id = :thread_id
            ORDER BY
                comment_id desc";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':thread_id', $_GET['thread_id'], PDO::PARAM_INT);

    $prepare -> execute();
    $comments = $prepare -> fetchAll();
?>

<html>
    <head>
        <meta charset="UTF-8">
        <title><?= $thread['name'] ?></title>
        <link rel="stylesheet" href="bbs_style.css">
    </head>
    <body>
        <p class="page_title"><?= htmlspecialchars($thread['name'], ENT_QUOTES) ?></p>
        <table>
            <thead>
                <tr>
                    <td>ユーザー名</td>
                    <td>コメント</td>
                    <td>作成日時</td>
                    <td>更新日時</td>
                </tr>
            </thead>
            <tbody>
                <?php foreach ($comments as $comment) { ?>
                    <tr>
                        <td>
                            <?= htmlspecialchars($comment['user_name'], ENT_QUOTES) ?>
                        </td>
                        <td>
                            <?= htmlspecialchars($comment['content'], ENT_QUOTES) ?>
                        </td>
                        <td>
                            <?= $comment['created_at'] ?>
                        </td>
                        <td>
                            <?= $comment['updated_at'] ?>
                        </td>
                        <?php if ($comment['user_id'] == $_SESSION['user_id']) { ?>
                            <form action="./bbs_display_edit_comment.php" method="GET">
                                <input type="hidden" name="comment_id" value="<?= $comment['comment_id'] ?>">
                                <td class="button_in_table">
                                    <button type="submit">コメント編集</button>
                                </td>
                                <td class="button_in_table">
                                    <button type= "submit" formaction="./bbs_delete_comment.php" formmethod="POST" onclick="return confirm_test()">コメント削除</button>
                                </td>
                            </form>
                        <?php } ?>
                    </tr>
                <?php } ?>
            </tbody>
        </table>
        <br>
        <?php if (!$comments) : ?>
            <p class="error"><?= 'このスレッドにはコメントがありません。' ?></p>
        <?php endif; ?>
        <?php if (isset($_GET['message_error'])) : ?>
            <p class="error"><?= htmlspecialchars($_GET['message_error'], ENT_QUOTES) ?></p>
        <?php endif; ?>
        <?php if (isset($_GET['message_done'])) : ?>
            <p class="done"><?= htmlspecialchars($_GET['message_done'], ENT_QUOTES) ?></p>
        <?php endif; ?>
        <br>
        <form action="./bbs_add_comment.php" method='POST'>
            <input type="hidden" name="token" value="<?= $token ?>">
            <input type="hidden" name="thread_id" value="<?= $_GET['thread_id'] ?>">
            <table>
                <tr>
                    <th>コメント</th>
                    <td><textarea name="content"></textarea></td>
                    <td class="button_in_table">
                        <button type= "submit">書き込む</button>
                    </td>
                </tr>
            </table>
        </form>
        <button type="submit">
            <a href="./bbs_home.php">ホームへ戻る</a>
        </button>
        <script>
            function confirm_test() {
                var select = confirm(`指定した書き込みを削除しますか？`);
                return select;
            }
        </script>
    </body>
</html>