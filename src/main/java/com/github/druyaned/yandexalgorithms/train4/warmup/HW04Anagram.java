package com.github.druyaned.yandexalgorithms.train4.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW04Anagram {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int TABLE_SIZE = 26;
        int[] table1 = new int[TABLE_SIZE];
        int[] table2 = new int[TABLE_SIZE];
        int c;
        while ((c = reader.read()) != -1 && c != '\n') {
            ++table1[c - 'a'];
        }
        while ((c = reader.read()) != -1 && c != '\n') {
            ++table2[c - 'a'];
        }
        for (int i = 0; i < TABLE_SIZE; ++i) {
            if (table1[i] != table2[i]) {
                writer.write("NO\n");
                return;
            }
        }
        writer.write("YES\n");
    }
    
}
