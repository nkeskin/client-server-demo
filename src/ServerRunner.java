import java.io.IOException;

public class ServerRunner {

  public static void main(String[] args) throws IOException {

    Server server = new Server(12345);

    Thread shutdownThread = new Thread(() -> {
      try {
        server.closeResources();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    });

    Runtime.getRuntime().addShutdownHook(shutdownThread);

    server.initServer();

    server.listenConversation();

  }
}
