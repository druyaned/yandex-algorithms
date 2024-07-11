package com.github.druyaned.yandexalgorithms.train3.l3dinamicprog1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW3Calculator {
    
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
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int[] dp = new int[n + 1];
        int[] prev = new int[n + 1];
        for (int i = 2; i <= n; ++i) {
            dp[i] = dp[i - 1] + 1;
            prev[i] = i - 1;
            if (i % 2 == 0 && dp[i] > dp[i / 2] + 1) {
                dp[i] = dp[i / 2] + 1;
                prev[i] = i / 2;
            }
            if (i % 3 == 0 && dp[i] > dp[i / 3] + 1) {
                dp[i] = dp[i / 3] + 1;
                prev[i] = i / 3;
            }
        }
        int[] path = new int[dp[n] + 1];
        for (int i = n, pi = 0; i > 0; i = prev[i], ++pi) {
            path[pi] = i;
        }
        writer.write(dp[n] + "\n1");
        for (int i = dp[n] - 1; i >= 0; --i) {
            writer.write(" " + path[i]);
        }
        writer.write('\n');
    }
    
}
/*
Доступные операции: '*3', '*2', '++'.
Надо из 1 получить n.
Как попасть в dp[i]? Из dp[i-1], dp[i/2] или dp[i/3].
*/
