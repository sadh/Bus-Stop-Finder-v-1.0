package com.services.model;

public class Marker {
private String busstop;
private double latitude,longitude;
private double distance;
public Marker(String busstop, double latitude, double longitude, double distance) {
	super();
	this.busstop = busstop;
	this.latitude = latitude;
	this.longitude = longitude;
	this.distance = distance;
}
public String getBusstop() {
	return busstop;
}
public void setBusstop(String busstop) {
	this.busstop = busstop;
}
public double getLatitude() {
	return latitude;
}
public void setLatitude(double latitude) {
	this.latitude = latitude;
}
public double getLongitude() {
	return longitude;
}
public void setLongitude(double longitude) {
	this.longitude = longitude;
}
public double getDistance() {
	return distance;
}
public void setDistance(double distance) {
	this.distance = distance;
}


}
