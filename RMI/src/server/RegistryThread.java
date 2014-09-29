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

        out.println(Util.REGISTRY);

        in.close();
        out.close();
        return;

      }

      if (line.equals(Util.LOOKUP)) {

        String serviceName = in.readLine();

        if (serviceMap.contains(serviceName)) {
          out.println(Util.FOUND);

          RemoteObjectRef ror = serviceMap.get(serviceName);

          String ro_IPAdr = ror.getIPAdresss();
          int ro_PortNum = ror.getPort();
          int ro_ObjKey = ror.getObjectKey();
          String ro_InterfaceName = ror.getRemoteInterfaceName();

          out.println(ro_IPAdr);
          out.println(ro_PortNum);
          out.println(ro_ObjKey);
          out.println(ro_InterfaceName);

          System.out.println("Finish lookup...");

        } else {
          out.println(Util.NOTFOUND);
          System.out.println("Not found the lookup service...");
        }

        in.close();
        out.close();
        return;

      }

      if (line.equals(Util.REBIND)) {

        String serviceName = in.readLine();
        String ro_IPAdr = in.readLine();
        int ro_PortNum = Integer.parseInt(in.readLine());
        int ro_ObjKey = Integer.parseInt(in.readLine());
        String ro_InterfaceName = in.readLine();
        RemoteObjectRef ror = new RemoteObjectRef(ro_IPAdr, ro_PortNum, ro_ObjKey, ro_InterfaceName);

        serviceMap.put(serviceName, ror);
        out.println(Util.REBINDACK);

        System.out.println("Finish rebind...");

        in.close();
        out.close();
        return;

      }

    } catch (IOException e) {
      System.out.println("Registry thread problem...");
      System.out.println(e.getMessage());
    }
  }
}
