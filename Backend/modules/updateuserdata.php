<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: UpdateUserData
		Input parameters:
			userID: The user to update
			name: The user’s Hitch nickname. (Optional)
			state: The user’s state (0=driver, 1=hitchhiker). (Optional)
			hitchhikes: The number of times this user has successfully used Hitch before. (Optional)
			birthdate: The user’s birthdate. (Optional)
			joinedDate: The date at which this user started using Hitch. (Optional)
			avatarURL: The URL of this user’s avatar. (Optional)

		Output parameters:
			- (Empty object)
	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['userID'];

	//Get the user
	$user = $db->getRow("SELECT * FROM hitch_users WHERE userID=?", array($user_id));
	if ($user == false) {
		throwError('This user could not be found');
	}

	//Get all input data if set, otherwise get the original value
	$name = isset($_GET['name']) ? $_GET['name'] : $user->name;
	$state = isset($_GET['state']) ? $_GET['state'] : $user->state;
	$hitchhikes = isset($_GET['hitchikes']) ? $_GET['hitchhikes'] : $user->hitchhikes;
	$birthdate = isset($_GET['birthdate']) ? $_GET['birthdate'] : $user->birthdate;
	$joinedDate = isset($_GET['joinedDate']) ? $_GET['joinedDate'] : $user->joinedDate;
	$avatarURL = isset($_GET['avatarURL']) ? $_GET['avatarURL'] : $user->avatarURL;

	//Update the data in the database
	$db->executeQuery("UPDATE hitch_users SET name=?, state=?, hitchhikes=?, birthdate=?, joinedDate=?, avatarURL=? WHERE userID=?", 
		array($name, $state, $hitchhikes, $birthdate, $joinedDate, $avatarURL, $user_id));

	//Output
	echo '{}';
?>