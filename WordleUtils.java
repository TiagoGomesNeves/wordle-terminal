import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;



public class WordleUtils {

    
    /**
     * Creates a set of all possible words from a given text file
     * @param filename - name of the file with the words
     * @return A set of words from the file
     */
    public static Set<String> readWordleWords(String filename){
        File fileInput = new File(filename);
        Scanner scan = null;

        try{
            scan = new Scanner(fileInput);
        }catch(FileNotFoundException e){
            System.out.println("Filename " + filename +" not found");
        }

        Set<String> words = new HashSet<>();

        // Goes through each line in the txt file
        // and adds it to the set
        while (scan.hasNextLine()){
            String word = scan.nextLine();
            words.add(word);
        }

        scan.close();
        return words;

    }


    /**
     * Chooses a random word from the set of all possible words
     * @param words - the set of words
     * @return a randomly chosen word
     */
    public static String getRandomWord(Set<String> words){
        Random gen = new Random();

        // Makes the upperbound the length of the set of words, then
        // chooses a random number
        int randNum = gen.nextInt(words.size());

        Iterator<String> iter = words.iterator();

        int count =0;

        // Iterates through the list of random words until count
        // equals the randomly chosen number, and gets the word at that location
        while (iter.hasNext()){
            String word = iter.next();
            if (count == randNum){
                return word;
            }
            ++count;
        }

        return "";
    }

    /**
     * Takes in the current guess and the map of letters that have not been guessed,
     * it then sets the letters of the guess to true in the map, meaning that they have been seen before
     * 
     * @param letters - the map of letters not yet guessed
     * @param guess - the current guess 
     * @return - the updates map of letters not yet guessed
     */
    public static Map<Character, Boolean> remainingLetters(Map<Character, Boolean> letters, String guess){

        // Gets each letter of the guess and updates the map boolean value to true
        for (int i =0; i < guess.length(); i++){
            char letter = Character.toUpperCase(guess.charAt(i));
            letters.put(letter, true);
        }

        return letters;
    }

    /**
     * Generates the appropiate feed back for each letter in the guessed word, compared
     * to the mysteryword
     * @param guess - the current guess from the user
     * @param mysteryWord - the mystery word chosen
     * @return - an array of feedback for each letter in the guessed word
     */
    public static WordleGame.Feedback [] getFeedback(String guess, String mysteryWord){

        WordleGame.Feedback [] feedback = new WordleGame.Feedback [5];

        Map<Character,Integer> mysteryAmount = new HashMap<Character,Integer>();

        // Creates a map of each letter in the mystery word with the amount of times it appears in
        // the mystery word
        for (int i =0; i < mysteryWord.length(); i++){
            char letter = mysteryWord.charAt(i);
            if (mysteryAmount.containsKey(letter)){
                mysteryAmount.put(letter, mysteryAmount.get(letter) +1);
            }else{
                mysteryAmount.put(letter, 1);
            }
        }

        /*  Checks if each letter in the guessed word is in the correct location, if it is
         the feedback array at that location is changed to RIGHT_LOCATION and the map of letters
         in the mystery word decreases by 1 
        */ 
        for (int i =0; i < guess.length(); i++){
            char guessChar = guess.charAt(i);
            char mysteryChar = mysteryWord.charAt(i);

            if (mysteryChar == guessChar){
                feedback[i] = WordleGame.Feedback.RIGHT_LOCATION;
                mysteryAmount.put(guessChar, mysteryAmount.get(guessChar) -1);
            }

        }

        /*
        Checks each word that is not in the correct location, if that letter is in the mystery word and the amount
        of that letter in the map is not 0, then adds WRONG_LOCATION to the feedback array, otherwise 
        adds NOT_IN_WORD
         */ 
        for (int i =0; i < guess.length(); i++){
            char guessChar = guess.charAt(i);
            if (feedback[i] == WordleGame.Feedback.RIGHT_LOCATION){
                continue;
            }
            else{
                boolean found = false;
                for ( int j =0; j < mysteryWord.length(); j++){
                    char mysteryChar = mysteryWord.charAt(j);

                    if (mysteryChar == guessChar && mysteryAmount.get(mysteryChar) != 0){
                        found = true;
                        mysteryAmount.put(mysteryChar, mysteryAmount.get(mysteryChar) -1 );
                        break;
                    }
                }

                if (found){
                    feedback[i] = WordleGame.Feedback.WRONG_LOCATION;
                }else{
                    feedback[i] = WordleGame.Feedback.NOT_IN_WORD;
                }
            } 
            
        }
        
        return feedback;
    }


    /**
     * Prints out the letters of the guessed word with either red, yellow or green depending
     * on the guesses feedback array
     * @param feedback - the feedback array of the guessed word
     * @param guess -  the guessed word
     */
    public static void printFeeback (WordleGame.Feedback [] feedback, String guess){
       
        for (int i =0; i < feedback.length; i++){
            char letter = guess.charAt(i);
            if (feedback[i] == WordleGame.Feedback.WRONG_LOCATION){
                TerminalOutputHelper.printWrongLocation(letter);
            }else if (feedback[i] == WordleGame.Feedback.RIGHT_LOCATION){
                TerminalOutputHelper.printCorrectLetter(letter);
            }else{
                TerminalOutputHelper.printWrongLetter(letter);
            }
        }
        System.out.println();
    }
}
