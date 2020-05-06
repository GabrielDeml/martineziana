package main.java;
import javafx.scene.paint.Stop;

import java.util.Random;

import static main.java.GFG.deBruijn;

public class Trails {
    static int NUM_TRAILS = 100;

    public static void main(String[] args) {
        // Binary Alphabet
        runTrailForAlphabet(2, "01");
        System.out.println();
        // ACTG Alphabet
        runTrailForAlphabet(4, "actg");
        System.out.println();
        // Full Alphabet
        runTrailForAlphabet(26, "abcdefghijklmnopqrstuvwxyz");
        System.out.println();
    }

    public static String generateString(int length, int alphabetSize, String alphabet){
        String text = "";
        Random random = new Random();
        for(int i = 0; i < length; i++){
            text += alphabet.charAt(random.nextInt(alphabetSize));
        }
        return text;
    }

    public static void runTrailForAlphabet(int alphabetSize, String alphabet){
        Random random = new Random();
        Stopwatch timer;
        System.out.println("Alphabet Size: "+alphabetSize+ "\t\tAlphabet: "+alphabet);
        System.out.println("prep		N		AvgTimeBrute		AvgTimeKMP		AvgBrute		AvgKMP");
        String text = "";
        for(int n = 14; n <= 21; n++){
            // Prep: variable init + text generation
            timer = new Stopwatch();

            text = generateString((int) Math.pow(2, n-1), alphabetSize, alphabet)
                    + ((n == 14) ? generateString((int) Math.pow(2, 13), alphabetSize, alphabet) : text); // we already have half of a randomly generated number, put that old number on the new number to save time.
            // text = deBruijn(n-1, 2, "01"); - StackOverflow
            String pattern = "";

            int avgComparisonsBrute = 0;
            float avgTimeBrute = 0;

            int avgComparisonsKMP = 0;
            float avgTimeKMP = 0;

            System.out.print(timer.elapsedTime() + "		");

            // run trails on text
            for(int i = 0; i <= NUM_TRAILS; i++){
                pattern = generateString(random.nextInt((int) Math.floor((double) n/2))+ (int) Math.ceil((double) n/2), alphabetSize, alphabet);
                timer = new Stopwatch();
                BruteForceSearch.search(text, pattern);
                avgTimeBrute += timer.elapsedTime();
                avgComparisonsBrute += BruteForceSearch.numInspections;


                timer = new Stopwatch();
                KMP kmp = new KMP(pattern);
                kmp.search(text);
                avgTimeKMP += timer.elapsedTime();
                avgComparisonsKMP += kmp.numInspections;
            }
            avgComparisonsBrute /= NUM_TRAILS;
            avgTimeBrute /= NUM_TRAILS;
            avgComparisonsKMP /= NUM_TRAILS;
            avgTimeKMP /= NUM_TRAILS;
            System.out.print(n + "		" + Math.round(avgTimeBrute * 1000000.0) / 100000.0 + "				" + Math.round(avgTimeKMP * 100000.0)/ 100000.0 + "			" + avgComparisonsBrute  + "			" + avgComparisonsKMP);
            System.out.println();
        }
    }
}
