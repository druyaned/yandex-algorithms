package com.github.druyaned.yandexalgorithms.train1.lect8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW3SecondMax {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Node {

        private final Node from;
        private Node left, right;
        private final int val;
        
        public Node(Node from, int val) {
            this.from = from;
            this.left = null;
            this.right = null;
            this.val = val;
        }
        
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = elems.length - 1;
        int val = Integer.parseInt(elems[0]);
        Node root = new Node(null, val);
        for (int i = 1; i < n; ++i) {
            val = Integer.parseInt(elems[i]);
            Node node = root;
            boolean equal, less, more;
            while (true) {
                equal = val == node.val;
                less = val < node.val;
                more = val > node.val;
                if (equal) {
                    break;
                }
                if (less && node.left == null) {
                    node.left = new Node(node, val);
                    break;
                }
                if (more && node.right == null) {
                    node.right = new Node(node, val);
                    break;
                }
                if (less) {
                    node = node.left;
                }
                if (more) {
                    node = node.right;
                }
            }
        }
        Node node = root;
        boolean hasLeftStep = true;
        while (true) {
            final Node f = node.from;
            final Node l = node.left;
            final Node r = node.right;
            if (l == null && r == null) {
                if (hasLeftStep) {
                    writer.write(f.val + "\n");
                } else {
                    writer.write(node.val + "\n");
                }
                return;
            }
            if (r == null) {
                if (hasLeftStep) {
                    node = node.left;
                    hasLeftStep = false;
                } else {
                    writer.write(node.val + "\n");
                    return;
                }
            } else {
                node = node.right;
            }
        }
    }
    
}
/*
2-й по величине - это значит, что надо 1 раз влево сместиться.
*/
