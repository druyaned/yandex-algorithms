package com.github.druyaned.yandexalgorithms.train5.l3setmap.p02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Anagram {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int ALPHABET_SIZE = 26;
        String first = readToken(reader);
        String second = readToken(reader);
        if (first == null || second == null) {
            writer.write("NO\n");
            return;
        }
        int[] firstLetterCounts = new int[ALPHABET_SIZE];
        int[] secondLetterCounts = new int[ALPHABET_SIZE];
        for (char letter : first.toCharArray()) {
            int letterIndex = letter - 'a';
            firstLetterCounts[letterIndex]++;
        }
        for (char letter : second.toCharArray()) {
            int letterIndex = letter - 'a';
            secondLetterCounts[letterIndex]++;
        }
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (firstLetterCounts[i] != secondLetterCounts[i]) {
                writer.write("NO\n");
                return;
            }
        }
        writer.write("YES\n");
    }
    
    private static final char[] buf = new char[(int)2e6];
    
    private static String readToken(Reader reader) throws IOException {
        int c;
        while ((c = reader.read()) != -1 && (c == ' ' || c == '\n')) {}
        if (c == -1) {
            return null;
        }
        buf[0] = (char)c;
        int l = 1;
        while ((c = reader.read()) != -1 && c != ' ' && c != '\n') {
            buf[l++] = (char)c;
        }
        return new String(buf, 0, l);
    }
    
}
