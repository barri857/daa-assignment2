package structures;

public class MyLinkedList {
    private class Node {
        int val; Node next;
        Node(int val) { this.val = val; }
    }
    private Node head;
    private int size;
    public long steps, moves, comparisons;

    public void add(int x) {
        if (head == null) {
            head = new Node(x);
            moves++;
        } else {
            Node curr = head;
            steps++;
            while (curr.next != null) {
                curr = curr.next;
                steps++;
            }
            curr.next = new Node(x);
            moves++;
        }
        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException(); //[cite: 2]
        if (index == 0) {
            Node newNode = new Node(x);
            newNode.next = head;
            head = newNode;
            moves += 2;
        } else {
            Node curr = head;
            steps++;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.next;
                steps++;
            }
            Node newNode = new Node(x);
            newNode.next = curr.next;
            curr.next = newNode;
            moves += 2;
        }
        size++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException(); //[cite: 2]
        if (index == 0) {
            head = head.next;
            moves++;
        } else {
            Node curr = head;
            steps++;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.next;
                steps++;
            }
            curr.next = curr.next.next;
            moves++;
        }
        size--;
    }

    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException(); //[cite: 2]
        Node curr = head;
        steps++;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
            steps++;
        }
        return curr.val;
    }

    public boolean contains(int x) {
        Node curr = head;
        steps++;
        while (curr != null) {
            comparisons++;
            if (curr.val == x) return true;
            curr = curr.next;
            steps++;
        }
        return false;
    }

    public void resetMetrics() { steps = 0; moves = 0; comparisons = 0; }
    public int size() { return size; }
}