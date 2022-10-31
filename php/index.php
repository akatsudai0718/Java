<?php
//var_dump($_POST);
$result = '';
if (isset($_POST['num1']) && isset($_POST['num2'])) {
    if ($_POST['num1'] == '') $result .= '左辺が空白です。';
    if ($_POST['num2'] == '') $result .= '右辺が空白です。';
    if (!is_numeric($_POST['num1']) && $_POST['num1'] != '') $result .= '左辺が不正です。';
    if (!is_numeric($_POST['num2']) && $_POST['num2'] != '') $result .= '右辺が不正です。';

    if (!$result) {
        $num1 = $_POST['num1'];
        $num2 = $_POST['num2'];
        $cal = $_POST['cal'];

        switch ($cal) {
            case '1':
                $result = $num1 + $num2;
                break;
            case '2':
                $result = $num1 - $num2;
                break;
            case '3':
                $result = $num1 * $num2;
                break;
            case '4':
                if ($num2 == 0) {
                    $result = '0で割ることはできません。';
                    break;
                }
                $result = $num1 / $num2;
                break;
        }        
    }






}
?>

<html>
    <head>
    </head>
    <body>
        <form method="POST">
            <input name="num1">
            <select name="cal">
                <option value="1">+</option>
                <option value="2">-</option>
                <option value="3">×</option>
                <option value="4">÷</option>
            </select>
            <input name="num2">
            <button>計算</button>
            <p>答え:<?php echo $result; ?></p>
        </form>
    </body>
</html>