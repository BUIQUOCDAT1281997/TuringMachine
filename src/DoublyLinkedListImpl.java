import java.util.*;

public class DoublyLinkedListImpl<E> {

    private Node head;
    private Node tail;
    private int size;

    public DoublyLinkedListImpl() {
        size = 0;
    }

    private class Node {
        E element;
        Node next;
        Node prev;
        int index;

        public Node(E element, Node next, Node prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
            index = 0;
        }
    }

    /**
     * returns the size of the linked list
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * return whether the list is empty or not
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * adds element at the starting of the linked list
     *
     * @param element
     */
    public void addFirst(E element) {
        Node tmp = new Node(element, head, null);
        if (head != null) {
            head.prev = tmp;
            tmp.index = head.index - 1;
        }
        head = tmp;
        if (tail == null) {
            tail = tmp;
        }
        size++;
    }

    /**
     * adds element at the end of the linked list
     *
     * @param element
     */
    public void addLast(E element) {

        Node tmp = new Node(element, null, tail);
        if (tail != null) {
            tail.next = tmp;
            tmp.index = tail.index + 1;
        }
        tail = tmp;
        if (head == null) {
            head = tmp;
        }
        size++;
    }

    /**
     * this method walks forward through the linked list
     */
    public void iterateForward() {

        System.out.println("iterating forward..");
        Node tmp = head;
        while (tmp != null) {
            System.out.println(tmp.element);
            tmp = tmp.next;
        }
    }

    /**
     * this method walks backward through the linked list
     */
    public void iterateBackward() {

        System.out.println("iterating backword..");
        Node tmp = tail;
        while (tmp != null) {
            System.out.println(tmp.element);
            tmp = tmp.prev;
        }
    }

    /**
     * this method removes element from the start of the linked list
     *
     * @return
     */
    public E removeFirst() {
        if (size == 0) throw new NoSuchElementException();
        Node tmp = head;
        head = head.next;
        head.prev = null;
        size--;
        System.out.println("deleted: " + tmp.element);
        return tmp.element;
    }

    /**
     * this method removes element from the end of the linked list
     *
     * @return
     */
    public E removeLast() {
        if (size == 0) throw new NoSuchElementException();
        Node tmp = tail;
        tail = tail.prev;
        tail.next = null;
        size--;
        System.out.println("deleted: " + tmp.element);
        return tmp.element;
    }

    public void remove(int index) {
        Node tmp = tail;
        while (tmp != null) {
            if (tmp.index == index) {
                size--;
                if (index == tail.index) {
                    tail = tmp.prev;
                }
                if (index == head.index) {
                    head = tmp.next;
                }
                if (tmp.prev != null) {
                    tmp.prev.next = tmp.next;
                }
                if (tmp.next != null) {
                    tmp.next.prev = tmp.prev;
                }

            }
            tmp = tmp.prev;
        }
    }

    public E get(int index) {
        Node tmp = tail;
        while (tmp != null) {
            if (tmp.index == index) {
                return tmp.element;
            }
            tmp = tmp.prev;
        }
        return null;
    }

    public void set(int index, E element) {
        Node tmp = tail;
        while (tmp != null) {
            if (tmp.index == index) {
                tmp.element = element;
            }
            tmp = tmp.prev;
        }
    }

    public class DoublyLinkedListIterator implements Iterator<E> {

        private Node current = null;

        private int location = 0;

        private List<Node> list;

        private DoublyLinkedListIterator() {
            list = new ArrayList<>();
            addToList(head);
        }

        private void addToList(Node head) {
            Node tmp = head;
            while (tmp != null) {
                list.add(tmp);
                tmp = tmp.next;
            }
        }

        private Node findNext() {
            return list.get(location++);
        }

        @Override
        public boolean hasNext() {
            return location < list.size();
        }

        @Override
        public E next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.element;
        }

        @Override
        public void remove() {
            DoublyLinkedListImpl.this.remove(current.index);
            list.remove(current);
            location--;
        }
    }

    public Iterator<E> iterator() {
        return new DoublyLinkedListIterator();
    }

    @Override
    public String toString() {
        String result = "";
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            result = result.concat(iterator.next().toString());
        }
        return result;
    }

}
