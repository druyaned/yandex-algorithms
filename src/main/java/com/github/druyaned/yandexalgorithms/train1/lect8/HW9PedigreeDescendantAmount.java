package com.github.druyaned.yandexalgorithms.train1.lect8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

public class HW9PedigreeDescendantAmount {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Node {
        private final int id;
        private final String name;
        private Node ancestor;
        private Node[] descendants;
        private int height;
        private int descendantAmount;
        public Node(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    
    private static void showNodes(Node[] nodes, int nodesN) {
        for (int i = 0; i < nodesN; ++i) {
            System.out.printf("[%d]%s", nodes[i].id, nodes[i].name);
            if (nodes[i].ancestor != null) {
                System.out.printf("(%s)", nodes[i].ancestor.name);
            }
            Node[] d = nodes[i].descendants;
            if (d == null) {
                System.out.printf(" h=%d cnt=%d\n", nodes[i].height, nodes[i].descendantAmount);
                continue;
            }
            System.out.print("={");
            final int l = d.length;
            if (l != 0) {
                System.out.printf("%s", d[0].name);
            }
            for (int j = 1; j < l; ++j) {
                System.out.printf(" %s", d[j].name);
            }
            System.out.printf("} h=%d cnt=%d\n", nodes[i].height, nodes[i].descendantAmount);
        }
    }
    
    private static Node fillDescendants(Node[] nodes, int nodesN) {
        Node root = nodes[0];
        for (int i = 0, ind[] = new int[nodesN]; i < nodesN; ++i) {
            Node node = nodes[i];
            Node ancestor = node.ancestor;
            if (ancestor != null) {
                if (ancestor.descendants == null) {
                    ancestor.descendants = new Node[ancestor.descendantAmount];
                }
                ancestor.descendants[ind[ancestor.id]++] = node;
            } else {
                root = node;
            }
        }
        return root;
    }
    
    private static Node[][] getHeights(Node root, int nodesN) {
        Node[] stack = new Node[nodesN];
        int stackSize = 0;
        stack[stackSize++] = root;
        int maxHeight = 0;
        int[] heightAmount = new int[nodesN + 1];
        heightAmount[0] = 1;
        for (int i = 0; stackSize != 0; ++i) {
            Node node = stack[--stackSize];
            int height = node.height + 1;
            heightAmount[height] += node.descendantAmount;
            for (int j = 0; j < node.descendantAmount; ++j) {
                final Node subNode = node.descendants[j];
                stack[stackSize++] = subNode;
                subNode.height = height;
                if (maxHeight < subNode.height) {
                    maxHeight = subNode.height;
                }
            }
        }
        int heightsN = maxHeight + 1;
        Node[][] heights = new Node[heightsN][];
        for (int i = 0; i < heightsN; ++i) {
            heights[i] = new Node[heightAmount[i]];
        }
        stack[stackSize++] = root;
        for (int i = 0, ind[] = new int[heightsN]; stackSize != 0; ++i) {
            Node node = stack[--stackSize];
            for (int j = 0; j < node.descendantAmount; ++j) {
                final Node subNode = node.descendants[j];
                stack[stackSize++] = subNode;
            }
            int height = node.height;
            heights[height][ind[height]++] = node;
        }
        return heights;
    }
    
    private static void showHeights(Node[][] heights) {
        for (int i = 0; i < heights.length; ++i) {
            System.out.printf("%d:", i);
            for (int j = 0; j < heights[i].length; ++j) {
                System.out.printf(" %s", heights[i][j].name);
            }
            System.out.println();
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        final int CAPACITY = 2 * n;
        HashMap<String, Node> nameNodeMap = new HashMap();
        Node[] nodes = new Node[CAPACITY];
        int nodesN = 0;
        for (int i = 0; i < n - 1; ++i) {
            String[] elems = reader.readLine().split(" ");
            String nodeName = elems[0];
            String ancestorName = elems[1];
            Node node = nameNodeMap.get(nodeName);
            if (node == null) {
                node = new Node(nodesN, nodeName);
                nameNodeMap.put(nodeName, node);
                nodes[nodesN++] = node;
            }
            Node ancestor = nameNodeMap.get(ancestorName);
            if (ancestor == null) {
                ancestor = new Node(nodesN, ancestorName);
                nameNodeMap.put(ancestorName, ancestor);
                nodes[nodesN++] = ancestor;
            }
            node.ancestor = ancestor;
            ++ancestor.descendantAmount;
        }
        Arrays.sort(nodes, 0, nodesN, (x1, x2) -> x1.name.compareTo(x2.name));
        Node root = fillDescendants(nodes, nodesN);
        Node[][] heights = getHeights(root, nodesN);
        int heightsN = heights.length;
        for (int i = heightsN - 1; i > 0; --i) {
            Node[] list = heights[i];
            for (int j = 0; j < list.length; ++j) {
                Node node = list[j];
                Node ancestor = node.ancestor;
                ancestor.descendantAmount += node.descendantAmount;
            }
        }
        for (int i = 0; i < nodesN; ++i) {
            final Node node = nodes[i];
            writer.write(node.name + " " + node.descendantAmount + "\n");
        }
    }
    
}
/*
Ну а тут надо прям подумать.
Короче, появилась классная идея. Распределить все ноды по
уровням. Корень - это уровень 0. Его потомки - это уровень 1.
Все ноды загоняю в массив и сортирую его по убыванию уровня.
Потом прохожу по массиву, добавляя к количеству предка
количество текущего потомка + 1. Тогда сложность будет O(n).
Немножко подучу английский: предок=ancestor; потомок=descendant.

Общее решение: обход дерева за O(n) с подсчетом всех потомков.
Чтоб это реализовать, надо идти от листьев до корня.
Для каждого значения высоты дерева надо хранить список узлов на
данной высоте. Буду идти от максимальной высоты, добавляя
текущее количество потомков к текущему предку.
Как хранить узлы на данной высоте?
Надо построить дерево. Что должна хранить структура узла?
Id, имя, предка, потомков, высоту. Остальные данные можно хранить
отдельно, обращаясь к элементам по id. После построения дерева
нужно пройтись по дереву, используя стек, и загонять каждый узел
к списку листьев на данной высоте. Потом пройтись по высотам,
начиная с конца, обновляя количество потомков для текущего
предка.
*/