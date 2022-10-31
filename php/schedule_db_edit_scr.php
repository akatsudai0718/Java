<?php
    if (!isset($_GET['id'])) {
        header('Location:./schedule_db_data.php');
        exit();
    }

    $db = new PDO('mysql:dbname=mysql;host=localhost;charset=utf8','root','');

    $sql = "SELECT * FROM test_todo WHERE id = :id";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':id', $_GET['id'], PDO::PARAM_INT);

    $prepare -> execute();
    $schedule = $prepare -> fetch();
?>

<html>
    <body>
        <p><font size="5">予定編集画面</font></p>
        <p><?=$schedule['year'] . "年" . $schedule['month'] . "月" . $schedule['day'] . "日の予定を編集" ?></p>
        <table>
            <thead>
                <tr>
                    <td>予定</td>
                </tr>
            </thead>
            <tbody>
                <form action="./schedule_db_edit_data.php" method="POST">
                    <input type="hidden" name="id" value="<?=$schedule["id"] ?>">
                    <tr>
                        <td>
                            <input type="text" name="content" value="<?=$schedule["content"] ?>">
                        </td>
                        <td>
                            <button type="submit">編集</button>
                        </td>
                    </tr>
                </form>
                <form action="./schedule_db.php" method="GET">
                    <input type="hidden" name="year" value="<?=$schedule["year"] ?>">
                    <input type="hidden" name="month" value="<?=$schedule["month"] ?>">
                    <td>
                        <button name="back">戻る</button>
                    </td>
                </form>
            </tbody>
        </table>
    </body>
</html>