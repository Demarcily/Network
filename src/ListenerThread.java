import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;

public class ListenerThread implements Runnable{
  private BufferedReader in;

  public ListenerThread(BufferedReader in) {
    this.in = in;
  }

  @Override
  public void run() {
    String msg = null;
    while (true) {
      try {
        msg = in.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println(msg);
    }
  }

  public void stop()  {
    try {
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}