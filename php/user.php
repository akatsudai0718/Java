<?php

$list = [];

$risou = [
    ['name' => '佐藤', 'age' => '14'],
    ['name' => '鈴木', 'age' => '56'],
    ['name' => '高橋', 'age' => '89']
];

$error = '';
$errormesssage = '';

if (isset($_POST['data1']) && isset($_POST['data2'])) {
    if ($_POST['data1'] == '') $error .= '名前が空白です。';
    if (is_numeric($_POST['data1'])) $error .= '名前に数字が入力されています。';
    if ($_POST['data2'] == '') $error .= '年齢が空白です。';
    if (!is_numeric($_POST['data2']) && $_POST['data2'] != '') $error .= '年齢に文字が入力されています。';

    if ($error) {
        $errormesssage = 'エラー:' . $error;
    }

    if (!$error) {
        $f = fopen("./user.csv", "a");

        $data = array(htmlspecialchars($_POST['data1']), htmlspecialchars($_POST['data2']));

        fputcsv($f, $data);
    }
}

$f = fopen("./user.csv", "r");

while ($line = fgetcsv($f)) {
    $data = array('name' => $line[0], 'age' => $line[1]);
    $list[] = $data;
}

fclose($f);

var_dump($list == $risou);

?>

<html>
    <head>
    </head>
    <body>
        <form method="POST">
            <p>名前を入力
            <input name="data1"></p>
            <p>年齢を入力
            <input name="data2"></p>
            <button>送信</button>
            <p><?=$errormesssage?></p>
        </form>

        <table border='1'>
            <tr>
                <th>名前</th>
                <th>年齢</th>
            </tr>     
            <?php foreach ($list as $user) { ?>
                <tr>
                    <th><?=$user['name']; ?></th>
                    <th><?=$user['age']; ?></th>
                </tr>
            <?php } ?>
        </table>
    </body>
</html>