<?php
    session_start();

    if (!isset($_SESSION["login_id"])) {
        header("Location:./schedule_db_login_scr.php");
        exit();
      }

    $_SESSION = array();
    session_destroy();
    
    header("Location:./schedule_db_login_scr.php");
    exit();
?>