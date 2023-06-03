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

  private static final int MES_LEN_IN_POWER_OF_TWO = 16;

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
      byte[] lengthBytes =  bufferedInputStream.readNBytes(MES_LEN_IN_POWER_OF_TWO);
      int mesLength = 0;
      int modulo = 1;
      for(byte b : lengthBytes) {
        if(b == 1) {
          mesLength = mesLength + modulo;
        }
        modulo *= 2;
      }
      clientMessage = new String(bufferedInputStream.readNBytes(mesLength), StandardCharsets.UTF_8);
      System.out.println(clientMessage);
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
