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

	//FOR TESTING ONLY: AUTOMATICALLY REPLY
	$otheruser = $db->getRow("SELECT userID FROM hitch_chatusers WHERE chatID=? AND NOT userID=?", array($chat_id, $user_id));
	if ($otheruser != false) {
		$messages = array("Hey!", "Interesting", "OK");
		$db->insertRow('hitch_chatmessages', array($chat_id, $otheruser->userID, date('Y-m-d H:i:s', time() + 5), $messages[rand(0,2)]));
	}

	//Output
	echo '{}';
?>