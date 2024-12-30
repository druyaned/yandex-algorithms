package druyaned.yandexalgorithms.train2.divb.lesson2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW3MakingPalindromes {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String line = reader.readLine();
        char[] chars = line.toCharArray();
        int n = chars.length;
        int cnt = 0;
        for (int i1 = n / 2 - 1, i2 = (n - 1) / 2 + 1; i1 >= 0 && i2 < n; --i1, ++i2) {
            if (chars[i1] != chars[i2]) {
                ++cnt;
            }
        }
        writer.write(cnt + "\n");
    }
    
}
/*
012 3 456
aaa(a)aaa | 7
012  345
aaa()aaa | 6
7/2 = 3
6/2 = 3
i1 = n / 2 - 1
i2 = (n - 1) / 2 + 1
*/