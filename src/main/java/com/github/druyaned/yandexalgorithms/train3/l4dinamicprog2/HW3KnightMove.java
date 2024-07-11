package com.github.druyaned.yandexalgorithms.train3.l4dinamicprog2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW3KnightMove {
    
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
        int m = readInt(reader);
        int[][] dp = new int[n + 1][m + 1];
        dp[1][1] = 1;
        for (int i = 2; i <= n; ++i) {
            for (int j = 2; j <= m; ++j) {
                dp[i][j] += dp[i-1][j-2] + dp[i-2][j-1];
            }
        }
        writer.write(dp[n][m] + "\n");
    }
    
}
/*
n=4 m=5
0 1 2 3 4 5 6
1 K 0 0 0 0 0
2 0 0 1 0 0 0
3 0 1 0 0 1 0
4 0 0 0 2 0 0
5 0 0 1 0 0 3
*/
