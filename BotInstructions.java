package csIIWeatherBot;
//This file runs the weather-chat bot in the console
import java.io.IOException;
import java.util.Scanner;

public class BotInstructions {

		public static void main(String[]args) throws IOException {
			Scanner kb = new Scanner(System.in);
			weatherBot bot = new weatherBot();
			int menuOption = 0;
			String enteredCityName;
			String enteredState;
			int enteredZipcode;
			while (menuOption != 100) {
				System.out.println("Welcome to the Weather Application\nPlease Enter the Option to proceed\n1. Find Weather using a ZipCode\n2. Find Weather using City Name and State\n3. Find Pollen Count using zipcode\n4. Find Pollen Count using City Name and State\n0. Exit\n");
				menuOption = kb.nextInt();
				switch (menuOption) {
					case 1:
						System.out.print("Please enter the Zipcode: ");
						enteredZipcode = kb.nextInt();
						bot.setZipcode(enteredZipcode);
						bot.zipcodeWeather();
						System.out.println("-------------------------------------------");
						break;
					case 2:
						System.out.print("Please enter the City Name ");
						kb.nextLine();
						enteredCityName = kb.nextLine();
						System.out.print("Please enter the State Name ");
						enteredState = kb.nextLine();
						bot.setCityName(enteredCityName);
						bot.setStateName(enteredState);
						bot.cityStateWeather();
						System.out.println("-------------------------------------------");
						break;
					case 3: 
						System.out.print("Please enter the Zipcode: ");
						enteredZipcode = kb.nextInt();
						bot.setZipcode(enteredZipcode);
						bot.zipcodePollen();
						System.out.println("-------------------------------------------");
						break;
					case 4:
						System.out.print("Please enter the City Name ");
						kb.nextLine();
						enteredCityName = kb.nextLine();
						enteredCityName = enteredCityName.replaceAll("\\s", "%20");
						bot.setCityName(enteredCityName);
						System.out.print("Please enter the State Name ");
						enteredState = kb.nextLine();
						enteredState = enteredState.replaceAll("\\s", "%20");
						bot.setStateName(enteredState);
						bot.cityPollenCount();
						System.out.println("-------------------------------------------");
						break;
					case 0:
						menuOption = 100;
						System.out.print("Thank you for using the Weather Application\nNow Exiting");
						break;
					default:
						System.out.println("Please enter a valid option");
						
				}

		}
			
	}
	}

}
