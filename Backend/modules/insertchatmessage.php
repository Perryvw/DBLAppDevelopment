<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode );
		
		Database object:
			$db
		
		Module: InsertChatMessage
		Input parameters:
			userID: The user placing the message.
			chatID: The chatbox the message is placed in.
			message: The chat message
			
		Output parameters:
			- (Empty object)
			
	*/
	
	//Check if required parameters are set
	if (!isset($_GET['userID']) || !isset($_GET['chatID']) || !isset($_GET['message'])) {
		throwError('Missing required parameters');	
	}
	
	//Input parameters
	$user_id = $_GET['userID'];
	$chat_id = $_GET['chatID'];
	$message = $_GET['message'];
	$date = date('Y-m-d H:i:s');

	//Check if the user is in this chat and the chat exists
	$check = $db->getRow("SELECT 1 FROM hitch_chatusers WHERE chatID=? AND userID=?", array($chat_id, $user_id));

	if ($check == false) {
		throwError('This user is not in this chatbox');
	}

	//Insert
	$db->insertRow('hitch_chatmessages', array($chat_id, $user_id, $date, $message));
	
	//Output
	echo '{}';
?>