package benchmark;

import structures.DynamicArray;
import structures.MyLinkedList;
import structures.MinHeap;
import org.openjdk.jol.info.GraphLayout;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {
    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static FileWriter csvWriter;

    public static void main(String[] args) throws IOException {
        new java.io.File("results").mkdirs();
        csvWriter = new FileWriter("results/results.csv");
        csvWriter.append("workload,variant,structure,n,time_ms,steps,moves,comparisons\n"); //

        for (int n : SIZES) {
            System.out.println("Running benchmarks for n=" + n);
            runWorkload1(n);
            runWorkload2(n);
            runWorkload3(n);
            runWorkload4(n);
            runMemoryFootprint(n);
        }
        csvWriter.close();
        System.out.println("Benchmark complete. Results saved to results/results.csv");
    }

    private static void runWorkload1(int n) throws IOException {
        Random rand = new Random(42);
        long[] timesArr = new long[5], timesList = new long[5];
        long stepsA = 0, movesA = 0, compA = 0, stepsL = 0, movesL = 0, compL = 0;

        for (int run = 0; run < 6; run++) {
            DynamicArray arr = new DynamicArray();
            MyLinkedList list = new MyLinkedList();
            for (int i = 0; i < n; i++) { int v = rand.nextInt(); arr.add(v); list.add(v); }
            int[] queries = new int[10000];
            for (int i = 0; i < 10000; i++) queries[i] = rand.nextInt(n);

            arr.resetMetrics(); list.resetMetrics();

            long startA = System.nanoTime();
            for (int q : queries) arr.get(q);
            long endA = System.nanoTime();

            long startL = System.nanoTime();
            for (int q : queries) list.get(q);
            long endL = System.nanoTime();

            if (run > 0) {
                timesArr[run - 1] = (endA - startA) / 1000000;
                timesList[run - 1] = (endL - startL) / 1000000;
                stepsA = arr.steps; movesA = arr.moves; compA = arr.comparisons;
                stepsL = list.steps; movesL = list.moves; compL = list.comparisons;
            }
        }
        writeCSV("W1", "-", "DynamicArray", n, median(timesArr), stepsA, movesA, compA);
        writeCSV("W1", "-", "MyLinkedList", n, median(timesList), stepsL, movesL, compL);
    }

    private static void runWorkload2(int n) throws IOException {
        Random rand = new Random(42);
        long[] timesArr = new long[5], timesList = new long[5];
        long stepsA = 0, movesA = 0, compA = 0, stepsL = 0, movesL = 0, compL = 0;

        for (int run = 0; run < 6; run++) {
            DynamicArray arr = new DynamicArray();
            MyLinkedList list = new MyLinkedList();
            int[] data = new int[n];
            for (int i = 0; i < n; i++) { data[i] = rand.nextInt(); arr.add(data[i]); list.add(data[i]); }

            int[] queries = new int[1000];
            for (int i = 0; i < 500; i++) queries[i] = data[rand.nextInt(n)];
            for (int i = 500; i < 1000; i++) queries[i] = rand.nextInt();

            arr.resetMetrics(); list.resetMetrics();

            long startA = System.nanoTime();
            for (int q : queries) arr.contains(q);
            long endA = System.nanoTime();

            long startL = System.nanoTime();
            for (int q : queries) list.contains(q);
            long endL = System.nanoTime();

            if (run > 0) {
                timesArr[run - 1] = (endA - startA) / 1000000;
                timesList[run - 1] = (endL - startL) / 1000000;
                stepsA = arr.steps; movesA = arr.moves; compA = arr.comparisons;
                stepsL = list.steps; movesL = list.moves; compL = list.comparisons;
            }
        }
        writeCSV("W2", "-", "DynamicArray", n, median(timesArr), stepsA, movesA, compA);
        writeCSV("W2", "-", "MyLinkedList", n, median(timesList), stepsL, movesL, compL);
    }

    private static void runWorkload3(int n) throws IOException {
        runW3Variant(n, "head", 0);
        runW3Variant(n, "middle", n / 2);
    }

    private static void runW3Variant(int n, String variant, int baseIndex) throws IOException {
        Random rand = new Random(42);
        long[] timesArr = new long[5], timesList = new long[5];
        long stepsA = 0, movesA = 0, compA = 0, stepsL = 0, movesL = 0, compL = 0;

        for (int run = 0; run < 6; run++) {
            DynamicArray arr = new DynamicArray();
            MyLinkedList list = new MyLinkedList();
            for (int i = 0; i < n; i++) { arr.add(i); list.add(i); }

            arr.resetMetrics(); list.resetMetrics();

            long startA = System.nanoTime();
            for (int i = 0; i < 1000; i++) arr.add(baseIndex, rand.nextInt());
            for (int i = 0; i < 1000; i++) arr.remove(baseIndex);
            long endA = System.nanoTime();

            long startL = System.nanoTime();
            for (int i = 0; i < 1000; i++) list.add(baseIndex, rand.nextInt());
            for (int i = 0; i < 1000; i++) list.remove(baseIndex);
            long endL = System.nanoTime();

            if (run > 0) {
                timesArr[run - 1] = (endA - startA) / 1000000;
                timesList[run - 1] = (endL - startL) / 1000000;
                stepsA = arr.steps; movesA = arr.moves; compA = arr.comparisons;
                stepsL = list.steps; movesL = list.moves; compL = list.comparisons;
            }
        }
        writeCSV("W3", variant, "DynamicArray", n, median(timesArr), stepsA, movesA, compA);
        writeCSV("W3", variant, "MyLinkedList", n, median(timesList), stepsL, movesL, compL);
    }

    private static void runWorkload4(int n) throws IOException {
        Random rand = new Random(42);
        long[] timesHeap = new long[5];
        long stepsH = 0, movesH = 0, compH = 0;

        for (int run = 0; run < 6; run++) {
            MinHeap heap = new MinHeap(n);
            int[] data = new int[n];
            for (int i = 0; i < n; i++) data[i] = rand.nextInt();

            long startH = System.nanoTime();
            for (int val : data) heap.insert(val);
            for (int i = 0; i < n; i++) heap.extractMin();
            long endH = System.nanoTime();

            if (run > 0) {
                timesHeap[run - 1] = (endH - startH) / 1000000;
                stepsH = heap.steps; movesH = heap.moves; compH = heap.comparisons;
            }
        }
        writeCSV("W4", "-", "MinHeap", n, median(timesHeap), stepsH, movesH, compH);
    }

    private static void runMemoryFootprint(int n) {
        DynamicArray arr = new DynamicArray();
        MyLinkedList list = new MyLinkedList();
        MinHeap heap = new MinHeap(n);
        for(int i=0; i<n; i++) { arr.add(i); list.add(i); heap.insert(i); }
        System.out.println("Memory for n=" + n + " -> Array: " + GraphLayout.parseInstance(arr).totalSize() +
                " bytes, List: " + GraphLayout.parseInstance(list).totalSize() +
                " bytes, Heap: " + GraphLayout.parseInstance(heap).totalSize() + " bytes");
    }

    private static long median(long[] arr) {
        Arrays.sort(arr);
        return arr[arr.length / 2];
    }

    private static void writeCSV(String w, String v, String s, int n, long t, long steps, long moves, long comp) throws IOException {
        csvWriter.append(String.format("%s,%s,%s,%d,%d,%d,%d,%d\n", w, v, s, n, t, steps, moves, comp));
    }
}