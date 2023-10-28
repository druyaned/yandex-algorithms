package com.github.druyaned.yandexalgorithms.train1.l8trees;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW8AVLBalanced {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Node {
        private final int val;
        private final Node from;
        private Node left, right;
        private int height;
        public Node(Node from, int val) {
            this.from = from;
            this.val = val;
            this.height = -1;
        }
    }
    
    private static void showStack(Node[] stack, int stackSize) {
        for (int i = 0; i < stackSize; ++i) {
            System.out.printf(" %d", stack[i].val);
        }
        System.out.println();
    }
    
    private static void showTree(Node root, int n) {
        Node[] stack = new Node[n];
        int stackSize = 0;
        for (Node node = root; node != null; node = node.left) {
            System.out.printf(" %d(%d)", node.val, node.height);
            stack[stackSize++] = node;
        }
        while (stackSize != 0) {
            Node node = stack[--stackSize];
            for (Node subNode = node.right; subNode != null; subNode = subNode.left) {
                System.out.printf(" %d(%d)", subNode.val, subNode.height);
                stack[stackSize++] = subNode;
            }
        }
        System.out.println();
    }
    
    private static int fillLeafs(Node[] leafs, Node root, int n) {
        Node[] stack = new Node[n];
        int stackSize = 0;
        for (Node node = root; node != null; node = node.left) {
            stack[stackSize++] = node;
        }
        int leafsN = 0;
        while (stackSize != 0) {
            Node node = stack[--stackSize];
            for (Node subNode = node.right; subNode != null; subNode = subNode.left) {
                stack[stackSize++] = subNode;
            }
            if (node.left == null && node.right == null) {
                leafs[leafsN++] = node;
            }
        }
        return leafsN;
    }
    
    private static void heightCalculation(Node[] leafs, int leafsN) {
        for (int i = 0; i < leafsN; ++i) {
            int height = 0;
            for (Node node = leafs[i]; node != null; node = node.from, ++height) {
                if (node.height >= height) {
                    break;
                }
                node.height = height;
            }
        }
    }
    
    private static boolean isAVLBalanced(Node root, int n) {
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
            int leftHeight = node.left == null ? 0 : node.left.height + 1;
            int rightHeight = node.right == null ? 0 : node.right.height + 1;
            int difference = Math.abs(leftHeight - rightHeight);
            if (difference > 1) {
                return false;
            }
        }
        return true;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = elems.length - 1;
        int val = Integer.parseInt(elems[0]);
        Node root = new Node(null, val);
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
        Node[] leafs = new Node[n];
        int leafsN = fillLeafs(leafs, root, n);
        heightCalculation(leafs, leafsN);
        if (isAVLBalanced(root, n)) {
            writer.write("YES\n");
        } else {
            writer.write("NO\n");
        }
    }
    
}
/*
input:
10 9 2 1 7 5 4 3 6 8 12 11 13 0
output:
NO

input:
7 3 2 1 9 5 4 6 8 0
output:
YES

Я понял, как надо считать высоту. Высота - это ведь что?
Это расстояние от данной вершины до листа. Значит, надо найти все листы и
пройтись по предкам, обновляя значения высоты для каждой пройденной вершины.
Сложность будет не очень веселой, но зато все будет правильно.
Хотя там может быть оптимизация. Если в какой-то вершине будет значение большее,
чем текущее, то дальше идти не надо. Тогда сложность будет хорошая.
*/