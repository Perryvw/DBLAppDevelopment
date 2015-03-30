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

	$firstName = array("Sophie", "Emma", "Julia", "Tess", "Isa", "Zoey", "Anna", "Eva", "Sarah",
		"Daan", "Bram", "Mark", "Finn", "Milan", "Lucas", "Luuk", "Liam", "Noah");

	$lastName = array("Smith", "Johnson", "Williams", "Brown", "Jones", "Davis", "Miller", "Garcia", "Rodriguez", "Wilson");

	$locations = array("Amsterdam","Leeuwarden","Den Haag","Rotterdam","Breda","Utrecht","Eindhoven","Maastricht","Nijmegen","Groningen","Heerenveen", "Tilburg");

	//How many sample users do we want to generate?
	$USERS_TO_GENERATE = 3;
	$HITCHHIKER_DISTRIBUTION = 0.2;

	//Generate users
	for ($i = 0; $i < $USERS_TO_GENERATE; $i++) {
		//Build a random name
		$name = $firstName[rand(1, 16)]." ".$lastName[rand(0,9)];
		
		//Insert into db and get user ID
		$user_id = $db->insertRow('hitch_users', array(NULL, randomPhoneNumber(), rand(0,1), $name, date('Y-m-d', time() - 356.25*24*3600*rand(16,40)), date('Y-m-d'), 0, ''));

		//generate hitchhike data
		//not all users should be hitchhiking
		$ishitching = rand(1,1000) < 1000 * $HITCHHIKER_DISTRIBUTION ? true : false;
		$hitchhikedate = '1990-1-1';
		if ($ishitching) {
			$hitchhikedate = date('Y-m-d H:i:s', time() - rand(10, 3600*24*2));
		}
		$db->insertRow('hitch_hitchhikestatus', array($user_id, $hitchhikedate, randomLocation(), randomLocation()));
	}

	function randomLocation() {
		echo $locations[rand(0,count($locations)-1)].'<br />';
		return $locations[rand(0,count($locations)-1)];
	}

	function randomPhoneNumber($length = 10) {
		$nr = '';
		for ($i=0; $i<$length; $i++) {
			$nr .= rand(0,9);
		}
		return $nr;
	}
?>