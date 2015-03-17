<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: RemoveUserToChat
		Input parameters:
			userID: The ID of the user joining the chatbox.
			chatID: The ID of the chatbox to join.
			
		Output parameters:
			-
			
	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID']) } || !isset($_GET['chatID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['userID'];
	$chat_id = $_GET['chatID'];
	
	//Get data from database
	$db->executeQuery("DELETE FROM hitch_chatusers WHERE chatID=? AND userID=?", array($chat_id, $user_id));

	//Output
	echo '{}';
?>