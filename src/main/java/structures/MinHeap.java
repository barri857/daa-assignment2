package structures;

public class MinHeap {
    public int[] data;
    private int size;
    public long steps, moves, comparisons;

    public MinHeap(int capacity) {
        data = new int[capacity];
        size = 0;
    }

    public void insert(int x) {
        if (size == data.length) resize();
        data[size] = x;
        moves++;
        bubbleUp(size); //[cite: 2]
        size++;
    }

    public int peekMin() {
        if (size == 0) throw new IllegalStateException(); //[cite: 2]
        steps++;
        return data[0];
    }

    public int extractMin() {
        if (size == 0) throw new IllegalStateException(); //[cite: 2]
        int min = data[0];
        steps++;
        data[0] = data[size - 1];
        moves++;
        size--;
        bubbleDown(0); //[cite: 2]
        return min;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            steps += 2;
            comparisons++;
            if (data[index] < data[parentIndex]) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            steps++;
            if (left < size) {
                steps++; comparisons++;
                if (data[left] < data[smallest]) smallest = left;
            }
            if (right < size) {
                steps++; comparisons++;
                if (data[right] < data[smallest]) smallest = right;
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
        moves += 3;
    }

    private void resize() {
        int[] newData = new int[data.length * 2];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
            moves++; steps++;
        }
        data = newData;
    }

    // Bonus Task B: O(n) buildHeap[cite: 4]
    public void buildHeap(int[] array) {
        this.data = array;
        this.size = array.length;
        for (int i = (size / 2) - 1; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    public void resetMetrics() { steps = 0; moves = 0; comparisons = 0; }
    public int size() { return size; }
}