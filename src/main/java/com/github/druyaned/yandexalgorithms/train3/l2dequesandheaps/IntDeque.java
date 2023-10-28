package com.github.druyaned.yandexalgorithms.train3.l2dequesandheaps;

public class IntDeque {
    
    private final int capacity;
    private int size;
    private final int[] arr;
    private int head, tail;
    
    public IntDeque(int capacity) {
        this.capacity = capacity;
        size = 0;
        arr = new int[capacity];
        head = tail = 0;
    }
    
    public int size() {
        return size;
    }
    
    public int get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException(String.format("index=%d size=%d", i, size));
        }
        return head <= tail ? arr[head + i] :
                i < capacity - head ? arr[head + i] : arr[i - capacity + head];
    }
    
    public int getFirst() {
        return arr[head];
    }
    
    public int getLast() {
        return arr[tail];
    }
    
    public boolean addFirst(int val) {
        if (size == capacity) {
            return false;
        }
        if (size == 0) {
            arr[0] = val;
            size = 1;
            return true;
        }
        if (head == 0) {
            arr[head = capacity - 1] = val;
        } else {
            arr[--head] = val;
        }
        ++size;
        return true;
    }
    
    public boolean addLast(int val) {
        if (size == capacity) {
            return false;
        }
        if (size == 0) {
            arr[0] = val;
            size = 1;
            return true;
        }
        if (tail == capacity - 1) {
            arr[tail = 0] = val;
        } else {
            arr[++tail] = val;
        }
        ++size;
        return true;
    }
    
    public boolean removeFirst() {
        if (size == 0) {
            return false;
        }
        if (size == 1) {
            head = tail = 0;
            size = 0;
            return true;
        }
        if (head == capacity - 1) {
            head = 0;
        } else {
            ++head;
        }
        --size;
        return true;
    }
    
    public boolean removeLast() {
        if (size == 0) {
            return false;
        }
        if (size == 1) {
            head = tail = 0;
            size = 0;
            return true;
        }
        if (tail == 0) {
            tail = capacity - 1;
        } else {
            --tail;
        }
        --size;
        return true;
    }
    
    public void show() {
        System.out.printf("head=%d tail=%d size=%d capacity=%d\n", head, tail, size, capacity);
        if (size == 0) {
            return;
        }
        if (head <= tail) {
            System.out.print("ind:");
            for (int i = head; i <= tail; ++i) {
                System.out.printf(" %2d", i);
            }
            System.out.println();
            System.out.print("arr:");
            for (int i = head; i <= tail; ++i) {
                System.out.printf(" %2d", arr[i]);
            }
            System.out.println();
        } else {
            System.out.print("ind:");
            for (int i = head; i < capacity; ++i) {
                System.out.printf(" %2d", i);
            }
            for (int i = 0; i <= tail; ++i) {
                System.out.printf(" %2d", i);
            }
            System.out.println();
            System.out.print("arr:");
            for (int i = head; i < capacity; ++i) {
                System.out.printf(" %2d", arr[i]);
            }
            for (int i = 0; i <= tail; ++i) {
                System.out.printf(" %2d", arr[i]);
            }
            System.out.println();
        }
    }
    
}
