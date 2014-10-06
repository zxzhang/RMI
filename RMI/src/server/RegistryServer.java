package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

import ror.RemoteObjectRef;

public class RegistryServer {
  private static int port = 11123;

  private static Hashtable<String, RemoteObjectRef> serviceTable = null;

  private static ServerSocket serverSoc = null;

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Please input the right port number...");
      return;
    }

    port = Integer.parseInt(args[0]);
    serviceTable = new Hashtable<String, RemoteObjectRef>();

    
    try {
      serverSoc = new ServerSocket(port);
      String host = InetAddress.getLocalHost().getHostAddress();

      System.out.printf("The registry host: %s\n", host);

      while (true) {
        Socket clientSoc = serverSoc.accept();
        Thread registryServer = new Thread(new RegistryThread(clientSoc, serviceTable));
        registryServer.start();
      }

    } catch (IOException e) {
      System.out.println("Registry server IO exception...");
      System.out.println(e.getMessage());
    }

  }
}
