<?php
    session_start();

    $schedule = [];
    $error = '';
    
    $year = date('Y');
    $month = date('n');

    if (!isset($_SESSION['login_id'])) {
        header('Location:./schedule_db_login_scr.php');
        exit();
    }

    if (!isset($_GET['year']) == '' && !isset($_GET['month']) == '') {
        if ($_GET['year'] != '' && $_GET['month'] != '') {
            $year = $_GET['year'];
            $month = $_GET['month'];
        }
    }

    if (isset($_GET['error'])) {
        $error = $_GET['error'];
    }

    $db = new PDO('mysql:dbname=mysql;host=localhost;charset=utf8','root','');

    $sql = "SELECT * FROM test_todo WHERE year = :year AND month = :month AND user_id = :user_id";
    $prepare = $db -> prepare($sql);
    $prepare -> bindValue(':year', $year, PDO::PARAM_INT);
    $prepare -> bindValue(':month', $month, PDO::PARAM_INT);
    $prepare -> bindValue(':user_id', $_SESSION['login_id'], PDO::PARAM_INT);

    $prepare -> execute();
    $schedule = $prepare -> fetchAll();
?>

<html>
    <head>
    </head>
    <body>
        <p>
            <form action="./schedule_db_logout.php">
                <font size="5">スケジュール画面</font>
                <button type="submit">ログアウト</button>
            </form>
        </p>
        <p>ログイン成功：ユーザー名は「<?=htmlspecialchars($_SESSION['login_name'], ENT_QUOTES) ?>」です。</p>
        <form action="./schedule_db.php" method="GET">
            <p>指定した年月の予定を表示</p>
            <p>
                <select name="year">
                    <option value="">年を選んでください</option>
                    <?php for ($y = date("Y", strtotime("-5 year")); $y <= date("Y", strtotime("+5 year")); $y++) { ?>
                        <option value="<?=$y ?>">
                            <?=$y ?>
                        </option>
                    <?php } ?>
                </select>
                年
                <select name="month">
                    <option value="">月を選んでください</option>
                    <?php for ($m = 1; $m <= 12; $m++) { ?>
                        <option value="<?=$m ?>">
                            <?=$m ?>
                        </option>
                    <?php } ?>
                </select>
                月
                <button>表示</button>
            </p>
        </form>
        <form action="./schedule_db_add.php" method="POST">
            <p>指定した日の予定を登録</p>
            <p>
                <input type="hidden" name="year" value="<?=$year ?>">
                <input type="hidden" name="month" value="<?=$month ?>">
                <select name="day">
                    <option value="">日付を選んでください</option>
                    <?php for ($i = 1; $i <= date("t", strtotime($year . "-" . $month)); $i++) { ?>
                        <option value="<?=$i ?>">
                            <?=$i ?>
                        </option>
                    <?php } ?>
                </select>
            日
            <input type="text" name="content">
            <button type="submit">登録</button>
            </p>
        </form>
        <?php if ($error) : ?>
            <p>エラー：<?=$error?></p>
        <?php endif; ?>
        <p><?=$year . "年" . $month . "月の予定" ?></p>
        <table border="1">
            <thead>
                <tr>
                    <td>日付</td>
                    <td>予定</td>
                </tr>
            </thead>
            <tbody>
                <?php for ($i = 1; $i <= date("t", strtotime($year . "-" . $month)); $i++) { ?>
                    <tr>
                        <td><?=htmlspecialchars($i, ENT_QUOTES) . "日";?></td>
                        <td>
                            <?php foreach ($schedule as $todo) { ?>
                                <?php if ($todo["day"] == $i) { ?>
                                    <?=htmlspecialchars($todo["content"], ENT_QUOTES); ?>
                                    <form style="display: inline" action="./schedule_db_edit_scr.php" method="GET">
                                        <input type="hidden" name="id" value="<?=$todo["id"] ?>">
                                        <button type="submit">編集</button>
                                        <button type="submit" formaction="./schedule_db_delete.php" formmethod="POST" onclick="return confirm_test(<?=$year ?>, <?=$month ?>, <?=$todo['day'] ?>)">削除</button>
                                    </form>
                                <?php } ?>
                            <?php } ?>
                        </td>
                    </tr>        
                <?php } ?>
            </tbody>
        </table>
        <script>
            function confirm_test(year, month, day) {
                var select = confirm(`${year}年${month}月${day}日の指定した予定を削除しますか？`);
                return select;
            }
        </script>
    </body>
</html>