import java.util.*;

// LoadBalancer class to manage server selection
class LoadBalancer {
    List<String> serverList; // List of servers
    int currentIndex; // Tracks which server will handle the next request

    // Constructor: initialize server list and starting index
    LoadBalancer(List<String> serverList) {
        this.serverList = serverList;
        this.currentIndex = 0;
    }

    // Returns the next server in round-robin fashion
    public String nextServer() {
        // Get the server at the current index
        String nextServer = serverList.get(currentIndex);

        // Move to the next server index, loop back to start if at end
        currentIndex = (currentIndex + 1) % serverList.size();

        return nextServer;
    }
}

// Main class
class Exp_9_Load_Balancing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Get the number of servers from the user
        System.out.print("Enter the number of servers: ");
        int noServer = sc.nextInt();
        sc.nextLine(); // Consume newline character after nextInt()

        // Step 2: Input the names of the servers
        List<String> serverList = new ArrayList<>();
        for (int i = 0; i < noServer; i++) {
            System.out.print("Enter name of Server " + (i + 1) + ": ");
            String serverName = sc.nextLine();
            serverList.add(serverName); // Add server name to the list
        }

        // Step 3: Get the number of requests to handle
        System.out.print("Enter the number of incoming requests: ");
        int noRequest = sc.nextInt();

        // Step 4: Create the LoadBalancer object
        LoadBalancer lb = new LoadBalancer(serverList);

        // Step 5: Assign each request to a server in round-robin order
        for (int i = 0; i < noRequest; i++) {
            String nextServer = lb.nextServer(); // Get the next server
            System.out.println("Request " + (i + 1) + " routed to " + nextServer);
        }

        sc.close(); // Close the scanner to avoid memory leak
    }
}
