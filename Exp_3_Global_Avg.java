import java.util.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Exp_3_Global_Avg {

    // Formatter to parse and format time in HH:mm format
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // Method to parse time string to LocalTime
    private static LocalTime parseTime(String timeStr) {
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    // Method to calculate skew in minutes between current time and agreed time
    private static int calculateSkew(LocalTime currentTime, LocalTime agreedTime) {
        return (int) Duration.between(agreedTime, currentTime).toMinutes();
    }

    // Method to adjust time by given minutes
    private static LocalTime adjustTime(LocalTime time, int minutes) {
        return time.plusMinutes(minutes);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Input agreed-upon time
        System.out.print("Enter Agreed Upon Time (HH:mm): ");
        String agreedTimeStr = scanner.nextLine();
        LocalTime agreedTime = parseTime(agreedTimeStr);

        // Step 2: Input number of machines
        System.out.print("Enter number of machines: ");
        int numMachines = Integer.parseInt(scanner.nextLine());

        // Step 3: Input current times of machines
        System.out.print("Enter current times of " + numMachines + " machines (space-separated HH:mm): ");
        String[] timeInputs = scanner.nextLine().split(" ");

        // Map to store current time of each machine
        Map<Integer, LocalTime> currentTimes = new HashMap<>();
        // Map to store list of skews for each machine over time
        Map<Integer, List<Integer>> skewRecords = new HashMap<>();

        // Initialize current times and skew records
        for (int i = 0; i < numMachines; i++) {
            LocalTime time = parseTime(timeInputs[i]);
            currentTimes.put(i + 1, time); // Machine indices start from 1
            skewRecords.put(i + 1, new ArrayList<>());
        }

        System.out.println("\nAgreed Upon Time: " + agreedTime);

        // Step 4: Initial skew calculation
        System.out.println("\nInitial Skews:");
        for (int i = 1; i <= numMachines; i++) {
            int skew = calculateSkew(currentTimes.get(i), agreedTime);
            skewRecords.get(i).add(skew);
            System.out.println(
                    "Machine " + i + " - Current Time: " + currentTimes.get(i) + ", Skew: " + skew + " minutes");
        }

        // Step 5: Simulate time progression
        int simulationSteps = numMachines - 1; // Number of simulation steps
        for (int step = 1; step <= simulationSteps; step++) {
            System.out.println("\nAfter " + (step * 5) + " minutes:");

            // Update current times by adding 5 minutes
            for (int i = 1; i <= numMachines; i++) {
                LocalTime updatedTime = adjustTime(currentTimes.get(i), 5);
                currentTimes.put(i, updatedTime);
            }

            // Calculate and record new skews
            for (int i = 1; i <= numMachines; i++) {
                int skew = calculateSkew(currentTimes.get(i), agreedTime);
                skewRecords.get(i).add(skew);
                System.out.println(
                        "Machine " + i + " - Current Time: " + currentTimes.get(i) + ", Skew: " + skew + " minutes");
            }
        }

        // Step 6: Calculate average skew and adjust clocks
        System.out.println("\nFinal Adjustments:");
        for (int i = 1; i <= numMachines; i++) {
            List<Integer> skews = skewRecords.get(i);
            int totalSkew = skews.stream().mapToInt(Integer::intValue).sum();
            int averageSkew = totalSkew / skews.size();

            LocalTime originalTime = currentTimes.get(i);
            LocalTime adjustedTime = adjustTime(originalTime, -averageSkew);

            System.out.println("Machine " + i + " - Average Skew: " + averageSkew + " minutes");
            if (averageSkew > 0) {
                System.out.println("Adjusting clock backward by " + averageSkew + " minutes to " + adjustedTime);
            } else if (averageSkew < 0) {
                System.out
                        .println("Adjusting clock forward by " + Math.abs(averageSkew) + " minutes to " + adjustedTime);
            } else {
                System.out.println("Clock is already synchronized.");
            }
        }

        scanner.close();
    }
}
