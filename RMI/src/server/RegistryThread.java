package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;

import ror.RemoteObjectRef;
import util.Util;

/**
 * @author San-Chuan Hung, Zhengxiong Zhang
 *
 *         The registry thread handle one request: WHOAREYOU, LOOKUP or REBIND.
 */
public class RegistryThread implements Runnable {
  Socket clientSoc = null;

  Hashtable<String, RemoteObjectRef> serviceTable = null;

  /**
   * @param clientSoc
   *          Client socket
   * @param serviceTable
   *          Sevice hashtable
   */
  public RegistryThread(Socket clientSoc, Hashtable<String, RemoteObjectRef> serviceTable) {
    this.clientSoc = clientSoc;
    this.serviceTable = serviceTable;
  }

  @Override
  public void run() {

    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));
      PrintWriter out = new PrintWriter(clientSoc.getOutputStream(), true);
      String line = in.readLine();

      if (line.equals(Util.WHOAREYOU)) { // just ask who are you
        doWhoAreYou(out);
      } else if (line.equals(Util.LOOKUP)) { // CASE LOOKUP
        doLookUp(in, out);
      } else if (line.equals(Util.REBIND)) { // CASE REBIND
        doRebind(in, out);
      } else if (line.equals(Util.UNBIND)) { // CASE UNBIND
        doUnbind(in, out);
      }

      in.close();
      out.close();

    } catch (IOException e) {
      System.out.println("Registry thread problem...");
      System.out.println(e.getMessage());
    }
  }

  private void doWhoAreYou(PrintWriter out) {
    out.println(Util.REGISTRY);
  }

  private void doLookUp(BufferedReader in, PrintWriter out) throws IOException {
    String serviceName = in.readLine().trim();

    if (serviceTable.containsKey(serviceName)) {
      out.println(Util.FOUND);
      RemoteObjectRef ror = serviceTable.get(serviceName);

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
  }

  private void doRebind(BufferedReader in, PrintWriter out) throws IOException {
    String serviceName = in.readLine().trim();
    String ro_IPAdr = in.readLine().trim();
    int ro_PortNum = Integer.parseInt(in.readLine());
    int ro_ObjKey = Integer.parseInt(in.readLine());
    String ro_InterfaceName = in.readLine().trim();
    RemoteObjectRef ror = new RemoteObjectRef(ro_IPAdr, ro_PortNum, ro_ObjKey, ro_InterfaceName);

    if (!serviceTable.contains(serviceName)) {
      serviceTable.put(serviceName, ror);
    }

    out.println(Util.REBINDACK);

    System.out.println("Finish rebind...");
  }

  private void doUnbind(BufferedReader in, PrintWriter out) throws IOException {
    String serviceName = in.readLine().trim();
    serviceTable.remove(serviceName);
    out.println(Util.UNBINDACK);

    System.out.println("Finish unbind...");
  }

}
