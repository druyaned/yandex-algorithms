package com.github.druyaned.yandexalgorithms.train4.l2strings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4CubesInMirror {
    
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
    
    private static void show(int n, int s[], int r[]) {
        System.out.print(" i:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %d", i);
        }
        System.out.printf("\ns1:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %d", s[i]);
        }
        System.out.printf("\ns2:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %d", r[i]);
        }
        System.out.println();
    }
    
    private static final int CALC_SIZE = 2;
    private static final long MODULO[] = {(int)1e9 + 7, (int)1e9 + 9};
    private static final long ARG[] = {(int)1e6 + 3, (int)1e6 + 33};
    
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
        return (h1[c][i1 + l] + h2[c][i2] * x[c][l]) % MODULO[c] == h2[c][i2 + l];
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        readInt(reader);
        int[] s1 = new int[n + 1];
        int[] s2 = new int[n + 1]; // reversed
        for (int i = 0; i < n; ++i) {
            s1[i] = s2[n - 1 - i] = readInt(reader);
        }
        show(n, s1, s2); // TODO: debug
        long[][] x = makeArguments(n);
        long[][] h1 = makeHashes(n, s1);
        long[][] h2 = makeHashes(n, s2);
        int[] ans = new int[n];
        ans[0] = n;
        int size = 1;
        for (int l = 1, i2 = n - 2 * l; l <= n / 2; ++l, i2 -= 2) {
            boolean allGood = true;
            for (int c = 0; c < CALC_SIZE; ++c) {
                if (!equalSubstrings(c, h1, h2, x, l, 0, i2)) {
                    allGood = false;
                    break;
                }
            }
            if (allGood) {
                ans[size++] = n - l;
            }
        }
        writer.write(Integer.toString(ans[size - 1]));
        System.out.print(Integer.toString(ans[size - 1])); // TODO: debug
        for (int i = size - 1; i > 0; --i) {
            writer.write(" " + ans[i - 1]);
            System.out.print(" " + ans[i - 1]); // TODO: debug
        }
        writer.write('\n');
        System.out.print('\n'); // TODO: debug
    }
    
}
/*
input:
6 2
1 1 2 2 1 1
output:
3 5 6

input:
8 3
3 1 2 2 1 3 2 1
output:
5 8

input:
9 3
2 3 1 2 2 1 3 2 1
output:
5 9
*/