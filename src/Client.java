import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

  private Socket socket;
  private OutputStream outputStream;

  public static void main(String[] args) throws IOException {
    Client client = new Client();
    client.initClient();
    client.sendMessage();
  }

  public void initClient() throws IOException {
    socket = new Socket("127.0.0.1", 12345);
  }

  public void sendMessage() throws IOException {
    outputStream = socket.getOutputStream();
    outputStream.write("client-server demo".getBytes());
    outputStream.flush();
    outputStream.close();
    socket.close();
  }

}
