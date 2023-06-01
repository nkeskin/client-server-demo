import java.io.IOException;

public class ClientRunner {

  public static void main(String[] args) throws IOException {

    Client client = new Client("127.0.0.1", 12345);

    Thread shutdownThread = new Thread(() -> {
      try {
        client.closeResources();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    });

    Runtime.getRuntime().addShutdownHook(shutdownThread);

    client.startConversation();

  }

}
