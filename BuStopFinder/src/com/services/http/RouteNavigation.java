package com.services.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import com.google.android.maps.GeoPoint;

import android.util.Log;

public class RouteNavigation {
	
	private String points;
	private ArrayList<GeoPoint> coordinates;
	
	public RouteNavigation(GeoPoint source,GeoPoint destination) throws IOException{
		double start_lat = source.getLatitudeE6()/1e6;
		double start_long = source.getLongitudeE6()/1e6;
		double stop_lat = destination.getLatitudeE6()/1e6;
		double stop_long = destination.getLongitudeE6()/1e6;
		String start = Double.toString(start_long)+","+Double.toString(start_lat);
		String stop = Double.toString(stop_long)+","+Double.toString(stop_lat);
		points = getConnection(getUrl(start, stop));
		coordinates = decodePolyline(points);
		
	}
	private String getUrl(String start, String end) {
	    //If params GeoPoint convert to lat,long string here
	    StringBuffer urlString = new StringBuffer();
	    urlString.append("http://maps.google.com/maps?f=d&hl=en");
	    urlString.append("&saddr=");// from
	    urlString.append(start);
	    urlString.append("&daddr=");// to
	    urlString.append(end);
	    urlString.append("&ie=UTF8&0&om=0&output=dragdir"); //DRAGDIR RETURNS JSON
	    Log.i("URLString", urlString.toString());
	    return urlString.toString();
	}
	
	private String getConnection(String url) throws IOException {
	    URL inUrl = new URL(url);
	    URLConnection yc = inUrl.openConnection();
	    BufferedReader in = new BufferedReader( new InputStreamReader(yc.getInputStream()));
	    String inputLine;
	    String encoded = "";
	    while ((inputLine = in.readLine()) != null)
	        encoded = encoded.concat(inputLine);
	    in.close();
	    String polyline = encoded.split("points:")[1].split(",")[0];
	    polyline = polyline.replace("\"", "");
	    polyline = polyline.replace("\\\\", "\\");
	    return polyline;
	}
	
	private static ArrayList<GeoPoint> decodePolyline(String encoded) {
	    ArrayList<GeoPoint> geopoints = new ArrayList<GeoPoint>();
	    int index = 0, len = encoded.length();
	    int lat = 0, lng = 0;
	    while (index < len) {
	        int b, shift = 0, result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lat += dlat;
	        shift = 0;
	        result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lng += dlng;
	        GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6), (int) (((double) lng / 1E5) * 1E6));
	        geopoints.add(p);
	    }
	 return geopoints;
	}
	
	public ArrayList<GeoPoint> getCoOrdinates(){
		return coordinates;
		
	}
	
	
}
