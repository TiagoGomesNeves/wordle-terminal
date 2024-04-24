public class TerminalOutputHelper {
    private static final String COLOUR_GREEN = "\u001B[32m";
    private static final String COLOUR_YELLOW = "\u001B[33m";
    private static final String COLOUR_RED = "\u001B[31m";
    private static final String RESET_COLOUR = "\u001B[0m";

    public static void printCorrectLetter(char C){
        System.out.print(COLOUR_GREEN + Character.toUpperCase(C) + " " + RESET_COLOUR );
    }
    public static void printWrongLocation(char C){
        System.out.print(COLOUR_YELLOW + Character.toUpperCase(C) + " " + RESET_COLOUR );
    }
    public static void printWrongLetter(char C){
        System.out.print(COLOUR_RED + Character.toUpperCase(C) + " " + RESET_COLOUR );
    }

}
