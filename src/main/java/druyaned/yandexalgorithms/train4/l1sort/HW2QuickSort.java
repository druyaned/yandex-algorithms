package druyaned.yandexalgorithms.train4.l1sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class HW2QuickSort {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
//            solve(reader, writer);
            clearSolve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static final char[] buf = new char[15];
    
    private static int readInt(Reader reader) throws IOException {
        int c;
        while ((c = reader.read()) != -1 && c != '+' && c != '-' && !('0' <= c && c <= '9')) {}
        if (c == -1) {
            throw new NumberFormatException("empty input");
        }
        buf[0] = (char)c;
        int l = 1;
        while ((c = reader.read()) != -1 && '0' <= c && c <= '9') {
            buf[l++] = (char)c;
        }
        return Integer.parseInt(new String(buf, 0, l));
    }
    
    private static void swap(int[] arr, int i1, int i2) {
        int toSwap = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = toSwap;
    }
    
    private static void show(int n, int[] arr) {
        System.out.print("\nind:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2d", i);
        }
        System.out.print("\narr:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2d", arr[i]);
        }
        System.out.println();
    }
    
    private static void showToPivot(int n, int[] arr, int c, int p) {
        System.out.print("\nind:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2d", i);
        }
        System.out.print("\narr:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2d", arr[i]);
        }
        System.out.print("\nptr:");
        for (int i = 0; i < n; ++i) {
            if (i == c) {
                System.out.printf(" %2c", 'c');
            } else if (i == p) {
                System.out.printf(" %2c", 'p');
            } else {
                System.out.print("   ");
            }
        }
        System.out.println();
    }
    
    private static void showToPivot(int n, int[] arr, int l, int p, int g) {
        System.out.print("\nind:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2d", i);
        }
        System.out.print("\narr:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2d", arr[i]);
        }
        System.out.print("\nptr:");
        for (int i = 0; i < n; ++i) {
            if (i == l) {
                System.out.printf(" %2c", 'l');
            } else if (i == p) {
                System.out.printf(" %2c", 'p');
            } else if (i == g) {
                System.out.printf(" %2c", 'g');
            } else {
                System.out.print("   ");
            }
        }
        System.out.println();
    }
    
    private static void showToOffset(int n, int[] arr, int e, int o, int g) {
        System.out.print("\nind:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2d", i);
        }
        System.out.print("\narr:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2d", arr[i]);
        }
        System.out.print("\nptr:");
        for (int i = 0; i < n; ++i) {
            if (i == e) {
                System.out.printf(" %2c", 'e');
            } else if (i == o) {
                System.out.printf(" %2c", 'o');
            } else if (i == g) {
                System.out.printf(" %2c", 'g');
            } else {
                System.out.print("   ");
            }
        }
        System.out.println();
    }
    
    private static void showToOffset(int n, int[] arr, int p, int o) {
        System.out.print("\nind:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2d", i);
        }
        System.out.print("\narr:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %2d", arr[i]);
        }
        System.out.print("\nptr:");
        for (int i = 0; i < n; ++i) {
            if (i == p) {
                System.out.printf(" %2c", 'p');
            } else if (i == o) {
                System.out.printf(" %2c", 'o');
            } else {
                System.out.print("   ");
            }
        }
        System.out.println();
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int[] arr = new int[n];
        for (int i = 0; i < n; ++i) {
            arr[i] = readInt(reader);
        }
        if (n == 0) {
            return;
        }
        show(n, arr); // TODO: debug
        int[] paramFirst = new int[n];
        int[] paramLast = new int[n];
        paramFirst[0] = 0;
        paramLast[0] = n - 1;
        int size = 1;
        Random generator = new Random();
        while (size > 0) {
            int first = paramFirst[--size], last = paramLast[size];
            int choosePivot = first + generator.nextInt(last - first + 1);
            int lessCount = 0, equalCount = 0;
            for (int i = first; i <= last; ++i) {
                if (arr[choosePivot] > arr[i]) {
                    lessCount++;
                }
                if (arr[choosePivot] == arr[i]) {
                    equalCount++;
                }
            }
            int pivot = first + lessCount;
            showToPivot(n, arr, choosePivot, pivot); // TODO: debug
            swap(arr, choosePivot, pivot);
            for (int less = first, greater = last;
                    less < pivot && pivot < greater;
                    ++less, --greater) {
                while (less < pivot && arr[pivot] > arr[less]) {
                    less++;
                }
                while (pivot < greater && arr[pivot] <= arr[greater]) {
                    greater--;
                }
                if (less < pivot && pivot < greater) {
                    showToPivot(n, arr, less, pivot, greater); // TODO: debug
                    swap(arr, less, greater);
                }
            }
            int offset = pivot + equalCount - 1;
            swap(arr, pivot, offset);
            showToOffset(n, arr, pivot, offset); // TODO: debug
            for (int greater = last, equal = pivot;
                    equal < offset && offset < greater;
                    ++equal, --greater) {
                while (equal < offset && arr[equal] == arr[offset]) {
                    equal++;
                }
                while (offset < greater && arr[offset] < arr[greater]) {
                    greater--;
                }
                if (equal < offset && offset < greater) {
                    showToOffset(n, arr, equal, offset, greater); // TODO: debug
                    swap(arr, equal, greater);
                }
            }
            if (first < pivot - 1) {
                paramFirst[size] = first;
                paramLast[size++] = pivot - 1;
            }
            if (offset + 1 < last) {
                paramFirst[size] = offset + 1;
                paramLast[size++] = last;
            }
        }
        writer.write(Integer.toString(arr[0]));
        for (int i = 1; i < n; ++i) {
            writer.write(" " + arr[i]);
        }
        writer.write('\n');
    }
    
    public static void clearSolve(BufferedReader reader, BufferedWriter writer)
            throws IOException {
        int n = readInt(reader);
        if (n == 0) {
            return;
        }
        int[] arr = new int[n];
        for (int i = 0; i < n; ++i) {
            arr[i] = readInt(reader);
        }
        int[] paramFirst = new int[n];
        int[] paramLast = new int[n];
        paramFirst[0] = 0;
        paramLast[0] = n - 1;
        int size = 1;
        Random generator = new Random();
        while (size > 0) {
            int first = paramFirst[--size], last = paramLast[size];
            int choosePivot = first + generator.nextInt(last - first + 1);
            int lessCount = 0, equalCount = 0;
            for (int i = first; i <= last; ++i) {
                if (arr[choosePivot] > arr[i]) {
                    lessCount++;
                }
                if (arr[choosePivot] == arr[i]) {
                    equalCount++;
                }
            }
            int pivot = first + lessCount;
            swap(arr, choosePivot, pivot);
            for (int less = first, greater = last;
                    less < pivot && pivot < greater;
                    ++less, --greater) {
                while (less < pivot && arr[pivot] > arr[less]) {
                    less++;
                }
                while (pivot < greater && arr[pivot] <= arr[greater]) {
                    greater--;
                }
                if (less < pivot && pivot < greater) {
                    swap(arr, less, greater);
                }
            }
            int offset = pivot + equalCount - 1;
            swap(arr, pivot, offset);
            for (int greater = last, equal = pivot;
                    equal < offset && offset < greater;
                    ++equal, --greater) {
                while (equal < offset && arr[equal] == arr[offset]) {
                    equal++;
                }
                while (offset < greater && arr[offset] < arr[greater]) {
                    greater--;
                }
                if (equal < offset && offset < greater) {
                    swap(arr, equal, greater);
                }
            }
            if (first < pivot - 1) {
                paramFirst[size] = first;
                paramLast[size++] = pivot - 1;
            }
            if (offset + 1 < last) {
                paramFirst[size] = offset + 1;
                paramLast[size++] = last;
            }
        }
        writer.write(Integer.toString(arr[0]));
        for (int i = 1; i < n; ++i) {
            writer.write(" " + arr[i]);
        }
        writer.write('\n');
    }
    
}
/*
input:
15
2 1 4 1 5 3 5 4 5 1 4 2 9 1 2
output:
1 1 1 1 2 2 2 3 4 4 4 5 5 5 9

n=15
ind: 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14
arr: 2  1  4  1  5  3  5  4  5  1  4  2  9  1  2
choosePivot=7 arr[choosePivot]=4 lessCount=8 equalCount=3
pivot = lessCount + first = 8
offset = pivot + equalCount - 1 = 10

ind:  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14
arr:  2  1  4  1  5  3  5  4  5  1  4  2  9  1  2
ptr:                       c  p                  

ind:  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14
arr:  2  1  4  1  5  3  5  5  4  1  4  2  9  1  2
ptr:        l                 p                 g

ind:  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14
arr:  2  1  2  1  5  3  5  5  4  1  4  2  9  1  4
ptr:              l           p              g   

ind:  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14
arr:  2  1  2  1  1  3  5  5  4  1  4  2  9  5  4
ptr:                    l     p        g         

ind:  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14
arr:  2  1  2  1  1  3  2  5  4  1  4  5  9  5  4
ptr:                       l  p  g               

ind:  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14
arr:  2  1  2  1  1  3  2  1  4  5  4  5  9  5  4
ptr:                          e     o            

ind:  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14
arr:  2  1  2  1  1  3  2  1  4  5  4  5  9  5  4
ptr:                             e  o           g

quicksort(first, pivot - 1;
quicksort(offset - 1, last);
*/
