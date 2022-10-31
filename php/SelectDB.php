<?php
    include("./ConnectDB.php");

    class SelectDB extends ConnectDB {
        public function __construct () {
            parent::__construct();
        }

        // public function selectQuery ($sql, $binds, $flag_row) {
        //     $db = $this->PDO;
        //     $prepare = $db->prepare($sql);
        //     if (!empty($binds)) {
        //         foreach ($binds as $bind) {
        //             $prepare->bindValue($bind[0], $bind[1], $bind[2]);
        //         }
        //     }
        //     $prepare->execute();
        //     $columns = $flag_row ? $prepare->fetch() : $prepare->fetchAll();

        public function selectQuery ($sql, $binds) {
            $db = $this->PDO;
            $prepare = $db->prepare($sql);
            if (!empty($binds)) {
                foreach ($binds as $bind) {
                    $prepare->bindValue($bind[0], $bind[1], $bind[2]);
                }
            }
            $prepare->execute();
            $columns = $prepare->fetchAll();

            /*
            $count = 0;
            while ($column = $prepare->fetch()) {
                $columns[] = $column;
                $count++
            }
            */

            return $columns;
        }
    }
?>