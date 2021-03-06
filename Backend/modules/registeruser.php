<?php
	/*
		Hitch back-end module.
		
		Throw an error:
		throwError( message, errorCode );
		
		Database object:
		$db
		
		Module: RegisterUser
		Input parameters:
			name: The user’s Hitch nickname.
			phoneNumer: The user's phone number.
			state: The user’s state (0=driver, 1=hitchhiker).
			hitchhikes: The number of times this user has successfully used Hitch before.
			birthdate: The user’s birthdate.
			joinedDate: The date at which this user started using Hitch.
			avatarURL: The URL of this user’s avatar.
		
		Output parameters;
			userID: The user’s ID.
		
	*/

	//Check if all required input paramters are set
	if (!isset($_GET['name']) || !isset($_GET['state']) || 
		!isset($_GET['avatarURL']) || !isset($_GET['phoneNumber'])) {
		throwError('Missing required parameters');	
	}

	//Input parameters
	$name = $_GET['name'];
	$state = $_GET['state'];
	$hitchhikes = 0;
	$birthdate = isset($_GET['birthdate']) ? $_GET['birthdate'] : '1900-1-1';
	$joinedDate = isset($_GET['joinedDate']) ? $_GET['joinedDate'] : date('Y-m-d');
	$avatarURL = $_GET['avatarURL'];
	$phoneNr = isset($_GET['phoneNumber']) ? $_GET['phoneNumber'] : 0;
		
	//Insert data and get the ID back
	$user_id = $db->insertRow('hitch_users', array(NULL, $phoneNr, $state, $name, $birthdate, $joinedDate, $hitchhikes, $avatarURL));

	//insert hitchhikestatus
	$db->insertRow('hitch_hitchhikestatus', array($user_id, '1990-1-1', '', ''));

	echo '{ "userID" : '.$user_id.' }';
?>