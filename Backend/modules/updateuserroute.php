<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: UpdateUserRoute
		Input parameters:
			routeID: The ID of the user owning the route.
			startpoint: The starting point of the route.
			endpoint: The endpoint of the route.
			timestamp: The departure timestamp.

		Output parameters:
			-
			
	*/
	
	//Check if required parameters are set
	if(!isset($_GET['routeID']) || !isset($_GET['startpoint']) || !isset($_GET['endpoint']) || !isset($_GET['timestamp'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$route_id = $_GET['routeID'];
	$startpoint = $_GET['startpoint'];
	$endpoint = $_GET['endpoint'];
	$timestamp = date('Y-m-d H:i:s', $_GET['timestamp']);

	//check if the route exists
	$route = $db->getRow("SELECT routeID FROM hitch_userroutes WHERE routeID=?", array($route_id));
	if ($route == false) {
		throwError('This route could not be found.');
	}
	
	//Get data from database
	$db->executeQuery("UPDATE hitch_userroutes SET startpoint=?, endpoint=?, timestamp=? WHERE routeID=?", array($startpoint, $endpoint, $timestamp, $route_id));

	//Output
	echo '{}';
?>