package druyaned.yandexalgorithms.train5.l1complexity.p10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DocumentFormatting {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int textWidth = readInt(reader);
        int lineHeight = readInt(reader);
        int charLength = readInt(reader);
        Text text = new Text(reader, textWidth, lineHeight, charLength);
        Cursor cursor = new Cursor(lineHeight);
        Nodes nodes = new Nodes();
        parseText(text, cursor, nodes);
        for (Token token : text.tokens) {
            if (token instanceof Image) {
                writer.write(String.format("%d %d\n", token.startX, token.startY));
            }
        }
    }
    
    private static class Text {
        private final int MAX_LENGTH = 2000;
        private final List<Token> tokens = new ArrayList(MAX_LENGTH);
        private int i = 0;
        private final int width, lineHeight, charLength;
        private final char[] raw;
        private Text(BufferedReader reader, int width, int lineHeight, int charLength)
                throws IOException {
            this.width = width;
            this.lineHeight = lineHeight;
            this.charLength = charLength;
            Paragraph firstParagraph = new Paragraph();
            firstParagraph.height = lineHeight;
            tokens.add(firstParagraph);
            char[] buffer = new char[MAX_LENGTH];
            int n = reader.read(buffer);
            if (n != -1) {
                raw = new char[n];
            } else {
                raw = new char[n = 0];
            }
            System.arraycopy(buffer, 0, raw, 0, n);
        }
    }
    
    private static class Cursor {
        private int x, y;
        private int h;
        private Cursor(int lineHeight) {
            this.h = lineHeight;
        }
        public void newLine(int lineHeight, Nodes nodes) {
            x = 0;
            y += h;
            h = lineHeight;
            resetNodesByY(nodes.iter, y);
        }
    }
    
    private static class Nodes {
        private static class Node {
            private final int startX, stopX, stopY;
            private Node next;
            private Node(int startX, int stopX, int stopY, Node next) {
                this.startX = startX;
                this.stopX = stopX;
                this.stopY = stopY;
                this.next = next;
            }
        }
        private class Iterator {
            private Node prev, next;
            public void addBefore(int startX, int stopX, int stopY) {
                Node node = new Node(startX, stopX, stopY, next);
                if (prev != null) {
                    prev.next = node;
                } else {
                    first = node;
                }
                prev = node;
            }
            public boolean removeNext() {
                if (next == null) {
                    return false;
                }
                if (prev != null) {
                    prev.next = next.next;
                } else {
                    first = next.next;
                }
                next = next.next;
                return true;
            }
            public boolean moveForward() {
                if (next != null) {
                    prev = next;
                    next = next.next;
                    return true;
                }
                return false;
            }
            public void moveBeforeFirst() {
                prev = null;
                next = first;
            }
        }
        private Node first;
        private final Iterator iter;
        private Nodes() {
            iter = new Iterator();
        }
        public boolean isEmpty() {
            return first == null;
        }
        public void clear() {
            first = iter.prev = iter.next = null;
        }
    }
    
    private static abstract class Token {
        protected int startX, startY, width, height;
        abstract void process(Text text, Cursor cursor, Nodes nodes);
    }
    
    private static class Paragraph extends Token {
        @Override public void process(Text text, Cursor cursor, Nodes nodes) {
            final Nodes.Iterator iter = nodes.iter;
            int maxStopY = cursor.y + cursor.h;
            for (iter.moveBeforeFirst(); iter.next != null; iter.moveForward()) {
                if (maxStopY < iter.next.stopY) {
                    maxStopY = iter.next.stopY;
                }
            }
            iter.moveBeforeFirst();
            nodes.clear();
            startX = cursor.x = 0;
            startY = cursor.y = maxStopY;
            width = 0;
            height = cursor.h = text.lineHeight;
            text.tokens.add(this);
        }
    }
    
    private static class Word extends Token {
        private Word(String value, int charLength) {
            width = value.length() * charLength;
        }
        @Override public void process(Text text, Cursor cursor, Nodes nodes) {
            final Nodes.Iterator iter = nodes.iter;
            boolean notPlaced = true;
            while (notPlaced) {
                int wordStart = atFragmentStart(cursor, iter) ?
                        cursor.x : cursor.x + text.charLength;
                int wordStop = wordStart + width;
                if (iter.next == null && wordStop <= text.width ||
                        iter.next != null && wordStop <= iter.next.startX) {
                    startX = wordStart;
                    startY = cursor.y;
                    height = text.lineHeight;
                    text.tokens.add(this);
                    cursor.x = wordStop;
                    notPlaced = false;
                } else if (iter.next == null && wordStop > text.width) {
                    cursor.newLine(text.lineHeight, nodes);
                } else { // iter.next != null && wordStop > iter.next.startX
                    cursor.x = iter.next.stopX;
                    iter.moveForward();
                }
            }
        }
    }
    
    private static abstract class Image extends Token {
        public Image(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
    
    private static class ImageEmbedded extends Image {
        private ImageEmbedded(int width, int height) {
            super(width, height);
        }
        @Override public void process(Text text, Cursor cursor, Nodes nodes) {
            final Nodes.Iterator iter = nodes.iter;
            boolean notPlaced = true;
            while (notPlaced) {
                int imageStart = atFragmentStart(cursor, iter) ?
                        cursor.x : cursor.x + text.charLength;
                int imageStop = imageStart + width;
                if (iter.next == null && imageStop <= text.width ||
                        iter.next != null && imageStop <= iter.next.startX) {
                    startX = imageStart;
                    startY = cursor.y;
                    text.tokens.add(this);
                    cursor.x = imageStop;
                    if (cursor.h < height) {
                        cursor.h = height;
                    }
                    notPlaced = false;
                } else if (iter.next == null && imageStop > text.width) {
                    cursor.newLine(text.lineHeight, nodes);
                } else { // iter.next != null && imageStop > iter.next.startX
                    cursor.x = iter.next.stopX;
                    iter.moveForward();
                }
            }
        }
    }
    
    private static class ImageSurrounded extends Image {
        private ImageSurrounded(int width, int height) {
            super(width, height);
        }
        @Override public void process(Text text, Cursor cursor, Nodes nodes) {
            final Nodes.Iterator iter = nodes.iter;
            boolean notPlaced = true;
            while (notPlaced) {
                int imageStart = cursor.x;
                int imageStop = imageStart + width;
                if (iter.next == null && imageStop <= text.width ||
                        iter.next != null && imageStop <= iter.next.startX) {
                    startX = imageStart;
                    startY = cursor.y;
                    text.tokens.add(this);
                    cursor.x = imageStop;
                    iter.addBefore(imageStart, imageStop, cursor.y + height);
                    notPlaced = false;
                } else if (iter.next == null && imageStop > text.width) {
                    cursor.newLine(text.lineHeight, nodes);
                } else { // iter.next != null && imageStop > iter.next.startX
                    cursor.x = iter.next.stopX;
                    iter.moveForward();
                }
            }
        }
    }
    
    private static class ImageFloating extends Image {
        private final int dx, dy;
        private ImageFloating(int width, int height, int dx, int dy) {
            super(width, height);
            this.dx = dx;
            this.dy = dy;
        }
        @Override public void process(Text text, Cursor cursor, Nodes nodes) {
            Token prevToken = text.tokens.getLast();
            startX = prevToken.startX + prevToken.width + dx;
            startY = prevToken.startY + dy;
            if (startX < 0) {
                startX = 0;
            }
            if (startX + width > text.width) {
                startX = text.width - width;
            }
            text.tokens.add(this);
        }
    }
    
    private static void parseText(Text text, Cursor cursor, Nodes nodes) {
        Token token;
        while ((token = nextToken(text)) != null) {
            token.process(text, cursor, nodes);
        }
    }
    
    private static Token nextToken(Text text) {
        while (text.i < text.raw.length && text.raw[text.i] == ' ') {
            text.i++;
        } // "word1_word2__\n___\n" means new paragraph
        if (text.i < text.raw.length && text.raw[text.i] == '\n') {
            text.i++; // skip first new line
            while (text.i < text.raw.length && text.raw[text.i] == ' ') {
                text.i++;
            }
            if (text.i < text.raw.length && text.raw[text.i] == '\n') {
                text.i++; // skip second new line index
                return new Paragraph();
            }
        }
        if (text.i >= text.raw.length) {
            return null;
        }
        int offset = text.i;
        while (text.i < text.raw.length &&
                text.raw[text.i] != ' ' && text.raw[text.i] != '\n') {
            text.i++;
        }
        String textToken = new String(text.raw, offset, text.i - offset);
        if (textToken.equals("(image")) {
            return buildImage(text);
        } else { // word
            return new Word(textToken, text.charLength);
        }
    }
    
    private static Token buildImage(Text text) {
        int layout = 0, w = 0, h = 0, dx = 0, dy = 0;
        while (text.raw[text.i] != ')') {
            text.i++; // space skip
            int offset = text.i;
            while (text.raw[text.i] != '=') {
                text.i++;
            }
            String param = new String(text.raw, offset, text.i - offset);
            text.i++; // equals skip
            offset = text.i;
            while (text.raw[text.i] != ' ' && text.raw[text.i] != '\n' &&
                    text.raw[text.i] != ')') {
                text.i++;
            }
            String value = new String(text.raw, offset, text.i - offset);
            switch (param) {
                case "layout" -> { layout = parseLayout(value); }
                case "width" -> { w = Integer.parseInt(value); }
                case "height" -> { h = Integer.parseInt(value); }
                case "dx" -> { dx = Integer.parseInt(value); }
                case "dy" -> { dy = Integer.parseInt(value); }
                default -> {}
            }
        }
        text.i++; // parenthesis skip
        if (layout == 1) {
            return new ImageEmbedded(w, h);
        }
        if (layout == 2) {
            return new ImageSurrounded(w, h);
        }
        if (layout == 3) {
            return new ImageFloating(w, h, dx, dy);
        }
        return null;
    }
    
    private static int parseLayout(String value) {
        switch (value) {
            case "embedded" -> { return 1; }
            case "surrounded" -> { return 2; }
            case "floating" -> { return 3; }
            default -> { return 0; }
        }
    }
    
    private static boolean atFragmentStart(Cursor cursor, Nodes.Iterator iter) {
        return cursor.x == 0 || iter.prev != null && iter.prev.stopX == cursor.x;
    }
    
    private static void resetNodesByY(Nodes.Iterator iter, int y) {
        for (iter.moveBeforeFirst(); iter.next != null; ) {
            if (iter.next.stopY <= y) {
                iter.removeNext();
            } else {
                iter.moveForward();
            }
        }
        iter.moveBeforeFirst();
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
    
}
