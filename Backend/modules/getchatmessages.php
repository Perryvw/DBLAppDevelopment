<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: GetChatMessages
		Input parameters:
			chatID: The ID of the chatbox to leave.
			limit: The maximum number of messages to retrieve.
			since: The date after which all messages should be returned.
			If limit is not specified, since should be and the other way around. They can also both be specified, setting the limit at whichever constraint is reached first.

		Output parameters:
			messages: An array of objects representing messages, each containing the following fields:
				userID: The user that placed the message.
				text: The text of the message.
				timestamp: The date at which the message was sent.
			
	*/
	
	//Check if required parameters are set
	if(!isset($_GET['chatID']) || !isset($_GET['since'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$chat_id = $_GET['chatID'];
	$since = $_GET['since'];
	if(isset('limit')){
		$limit = $_GET['limit'];
	}
	
	//Get data from database
	if($limit) {
		$result = json_encode($db->getResult("SELECT * FROM hitch_chatmessages WHERE timestamp > ".$since." AND chatID=? LIMIT ".$limit, array($chatID)));
	}
	else {
		$result = json_encode($db->getResult("SELECT * FROM hitch_chatmessages WHERE timestamp > ".$since." AND chatID=?", array($chatID)));
	}
	
	echo $result;
?>