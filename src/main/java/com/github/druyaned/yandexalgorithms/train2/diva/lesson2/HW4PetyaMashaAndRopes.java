package com.github.druyaned.yandexalgorithms.train2.diva.lesson2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4PetyaMashaAndRopes {
    
    private static final char[] buf = new char[16];
    
    private static int readInt(BufferedReader reader) throws IOException {
        int chVal = reader.read();
        while (chVal != -1 && chVal != '-' && (chVal < '0' || '9' < chVal)) {
            chVal = reader.read();
        }
        int l = 0;
        buf[l++] = (char)chVal;
        chVal = reader.read();
        while ('0' <= chVal && chVal <= '9') {
            buf[l++] = (char)chVal;
            chVal = reader.read();
        }
        return Integer.parseInt(new String(buf, 0, l));
    }
    
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
        int maxRope = 0;
        int sum = 0;
        for (int i = 0; i < n; ++i) {
            int rope = readInt(reader);
            if (maxRope < rope) {
                maxRope = rope;
            }
            sum += rope;
        }
        sum -= maxRope;
        if (maxRope > sum) {
            writer.write((maxRope - sum) + "\n");
        } else {
            writer.write((maxRope + sum) + "\n");
        }
    }
    
}
