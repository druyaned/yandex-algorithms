package com.github.druyaned.yandexalgorithms.train1.lect8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW7BranchOutput {
    
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
        public Node(int val) {
            this.val = val;
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = elems.length - 1;
        int val = Integer.parseInt(elems[0]);
        Node root = new Node(val);
        for (int i = 1; i < n; ++i) {
            val = Integer.parseInt(elems[i]);
            Node node = root;
            while (true) {
                final boolean equal = val == node.val;
                final boolean less = val < node.val;
                final boolean more = val > node.val;
                if (equal) {
                    break;
                }
                if (less && node.left == null) {
                    node.left = new Node(val);
                    break;
                }
                if (more && node.right == null) {
                    node.right = new Node(val);
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
        Node[] stack = new Node[n];
        int stackSize = 0;
        for (Node node = root; node != null; node = node.left) {
            stack[stackSize++] = node;
        }
        while (stackSize != 0) {
            Node node = stack[--stackSize];
            for (Node subNode = node.right; subNode != null; subNode = subNode.left) {
                stack[stackSize++] = subNode;
            }
            final Node l = node.left, r = node.right;
            if (l != null && r == null || l == null && r != null) {
                writer.write(node.val + "\n");
            }
        }
    }
    
}
/*
input:
10 9 2 1 7 5 4 3 6 8 12 11 13 0
output:
4
9
*/