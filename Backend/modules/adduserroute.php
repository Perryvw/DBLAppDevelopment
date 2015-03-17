<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: AddUserRoute
		Input parameters:
			userID: The ID of the user owning the route.
			startpoint: The starting point of the route.
			endpoint: The endpoint of the route.
			timestamp: The departure timestamp.

		Output parameters:
			routeID: The ID for the added route.
			
	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID']) || !isset($_GET['startpoint']) || !isset($_GET['endpoint']) || !isset($_GET['timestamp'])) {
		throwError('Missing required parameters');
	}

	//Set the timezone since we're using dates
	date_default_timezone_set('Europe/London');
	
	//Input parameters
	$user_id = $_GET['userID'];
	$start_point = $_GET['startpoint'];
	$end_point = $_GET['endpoint'];
	$timestamp = date('Y-m-d H:i:s', $_GET['timestamp']);
	
	//Get user
	$user = $db->getRow("SELECT userID FROM hitch_users WHERE userID=?", array($user_id));
	if ($user == false) {
		throwError('This user could not be found!');
	}
	
	$route_id = $db->insertRow('hitch_userroutes', array(null, $user_id, $timestamp, $start_point, $end_point));

	echo '{ "routeID" : '.$route_id.' }';
?>