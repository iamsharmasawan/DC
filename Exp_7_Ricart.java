import java.util.*;

public class Exp_7_Ricart {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Request set will contain the list of sites each site must communicate with
        Map<Integer, List<Integer>> requestSet = new HashMap<>();

        System.out.print("Enter number of sites: ");
        int n = sc.nextInt();

        // Building the request set for each site (excluding itself)
        for (int i = 1; i <= n; i++) {
            List<Integer> lst = new ArrayList<>();
            for (int j = 1; j <= n; j++) {
                if (i != j) {
                    lst.add(j);
                }
            }
            requestSet.put(i, lst);
        }

        System.out.print("\nEnter number of sites requesting to enter CS: ");
        int m = sc.nextInt();

        // List to hold requests: each element is (timestamp, site number)
        List<int[]> requestSites = new ArrayList<>();

        // Taking request input from user
        for (int i = 0; i < m; i++) {
            System.out.print("Enter the timestamp and site no.: ");
            int timestamp = sc.nextInt();
            int site = sc.nextInt();
            requestSites.add(new int[] { timestamp, site });
        }

        System.out.println();

        // Sort based on timestamps (simulating the Ricart-Agrawala timestamp rule)
        requestSites.sort((a, b) -> Integer.compare(a[0], b[0]));

        // Sending request messages
        for (int[] req : requestSites) {
            int site = req[1];
            for (int dest : requestSet.get(site)) {
                System.out.println("Site " + site + " sending request message to Site " + dest);
            }
        }

        System.out.println();

        // Keep track of current requesting sites
        List<Integer> requestingSites = new ArrayList<>();
        for (int[] req : requestSites) {
            requestingSites.add(req[1]);
        }

        // Processing reply messages and entry to CS
        for (int[] req : requestSites) {
            int curSite = req[1]; // Current site wanting to enter CS
            int curTime = req[0]; // Its timestamp

            for (int peer : requestSet.get(curSite)) {
                // If the other site is not in request list -> it will reply immediately
                if (!requestingSites.contains(peer)) {
                    System.out.println("Site " + peer + " sending reply message to Site " + curSite);
                } else {
                    // Check the timestamp of peer site
                    for (int[] check : requestSites) {
                        if (check[1] == peer) {
                            if (check[0] > curTime) {
                                System.out.println("Site " + peer + " sending reply message to Site " + curSite);
                            }
                            break;
                        }
                    }
                }
            }

            System.out.println("\nSince Site " + curSite
                    + " has received reply messages from all sites in its request set, it enters the CS.");
            if (!requestingSites.isEmpty()) {
                System.out.println("\nSite " + curSite + " exits the CS\n");
            }
            requestingSites.remove(0); // Remove the site that has exited CS
        }

        sc.close();
    }
}
