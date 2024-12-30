package druyaned.yandexalgorithms.train3.l2dequesandheaps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW1QueueWithErrorProtection {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int CAPACITY = (int)2e6;
        Deque deque = new Deque(CAPACITY);
        Executor executor = new Executor(deque, reader, writer);
        executor.run();
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

        public void clear() {
            head = tail = size = 0;
        }

    }
    
    private static class Executor {

        private final Deque deque;
        private final BufferedReader reader;
        private final BufferedWriter writer;

        public Executor(Deque deque, BufferedReader reader, BufferedWriter writer) {
            this.deque = deque;
            this.reader = reader;
            this.writer = writer;
        }

        public void run() throws IOException {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] elems = line.split(" ");
                String name = elems[0];
                switch (name) {
                    case "push" -> {
                        deque.addLast(Integer.parseInt(elems[1]));
                        writer.write("ok\n");
                    }
                    case "pop" -> {
                        if (deque.size() == 0) {
                            writer.write("error\n");
                        } else {
                            writer.write(Integer.toString(deque.getFirst()) + "\n");
                            deque.removeFirst();
                        }
                    }
                    case "front" -> {
                        if (deque.size() == 0) {
                            writer.write("error\n");
                        } else {
                            writer.write(Integer.toString(deque.getFirst()) + "\n");
                        }
                    }
                    case "size" -> {
                        writer.write(Integer.toString(deque.size()) + "\n");
                    }
                    case "clear" -> {
                        deque.clear();
                        writer.write("ok\n");
                    }
                    default -> {
                        writer.write("bye\n");
                        return;
                    }
                }
            }
        }

    }
    
}
