package com.github.druyaned.yandexalgorithms.train4.l4bruteforce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW2LostWorld {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        switch (n) {
            case 0, 2, 3 -> { writer.write("0\n"); return; }
            case 1 -> { writer.write("1\n"); return; }
        }
        boolean[] ur = new boolean[n]; // used rows
        boolean[] uc = new boolean[n]; // used columns
        boolean[] up = new boolean[2 * n - 1]; // used diagolans "plus":  ind = r + c
        boolean[] um = new boolean[2 * n - 1]; // used diagolans "minus": ind = r - c + n - 1
        int cnt = 0;
        int[] stack = new int[n]; // positions of each dinosaur
        for (int start = 0; start < n; start++) {
            stack[0] = start;
            ur[0] = uc[start] = up[start] = um[n - start - 1] = true;
            for (int i = 1; i != 0; ) {
                if (stack[i] == 0) { // not visited
                    stack[i] = n * (stack[i - 1] / n + 1);
                } else if (stack[i] >= n * (i + 1)) {
                        stack[i--] = 0;
                        int r = stack[i] / n;
                        int c = stack[i] % n;
                        int p = r + c;
                        int m = n - c + r - 1;
                        if (ur[r] && uc[c] && up[p] && um[m]) {
                            ur[r] = uc[c] = up[p] = um[m] = false;
                        }
                        stack[i]++;
                } else {
                    int r = stack[i] / n;
                    int c = stack[i] % n;
                    int p = r + c;
                    int m = n - c + r - 1;
                    if (!ur[r] && !uc[c] && !up[p] && !um[m]) {
                        if (i == n - 1) {
                            cnt++;
                            stack[i]++;
                        } else {
                            ur[r] = uc[c] = up[p] = um[m] = true;
                            i++;
                        }
                    } else {
                        stack[i]++;
                    }
                }
            }
            ur[0] = uc[start] = up[start] = um[n - start - 1] = false;
        }
        writer.write(Integer.toString(cnt) + "\n");
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
    
}
