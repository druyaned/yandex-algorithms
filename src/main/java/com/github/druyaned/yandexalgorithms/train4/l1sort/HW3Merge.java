package com.github.druyaned.yandexalgorithms.train4.l1sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW3Merge {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static final char[] buf = new char[15];
    
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
        int n1 = readInt(reader);
        int[] arr1 = new int[n1];
        for (int i = 0; i < n1; ++i) {
            arr1[i] = readInt(reader);
        }
        int n2 = readInt(reader);
        int[] arr2 = new int[n2];
        for (int i = 0; i < n2; ++i) {
            arr2[i] = readInt(reader);
        }
        int n = n1 + n2;
        int[] arr = new int[n];
        for (int i = 0, i1 = 0, i2 = 0; i < n; ++i) {
            if (i1 < n1 && i2 < n2 && arr1[i1] <= arr2[i2] || i2 == n2) {
                arr[i] = arr1[i1++];
            } else {
                arr[i] = arr2[i2++];
            }
        }
        if (n > 0) {
            writer.write(Integer.toString(arr[0]));
        }
        for (int i = 1; i < n; ++i) {
            writer.write(" " + arr[i]);
        }
        writer.write('\n');
    }
    
}
/*
intput:
5
1 3 5 5 9
3
2 5 6
output:
1 2 3 5 5 5 6 9
*/