<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: GetChatBoxes
		Input parameters:
			startpoint: The startpoint of the routes related to the chatboxes (Optional).
			endpoint: The endpoint of the routes related to the chatboxes (Optional).


		Output parameters:
			chatboxes: An array of objects representing chatboxes, each containing the following fields:
				chatID: The hitchhiker’s user ID.
				created: The date at which the chatbox was created. 

	*/
	
	//Check if required parameters are set
	if(!isset($_GET['startpoint']) || !isset($_GET['endpoint'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$startpoint = $_GET['startpoint'];
	$endpoint = $_GET['endpoint'];
	
	//Get data from database
	$result = $db->getResult("SELECT chatID, dateCreated FROM hitch_chatboxes WHERE routeStartPoint=? AND routeEndPoint=?", array($startpoint, $endpoint));
	
	//Output
	echo '{ "chatboxes" : '.json_encode($result).' }';
?>