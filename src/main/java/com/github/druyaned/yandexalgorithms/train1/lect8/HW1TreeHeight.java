package com.github.druyaned.yandexalgorithms.train1.lect8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW1TreeHeight {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Node {
        
        private Node left, right;
        private final int val;
        
        public Node(Node left, Node right, int val) {
            this.left = left;
            this.right = right;
            this.val = val;
        }
        
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = elems.length - 1;
        int val = Integer.parseInt(elems[0]);
        Node root = new Node(null, null, val);
        int treeHeight = 1;
        for (int i = 1; i < n; ++i) {
            val = Integer.parseInt(elems[i]);
            Node node = root;
            int height = 1;
            boolean equal, less, more;
            while (true) {
                equal = val == node.val;
                less = val < node.val;
                more = val > node.val;
                if (equal || less && node.left == null || more && node.right == null) {
                    break;
                }
                if (less) {
                    node = node.left;
                }
                if (more) {
                    node = node.right;
                }
                ++height;
            }
            if (less) {
                node.left = new Node(null, null, val);
                ++height;
            }
            if (more) {
                node.right = new Node(null, null, val);
                ++height;
            }
            if (treeHeight < height) {
                treeHeight = height;
            }
        }
        writer.write(treeHeight + "\n");
    }
    
}
