package com.github.druyaned.yandexalgorithms.train1.lect3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class HW6AlienGenome {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String genome1 = reader.readLine();
        String genome2 = reader.readLine();
        HashSet<String> pairs = new HashSet<>();
        for (int i = 0; i < genome2.length() - 1; ++i) {
            pairs.add(genome2.substring(i, i + 2));
        }
        int count = 0;
        for (int i = 0; i < genome1.length() - 1; ++i) {
            if (pairs.contains(genome1.substring(i, i + 2))) {
                ++count;
            }
        }
        writer.write(Integer.toString(count) + "\n");
    }
    
}
