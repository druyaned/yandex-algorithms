package com.github.druyaned.yandexalgorithms.train1.lect3;

public class IntList {
    
    public static final int MAX_CAPACITY = 0x800000;
    public static final int DEFAULT_CAPACITY = 0x10;
    
    private int capacity;
    private int size;
    private int[] array;
    
    public IntList() {
        capacity = DEFAULT_CAPACITY;
        size = 0;
        array = new int[capacity];
    }
    
    public IntList(int initialCapacity) {
        if (initialCapacity < DEFAULT_CAPACITY) {
            capacity = DEFAULT_CAPACITY;
        } else if (MAX_CAPACITY < initialCapacity) {
            throw new IllegalArgumentException("capacity (" + initialCapacity +
                    ") must be <= " + MAX_CAPACITY);
        } else {
            capacity = DEFAULT_CAPACITY;
            while (capacity < initialCapacity) {
                capacity <<= 1;
            }
        }
        size = 0;
        array = new int[capacity];
    }
    
    public IntList(int... array) {
        if (array.length < DEFAULT_CAPACITY) {
            this.capacity = DEFAULT_CAPACITY;
        } else if (MAX_CAPACITY < array.length) {
            throw new IllegalArgumentException("capacity (" + array.length +
                    ") must be <= " + MAX_CAPACITY);
        } else {
            this.capacity = DEFAULT_CAPACITY;
            while (this.capacity < array.length) {
                this.capacity <<= 1;
            }
        }
        this.size = array.length;
        this.array = new int[capacity];
        System.arraycopy(array, 0, this.array, 0, array.length);
    }
    
    public int capacity() {
        return capacity;
    }
    
    public int size() {
        return size;
    }
    
    public int get(int i) {
        adjustIndex(i);
        return array[i];
    }
    
    public void set(int i, int newValue) {
        adjustIndex(i);
        array[i] = newValue;
    }
    
    public boolean add(int value) {
        if (size == MAX_CAPACITY) {
            return false;
        }
        if (size == capacity) {
            arrayExtension();
        }
        array[size++] = value;
        return true;
    }
    
    public boolean remove() {
        if (size == 0) {
            return false;
        }
        --size;
        return true;
    }
    
    public int[] toArray() {
        int[] copy = new int[size];
        System.arraycopy(array, 0, copy, 0, size);
        return copy;
    }
    
    private void adjustIndex(int i) {
        if (size <= i || i < 0) {
            throw new IndexOutOfBoundsException(i + " is out of range [0, " + size + ")");
        }
    }
    
    private void arrayExtension() {
        capacity <<= 1;
        int[] oldArray = array;
        array = new int[capacity];
        System.arraycopy(oldArray, 0, array, 0, size);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{capacity=").append(capacity)
                .append(", size=").append(size)
                .append(", array=[");
        if (0 < size) {
            builder.append(array[0]);
        }
        for (int i = 1; i < size; ++i) {
            builder.append(", ").append(array[i]);
        }
        return builder.append("]}").toString();
    }
    
}
