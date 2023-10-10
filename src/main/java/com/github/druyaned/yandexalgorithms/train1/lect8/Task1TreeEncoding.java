package com.github.druyaned.yandexalgorithms.train1.lect8;

/**
 * Декция 8 задача 1.
 * D - в наиболее левого непосещенного ребенка (детей всегда либо 2, либо 0).<br>
 * U - поднимаемся вверх до тех пор, пока приходим из правого ребенка.
 * Если пришли в вершину из левого ребенка - сразу пойдем в правого.<br>
 * По сериализованной записи восстановить код для листьев.
 * 
 * @author druyaned
 */
public class Task1TreeEncoding {
    
    public static class Node {
        public Node left, right, up;
    }
    
    public static void main(String[] args) {
        String sequence1 = "DDUUDU";
        String sequence2 = "DDDUUUDDUDUU";
        Node root1 = recoverTree(sequence1);
        Node root2 = recoverTree(sequence2);
        String tree1 = traverseTree(root1);
        String tree2 = traverseTree(root2);
        System.out.println("sequence1: " + sequence1);
        System.out.println("tree1:     " + tree1);
        System.out.println("sequence2: " + sequence2);
        System.out.println("tree2:     " + tree2);
    }
    
    public static Node recoverTree(String sequence) {
        Node root = new Node();
        recoverTree(root, sequence, 0);
        return root;
    }
    
    private static int recoverTree(Node root, String sequence, int charInd) {
        if (charInd == sequence.length() || charInd == -1) {
            return -1;
        }
        char ch = sequence.charAt(charInd);
        if (ch == 'D') {
            Node newLeft = new Node();
            Node newRight = new Node();
            newLeft.up = root;
            newRight.up = root;
            root.left = newLeft;
            root.right = newRight;
            int newCharInd = recoverTree(newLeft, sequence, charInd + 1);
            return recoverTree(newRight, sequence, newCharInd);
        } else { // U
            Node nowNode = root;
            while (nowNode.up != null && nowNode.up.right == nowNode) {
                nowNode = nowNode.up;
            }
            if (nowNode.up == null) {
                return -1;
            }
            return recoverTree(nowNode.up.right, sequence, charInd + 1);
        }
    }
    
    public static String traverseTree(Node root) {
        StringBuilder treeBuilder = new StringBuilder();
        traverseTree(root, treeBuilder);
        return treeBuilder.toString();
    }
    
    private static void traverseTree(Node root, StringBuilder treeBuilder) {
        if (root.left != null && root.right != null) {
            treeBuilder.append('D');
            traverseTree(root.left, treeBuilder);
            traverseTree(root.right, treeBuilder);
            if (root.up == null) {
                treeBuilder.setLength(treeBuilder.length() - 1);
            }
        } else {
            treeBuilder.append('U');
        }
    }
    
}
