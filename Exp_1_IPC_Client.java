import java.io.*;
import java.net.*;

public class Exp_1_IPC_Client {
    public static void main(String[] args) {

        // Try-with-resources: automatically closes the socket when done
        try (Socket socket = new Socket("localhost", 5000)) {
            /*
             * Establishes a connection to the server running on the same machine
             * (localhost)
             * and listening on port 5000.
             * 
             * If the server is not running or the port is unavailable, an exception will be
             * thrown.
             */

            // Input stream from the console (user types message here)
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            /*
             * System.in -> reads raw bytes
             * InputStreamReader -> converts bytes to characters
             * BufferedReader -> buffers characters and reads lines efficiently
             */

            // Output stream to the server (used to send messages)
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            /*
             * socket.getOutputStream() gives an OutputStream to write data to the server.
             * PrintWriter makes it easier to send strings (lines).
             * 'true' enables auto-flushing after every println().
             */

            // Input stream from the server (to receive server replies)
            BufferedReader serverResponse = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /*
             * Reads messages that come back from the server in response to what the client
             * sends.
             */

            String message;

            // Instruction to user
            System.out.println("Enter message to be sent to server (type 'exit' to quit): ");

            // Infinite loop until the user types "exit"
            while (!(message = input.readLine()).equalsIgnoreCase("exit")) {
                /*
                 * input.readLine() reads a line entered by the user from the console.
                 * If the message is not "exit", we send it to the server.
                 */

                // Send message to server
                output.println(message);

                // Read and display server's response
                System.out.println(serverResponse.readLine());
            }

            // When "exit" is typed, loop ends and socket will close automatically due to
            // try-with-resources

        } catch (IOException e) {
            // Catch and print any IOException (e.g., server not running)
            e.printStackTrace();
        }

    }
}
