package druyaned.yandexalgorithms.train1.l3sets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class HW8AngryPigs {
    
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
        HashSet<Integer> x = new HashSet<>();
        String[] words;
        for (int i = 0; i < n; ++i) {
            words = reader.readLine().split(" ");
            x.add(Integer.valueOf(words[0]));
        }
        writer.write(Integer.toString(x.size()) + "\n");
    }
    
}
