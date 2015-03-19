<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: AddUserToChat
		Input parameters:
			userID: The ID of the user joining the chatbox.
			chatID: The ID of the chatbox to join.
			
		Output parameters:
			-
			
	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID']) || !isset($_GET['chatID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['userID'];
	$chat_id = $_GET['chatID'];

	//Check if the chat exists
	$chat = $db->getRow("SELECT 1 FROM hitch_chatboxes WHERE chatID=?", array($chat_id));
	if ($chat == false) {
		throwError('This chat was not found.');
	}

	//Check if the user exists
	$user = $db->getRow("SELECT 1 FROM hitch_users WHERE userID=?", array($user_id));
	if ($user == false) {
		throwError('This user was not found.');
	}
	
	//Get data from database
	$db->insertRow('hitch_chatusers', array($chat_id, $user_id));

	//Output
	echo '{}';
?>