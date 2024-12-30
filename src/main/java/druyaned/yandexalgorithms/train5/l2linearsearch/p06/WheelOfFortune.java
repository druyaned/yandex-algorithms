package druyaned.yandexalgorithms.train5.l2linearsearch.p06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WheelOfFortune {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int sectorCount = readInt(reader);
        int[] winnings = new int[sectorCount + 1];
        for (int i = 1; i <= sectorCount; i++) {
            winnings[i] = readInt(reader);
        }
        int velocityFrom = readInt(reader);
        int velocityTo = readInt(reader);
        int velocityDecrease = readInt(reader);
        int indexFrom = divUp(velocityFrom, velocityDecrease);
        int indexTo = divUp(velocityTo, velocityDecrease);
        int winning = 0;
        if (indexTo - indexFrom + 1 >= sectorCount) {
            for (int i = 1; i <= sectorCount; i++) {
                if (winning < winnings[i]) {
                    winning = winnings[i];
                }
            }
        } else {
            for (int i = indexFrom; i <= indexTo; i++) {
                int clockwiseSector = (i - 1) % sectorCount + 1;
                int counterSector = (sectorCount - clockwiseSector + 1) % sectorCount + 1;
                if (winning < winnings[clockwiseSector]) {
                    winning = winnings[clockwiseSector];
                }
                if (winning < winnings[counterSector]) {
                    winning = winnings[counterSector];
                }
            }
        }
        writer.write(Integer.toString(winning) + "\n");
    }
    
    private static int divUp(int number, int divider) {
        return (number - 1) / divider + 1;
    }
    
    private static final char[] buf = new char[16];
    private static int readInt(Reader reader) throws IOException {
        int c;
        while ((c = reader.read()) != -1 && c != '+' && c != '-' && !('0' <= c && c <= '9')) {}
        if (c == -1) {
            throw new NumberFormatException("empty input");
        }
        buf[0] = (char)c;
        int l = 1;
        while ((c = reader.read()) != -1 && '0' <= c && c <= '9') {
            buf[l++] = (char)c;
        }
        return Integer.parseInt(new String(buf, 0, l));
    }
    
}
