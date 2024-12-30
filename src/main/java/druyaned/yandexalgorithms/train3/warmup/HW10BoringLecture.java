package druyaned.yandexalgorithms.train3.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW10BoringLecture {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int TABLE_SIZE = 26;
        char[] line = reader.readLine().toCharArray();
        int n = line.length;
        long[] counts = new long[TABLE_SIZE];
        for (int i = 0; i < n / 2; ++i) {
            int i1 = line[i] - 'a', i2 = line[n - 1 - i] - 'a';
            long d = i + 1L;
            long cnt = (d + 1L) * d + d * (n - d * 2L);
            counts[i1] += cnt;
            counts[i2] += cnt;
        }
        if (n % 2 == 1) {
            int i = n / 2;
            long d = i + 1L;
            long cnt = (d + 1L) * d + d * (n - d * 2L);
            counts[line[i] - 'a'] += cnt;
        }
        for (int i = 0; i < TABLE_SIZE; ++i) {
            if (counts[i] != 0) {
                writer.write(String.format("%c: %d\n", (char)('a' + i), counts[i]));
            }
        }
    }
    
}
/*
1 + 2 + ... + n = (n + 1) * n / 2

abcdefghi | len=9
a: 1+1+1+1+1+1+1+1+1 | 1*9
b: 1+2+2+2+2+2+2+2+1 | 1*2 + 2*7
c: 1+2+3+3+3+3+3+2+1 | 1*2 + 2*2 + 3*5
d: 1+2+3+4+4+4+3+2+1 | 1*2 + 2*2 + 3*2 + 4*3
e: 1+2+3+4+5+4+3+2+1 | 1*2 + 2*2 + 3*2 + 4*2 + 5*1
f: 1+2+3+4+4+4+3+2+1 | 1*2 + 2*2 + 3*2 + 4*3
g: 1+2+3+3+3+3+3+2+1 | 1*2 + 2*2 + 3*5
h: 1+2+2+2+2+2+2+2+1 | 1*2 + 2*7
i: 1+1+1+1+1+1+1+1+1 | 1*9
1*9 = 1*2 + 1*7
1*2 + 2*7 = (1+2)*2 + 2*5
1*2 + 2*2 + 3*5 = (1+2+3)*2 + 3*3
1*2 + 2*2 + 3*2 + 4*3 = (1+2+3+4)*2 + 4*1
1*2 + 2*2 + 3*2 + 4*2 + 5*1 = (1+2+3+4)*2 + 5

abcdefghij | len=10
a: 1+1+1+1+1+1+1+1+1+1 | 1*10
b: 1+2+2+2+2+2+2+2+2+1 | 1*2 + 2*8
c: 1+2+3+3+3+3+3+3+2+1 | 1*2 + 2*2 + 3*6
d: 1+2+3+4+4+4+4+3+2+1 | 1*2 + 2*2 + 3*2 + 4*4
e: 1+2+3+4+5+5+4+3+2+1 | 1*2 + 2*2 + 3*2 + 4*2 + 5*2
f: 1+2+3+4+5+5+4+3+2+1 | 1*2 + 2*2 + 3*2 + 4*2 + 5*2
g: 1+2+3+4+4+4+4+3+2+1 | 1*2 + 2*2 + 3*2 + 4*4
h: 1+2+3+3+3+3+3+3+2+1 | 1*2 + 2*2 + 3*6
i: 1+2+2+2+2+2+2+2+2+1 | 1*2 + 2*8
j: 1+1+1+1+1+1+1+1+1+1 | 1*10
1*10 = 1*2 + 1*8
1*2 + 2*8 = (1+2)*2 + 2*6
1*2 + 2*2 + 3*6 = (1+2+3)*2 + 3*4
1*2 + 2*2 + 3*2 + 4*4 = (1+2+3+4)*2 + 4*2
1*2 + 2*2 + 3*2 + 4*2 + 5*2 = (1+2+3+4+5)*2
*/