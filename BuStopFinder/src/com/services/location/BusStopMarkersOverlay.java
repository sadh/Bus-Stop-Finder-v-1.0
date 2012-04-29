package com.services.location;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class BusStopMarkersOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context mContext;

	public BusStopMarkersOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));

	}

	public BusStopMarkersOverlay(Drawable defaultMarker, Context con) {
		super(boundCenterBottom(defaultMarker));
		mContext = con;

	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	protected OverlayItem createItem(int i) {

		return mOverlays.get(i);
	}

	public int size() {

		return mOverlays.size();
	}

	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet()+(index+1));
		dialog.show();
		
		return false;
	}

}
