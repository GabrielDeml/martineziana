package main.java;

public class BruteForceSearch {
    public static int comparisons = 0;
    public static int search(String text, String pattern) {
        comparisons = 0;
        int N = text.length();
        int M = pattern.length();

        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                comparisons++;
                if (text.charAt(i + j) != pattern.charAt(j))
                { break; }
            }
            comparisons++;
            if (j == M)
            { return i; }
        }

        return N;
    }
}