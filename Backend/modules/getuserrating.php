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

	//Check if the user exists
	$user = $db->getRow("SELECT 1 FROM hitch_users WHERE userID=?", array($user_id));
	if ($user == false) {
		throwError('This user could not be found');
	}
	
	//Get data from database
	$result = $db->getRow("SELECT COALESCE(AVG(rating), 0) as 'rating', COUNT(rating) as 'numRatings' FROM hitch_ratings WHERE toUserId=?", array($user_id));
	$result->userID = $user_id;

	echo json_encode($result);	
?>