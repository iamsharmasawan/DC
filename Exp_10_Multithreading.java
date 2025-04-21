
// Importing required classes for multithreading
import java.lang.Thread;

public class Exp_10_Multithreading {

    // Function to check if a number is prime
    public static boolean isPrime(int n) {
        // Prime numbers are greater than 1
        if (n <= 1)
            return false;

        // Check divisibility from 2 to n-1
        for (int i = 2; i < n; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    // Thread to generate prime numbers up to a limit
    static class PrimeThread extends Thread {
        int limit;

        // Constructor to initialize the limit
        PrimeThread(int limit) {
            this.limit = limit;
        }

        @Override
        public void run() {
            // Loop from 2 to the given limit
            for (int i = 2; i <= limit; i++) {
                // Call the isPrime function to check
                if (isPrime(i)) {
                    System.out.println("Prime: " + i);
                }
            }
        }
    }

    // Thread to generate Fibonacci sequence up to a certain number of terms
    static class FibonacciThread extends Thread {
        int terms;

        // Constructor to initialize number of terms
        FibonacciThread(int terms) {
            this.terms = terms;
        }

        @Override
        public void run() {
            int a = 0, b = 1;

            // If at least one term, print first number
            if (terms >= 1) {
                System.out.println("Fibo 1: " + a);
            }

            // If at least two terms, print second number
            if (terms >= 2) {
                System.out.println("Fibo 2: " + b);
            }

            // Loop for the rest of the terms (starting from the 3rd)
            for (int i = 2; i < terms; i++) {
                int c = a + b;
                System.out.println("Fibo " + (i + 1) + ": " + c);
                a = b;
                b = c;
            }
        }
    }

    // Main method: entry point
    public static void main(String[] args) {
        // Create thread objects with specified limits
        PrimeThread primeThread = new PrimeThread(20); // Print primes up to 20
        FibonacciThread fibonacciThread = new FibonacciThread(10); // Generate 10 Fibonacci numbers

        // Start both threads concurrently
        primeThread.start();
        fibonacciThread.start();

        // Wait for both threads to finish
        try {
            primeThread.join();
            fibonacciThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final message after threads complete
        System.out.println("Both threads have finished executing.");
    }
}
