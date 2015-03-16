<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: getUserRating
		Input parameters:
			userID: The ID of the queried user.
		
		Output parameters:
			userID: The user’s ID.
			rating: The user’s rating.
			numRatings: The number of times this user has been rated.

	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['userID'];
	
	//Get data from database
	$result = $db->getRow("SELECT AVG(rating) as rating FROM hitch_ratings WHERE toUserId=?", array($user_id));
	
	//Check result. If empty - error, else return result.
	if(empty($result)) {
		throwError('Result Empty', 403);
	}
	else {
		echo json_encode($result);
	}
?>