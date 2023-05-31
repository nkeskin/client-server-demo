import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {

  private ServerSocket serverSocket;
  private Socket socket;
  private BufferedInputStream bufferedInputStream;
  private BufferedOutputStream bufferedOutputStream;
  private final int port;

  public Server(int port) throws IOException {
    this.port = port;
    initServer();
    System.out.println("Server initialized, listening for incoming messages.");
  }

  private void initServer() throws IOException {
    serverSocket = new ServerSocket(port);
  }

  public void listenConversation() throws IOException {
    socket = serverSocket.accept();
    bufferedInputStream = new BufferedInputStream(socket.getInputStream());
    bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
    String clientMessage = "initial";
    while (!"end conversation".equalsIgnoreCase(clientMessage)) {
      if (bufferedInputStream.available() > 0) {
        clientMessage = new String(bufferedInputStream.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println(clientMessage);
        String serverMessage = "hello";
        bufferedOutputStream.write(serverMessage.getBytes());
        bufferedOutputStream.flush();
      }
    }
  }

  public void closeResources() throws IOException {
    System.out.println("Closing server resources:");
    if (socket != null) {
      System.out.println("Closing socket");
      socket.close();
    }
    if (serverSocket != null) {
      System.out.println("Closing server socket");
      serverSocket.close();
    }
    if (bufferedInputStream != null) {
      System.out.println("Closing bufferedInputStream");
      bufferedInputStream.close();
    }
    if (bufferedOutputStream != null) {
      System.out.println("Closing bufferedOutputStream");
      bufferedOutputStream.close();
    }
  }

}
