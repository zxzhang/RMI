package util;

public class Util {

  public enum MessageType {
    Client2Server, Server2Client, RIGHT, WRONG
  }
  
  public enum RegistryMessageType{
    Rebind, Lookup
  }
  
  public static final String CANTFINDOBJ = "Can't find the object.";
  public static final String WHOAREYOU = "who are you?";
  public static final String REGISTRY = "I am a simple registry.";
  public static final String LOOKUP = "lookup";
  public static final String FOUND = "found";
  public static final String NOTFOUND = "it is not found!.";
  public static final String REBIND = "rebind";
  public static final String REBINDACK = "OK.";
  
  /*
   * public static void main(String[] args) { System.out.println(MessageType.Client2Server);
   * System.out.println(MessageType.Server2Client); System.out.println(MessageType.RIGHT);
   * System.out.println(MessageType.WRONG); }
   */

}
