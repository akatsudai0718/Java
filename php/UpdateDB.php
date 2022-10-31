<?php
    include("./ConnectDB.php");

    class UpdateDB extends ConnectDB {
        public function __construct () {
            parent::__construct();
        }

        public function updateQuery ($sql, $binds) {
            $db = $this->PDO;
            $prepare = $db->prepare($sql);
            if (!empty($binds)) {
                foreach ($binds as $bind) {
                    $prepare->bindValue($bind[0], $bind[1], $bind[2]);
                }
            }
            $prepare->execute();
        }
    }
?>