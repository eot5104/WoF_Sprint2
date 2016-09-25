package wheeloffortune;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Phil O'Connell <pxo4@psu.edu>
 */
public class WheelOfFortune {

  private static final Scanner _keyboard = new Scanner(System.in);
  private static final Random _random = new Random();

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
      /* 18 */ "LOSE-A-TURN",
      /* 19 */ "$800",
      /* 20 */ "$350",
      /* 21 */ "$450",
      /* 22 */ "$700",
      /* 23 */ "$300",
      /* 24 */ "$600"
  );
  private static final int _wedgeCount = _wedges.size();

  private static int chooseRandomWedgeIndex() {
    return _random.nextInt(_wedgeCount);
  }

  private static String chooseRandomWedgeValue() {
    return _wedges.get(chooseRandomWedgeIndex());
  }

  private static final List<String> _menuChoices = Arrays.asList(
      "1. Spin the wheel",
      "2. Buy a vowel",
      "3. Solve the puzzle",
      "4. Quit the game",
      "", // 5
      "", // 6
      "", // 7
      "", // 8
      "9. Test letter input"
  );

  private static final List<String> _puzzleBank = Arrays.asList(
      "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG",
      "PENN STATE ABINGTON",
      "INFORMATION SCIENCES AND TECHNOLOGY"
  );

  private static final int _quitChoiceNumber = 4;

  private static boolean isValidMenuChoice(int choice) {
    if (choice < 1 || choice > _menuChoices.size()) {
      return false;
    }

    int index = choice - 1;
    String menuText = _menuChoices.get(index);

    return !menuText.equals("");
  }

  private static char inputLetter() {
    char letter = ' ';
    boolean finished = false;

    while (!finished) {
      System.out.print("Enter a letter: ");

      String line = _keyboard.nextLine();
      if (line.length() != 1) {
        System.out.println("Enter just one letter");
      } else {
        letter = line.charAt(0);
        if (!Character.isLetter(letter)) {
          System.out.println("That is not a letter");
        } else {
          finished = true;
        }
      }
    }

    return letter;
  }

  private static void gameMenu() {
    int choice = 0;
    String line = "";
    boolean quit = false;

    while (!quit) {
      System.out.println("\n===============================================================================");
      for (String menuChoice : _menuChoices) {
        if (!menuChoice.equals("")) {
          System.out.println(menuChoice);
        }
      }
      System.out.print("Enter choice: ");
      line = _keyboard.nextLine();
      try {
        choice = Integer.parseInt(line);
      } catch (NumberFormatException nfe) {
        System.out.println("Invalid input");
        continue;
      }

      if (!isValidMenuChoice(choice)) {
        System.out.println("Not a menu choice");
        continue;
      }

      System.out.println("You chose: " + _menuChoices.get(choice - 1));
      switch (choice) {
        case _quitChoiceNumber:
          quit = true;
          break;
        case 1:
          System.out.println("You landed on: " + chooseRandomWedgeValue());
          break;
        case 9:
          System.out.println("Your letter is: " + inputLetter());
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
