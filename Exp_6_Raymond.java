import java.util.*;

public class Exp_6_Raymond {

    // Total number of processes/nodes in the system
    static int n = 5;

    // Each process maintains a queue of requests
    static Map<Integer, List<Integer>> requestQueue = new HashMap<>();

    // This map holds which process currently holds the token or points to the one
    // that does
    static Map<Integer, Integer> holder = new HashMap<>();

    // Token map shows whether a process currently holds the token (1 = yes, 0 = no)
    static Map<Integer, Integer> token = new HashMap<>();

    // This is the adjacency matrix of the tree (i.e., which node is connected to
    // which parent)
    static int[][] adjMatrix = {
            { 1, 0, 0, 0, 0 }, // 0 is root
            { 1, 0, 0, 0, 0 }, // 1's parent is 0
            { 1, 0, 0, 0, 0 }, // 2's parent is 0
            { 0, 1, 0, 0, 0 }, // 3's parent is 1
            { 0, 1, 0, 0, 0 } // 4's parent is 1
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initialize the request queue for each process as empty
        for (int i = 0; i < n; i++) {
            requestQueue.put(i, new ArrayList<>());
        }

        // Initialize holder mapping — who is holding token or directing towards token
        // holder
        holder.put(0, 0); // 0 holds the token (initial root)
        holder.put(1, 0);
        holder.put(2, 0);
        holder.put(3, 1);
        holder.put(4, 1);

        // Initialize token ownership (only root node has it initially)
        token.put(0, 1); // root process has the token
        token.put(1, 0);
        token.put(2, 0);
        token.put(3, 0);
        token.put(4, 0);

        System.out.println("Raymond Tree based Mutual Exclusion\n");
        System.out.println("Adjacency Matrix for the spanning tree:\n");

        // Display adjacency matrix (represents the tree structure)
        for (int[] row : adjMatrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        // Ask user for the requesting process
        System.out.print("\nEnter the process who wants to enter the CS: ");
        int reqProcess = sc.nextInt();

        // Start token requesting chain from requester to root (if needed)
        int parent = findParent(reqProcess);

        // Simulate token passing until requester finally receives it
        while (token.get(reqProcess) != 1) {
            int child = requestQueue.get(parent).get(0); // Get first requester
            requestQueue.get(parent).remove(0); // Remove it from queue

            // Update holders and token possession
            holder.put(parent, child); // Parent passes token to child
            holder.put(child, 0); // Child now knows parent is root
            token.put(parent, 0); // Parent no longer has token
            token.put(child, 1); // Child now has token

            System.out.println(
                    "\nParent process " + parent + " has the token and sends the token to request process " + child);
            printRequestQueue();

            parent = child; // Continue simulation from the child
        }

        // When token is finally with the requester
        if (token.get(parent) == 1 && requestQueue.get(parent).get(0) == parent) {
            requestQueue.get(parent).remove(0); // Remove self from own queue
            holder.put(parent, parent); // Holder is itself now
            System.out.println("\nProcess " + parent + " enters the Critical Section");
            printRequestQueue();
        }

        // If the request queue is now empty, it can release the CS
        if (requestQueue.get(parent).isEmpty()) {
            System.out
                    .println("\nRequest queue of process " + parent + " is empty. Therefore Release Critical Section");
        }

        // Print final state of holders
        System.out.println("\nHolder: " + holder);
    }

    // Recursive function to find the parent with token and send request up the tree
    static int findParent(int reqProcess) {
        // Add self to its own request queue (wants CS)
        requestQueue.get(reqProcess).add(reqProcess);

        int parent = -1;

        // Find immediate parent from the adjacency matrix
        for (int i = 0; i < n; i++) {
            if (adjMatrix[reqProcess][i] == 1) {
                parent = i;
                requestQueue.get(parent).add(reqProcess); // Send request to parent
                break;
            }
        }

        System.out.println("\nProcess " + reqProcess + " sending Request to parent Process " + parent);
        printRequestQueue();

        // If parent has token, return it. Else, recursively go up the tree.
        if (token.get(parent) == 1) {
            return parent;
        } else {
            return findParent(parent); // Recursive call up the tree
        }
    }

    // Utility function to print request queues of all processes
    static void printRequestQueue() {
        System.out.println("\nRequest Queue:");
        for (int i = 0; i < n; i++) {
            System.out.println("Process " + i + ": " + requestQueue.get(i));
        }
    }
}
