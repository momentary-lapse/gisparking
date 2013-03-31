/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GeoJSONParser {

    String address = null;
    String country = null;
    String city = null;
    JSONParser parser;

    public GeoJSONParser() {
        parser = new JSONParser();
    }

    public void parse(String jsonString) throws ParseException, NullPointerException {

        JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
        jsonObject = (JSONObject) jsonObject.get("response");
        jsonObject = (JSONObject) jsonObject.get("GeoObjectCollection");
        JSONArray jsonArray = (JSONArray) jsonObject.get("featureMember");
        jsonObject = (JSONObject) jsonArray.get(0);
        jsonObject = (JSONObject) jsonObject.get("GeoObject");
        jsonObject = (JSONObject) jsonObject.get("metaDataProperty");
        jsonObject = (JSONObject) jsonObject.get("GeocoderMetaData");
        jsonObject = (JSONObject) jsonObject.get("AddressDetails");
        jsonObject = (JSONObject) jsonObject.get("Country");
        address = jsonObject.get("AddressLine").toString();
        country = jsonObject.get("CountryName").toString();
        jsonObject = (JSONObject) jsonObject.get("AdministrativeArea");
        jsonObject = (JSONObject) jsonObject.get("Locality");
        city = jsonObject.get("LocalityName").toString();

    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
    
}
