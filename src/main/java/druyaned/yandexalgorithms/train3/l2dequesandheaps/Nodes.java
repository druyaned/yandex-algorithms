package druyaned.yandexalgorithms.train3.l2dequesandheaps;

import java.util.function.Consumer;

/**
 * Implementation of the {@code LinkedList} idea.
 * @author druyaned
 * @param <T> data type to be stored in the structure.
 */
public class Nodes<T> {
    
//-Static-------------------------------------------------------------------------------------------
    
    private static class Node<T> {
        private final T value;
        private Node<T> prev, next;
        private Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
    
    /** Iterator of {@link Nodes collection} which is located between its elements. */
    public class Iterator {
        private Node<T> prev, next;
        /**
         * Returns element previous to the iterator in the collection
         * or {@code null} if there is no one.
         * @return element previous to the iterator in the collection
         *         or {@code null} if there is no one
         */
        public T previous() {
            return prev != null ? prev.value : null;
        }
        /**
         * Returns element next to the iterator in the collection
         * or {@code null} if there is no one.
         * @return element next to the iterator in the collection
         *         or {@code null} if there is no one
         */
        public T next() {
            return next != null ? next.value : null;
        }
        /**
         * Adds a new element to the collection immediately before the iterator.
         * @param value to be added as a new element
         */
        public void addBefore(T value) {
            Node node = new Node(value, prev, next);
            if (prev != null) {
                prev.next = node;
            } else {
                first = node;
            }
            if (next != null) {
                next.prev = node;
            } else {
                last = node;
            }
            prev = node;
            size++;
        }
        /**
         * Adds a new element to the collection immediately after the iterator.
         * @param value to be added as a new element
         */
        public void addAfter(T value) {
            Node node = new Node(value, prev, next);
            if (prev != null) {
                prev.next = node;
            } else {
                first = node;
            }
            if (next != null) {
                next.prev = node;
            } else {
                last = node;
            }
            next = node;
            size++;
        }
        /**
         * Removes element previous to the iterator in the collection and returns its value
         * if there is such an element, otherwise does nothing and returns {@code null}.
         * @return value of removed element if there is such an element,
         *         otherwise - {@code null}
         */
        public T removePrevious() {
            if (prev == null) {
                return null;
            }
            T removed = prev.value;
            if (prev.prev != null) {
                prev.prev.next = next;
            } else {
                first = next;
            }
            if (next != null) {
                next.prev = prev.prev;
            } else {
                last = prev.prev;
            }
            prev = prev.prev;
            size--;
            return removed;
        }
        /**
         * Removes element next to the iterator in the collection and returns its value
         * if there is such an element, otherwise does nothing and returns {@code null}.
         * @return value of removed element if there is such an element,
         *         otherwise - {@code null}
         */
        public T removeNext() {
            if (next == null) {
                return null;
            }
            T removed = next.value;
            if (next.next != null) {
                next.next.prev = prev;
            } else {
                last = prev;
            }
            if (prev != null) {
                prev.next = next.next;
            } else {
                first = next.next;
            }
            next = next.next;
            size--;
            return removed;
        }
        /**
         * Moves the iterator one element back and returns {@code true}
         * if there is such an element, otherwise just returns {@code false}.
         * @return {@code true} if the moving succeeded, otherwise - {@code false}
         */
        public boolean moveBack() {
            if (prev != null) {
                next = prev;
                prev = prev.prev;
                return true;
            }
            return false;
        }
        /**
         * Moves the iterator one element forward and returns {@code true}
         * if there is such an element, otherwise just returns {@code false}.
         * @return {@code true} if the moving succeeded, otherwise - {@code false}
         */
        public boolean moveForward() {
            if (next != null) {
                prev = next;
                next = next.next;
                return true;
            }
            return false;
        }
        /** Places the iterator before the first element of the {@link Nodes collection}. */
        public void moveBeforeFirst() {
            prev = null;
            next = first;
        }
        /** Places the iterator after the last element of the {@link Nodes collection}. */
        public void moveAfterLast() {
            prev = last;
            next = null;
        }
    }
    
//-Non-static---------------------------------------------------------------------------------------
    
    private Node<T> first, last;
    private final Iterator iter;
    private int size;
    
    /** Creates a new empty collection. */
    public Nodes() {
        iter = new Iterator();
    }
    
    /**
     * Returns value of the first element or {@code null} if there is no one.
     * @return value of the first element or {@code null} if there is no one.
     */
    public T getFirst() {
        return first != null ? first.value : null;
    }
    
    /**
     * Returns value of the last element or {@code null} if there is no one.
     * @return value of the last element or {@code null} if there is no one.
     */
    public T getLast() {
        return last != null ? last.value : null;
    }
    
    /**
     * Returns size of the collection.
     * @return size of the collection
     */
    public int size() {
        return size;
    }
    
    /**
     * Returns {@code true} if the collection is empty, otherwise - {@code false}.
     * @return {@code true} if the collection is empty, otherwise - {@code false}
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /** Removes all elements from the collection by making all fields be a null or a zero. */
    public void clear() {
        first = last = iter.prev = iter.next = null;
        size = 0;
    }
    
    /**
     * Returns {@link Iterator iterator} of the collection.
     * @return {@link Iterator iterator} of the collection
     */
    public Iterator iterator() {
        return iter;
    }
    
    /**
     * Performs a given operation on the given argument.
     * @param consumer to perform a given operation on the given argument
     */
    public void forEach(Consumer<T> consumer) {
        for (Node<T> node = first; node != null; node = node.next) {
            consumer.accept(node.value);
        }
    }
    
}
