package com.services.location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpException;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.services.http.MarkerAPIImpl;
import com.services.http.RouteNavigation;
import com.services.model.Marker;

public class BusStopLocatorActivity extends MapActivity{
	
	private LocationManager lm;
	private MapView map;
	private MapController mc;
	private BusStopMarkersOverlay bus_stop_overlays;
	private List<Overlay> mapOverlays;
	private CurrentLoctionOverlay myloc;
	private Location loc;
	private GeoPoint nearest_stop;
	private GeoPoint mylocation;
	private LocationListener ln;
	private List<Marker> busstops;
	private TextView tv;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.map_view);
		tv = (TextView) findViewById(R.id.myloc);
		map = (MapView) findViewById(R.map.MapView);
		mc = map.getController();
		mapOverlays = map.getOverlays();
		map.setBuiltInZoomControls(true);
		myloc = new CurrentLoctionOverlay(this, map);
		myloc.enableCompass();
		myloc.enableMyLocation();
		Drawable drawable = this.getResources().getDrawable(R.drawable.bus);
		bus_stop_overlays = new BusStopMarkersOverlay(drawable, this);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String location_provider = lm.getBestProvider(criteria, true);
		loc = lm.getLastKnownLocation(location_provider);
		
		ln = new LocationListener() {
			
			public void onLocationChanged(Location location) {
				loc = location;
				tv.setText("My last location: "+loc.getLatitude()+" & "+loc.getLongitude());
				changeOnUpdate();	
			}
			
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
			}
			
			public void onProviderEnabled(String provider) {
				
			}
			
			public void onProviderDisabled(String provider) {
				
			}
		};
		
		tv.setText(location_provider);
		lm.requestLocationUpdates(location_provider, 5000,(float) 10.00, ln);
		
	}
	
	/*Setting new Position and markers*/
	
	private void changeOnUpdate(){
		GeoPoint p = new GeoPoint((int) (loc.getLatitude() * 1E6), (int) (loc.getLongitude() * 1E6));
		mc.animateTo(p);
		mc.setZoom(17);
		map.invalidate();
		myloc.onLocationChanged(loc);
		final BusStopLocationExplorerTask getlocations = new BusStopLocationExplorerTask();
		getlocations.execute();
	}
	
	
	/*Creating Map Overlay*/
	private void genMapOverlays() throws Exception {
		Iterator<Marker> it = busstops.iterator();
		int i = 0;
		while (it.hasNext()) {
			GeoPoint point = new GeoPoint((int) (busstops.get(i).getLatitude() * 1E6),
					(int) (busstops.get(i).getLongitude() * 1E6));
			OverlayItem item = new OverlayItem(point, busstops.get(i).getBusstop(),
					"This is Bus Stop No:");
			bus_stop_overlays.addOverlay(item);
			i++;
		}

	}
	
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	
	/*Retrieving Bus stop locations in background*/
	private class BusStopLocationExplorerTask extends AsyncTask<Void, Void, List<Marker>> {
		
		@Override
		protected void onPostExecute(List<Marker> result) {
			super.onPostExecute(result);
			busstops = result;
			try {
				genMapOverlays();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mapOverlays.add(myloc);
			if(busstops.size()>0){
			nearest_stop = new GeoPoint((int)(busstops.get(0).getLongitude()*1e6),(int)(busstops.get(0).getLatitude()*1e6));
			mylocation = new GeoPoint((int)(loc.getLongitude()*1e6), (int)(loc.getLatitude()*1e6));
			}
			final RouteNavigationTask path_task = new RouteNavigationTask();
			path_task.execute();
			tv.setText("No of Busstop laction:"+result.size());
			mapOverlays.add(bus_stop_overlays);
			
		}

		@Override
		protected List<Marker> doInBackground(Void... params) {
			MarkerAPIImpl markers = new MarkerAPIImpl();
			try {
				return markers.getBusStopLocations(loc.getLatitude(), loc.getLongitude());
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (HttpException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	private class RouteNavigationTask extends AsyncTask<Void, Void, ArrayList<GeoPoint>>{

		
		@Override
		protected void onPostExecute(ArrayList<GeoPoint> result) {
			super.onPostExecute(result);
			PathOverlay path = new PathOverlay(result);
			mapOverlays.add(path);
			map.invalidate();
		}

		@Override
		protected ArrayList<GeoPoint> doInBackground(Void... params) {
			try {
				RouteNavigation route = new RouteNavigation(mylocation, nearest_stop);
				return route.getCoOrdinates();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
}
