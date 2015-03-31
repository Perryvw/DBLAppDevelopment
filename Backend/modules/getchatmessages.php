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
	if(!isset($_GET['chatID']) || (!isset($_GET['since']) && !isset($_GET['limit']))) {
		throwError('Missing required parameters');
	}

	//Input parameters
	$chat_id = $_GET['chatID'];
	$since = isset($_GET['since']) ? date('Y-m-d H:i:s', $_GET['since']) : -1;
	$limit = isset($_GET['limit']) ? $_GET['limit'] : -1;

	if (!is_numeric($limit)) {
		throwError('Limit must be numeric!');
	}
	
	$result = null;

	//Check the case
	if ($since == -1) {
		//only limit is set
		$result = $db->getResult("SELECT a.userID, (SELECT name FROM hitch_users b WHERE a.userID=b.userID) as 'username', a.message as 'text', a.timestamp 
			FROM hitch_chatmessages a WHERE a.chatID=? ORDER BY a.timestamp DESC LIMIT ".$limit, array($chat_id));
	} elseif ($limit == -1) {
		//only since is set
		$result = $db->getResult("SELECT a.userID, (SELECT name FROM hitch_users b WHERE a.userID=b.userID) as 'username', a.message as 'text', a.timestamp 
			FROM hitch_chatmessages a WHERE a.chatID=? AND a.timestamp >= ? ORDER BY a.timestamp DESC", array($chat_id, $since));
	} else {
		//both are set
		$result = $db->getResult("SELECT a.userID, (SELECT name FROM hitch_users b WHERE a.userID=b.userID) as 'username', a.message as 'text', a.timestamp 
			FROM hitch_chatmessages a WHERE a.chatID=? AND a.timestamp >= ? ORDER BY a.timestamp DESC LIMIT ".$limit, array($chat_id, $since));
	}
	
	foreach ($result as $r) {
		$r->timestamp = strtotime($r->timestamp);
	}

	//output data
	echo '{ "messages" : '.json_encode($result).' }';
?>