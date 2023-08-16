package main.java;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

  private Socket socket;
  private BufferedOutputStream bufferedOutputStream;
  private BufferedInputStream bufferedInputStream;
  private final Scanner scanner;
  private final String host;
  private final int port;

  private static final int MES_LEN_IN_POWER_OF_TWO = 16; //16 means 2^16 Byte

  public Client(String host, int port) throws IOException {
    this.host = host;
    this.port = port;
    initClient();
    scanner = new Scanner(System.in);
    System.out.println("main.java.Client initialized.");
  }

  private void initClient() throws IOException {
    socket = new Socket(host, port);
    bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
    bufferedInputStream = new BufferedInputStream(socket.getInputStream());
  }

  public void startConversation() throws IOException {
    System.out.println("Enter a message to send:");
    String message;
    do {
      message = scanner.nextLine();
      byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
      bufferedOutputStream.write(getLengthBytes(messageBytes.length));
      bufferedOutputStream.write(messageBytes);
      bufferedOutputStream.flush();
    } while (!"end conversation".equalsIgnoreCase(message));
  }

  private byte[] getLengthBytes(int messageLength) {
    int modulo = 2;
    byte[] lengthBytes = new byte[16];
    for (int i = 0; i < MES_LEN_IN_POWER_OF_TWO; i++) {
      if (messageLength % modulo == 1) {
        lengthBytes[i] = 1;
      } else {
        lengthBytes[i] = 0;
      }
      messageLength = messageLength / 2;
    }
    return lengthBytes;
  }

  public void closeResources() throws IOException {
    System.out.println("Closing client resources:");
    if (socket != null) {
      System.out.println("Closing socket");
      socket.close();
    }
    if (bufferedOutputStream != null) {
      System.out.println("Closing bufferedOutputStream");
      bufferedOutputStream.close();
    }
    if (bufferedInputStream != null) {
      System.out.println("Closing bufferedInputStream");
      bufferedInputStream.close();
    }
    if (scanner != null) {
      System.out.println("Closing scanner");
      scanner.close();
    }
  }

}
