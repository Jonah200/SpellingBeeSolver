package app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Object that solves the puzzle once addLetters and solve methods are used
 */
public class Solver {
    private List<String> englishWords;

    private List<String> validWords;

    private char[] lettersAsChar;

    private String[] lettersAsString;

    private String centerLetter;

    private String outerLetters;

    public Solver() throws IOException, URISyntaxException {
        englishWords = new ArrayList<String>();

        englishWords = Files.readAllLines(Paths.get(this.getClass().getResource("words.txt").toURI()), Charset.defaultCharset());

        lettersAsChar = new char[7];

        lettersAsString = new String[7];

        validWords = new ArrayList<String>();
    }
    //adds the center and outer letters to an array. 
    public void addLetters(String centerLetter, String outerLetters){
        this.centerLetter = centerLetter;
        this.outerLetters = outerLetters;
        this.centerLetter.getChars(0, 1, this.lettersAsChar, 0);
        outerLetters.getChars(0, this.outerLetters.length(),this.lettersAsChar, 1);
        for (int i = 0; i < lettersAsChar.length; i++) {
            lettersAsString[i] = Character.toString(this.lettersAsChar[i]);
        }
    }
    //finds all words that contain the center letter in the puzzle in the words.txt file, and adds them to the validWords list
    public void centerFilter(){
        for (String string : this.englishWords) {
            if(string.contains(this.centerLetter)){
                this.validWords.add(string);
            }
        }
    }
    //removes words from the validWords list that contain punctuation
    public void punctFilter(){
        String[] punct = {".",",","''","-","&","/","`"};
        for (int i = 0; i < this.validWords.size(); i++) {
            for(int j = 0; j < punct.length; j++){
                if(this.validWords.get(i).contains(punct[j])){
                    this.validWords.remove(i);
                    i--;
                }
            }
        }
    }
    //removes words that are three letters or less in length
    public void lengthFilter(){
        for (int i = 0; i < this.validWords.size(); i++){
            if(this.validWords.get(i).length() <= 3){
                this.validWords.remove(i);
                i--;
            }
        }
        
    }
    
}