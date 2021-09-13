import java.io.*;
import java.util.*;

public class Hangman {

    // Declare instance variables
    int size;
    String[] names;
    int wrong, guesses;
    ArrayList<Character> used;
    ArrayList<String> usedNames;
    String currentName;
    Scanner file, file2;
    int score;
    int total;
    
    // Constructor
    Hangman() {
        
        size = 0;
        score = 0;
        total = 0;
        used = new ArrayList<Character>();
        usedNames = new ArrayList<String>();
        currentName = "";
    }
    
    // Load movie names from movies.txt
    void setup() {

        // Try opening file, display error if file can't be opened
        try {

            file = new Scanner(new File("movies.txt"));
            file2 = new Scanner(new File("movies.txt"));
        }
        catch (Exception e) {

            System.out.println("Could not find list of movies.");
        }

        while (file.hasNextLine()) {

            file.nextLine();
            size++;
        }
            
        names = new String[size];
        
        // Store movie names in array names
        int index = 0;
        while (file2.hasNextLine()) {

            names[index] = file2.nextLine();
            index++;
        }
    }
    
    // Randomly generate a movie name that hasn't been chosen before
    void chooseName() {

        boolean used = false;
        Random r = new Random();
        
        // Generate a name and check if it has been used
        currentName = names[r.nextInt(size)];
        for (int a = 0; a < usedNames.size(); a++) {

            if (currentName.equals(usedNames.get(a)))
                used = true;
        }
        
        // If the name has been used, generate a new name
        while (used) {

            used = false;
            currentName = names[r.nextInt(size)];
            for (int a = 0; a < usedNames.size(); a++)
            {
                if (currentName.equals(usedNames.get(a)))
                    used = true;
            }
        }
        
        usedNames.add(currentName);
        
        // If all the movies have been guessed, display a game completion message
        if (usedNames.size() == size)
            System.out.println("You've completed the game!");
    }
    
    // Start a new round
    void playGame() {

        // Setting up the variables for the new game
        Scanner input = new Scanner(System.in);
        chooseName();
        currentName = currentName.toLowerCase();
        char guess = ' ', playAgain = ' ';
        boolean found = false, over = false, correct = false, all = true;
        wrong = 0;
        guesses = 0;
        used.clear();
        total++;
        
        // Printing the blank name, without any guesses
        for (int i = 0; i < currentName.length(); i++) {

            if ((currentName.charAt(i) >= 97 && currentName.charAt(i) <= 122) || (currentName.charAt(i) >= 48 && currentName.charAt(i) <= 57))
                System.out.print("_ ");
            else
                System.out.print(currentName.charAt(i) + " ");
        }

        System.out.println();

        for (int i = 0; i < currentName.length(); i++) {

            if (currentName.charAt(i) == 'a' || currentName.charAt(i) == 'e' || currentName.charAt(i) == 'i' || currentName.charAt(i) == 'o' || currentName.charAt(i) == 'u')
                System.out.print("x ");
            else
                System.out.print("  ");
        }

        System.out.println();
        
        // Guessing
        while (!over) {

            all = true;
            found = false;
            System.out.print("Guess: ");
            guess = input.next().charAt(0);
            if (guess == '|')
                System.exit(0);
            
            for (int j = 0; j < used.size(); j++) {

                if (guess == used.get(j))
                    found = true;
            }
            
            if (found) {

                System.out.println("You have already guessed that.");
                all = false;
            }
            else {

                correct = false;
                used.add(guess);
                guesses++;
                for (int k = 0; k < currentName.length(); k++) {

                    found = false;
                    for (int l = 0; l < used.size(); l++) {

                        if (currentName.charAt(k) == used.get(l))
                            found = true;
                    }
                    
                    if (((currentName.charAt(k) >= 97 && currentName.charAt(k) <= 122) || (currentName.charAt(k) >= 48 && currentName.charAt(k) <= 57)) && !found) {

                        all = false;
                        System.out.print("_ ");
                    }
                    else
                        System.out.print(currentName.charAt(k) + " ");
                        
                    if (currentName.charAt(k) == guess)
                        correct = true;
                }

                System.out.println();

                for (int k = 0; k < currentName.length(); k++) {

                    if (currentName.charAt(k) == 'a' || currentName.charAt(k) == 'e' || currentName.charAt(k) == 'i' || currentName.charAt(k) == 'o' || currentName.charAt(k) == 'u')
                        System.out.print("x ");
                    else
                        System.out.print("  ");
                }

                System.out.println();
                if (!correct)
                    wrong++;
            }
            
            if (wrong == 7 || all)
                over = true;
        }
        
        // Game over
        if (over) {

            if (wrong == 7) {

                System.out.println("You guessed wrong 7 times. You lose.");
                System.out.println("Movie: " + currentName);
            }
            else {

                score++;
                System.out.println("You win! Guesses: " + guesses);
            }
                
            System.out.println("Score: " + score + "/" + total);
                
            System.out.println("Play again? [y/n]");
            playAgain = input.next().charAt(0);
            
            while (playAgain != 'y' && playAgain != 'Y' && playAgain != 'n' && playAgain != 'y') {

                System.out.println("Invalid option.");
                System.out.println("Play again? [y/n]");
                playAgain = input.next().charAt(0);
            }
            
            if (playAgain == 'y' || playAgain == 'Y') {

                System.out.print('\u000C');
                playGame();
            }
            else if (playAgain == 'n' || playAgain == 'N')
                System.out.println("Ended.");
        }

        // Close scanner object
        input.close();
    }
    
    // Main method
    public static void main(String[] args) {
        
        Hangman obj = new Hangman();
        obj.setup();
        obj.playGame();
    }
}