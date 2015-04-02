<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: GetUserRatings
		Input parameters:
			userID: The ID of the queried user.
		
		Output parameters:
			ratings: An array of objects containing the following fields:
				userName: The user giving the rating
				rating: The rating 0-5.
				comment: The textual comment for the rating

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
	$result = $db->getResult("SELECT (SELECT b.name FROM hitch_users b WHERE b.userID=a.byUserID) as 'userName', a.rating, a.comment FROM hitch_ratings a WHERE a.toUserID=?", array($user_id));

	echo '{ "ratings" : '.json_encode($result).' }';	
?>