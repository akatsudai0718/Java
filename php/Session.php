<?php
    class Session {

        public function __construct () {
            session_start();
        }

        public function getToken () {
            $token_byte = openssl_random_pseudo_bytes(16);
            $token = bin2hex($token_byte);
            
            return $token;
        }

        public function destroy () {
            session_destroy();
        }
    }
?>