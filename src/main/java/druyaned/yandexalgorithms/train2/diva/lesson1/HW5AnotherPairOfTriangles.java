package druyaned.yandexalgorithms.train2.diva.lesson1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW5AnotherPairOfTriangles {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int P = Integer.parseInt(reader.readLine());
        if (P == 4) {
            writer.write("-1\n");
            return;
        }
        int a1 = P % 2 == 0 ? 2 : 1;
        int b1 = (P - a1) / 2;
        int c1 = b1;
        int a2 = P / 3;
        int b2, c2;
        switch (P % 3) {
            case 0 -> b2 = c2 = P / 3;
            case 1 -> {b2 = P / 3; c2 = P / 3 + 1;}
            default -> {b2 = c2 = P / 3 + 1;}
        }
        writer.write(a2 + " " + b2 + " " + c2 + "\n");
        writer.write(a1 + " " + b1 + " " + c1 + "\n");
    }
    
}
