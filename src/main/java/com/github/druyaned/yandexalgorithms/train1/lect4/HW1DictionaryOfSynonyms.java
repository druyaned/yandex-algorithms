package com.github.druyaned.yandexalgorithms.train1.lect4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class HW1DictionaryOfSynonyms {

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
        HashMap<String, String> dictionary = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            String[] words = reader.readLine().split(" ");
            String word = words[0];
            String synonym = words[1];
            dictionary.put(word, synonym);
            dictionary.put(synonym, word);
        }
        String word = reader.readLine();
        String synonym = dictionary.get(word);
        writer.write(synonym + "\n");
    }
    
}
