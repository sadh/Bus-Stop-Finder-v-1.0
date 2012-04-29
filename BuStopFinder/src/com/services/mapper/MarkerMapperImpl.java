package com.services.mapper;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.services.model.Marker;

public class MarkerMapperImpl implements MarkerMapper {

	public List<Marker> mapFrom(String input) {
		Type typeofdst = new TypeToken<List<Marker>>(){}.getType();
		Gson gson = new Gson();
		return gson.fromJson(input, typeofdst);
	}

}
