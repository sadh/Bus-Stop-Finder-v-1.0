package com.services.location;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class PathOverlay extends Overlay {
	
	private ArrayList<GeoPoint> pointList;

	public PathOverlay(ArrayList<GeoPoint> pointList) {
		this.pointList = pointList; 
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		Point current = new Point();
        Path path = new Path();
        Projection projection = mapView.getProjection();
        Iterator<GeoPoint> iterator = pointList.iterator();
        if (iterator.hasNext()) {
            projection.toPixels(iterator.next(), current);
            path.moveTo((float) current.x, (float) current.y);
        } else return;
        while(iterator.hasNext()) {
            projection.toPixels(iterator.next(), current);
            path.lineTo((float) current.x, (float) current.y);
        }

        Paint pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setStrokeWidth(3.0f);
        pathPaint.setColor(Color.GREEN);
        pathPaint.setStyle(Style.STROKE);
        canvas.drawPath(path, pathPaint);
    }
	
}
	