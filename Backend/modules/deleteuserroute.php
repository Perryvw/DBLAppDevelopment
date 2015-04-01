<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: DeleteUserRoute
		Input parameters:
			routeID: The ID of the route to be removed
		Output parameters:
			-
			
	*/
	
	//Check if required parameters are set
	if(!isset($_GET['routeID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$route_id = $_GET['routeID'];

	//Check if the route exists in our database
	$route = $db->getRow("SELECT 1 FROM hitch_userroutes WHERE userrouteID=?", array($route_id));
	if ($route == false) {
		throwError('This route could not be found.');
	}
	
	//Get data from database
	$db->executeQuery("DELETE FROM hitch_userroutes WHERE userrouteID=?", array($route_id));

	//Output
	echo '{}';
?>