<?php
    include("./Session.php");

    $session = new Session();
    $session->destroy();

    header("Location:./bbs_display_login.php");
    exit();
?>