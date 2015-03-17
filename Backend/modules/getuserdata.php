<?php
	/*
		Hitch back-end module.
		
		Throw an error:
		throwError( message, errorCode );
		
		Database object:
		$db
		
		Module: GetUserData
		Input parameters:
			userID: The ID of the queried user
		
		Output parameters;
			userID: The user’s ID.
			name: The user’s Hitch nickname.
			state: The user’s state (0=driver, 1=hitchhiker).
			hitchhikes: The number of times this user has successfully used Hitch before.
			birthdate: The user’s birthdate.
			joinedDate: The date at which this user started using Hitch.
			avatarURL: The URL of this user’s avatar.
	*/

	//Check if all required input paramters are set
	if (!isset($_GET['userID'])) {
		throwError('Missing required parameters');	
	}

	//Input parameters
	$user_id = $_GET['userID'];
		
	//RETURN OUTPUT DATA AS JSON	
	$result = $db->getRow("SELECT * FROM hitch_users WHERE userId=?", array($user_id));
	
	//Check result. If empty - error, else return result.
	if(empty($result)) {
		throwError('This user could not be found.');
	}
	else {
		echo json_encode($result);
	}
?>