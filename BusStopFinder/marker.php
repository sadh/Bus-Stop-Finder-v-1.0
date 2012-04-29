<?php
require_once('webroot.php');
$loc_x = $_GET["lat"];
$loc_y = $_GET["long"];
$dist = $_GET["dist"];
$coordinates = array();
$results = get_Busstop_Location($loc_x,$loc_y,$dist);
if($results){
	while($row = mysql_fetch_array($results)){
	$busstop = trim($row['name']);
	$latitude = trim($row['x_pos']);
	$longitude = trim($row['y_pos']);
	$distance = trim($row['distance']);
	$marker = array('busstop'=>$busstop,'latitude'=>$latitude,'longitude'=>$longitude,'distance'=>$distance);
	array_push($coordinates,$marker);
}
echo json_encode($coordinates);
}
mysql_close($connection);
?>