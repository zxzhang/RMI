package util;

/**
 * @author Sanshuan Hung, Zhengxiong Zhang
 *
 *         The utilities class
 */
public class Util {

  // message type
  public enum MessageType {
    Client2Server, Server2Client, RIGHT, WRONG
  }

  /*
   * public enum RegistryMessageType { Rebind, Lookup }
   */

  // message string
  public static final String CANTFINDOBJ = "Can't find the object.";

  public static final String WHOAREYOU = "who are you?";

  public static final String REGISTRY = "I am a simple registry.";

  public static final String LOOKUP = "lookup";

  public static final String FOUND = "found";

  public static final String NOTFOUND = "it is not found!.";

  public static final String REBIND = "rebind";

  public static final String UNBIND = "unbind";

  public static final String REBINDACK = "rebind OK";

  public static final String UNBINDACK = "unbind OK";

  public static final int p = 33;

  public static final int hash_table_size = 1000;

  /*
   * public static void main(String[] args) { System.out.println(MessageType.Client2Server);
   * System.out.println(MessageType.Server2Client); System.out.println(MessageType.RIGHT);
   * System.out.println(MessageType.WRONG); }
   */

}
