package com.github.druyaned.yandexalgorithms.train1.l3sets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class HW7Turtles {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        HashSet<Integer> places = new HashSet<>();
        String[] words;
        int before, after;
        int liers = 0;
        int place;
        for (int i = 0; i < n; ++i) {
            words = reader.readLine().split(" ");
            before = Integer.parseInt(words[0]);
            after = Integer.parseInt(words[1]);
            place = before + 1;
            if (before < 0 || after < 0) {
                ++liers;
            } else if (place + after != n) {
                ++liers;
            } else if (!places.add(place)) {
                ++liers;
            }
        }
        writer.write(Integer.toString(n - liers) + "\n");
    }
    
}
