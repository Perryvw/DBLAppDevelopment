<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: GetHitchhikeMatches
		Input parameters:
			routeID: The route ID for which mathing is returned.
			timeWindow: The amount of seconds since the match was made. (Optional, default = 7200).

		Output parameters:
			matches: An array of objects representing drivers, each containing the following fields:
				userID: The hitchhiker’s user ID.
				routeID: The routeID matched to the hitchhiker
				relevance: The internal score assigned to the match.
				timestamp: A timestamp of when the match was made.

	*/
	
	//Check if required parameters are set
	if(!isset($_GET['routeID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$route_id = $_GET['routeID'];

	//get a since time
	$since = time() - 7200;
	if(isset($_GET['timeWindow'])) {
		$since = time() - $_GET['timeWindow'];
	}

	//set the since time to a date
	$since = date('Y-m-d H:i:s', $since);

	//Get data from database
	$result = $db->getResult("SELECT routeID, hitchhikerID as 'userID', timestamp, relevance FROM hitch_matches WHERE routeID=? AND timestamp > ?", array($route_id, $since));
	
	//Check result. If empty - error, else return result.
	echo '{ "matches" : '.json_encode($result).' }';	
?>