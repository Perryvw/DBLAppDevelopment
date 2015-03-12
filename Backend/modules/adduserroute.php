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
	
	//Input parameters
	$user_id = $_GET['userID'];
	$start_point = $_GET['startpoint'];
	$end_point = $_GET['endpoint'];
	$timestamp = $_GET['timestamp'];
	
	//Get data from database
	
	//WHAT TO DO WITH AUTO INCREMENTED ROUTE ID????!!?!?!?!
	
	
	$db->insertRow('hitch_userroutes', array(null, $user_id, $startpoint, $endpoint, $timestamp));
?>