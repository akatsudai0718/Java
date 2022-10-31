<?php
    function trim_preg($str) {
        $pattern = '/\A\s+|\s+\z/u';
        return preg_replace($pattern, '', $str);
    }

    function validation_mail($mail) {
        return filter_var($mail,  FILTER_VALIDATE_EMAIL);
    }

    function validation_password($password) {
        $pattern = '/\A[a-z\d]{8,24}+\z/i';
        return preg_match($pattern, $password);
    }
?>