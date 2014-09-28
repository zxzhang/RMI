package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import ror.RemoteObjectRef;
import util.Util;

public class RegistryThread implements Runnable {

  Socket clientSoc = null;

  ConcurrentHashMap<String, RemoteObjectRef> serviceMap = null;

  public RegistryThread(Socket clientSoc, ConcurrentHashMap<String, RemoteObjectRef> serviceMap) {
    this.clientSoc = clientSoc;
    this.serviceMap = serviceMap;
  }

  @Override
  public void run() {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));
      PrintWriter out = new PrintWriter(clientSoc.getOutputStream(), true);
      String line = in.readLine();
      
      if (line.equals(Util.WHOAREYOU)) {
        out.write(Util.REGISTRY);
      }
      
      //... lookup
      
    } catch (IOException e) {
      System.out.println("Registry thread problem...");
      System.out.println(e.getMessage());
    }
  }

}
