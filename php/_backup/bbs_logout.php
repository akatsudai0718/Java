<?php
    session_start();

    session_destroy();

    header("Location:./bbs_display_login.php");
    exit();
?>