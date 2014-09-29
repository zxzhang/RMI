package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import ror.RemoteObjectRef;

public class RegistryServer {
  private static int port = 11123;

  private static ConcurrentHashMap<String, RemoteObjectRef> serviceMap = new ConcurrentHashMap<String, RemoteObjectRef>();

  private static ServerSocket serverSoc = null;

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Please input the right port number...");
    }

    port = Integer.parseInt(args[0]);

    try {
      serverSoc = new ServerSocket(port);
      String host = InetAddress.getLocalHost().getHostAddress();

      System.out.printf("The registry host: %s\n", host);

      while (true) {
        Socket clientSoc = serverSoc.accept();
        Thread registryServer = new Thread(new RegistryThread(clientSoc, serviceMap));
        registryServer.start();
      }

    } catch (IOException e) {
      System.out.println("Registry server IO exception...");
      System.out.println(e.getMessage());
    }

  }
}
