package druyaned.yandexalgorithms.train5.l4binsearch.p05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FractionNumbering {
    
    private static final char[] buffer = new char[(int)2e6];
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final long MAX_RANK = (int)2e9;
        long elemNumber = readLong(reader);
        long leftRank = 1L;
        long rightRank = MAX_RANK;
        while (leftRank < rightRank) {
            long midRank = (leftRank + rightRank) / 2;
            long count = getElemCount(midRank);
            if (elemNumber <= count) {
                rightRank = midRank;
            } else {
                leftRank = midRank + 1;
            }
        }
        long rank = leftRank;
        long prevElemCount = getElemCount(rank - 1);
        long pos = elemNumber - prevElemCount;
        long numerator = rank % 2 == 0 ? rank + 1 - pos : pos;
        long denominator = rank + 1 - numerator;
        writer.write(Long.toString(numerator) + "/" + denominator + "\n");
    }
    
    private static long getElemCount(long rank) {
        return rank * (rank + 1) / 2;
    }
    
    private static long readLong(Reader reader) throws IOException {
        int bufferLength = nextTokenInBuffer(reader);
        return Long.parseLong(new String(buffer, 0, bufferLength));
    }
    
    private static int nextTokenInBuffer(Reader reader) throws IOException {
        int ch;
        while ((ch = reader.read()) != -1 && Character.isWhitespace(ch)) {}
        if (ch == -1) {
            return 0;
        }
        buffer[0] = (char)ch;
        int bufferLength = 1;
        while ((ch = reader.read()) != -1 && !Character.isWhitespace(ch)) {
            buffer[bufferLength++] = (char)ch;
        }
        return bufferLength;
    }
    
}
/*
From fraction matrix:
1/1 2/1 1/2 1/3 2/2 3/1 4/1
    1)  down;   while (i != 1) { upRight; }
    2)  right;  while (j != 1) { downLeft; }

rank| fractions
1| 1/1
2| 2/1 1/2
3| 1/3 2/2 3/1
4| 4/1 3/2 2/3 1/4
5| 1/5 2/4 3/3 4/2 5/1
...

rank| numerator
1| 1                | 1
2| 2 1              | 2  3
3| 1 2 3            | 4  5  6
4| 4 3 2 1          | 7  8  9  10
5| 1 2 3 4 5        | 11 12 13 14 15
6| 6 5 4 3 2 1      | 16 17 18 19 20 21
7| 1 2 3 4 5 6 7    | 22 23 24 25 26 27 28
8| 8 7 6 5 4 3 2 1  | 29 30 31 32 33 34 35 36
...
*/
