package com.github.druyaned.yandexalgorithms.train4.l2strings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW3ZFunction {
    
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
    
    private static long[][] makeHashes(int n, long[] s) {
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
        final int MAX_LEN = (int)1e6;
        long s[] = new long[MAX_LEN + 1];
        int n = 0, ch;
        while ((ch = reader.read()) != -1 && ch != '\n') {
            s[n++] = ch;
        }
        long[][] x = makeArguments(n);
        long[][] h = makeHashes(n, s);
        int[] z = new int[n];
        for (int i = 1; i < n; ++i) {
            int leftLen = 0, rightLen = n - i, midLen;
            while (leftLen < rightLen) {
                midLen = (leftLen + rightLen + 1) / 2;
                boolean allGood = true;
                for (int c = 0; c < CALC_SIZE; ++c) {
                    if (!equalSubstrings(c, h, x, midLen, 0, i)) {
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
        writer.write(Integer.toString(z[0]));
        for (int i = 1; i < n; ++i) {
            writer.write(" " + z[i]);
        }
        writer.write('\n');
    }
    
}
