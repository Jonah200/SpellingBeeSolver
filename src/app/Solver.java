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

    public List<String> getValidWords(){
        return this.validWords;
    }

    public void allLowerCase(){
        String word;
        for (int i = 0; i < englishWords.size(); i++) {
            word = englishWords.get(i);
            englishWords.set(i, word.toLowerCase());
        }
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
    //should take a word from the remaining valid words list, and turn it into a char[]. If one of the letters of the array are the equal to one of the letters in the lettersAsChar array, then it will add on to validLetterCount.
    // If the final validLetterCount for the word is equal to the length of the word, then all of the letters are valid, and the word can be used.
    public void finalElim(){
        int validLetterCount = 0;
        char[] currentWord;
        int f = 0;
        for (int i = 0; i < this.validWords.size(); i++) {
            validLetterCount = 0;
            currentWord = this.validWords.get(i).toCharArray();
            f = 0;
            for (int j = 0; j < currentWord.length; j++) {
                if(currentWord[j] == this.lettersAsChar[f]){
                    validLetterCount++;
                    f++;
                }else{
                    f++;
                }
            }
            if(currentWord.length != validLetterCount){
                this.validWords.remove(i);
                i--;
            }
        }
        
        
    }

    public void solve() {
        this.allLowerCase();
        this.centerFilter();
        this.punctFilter();
        this.lengthFilter();
        this.finalElim();
    }
    
}