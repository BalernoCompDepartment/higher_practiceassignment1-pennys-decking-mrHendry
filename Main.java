import java.io.*;
import java.util.Scanner;

class Deck {
  //Record structure to store the Deck data during the program
  String name;
  double length;
  double width;
  int price;
}

class Main {
  public static void main(String[] args) {
    Deck [] decks = new Deck [6]; //array of Deck records to store the file in
    decks = readDecksFromFile();
    int menuChoice = getValidChoice();
    while (menuChoice != 4) { //while they have not entered 4 to quit keep asking for another choice
      if (menuChoice == 1) {
        displayCheapestDeck(decks);
      } else if (menuChoice == 2) {
        displayLongDecks(decks);
      } else {
        displayLargeAreaDecks(decks);
      }
      menuChoice = getValidChoice();
    } //end of the press 4 to quit loop
    Screen.display("Thank you for visiting Penny's Decking","Goodbye");
  }

public static Deck [] readDecksFromFile () {
  //function to read an array of Deck records from a comman seperated file
  Scanner fileScanner = null;
  String fileName = "Decks.csv"; //file to read the decks from
  Deck [] tempDecks = new Deck [6]; 
  int index = 0; //start storing decks in index 0 of the array
  try {
    fileScanner = new Scanner (new BufferedReader (new FileReader (fileName)));
    fileScanner.useDelimiter("[\\n,\\r,]+"); //items in file seperated by end of line or a ,
    while (fileScanner.hasNext()) {
        tempDecks[index] = new Deck();
        tempDecks[index].name = fileScanner.next();
        tempDecks[index].length = fileScanner.nextDouble();
        tempDecks[index].width = fileScanner.nextDouble();
        tempDecks[index].price = fileScanner.nextInt();
        //move on to the next index in the array for the next item in the file
        index = index + 1; 
    }
  }
  catch (FileNotFoundException error) {
    System.out.println("File not found try again please " + error);
    //if the file does not exist display error message
  }
  finally{
    if (fileScanner != null){
      //if the file was opened close the file when finished
      fileScanner.close();
    }
  }
  return tempDecks; //return the array with the data read from the file to the main program
}

public static int getValidChoice () {
  //function to get a valid number between 1 and 4 an error message is displayed and the user asked to re enter until they enter a valid value
  int choice = Keyboard.getInt("Enter 1 to find the cheapest deck\nEnter 2 to display the names of garden decks over a certain length\nEnter 3 to display the number of garden decks that are available over a certain area\nEnter 4 to quit");
  while (choice < 1 || choice > 4) {
    Screen.display("Invalid Selection","Error");
    choice = Keyboard.getInt("Enter 1 to find the cheapest deck\nEnter 2 to display the names of garden decks over a certain length\nEnter 3 to display the number of garden decks that are available over a certain area\nEnter 4 to quit");
  }
  return choice; //return the valid value entered to the main program
}

public static void displayCheapestDeck (Deck [] theList) {
 //procedure to display the name and price of the cheapest deck in an array of Deck records called theList
  int minCostIndex = 0; //initially the smallest is the first in the list
  for (int index = 1; index < theList.length; index++){ //loop for everything other than the first value in the array
    if (theList[index].price < theList[minCostIndex].price){
      //if the current value is less than the cheapest so far set the smallest to the current location
      minCostIndex = index;
    }
  }
  Screen.display(theList[minCostIndex].name + " is the cheapest deck at £" + theList[minCostIndex].price,"The Cheapest Deck");
}

public static void displayLongDecks (Deck [] theList) {
//this procedure will display any decks in the array of Deck records called theList that are as long or longer than a number entered by the user
  double userLength = getValidReal(2,15,"Please enter the minimum length of deck you are interested in");
  //this is the smallest length of deck we are looking for
  String displayMessage = "The decks over " + userLength + " long are\n\n";
  //This String is used to store matching decks to be displayed at the end of the procedure
  for (int index = 0; index < theList.length; index++){
    if (theList[index].length >= userLength) {
      //if the current decks length is the same or more than we are looking for add its name to the output
      displayMessage = displayMessage + theList[index].name +"\n";
    }
  }
  Screen.display(displayMessage,"Deck Length Check");
}

public static void displayLargeAreaDecks (Deck [] theList) {
  double userArea = getValidReal(4, 80, "Please enter the area (in metres squared) you want to see decks larger than");
  int numbeOfDecks = 0; //initialise the numbeOfDecks to 0
  for (int index = 0; index < theList.length; index ++) {
    if (theList[index].length*theList[index].width > userArea) {
      //if the current decks area (length * width) is more than the user entered then incriment the numbeOfDecks
      numbeOfDecks = numbeOfDecks + 1;
    }
  }
  Screen.display("There are " + numbeOfDecks + " over " + userArea + " metres squared","Deck Area Check");
}

public static double getValidReal (double min, double max, String msg) {
  // a function to get a valid real number between min and max parameteres it will display the msg parametere when asking for enter along with min and max allowed values
  double enteredReal = Keyboard.getReal(msg + "between " + min + " and " + max);
    while (enteredReal < min || enteredReal > max) {
      Screen.display("Invalid Selection","Error");
      enteredReal = Keyboard.getReal(msg + "between " + min + " and " + max);
  }
  return enteredReal;
}
}