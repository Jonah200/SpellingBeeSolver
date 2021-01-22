package app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a
 */
public class Solver {
    private List<String> englishWords;

    private List<String> validWords;

    private char[] letters;

    private char centerLetter;

    private String outerLetters;

    public Solver() throws IOException, URISyntaxException {
        englishWords = new ArrayList<String>();

        englishWords = Files.readAllLines(Paths.get(this.getClass().getResource("words.txt").toURI()), Charset.defaultCharset());

        letters = new char[7];

        validWords = new ArrayList<String>();
    }

    public void addLetters(char centerLetter, String outerLetters){
        this.centerLetter = centerLetter;
        this.outerLetters = outerLetters;
        this.letters[0] = this.centerLetter;
        outerLetters.getChars(0, this.outerLetters.length(),this.letters, 1);
    }
    
}