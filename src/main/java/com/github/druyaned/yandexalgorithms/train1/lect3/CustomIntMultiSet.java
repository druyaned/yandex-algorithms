package com.github.druyaned.yandexalgorithms.train1.lect3;

public class CustomIntMultiSet {
    
    public static final int CAPACITY = 0xff;
    
    private final int[][] hashTable = new int[CAPACITY][];
    private final int[] sizes = new int[CAPACITY];
    
    public boolean add(int value) {
        int i = hash(value);
        if (hashTable[i] == null) {
            hashTable[i] = new int[CAPACITY];
        }
        if (sizes[i] == CAPACITY) {
            return false;
        }
        hashTable[i][sizes[i]++] = value;
        return true;
    }
    
    public boolean contains(int value) {
        int i = hash(value);
        if (hashTable[i] == null) {
            return false;
        }
        for (int j = 0; j < sizes[i]; ++j) {
            if (hashTable[i][j] == value) {
                return true;
            }
        }
        return false;
    }
    
    public boolean remove(int value) {
        int i = hash(value);
        if (hashTable[i] == null) {
            return false;
        }
        for (int j = 0; j < sizes[i]; ++j) {
            if (hashTable[i][j] == value) {
                int lastValue = hashTable[i][sizes[i]-- - 1];
                hashTable[i][j] = lastValue;
                return true;
            }
        }
        return false;
    }
    
    private int hash(int value) {
        return value < 0 ? -value % CAPACITY : value % CAPACITY;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{hashTable=[");
        int i = 0;
        while (i < CAPACITY && hashTable[i] == null) {
            ++i;
        }
        if (i < CAPACITY) {
            addArray(builder, sizes[i], hashTable[i]);
        }
        for (++i; i < CAPACITY; ++i) {
            if (hashTable[i] != null) {
                builder.append(", ");
                addArray(builder, sizes[i], hashTable[i]);
            }
        }
        return builder.append("]}").toString();
    }
    
    private static void addArray(StringBuilder builder, final int N, int[] array) {
        builder.append("{size=").append(N)
                .append(", array=[");
        if (0 < N) {
            builder.append(array[0]);
        }
        for (int i = 1; i < N; ++i) {
            builder.append(", ").append(array[i]);
        }
        builder.append("]}");
    }
    
}
