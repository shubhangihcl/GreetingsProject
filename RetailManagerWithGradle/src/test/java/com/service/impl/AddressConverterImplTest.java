package com.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*; 

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;

import com.model.Address;
import com.model.Shop;
 

@RunWith(MockitoJUnitRunner.class) 
public class AddressConverterImplTest {
	private final static Logger LOGGER = Logger.getLogger(AddressConverterImpl.class.getName()); 
    
	@Mock
	Shop shop;
    
    @Mock
    Address address;
    
    @Before  
    public void setUp() {  
    	LOGGER.info("Setup");
    	shop=new Shop();
    	address = new Address();
    	shop.setName("SAMSUNG");
    	address.setNumber("1234L");
    	address.setPostCode("1234L");
    	shop.setAddress(address);
    }  
    @After  
    public void tearDown() {  
    	LOGGER.info("tearDown");
    	address=null;
        shop=null;
    }  
  
	@Test
	public void testConvertToLatLong() {
		LOGGER.info("Test Method testConvertToLatLong");
		LOGGER.info("Shop Name : " + shop.getName());
		LOGGER.info("Address Number : " + address.getNumber());
		LOGGER.info("PostCode : " + address.getPostCode());
		when (shop.getLatitude()).thenReturn("124578");
		when(shop.getLongitude()).thenReturn("124578");
		assertEquals(shop.getLatitude(), "124578");
		assertEquals(shop.getLongitude(), "124578");
	}
	@Test
	public void testConvertFromLatLong() {
		LOGGER.info("Test Method testConvertFromLatLong");
		
		LOGGER.info("Shop Name : " + shop.getName());
		LOGGER.info("Address Number : " + address.getNumber());
		LOGGER.info("PostCode : " + address.getPostCode());
		String addressNumber = "12345L";
		String postCode = "12345L";
		when(shop.getAddress().getNumber()).thenReturn(addressNumber);
		when(shop.getAddress().getPostCode()).thenReturn(postCode);
		assertEquals(shop.getAddress().getNumber(), addressNumber);
		assertEquals(shop.getAddress().getPostCode(),postCode);
	}
}
