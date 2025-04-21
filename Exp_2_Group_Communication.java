import java.io.*; // For handling input and output streams
import java.net.*; // For networking classes like MulticastSocket, InetAddress, etc.
import java.util.*; // For Scanner class to take user input

public class Exp_2_Group_Communication {

    // Multicast group IP address (must be in the range 224.0.0.0 to
    // 239.255.255.255)
    private static final String MULTICAST_GROUP = "230.0.0.0";

    // Port number where all clients will send and receive messages
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try {
            // Get the InetAddress object for the multicast group
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP);

            // Create a MulticastSocket bound to the specified port
            // This socket is used for both sending and receiving messages
            MulticastSocket socket = new MulticastSocket(PORT);

            // Join the multicast group to receive messages sent to this group address
            socket.joinGroup(group);

            // Start a new thread that continuously listens for messages from the group
            Thread receiveThread = new Thread(() -> {
                try {
                    // Create a buffer to store incoming messages (maximum 1024 bytes)
                    byte[] buffer = new byte[1024];

                    // Infinite loop to keep receiving messages
                    while (true) {
                        // Create an empty DatagramPacket to receive incoming data
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                        // Receive data from the multicast group
                        socket.receive(packet);

                        // Convert received bytes into a readable string message
                        String message = new String(packet.getData(), 0, packet.getLength());

                        // Display the received message on the console
                        System.out.println("\n[Received]: " + message);

                        // Reprint the input prompt after receiving a message
                        System.out.print("> ");
                    }
                } catch (IOException e) {
                    // Handle any error while receiving messages
                    e.printStackTrace();
                }
            });

            // Start the receiver thread
            receiveThread.start();

            // Create a Scanner object to read input from the user
            Scanner sc = new Scanner(System.in);

            // Prompt the user to enter their name
            System.out.print("Enter your name: ");
            String name = sc.nextLine(); // Read the user's name

            // Inform the user they can start sending messages
            System.out.println("You can now start chatting! Type your message:");

            // Infinite loop xd3cqfor reading and sending messages
            while (true) {
                System.out.print("> "); // Prompt user to enter message

                String message = sc.nextLine(); // Read the message from the user

                // Combine the user's name and the message
                String fullMessage = name + ": " + message;

                // Convert the message to bytes so it can be sent
                byte[] buffer = fullMessage.getBytes();

                // Create a datagram packet to send to the multicast group
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);

                // Send the packet to the multicast group
                socket.send(packet);
            }

        } catch (IOException e) {
            // Handle exceptions during setup or sending
            e.printStackTrace();
        }
    }
}
