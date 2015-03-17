<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: GetUserRatingByUser
		Input parameters:
			userID: The ID of the user giving the rating.
			subjectID: The ID of the user that has possibly received a rating.

		Output parameters:
			rating: The rating this user has given the subject, or -1 if no rating was given.

	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID']) || !isset($_GET['subjectID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['userID'];
	$subject_id = $_GET['subjectID'];

	//Check if the subject exists
	$subject = $db->getRow("SELECT 1 FROM hitch_users WHERE userID=?", array($subject_id));
	if ($subject == false) {
		throwError('The subject was not found');
	}
	
	//Get data from database
	$result = $db->getRow("SELECT rating FROM hitch_ratings WHERE byUserId=? AND toUserId=?", array($user_id, $subject_id));
	$rating = $result == false ? -1 : $result->rating;

	//Output
	echo '{ "rating" : '.$rating.' }';
?>