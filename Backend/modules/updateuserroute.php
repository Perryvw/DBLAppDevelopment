<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: UpdateUserRoute
		Input parameters:
			userID: The ID of the user owning the route.
			startpoint: The starting point of the route.
			endpoint: The endpoint of the route.
			timestamp: The departure timestamp.

		Output parameters:
			-
			
	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID']) || !isset($_GET['startpoint']) || !isset($_GET['endpoint']) || !isset($_GET['timestamp'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['userID'];
	$startpoint = $_GET['startpoint'];
	$endpoint = $_GET['endpoint'];
	$timestamp = $_GET['timestamp'];
	
	//Get data from database
	$db->executeQuery("UPDATE hitch_userroutes SET startpoint=?, endpoint=?, timestamp=? WHERE userID=?", array($startpoint, $endpoint, $timestamp, $user_id));
?>