package com.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Logger;
import java.net.URL;
import org.codehaus.jackson.map.ObjectMapper;


import com.model.GoogleResponse;
import com.service.AddressConverter;


public class AddressConverterImpl implements AddressConverter{
	private final static Logger LOGGER = Logger.getLogger(AddressConverterImpl.class.getName()); 
	private static final String URL = "https://maps.googleapis.com/maps/api/geocode/json";
	@Override
	public GoogleResponse convertToLatLong(String fullAddress) throws IOException {
		URL url = new URL(URL + "?address=" + URLEncoder.encode(fullAddress, "UTF-8") + "&sensor=false");
			  LOGGER.info("\nEncoded URL : \n "+ url);
			  LOGGER.info("\nConnecting to Geocoder......");
			  URLConnection conn = url.openConnection();
			  InputStream in = conn.getInputStream() ;
			  LOGGER.info("\nConnected to Geocoder ");
			  ObjectMapper mapper = new ObjectMapper();
			  GoogleResponse response = (GoogleResponse)mapper.readValue(in,GoogleResponse.class);
			  in.close();
				
			  return response;
	}
	@Override
	public GoogleResponse convertFromLatLong(String latlongString) throws IOException {
		 URL url = new URL(URL + "?latlng=" + URLEncoder.encode(latlongString, "UTF-8") + "&sensor=false");
				  LOGGER.info("\nEncoded URL : "+ url);
				  URLConnection conn = url.openConnection();
		          LOGGER.info("\nGetting input stream : ");
				  InputStream in = conn.getInputStream() ;
				  ObjectMapper mapper = new ObjectMapper();
				  GoogleResponse response = (GoogleResponse)mapper.readValue(in,GoogleResponse.class);
				  in.close();
				
			  return response;
	}

}
