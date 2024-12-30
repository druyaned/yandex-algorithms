package druyaned.yandexalgorithms.train4.sprint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Task2MirrorZFunction {
    
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
                h[c][i] = (h[c][i - 1] * ARG[c] + s[i]) % MODULO[c];
            }
        }
        return h;
    }
    
    private static boolean equalSubstrings(int c, long[][] h1, long[][] h2, long[][] x,
            int l, int i2) {
        // hash = (h[c][i + l] - h[c][i] * x[c][l]) % MODULO[c]; can be negative
        return (h1[c][l] + h2[c][i2 - 1] * x[c][l]) % MODULO[c] == h2[c][i2 - 1 + l];
    }
    
    private static void showS(int n, int s[]) {
        System.out.print("\ni:");
        for (int i = 1; i <= n; i++) {
            System.out.printf(" %d", i);
        }
        System.out.print("\ns:");
        for (int i = 1; i <= n; i++) {
            System.out.printf(" %d", s[i]);
        }
        System.out.println();
    }
    
    private static void showR(int n, int r[]) {
        System.out.print("\ni:");
        for (int i = 1; i <= n; i++) {
            System.out.printf(" %d", i);
        }
        System.out.print("\nr:");
        for (int i = 1; i <= n; i++) {
            System.out.printf(" %d", r[i]);
        }
        System.out.println();
    }
    
    private static void showZ(int n, int z[]) {
        System.out.print("\nz:");
        for (int i = 1; i <= n; i++) {
            System.out.printf(" %d", z[i]);
        }
        System.out.println();
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int ALPHABET_SIZE = 26;
        int n = readInt(reader);
        int s[] = new int[n + 1];
        int r[] = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            int ch = reader.read();
            if ('A' <= ch && ch <= 'Z') {
                s[i] = ch - 'A' + 1;
            } else {
                s[i] = ch - 'a' + 1 + ALPHABET_SIZE;
            }
            r[n - i + 1] = s[i];
        }
        showS(n, s); // TODO: debug
        showR(n, r); // TODO: debug
        long x[][] = makeArguments(n);
        long h1[][] = makeHashes(n, s);
        long h2[][] = makeHashes(n, r);
        int z[] = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            int leftLen = 0, rightLen = i, midLen;
            while (leftLen < rightLen) {
                midLen = (leftLen + rightLen + 1) / 2;
                boolean allGood = true;
                System.out.printf("l=%d i=%d i2=%d\n", midLen, i, n - i + 1); // TODO: debug
                for (int c = 0; c < CALC_SIZE; ++c) {
                    if (!equalSubstrings(c, h1, h2, x, midLen, n - i + 1)) {
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
            z[i] = rightLen;
        }
        showZ(n, z); // TODO: debug
        writer.write(Integer.toString(z[1]));
        for (int i = 2; i <= n; ++i) {
            writer.write(" " + z[i]);
        }
        writer.write('\n');
    }
    
}
