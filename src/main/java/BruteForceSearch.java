package main.java;

public class BruteForceSearch {
    public static int numInspections = 0;
    public static int search(String text, String pattern) {
        numInspections = 0;
        int N = text.length();
        int M = pattern.length();

        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (inspect(text, i+j) != inspect(pattern, j))
                { break; }
            }
            if (j == M)
            { return i; }
        }

        return N;
    }

    private static char inspect(String s, int idx) {
        numInspections++;
        return s.charAt(idx);
    }
}