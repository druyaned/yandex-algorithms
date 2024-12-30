package druyaned.yandexalgorithms.train1.l4maps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class HW9StressControl {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class StressIndexes {
        
        private static final int LIM = 30;
        
        private final int[] arr = new int[LIM];
        private int size = 0;
        
        private void add(int stressIndex) {
            arr[size++] = stressIndex;
            for (int i = size - 1; i != 0 && arr[i - 1] > arr[i]; --i) {
                int temp = arr[i - 1];
                arr[i - 1] = arr[i];
                arr[i] = temp;
            }
        }
        
        public boolean contains(int stressIndex) {
            int left = 0, right = size - 1, mid;
            while (left <= right) {
                mid = (left + right) / 2;
                if (stressIndex == arr[mid]) {
                    return true;
                } else if (stressIndex < arr[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return false;
        }
        
        public void clear() {
            size = 0;
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (size != 0) {
                builder.append(arr[0]);
            }
            for (int i = 1; i < size; ++i) {
                builder.append(' ').append(arr[i]);
            }
            return builder.toString();
        }
        
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        HashMap<String, StressIndexes> dictionary = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            String word = reader.readLine();
            String lowWord = word.toLowerCase();
            StressIndexes stresses = dictionary.get(lowWord);
            if (stresses == null) {
                stresses = new StressIndexes();
                dictionary.put(lowWord, stresses);
            }
            for (int j = 0; j < word.length(); ++j) {
                char ch = word.charAt(j);
                if ('A' <= ch && ch <= 'Z') {
                    stresses.add(j);
                    break;
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        int chVal;
        int mistakeCount = 0;
        while ((chVal = reader.read()) != -1) {
            char ch = (char)chVal;
            if (Character.isWhitespace(ch) && builder.length() != 0) {
                String word = builder.toString();
                builder.setLength(0);
                String lowWord = word.toLowerCase();
                StressIndexes stresses = dictionary.get(lowWord);
                boolean oneStress = false;
                for (int i = 0; i < word.length(); ++i) {
                    char wordCh = word.charAt(i);
                    if ('A' <= wordCh && wordCh <= 'Z') {
                        if (oneStress) {
                            ++mistakeCount;
                            break;
                        }
                        oneStress = true;
                        if (stresses != null && !stresses.contains(i)) {
                            ++mistakeCount;
                            break;
                        }
                    }
                }
                if (!oneStress) {
                    ++mistakeCount;
                }
            } else if (!Character.isWhitespace(ch)) {
                builder.append(ch);
            }
        }
        writer.write(mistakeCount + "\n");
    }
    
}
