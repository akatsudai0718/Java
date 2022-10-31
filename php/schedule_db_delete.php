<?php
    if (isset($_POST['id'])) {
        $db = new PDO('mysql:dbname=mysql;host=localhost;charset=utf8','root','');

        $sql = "SELECT * FROM test_todo WHERE id = :id";
        $prepare = $db -> prepare($sql);
        $prepare -> bindValue(':id', $_POST['id'], PDO::PARAM_INT);

        $prepare -> execute();
        $data = $prepare -> fetch();

        $sql = "DELETE FROM test_todo WHERE id = :id";
        $prepare = $db -> prepare($sql);
        $prepare -> bindValue(':id', $data['id'], PDO::PARAM_INT);

        $prepare -> execute();

        header('Location:./schedule_db.php?year='.$data['year'].'&month='.$data['month']);
        exit();
    }

    header('Location:./schedule_db.php');
    exit();
?>