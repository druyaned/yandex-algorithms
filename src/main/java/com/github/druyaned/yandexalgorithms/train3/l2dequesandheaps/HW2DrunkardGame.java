package com.github.druyaned.yandexalgorithms.train3.l2dequesandheaps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW2DrunkardGame {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static final char[] buf = new char[16];
    
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
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int CAPACITY = 10;
        Deque player1 = new Deque(CAPACITY);
        Deque player2 = new Deque(CAPACITY);
        for (int i = 0; i < CAPACITY / 2; ++i) {
            player1.addLast(readInt(reader));
        }
        for (int i = 0; i < CAPACITY / 2; ++i) {
            player2.addLast(readInt(reader));
        }
        int moves = 0;
        int moveLimit = (int)1e6;
        while (player1.size() != 0 && player2.size() != 0 && moves != moveLimit) {
            int card1 = player1.getFirst();
            int card2 = player2.getFirst();
            player1.removeFirst();
            player2.removeFirst();
            if (card1 == 0 && card2 == 9) {
                player1.addLast(card1);
                player1.addLast(card2);
            } else if (card1 == 9 && card2 == 0) {
                player2.addLast(card1);
                player2.addLast(card2);
            } else if (card1 > card2) {
                player1.addLast(card1);
                player1.addLast(card2);
            } else if (card1 < card2) {
                player2.addLast(card1);
                player2.addLast(card2);
            }
            ++moves;
        }
        if (moves == moveLimit) {
            writer.write("botva\n");
        } else if (player1.size() == 0) {
            writer.write("second " + moves);
        } else {
            writer.write("first " + moves);
        }
    }
    
    private static class Deque {

        private final int capacity;
        private int size;
        private final int[] arr;
        private int head, tail;

        public Deque(int capacity) {
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

        public void clear() {
            size = head = tail = 0;
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
    
}

