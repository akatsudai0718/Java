<?php
    include("./Session.php");
    include("./ConnectDB.php");
    include("./SelectDB.php");

    $session = new Session();
    $token = $session->GetToken();
    $_SESSION['token'] = $token;

    if (!isset($_SESSION['user_id'])) {
        header("Location:./bbs_display_login.php");
        exit();
    }

    $db = new SelectDB();
    $sql = "SELECT
                thread.id AS thread_id
                ,thread.name AS thread_name
                ,thread.user_id AS user_id
                ,user.name AS user_name
            FROM
                thread
            INNER JOIN
                user
                ON
                thread.user_id = user.id
            ORDER BY
                thread_id desc";
    $threads = $db->selectQuery($sql);
?>

<html>
    <head>
        <meta charset="UTF-8">
        <title>〇〇掲示板</title>
        <link rel="stylesheet" href="bbs_style.css">
    </head>
    <body>
        <p class="page_title">〇〇掲示板</p>
        <p>ユーザー名：<?= htmlspecialchars($_SESSION['user_name'], ENT_QUOTES) ?></p>
        <form action="./bbs_add_thread.php" method='POST'>
            <input type="hidden" name="token" value="<?= $token ?>">
            <table>
                <tr>
                    <th>スレッド名</th>
                    <td><input type="text" name="thread_name"></td>
                    <td class="button_in_table"><button type="submit">スレッド作成</button></td>
                </tr>
            </table>
        </form>
        <br>
        <?php if (isset($_GET['message_error'])) : ?>
            <p class="error"><?= htmlspecialchars($_GET['message_error'], ENT_QUOTES) ?></p>
        <?php endif; ?>
        <?php if (isset($_GET['message_done'])) : ?>
            <p class="done"><?= htmlspecialchars($_GET['message_done'], ENT_QUOTES) ?></p>
        <?php endif; ?>
        <table>
            <thead>
                <tr>
                    <td>投稿者</td>
                    <td>スレッド名</td>
                </tr>
            </thead>
            <tbody>
                <?php foreach ($threads as $thread) { ?>
                    <tr>
                        <td>
                            <?= htmlspecialchars($thread['user_name'], ENT_QUOTES) ?>
                        </td>
                        <td>
                            <a href="./bbs_display_thread.php?thread_id=<?= $thread['thread_id'] ?>">
                                <?= htmlspecialchars($thread['thread_name'], ENT_QUOTES) ?>
                            </a>
                        </td>
                        <?php if ($thread['user_id'] == $_SESSION['user_id']) { ?>
                            <form action="./bbs_delete_thread.php" method="POST">
                                <input type="hidden" name="token" value="<?= $token ?>">
                                <input type="hidden" name="thread_id" value="<?= $thread['thread_id'] ?>">
                                <td class="button_in_table">
                                    <button type="submit" onclick="return confirm_test()">スレッド削除</button>
                                </td>
                            </form>
                        <?php } ?>
                    </tr>
                <?php } ?>
            </tbody>
        </table>
        <br>
        <form action="./bbs_logout.php">
            <button type="submit">ログアウトする</button>
        </form>
        <script>
            function confirm_test() {
                var select = confirm(`指定したスレッドを削除しますか？`);
                return select;
            }
        </script>
    </body>
</html>