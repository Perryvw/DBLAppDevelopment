<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: GetUserRoutes
		Input parameters:
			userID: The ID of the queried user.
			
		Output parameters:
			routes: An array of objects representing routes, each containing the following fields:
				routeID: The unique ID for this route.
				userID: The ID of the user owning this route.
				startpoint: The startpoint of the route.
				endpoint: The endpoint of the route.
				timestamp: The time of departure for this route.

	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['userID'];
	
	//Get data from database
	$result = $db->getResult("SELECT userrouteID as 'routeID', userID, startPoint, endPoint, timestamp FROM hitch_userroutes WHERE userID=?", array($user_id));
	
	//Check result. If empty - error, else return result.
	echo '{ "routes" : '.json_encode($result).' }';
?>