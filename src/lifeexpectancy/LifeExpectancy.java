package lifeexpectancy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PApplet;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;



public class LifeExpectancy extends PApplet {
	UnfoldingMap map;
	Map<String,Float>lifeExpByCountry;
	List<Feature> countries = new ArrayList<Feature>();
	List<Marker> countryMarkers;
	
	public void setup() {
		size(800,600,OPENGL);
		
		map = new UnfoldingMap(this,50,50,700,500, new Google.GoogleMapProvider()); 
		
		MapUtils.createDefaultEventDispatcher(this,map);
		
		lifeExpByCountry = loadLifeExpectancyFromCSV("LifeExpectancyWorldBankModule3.csv");
		
		countries = GeoJSONReader.loadData(this,"countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		map.addMarkers(countryMarkers);
		shadeCountries();
				
	}
	public void draw() {
		map.draw();
	}
	
	private Map<String,Float> loadLifeExpectancyFromCSV(String fileName){
		
		Map<String,Float>lifeExpMap = new HashMap<String,Float>();
		String[] rows = loadStrings(fileName);
		
		for(String row : rows) {
			String[] columns = row.split(",");
			if(columns.length == 6 && !columns[5].equals("..")) {
				float value = Float.parseFloat(columns[5]);
				lifeExpMap.put(columns[4],value);
			}
		}
		
		return lifeExpMap;
	}
	
	private void shadeCountries() {
		for(Marker marker : countryMarkers) {
			String countryId = marker.getId();
			
			if(lifeExpByCountry.containsKey(countryId)) {
				float lifeExp = lifeExpByCountry.get(countryId);
				int colorLevel = (int) map(lifeExp,40,90,10,255);//40-90 = life exp range.10-255 = color range.
				marker.setColor(color (255-colorLevel,100,colorLevel));
			} else {
				marker.setColor(color(150,150,150));
			}
		}
	}
	

}
