package com.github.druyaned.yandexalgorithms.train3.l1stack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4SortOfWagons {
    
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
        int[] wagons = new int[n];
        for (int i = 0; i < n; ++i) {
            wagons[i] = readInt(reader);
        }
        int[] stack = new int[n];
        int size = 0;
        int counter = 1;
        for (int i = 0; i < n; ++i) {
            stack[size++] = wagons[i];
            while (size > 0 && stack[size - 1] == counter) {
                ++counter;
                --size;
            }
        }
        if (size == 0) {
            writer.write("YES\n");
        } else {
            writer.write("NO\n");
        }
    }
    
}
/*
input:
6
5 4 3 1 2 6
output:
YES
*/