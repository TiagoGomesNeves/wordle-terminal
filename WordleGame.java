import java.util.Set;

public class WordleGame {

    /**
     * enum of the possible feedback for each letter in the
     * guess
     */
    public enum Feedback{
        NOT_IN_WORD, RIGHT_LOCATION, WRONG_LOCATION
    }

    private Set<String> words;
    private String mysteryword;
    private int numGuessesLeft;
    private boolean playerWon;
    

    /**
     * Constructor for WorldeGame, initializes each instance variable to its default value
     * @param words - set of all possible words for the mysteryword
     */
    public WordleGame(Set<String> words){
        this.words = words;
        this.mysteryword = "";
        this.numGuessesLeft = 0;
        playerWon = false;
        
    }

    /**
     * Starts the game, initalizes the variables to to the appropiate values
     */
    public void startGame(){
        mysteryword = WordleUtils.getRandomWord(words);
        numGuessesLeft = 5;
        playerWon = false;
    }

    /**
     * checks if game is over when number of guesses is 0 or the player has won
     *
     * @return - returns true if the game is over
     */
    public boolean isGameOver(){
        return numGuessesLeft == 0 || playerWon;
    }

    /**
     * @return - the number of guesses left
     */
    public int getNumGuessesLeft(){
        return numGuessesLeft;
    }

    /**
     * @return if the player has won
     */
    public boolean hasPlayerWon(){
        return playerWon;
    }

    /**
     * @return - the mystery word
     */
    public String getMysteryWord(){
        return mysteryword;
    }

    /**
     * Takes in a guess checks if it is valid and if the user has won, also gets the feeback
     * for the word
     * @param guess - the guessd word
     * @return- the feedback for the word
     */
    public Feedback [] makeGuess(String guess){

        // Slows down the output
        try{
            Thread.sleep(150);
           }catch (InterruptedException e){
                System.exit(1);
           }

        guess = guess.toLowerCase(); // Makes word lowercase

        // Checks if the word is valid by checking if it is in the set of possible words
        if (!words.contains(guess)){
            throw new IllegalArgumentException("Guess is not valid");
        }

        numGuessesLeft--;

        // Obtains the feedback for the word
        Feedback [] feedback = WordleUtils.getFeedback(guess, mysteryword);

        // Checks if players has won
        if (guess.equals(mysteryword)){
            playerWon = true;
        }

        return feedback;
        
    }


}

