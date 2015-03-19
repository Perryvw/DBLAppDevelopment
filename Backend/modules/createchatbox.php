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
	$user2_id = $_GET['user2ID'];
	$start_point = $_GET['startPoint'];
	$end_point = $_GET['endPoint'];

	//get users from database
	$user1 = $db->getRow("SELECT userID FROM hitch_users WHERE userID=?", array($user1_id));
	$user2 = $db->getRow("SELECT userID FROM hitch_users WHERE userID=?", array($user2_id));

	//check if user 1 was found
	if ($user1 == false) {
		throwError('User 1 was not found.');
	}

	//check if user 2 was found
	if ($user2 == false) {
		throwError('User 2 was not found.');
	}

	//Make chatbox in database (returns chat id)
	$chat_id = $db->insertRow('hitch_chatboxes', array(null, null, $start_point, $end_point));

	//Add users to the chatbox in db
	$db->insertRow('hitch_chatusers', array($chat_id, $user1_id));
	$db->insertRow('hitch_chatusers', array($chat_id, $user2_id));
	
	//Output
	echo '{ "chatID" : '.$chat_id.' }';
?>