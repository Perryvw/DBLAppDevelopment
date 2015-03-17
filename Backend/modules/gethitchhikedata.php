<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: GetHitchhikeData
		Input parameters:
			limit: The maximum number of results to be returned (Optional, default is 20).
			destination: The destination of the hitchhikers (Optional).
			location: The location of the user requesting the data (Optional).
			order: The order of the data (Optional).
				Possible values are:
				ta - Sort by time ascending. (Default)
				td - Sort by time descending.
				la - Sort by distance to location ascending (requires location parameter!).
				ld - Sort by distance to location descending (requires location parameter!).

		Output parameters:
			hitchhikers: An array of objects representing hitchhikers, each containing the following fields:
				userID: The hitchhiker’s user ID.
				location: The location of the hitchhiker.
				destination: The destination of the hitchhikers.
				timestamp: The time the hitchhike data was updated.
	
	*/
	
	//Get input parameters
	$limit = isset($_GET['limit']) ? $_GET['limit'] : 20;
	$destination = isset($_GET['destination']) ? $_GET['destination'] : false;
	$location = isset($_GET['location']) ? $_GET['location'] : false;
	$order = isset($_GET['order']) ? $_GET['order'] : 'ta';

	$orderStr = "timestamp ASC";
	if ($order == 'td') {
		$orderStr = "timestamp DESC";
	}

	$query = null;
	$result = $db->getResult("SELECT * FROM hitch_hitchhikestatus ORDER BY ".$orderStr." LIMIT 20", array($limit));

	//Output
	echo '{ "hitchhikers": '.json_encode($result).' }';	
?>