<?php
    include("./ConnectDB.php");

    class DeleteDB extends ConnectDB {
        public function __construct () {
            parent::__construct();
        }

        public function deleteQuery ($sql, $binds) {
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