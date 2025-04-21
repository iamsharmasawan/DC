import java.util.*;

class Main {
    // Total number of processes
    static int P = 5;

    // Total number of resource types
    static int R = 3;

    // Function to calculate the "Need" matrix = Max - Allocation
    static void calculateNeed(int need[][], int maxm[][], int allot[][]) {
        for (int i = 0; i < P; i++) {
            for (int j = 0; j < R; j++) {
                // Need[i][j] = maximum resources a process needs - currently allocated
                need[i][j] = maxm[i][j] - allot[i][j];
            }
        }
    }

    // Function to print any 2D matrix (like Need or Allocation) in a readable
    // format
    static void printMatrix(String label, int[][] matrix) {
        System.out.println(label);
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("P" + i + ": ");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println(); // new line for each process
        }
    }

    // Function to check if the system is in a safe state or not
    static boolean isSafe(int processes[], int avail[], int maxm[][], int allot[][]) {
        // Step 1: Calculate the Need matrix
        int[][] need = new int[P][R];
        calculateNeed(need, maxm, allot);

        // Print the calculated Need matrix
        printMatrix("Initial Need Matrix:", need);

        // Boolean array to mark whether each process is finished
        boolean[] finish = new boolean[P];

        // This array will store the safe sequence
        int[] safeSeq = new int[P];

        // Make a copy of available resources (these are the current resources available
        // in the system)
        int[] work = Arrays.copyOf(avail, R);

        System.out.println("\nInitial Available Resources: " + Arrays.toString(work));

        int count = 0; // How many processes have safely finished

        // Keep checking until all processes are finished
        while (count < P) {
            boolean found = false; // To check if a process could safely execute in this iteration

            // Try to find a process whose needs can be satisfied with current available
            // resources
            for (int p = 0; p < P; p++) {
                if (!finish[p]) { // Process not yet completed
                    int j;

                    // Check if all needed resources for process 'p' are available
                    for (j = 0; j < R; j++) {
                        if (need[p][j] > work[j]) {
                            break; // If even one resource is not available, break the loop
                        }
                    }

                    // If all resources needed by process p are available
                    if (j == R) {
                        // Simulate process execution
                        System.out.println("\nIteration " + (count + 1) + ": Process P" + p + " is executing.");
                        System.out.println("Available Resources before allocation: " + Arrays.toString(work));

                        // After process p completes, it will release its allocated resources back to
                        // available pool
                        for (int k = 0; k < R; k++) {
                            work[k] += allot[p][k];
                        }

                        System.out.println("Available Resources after allocation: " + Arrays.toString(work));

                        // Add this process to safe sequence
                        safeSeq[count++] = p;

                        // Mark this process as finished
                        finish[p] = true;

                        // Mark that we found a process that could run
                        found = true;
                    }
                }
            }

            // If no process could be found in this iteration, system is in unsafe state
            if (!found) {
                System.out.println("System is not in a safe state.");
                return false;
            }
        }

        // If we reach here, all processes could complete
        System.out.print("\nSystem is in a safe state.\nSafe sequence is: ");
        for (int i = 0; i < P; i++)
            System.out.print("P" + safeSeq[i] + " ");

        return true;
    }

    public static void main(String[] args) {
        // Define process IDs
        int processes[] = { 0, 1, 2, 3, 4 };

        // Define initial available resources of each type
        int avail[] = { 3, 3, 2 }; // Total resources in system - sum of allocated

        // Maximum resources each process may request
        int maxm[][] = {
                { 7, 5, 3 },
                { 3, 2, 2 },
                { 9, 0, 2 },
                { 4, 2, 2 },
                { 5, 3, 3 }
        };

        // Resources currently allocated to each process
        int allot[][] = {
                { 0, 1, 0 },
                { 2, 0, 0 },
                { 3, 0, 2 },
                { 2, 1, 1 },
                { 0, 0, 2 }
        };

        // Check if the system is in a safe state
        isSafe(processes, avail, maxm, allot);
    }
}
