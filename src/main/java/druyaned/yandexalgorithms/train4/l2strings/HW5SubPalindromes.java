package druyaned.yandexalgorithms.train4.l2strings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW5SubPalindromes {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static final char[] buf = new char[16];
    
    private static int readInt(Reader reader) throws IOException {
        int c;
        while ((c = reader.read()) != -1 && c != '+' && c != '-' && !('0' <= c && c <= '9')) {}
        if (c == -1) {
            throw new NumberFormatException("empty input");
        }
        buf[0] = (char)c;
        int l = 1;
        while ((c = reader.read()) != -1 && '0' <= c && c <= '9') {
            buf[l++] = (char)c;
        }
        return Integer.parseInt(new String(buf, 0, l));
    }
    
    private static final int CALC_SIZE = 2;
    private static final long MODULO[] = {(int)1e9 + 7, (int)1e9 + 9};
    private static final long ARG[] = {257, 263};
    
    private static long[][] makeArguments(int n) {
        long[][] x = new long[CALC_SIZE][n + 1];
        for (int c = 0; c < CALC_SIZE; ++c) {
            x[c][0] = 1;
            for (int i = 1; i <= n; ++i) {
                x[c][i] = (x[c][i - 1] * ARG[c]) % MODULO[c];
            }
        }
        return x;
    }
    
    private static long[][] makeHashes(int n, int[] s) {
        long[][] h = new long[CALC_SIZE][n + 1];
        for (int c = 0; c < CALC_SIZE; ++c) {
            for (int i = 1; i <= n; ++i) {
                h[c][i] = (h[c][i - 1] * ARG[c] + s[i - 1]) % MODULO[c];
            }
        }
        return h;
    }
    
    private static boolean equalSubstrings(int c, long[][] h1, long[][] h2, long[][] x,
            int l, int i1, int i2) {
        // hash = (h[c][i + l] - h[c][i] * x[c][l]) % MODULO[c]; can be negative
        if (i1 < 0 || i2 < 0) {
            return false;
        }
        long left = (h1[c][i1 + l] + h2[c][i2] * x[c][l]) % MODULO[c];
        long right = (h2[c][i2 + l] + h1[c][i1] * x[c][l]) % MODULO[c];
        return left == right;
    }
    
    private static void show(int n, int[] s1, int[] s2) {
        System.out.print(" i:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2d", i);
        }
        System.out.print("\ns1:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2c", (char)(s1[i] + 'a' - 1));
        }
        System.out.print("\ns2:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2c", (char)(s2[i] + 'a' - 1));
        }
        System.out.println();
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int MAX_LEN = (int)1e5;
        int[] s1 = new int[MAX_LEN];
        int n = 0, ch;
        while ((ch = reader.read()) != -1 && ch != '\n') {
            s1[n++] = ch - 'a' + 1;
        }
        int[] s2 = new int[MAX_LEN];
        for (int i = 0; i < n; ++i) {
            s2[n - 1 - i] = s1[i];
        }
        long x[][] = makeArguments(n);
        long h1[][] = makeHashes(n, s1);
        long h2[][] = makeHashes(n, s2);
        show(n, s1, s2); // TODO: debug
        long cnt = 0;
        for (int i = 0; i < n; ++i) { // pivot
            int leftLen = 0, rightLen = n, midLen;
            while (leftLen < rightLen) {
                midLen = (leftLen + rightLen + 1) / 2;
                boolean allGood = true;
                for (int c = 0; c < CALC_SIZE; ++c) {
                    if (!equalSubstrings(c, h1, h2, x, midLen, i - midLen, n - 1 - i - midLen)) {
                        allGood = false;
                        break;
                    }
                }
                if (allGood) {
                    leftLen = midLen;
                } else {
                    rightLen = midLen - 1;
                }
            }
            System.out.printf("i=%d len=%d\n", i, rightLen); // TODO: debug
            cnt += rightLen + 1;
        }
        show(n, s1, s2); // TODO: debug
        for (int i = 1; i < n; ++i) { // mirror
            int leftLen = 0, rightLen = n, midLen;
            while (leftLen < rightLen) {
                midLen = (leftLen + rightLen + 1) / 2;
                boolean allGood = true;
                for (int c = 0; c < CALC_SIZE; ++c) {
                    if (!equalSubstrings(c, h1, h2, x, midLen, i - midLen, n - i - midLen)) {
                        allGood = false;
                        break;
                    }
                }
                if (allGood) {
                    leftLen = midLen;
                } else {
                    rightLen = midLen - 1;
                }
            }
            System.out.printf("i=%d len=%d\n", i, rightLen); // TODO: debug
            cnt += rightLen;
        }
        writer.write(cnt + "\n");
        System.out.print(cnt + "\n"); // TODO: debug
    }
    
}
/*
n=15
abccbaabcdcdcba
[unary]: 15
[pivot]: 1 + 4 + 1 = 6
    c d c
    a b c d c d c b a
    c d c
[mirror]: 3 + 3 = 6
    a b c c b a
    c b a a b c
total=27
*/