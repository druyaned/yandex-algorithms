package druyaned.yandexalgorithms.train1.l4maps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import static java.lang.Character.isDigit;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class HW10CheatCheck {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static boolean isCharOfIdentifier(char ch) {
        return 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z' ||
                '0' <= ch && ch <= '9' ||
                ch == '_';
    }
    
    private static boolean isIdentifier(String word, boolean caseSensitive,
            boolean digitStart, HashSet<String> keywords) {
        if (word == null || word.length() == 0) {
            return false;
        }
        if (!caseSensitive) {
            word = word.toLowerCase();
        }
        if (keywords.contains(word)) {
            return false;
        }
        if (!digitStart && isDigit(word.charAt(0))) {
            return false;
        }
        boolean allDigits = true;
        for (int i = 0; i < word.length(); ++i) {
            if (!isDigit(word.charAt(i))) {
                allDigits = false;
                break;
            }
        }
        if (allDigits) {
            return false;
        }
        for (int i = 0; i < word.length(); ++i) {
            if (!isCharOfIdentifier(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int INIT_CAPACITY = 10000;
        String[] elements = reader.readLine().split(" ");
        int n = Integer.parseInt(elements[0]);
        boolean caseSensitive = elements[1].equals("yes");
        boolean digitStart = elements[2].equals("yes");
        HashSet<String> keywords = new HashSet<>();
        for (int i = 0; i < n; ++i) {
            if (caseSensitive) {
                keywords.add(reader.readLine());
            } else {
                keywords.add(reader.readLine().toLowerCase());
            }
        }
        HashMap<String, Integer> identifiers = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        int maxCount = 0;
        int chVal;
        ArrayList<String> idList = new ArrayList<>(INIT_CAPACITY);
        while ((chVal = reader.read()) != -1) {
            char ch = (char)chVal;
            boolean isCharOfIdentifier = isCharOfIdentifier(ch);
            if (!isCharOfIdentifier && builder.length() != 0) {
                String word = builder.toString();
                builder.setLength(0);
                if (isIdentifier(word, caseSensitive, digitStart, keywords)) {
                    if (!caseSensitive) {
                        word = word.toLowerCase();
                    }
                    Integer count = identifiers.get(word);
                    int newCount = count == null ? 1 : count + 1;
                    identifiers.put(word, newCount);
                    idList.add(word);
                    if (maxCount < newCount) {
                        maxCount = newCount;
                    }
                }
            } else if (isCharOfIdentifier) {
                builder.append(ch);
            }
        }
        if (builder.length() != 0) {
            String word = builder.toString();
            builder.setLength(0);
            if (isIdentifier(word, caseSensitive, digitStart, keywords)) {
                if (!caseSensitive) {
                    word = word.toLowerCase();
                }
                Integer count = identifiers.get(word);
                int newCount = count == null ? 1 : count + 1;
                identifiers.put(word, newCount);
                idList.add(word);
                if (maxCount < newCount) {
                    maxCount = newCount;
                }
            }
        }
        for (int i = 0; i < idList.size(); ++i) {
            String identifier = idList.get(i);
            int count = identifiers.get(identifier);
            if (count == maxCount) {
                writer.write(identifier);
                return;
            }
        }
    }
    
}
