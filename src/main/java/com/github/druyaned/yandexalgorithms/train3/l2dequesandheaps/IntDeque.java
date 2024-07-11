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
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int capacity() {
        return capacity;
    }
    
    public int size() {
        return size;
    }
    
    public int get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException(String.format("index=%d size=%d", i, size));
        }
        return head <= tail ? arr[head + i] :
                i < capacity - head ? arr[head + i] : arr[i + head - capacity];
    }
    
    public int getFirst() {
        return arr[head];
    }
    
    public int getLast() {
        return arr[tail];
    }
    
    public void set(int i, int newVal) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException(String.format("index=%d size=%d", i, size));
        }
        int ind = head <= tail ? head + i :
                i < capacity - head ? head + i : i + head - capacity;
        arr[ind] = newVal;
    }
    
    public void clear() {
        size = head = tail = 0;
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
        System.out.printf("capacity=%d size=%d head=%d tail=%d\n", capacity, size, head, tail);
        if (size == 0) {
            return;
        }
        String elementFormat = " %" + maxLengthOfElement() + "d";
        System.out.print("ind:");
        for (int i = head; ; i = (i + 1) % capacity) {
            System.out.printf(elementFormat, i);
            if (i == tail) {
                break;
            }
        }
        System.out.print("\narr:");
        for (int i = head; ; i = (i + 1) % capacity) {
            System.out.printf(elementFormat, arr[i]);
            if (i == tail) {
                break;
            }
        }
        System.out.println();
    }
    
    private int maxLengthOfElement() {
        int maxLength = 1;
        for (int i = head; ; i = (i + 1) % capacity) {
            int lengthOfElement = Integer.toString(arr[i]).length();
            if (maxLength < lengthOfElement) {
                maxLength = lengthOfElement;
            }
            if (i == tail) {
                break;
            }
        }
        return maxLength;
    }
    
}
