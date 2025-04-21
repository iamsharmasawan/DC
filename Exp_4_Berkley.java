import java.util.*;
import java.time.LocalTime;
import java.time.Duration;

public class Exp_4_Berkley {
    // Helper method to convert HH:MM string to total seconds
    public static int convertToSeconds(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 3600 + minutes * 60;
    }

    // Helper method to convert seconds back to HH:MM format
    public static String convertToTimeString(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Read number of clients
        System.out.print("Enter number of clients: ");
        int nc = sc.nextInt();
        sc.nextLine(); // consume leftover newline

        // Step 2: Read client times
        System.out.print("Enter time of clients in HH:MM: ");
        String[] clientTimes = sc.nextLine().split(" ");

        // Step 3: Read server time
        System.out.print("Enter time of server in HH:MM: ");
        String serverTime = sc.nextLine();

        // Step 4: Convert all times to seconds
        int serverSeconds = convertToSeconds(serverTime);
        int[] clientSeconds = new int[nc];

        for (int i = 0; i < nc; i++) {
            clientSeconds[i] = convertToSeconds(clientTimes[i]);
        }

        // Step 5: Calculate average time (in seconds)
        int total = serverSeconds;
        for (int seconds : clientSeconds) {
            total += seconds;
        }

        int avgTime = total / (nc + 1); // Including server

        System.out.println();

        // Step 6: Synchronize server
        if (serverSeconds == avgTime) {
            System.out.println("Server is already synchronized.");
        } else if (serverSeconds < avgTime) {
            int diff = avgTime - serverSeconds;
            System.out.println("Server is behind. Increase its clock by " + Duration.ofSeconds(diff) +
                    ", so now its time is " + convertToTimeString(avgTime));
        } else {
            int diff = serverSeconds - avgTime;
            System.out.println("Server is ahead. Decrease its clock by " + Duration.ofSeconds(diff) +
                    ", so now its time is " + convertToTimeString(avgTime));
        }

        System.out.println();

        // Step 7: Synchronize clients
        for (int i = 0; i < nc; i++) {
            int clientDiff = Math.abs(clientSeconds[i] - avgTime);
            if (clientSeconds[i] > avgTime) {
                System.out.println("Client " + i + " is ahead by " + Duration.ofSeconds(clientDiff));
                System.out.println("Therefore, decrease its clock to get " + convertToTimeString(avgTime) + "\n");
            } else if (clientSeconds[i] == avgTime) {
                System.out.println("Client " + i + " is already synchronized to time " + convertToTimeString(avgTime));
            } else {
                System.out.println("Client " + i + " is behind by " + Duration.ofSeconds(clientDiff));
                System.out.println("Therefore, increase its clock to get " + convertToTimeString(avgTime) + "\n");
            }
        }

        sc.close();
    }
}
