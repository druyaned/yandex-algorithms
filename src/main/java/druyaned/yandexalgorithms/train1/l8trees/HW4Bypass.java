package druyaned.yandexalgorithms.train1.l8trees;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4Bypass {
    
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
            this.left = null;
            this.right = null;
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
            boolean equal, less, more;
            while (true) {
                equal = val == node.val;
                less = val < node.val;
                more = val > node.val;
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
        Node node = root;
        while (node != null) {
            stack[stackSize++] = node;
            node = node.left;
        }
        while (stackSize != 0) {
            node = stack[--stackSize];
            Node subNode = node.right;
            while (subNode != null) {
                stack[stackSize++] = subNode;
                subNode = subNode.left;
            }
            writer.write(node.val + "\n");
        }
    }
    
}
/*
Вывод в порядке возрастания.
Это значит, если я достиг самого левого во время обхода, то надо его вывести.
Также надо хранить в стеке тех, что еще не выведены.
Последний элемент стека либо не имеет левого сына, либо левый сын посещен.
Остается добавить всех тех, что самые левые у правого.

input:
10 9 2 1 7 5 4 3 6 8 12 11 13 0
output:
1
2
3
4
5
6
7
8
9
10
11
12
13
*/
