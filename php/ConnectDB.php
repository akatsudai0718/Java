<?php
    class ConnectDB {
        public $PDO;
        
        public function __construct () {
            $this->PDO = new PDO('mysql:dbname=test;host=localhost;charset=utf8','root','');
        }
    }
?>