package com.github.druyaned.yandexalgorithms.train4.l2strings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW1EqualityOfSubstrings {
    
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
    
    private static boolean equalSubstrings(int c, long[][] h, long[][] x, int l, int a, int b) {
        // hash = (h[c][i + l] - h[c][i] * x[c][l]) % MODULO[c]; can be negative
        long left = (h[c][a + l] + h[c][b] * x[c][l]) % MODULO[c];
        long right = (h[c][b + l] + h[c][a] * x[c][l]) % MODULO[c];
        return left == right;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int MAX_LEN = 2 * (int)1e5;
        int[] s = new int[MAX_LEN + 1];
        int n = 0, ch;
        while ((ch = reader.read()) != '\n') {
            s[n++] = ch - 'a' + 1;
        }
        long x[][] = makeArguments(n);
        long h[][] = makeHashes(n, s);
        int q = readInt(reader);
        for (int i = 0; i < q; ++i) {
            int l = readInt(reader);
            int a = readInt(reader);
            int b = readInt(reader);
            boolean allGood = true;
            for (int c = 0; c < CALC_SIZE; ++c) {
                if (!equalSubstrings(c, h, x, l, a, b)) {
                    allGood = false;
                    break;
                }
            }
            if (allGood) {
                writer.write("yes\n");
            } else {
                writer.write("no\n");
            }
        }
    }
    
}
/*
prime numbers:  999999893 999999929 999999937 1000000007 1000000009
prime numbers:  227 229 233 239 241 251 257 263 269 271 277 281 283

h_substring = (h[i + l] - (h[i]) * x[l]) % mod1

input:
abcababc
3
2 0 3
3 0 5
2 0 6
output:
yes
yes
no

12345678
abcababc
*/