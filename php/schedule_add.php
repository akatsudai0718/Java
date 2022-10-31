<?php
    if (isset($_POST['day']) && isset($_POST['content'])) {
        if ($_POST['day'] == '') $error .= '日付が選択されていません。';
        if ($_POST['content'] == '') $error .= '予定が入力されていません。';

        if ($error) {
            header('Location:./schedule.php?error='.$error);
            exit;
        }

        $f = fopen("./todo.csv", "a");

        $inputdata = array($_POST['day'], $_POST['content']);

        fputcsv($f, $inputdata);

        fclose($f);

        header('Location:./schedule.php');
        exit();
    }
    

?>