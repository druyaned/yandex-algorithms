package druyaned.yandexalgorithms.train3.l1stack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HW1StackWithErrorProtection {
    
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
        Stack stack = new Stack();
        Executor executor = new Executor();
        executor.add("push", new Push());
        executor.add("pop", new Pop());
        executor.add("back", new Back());
        executor.add("size", new Size());
        executor.add("clear", new Clear());
        executor.add("exit", new Exit());
        executor.run(stack, reader, writer);
    }
    
}

class Stack {
    
    private final int capacity;
    private int size;
    private final int[] arr;
    
    public Stack() {
        capacity = (int)2e6;
        size = 0;
        arr = new int[capacity];
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int back() {
        return arr[size - 1];
    }
    
    public boolean push(int v) {
        if (size == capacity) {
            return false;
        }
        arr[size++] = v;
        return true;
    }
    
    public boolean pop() {
        if (size == 0) {
            return false;
        }
        --size;
        return true;
    }
    
    public void clear() {
        size = 0;
    }
    
}

interface Command {
    
    String execute(Stack stack, Object... args);
    
}

class Push implements Command {
    
    @Override public String execute(Stack stack, Object... args) {
        int v = (Integer)args[0];
        return stack.push(v) ? "ok" : "not added";
    }
    
}

class Pop implements Command {

    @Override public String execute(Stack stack, Object... args) {
        if (stack.isEmpty()) {
            return "error";
        } else {
            int back = stack.back();
            stack.pop();
            return Integer.toString(back);
        }
    }
    
}

class Back implements Command {
    
    @Override public String execute(Stack stack, Object... args) {
        if (stack.isEmpty()) {
            return "error";
        } else {
            return Integer.toString(stack.back());
        }
    }
    
}

class Size implements Command {

    @Override public String execute(Stack stack, Object... args) {
        return Integer.toString(stack.size());
    }
    
}

class Clear implements Command {

    @Override public String execute(Stack stack, Object... args) {
        stack.clear();
        return "ok";
    }
    
}

class Exit implements Command {
    
    @Override public String execute(Stack stack, Object... args) {
        return "bye";
    }
    
}

class Executor {
    
    private final Map<String, Command> commandMap = new HashMap<>();
    
    public void add(String name, Command command) {
        commandMap.put(name, command);
    }
    
    public void run(Stack stack, BufferedReader reader, BufferedWriter writer) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] elems = line.split(" ");
            String name = elems[0];
            Command command = commandMap.get(name);
            if (elems.length == 1) {
                writer.write(command.execute(stack) + "\n");
            } else {
                writer.write(command.execute(stack, Integer.valueOf(elems[1])) + "\n");
            }
            if (name.equals("exit")) {
                break;
            }
        }
    }
    
}

/*
Какой план выполнения?
class Stack; interface Command; class Executor.
Stack: {capacity; size; arr};
Command: {String execute(Object... args)};
Executor: {Map commandMap; void add(String name, Command c); void run()};

push n
Добавить в стек число n (значение n задается после команды). Программа должна вывести ok.

pop
Удалить из стека последний элемент. Программа должна вывести его значение. (error)

back
Программа должна вывести значение последнего элемента, не удаляя его из стека. (error)

size
Программа должна вывести количество элементов в стеке.

clear
Программа должна очистить стек и вывести ok.

exit
Программа должна вывести bye и завершить работу.
*/