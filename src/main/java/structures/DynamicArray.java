package structures;

public class DynamicArray {
    private int[] data;
    private int size;
    public long steps, moves, comparisons;

    public DynamicArray() {
        data = new int[10];
        size = 0;
    }

    public void add(int x) {
        if (size == data.length) resize();
        data[size++] = x;
        moves++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException(); //[cite: 2]
        if (size == data.length) resize();
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            moves++;
            steps++;
        }
        data[index] = x;
        size++;
        moves++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException(); //[cite: 2]
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            moves++;
            steps++;
        }
        size--;
    }

    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException(); //[cite: 2]
        steps++;
        return data[index];
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            steps++;
            comparisons++;
            if (data[i] == x) return true;
        }
        return false;
    }

    private void resize() {
        int[] newData = new int[data.length * 2]; //[cite: 2]
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
            moves++;
            steps++;
        }
        data = newData;
    }

    public void resetMetrics() { steps = 0; moves = 0; comparisons = 0; }
    public int size() { return size; }
}