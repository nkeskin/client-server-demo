import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {

  private ServerSocket serverSocket;
  private Socket socket;
  private InputStream inputStream;

  public static void main(String[] args) throws IOException {
    Server server = new Server();
    server.initServer();
    server.listenMessage();
  }

  public void initServer() throws IOException {
    serverSocket = new ServerSocket(12345);
  }

  public void listenMessage() throws IOException {
    socket = serverSocket.accept();
    inputStream = socket.getInputStream();
    String message = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    System.out.println(message);
    socket.close();
    serverSocket.close();
    inputStream.close();
  }

}
