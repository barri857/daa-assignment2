---

### `REPORT.md`

```markdown
# Assignment 2 - Data Structures and Workload Analysis Report
**Student:** Temirgaliyev Nurassyl
**Group:** SE-2523

---

## 1. Complexity Table

| Structure | Operation | Best Case | Average Case | Worst Case | Justification |
| :--- | :--- | :---: | :---: | :---: | :--- |
| **DynamicArray** | `add(x)` | $\Theta(1)$ | $\Theta(1)$ | $O(n)$ | Appends to the end in constant time; triggers an $O(n)$ array copy when capacity is full. |
| **DynamicArray** | `add(i, x)` | $\Theta(1)$ | $O(n)$ | $O(n)$ | Best case appends at the end; worst case inserts at index 0, requiring shifting $n$ elements. |
| **DynamicArray** | `remove(i)` | $\Theta(1)$ | $O(n)$ | $O(n)$ | Removing from the end is $\Theta(1)$; removing from index 0 shifts subsequent elements. |
| **DynamicArray** | `get(i)` | $\Theta(1)$ | $\Theta(1)$ | $\Theta(1)$ | Direct memory access via array index arithmetic. |
| **DynamicArray** | `contains(x)` | $\Theta(1)$ | $O(n)$ | $O(n)$ | Best case finds element immediately; worst case scans the entire array. |
| **MyLinkedList** | `add(x)` | $O(n)$ | $O(n)$ | $O(n)$ | Traversing to the tail pointer takes linear time without a persistent tail reference. |
| **MyLinkedList** | `add(i, x)` | $\Theta(1)$ | $O(n)$ | $O(n)$ | Best case inserts at the head; worst case traverses to index $i$. |
| **MyLinkedList** | `remove(i)` | $\Theta(1)$ | $O(n)$ | $O(n)$ | Best case removes the head node; worst case traverses to index $i-1$. |
| **MyLinkedList** | `get(i)` | $\Theta(1)$ | $O(n)$ | $O(n)$ | Best case accesses the head; worst case requires sequential node traversal. |
| **MinHeap** | `insert(x)` | $\Theta(1)$ | $O(\log n)$ | $O(\log n)$ | Best case element stays at the leaf; worst case bubbles up to the root. |
| **MinHeap** | `peekMin()` | $\Theta(1)$ | $\Theta(1)$ | $\Theta(1)$ | The minimum element is always stored at the root index `0`. |
| **MinHeap** | `extractMin()` | $O(\log n)$ | $O(\log n)$ | $O(\log n)$ | Swaps root with last leaf and restores heap property by bubbling down the tree height. |

*Auxiliary Space:* $O(n)$ for all three data structures to store elements.

---

## 2. Loop Invariant Proofs

### Proof 1: `contains(x)` in `DynamicArray`
* **Invariant:** At the beginning of each iteration of the search loop for index `i` (from `0` to `size - 1`), the target value `x` has not been found in the subarray slice `data[0 ... i-1]`.
* **Initialization:** Before the first iteration (`i = 0`), the checked subarray `data[0 ... -1]` is empty. The statement that `x` is absent from an empty set is vacuously true.
* **Maintenance:** Assume the invariant holds before iteration `i`. We test `data[i]`. If `data[i] == x`, the method terminates and correctly returns `true`. If `data[i] != x`, then `x` is confirmed absent from `data[0 ... i]`, maintaining the invariant for the next iteration `i + 1`.
* **Termination:** The loop terminates when a match is found or when `i == size`. If `i == size`, the invariant ensures `x` was checked against all elements in `data[0 ... size-1]` without success.
* **Conclusion:** This proves that returning `false` upon loop completion is correct because the target value does not exist in the structure.

### Proof 2: `bubbleDown(index)` in `MinHeap`
* **Invariant:** At the start of each iteration in the `bubbleDown` routine, the binary heap property is satisfied everywhere in the tree except potentially between the current parent node at `index` and its direct children.
* **Initialization:** When `extractMin()` replaces the root with the final leaf element, all subtrees remain valid heaps. The only potential violation exists between the new root at `index = 0` and its children.
* **Maintenance:** During an iteration, if `data[index]` violates the min-heap order against its children, it is swapped with the smallest child. The unswapped branch remains a valid heap, while the swapped branch receives a smaller parent, pushing the single localized violation down to the child index.
* **Termination:** The loop terminates when `smallest == index` (the parent is smaller than both children) or when the node becomes a leaf.
* **Conclusion:** Because the single potential violation is systematically pushed down until local order is satisfied, the entire structure successfully restores the global min-heap property ($a[\text{parent}] \le a[\text{child}]$).

---

## 3. Performance Discussion & Bonus Analysis

### CPU Cache Locality vs. Pointer Chasing
Although `DynamicArray` and `MyLinkedList` share similar theoretical Big-$O$ bounds for linear iteration workloads (such as Workload 2), `DynamicArray` executes significantly faster in practice. This performance difference stems from underlying hardware architecture: arrays store primitive values in contiguous blocks of memory, allowing modern CPUs to prefetch entire cache lines via spatial locality. Conversely, `MyLinkedList` stores nodes in fragmented heap allocations, forcing the processor into expensive "pointer chasing" where every node access incurs a cache miss.

### Memory Footprint & Garbage Collection
Object headers and reference pointers impose a heavy memory penalty on linked structures. Using JOL (Java Object Layout) analysis for Bonus Task A, `MyLinkedList` consumes roughly 3 to 4 times more memory than `DynamicArray` storing the same number of primitive `int` elements. Each linked node requires a 16-byte object header, an 8-byte value field, and reference pointers, whereas a primitive array packs raw integers tightly and avoids boxing overhead. This higher object count also places increased pressure on the JVM garbage collector.

### $O(n)$ buildHeap vs. $O(n \log n)$ Insertions
For Bonus Task B, implementing Floyd’s bottom-up `buildHeap` constructs a min-heap in linear time $\Theta(n)$ by calling `bubbleDown` from the lowest internal parent up to the root. Most nodes reside near the bottom levels and shift minimally. In contrast, inserting $n$ elements individually via `insert(x)` requires $O(n \log n)$ time because every insertion starts at a leaf and can potentially bubble up through the entire height of the tree.

### When to Choose Which Structure
* **DynamicArray:** Best for general-purpose workloads heavily reliant on fast index lookups and cache-friendly iterations.
* **MyLinkedList:** Preferable only when constant-time insertions and removals are restricted strictly to the head or tail boundaries without random index access.
* **MinHeap:** The mandatory choice for priority-based job scheduling and algorithms requiring efficient extraction of extrema.