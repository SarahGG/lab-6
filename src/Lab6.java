import java.util.ArrayList;
import java.util.Scanner;

/**
 * translates a sentence into pig latin
 *
 * @author Sarah Guarino
 * @version  1.0
 */
public class Lab6 {
    private static Scanner scnr = new Scanner(System.in);
    private static int firstVowel = 0;
    private static int i = 0;
    private static boolean hasLowerCase = false;
    private static boolean hasUpperCase = false;
    private static boolean doAgain = false;
    private static String userSentence = "";
    private static String endSentenceWith = "";
    private static ArrayList<String> userWords = new ArrayList<>();

    public static void main(String[] args) {
        do {
            // clears all of these values for repeat rounds
            userSentence = "";
            endSentenceWith = "";
            userWords.clear();
            i = 0;

            // asks for and then scans for a sentence to translate
            userSentence = getSentence();

            //removes period from the end of the sentence if one exists
            userSentence = trimPeriod(userSentence);

            //splits sentence into words in an ArrayList
            splitSentence();

            //checking each word
            for(i = 0; i < userWords.size(); i++) {

                // leaves anything with a special character alone, proceeds to translate anything else
                if (userWords.get(i).matches("^.*[^a-zA-Z'].*$")) {// the word remains the same, because it isn't actually a word!
                    userWords.set(i, userWords.get(i));
                } else {
                    //figures out which case the word is in, then switches to lowercase for translating
                    setCase();
                    userWords.set(i, userWords.get(i).toLowerCase());

                    //setTranslatedWord();

                    if (userWords.get(i).matches("^[aeiou].*$")) {
                        translateVowelFirst();
                    } else {
                        if(userWords.get(i).matches("^.*[aeiou].*$")) {
                            translateConsonantFirst();
                        } else {
                            userWords.set(i, (userWords.get(i) + "ay"));
                        }
                    }
                }
            }

            //compiles the translated sentence and then prints it to the console
            userSentence = getTranslatedSentence(userWords);
            System.out.println("You translated sentence: " + userSentence);

            System.out.println("\nWould you like to translate another sentence(y/n)?: ");
            checkInput();

            //clears my scanner
            scnr.nextLine();
        }
        while(doAgain);

        System.out.println("Goodbye!");
    }

    /**
     * receives a sentence from the user
     *
     * @return the string the user types in
     */
    private static String getSentence() {
        String userInput;

        System.out.print("Please enter a sentence: ");
        userInput = scnr.nextLine();

        return userInput;
    }

    /**
     * removes the period, question mark, or exclamation mark
     * at the end of the sentence, if one exists, then stores that
     * character to be added back later
     *
     * @param trimSentence the sentence you want trimmed
     * @return the trimmed sentence
     */
    private static String trimPeriod(String trimSentence) {
        // if the user's sentence ends with a . ? or !
        if (trimSentence.matches(".*[!?.]$")) {
            // the last character of the word, turned into a string and saved for later use
            endSentenceWith = Character.toString(trimSentence.charAt(trimSentence.length() - 1));

            // removes that same last character from the sentence, to be added back after translation
            trimSentence = trimSentence.substring(0, (trimSentence.length() - 1));
        }

        return trimSentence;
    }

    /**
     * splits the string into an ArrayList of words
     */
    private static void splitSentence () {
        //enhanced for loop that splits the string every place it finds a space
        for(String word : userSentence.split(" ")) {
            userWords.add(word);
        }
    }

    /**
     * analyzes the word and defines it as uppercase
     * lowercase, or title case for later translation
     */
    private static void setCase() {
        // resetting the properties every time it's called
        hasLowerCase = false;
        hasUpperCase = false;

        if (userWords.get(i).matches("^.*[a-z].*$")) { // if this word has any lower case characters
            //if in possession of an uppercase letter
            hasLowerCase = true;
        }
        if (userWords.get(i).matches("^.*[A-Z].*$")) {
            hasUpperCase = true;
        }
    }

