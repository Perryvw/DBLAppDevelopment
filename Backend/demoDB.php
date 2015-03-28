<?php
	/* 
		Executing this file will fill the hitch databse with demo data. 
	*/

	//Require libraries
	require('database.php');

	//Set the timezone since we're using dates
	date_default_timezone_set('Europe/London');

	//Connect to the database
	$db = new DatabaseObject("hitch");



?>