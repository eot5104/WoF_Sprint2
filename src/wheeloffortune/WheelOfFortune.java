/*
* CMPSC 261, Section 1
* Fall 2016
* Instructor: Phil O'Connell
* Student: Elaine Tang
* ID: eot5104
*/

package wheeloffortune;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Phil O'Connell <pxo4@psu.edu>
 */
public class WheelOfFortune {
  private static char letteruno; //declare variable letter
  private static char letterdos;
  private static char solvepuzzle;

  // To read from the keyboard
  private static final Scanner _keyboard = new Scanner(System.in);
  private static int Win = 0;
  // Used to get random values for puzzle and wheel
  private static final Random _random = new Random();

  // True if we want to show all letters
  private static boolean revealLetters = false;

  /*
  * These are the wedges that are part of the wheel.
  * There are 24.  Some values can appear more than once
  */
  private static final List<String> _wedges = Arrays.asList(
      /* 01 */"$5000",
      /* 02 */ "$600",
      /* 03 */ "$500",
      /* 04 */ "$300",
      /* 05 */ "$500",
      /* 06 */ "$800",
      /* 07 */ "$550",
      /* 08 */ "$400",
      /* 09 */ "$300",
      /* 10 */ "$900",
      /* 11 */ "$500",
      /* 12 */ "$300",
      /* 13 */ "$900",
      /* 14 */ "BANKRUPT",
      /* 15 */ "$600",
      /* 16 */ "$400",
      /* 17 */ "$300",
      /* 18 */ "LOSE A TURN",
      /* 19 */ "$800",
      /* 20 */ "$350",
      /* 21 */ "$450",
      /* 22 */ "$700",
      /* 23 */ "$300",
      /* 24 */ "$600"
  );

  /*
  * The number of wedges will not change throughout the game
  * We can cache the value so we're not calling .size() over and over
  */
  private static final int _wedgeCount = _wedges.size();

  private static String chooseRandomWedgeValue() {
    // Choose a random index
    int randomWedgeIndex = _random.nextInt(_wedgeCount);
  

      if(_wedges.get(randomWedgeIndex) != "LOSE A TURN" && _wedges.get(randomWedgeIndex) != "BANKRUPT"){
        Win += Integer.parseInt(_wedges.get(randomWedgeIndex).replace("$", ""));
    }
    else if (_wedges.get(randomWedgeIndex) == "BANKRUPT")
    {
        Win = 0;
    }
    // Return the corresponding wedge
    return _wedges.get(randomWedgeIndex);
  }

  // The menu choices
  private static final List<String> _menuChoices = Arrays.asList(
      "1. Spin the wheel",
      "2. Buy a vowel",
      "3. Solve the puzzle",
      "4. Quit the game"
       );
  private static final int _quitChoiceNumber = 4;

  // The different puzzles to choose from
  private static final List<String> _puzzles = Arrays.asList(
      "THIS JAWN IS LIT",
      "PLAY THIS GAME PLEASE",
      "JUSTICE FOR HARAMBE"
  );

  /*
  * The number of puzzles will not change throughout the game
  * We can cache the value so we're not calling .size() over and over
  */
  private static final int _puzzlesCount = _puzzles.size();

  /*
  * We will store the guessed letters in a hash map.
  * The "key" will be the character that was guessed
  * The "value" will be true/false
  *
  * Actually, the "value" aspect of this is not relevant.
  * Just the fact that a letter appears in the map as a key, is enough to imply
  * it was guessed.
  */
  private static Map<Character, Boolean> guessedLetters = new HashMap<>();

  /*
  * Given a puzzle, return a masked version, with hidden letters
  */
  private static String maskPuzzle(String puzzle, boolean revealLetters) {
    // Use a string builder, since Java strings are immutable
    StringBuilder maskedPuzzle = new StringBuilder();

    // For each letter in the puzzle
    for (int i = 0; i < puzzle.length(); i++) {
      // Current letter
      char c = puzzle.charAt(i);
      
    
      /*
      * Either we're revealing all letters, or we've already guessed the
      * letter
      */
      boolean isLetterGuessed = revealLetters || guessedLetters.containsKey(c);

      /*
      * If the letter is not blank (we don't mask blanks), and the letter
      * has not been guessed, then we will mask it.
      */
      if (c != ' ' && !isLetterGuessed){
        c = '_';
      }
      
      // Put one space after each character (even a space) in the puzzle
      maskedPuzzle.append(c + " ");
    }

    // Convert the string builder to a string and return it
    return maskedPuzzle.toString();
  }

