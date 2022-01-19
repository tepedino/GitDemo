package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestDataBuild {

	public AddPlace addPlacePayload(String name, String language, String address) {
		
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setName(name);
		p.setPhone_number("(+91) 983 893 3937,");
		p.setAddress(address);
		p.setWebsite("http://google.com,");
		p.setLanguage(language);

		List<String> types = new ArrayList<String>();
		types.add("shoe park");
		types.add("shoe park");
		p.setTypes(types);

		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		p.setLocation(loc);
		
		return p;
	}
	
	public String deletePlacePayload(String place_id) {
		return "{\"place_id\": \"" + place_id + "\"}";
	}
}
