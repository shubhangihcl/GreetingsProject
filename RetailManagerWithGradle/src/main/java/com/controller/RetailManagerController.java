package com.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Address;
import com.model.GoogleResponse;
import com.model.Result;
import com.model.Shop;
import com.service.impl.AddressConverterImpl;

@RestController
public class RetailManagerController {
	    private final static Logger LOGGER = Logger.getLogger(RetailManagerController.class.getName()); 
		Map<String,Map<String,Shop>> shopMap=new HashMap<String, Map<String,Shop>>();
	    
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/addShop",method=RequestMethod.POST)
	    public Map <String,Map<String,Shop>> addShop(@RequestParam(value="shopName") String shopName,@RequestParam(value = "addressNumber") String addressNumber,@RequestParam(value="postCode") String postCode) {
	      Shop shop=new Shop();
	      Address address =new Address();
	      shop.setName(shopName);
	      address.setNumber(addressNumber);
	      address.setPostCode(postCode);
	      shop.setAddress(address);
	      String fullAddress = shopName+","+addressNumber.toString()+","+postCode.toString();
	      try{
	    	  LOGGER.info("\nAbout to address conversion....");
			  GoogleResponse googleResponse = new AddressConverterImpl().convertToLatLong(fullAddress);
		 
	          LOGGER.info("\nCompleted address conversion.");
	      if(googleResponse.getStatus().equals("OK"))
	      {
	       for(Result result : googleResponse.getResults())
	       {
	        LOGGER.info("\nLattitude of address is :"  +result.getGeometry().getLocation().getLat());
	        LOGGER.info("\nLongitude of address is :" + result.getGeometry().getLocation().getLng());
	        shop.setLatitude(result.getGeometry().getLocation().getLat());
	        shop.setLongitude(result.getGeometry().getLocation().getLng());
		    if(shopMap.containsKey(shop.getLatitude())){
		    	LOGGER.info("\nLatitude already present");
		    	if(shopMap.get(shop.getLatitude()).containsKey(shop.getLongitude())){
		    		LOGGER.info("\nMap Entry already Present");
		    	}
		    	else{
						LOGGER.info("\nOnly Longitude Added");
						shopMap.get(shop.getLatitude()).put(shop.getLongitude(),shop);
	        	}
		    }
			else  {
				LOGGER.info("\nLatitude, Longitude is not present. Adding new entry");
				shopMap.put(shop.getLatitude(), new HashMap(){{put(shop.getLongitude(),shop);}});
			}
	       } 
	      }
	      else
	      {
	       LOGGER.info(googleResponse.getStatus());
	       LOGGER.info("\nShop Not added to the list");
	      }
	      }
	      catch(Exception e){
	    	  e.printStackTrace();
	      }
	      return shopMap;
	    }
		
		@RequestMapping(value="/getNearestShop",method=RequestMethod.GET)
		public  List<Shop> getNearestShop(@RequestParam(value="customerLatitude") String customerLatitude, @RequestParam(value="customerLongitude") String customerLongitude){
			List<Shop> nearestShops = new ArrayList<>();
			Shop shop=new Shop();
			try{
				
			  LOGGER.info("\nIn GetNearest shop\n");
			  GoogleResponse googleResponse = new AddressConverterImpl().convertFromLatLong(customerLatitude+","+customerLongitude);
			  if(googleResponse.getStatus().equals("OK"))
			  {
			   for(Result result : googleResponse.getResults())
			   {
				if(shopMap.containsKey(result.getGeometry().getLocation().getLat()) && shopMap.get(result.getGeometry().getLocation().getLat()).containsKey(result.getGeometry().getLocation().getLng()) ) {
					LOGGER.info("\nAddress is :"  +result.getFormattedAddress());
					nearestShops.add(shop);
				}
				else{
					LOGGER.info("\nShop not present in list");
				}
			   }
			  }
			  else
			  {
			   LOGGER.info(googleResponse.getStatus());
			  }
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return nearestShops;
		}
}
