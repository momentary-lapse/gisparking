/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.IOException;
import java.net.MalformedURLException;
import org.json.simple.parser.ParseException;



public class Geocoder {

    GeoJSONParser parser;
    
    
    public Geocoder() {
        parser = new GeoJSONParser();
    }
    
    public void geocode(double lat, double lng) throws MalformedURLException, IOException, ParseException {
        
        UrlReader urlReader = new UrlReader("http://geocode-maps.yandex.ru/1.x/?format=json&geocode=" + lng + "," + lat + "&results=1");
        String jsonString = urlReader.read();
        
        parser.parse(jsonString);

        
    }
    
    public String getAddress() {
        return parser.getAddress();
    }
    
    public String getCountry() {
        return parser.getCountry();
    }
    
    public String getCity() {
        return parser.getCity();
    }
    
    
}
