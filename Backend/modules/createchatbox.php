<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: CreateChatbox
		Input parameters:
			user1ID: The ID of the user making the chatbox.
			user2ID: The ID of the other user entering the chatbox.
			startPoint: The start point of an route for which chat is created.
			endPoint: The end point of an route for which chat is created.
			
		Output parameters:
			chatID: The ID of the created chatbox.
			
	*/
	
	//Check if required parameters are set
	if (!isset($_GET['user1ID']) || !isset($_GET['user2ID']) || !isset($_GET['startPoint']) || !isset($_GET['endPoint'])) {
		throwError('Missing required parameters');	
	}
	
	//Input parameters
	$user1_id = $_GET['user1ID'];
	$user2_id = $_GET['user12ID'];
	$start_point = $_GET['startPoint'];
	$end_point = $_GET['endPoint'];
	
	//Put data to database
	$db->insertRow('hitch_chatboxes', array(null, null, $start_point, $endPoint));
	$db->insertRow('hitch_chatusers', array($user1_id, $user2_id));
	
	
?>