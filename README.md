# Process Scheduling Simulator

This project is a Java-based **CPU Scheduling Simulator** simulating 
process scheduling using the following scheduling algorithms:

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

Processes are ran in the order they arrived using a simple FIFO queue.

### ✔ Round Robin

Time-shared scheduling using a user-defined time quantum.

### ✔ Priority Scheduling

Non-preemptive priority-based execution with FCFS logic used for tie-breaking.

### ✔ Multi-Level Queue

Two-level queue system:
- High Priority → Round Robin
- Low Priority → FCFS

------------------------------------------------------------------------

## 🎯 Performance Metrics

Automatically calculated and displayed for each algorithm: 
- Completion Time (for each process)
- Turnaround Time (for each process as well as the avergae)
- Waiting Time (for each process as well as the avergae)
- Response Time (for each process as well as the avergae)
- Average CPU Utilization
- Average Throughput

------------------------------------------------------------------------

## 🛠 Technologies Used

-   **Java** (Core logic)
-   **Object-Oriented Structure** using a created `ProcessControlBlock` class
-   **Queue Structures:** `LinkedList`, `PriorityQueue`

------------------------------------------------------------------------

## ▶ Running the Program

1.  Compile the program:

        javac path/to/file/ProcessControlBlock.java

2.  Run the program:

        java path/to/file/ProcessControlBlock

3.  Follow prompts to enter:

    -   Number of processes (>0)
    -   Arrival Time (>=0)
    -   Burst Time (>0)
    -   Priority (1-4)

4.  View results of all scheduling algorithms.

------------------------------------------------------------------------

## 📁 File Structure

    src/
    └── system/
        └── ProcessControlBlock.java

------------------------------------------------------------------------

## 👤 Authors
-   Kemone Laws -- Priority Scheduling
-   Diwani Walters -- Multi-Level Queue Scheduling
-   Olivia McFarlane -- Calculate Performance Metrics, Main, First-Come First-Serve Scheduling
-   Javone Anthony Gordon -- Round Robin Scheduling

Created as part of a CPU Scheduling Simulation project for an Operating Systems module.