package druyaned.yandexalgorithms.train5.l4binsearch.p02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OneDimensionalNavalBattle {
    
    private static final char[] BUFFER = new char[(int)2e6];
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final long MAX_SHIP = 2000000L;
        final long MIN_SHIP = 0L;
        // rightBinsearch
        long capacity = readLong(reader);
        long leftShip = MIN_SHIP, rightShip = MAX_SHIP;
        while (leftShip < rightShip) {
            long mid = (leftShip + rightShip + 1) / 2; // (l r)=(0 1) => mid=1
            if (spaceForShips(mid) <= capacity) {
                leftShip = mid;
            } else {
                rightShip = mid - 1;
            }
        }
        long ship = rightShip;
        writer.write(Long.toString(ship) + "\n");
    }
    
    private static long spaceForShips(long maxShip) {
        long space = 0L, count = 1L;
        while (maxShip > 0) {
            space += (maxShip-- + 1) * count++;
        }
        return space - 1;
    }
    
    private static long readLong(Reader reader) throws IOException {
        int bufferLength = nextTokenInBuffer(reader);
        return Long.parseLong(new String(BUFFER, 0, bufferLength));
    }
    
    private static int nextTokenInBuffer(Reader reader) throws IOException {
        int ch;
        while ((ch = reader.read()) != -1 && Character.isWhitespace(ch)) {}
        if (ch == -1) {
            return 0;
        }
        BUFFER[0] = (char)ch;
        int bufferLength = 1;
        while ((ch = reader.read()) != -1 && !Character.isWhitespace(ch)) {
            BUFFER[bufferLength++] = (char)ch;
        }
        return bufferLength;
    }
    
}
/*
1: 1*(1+1) - 1 = 1
2: 1*(2+1) + 2*2 - 1 = 6
3: 1*(3+1) + 2*3 + 3*2 - 1 = 15;
*/
