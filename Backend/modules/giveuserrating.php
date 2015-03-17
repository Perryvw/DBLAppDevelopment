<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: giveUserRating
		Input parameters:
			userID: The ID of the user giving the rating.
			receiverID: The ID of the user receiving the rating.
			rating: The rating (1-5)
			comment: user comment
		
		Output parameters:
			-

	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID']) || !isset($_GET['receiverID']) || !isset($_GET['rating'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['userID'];
	$receiver_id = $_GET['receiverID'];
	$rating = $_GET['rating'];
	$comment = '';
	if(isset($_GET['comment'])){
		//url-decode the string
		$comment = urldecode($_GET['comment']);
	}
	
	//Put data to database
	$db->insertRow('hitch_ratings', array($user_id, $receiver_id, $rating, $comment));
?>