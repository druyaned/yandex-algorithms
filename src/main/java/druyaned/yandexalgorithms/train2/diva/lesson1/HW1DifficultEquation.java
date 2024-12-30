package druyaned.yandexalgorithms.train2.diva.lesson1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW1DifficultEquation {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int a = Integer.parseInt(reader.readLine());
        int b = Integer.parseInt(reader.readLine());
        int c = Integer.parseInt(reader.readLine());
        int d = Integer.parseInt(reader.readLine());
        if (a == 0) {
            writer.write("INF\n");
        } else if (b % a == 0) {
            if (c != 0 && d % c == 0 && b / a == d / c) {
                writer.write("NO\n");
            } else {
                writer.write((-b / a) + "\n");
            }
        } else {
            writer.write("NO\n");
        }
    }
    
}
