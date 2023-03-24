package csIIWeatherBot; 

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.*;
public class weatherBot{
	private int zipcode;
	private String cityName, state, json, weatherF, weatherConditions, parsedCityName, parsedStateName, predominantType, treeLevel, weedLevel, grassLevel, pollenNames;
	public weatherBot() {
	}
	public weatherBot(int NewZipcode) {
		this.zipcode = NewZipcode;
	}
	public weatherBot(String newCity, String newState) {
		this.cityName = newCity;
		this.state = newState;
	}
	public void setZipcode(int newZipcode) {
		this.zipcode = newZipcode;
	}
	public void setCityName(String newCity) {
		this.cityName = newCity;
	}
	public void setStateName(String newState) {
		this.state = newState;
	}
	public String zipcodeWeather(int zipcode) throws IOException {				//get weather and conditions via zip-code
		URL url = new URL ("http://api.openweathermap.org/data/2.5/weather?zip="+zipcode+",us&units=imperial&appid=82bc65135197b00abdf21bd737fd3f9b");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");


		//Buffered Reader
		int status = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		StringBuffer data = null;
		while ((inputLine = in.readLine()) != null) {
		   data = content.append(inputLine);
		}
		in.close();
		
		
		json = content.toString();
		weatherF = parseWeatherF();		
		weatherConditions = parseWeatherCondition();		
		parsedCityName = parseCityName();
		
		return("The Temperature in "+parsedCityName+" is " + weatherF + "F with " + weatherConditions);
	}
	private String parseWeatherF() {                 //get Temp in Fahrenheit
		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		jobject = jobject.getAsJsonObject("main");
		String result = jobject.get("temp").getAsString();
		return result;
	}
	private String parseWeatherCondition() {        //get the weather conditions
		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("weather");
		jobject = jarray.get(0).getAsJsonObject();
		String result = jobject.get("description").getAsString();
		return result;
	}
	private String parseCityName() {               //get the city name
		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		String result = jobject.get("name").getAsString();
		return result;
	}
	public String cityStateWeather(String cityName, String state) throws IOException {				//get weather and conditions via city and state name
		URL url = new URL ("http://api.openweathermap.org/data/2.5/weather?q="+cityName+","+state+"&units=imperial&appid=82bc65135197b00abdf21bd737fd3f9b");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");


		//Buffered Reader
		int status = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		StringBuffer data = null;
		while ((inputLine = in.readLine()) != null) {
		   data = content.append(inputLine);
		}
		in.close();
		
		
		json = content.toString();
		weatherF = parseWeatherF();		
		weatherConditions = parseWeatherCondition();		
		cityName = parseCityName();
		
		return("The Temperature in "+cityName+" is " + weatherF + "F with " + weatherConditions);
	}
	public String[] cityPollenCount(String cityName, String state) throws IOException {		//get pollen information via city and state name
		URL url = new URL ("https://api.weatherbit.io/v2.0/current/airquality?&city="+cityName+","+state+"&key=c989f9d4d3a145ef83cba28ae5f54bac");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");


		//Buffered Reader
		int status = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		StringBuffer data = null;
		while ((inputLine = in.readLine()) != null) {
		   data = content.append(inputLine);
		}
		in.close();
		
		
		json = content.toString();
		String[] response;
		response = parsePollenInfo();

		return (response);
	}
	private String[] parsePollenInfo() {					//parses pollen json and prints information
		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("data");
	    jobject = jarray.get(0).getAsJsonObject();
		predominantType = jobject.get("predominant_pollen_type").getAsString();
		treeLevel = jobject.get("pollen_level_tree").getAsString();
		weedLevel = jobject.get("pollen_level_weed").getAsString();
		grassLevel = jobject.get("pollen_level_grass").getAsString();
		String [] response;
		response = new String[6];
		response[0]="0 = None, 1 = Low, 2 = Moderate, 3 = High, 4 = Very High";
		response[1]=("The predominant pollen type is: " + predominantType);
		response[2]=("Tree pollen level: "+treeLevel);
		response[3]=("Weed pollen level: "+weedLevel);
		response[4]=("Grass pollen level: "+grassLevel);
		/*System.out.println("0 = None, 1 = Low, 2 = Moderate, 3 = High, 4 = Very High\nThe predominant pollen type is: " + predominantType);
		System.out.println("Tree pollen level: "+treeLevel+"\nWeed pollen level: "+weedLevel+"\nGrass pollen level: "+grassLevel);*/
		return response;
	}
	public String[] zipcodePollen(int zipcode) throws IOException {		//get pollen information via zipcode
		URL url = new URL ("https://api.weatherbit.io/v2.0/current/airquality?&postal_code="+zipcode+"&country=US&key=c989f9d4d3a145ef83cba28ae5f54bac");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		//Buffered Reader
		int status = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		StringBuffer data = null;
		while ((inputLine = in.readLine()) != null) {
		   data = content.append(inputLine);
		}
		in.close();
		json = content.toString();
		parsedCityName = parseCityStateNamePollen(json);
		String[] response;
		response = parsePollenInfo();
		response[5]=("Pollen Levels for "+parsedCityName);
		return response;
		
	}
	String parseCityStateNamePollen(String jsonLine) {	//get city name from pollen (zip code) json
		JsonElement jelement = new JsonParser().parse(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();
		parsedCityName = jobject.get("city_name").getAsString();
		parsedStateName = jobject.get("state_code").getAsString();
		pollenNames = (parsedCityName+", "+parsedStateName);
		return pollenNames;
	}
}
