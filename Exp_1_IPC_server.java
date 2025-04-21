import java.io.*; // For InputStreamReader, BufferedReader, PrintWriter
import java.net.*; // For ServerSocket, Socket

public class Exp_1_IPC_server {
	public static void main(String[] args) {

		// Try-with-resources ensures that the ServerSocket is closed automatically
		try (ServerSocket serverSocket = new ServerSocket(5000)) {
			/*
			 * Step 1: Create a ServerSocket object that listens on port 5000.
			 * This opens the server and waits for incoming client connections on that port.
			 * If the port is already in use, an exception will be thrown.
			 */

			System.out.println("Server listening on port 5000");

			// Step 2: Accept an incoming connection (blocking call, waits until a client
			// connects)
			Socket socket = serverSocket.accept();

			System.out.println("Client connected to the server");

			// Step 3: Set up a reader to receive messages from the client
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			/*
			 * socket.getInputStream() receives raw byte stream from the client.
			 * InputStreamReader converts bytes to characters.
			 * BufferedReader helps read data line by line (efficiently).
			 */

			// Step 4: Set up a writer to send messages to the client
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			/*
			 * socket.getOutputStream() is used to send data to the client.
			 * PrintWriter helps write text lines easily.
			 * 'true' enables auto-flushing of the output buffer after every println().
			 */

			String received;

			// Step 5: Continuously read messages from the client until the connection is
			// closed
			while ((received = input.readLine()) != null) {
				/*
				 * input.readLine() waits for a line of text from the client.
				 * If the client closes the connection, this will return null and exit the loop.
				 */

				// Display message received from the client
				System.out.println("Client Message: " + received);

				// Send acknowledgment/response back to client
				output.println("Server Received: " + received);
				/*
				 * You can modify this line to implement logic or response based on the message.
				 */
			}

		} catch (IOException e) {
			// Handles any exceptions such as port already in use, client crash, etc.
			e.printStackTrace();
		}
	}
}
