package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import ror.RORtbl;
import util.Message;

public class ServerThread implements Runnable {

  private Socket clientSoc = null;

  private RORtbl tbl = null;

  public ServerThread(Socket clientSoc, RORtbl tbl) {
    this.clientSoc = clientSoc;
    this.tbl = tbl;
  }

  @Override
  public void run() {

    try {

      ObjectInputStream in = new ObjectInputStream(clientSoc.getInputStream());

      Message message = (Message) in.readObject();

      if (message != null) {
        processMessage(message);
      }

    } catch (IOException e) {
      System.out.println("Can't open the socket!");
      System.out.println(e.getMessage());
    } catch (ClassNotFoundException e) {
      System.out.println("Can't find the message class!");
      System.out.println(e.getMessage());
    }

  }

  private void processMessage(Message message) {
  }

}
