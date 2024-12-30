package druyaned.yandexalgorithms.train2.divb.lesson1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW2UndergroundCircleLine {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int station1 = Integer.parseInt(elems[1]);
        int station2 = Integer.parseInt(elems[2]);
        if (station1 > station2) {
            int temp = station1;
            station1 = station2;
            station2 = temp;
        }
        int way1 = station2 - station1 - 1;
        int way2 = station1 - 1 + n - station2;
        int way = way1 < way2 ? way1 : way2;
        writer.write(way + "\n");
    }
    
}
/*
8 2 6
1 2 3 4 5 6 7 8
  .       .
6 - 2 - 1 = 3; [3, 5]
2 - 1 + 8 - 6 = 3; [1] || [7, 8]
*/