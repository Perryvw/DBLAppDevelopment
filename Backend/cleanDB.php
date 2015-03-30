<?php
	/* 
		Executing this file will empty the hitch databse. 
	*/
	
	//Require libraries
	require('database.php');

	//Set the timezone since we're using dates
	date_default_timezone_set('Europe/London');

	//Connect to the database
	$db = new DatabaseObject("hitch");

	//List tables to clean
	$tablesToClean = array(
		"hitch_chatboxes",
		"hitch_chatmessages",
		"hitch_chatusers",
		"hitch_hitchhikestatus",
		"hitch_matches",
		"hitch_ratings",
		"hitch_routes",
		"hitch_userroutes",
		"hitch_users"
	);

	//loop over all tables to clean
	foreach($tablesToClean as $table){
		//truncate
		$db->executeQuery("TRUNCATE TABLE ".$table, array());

		//reset auto increment
		$db->executeQuery("ALTER TABLE ".$table." auto_increment = 1", array());
	}
?>