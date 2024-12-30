package druyaned.yandexalgorithms.train3.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW2BeautifulString {
    
    private static final char[] buf = new char[16];
    
    private static int readInt(BufferedReader reader) throws IOException {
        int chVal = reader.read();
        while (chVal != -1 && chVal != '-' && (chVal < '0' || '9' < chVal)) {
            chVal = reader.read();
        }
        int l = 0;
        buf[l++] = (char)chVal;
        chVal = reader.read();
        while ('0' <= chVal && chVal <= '9') {
            buf[l++] = (char)chVal;
            chVal = reader.read();
        }
        return Integer.parseInt(new String(buf, 0, l));
    }
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int k = readInt(reader);
        char[] line = reader.readLine().toCharArray();
        int n = line.length;
        int maxBeauty = 1;
        for (char ch = 'a'; ch <= 'z'; ++ch) {
            int swapCount = 0;
            for (int l = 0, r = 0; r < n && l < n; ++l) {
                while (r < n) {
                    if (line[r] != ch && swapCount == k) {
                        break;
                    }
                    if (line[r] != ch) {
                        ++swapCount;
                    }
                    ++r;
                }
                if (maxBeauty < r - l) {
                    maxBeauty = r - l;
                }
                if (line[l] != ch) {
                    --swapCount;
                }
            }
        }
        writer.write(Integer.toString(maxBeauty));
    }
    
}
/*
В Яндекс Контесте на Java не прошел даже 1-ый тест,
а на C++ все приняло прекрасно.
*/