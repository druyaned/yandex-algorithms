package druyaned.yandexalgorithms.train1.l3sets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class HW4WordsInText {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        HashSet<String> dictionary = new HashSet<>();
        StringBuilder builder = new StringBuilder();
        int chVal;
        char ch;
        boolean isWhitespace;
        while ((chVal = reader.read()) != -1) {
            ch = (char)chVal;
            isWhitespace = Character.isWhitespace(ch);
            if (isWhitespace && builder.length() != 0) {
                dictionary.add(builder.toString());
                builder.setLength(0);
            } else if (!isWhitespace) {
                builder.append(ch);
            }
        }
        writer.write(Integer.toString(dictionary.size()) + "\n");
    }
    
}
