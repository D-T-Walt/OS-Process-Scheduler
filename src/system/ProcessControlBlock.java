package system;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class ProcessControlBlock {
	private int pid;
	private int arrivalTime;
	private int burstTime;
	private int priority;
	private int queueLevel;

	//calculated during and after simulation
	private int completionTime;
	private int waitingTime;
	private int turnaroundTime;
	private int responseTime;

	//tracking variables for simulatioon logic
	private int remainingTime;
	private int startTime;

	 public ProcessControlBlock(int pid, int arrivalTime, int burstTime, int priority, int queueLevel) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.queueLevel = queueLevel;
        
        // Auto-initialize tracking vars
        this.remainingTime = burstTime; // At start, remaining = full burst
        this.startTime = -1;            // -1 indicates it hasn't started yet
        
        // Initialize metrics to 0 or -1
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.responseTime = 0;
    }

	public ProcessControlBlock() {
		this.pid = -1;
		this.arrivalTime = -1;
		this.burstTime = -1;
		this.priority = -1;
		this.queueLevel = -1;
		this.completionTime = -1;
		this.waitingTime = -1;
		this.turnaroundTime = -1;
		this.responseTime = -1;
		this.remainingTime = -1;
		this.startTime = -1;
		
	}
	
	public ProcessControlBlock(ProcessControlBlock obj) {
		this.pid = obj.pid;
		this.arrivalTime = obj.arrivalTime;
		this.burstTime = obj.burstTime;
		this.priority = obj.priority;
		this.queueLevel = obj.queueLevel;
		this.completionTime = obj.completionTime;
		this.waitingTime = obj.waitingTime;
		this.turnaroundTime = obj.turnaroundTime;
		this.responseTime = obj.responseTime;
		this.remainingTime = obj.remainingTime;
		this.startTime = obj.startTime;
		
	}

	public int getPid() {
		return pid;
	}

	public void setpid(int pid) {
		this.pid = pid;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getTurnaroundTime() {
		return turnaroundTime;
	}

	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}

	public int getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public int getQueueLevel() {
		return queueLevel;
	}

	public void setQueueLevel(int queueLevel) {
		this.queueLevel = queueLevel;
	}

	public int getStartTime(){
		return startTime;
	}

	public void setStartTime(int startTime){
		this.startTime = startTime;
	}
	
	
	
	@Override
	public String toString() {
		return "ProcessControlBlock [pid=" + pid + ", arrivalTime=" + arrivalTime + ", burstTime=" + burstTime
				+ ", priority=" + priority + ", queueLevel=" + queueLevel + ", completionTime=" + completionTime
				+ ", waitingTime=" + waitingTime + ", turnaroundTime=" + turnaroundTime + ", responseTime="
				+ responseTime + ", remainingTime=" + remainingTime + ", startTime=" + startTime + "]";
	}

	 public static void calculatePerformanceMetrics(List<ProcessControlBlock> processes) {
        double totalTAT = 0, totalWT = 0, totalRT = 0, totalBT = 0;
        int maxCompletionTime = 0;
        int minArrivalTime = Integer.MAX_VALUE;

        for (ProcessControlBlock pcb : processes) {
            // Calculate individual metrics based on Completion/Start times
            int tat = pcb.getCompletionTime() - pcb.getArrivalTime();
            int wt = tat - pcb.getBurstTime();
            int rt = pcb.getStartTime() - pcb.getArrivalTime();

            pcb.setTurnaroundTime(tat);
            pcb.setWaitingTime(wt);
            pcb.setResponseTime(rt);

            // Aggregate for averages
            totalTAT += tat;
            totalWT += wt;
            totalRT += rt;
            totalBT += pcb.getBurstTime();

            // Track total timeline for utilization/throughput
            if (pcb.getCompletionTime() > maxCompletionTime) {
                maxCompletionTime = pcb.getCompletionTime();
            }
            if (pcb.getArrivalTime() < minArrivalTime) {
                minArrivalTime = pcb.getArrivalTime();
            }
        }

        double totalExecutionTime = (double) maxCompletionTime - minArrivalTime;
        double avgTAT = totalTAT / processes.size();
        double avgWT = totalWT / processes.size();
        double avgRT = totalRT / processes.size();
        double cpuUtil = (totalExecutionTime > 0) ? ((totalBT / totalExecutionTime) * 100) : 0;
        double throughput = (totalExecutionTime > 0) ? ((double) processes.size() / (totalExecutionTime)) : 0;
        // Print the final results
        System.out.println("\n-----------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-8s | %-6s | %-10s | %-10s | %-8s | %-8s |\n", 
            "PID", "Arrival", "Burst", "Finish", "Turnaround", "Waiting", "Response");
        System.out.println("-----------------------------------------------------------------------------");
        
        processes.sort(Comparator.comparingInt(ProcessControlBlock::getPid)); // Sort by PID for display

        for (ProcessControlBlock p : processes) {
            System.out.printf("| P%-4d | %-8d | %-6d | %-10d | %-10d | %-8d | %-8d |\n", 
                p.getPid(), p.getArrivalTime(), p.getBurstTime(), 
                p.getCompletionTime(), p.getTurnaroundTime(), p.getWaitingTime(), p.getResponseTime());
        }
        System.out.println("-----------------------------------------------------------------------------");
        
        System.out.printf("Average Turnaround Time: %.2f\n", avgTAT);
        System.out.printf("Average Waiting Time:    %.2f\n", avgWT);
        System.out.printf("Average Response Time:   %.2f\n", avgRT);
        System.out.printf("CPU Utilization:         %.2f%%\n", cpuUtil);
        System.out.printf("Throughput:              %.4f processes/unit time\n", throughput);
    }

	public static void firstComeFirstServe(List<ProcessControlBlock> processes){		
		//sort based on arrival time
		//allows for processes to get added to the queue in the correct order
		processes.sort(Comparator.comparingInt(ProcessControlBlock::getArrivalTime));
		Queue<ProcessControlBlock> readyQueue = new LinkedList<>();
		
		int currTime = 0, processIndex = 0, compProcesses =0;
		
		//runs until all processes are complete
		while(compProcesses < processes.size()){
			//move the processes into the FIFO ready queue
			while(processIndex <processes.size() && processes.get(processIndex).getArrivalTime() <= currTime){
				readyQueue.add(processes.get(processIndex));
				processIndex++;
			}
			//if there are processes in the ready queue this will run 
			if(!readyQueue.isEmpty()){
				ProcessControlBlock pcb = readyQueue.poll();//get the head of the queue, the first process that arrived
				int startTime = Math.max(pcb.getArrivalTime(), currTime);
				
				pcb.setStartTime(startTime);
				
				pcb.setResponseTime(pcb.getStartTime() - pcb.getArrivalTime());
				
				pcb.setCompletionTime(pcb.getStartTime() + pcb.getBurstTime());
				
				currTime = pcb.getCompletionTime();
				
				pcb.setTurnaroundTime(pcb.getCompletionTime() - pcb.getArrivalTime());
				
				pcb.setWaitingTime(pcb.getTurnaroundTime() - pcb.getBurstTime());
				
				pcb.setRemainingTime(0);
				
				compProcesses++;	
			}else{
				if(processIndex < processes.size()){
					currTime = processes.get(processIndex).getArrivalTime();
				}
			}
		}
		
		calculatePerformanceMetrics(processes);
	}
	
	public static void roundRobin(List<ProcessControlBlock> processes, Scanner input)
	{
	    int quantum = -1;

	    // input loop for quantum
	    while (quantum <= 0)
	    {
	        System.out.print("\nEnter the quantum for Round Robin: ");
	        if (input.hasNextInt())
	        {
	            quantum = input.nextInt();
	            if (quantum <= 0)
	            {
	                System.err.println("Quantum must be > 0!");
	            }
	        } else {
	            System.err.println("Invalid input!");
	            input.next();
	        }
	    }

	    System.out.println("\n--- Running Round Robin (Quantum: " + quantum + ") ---");

	    // Make a fresh copy of the processes so we don't reuse state from previous scheduling runs
	    List<ProcessControlBlock> tempProcesses = new ArrayList<>();
	    for (ProcessControlBlock p : processes) {
	        // Use the constructor - it initializes remainingTime = burstTime and startTime = -1
	        ProcessControlBlock copy = new ProcessControlBlock(p.getPid(), p.getArrivalTime(), p.getBurstTime(), p.getPriority(), p.getQueueLevel());
	        // Ensure metrics are reset/clean
	        copy.setCompletionTime(0);
	        copy.setTurnaroundTime(0);
	        copy.setWaitingTime(0);
	        copy.setResponseTime(0);
	        tempProcesses.add(copy);
	    }

	    // sort by arrival time
	    tempProcesses.sort(Comparator.comparingInt(ProcessControlBlock::getArrivalTime));

	    int currentTime = 0;
	    int completed = 0;
	    Queue<ProcessControlBlock> readyQueue = new LinkedList<>();

	    int pIndex = 0; // next process to arrive

	    while (completed < tempProcesses.size()) {

	        // add all newly arrived processes to the queue
	        while (pIndex < tempProcesses.size() && tempProcesses.get(pIndex).getArrivalTime() <= currentTime) {
	            readyQueue.add(tempProcesses.get(pIndex));
	            pIndex++;
	        }

	        // if ready queue is empty, jump to next arrival (no change to existing objects)
	        if (readyQueue.isEmpty()) {
	            if (pIndex < tempProcesses.size()) {
	                currentTime = tempProcesses.get(pIndex).getArrivalTime();
	                // add that process now so we can run it immediately
	                readyQueue.add(tempProcesses.get(pIndex));
	                pIndex++;
	            }
	            continue;
	        }

	        // run the process at the head of the ready queue
	        ProcessControlBlock current = readyQueue.poll();

	        // set start time if this is the first time the process runs
	        if (current.getStartTime() == -1) {
	            current.setStartTime(currentTime);
	        }

	        // Determine how long to run: min(quantum, remaining)
	        int timeToRun = Math.min(quantum, current.getRemainingTime());
	        // advance time & decrease remaining
	        current.setRemainingTime(current.getRemainingTime() - timeToRun);
	        currentTime += timeToRun;

	        // Add any processes that arrived while we were running
	        while (pIndex < tempProcesses.size() && tempProcesses.get(pIndex).getArrivalTime() <= currentTime) {
	            readyQueue.add(tempProcesses.get(pIndex));
	            pIndex++;
	        }

	        // If not finished, requeue. If finished, set completionTime and count it.
	        if (current.getRemainingTime() > 0) {
	            readyQueue.add(current);
	        } else {
	            completed++;
	            current.setCompletionTime(currentTime);
	        }
	    }

	    // Calculate & print metrics using the temporary list (so original list remains unchanged)
	    calculatePerformanceMetrics(tempProcesses);
	}
	
	public static void priorityScheduling(List <ProcessControlBlock> processes)
	{
		//Reset processes to initial state
		List<ProcessControlBlock> tempProcesses = new ArrayList<>();
    	
    	for (ProcessControlBlock p : processes) 
    	{
        	tempProcesses.add(new ProcessControlBlock(p.getPid(), p.getArrivalTime(), p.getBurstTime(), p.getPriority(), p.getQueueLevel()));
    	}
    	
    	//Sort the temporary list by arrival time 
    	tempProcesses.sort(Comparator.comparingInt(ProcessControlBlock::getArrivalTime)); 
    	
    	//Ready Queue: PRIORITY QUEUE IMPLEMENTATION
    	PriorityQueue<ProcessControlBlock> readyQueue = new PriorityQueue<>
    	(
	        (p1, p2) -> 
	        {
	            if (p1.getPriority() != p2.getPriority()) {
	                // Primary sort: Lower priority number (e.g., 1) is selected first
	                return Integer.compare(p1.getPriority(), p2.getPriority()); 
	            } else {
	                // Tie-breaker: If priorities are equal, use FCFS (earlier arrival time)
	                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime()); 
	            }
	        }
	    ); 
	    
		int currentTime = 0;
	    int completedCount = 0;
	    int nextProcessIndex = 0;
	    
		// Loop until all processes are completed
	    while (completedCount < tempProcesses.size()) 
	    {
	        
	        //Add newly arrived processes to the Ready Queue
	        while (nextProcessIndex < tempProcesses.size() && tempProcesses.get(nextProcessIndex).getArrivalTime() <= currentTime) 
	        {
	            readyQueue.add(tempProcesses.get(nextProcessIndex));
	            nextProcessIndex++;
	        }
	        
	        //Check if Ready Queue is empty (and handle time jump if necessary)
	        if (readyQueue.isEmpty()) 
	        {
	            if (nextProcessIndex < tempProcesses.size()) {
	                currentTime = tempProcesses.get(nextProcessIndex).getArrivalTime();
	            } 
	            else 
	            {
	                break; 
	            }
	            continue;
	        }
	        
	        //Select the highest priority process (NON-PREEMPTIVE SELECTION)
	        ProcessControlBlock current = readyQueue.poll();
	        
	        //Record Start Time
	        if (current.getStartTime() == -1) 
	        {
	            current.setStartTime(currentTime);
	        }
	        
	        //Run Non-Preemptively for its full Burst Time
	        int executionTime = current.getRemainingTime(); 
	        currentTime += executionTime;
	        
	        //Update the process state and metrics
	        current.setRemainingTime(0);
	        completedCount++;
	        current.setCompletionTime(currentTime);
	        
	        //Update the original list's metrics for final calculation/output
	        for (ProcessControlBlock originalPcb : processes) 
	        {
	            if (originalPcb.getPid() == current.getPid()) 
	            {
	                originalPcb.setStartTime(current.getStartTime());
	                originalPcb.setCompletionTime(current.getCompletionTime());
	                break;
	            }
	        }
	    }
	
	   	// 3. Calculate and display metrics
    	calculatePerformanceMetrics(processes);
	}
	
	
	
	
	public static void multiLevelQueue(List <ProcessControlBlock> processes, Scanner scanner){
		int quantum= -1;
		boolean validInput = false;
		int currentTime= 0;
		int completedNum= 0;
		int nextProcess= 0; //used as the index of the next process
		
		try{	
			while(!validInput){
				System.out.print("Enter the time quantum of the higher priority processes: ");
				if(scanner.hasNextInt()){
					quantum = scanner.nextInt();
					if(quantum > 0){
						validInput = true;
					}else{
						System.err.println("\nINVALID INPUT :(\nPlease enter an integer greater than 0.\n");
					}
				}else{
					System.err.println("\nINVALID INPUT :(\nPlease enter an integer.\n");
					scanner.next();
				}
			}
			
			//Creates a temporary list
			List<ProcessControlBlock> tempProcesses = new ArrayList<>();
	    	
			//The original input values of the processes
	    	for (ProcessControlBlock p : processes) 
	    	{
	        	tempProcesses.add(new ProcessControlBlock(p.getPid(), p.getArrivalTime(), p.getBurstTime(), p.getPriority(), p.getQueueLevel()));
	    	}
	    	
	    	//Sort the temporary list by arrival time 
	    	tempProcesses.sort(Comparator.comparingInt(ProcessControlBlock::getArrivalTime));
			
			//The queues for the split of the ready queue is created
			Queue<ProcessControlBlock> highPriority= new LinkedList<>(); //Interactive
			Queue<ProcessControlBlock> lowPriority= new LinkedList<>(); //Batch
			
			while(completedNum< tempProcesses.size()) {
				//Check to make sure the processes arent finished
				//use the next process as an index to find the arrival time of the next process and check if it is less than or greater than the current time
				while(nextProcess< tempProcesses.size() && tempProcesses.get(nextProcess).getArrivalTime()<= currentTime){
					//Use the priority of the process to place it in its respective queue
					if(tempProcesses.get(nextProcess).getPriority()<= 2) {
						highPriority.add(tempProcesses.get(nextProcess));
					}
					else {
						lowPriority.add(tempProcesses.get(nextProcess));
					}
					
					nextProcess++;
				}
				
				//The actual scheduling implementation
				//Objects to keep track of the current process and the current queue
				ProcessControlBlock curr= null;
				Queue<ProcessControlBlock> active= new LinkedList<>();
				
				//If the high priority (RR) queue isn't empty
				if(!highPriority.isEmpty()){
					curr= highPriority.poll(); //Dequeue the head of the RR queue and store in the current process object
					active= highPriority; //The RR queue is made the current queue
				}
				//If the low priority (FCFS) queue isn't empty
				else if(!lowPriority.isEmpty()){
					curr= lowPriority.poll(); //Dequeue the head of the FCFS queue and store in the current process object
					active= lowPriority; //The FCFS queue is made the current queue
				}
				else{
					//Since there is no process currently ready, the current time is assigned the next process arrival time
					if (nextProcess < tempProcesses.size()) {
						currentTime = tempProcesses.get(nextProcess).getArrivalTime();
					}
					continue;//ignore the rest of the loop block and goes to the next iteration
				}
				
				//Current process is executed
				//its start time is made to be the current time
				if(curr.getStartTime()== -1) {
					curr.setStartTime(currentTime);					
				}
				
				int duration;
				
				if(active== highPriority) {
					//if the active queue is high priority, the duration of the process is
					//the lower number of the quantum and remaining time is its duration
					duration= Math.min(quantum, curr.getRemainingTime());
				}
				else {
					//if the active queue is low priority, the duration of the process is the remaining time/burst time of the FCFS process
					duration= curr.getRemainingTime(); //same as the burst time for FCFS, because these run completely before yielding
				}
				
				curr.setRemainingTime(curr.getRemainingTime()- duration); //Remaining time of the process is the difference between the previoru remaining time and the duration it ran for
				currentTime+= duration; //The current time is incremented by the duration of the process
				
				//Check for any process arrivals during the execution of the current process
				while (nextProcess < tempProcesses.size() && tempProcesses.get(nextProcess).getArrivalTime()<= currentTime)
				{
					if (tempProcesses.get(nextProcess).getPriority() <= 2) {
						highPriority.add(tempProcesses.get(nextProcess));
					} else {
						lowPriority.add(tempProcesses.get(nextProcess));
					}
					nextProcess++;
				}
				
				if(curr.getRemainingTime()> 0) {
					//If the process wasn't completed which can only be the case for RR the process is requeued
					highPriority.add(curr);
				}
				else {
					//The process was completed
					completedNum++; //number of completed processes is incremented
					curr.setCompletionTime(currentTime); //The current time is set as the process's completion time
				}
			}
			calculatePerformanceMetrics(tempProcesses);

		} 
		catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public static void main(String[] args) {
		try(Scanner scanner = new Scanner(System.in)){
			int numOfProcesses = 0;
			boolean validInput = false;
			System.out.println("****WELCOME TO THE PROCESS SCHEDULING SIMULATOR****\n");
			
			while(!validInput){
				System.out.print("Enter the number of processes to be scheduled: ");
				if(scanner.hasNextInt()){
					numOfProcesses = scanner.nextInt();
					if(numOfProcesses > 0){
						validInput = true;
					}else{
						System.err.println("\nINVALID INPUT :(\nPlease enter an integer greater than 0.\n");
					}
				}else{
					System.err.println("\nINVALID INPUT :(\nPlease enter an integer.\n");
					scanner.next();
				}
			}
			
			List<ProcessControlBlock> processes = new ArrayList<>();
			System.out.println("\n---Process Details---");
			for(int i = 1; i <= numOfProcesses; i++){
				ProcessControlBlock pcb = new ProcessControlBlock(-1, -1, -1, -1, -1);
				pcb.pid = i;
				System.out.println("\nPROCESS " + i);
				
				while(pcb.arrivalTime < 0){
					System.out.print("Arrival Time: ");
					if(scanner.hasNextInt()){
						pcb.arrivalTime = scanner.nextInt();
						if(pcb.arrivalTime < 0){
							System.err.println("Time cannot be negative. Enter valid arrival time.");
						}
					}else{
						System.err.println("\nINVALID INPUT :(\nPlease enter an integer.\n");
						scanner.next();
					}
				}
				
				while(pcb.burstTime <= 0){
					System.out.print("Burst Time: ");
					if(scanner.hasNextInt()){
						pcb.burstTime = scanner.nextInt();
						if(pcb.burstTime <= 0){
							System.err.println("Time cannot be negative. Enter valid burst time.");
						}
					}else{
						System.err.println("\nINVALID INPUT :(\nPlease enter an integer.\n");
						scanner.next();
					}
				}
				
				while(pcb.priority < 1 || pcb.priority > 4){
					System.out.print("Priority (1-4): ");
					if(scanner.hasNextInt()){
						pcb.priority = scanner.nextInt();
						if(pcb.priority < 1 || pcb.priority > 4){
							System.err.println("Priority must be between 1-4, inclusive.");
						}
					}else{
						System.err.println("\nINVALID INPUT :(\nPlease enter an integer.\n");
						scanner.next();
					}
				}
				
				processes.add(pcb);
				System.out.println("Process " + pcb.pid + " added successfully.");
			}
			
			System.out.println("\n--- Running First Come First Serve (FCFS) ---\n");
			firstComeFirstServe(processes);

			roundRobin(processes, scanner);
			
		    System.out.println("\n--- Running Priority Scheduling (Non-Preemptive) ---");
		    priorityScheduling(processes);
			
			System.out.println("\n--- Running Multi Level Queue (MLQ) ---\n");
			multiLevelQueue(processes, scanner);
			
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}	
	}
}
