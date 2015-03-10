<?php	
	class DatabaseObject {
		//database connection data
		//private $db_ip = "185.13.226.226:3306"; // REMOTE DB ACCESS
		private $db_ip = "localhost";
		private $db_name = "perryof119_hitch";
		private $db_user = "perryof119_huser";
		private $db_password = "hitch";
		
		public $dbCon;
		
		function __construct($db_name){
			//connect to the database
			$this->dbCon = new PDO("mysql:host=".$this->db_ip.";dbname=".$this->db_name, $this->db_user, $this->db_password);;
		}
		
		/*
		Execute a prepared query, return the statement.
		Params:	query - The query to execute with '?' instead of variables
				params - The variables in order of the '?'s in the query
		*/
		function executeQuery($query, $params) {
			//Prepare
			$stmt = $this->dbCon->prepare($query);
			
			//Execute
			$stmt->execute($params) or die(print_r($stmt->errorInfo()));
			
			//return statement
			return $stmt;
		}
		
		/* 
		Return the first row returned by the query, false if empty
		Params: query - A valid SQL query with '?' instead of variables
				params - A list of variables sorted in location
		*/
		function getRow($query, $params = array()){
			$res = $this->getResult($query, $params);
			if (count($res) < 1) {
				return false;
			}
			return $res[0];
		}
		
		/* 
		Return an array of objects returned by the query (can be empty)
		Params: query - A valid SQL query with '?' instead of variables
				params - A list of variables sorted in location
		*/
		function getResult($query, $params = array()){
			$stmt = $this->executeQuery($query, $params) or die(print_r($this->dbCon->errorInfo()));
			
			//build return object
			$rows = array();
			while ( $row = $stmt->fetch(PDO::FETCH_ASSOC) ){
				$rows[] = (object)$row;
			}
			
			//Return result
			return $rows;
		}
		
		/* 
		Insert a row into some table and return the auto increment key at which it was inserted.
		If the row does not have an auto increment key, returns 0.
		Params: table - The name of the table to insert the row in
				params - A list of variables representing each column of the row to insert
		*/
		function insertRow($table, $params){
			//build our query 
			$query = 'INSERT INTO '.$table.' VALUES(';
			for ($i = 0; $i < count($params); $i++){
				$query .= "?";
				if ($i < count($params)-1){
					$query .= ', ';
				}	
			}
			$query .= ')';
			
			//Prepare
			$stmt = $this->executeQuery($query, $params);
			
			//Execute and close
			
			//Return id of the row we inserted
			return $this->dbCon->lastInsertId();
		}
		
		//Close the database connection
		function close() {
			$this->dbCon->close();
		}
		
		//Convert an array of objects to an array of references
		function toRefs($arr){
			$refs = array();
			foreach($arr as $key => $value)
				$refs[$key] = &$arr[$key];
			return $refs;
		}
		
		//Get the type string of a list of objects
		function getTypeString( $arr ) {
			$str = '';
			for ($i = 0; $i < count($arr); $i++){
				if (gettype($arr[$i]) == 'integer') {
					$str .= 'i';
				} else {
					$str .= 's';
				}				
			}
			return $str;
		}
	}
?>