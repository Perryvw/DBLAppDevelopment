<?php
	/*
		Hitch back-end module.
		
		Throw an error:
		throwError( message, errorCode );
		
		Database object:
		$db
		
		Module: GetRouteData
		Input parameters:
			routeID: The ID of the route
		
		Output parameters;
			userID: the ID of the owner of the route
			distance: the length of the route in meters
			duration: the duration of the route in seconds
			startPoint: the starting point of the route.
			endPoint: the end point of the route.
			departure: the departure time of the route.
			arrival: the estimated arrival time
	*/

	//Check if all required input paramters are set
	if (!isset($_GET['routeID'])) {
		throwError('Missing required parameters');	
	}

	//Input parameters
	$route_id = $_GET['routeID'];
		
	//get route data from the database
	$result = $db->getRow("SELECT a.userID, a.timestamp as 'departure', a.startPoint, a.endPoint, b.distance, b.duration FROM hitch_userroutes a, hitch_routes b
		WHERE ((a.startPoint=b.startPoint AND a.endPoint=b.endPoint) OR (a.startPoint=b.endPoint AND a.endPoint=b.startPoint))
		AND a.userrouteID=? LIMIT 1", array($route_id));

	//Check result. If empty - error, else return result.
	if($result == false) {
		throwError('This route could not be found.');
	}
	else {
		//change date to UNIX timestamp
		$result->departure = strtotime($result->departure);

		//set arrival time
		$result->arrival = $result->departure + $result->duration;

		echo json_encode($result);
	}
?>