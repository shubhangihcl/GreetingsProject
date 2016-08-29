package com.service;

import java.io.IOException;

import com.model.GoogleResponse;

public interface AddressConverter {
	public GoogleResponse convertToLatLong(String fullAddress) throws IOException;
	public GoogleResponse convertFromLatLong(String latlongString) throws IOException;
	
}