  // Choose a random puzzle
  private static String chooseRandomPuzzle() {
    // Choose a random puzzle index
    int randomPuzzleIndex = _random.nextInt(_puzzlesCount);

    //Return the corresponding puzzle
    return _puzzles.get(randomPuzzleIndex);
  }

  // Determine if the given number choice actually appears on the menu
  private static boolean isValidMenuChoice(int choice) {
    if ((choice < 1) || (choice > _menuChoices.size())) {
      return false;
    }

    // Subtrace 1 because arrays/lists are zero-based
    int index = choice - 1;
    String menuText = _menuChoices.get(index);

    return !menuText.equals("");
  }

  // Input a letter from the keyboard
  private static char inputLetter() {
    char letter = ' ';    
       
    boolean finished = false;

    while (!finished) {
      System.out.print("Enter a letter: ");

      String line = _keyboard.nextLine();
      if (line.length() != 1) {
        System.out.println("Enter just one letter");
      } else {
        // Convert letter to upper case
        letter = Character.toUpperCase(line.charAt(0));
        if (!Character.isLetter(letter)) {
          System.out.println("That is not a letter");
        } else {
          // Will exit the loop
          finished = true;
        }
      }
    }

    return letter;
  }

  // Display the game menu, and handle the choices made
  private static void gameMenu() {
    // Choice from the menu
    int choice = 0;
    
                                
    // Line entered from keyboard
    String line = "";

    // True when user wants to quit
    boolean quit = false;

    // Choose one of the puzzles at random
    String puzzle = chooseRandomPuzzle();

    // Repeat the menu until the user chooses to quit
    while (!quit) {
      System.out.println("\n                      ======================");
      System.out.println("\n                      =  Wheel Of Fortune  =");
      System.out.println("\n                      ======================");
      System.out.println("\n                                            ");

      System.out.println(maskPuzzle(puzzle, revealLetters));
      System.out.println();
      System.out.println("You Won: $" + Win);
      

      // Loop over the menu choices, and display each one
      for (String menuChoice : _menuChoices) {
        // Skip blank place-holder choices
        if (!menuChoice.equals("")) {
          System.out.println(menuChoice);
        }
      }
      System.out.print("Enter choice: ");
      line = _keyboard.nextLine();
      try {
        // If the input was not an integer, then that error will be caught
        choice = Integer.parseInt(line);
      } catch (NumberFormatException nfe) {
        // Error message, then go to the top of the loop
        System.out.println("Invalid input");
        continue;
      }

      // If not valid, then go back to the top of the loop
      if (!isValidMenuChoice(choice)) {
        System.out.println("Not a menu choice");
        continue;
      }

      System.out.println("You chose: " + _menuChoices.get(choice - 1));
      switch (choice) {
        case _quitChoiceNumber:
          // This will allow us to leave the menu loop
          quit = true;
          break;

        case 1: // Spin the wheel
          System.out.println("You landed on: " + chooseRandomWedgeValue());
          letteruno = inputLetter();
          
          if(letteruno =='a' || letteruno =='A' || letteruno == 'e' || letteruno == 'E' || 
             letteruno =='i' || letteruno =='I' || letteruno =='o' || letteruno == 'O' || 
             letteruno =='u' || letteruno =='U') //display vowels 
          {
              System.out.print("This is a vowel \n");
                     
          }
          else 
          {
              System.out.print("This is not a Vowel");
              guessedLetters.put(letteruno, true);
          }
          
          System.out.println("\nYour letter is: " + letteruno);
          
          break;
        case 2: //buy vowel
            letterdos = inputLetter();
          
          if(letterdos =='a' || letterdos =='A' || letterdos == 'e' || letterdos == 'E' ||
             letterdos =='i' || letterdos =='I' || letterdos =='o' || letterdos == 'O' || 
             letterdos =='u' || letterdos =='U')
          {
              
              if (Win >= 250) //allows only vowels to be purchased 
              {
                  System.out.print("You bought vowel: \n" + letterdos);//lets users know they bought a vowel
                  Win = Win-250;//subtracts $250 when a vowel is bought
              }
              guessedLetters.put(letterdos,true); //lets users know that the letter they chose is a vowel
          }
          else
          {
              System.out.print("You can only buy vowels");//lets users know that you can only BUY vowels
          }
          break;
   
    
      }
    }
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    gameMenu();
  }

}
