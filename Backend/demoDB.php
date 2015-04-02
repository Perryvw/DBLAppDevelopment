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

	//How many sample users do we want to generate?
	$USERS_TO_GENERATE = 50;
	$HITCHHIKER_DISTRIBUTION = 0.2;

	//Generate users
	for ($i = 0; $i < $USERS_TO_GENERATE; $i++) {
		//Build a random name
		$name = $firstName[rand(1, 16)]." ".$lastName[rand(0,9)];
		
		//not all users should be hitchhiking, probability based
		$ishitching = rand(1,1000) < 1000 * $HITCHHIKER_DISTRIBUTION ? true : false;

		//Insert into db and get user ID
		$user_id = $db->insertRow('hitch_users', array(NULL, randomPhoneNumber(), $ishitching ? 1 : 0, $name, date('Y-m-d', time() - 356.25*24*3600*rand(16,40)), date('Y-m-d'), 0, ''));

		//generate hitchhike data
		$hitchhikedate = '1990-1-1';
		if ($ishitching) {
			$hitchhikedate = date('Y-m-d H:i:s', time() - rand(10, 3600*24*2));
		}
		$db->insertRow('hitch_hitchhikestatus', array($user_id, $hitchhikedate, randomLocation(), randomLocation()));

		//generate some ratings for the user
		$numRatings = rand(0, 5);
		for ($j = 0; $j < $numRatings; $j++){
			$db->insertRow('hitch_ratings', array($user_id, $user_id+$j+1, rand(0,5), ''));
		}

		//generate some routes for the user
		$numRoutes = rand(1,5);
		for ($j=0; $j < $numRoutes; $j++) {
			$route_id = $db->insertRow('hitch_userroutes', array(null, $user_id, date('Y-m-d H:i:s',time() -12*3600 + rand(0, 3600*24)), randomLocation(), randomLocation()));
		}
	}

	//Return a random location name
	function randomLocation() {
		$locations = array("Amsterdam","Leeuwarden","Den Haag","Rotterdam","Breda","Utrecht","Eindhoven","Maastricht","Nijmegen","Groningen","Heerenveen", "Tilburg");
		return $locations[rand(0,count($locations)-1)];
	}

	//Return a random phone number
	function randomPhoneNumber($length = 10) {
		$nr = '';
		for ($i=0; $i<$length; $i++) {
			$nr .= rand(0,9);
		}
		return $nr;
	}
?>