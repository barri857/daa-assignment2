package structures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataStructuresTest {

    @Test
    public void testDynamicArrayEdgeCases() {
        DynamicArray arr = new DynamicArray();
        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(0));
        arr.add(10);
        arr.add(20);
        assertTrue(arr.contains(10));
        assertFalse(arr.contains(99));
        assertEquals(10, arr.get(0));
        arr.remove(0);
        assertEquals(20, arr.get(0));
    }

    @Test
    public void testMyLinkedListEdgeCases() {
        MyLinkedList list = new MyLinkedList();
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
        list.add(5);
        list.add(0, 1);
        assertEquals(1, list.get(0));
        assertEquals(5, list.get(1));
    }

    @Test
    public void testMinHeapProperties() {
        MinHeap heap = new MinHeap(10);
        assertThrows(IllegalStateException.class, heap::extractMin);
        heap.insert(50);
        heap.insert(10);
        heap.insert(30);

        // Assert Heap Property a[parent] <= a[child] manually
        assertTrue(heap.data[0] <= heap.data[1]);
        assertTrue(heap.data[0] <= heap.data[2]);

        // Sorted Output test
        assertEquals(10, heap.extractMin());
        assertEquals(30, heap.extractMin());
        assertEquals(50, heap.extractMin());
    }
}