    /**
     * translates any word that starts with a vowel
     */
    private static void translateVowelFirst() {
        String upperCase = (userWords.get(i) + "way").toUpperCase();
        String titleCase = (Character.toString(userWords.get(i).charAt(0)).toUpperCase() +
                userWords.get(i).substring(1, userWords.get(i).length()) +
                "way");
        String lowerCase = userWords.get(i) + "way";

        userWords.set(i, getWordWithCase(upperCase, titleCase, lowerCase));
    }

    /**
     * translates any word that starts with a consonant
     */
    private static void translateConsonantFirst() {
        int j = 0;

        for(j = 0; j < userWords.get(i).length(); j++) {
            if(Character.toString(userWords.get(i).charAt(j)).matches("^.*[aeiou].*$")) {
                firstVowel = j;
                break; // breaks once the first vowel is found
            }
        }

        String upperCase = (userWords.get(i).substring(firstVowel, userWords.get(i).length()) +
                userWords.get(i).substring(0, firstVowel).trim() +
                "ay").toUpperCase();
        String titleCase = Character.toString(userWords.get(i).charAt(firstVowel)).toUpperCase() +
                userWords.get(i).substring((firstVowel + 1), userWords.get(i).length()) +
                userWords.get(i).substring(0, firstVowel) +
                "ay";
        String lowerCase = userWords.get(i).substring(firstVowel, userWords.get(i).length()) +
                userWords.get(i).substring(0, firstVowel).trim() +
                "ay";

        System.out.println("0 "
                + (firstVowel - 1) + " - "
                + firstVowel + " "
                + (userWords.get(i).length() - 1));

        userWords.set(i, getWordWithCase(upperCase, titleCase, lowerCase));
    }

    /**
     * figures out if a word is meant to be
     * uppercase, lowercase, or titlecase
     * returns the appropriate value
     *
     * @param upperCase value if word is meant to be uppercase
     * @param titleCase  value if word is meant to be titlecase
     * @param lowerCase value if word is meant to be lowercase
     * @return returns word with appropriate case and translation
     */
    private static String getWordWithCase(String upperCase, String titleCase, String lowerCase) {
        String translatedWord;

        // Titlecase
        if ((hasUpperCase) && (hasLowerCase)) {
            translatedWord = titleCase;
        } else if (hasUpperCase) { // Uppercase
            translatedWord = upperCase;
        } else { //  Lowercase
            translatedWord = lowerCase;
        }

        return translatedWord;
    }

    /**
     * adds each word back into sentence form
     * trims the extra space off the end and
     * adds the sentence punctuation at the end
     *
     * @param pigLatinArray the array where your translated words are stored
     * @return the translated sentence
     */
    private static String getTranslatedSentence(ArrayList<String> pigLatinArray) {
        //resets my i counter and my user sentence
        i = 0;
        String pigLatinSentence = "";

        //re-compiles the sentence word for word
        for (i = 0; i < pigLatinArray.size(); i++) {
            pigLatinSentence += pigLatinArray.get(i) + " ";
        }
        //trims off the extra space
        pigLatinSentence = pigLatinSentence.trim();
        //adds the ending punctuation back
        pigLatinSentence += endSentenceWith;

        return pigLatinSentence;
    }

    /**
     * asks user if they want to continue and then checks their input
     * for yes, no, a test result, or an incorrect input
     */
    private static void checkInput() {
        boolean correctInput;
        char userContinueCheck;

        do {
            // collects input the first character (a-z regardless of case)
            // switches to lowercase
            userContinueCheck = Character.toLowerCase(scnr.next().charAt(0));

            if (userContinueCheck == 'y') {
                correctInput = true;
                doAgain = true;
            }
            else if (userContinueCheck == 'n') {
                correctInput = true;
                doAgain = false;
            }
            else {
                System.out.println("Incorrect input, please try again!");
                correctInput = false;
            }
        }
        // only repeats if user input y or n
        while(!correctInput);
    }
}
