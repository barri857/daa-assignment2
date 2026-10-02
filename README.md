# Assignment 2 - In-Memory Workload Engine & Data Structures

## Project Structure
- `src/main/java/structures/`: Contains `DynamicArray`, `MyLinkedList`, and `MinHeap` implemented completely from scratch without `java.util` collections.
- `src/main/java/benchmark/`: Contains `BenchmarkRunner` for executing workloads W1–W4, tracking metrics, and exporting data.
- `src/test/java/structures/`: Contains JUnit 5 tests covering edge cases, correctness, and heap properties.
- `results/`: Contains `results.csv` and generated PNG performance plots.

## Requirements & Technologies
- Language: Java (JDK 17+)
- Build Tool: Maven
- Testing Framework: JUnit 5
- Memory Layout Tool: JOL (Java Object Layout) for Bonus Task A

## How to Build and Run
1. **Run JUnit Tests**: Execute tests via your IDE or run via Maven:
   ```bash
   mvn test