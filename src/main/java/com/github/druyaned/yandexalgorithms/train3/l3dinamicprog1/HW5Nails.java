package com.github.druyaned.yandexalgorithms.train3.l3dinamicprog1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW5Nails {
    
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
        int[] nails = n < 4 ? new int[4] : new int[n + 1];
        int[] dp = n < 4 ? new int[4] : new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            nails[i] = readInt(reader);
        }
        Arrays.sort(nails, 1, n + 1);
        dp[2] = nails[2] - nails[1];
        dp[3] = dp[2] + nails[3] - nails[2];
        for (int i = 4; i <= n; ++i) {
            dp[i] = dp[i - 1] + nails[i] - nails[i - 1];
            int another = dp[i - 2] + nails[i] - nails[i - 1];
            if (dp[i] > another) {
                dp[i] = another;
            }
        }
        writer.write(dp[n] + "\n");
    }
    
}
/*
input:
6
3 13 12 4 14 6
output:
5

3 4 6 12 13 14
0 1 3  7  4  5
*/
