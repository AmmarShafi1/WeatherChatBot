//Weather-Chatbot for IRC Chatrooms
package csIIWeatherBot;
import java.io.IOException;
import java.util.Scanner;
import org.jibble.pircbot.*;

public class bot extends PircBot{
	Scanner kb = new Scanner(System.in);
	weatherBot bot = new weatherBot();
	int menuOption = 0;
	String enteredCityName;
	String enteredState;
	String receivedMessage;
	String enteredOption;
	int enteredZipcode;
	boolean entryCompleted = false;
	public bot() {
		this.setName("myWeatherBot");
		}
	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		if (message.contains("?Weather")) {
			menu(channel);
		}
		if ((message.contains("?1"))&&((message.contains("_")==false))) {
			sendMessage(channel, "Please enter \"?1_five-digit-zipcode\"");	
			}	
		if ((message.contains("?1_"))) {
			receivedMessage = message.substring(3);
			enteredZipcode = Integer.parseInt(receivedMessage);
				try {
					sendMessage(channel,bot.zipcodeWeather(enteredZipcode));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}
		if (message.contains("?2")&&(message.contains("_")==false)) {
			sendMessage(channel, "Please enter \"?2_City Name_State Name\"");
		}
		if (message.contains("?2_")) {
			receivedMessage=message.substring(3);
			String[] parts = receivedMessage.split("_");
			enteredCityName = parts[0];
			enteredState = parts[1];
			try {
				sendMessage(channel, bot.cityStateWeather(enteredCityName, enteredState));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		if (message.contains("?3")&&(message.contains("_")==false)) {
			sendMessage(channel, "Please enter \"?3_five-digit-zipcode");
		}
		if (message.contains("?3_")) {
			receivedMessage = message.substring(3);
			String [] output = null;
			enteredZipcode = Integer.parseInt(receivedMessage);
			try {
				output = bot.zipcodePollen(enteredZipcode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendMessage(channel, output[5]);
			for(int i=0;i<5;i++) {
				sendMessage(channel, output[i]);
			}
		}
		if (message.contains("?4")&&(message.contains("_")==false)) {
			sendMessage(channel, "Please enter \"?4_City Name_State Name\"");
		}
		if (message.contains("?4_")) {
			receivedMessage=message.substring(3);
			String[] parts = receivedMessage.split("_");
			enteredCityName = parts[0];
			enteredState = parts[1];
			String [] output = null;
			try {
				output = bot.cityPollenCount(enteredCityName,enteredState);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0;i<5;i++) {
				sendMessage(channel, output[i]);
			}

		}
		if (message.contains("Hello")) {
			sendMessage(channel, "Hey "+sender+"!");
		}
		if (message.contains("Exit")) {
			quitServer();
		} 
	}
	
	public void menu(String channel) {
		sendMessage(channel, "Welcome to the Weather Application");
		sendMessage(channel,"Please Enter an Option to proceed");
		sendMessage(channel, "?1.Get Weather Using Zipcode");
		sendMessage(channel, "?2.Find Weather using City Name and State");
		sendMessage(channel, "?3.Find Pollen Count using Zipcode");
		sendMessage(channel, "?4.Find Pollen Count using City Name and State");
		sendMessage(channel, "?0.Exit");
		
	}
}
