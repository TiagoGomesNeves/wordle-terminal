import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class WordleRunner {

    /**
     * Prints out each word that has been gussed with the appropiate colour
     * see WordleUtils.printFeedback() method
     * @param results - the feedbacks of all previous guesses
     * @param guesses - the list of all the guesses
     */
    public static void printAllFeedback(ArrayList<WordleGame.Feedback []> results , ArrayList<String> guesses){
        for (int i =0; i < guesses.size(); i++){
            WordleUtils.printFeeback(results.get(i), guesses.get(i));
        }
    }

    /**
     * Initalizes the letters that has not been seen yet using the unicode value of each letter
     * @param letters - Map of letters to be initalized 
     * @return - the map of letters 
     */
    public static Map<Character,Boolean> initalizeLetters(Map<Character,Boolean> letters){
        for (int i =65; i < 91; i++){
            letters.put((char) i, false);
        }

        return letters;
    }

    public static String listLetters (Map<Character,Boolean> letters){
        String output = "";
        for (Character c: letters.keySet()){
            if (!letters.get(c)){
                output += c + " ";
            }
        }
        return output;
    }

    /**
     * Starts the wordle game, has the main logic of te game
     * @param game - game object form the WordleGame class
     */
    public static void playGame(WordleGame game){

        // Initalizes the start of the game
        game.startGame();


        Scanner scan = new Scanner(System.in);
        ArrayList<String> guesses = new ArrayList<>();
        ArrayList<WordleGame.Feedback []> results = new ArrayList<>();

        // Initalizes the letters that have not been used yet
        Map<Character,Boolean> letters = new TreeMap<>();
        initalizeLetters(letters);
        

        // Main loop that takes in a valid guess and checks if the user has won the game
        while (!game.isGameOver()){
            printAllFeedback(results, guesses);

            System.out.println("There are " + game.getNumGuessesLeft() + " guesses left");
            System.out.println("Letters not used:");

            // Prints out each letter that has not been used
            System.out.println(listLetters(letters));
            // Takes in user guess
            System.out.print("Enter a guess: ");
            String guess = scan.nextLine();

            try{
                // Checks if guess is valid, if not it catches IllegalArgumentException
                WordleGame.Feedback [] feedback = game.makeGuess(guess);
                guesses.add(guess);
                results.add(feedback);
                WordleUtils.remainingLetters(letters, guess);

            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
            System.out.println();
        }

        printAllFeedback(results, guesses);
        System.out.println("Game Over");

        if (game.hasPlayerWon()){
            System.out.println("You Won!");
        }else{
            System.out.println("You lost! The mystery word was: " + game.getMysteryWord());
        }
        scan.close();

    }
    public static void main(String[] args) {

        // Sets the list of avaliable words using the words in wordleWords.txt
        Set<String> words = WordleUtils.readWordleWords("wordleWords.txt");
        WordleGame game = new WordleGame(words);
        playGame(game); // Starts the game
        

    }
    
}
