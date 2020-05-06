package main.java;

public class KMP {
    private String pat;
    private int[][] dfa;
    public int numInspections;

    public KMP(String pat) {
        // Build DFA from pattern.
        this.pat = pat;
        int M = pat.length();
        int R = 256; // Why 256? Num possible Characters. If using HashSet of arrays less space to look up chars
        dfa = new int[R][M];
        dfa[inspect(pat, 0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {  // Compute dfa[][j].
            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][X];
            }
            // Copy mismatch cases.
            dfa[inspect(pat, j)][j] = j + 1;         // Set match case.
            X = dfa[inspect(pat, j)][X];           // Update restart state.
        }
    }

    public int search(String txt) {  // Simulate operation of DFA on txt.
        int i, j, N = txt.length(), M = pat.length();
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[inspect(txt, i)][j];
        }
        if (j == M) {
            return i - M;  // found (hit end of pattern)
        } else {
            return N;      // not found (hit end of text)
        }
    }

    private char inspect(String s, int idx) {
        numInspections++;
        return s.charAt(idx);
    }

    public static void main(String[] args) {
        String pat = "bro";
        String txt = "hellohibrodudethinghumananimal";
        KMP kmp = new KMP(pat);
        System.out.println("text:    " + txt);
        int offset = kmp.search(txt);
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++)
            System.out.print(" ");
        System.out.println(pat);
    }   // See page 769.
}