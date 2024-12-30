package druyaned.yandexalgorithms.train3.l2dequesandheaps;

/**
 * A binary tree with a minimal element at the root.
 * To reach that state every ancestor must be not greater than any descendant.
 * The tree is stored as an array where index of an ancestor is <code>(i-1)/2</code>
 * and indexes of descendants are <code>2*i+1</code> and <code>2*i+2</code>.
 * @param <T> the type of objects that this object may be compared to
 * @author ed
 */
public class HeapMin<T extends Comparable<T>> {
    
//-Fields-------------------------------------------------------------------------------------------
    
    private final int capacity;
    private int size;
    private final Object[] arr;
    
//-Constructors-------------------------------------------------------------------------------------
    
    public HeapMin(int capacity) {
        this.capacity = capacity;
        size = 0;
        arr = new Object[capacity];
    }
    
//-Getters------------------------------------------------------------------------------------------
    
    public int capacity() {
        return capacity;
    }
    
    public int size() {
        return size;
    }
    
    public T root() {
        return (T)arr[0];
    }
    
//-Modifiers----------------------------------------------------------------------------------------
    
    public boolean add(T val) {
        if (size == capacity) {
            return false;
        }
        arr[size] = val;
        int i = size++;
        int a = (i - 1) / 2; // ancestor
        while (a >= 0 && ((T)arr[a]).compareTo((T)arr[i]) > 0) {
            swap(a, i);
            i = a;
            a = (i - 1) / 2;
        }
        return true;
    }
    
    public T pop() {
        if (size == 0) {
            return null;
        }
        T root = (T)arr[0];
        arr[0] = arr[--size];
        int i = 0;
        int l = 1, r = 2, d; // l, r - left and right descendants; d - min descendant
        while (l < size) {
            d = r < size && ((T)arr[r]).compareTo((T)arr[l]) < 0 ? r : l;
            if (((T)arr[i]).compareTo((T)arr[d]) > 0) {
                swap(i, d);
            } else {
                break;
            }
            i = d;
            l = 2 * i + 1;
            r = 2 * i + 2;
        }
        return root;
    }
    
//-Methods------------------------------------------------------------------------------------------
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public void show() {
        if (size == 0) {
            System.out.println("The heap is empty");
            return;
        }
        checkDescendants();
        System.out.printf("heap: capacity=%d size=%d\n", capacity, size);
        int l = maxLengthOfElement();
        String element = "%" + l + "d";
        int p = power();
        int m = l / 2 + 1;
        int d = (l - 1) / 2;
        int nextLineCount = 2;
        System.out.print(" ".repeat((p-1) * m));
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                System.out.printf(element + "\n", arr[i]);
            } else if (i == nextLineCount - 2) {
                nextLineCount <<= 1;
                System.out.printf(element + "\n", arr[i]);
                p /= 2;
                System.out.print(" ".repeat((p-1) * m));
            } else {
                System.out.printf(element + "%s", arr[i], " ".repeat((2*p-1) * m - d));
            }
        }
    }
    
//-Private-methods----------------------------------------------------------------------------------
    
    private void swap(int i1, int i2) {
        Object toSwap = arr[i2];
        arr[i2] = arr[i1];
        arr[i1] = toSwap;
    }
    
    private void checkDescendants() {
        for (int i = 0; i < size; i++) {
            int l = 2 * i + 1, r = 2 * i + 2;
            T arrI = (T)arr[i];
            if (r < size && (arrI.compareTo((T)arr[l]) > 0 || arrI.compareTo((T)arr[r]) > 0)) {
                throw new IllegalStateException(String.format("In HeapMin " +
                        "ancestor must be not greater than any descendant: " +
                        "%s[%d]: %s[%d] %s[%d]", arr[i], i, arr[l], l, arr[r], r));
            }
            if (l < size && arrI.compareTo((T)arr[l]) > 0) {
                throw new IllegalStateException(String.format("In HeapMin " +
                        "ancestor must be not greater than any descendant: " +
                        "%s[%d]: %s[%d]", arr[i], i, arr[l], l));
            }
        }
    }
    
    private int maxLengthOfElement() {
        int maxLength = 1;
        for (int i = 0; i < size; i++) {
            int lengthOfElement = arr[i].toString().length();
            if (maxLength < lengthOfElement) {
                maxLength = lengthOfElement;
            }
        }
        return maxLength;
    }
    
    private int power() {
        int sizeCopy = size;
        int p = 1;
        while ((sizeCopy /= 2) > 0) {
            p *= 2;
        }
        return p;
    }
    
}
/*
ancestor: (i-1)/2
descendants: l=2*i+1, r=2*i+2

indexes:
            0
      .-----^-----.
      1           2
   .--^--.     .--^--.
   3     4     5     6
 .-^-. .-^-. .-^-. .-^-.
 7   8 9  10 11 12 13 14

data:
            1
      .-----^-----.
      3           2
   .--^--.     .--^--.
   7     4     5     6
 .-^-.
 9   8

Calculate spaces to show (len == maxLengthOfElement):
m=len/2+1 d=(len-1)/2
len=1 m=1 d=0
| 7*1       | p=8
| 3*1 7*1-0 | p=4
| 1*1 3*1-0 | p=2
| 0*1 1*1-0 | p=1
len=2  m=2 d=0
| 7*2       | p=8
| 3*2 7*2-0 | p=4
| 1*2 3*2-0 | p=2
| 0*2 1*2-0 | p=1
len=3  m=2 d=1
| 7*2       | p=8
| 3*2 7*2-1 | p=4
| 1*2 3*2-1 | p=2
| 0*2 1*2-1 | p=1
len=4  m=3 d=1
| 7*3       | p=8
| 3*3 7*3-1 | p=4
| 1*3 3*3-1 | p=2
| 0*3 1*3-1 | p=1
len=5  m=3 d=2
| 7*3       | p=8
| 3*3 7*3-2 | p=4
| 1*3 3*3-2 | p=2
| 0*3 1*3-2 | p=1
*/
