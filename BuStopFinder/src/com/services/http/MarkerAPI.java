package com.services.http;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpException;

import com.services.model.Marker;

public interface MarkerAPI {
List<Marker> getBusStopLocations(double latitude,double longitude) throws IllegalStateException, IOException, HttpException;
}
