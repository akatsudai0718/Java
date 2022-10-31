<?php
    // todo CSVからデータを取得し、変数に配列の形でデータを代入する
    $schedule = [];
    $error = '';

    include('./schedule_add.php');

    if (isset($_GET['error'])) {
        $error = $_GET['error'];
    }

    $f = fopen("./todo.csv", "r");

    while ($line = fgetcsv($f)) {
        $data = array('day' => $line[0], 'content' => $line[1]);
        $schedule[] = $data;
    }

    fclose($f);
?>

<html>
    <body>
        <form method="POST">
            <select name="day">
                <option value="">日付を選んでください</option>
                <!-- todo ループ処理で1~30まで -->
                <?php for ($i=1; $i<31; $i++) { ?>
                    <option value='<?=$i ?>'>
                        <?=$i ?>
                    </option>
                <?php } ?>
            </select>
            <input type="text" name="content">
            <button>登録</button>
        </form>
        <?php if ($error) : ?>
            <p>エラー：<?=$error?></p>
        <?php endif; ?>
        <p>1ヶ月の予定</p>
        <table border='1'>
            <thead>
                <tr>
                    <td>日付</td>
                    <td>予定</td>
                </tr>
            </thead>
            <tbody>
                <?php for ($i=1; $i<31; $i++) { ?>
                    <tr>
                        <td><?=htmlspecialchars($i, ENT_QUOTES) . '日';?></td>
                        <td>
                            <!-- todo $scheduleをループ処理で表示 -->
                            <?php
                                $content = '';
                                foreach ($schedule as $todo) {
                                    if ($todo['day'] == $i) {
                                        if ($content == '') {
                                            $content = $todo['content'];
                                        } else {
                                            $content = $content . ' ' . $todo['content'];
                                        }
                                    }
                                }
                                echo htmlspecialchars($content, ENT_QUOTES);
                            ?>
                        </td>
                    </tr>        
                <?php } ?>
            </tbody>
        </table>
    </body>
</html>