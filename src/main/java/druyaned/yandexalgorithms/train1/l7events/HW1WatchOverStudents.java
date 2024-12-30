package druyaned.yandexalgorithms.train1.l7events;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW1WatchOverStudents {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Watcher {
        private final int type; // 1 == "from", 0 == "to"
        private final int ind;
        Watcher(int isFrom, int ind) {
            this.type = isFrom;
            this.ind = ind;
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int m = Integer.parseInt(elems[1]);
        int watchersN = m * 2;
        Watcher[] watchers = new Watcher[watchersN];
        for (int i = 0; i < watchersN; i += 2) {
            elems = reader.readLine().split(" ");
            int fromInd = Integer.parseInt(elems[0]);
            int toInd = Integer.parseInt(elems[1]);
            watchers[i] = new Watcher(1, fromInd);
            watchers[i + 1] = new Watcher(0, toInd);
        }
        // solve
        Arrays.sort(watchers, (w1, w2) -> w1.ind == w2.ind ? w2.type - w1.type : w1.ind - w2.ind);
        int hasntWatcherCnt = n;
        int prevStart = 0;
        int watchersCnt = 0;
        
        for (int i = 0; i < watchersN; ++i) {
            if (watchers[i].type == 1) { // if "from" type
                if (watchersCnt == 0) {
                    prevStart = watchers[i].ind;
                }
                ++watchersCnt;
            } else { // "to" type
                --watchersCnt;
                if (watchersCnt == 0) {
                    hasntWatcherCnt -= watchers[i].ind - prevStart + 1;
                }
            }
        }
        writer.write(hasntWatcherCnt + "\n");
    }
    
}
/*
Input:
21 6
6 11
2 4
13 15
3 7
17 22
18 20
Output:
4
*/