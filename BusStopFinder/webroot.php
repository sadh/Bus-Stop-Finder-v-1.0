<?php
$hostname = 'localhost';
$username = 'root';
$password = '';
$db = 'busstopinfo';
$connection = mysql_connect($hostname,$username,$password) or die ("Database Service is unavailable");
mysql_select_db($db,$connection);

function get_Busstop_Location($loc_x,$loc_y,$distance) {
    $sql = sprintf("SELECT id,name,x_pos,y_pos, ( 6371000 * acos( cos( radians('%s') ) * cos( radians( x_pos ) ) * cos( radians( y_pos ) - radians('%s') ) + sin( radians('%s') ) * sin( radians( x_pos ) ) ) ) AS distance FROM busstoppos HAVING distance < '%s' ORDER BY distance LIMIT 0 , 3",
	mysql_real_escape_string($loc_x),
	mysql_real_escape_string($loc_y),
	mysql_real_escape_string($loc_x),
	mysql_real_escape_string($distance));
	$result = mysql_query($sql);
    return formatted_result($result);
}

function formatted_result($result) {
	if($result){
		return $result;
	}else{
		return false;
	}
}