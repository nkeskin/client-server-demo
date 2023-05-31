import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

  private Socket socket;
  private BufferedOutputStream bufferedOutputStream;
  private BufferedInputStream bufferedInputStream;
  private final Scanner scanner;
  private final String host;
  private final int port;

  public Client(String host, int port) throws IOException {
    this.host = host;
    this.port = port;
    initClient();
    scanner = new Scanner(System.in);
    System.out.println("Client initialized.");
  }

  private void initClient() throws IOException {
    socket = new Socket(host, port);
  }

  public void startConversation() throws IOException {
    System.out.println("Enter a message to send:");
    String message;
    bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
    bufferedInputStream = new BufferedInputStream(socket.getInputStream());
    do {
      message = scanner.nextLine();
      bufferedOutputStream.write(message.getBytes());
      bufferedOutputStream.flush();
    } while (!"end conversation".equalsIgnoreCase(message));
  }

  public void closeResources() throws IOException {
    System.out.println("Closing client resources:");
    if (socket != null) {
      System.out.println("Closing socket");
      socket.close();
    }
    if (scanner != null) {
      System.out.println("Closing scanner");
      scanner.close();
    }
    if (bufferedOutputStream != null) {
      System.out.println("Closing bufferedOutputStream");
      bufferedOutputStream.close();
    }
    if (bufferedInputStream != null) {
      System.out.println("Closing bufferedInputStream");
      bufferedInputStream.close();
    }
  }

}
