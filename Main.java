

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Scanner;

public class Main {
    // Automated readability index
    public static double automatedReadabilityIndex ( int characters, int words, int sentences) {
        return (4.71 * ((double)characters / words)) + (0.5 * ((double)words / sentences)) - 21.43;
    }
    //Flesch–Kincaid readability tests
    public static double fleschKincaid(int words, int sentences, int syllables) {
        return 0.39 * ((double)words / sentences) + 11.8 * ((double)syllables / words) - 15.59;
    }

    // SMOG index
    public static double  smogIndex (int polysyllables, int sentences) {
        return 1.043 * Math.sqrt(polysyllables * (30.0 / sentences)) + 3.1291;
    }

    //Coleman–Liau index
    public static double colemanLiau (int characters, int words, int sentences) {
        double l = ((double)characters / words) * 100;
        double s = ((double) sentences / words) * 100;
        return 0.058 * l - 0.296 * s - 15.8;
    }

    public static String agesAllowed(int score) {
        String agesAllowed;

        switch (score) {
            case 1:
                agesAllowed = " (about 6 year olds).";
                break;
            case 2:
                agesAllowed = " (about 7 year olds).";
                break;
            case 3:
                agesAllowed = " (about 9 year olds).";
                break;
            case 4:
                agesAllowed = " (about 10 year olds).";
                break;
            case 5:
                agesAllowed = " (about 11 year olds).";
                break;
            case 6:
                agesAllowed = " (about 12 year olds).";
                break;
            case 7:
                agesAllowed = " (about 13 year olds).";
                break;
            case 8:
                agesAllowed = " (about 14 year olds).";
                break;
            case 9:
                agesAllowed = " (about 15 year olds).";
                break;
            case 10:
                agesAllowed = " (about 16 year olds).";
                break;
            case 11:
                agesAllowed = " (about 17 year olds).";
                break;
            case 12:
                agesAllowed = " (about 18 year olds).";
                break;
            case 13:
                agesAllowed = " (about 24 year olds).";
                break;
            default :
                agesAllowed = " (24+ year olds).";
                break;
        }
        return agesAllowed;

    }

    public static boolean checkVowel(char c) {
        return c == 'a' ||
               c == 'e' ||
               c == 'i' ||
               c == 'o' ||
               c == 'u';
    }

    //index 0 syllables, index 1 polysyllables
    public static int[] checkSyllables (String [] words) {
            int syllables = 0;
            int polysyllables = 0;
            int[] res = new int[2];


            for (String word : words) {
                boolean isAntVowel = false;
                int auxVowels = 0;
                for (int i = 0; i< word.length(); i++) {
                    String wordAux = new String(word);
                    if(checkVowel(word.charAt(i)) && !isAntVowel ) {
                        if (word.charAt(i) == 'e' && i == word.length() - 1 ) {

                        } else {
                            auxVowels++;
                            isAntVowel = true;
                        }
                    }
                    else {
                        isAntVowel = false;
                    }
                }
                if (auxVowels == 0){
                    auxVowels = 1;
                }
                syllables += auxVowels;
                if (auxVowels >= 3 ) {
                    polysyllables++;
                }
            }
            res[0] = syllables;
            res[1] = polysyllables;
            return res;
    }

    public static void checkString(String s) {
        int words = 0, sentences = 0, characters = 0, syllables, polysyllables;
        String regWords = "[\\s]";
        String regSentences = "[!?.]";

        String [] wordList = s.split(regWords);
        String [] sentenceList = s.split(regSentences);


        words = wordList.length;
        sentences = sentenceList.length;
        characters = s.replaceAll("[\\s]","").length();
        int [] sylPoly = new int[2];
        sylPoly = checkSyllables(wordList);
        syllables = sylPoly[0];
        polysyllables = sylPoly[1];

        //ARI
        double scoreARI = automatedReadabilityIndex(characters, words, sentences);
        int scoreARI_Rounded= (int) Math.ceil(scoreARI);
        String agesARI = agesAllowed(scoreARI_Rounded);

        //FK
        double scoreFK = fleschKincaid(words , sentences, syllables);
        int scoreFKrounded = (int) Math.ceil(scoreFK);
        String agesFK  = agesAllowed(scoreFKrounded);

        //SMOG
        double scoreSmog = smogIndex(polysyllables, sentences);
        int scoreSmogRounded = (int) Math.ceil(scoreSmog);
        String agesSmog = agesAllowed(scoreSmogRounded);

        //CL
        double scoreCL = colemanLiau(characters, words, sentences);
        int scoreCLrounded = (int) Math.ceil(scoreCL);
        String agesCL = agesAllowed(scoreCLrounded);


        System.out.printf("The text is:\n %s\n", s);
        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.println("Syllables: " + syllables);
        System.out.println("Polysyllables: " + polysyllables);

        System.out.println("Automated Readability Index: " + scoreARI + agesARI);
        System.out.println("Flesch–Kincaid readability tests: " + scoreFK + agesFK);
        System.out.println("Simple Measure of Gobbledygook: " + scoreSmog + agesSmog);
        System.out.println("Coleman–Liau index: " + scoreCL + agesCL);

    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void main(String[] args) {
        try {
            String s = readFileAsString(args[0]);
            checkString(s);
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }

    }
}
