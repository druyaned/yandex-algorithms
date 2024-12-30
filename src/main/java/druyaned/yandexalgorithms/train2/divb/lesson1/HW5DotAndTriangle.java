package druyaned.yandexalgorithms.train2.divb.lesson1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW5DotAndTriangle {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static boolean isInTriangle(int d, int x1, int y1) {
        return 0 <= x1 && x1 <= d && 0 <= y1 && y1 <= d - x1;
    }
    
    public static int rangeToCompare(int x1, int y1, int x2, int y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int d = Integer.parseInt(reader.readLine());
        String[] elems = reader.readLine().split(" ");
        int x1 = Integer.parseInt(elems[0]);
        int y1 = Integer.parseInt(elems[1]);
        int xA = 0, yA = 0;
        int xB = d, yB = 0;
        int xC = 0, yC = d;
        if (isInTriangle(d, x1, y1)) {
            writer.write("0\n");
        } else {
            int rA = rangeToCompare(x1, y1, xA, yA);
            int rB = rangeToCompare(x1, y1, xB, yB);
            int rC = rangeToCompare(x1, y1, xC, yC);
            if (rA <= rB && rA <= rC) {
                writer.write("1\n");
            } else if (rB <= rA && rB <= rC) {
                writer.write("2\n");
            } else {
                writer.write("3\n");
            }
        }
    }
    
}
