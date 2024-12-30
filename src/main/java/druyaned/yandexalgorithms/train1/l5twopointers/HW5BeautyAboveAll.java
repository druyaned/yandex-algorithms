package druyaned.yandexalgorithms.train1.l5twopointers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW5BeautyAboveAll {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elements = reader.readLine().split(" ");
        int n = Integer.parseInt(elements[0]);
        int k = Integer.parseInt(elements[1]);
        int[] kinds = readArr(reader, true);
        // solve
        int[] kindCount = new int[k + 1];
        int size = 0;
        int minDiffL = 1;
        int minDiffR = n;
        for (int l = 1, end = 1; l <= n; ++l) {
            while (end <= n && size < k) {
                if (kindCount[kinds[end]] == 0) {
                    ++size;
                }
                ++kindCount[kinds[end++]];
            }
            if (size < k) {
                break;
            }
            int r = end - 1;
            if (r - l < minDiffR - minDiffL) {
                minDiffL = l;
                minDiffR = r;
            }
            --kindCount[kinds[l]];
            if (kindCount[kinds[l]] == 0) {
                --size;
            }
        }
        writer.write(minDiffL + " " + minDiffR + "\n");
    }
    
    private static int[] readArr(BufferedReader reader, boolean startFromOne) throws IOException {
        String line = reader.readLine();
        int n = 1;
        for (int i = 0; i < line.length(); ++i) {
            if (line.charAt(i) == ' ') {
                ++n;
            }
        }
        int[] arr = startFromOne ? new int[n + 1] : new int[n];
        for (int l = 0, r = 1, i = startFromOne ? 1 : 0; l < line.length(); ) {
            while (r < line.length() && line.charAt(r) != ' ') {
                ++r;
            }
            arr[i++] = Integer.parseInt(line.substring(l, r));
            l = r + 1;
            r = l + 1;
        }
        return arr;
    }
    
}
/*
Завожу хэш-отображение [key=variety; value=count].
Буду считать количество уникальных variety в текущем отрезке.
Буду расширять отрезок, пока size < k.
После расширения сравниваю на то, минимален ли отрезок.
*/
