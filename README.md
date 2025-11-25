# Process Scheduling Simulator

This project is a Java-based **CPU Scheduling Simulator** implementing
the following scheduling algorithms:

-   **First Come First Serve (FCFS)**
-   **Round Robin (RR)**
-   **Priority Scheduling (Non-Preemptive)**
-   **Multi-Level Queue (MLQ)**

It allows the user to input process details and then generates
performance metrics such as: 
- Turnaround Time
- Waiting Time
- Response Time
- CPU Utilization
- Throughput

------------------------------------------------------------------------

## 📌 Features

### ✔ FCFS

Executes processes in order of arrival using a simple FIFO queue.

### ✔ Round Robin

Time-shared scheduling using a user-defined quantum.

### ✔ Priority Scheduling

Non-preemptive priority-based execution with FCFS tie-breaking.

### ✔ Multi-Level Queue

Two-level queue system:\
- High Priority → Round Robin\
- Low Priority → FCFS

------------------------------------------------------------------------

## 🎯 Performance Metrics

Automatically calculated for each algorithm: - Completion Time\
- Turnaround Time\
- Waiting Time\
- Response Time\
- CPU Utilization\
- Throughput

------------------------------------------------------------------------

## 🛠 Technologies Used

-   **Java** (Core logic)
-   **Object-Oriented Structure** using a `ProcessControlBlock` class\
-   **Queue Structures:** `LinkedList`, `PriorityQueue`

------------------------------------------------------------------------

## ▶ Running the Program

1.  Compile the program:

        javac ProcessControlBlock.java

2.  Run the program:

        java ProcessControlBlock

3.  Follow prompts to enter:

    -   Number of processes\
    -   Arrival Time\
    -   Burst Time\
    -   Priority

4.  View results of all scheduling algorithms.

------------------------------------------------------------------------

## 📁 File Structure

    src/
    └── ProcessControlBlock.java

------------------------------------------------------------------------

## 👤 Author

Created as part of a CPU Scheduling Simulation project.
