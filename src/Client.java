import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  public static void main(String[] args) {
    String ip = (String) JOptionPane.showInputDialog(null,"IP?","Connect to..",JOptionPane.QUESTION_MESSAGE);
    int port = Integer.parseInt(JOptionPane.showInputDialog(null,"Port?","Connect to..",JOptionPane.QUESTION_MESSAGE));       ;
    Socket socket = null;

    try {
      socket = new Socket(ip,port);
    } catch (IOException e) {
      System.out.println("Client failed to connect");
      System.exit(0);
    }

    // GO
    try {
      PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
      ListenerThread in = new ListenerThread(new BufferedReader(new InputStreamReader(socket.getInputStream())));
      Thread listener = new Thread(in);
      listener.start();
      Scanner tgb = new Scanner(System.in);
      boolean run = true;
      while (run) {
        String msg = tgb.nextLine();
        if (msg.equals("quit")) {
          run = false;
          out.println(msg);
        } else {
          out.println("Client: " + msg);
        }
      }

      out.close();
      socket.close();
      System.out.println("Done!");
    } catch (Exception e) {
      System.out.println("Client failed to communicate");
    }
  }
}