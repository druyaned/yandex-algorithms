package druyaned.yandexalgorithms.train1.l5twopointers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class HW8Substring {
    
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
        int k = Integer.parseInt(elems[1]);
        String s = reader.readLine();
        // solve
        HashMap<Character, Integer> charCount = new HashMap<>();
        char kPlus1Char = '\0';
        int maxLength = 0;
        int startPos = 0;
        for (int l = 0, r = 0; l < n; ++l) {
            if (kPlus1Char == '\0') { // is not set
                while (r < n && kPlus1Char == '\0') {
                    char chR = s.charAt(r++);
                    Integer count = charCount.get(chR);
                    int newCount = count == null ? 1 : count + 1;
                    charCount.put(chR, newCount);
                    if (newCount == k + 1) {
                        kPlus1Char = chR;
                    }
                }
                if (kPlus1Char == '\0') {
                    int curLength = r - l;
                    if (maxLength < curLength) {
                        maxLength = curLength;
                        startPos = l + 1;
                    }
                    break;
                } else {
                    int curLength = r - 1 - l;
                    if (maxLength < curLength) {
                        maxLength = curLength;
                        startPos = l + 1;
                    }
                }
            }
            char chL = s.charAt(l);
            int count = charCount.get(chL);
            charCount.put(chL, count - 1);
            if (chL == kPlus1Char) {
                kPlus1Char = '\0';
            }
        }
        writer.write(maxLength + " " + startPos + "\n");
    }
    
}
/*
Нужен пример.

8 2
aabbbccd
l   r
a b
2 3
aabbbccd
   l    r
b c d
2 2 1

Дальше надо l сдвинуть до тех пор, пока не уменьшится char с count=k+1.
Ход какой? Если kPlus1Char не установлен, ищу kPlus1Char. Обновляю maxLength, если надо.
Потом просто уменьшаю count у текущего chL.
Если он же kPlus1Char, то снимаю флаг с kPlus1Char.
*/
