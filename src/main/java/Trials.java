package main.java;

import java.util.Random;


public class Trials {
    static int NUM_TRAILS = 1000;

    public static void main(String[] args) {
        // ACTG Alphabet
        runTrailForAlphabet(4, "actg");
        System.out.println();
        // Binary Alphabet
        runTrailForAlphabet(2, "01");
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
        System.out.println("prep		N		AvgTimeBrute		AvgTimeKMP		AvgCompBrute		AvgCompKMP		WorstCompBrute      WorstCompKMP");
        String text = "";
        for(int n = 14; n <= 21; n++){
            // Prep: variable init + text generation
            timer = new Stopwatch();

            text = generateString((int) Math.pow(2, n-1), alphabetSize, alphabet)
                    + ((n == 14) ? generateString((int) Math.pow(2, 13), alphabetSize, alphabet) : text); // we already have half of a randomly generated number, put that old number on the new number to save time.
            // text = deBruijn(n-1, 2, "01"); - StackOverflow
            String pattern = "";

            long avgComparisonsBrute = 0;
            float avgTimeBrute = 0;
            long worstComparisonsBrute = 0;

            long avgComparisonsKMP = 0;
            float avgTimeKMP = 0;
            long worstComparisonsKMP = 0;

            System.out.print(timer.elapsedTime() + "		");

            // run trails on text
            for(int i = 0; i < NUM_TRAILS; i++){
                pattern = generateString(random.nextInt(n/2) + n/3, alphabetSize, alphabet);
                timer = new Stopwatch();
                BruteForceSearch.search(text, pattern);
                avgTimeBrute += timer.elapsedTime();
                avgComparisonsBrute += BruteForceSearch.numInspections;
                worstComparisonsBrute = Math.max(worstComparisonsBrute, BruteForceSearch.numInspections);


                timer = new Stopwatch();
                KMP kmp = new KMP(pattern);
                kmp.search(text);
                avgTimeKMP += timer.elapsedTime();
                avgComparisonsKMP += kmp.numInspections;
                worstComparisonsKMP = Math.max(worstComparisonsKMP, kmp.numInspections);
            }
            avgComparisonsBrute /= NUM_TRAILS;
            avgTimeBrute /= NUM_TRAILS;
            avgComparisonsKMP /= NUM_TRAILS;
            avgTimeKMP /= NUM_TRAILS;
            System.out.print(n + "		" + Math.round(avgTimeBrute * 1000000.0) / 1000000.0 + "				" + Math.round(avgTimeKMP * 1000000.0)/ 1000000.0 + "			" + avgComparisonsBrute  + "				" + avgComparisonsKMP + "				" + worstComparisonsBrute + "				" + worstComparisonsKMP);
            System.out.println();
        }
    }
}
