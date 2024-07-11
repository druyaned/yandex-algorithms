package com.github.druyaned.yandexalgorithms.train5.l3setmap.p06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class WordsReplacement {
    
    private static final char[] BUFFER = new char[(int)2e6];
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        HashSet<String> dictionary = new HashSet();
        String dictionaryLine = reader.readLine();
        String text = reader.readLine();
        fillDictionary(dictionary, dictionaryLine);
        String newText = updatedText(dictionary, text);
        writer.write(newText);
    }
    
    private static void fillDictionary(HashSet<String> dictionary, String line) {
        char[] a = line.toCharArray();
        int bufferLength = 0;
        for (int i = 0; i <= a.length; i++) {
            if (i != a.length && a[i] != ' ') {
                BUFFER[bufferLength++] = a[i];
            } else {
                boolean alreadyHasShorter = false;
                for (int j = 1; j < bufferLength; j++) {
                    if (dictionary.contains(new String(BUFFER, 0, j))) {
                        alreadyHasShorter = true;
                        break;
                    }
                }
                if (!alreadyHasShorter) {
                    dictionary.add(new String(BUFFER, 0, bufferLength));
                }
                bufferLength = 0;
            }
        }
    }
    
    private static String updatedText(HashSet<String> dictionary, String line) {
        char[] newText = new char[(int)2e6];
        int newTextLength = 0;
        char[] a = line.toCharArray();
        int bufferLength = 0;
        for (int i = 0; i <= a.length; i++) {
            if (i != a.length && a[i] != ' ') {
                BUFFER[bufferLength++] = a[i];
            } else {
                int trueLength = 1;
                for (; trueLength < bufferLength; trueLength++) {
                    if (dictionary.contains(new String(BUFFER, 0, trueLength))) {
                        break;
                    }
                }
                System.arraycopy(BUFFER, 0, newText, newTextLength, trueLength);
                newTextLength += trueLength;
                newText[newTextLength++] = ' ';
                bufferLength = 0;
            }
        }
        return new String(newText, 0, newTextLength - 1);
    }
    
}
