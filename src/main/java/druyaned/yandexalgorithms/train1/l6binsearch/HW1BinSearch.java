package druyaned.yandexalgorithms.train1.l6binsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW1BinSearch {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static boolean binSearch(int[] arr, int key) {
        int l = 0, r = arr.length - 1, mid;
        while (l <= r) {
            mid = (l + r) / 2;
            if (key == arr[mid]) {
                return true;
            } else if (key < arr[mid]) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return false;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int k = Integer.parseInt(elems[1]);
        int[] arr = new int[n];
        int[] keys = new int[k];
        elems = reader.readLine().split(" ");
        for (int i = 0; i < n; ++i) {
            arr[i] = Integer.parseInt(elems[i]);
        }
        elems = reader.readLine().split(" ");
        for (int i = 0; i < k; ++i) {
            keys[i] = Integer.parseInt(elems[i]);
        }
        //solve
        for (int i = 0; i < k; ++i) {
            if (binSearch(arr, keys[i])) {
                writer.write("YES\n");
            } else {
                writer.write("NO\n");
            }
        }
    }
    
}
