import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
  public static void main(String[] args){
    String username = null;
    int port = 8000;
    boolean run = true;
    ServerSocket serverSocket;
    Socket socket;
    System.out.println("Server started.");

    try {
      serverSocket = new ServerSocket(port);
      while (true) {
        System.out.println("Waiting for connections!");
        socket = serverSocket.accept();
        // Go
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ListenerThread in = new ListenerThread(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        Thread listener = new Thread(in);
        listener.start();
        System.out.println("Client connected!");
        Scanner tgb = new Scanner(System.in);
        //Protocol
        while (run) {
          if (username == null) {
            out.println("SERVER: Welcome! \n What's your name?");
            username = tgb.nextLine();
          }
          String msg = tgb.nextLine();
          if (msg.equals("quit")) {
            run = false;
          } else {
            out.println(msg);
          }
        }
        out.close();
        socket.close();
      }
    } catch (IOException e) {
      System.out.println("Server fail");
    }
  }
}