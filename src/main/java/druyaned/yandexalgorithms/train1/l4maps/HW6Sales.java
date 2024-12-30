package druyaned.yandexalgorithms.train1.l4maps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class HW6Sales {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        TreeMap<String, TreeMap<String, Long>> buyerTree = new TreeMap<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split(" ");
            String buyer = words[0];
            String product = words[1];
            long count = Long.parseLong(words[2]);
            TreeMap<String, Long> foundProductTree = buyerTree.get(buyer);
            if (foundProductTree == null) {
                TreeMap<String, Long> productTree = new TreeMap<>();
                productTree.put(product, count);
                buyerTree.put(buyer, productTree);
            } else {
                Long foundCount = foundProductTree.get(product);
                if (foundCount == null) {
                    foundProductTree.put(product, count);
                } else {
                    foundProductTree.put(product, foundCount + count);
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, TreeMap<String, Long>> entry : buyerTree.entrySet()) {
            String buyer = entry.getKey();
            TreeMap<String, Long> productTree = entry.getValue();
            builder.setLength(0);
            builder.append(buyer).append(":\n");
            for (Map.Entry<String, Long> productTreeEntry : productTree.entrySet()) {
                String product = productTreeEntry.getKey();
                Long count = productTreeEntry.getValue();
                builder.append(product).append(' ').append(count).append('\n');
            }
            writer.write(builder.toString());
        }
    }
    
}
