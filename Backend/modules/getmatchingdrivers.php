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
	function getDistance($start, $end, $db) {
		//try to retrieve data from the cache first:
		$res = $db->getRow("SELECT distance FROM hitch_routes WHERE (startPoint=? AND endPoint=?) OR (endPoint=? AND startPoint=?)", array($start, $end, $end, $start));

		if ($res == false) {
			//If the data is not found in the cache, request it from the API
			$response = file_get_contents("https://maps.googleapis.com/maps/api/distancematrix/json?origins=".urlencode($start)."&destinations=".urlencode($end));
			$respObj = json_decode($response);
			if ($respObj->status == "OK") {
				//Get distance from response
				$dist = $respObj->rows[0]->elements[0]->distance->value;
				$duration = $respObj->rows[0]->elements[0]->duration->value;

				//save in the cache
				$db->insertRow('hitch_routes', array($start, $end, $dist, $duration));

				return $dist;
			} else {
				//invalid return data
				return -1;
			}
		} else {
			//returned cached data;
			return $res->distance;
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

	if ($hhiker->location == $hhiker->destination || $hhiker->location == "" || $hhikekr->destination="") {
		echo '{ "routes" : [] }';
		exit();
	}

	$matchedRoutes = array();

	//Use iterative deepening to look further back
	for ($i = 0; $i < 6 && count($matchedroutes) < 5; $i++) {
		//get routes leaving withing X time
		$routes = $db->getResult("SELECT a.*, (SELECT COALESCE(AVG(rating), 0) FROM hitch_ratings b WHERE b.toUserID = a.userID) as 'rating',
			(SELECT name FROM hitch_users c WHERE c.userID=a.userID) as 'ownerName' FROM hitch_userroutes a WHERE 
			(TIMESTAMPDIFF(MINUTE, ?, timestamp) % 10080) >= ? AND (TIMESTAMPDIFF(MINUTE, ?, timestamp) % 10080) < ?", 
			array($hhiker->timestamp, $i*60, $hhiker->timestamp, ($i+1)*60));

		foreach($routes as $route) {
			//Get the distance from route to destination
			$d1 = getDistance($route->startPoint, $route->endPoint, $db);
			if ($d1 == 0) $d1 = 1;
			
			//get the distance from route to hitchhiker
			$d2 = getDistance($route->startPoint, $hhiker->location, $db);

			//get the distance from hitchhiker to hitchhiker destination
			$d3 = getDistance($hhiker->location, $hhiker->destination, $db);

			//get the distance from the hitchhiker dest to route dest
			$d4 = getDistance($hhiker->destination, $route->endPoint, $db);

			//check the route deviation in percentages
			$deviation = (($d2 + $d3 + $d4) - $d1) / $d1;
			if ($deviation <= $MAX_PERC_DEVIATION) {
				//if the deviation is allowed, add the route to our matches
				$matchedRoutes[] = (object) array("userID" => $route->userID, "routeID" => $route->userrouteID, 
					"relevance" => (100-($deviation/$MAX_PERC_DEVIATION)*90), "rating" => $route->rating, "userName" => $route->ownerName,
					"routeName"=>$route->startPoint."-".$route->endPoint);
			}
		}
	}

	//order the data
	usort($matchedRoutes, function($a, $b) {
		if ($a->relevance == $b->relevance) {
			return $b->rating - $a ->rating;
		} else {
			return $b->relevance - $a->relevance;
		}
	});

	//remove old matches for this user
	$db->executeQuery("DELETE FROM hitch_matches WHERE hitchhikerID=?", array($hhiker->userID));

	//Insert the matches made into the database
	foreach ($matchedRoutes as $match){
		$db->insertRow('hitch_matches', array($match->routeID, $hhiker->userID, date('Y-m-d H:i:s'), $match->relevance));
	}
	
	echo '{ "routes" : '.json_encode($matchedRoutes).' }';
?>