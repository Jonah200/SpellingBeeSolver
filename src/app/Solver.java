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
        //File file = new File("words.txt");
        
        englishWords = new ArrayList<String>();

        englishWords = Files.readAllLines(Paths.get(this.getClass().getResource("/2of4brif.txt").toURI()), Charset.defaultCharset());

        englishWords.addAll(Files.readAllLines(Paths.get(this.getClass().getResource("/2of5core.txt").toURI()), Charset.defaultCharset()));

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
        String[] punct = {".",",","''","-","&","/","`","'"};
        for (int i = 0; i < this.validWords.size(); i++) {
            for(int j = 0; j < punct.length; j++){
                if(this.validWords.get(i).contains(punct[j])){
                    this.validWords.remove(i);
                    i--;
                    break;
                }
            }
        }
    }
    //removes words that are three letters or less in length
    public void lengthFilter(){
        for (int i = 0; i < this.validWords.size(); i++){
            if(this.validWords.get(i).length() <= 3 || this.validWords.get(i).length() > 20){
                this.validWords.remove(i);
                i--;
            }
        }
        
    }
    
    public void finalElim(){
        String al = "abcdefghijklmnopqrstuvwxyz";
        char[] alpha = al.toCharArray();
        int early;
        for (char c : lettersAsChar) {
            for (char d : alpha) {
                if(d == c){
                    early = al.indexOf(d);
                    alpha[early] = '0';
                    break;
                }
            }
        }
        List<Integer> invalids = new ArrayList<>();
        System.out.println(englishWords.size());
        for (String string : validWords) {
            for (char c : alpha) {
                if(string.contains(Character.toString(c))){
                    System.out.println("Left to Sort: " + validWords.indexOf(string) + " Total to Sort: " + validWords.size() + " Invalid: " + invalids.size() + " Valid: " + (validWords.size() - invalids.size()) + " current word: " + string);
                    invalids.add(validWords.indexOf(string));
                    validWords.set(validWords.indexOf(string), "invalid");
                    break;
                }
            }
        }
        for (int i = 0; i < validWords.size(); i++) {
            if(validWords.get(i).equals("invalid")){
                validWords.remove(validWords.get(i));
                System.out.println("Remaining: "+ validWords.size());
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
        System.out.println("Valid Words found: " + validWords.size());
    }
    
}