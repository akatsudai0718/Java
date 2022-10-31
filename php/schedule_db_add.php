<?php
    session_start();

    if (isset($_POST['year']) && isset($_POST['month']) && isset($_POST['day']) && isset($_POST['content'])) {
        if ($_POST['day'] == '') $error .= '日付が選択されていません。';
        if ($_POST['content'] == '') $error .= '予定が入力されていません。';

        if ($error) {
            header('Location:./schedule_db.php?error='.$error);
            exit;
        }

        $db = new PDO('mysql:dbname=mysql;host=localhost;charset=utf8','root','');

        $sql = "INSERT INTO test_todo (user_id, year, month, day, content) VALUES (:user_id, :year, :month, :day, :content)";
        $prepare = $db -> prepare($sql);
        $prepare -> bindValue(':user_id', $_SESSION['login_id'], PDO::PARAM_INT);
        $prepare -> bindValue(':year', $_POST['year'], PDO::PARAM_INT);
        $prepare -> bindValue(':month', $_POST['month'], PDO::PARAM_INT);
        $prepare -> bindValue(':day', $_POST['day'], PDO::PARAM_INT);
        $prepare -> bindValue(':content', $_POST['content'], PDO::PARAM_STR);

        $prepare -> execute();

        header('Location:./schedule_db.php?year='.$_POST['year'].'&month='.$_POST['month']);
        exit();
    }
    
    header('Location:./schedule_db.php');
    exit();
?>