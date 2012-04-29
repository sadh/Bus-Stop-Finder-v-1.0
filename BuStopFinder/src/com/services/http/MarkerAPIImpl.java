package com.services.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.services.mapper.MarkerMapper;
import com.services.mapper.MarkerMapperImpl;
import com.services.model.Marker;

public class MarkerAPIImpl implements MarkerAPI {
	private MarkerMapper mapper;
	private final String urlprefix = "http://192.168.1.10/BusStopFinder/marker.php?";
	
	public MarkerAPIImpl(){
		mapper = new MarkerMapperImpl();
	}

private String convertInputStreamToString(InputStream is) throws IOException{
	StringBuffer sb = new StringBuffer();
	while(true){
		final int ch = is.read();
		if(ch<0){
			break;
		}else{
			sb.append((char)ch);
		}
	}
	
	is.close();
	return sb.toString();
}

public List<Marker> getBusStopLocations(double latitude, double longitude)
		throws IllegalStateException, IOException, HttpException{
	HttpClient client = new DefaultHttpClient();
	HttpResponse response= null;
	try {
		URI myLocUrl = new URI(urlprefix + "lat=" + latitude + "&" + "long="
				+ longitude + "&" + "dist=" + "1000");
		HttpGet get = new HttpGet(myLocUrl);
		 response= client.execute(get);
	} catch (URISyntaxException e) {
			e.printStackTrace();
	}
	catch(ClientProtocolException e){
		e.printStackTrace();
	}catch(IOException e){
		e.printStackTrace();
	}
	
	int statuscode =response.getStatusLine().getStatusCode();
	
	switch(statuscode){
	
	case 200:
	case 201:
		InputStream is = response.getEntity().getContent();
		String json =convertInputStreamToString(is);			
		return mapper.mapFrom(json);
	
	case 401:
	case 404:
	case 500:
		throw new HttpException();
	default:
		throw new IOException();
	}
}
}
