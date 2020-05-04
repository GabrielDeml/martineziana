public class BruteForceSearch {
    public static int search(String text, String pattern) {
        int N = text.length();
        int M = pattern.length();

        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (text.charAt(i + j) != pattern.charAt(j))
                { break; }
            }

            if (j == M)
            { return i; }
        }

        return N;
    }
}