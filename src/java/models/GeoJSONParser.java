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

    String jsonString;
    String address = null;
    String country = null;
    String city = null;

    public GeoJSONParser(String jsonString) {
        this.jsonString = jsonString;
    }

    public void parse() throws ParseException, NullPointerException {

        JSONParser parser = new JSONParser();

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

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
    
}
