<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: GetMatchingroutes
		Input parameters:
			hitchhikerID: The user ID of the hitchhiker.
			amount: The amount of routes to return. (Optional, default = 5).

		Output parameters:
			routes: An array of objects representing routes, each containing the following fields:
				userID: The route’s user ID.
				routeID: The routeID matched to the hitchhiker
				relevance: The internal score assigned to the match.
	*/

	//Algorithm parameters
	$MAX_PERC_DEVIATION = 0.15; //The maximum percentage the algorithm can deviate from the original route length

	//Request a distance between two places from the google API
	function getDistance($start, $end) {
		$response = file_get_contents("https://maps.googleapis.com/maps/api/distancematrix/json?origins=".$start."&destinations=".$end);
		$respObj = json_decode($response);
		if ($respObj->status == "OK") {
			return $respObj->rows[0]->elements[0]->distance->value;
		} else {
			return -1;
		}
	}
	
	//Check if required parameters are set
	if(!isset($_GET['hitchhikerID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['hitchhikerID'];
	$amount = 5;
	if(isset($_GET['amount'])){
		$amount = $_GET['amount'];
	}
	
	//Get hitchhiker location and destination
	$hhiker = $db->getRow("SELECT userID, location, destination, timestamp FROM hitch_hitchhikestatus WHERE userID=?", array($user_id));

	if ($hhiker == false) {
		throwError('Hitchhiker ID not found.');
	}

	$hhiker->timestamp = '2015-03-17 13:00:00';

	$matchedRoutes = array();

	//Use iterative deepening to look further back
	for ($i = 0; $i < 12 && count($matchedroutes) < 5; $i++) {
		//get routes leaving withing X time
		$routes = $db->getResult("SELECT * FROM hitch_userroutes WHERE 
			 (TIMESTAMPDIFF(MINUTE, ?, timestamp) % 10080) >= ? AND (TIMESTAMPDIFF(MINUTE, ?, timestamp) % 10080) < ?", 
			 array($hhiker->timestamp, $i*60, $hhiker->timestamp, ($i+1)*60));

		foreach($routes as $route) {
			//Get the distance from route to destination
			$d1 = getDistance($route->startPoint, $route->endPoint);
			
			//get the distance from route to hitchhiker
			$d2 = getDistance($route->startPoint, $hhiker->location);

			//get the distance from hitchhiker to route destination
			$d3 = getDistance($hhiker->location, $route->endPoint);

			//check the route deviation in percentages
			if ((($d2 + $d3) - $d1) / $d1 <= $MAX_PERC_DEVIATION) {
				//if the deviation is allowed, add the route to our matches
				$matchedRoutes[] = (object) array("userID" => $route->userID, "routeID" => $route->userrouteID, "relevance" => 100);
			}
		}
	}

	//Insert the matches made into the database
	foreach ($matchedRoutes as $match){
		$db->insertRow('hitch_matches', array($match->routeID, $hhiker->userID, date('Y-m-d H:i:s'), $match->relevance));
	}
	
	echo '{ "routes" : '.json_encode($matchedRoutes).' }';
?>