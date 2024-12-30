package druyaned.yandexalgorithms.train3.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4ControlWork {
    
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
        int n = readInt(reader);
        int k = readInt(reader);
        int rowPetya = readInt(reader);
        int placePetya = readInt(reader);
        int positionPetya = placePetya + (rowPetya - 1) * 2;
        int frontPositionVasya = positionPetya - k;
        int backPositionVasya = positionPetya + k;
        int rowVasya, placeVasya;
        if (frontPositionVasya >= 1 && backPositionVasya <= n) {
            int frontRowVasya = (frontPositionVasya - 1) / 2 + 1;
            int backRowVasya = (backPositionVasya - 1) / 2 + 1;
            if (rowPetya - frontRowVasya < backRowVasya - rowPetya) {
                rowVasya = frontRowVasya;
                placeVasya = frontPositionVasya % 2 == 0 ? 2 : 1;
            } else {
                rowVasya = backRowVasya;
                placeVasya = backPositionVasya % 2 == 0 ? 2 : 1;
            }
        } else if (backPositionVasya <= n) {
            rowVasya = (backPositionVasya - 1) / 2 + 1;
            placeVasya = backPositionVasya % 2 == 0 ? 2 : 1;
        } else if (frontPositionVasya >= 1) {
            rowVasya = (frontPositionVasya - 1) / 2 + 1;
            placeVasya = frontPositionVasya % 2 == 0 ? 2 : 1;
        } else {
            writer.write("-1\n");
            return;
        }
        writer.write(rowVasya + " " + placeVasya + "\n");
    }
    
}
/*
input:
7
3
2
1
output:
3 2

input:
9
3
2
2
output:
1 1
*/