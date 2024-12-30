package druyaned.yandexalgorithms.train1.l6binsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW6VerySimpleTask {
    
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
        int x = Integer.parseInt(elems[1]);
        int y = Integer.parseInt(elems[2]);
        // solve
        int fast = Integer.min(x, y);
        int slow = Integer.max(x, y);
        int startN = 1;
        int startT = fast;
        int leftT = 0, rightT = n * fast, midT;
        while (leftT < rightT) {
            midT = (int)(((long)leftT + (long)rightT) / 2L);
            int fastN = midT / fast;
            int slowN = midT / slow;
            if (n <= fastN + slowN + startN) {
                rightT = midT;
            } else {
                leftT = midT + 1;
            }
        }
        writer.write((startT + leftT) + "\n");
    }
    
}
/*
N x y
5 2 3
fast = min(x, y) = 2;
slow = max(x, y) = 3;
startN = 1;
startT = fast = 2; // sec

from startT | from startN
          0 | 0
          1 | 0
          2 | 1
          3 | 2
          4 | 3
          5 | 3
          6 | 5 !
          7 | 5
          8 | 6
          9 | 7
         10 | 8

leftT = 0, rightT = n * fast;
fastN = t / fast;
slowN = t / slow;
n = fastN + slowN;
good: n >= N
Левый бин-поиск (плохо -> хорошо).
*/