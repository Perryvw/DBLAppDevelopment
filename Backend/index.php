<?php
	/* 
		Hitch backend main class 
	*/

	//Require libraries
	require('database.php');

	//List of modules
	$modules = array(
		"GetUserData" => "getuserdata.php",
		"RegisterUser" => "registeruser.php",
		"GetUserHitchhikeData" => "getuserhitchhikedata.php",
		"AddUserRoute" => "adduserroute.php",
		"AddUserToChat" => "addusertochat.php",
		"CreateChatbox" => "createchatbox.php",
		"DeleteUserRoute" => "deleteuserroute.php",
		"GetChatBoxes" => "getchatboxes.php",
		"GetChatMessages" => "getchatmessages.php",
		"GetHitchhikeData" => "gethitchhikedata.php",
		"GetHitchhikeMatches" => "gethitchhikematches.php",
		"GetMatchingDrivers" => "getmatchingdrivers.php",
		"GetUserRating" => "getuserrating.php",
		"GetUserRatingByUser" => "getuserratingbyuser.php",
		"GetUserRoutes" => "getuserroutes.php",
		"GiveUserRating" => "giveuserrating.php",
		"InsertChatMessage" => "insertchatmessage.php",
		"RegisterUser" => "registeruser.php",
		"RemoveUserFromChat" => "removeuserfromchat.php",
		"RemoveUserHitchhikeData" => "removeuserhitchhikedata.php",
		"UpdateUserRoute" => "updateuserroute.php"
	);

	//Check if the func parameter is set, if not: throw an error.
	if (!isset($_GET['func'])) {
		//throw error
		throwError('func parameter not found.');
	}

	//Check if the given func parameter is one of our modules, if not throw an error.
	if (!array_key_exists($_GET['func'], $modules)) {
		//throw other error
		throwError('Module name not recognized.');
	}

	//Connect to the database
	$db = new DatabaseObject("hitch");

	//func parameter is set, and module known: load module.
	require_once('modules/'.$modules[$_GET['func']]);

	//Throw an error (in JSON format) and stop execution.
	function throwError($message = 'Internal server error', $statusCode = 500) {
		echo '{ "errorCode" : '.$statusCode.', "error" : "'.$message.'" }';
		exit;
	}
?>