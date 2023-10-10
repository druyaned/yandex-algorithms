package com.github.druyaned.yandexalgorithms.train1.lect3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW10JoggingInManhattan {
    
    private static final int LIM = 202 * 203;
    private static final int MID = LIM / 2;
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Area {
        
        private final int T;
        private int xMin, xMax;
        private final int yMin[], yMax[];

        private Area(int t, int x, int y) {
            T = t;
            xMin = xMax = x;
            yMin = new int[LIM];
            yMax = new int[LIM];
            yMin[MID + x] = y;
            yMax[MID + x] = y;
        }

        private void extendArea() {
            if (xMin > xMax) { // if empty
                return;
            }
            int baseInd = MID + xMin;
            for (int i = -T; i < 0; ++i) {
                yMin[baseInd + i] = yMin[baseInd] - i - T;
                yMax[baseInd + i] = yMax[baseInd] + i + T;
            }
            baseInd = MID + xMax;
            for (int i = 1; i <= T; ++i) {
                yMin[baseInd + i] = yMin[baseInd] + i - T;
                yMax[baseInd + i] = yMax[baseInd] - i + T;
            }
            for (int ind = MID + xMin; ind <= MID + xMax; ++ind) {
                yMin[ind] = yMin[ind] - T;
                yMax[ind] = yMax[ind] + T;
            }
            xMin -= T;
            xMax += T;
        }
        
        private void setAndExtend(int x, int y) {
            xMin = x - T;
            xMax = x + T;
            int baseInd = MID + x;
            for (int i = -T; i < 0; ++i) {
                yMin[baseInd + i] = y - i - T;
                yMax[baseInd + i] = y + i + T;
            }
            for (int i = 0; i <= T; ++i) {
                yMin[baseInd + i] = y + i - T;
                yMax[baseInd + i] = y - i + T;
            }
        }
        
        private int dotCount() {
            int count = 0;
            for (int ind = MID + xMin; ind <= MID + xMax; ++ind) {
                count += yMax[ind] - yMin[ind] + 1;
            }
            return count;
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append('{').append(xMin).append(';').append(xMax).append("}:");
            for (int ind = MID + xMin; ind <= MID + xMax; ++ind) {
                builder.append(' ').append(ind - MID).append('[').append(yMin[ind])
                        .append(';').append(yMax[ind]).append(']');
            }
            return builder.toString();
        }
        
    }
    
    private static void intersection(Area area, Area dotArea) {
        final int xMinOfBoth = Integer.max(area.xMin, dotArea.xMin);
        final int xMaxOfBoth = Integer.min(area.xMax, dotArea.xMax);
        area.xMin = xMinOfBoth; // remove all that is not in the intersection
        int areaInd = MID + xMinOfBoth;
        int areaIndMax = MID + xMaxOfBoth;
        for (; areaInd <= areaIndMax; ++areaInd) {
            int yMin = Integer.max(area.yMin[areaInd], dotArea.yMin[areaInd]);
            int yMax = Integer.min(area.yMax[areaInd], dotArea.yMax[areaInd]);
            if (yMin <= yMax) { // remove while not in the intersection
                area.xMin = areaInd - MID;
                area.yMin[areaInd] = yMin;
                area.yMax[areaInd] = yMax;
                ++areaInd;
                break;
            }
        }
        for (; areaInd <= areaIndMax; ++areaInd) {
            int yMin = Integer.max(area.yMin[areaInd], dotArea.yMin[areaInd]);
            int yMax = Integer.min(area.yMax[areaInd], dotArea.yMax[areaInd]);
            if (yMin <= yMax) { // intersect while in the intersection
                area.yMin[areaInd] = yMin;
                area.yMax[areaInd] = yMax;
            } else {
                break;
            }
        }
        area.xMax = areaInd - MID - 1; // remove the remainings that not in the intersection
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] words = reader.readLine().split(" ");
        int t = Integer.parseInt(words[0]);
        int d = Integer.parseInt(words[1]);
        int n = Integer.parseInt(words[2]);
        Area area = new Area(t, 0, 0);
        Area dotArea = new Area(d, 0, 0);
        for (int i = 0; i < n; ++i) {
            words = reader.readLine().split(" ");
            int x = Integer.parseInt(words[0]);
            int y = Integer.parseInt(words[1]);
            area.extendArea();
            dotArea.setAndExtend(x, y);
            intersection(area, dotArea);
        }
        writer.write(String.format("%d\n", area.dotCount()));
        final int areaIndMax = MID + area.xMax;
        for (int areaInd = MID + area.xMin; areaInd <= areaIndMax; ++areaInd) {
            for (int y = area.yMin[areaInd]; y <= area.yMax[areaInd]; ++y) {
                writer.write(String.format("%d %d\n", areaInd - MID, y));
            }
        }
    }
    
}
