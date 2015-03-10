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
			state: The user’s state (0=driver, 1=hitchhiker).
			hitchhikes: The number of times this user has successfully used Hitch before.
			birthdate: The user’s birthdate.
			joinedDate: The date at which this user started using Hitch.
			avatarURL: The URL of this user’s avatar.
		
		Output parameters;
			userID: The user’s ID.
		
	*/

	//Check if all required input paramters are set
	if (!isset($_GET['name']) || !isset($_GET['state']) || !isset($_GET['hitchhikes']) ||
	   !isset($_GET['birthdate']) || !isset($_GET['joinedDate']) || !isset($_GET['avatarURL'])) {
		throwError('Missing required parameters');	
	}

	//Input parameters
	$name = $_GET['name'];
	$state = $_GET['state'];
	$hitchikes = $_GET['hitchhikes'];
	$birthdate = $_GET['birthdate'];
	$joinedDate = $_GET['joinedDate'];
	$avatarURL = $_GET['avatarURL'];
		
	//DO SOMETHING

	//RETURN OUTPUT DATA AS JSON	
?>