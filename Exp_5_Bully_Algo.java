import java.util.*;

public class Exp_5_Bully_Algo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Read input from the user
        System.out.print("Enter the number of machines: ");
        int n = sc.nextInt();

        System.out.print("Enter the index of machine P which sends the message: ");
        int p_ind = sc.nextInt(); // machine that starts the election

        System.out.print("Enter the index of the failed coordinator: ");
        int failed_ind = sc.nextInt(); // the coordinator that has failed

        int new_coordinator = -1; // to store the new coordinator index

        // Step 2: Election messages from machine P
        for (int i = p_ind; i < n - 1; i++) { // last machine can't send message further
            if (i != failed_ind) {
                System.out.print(i + " sends an election message to machine ---> ");

                List<Integer> eligibleMachines = new ArrayList<>();

                // Step 3: i sends message to all higher-numbered machines
                for (int j = i + 1; j < n; j++) {
                    System.out.print(j + "  ");
                }

                // Step 4: Higher-numbered machines (except failed one) reply with OK
                for (int j = i + 1; j < n; j++) {
                    if (j != failed_ind) {
                        eligibleMachines.add(j);
                        new_coordinator = j; // highest among responders becomes the new coordinator
                    }
                }

                // Step 5: Print OK messages from eligible machines
                if (!eligibleMachines.isEmpty()) {
                    System.out.print("\nOK message sent by machine ---> ");
                    for (int machine : eligibleMachines) {
                        System.out.print(machine + " ");
                    }
                }
                System.out.println("\n");
            }
        }

        // Step 6: Display final coordinator
        System.out.println("New coordinator: " + new_coordinator);

        // Step 7: Coordinator informs all lower-numbered machines
        System.out.print("\n" + new_coordinator + " sends coordinator message to machine ---> ");
        for (int i = 0; i < new_coordinator; i++) {
            if (i != failed_ind) {
                System.out.print(i + "  ");
            }
        }

        sc.close();
    }
}
