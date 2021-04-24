import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    int port = 3124;
    InetAddress host;

    Model model = new Model();

    public Server() {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            ServerSocket ss = new ServerSocket(port, 0, host);
            System.out.println("Server started!");

            int count = 0;
            while (true) {
                Socket cs = ss.accept();
                System.out.println("Client connected!");

                count++;

                WCS wcs = new WCS(cs, model);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
