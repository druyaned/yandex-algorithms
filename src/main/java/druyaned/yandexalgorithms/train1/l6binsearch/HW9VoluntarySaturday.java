package druyaned.yandexalgorithms.train1.l6binsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW9VoluntarySaturday {
    
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
        int teams = Integer.parseInt(elems[1]);
        int teamSize = Integer.parseInt(elems[2]);
        int[] heights = new int[n];
        for (int i = 0; i < n; ++i) {
            heights[i] = Integer.parseInt(reader.readLine());
        }
        // solve
        Arrays.sort(heights);
        int leftDif = 0, rightDif = heights[n - 1] - heights[0], midDif;
        while (leftDif < rightDif) {
            midDif = (leftDif + rightDif) / 2;
            int cnt = 0;
            for (int i = teamSize - 1; i < n;) {
                int dif = heights[i] - heights[i - teamSize + 1];
                if (dif <= midDif) {
                    ++cnt;
                    i += teamSize;
                } else {
                    ++i;
                }
            }
            if (cnt >= teams) {
                rightDif = midDif;
            } else {
                leftDif = midDif + 1;
            }
        }
        writer.write(leftDif + "\n");
    }
    
}
/*
n=11
teams=3
teamSize=3
arr:  4  5  6  8  8 10 10 11 11 11 12
ind:  0  1  2  3  4  5  6  7  8  9 10
dif:        2  3  2  2  2  1  1  0  1
ans=2
Левый бин-поиск по ответу (наименьшее значение максимальной
разницы в росте среди команд).
Все хорошо, когда количество команд для текущей разницы
не меньше teams.
*/
