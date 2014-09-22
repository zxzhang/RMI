package ror;

public class RemoteObjectRef {

  public String IP_adr = "localhost";

  public int Port = 12323;

  public int Obj_Key = 0;

  public String Remote_Interface_Name = "";

  public RemoteObjectRef(String ip, int port, int obj_key, String riname) {
    IP_adr = ip;
    Port = port;
    Obj_Key = obj_key;
    Remote_Interface_Name = riname;
  }

  // this method is important, since it is a stub creator.
  //
  public Object localise() {
    // Implement this as you like: essentially you should
    // create a new stub object and returns it.
    // Assume the stub class has the name e.g.
    //
    // Remote_Interface_Name + "_stub".
    //
    // Then you can create a new stub as follows:
    //
    // Class c = Class.forName(Remote_Interface_Name + "_stub");
    // Object o = c.newinstance()
    //
    // For this to work, your stub should have a constructor without arguments.
    // You know what it does when it is called: it gives communication module
    // all what it got (use CM's static methods), including its method name,
    // arguments etc., in a marshalled form, and CM (yourRMI) sends it out to
    // another place.
    // Here let it return null.
    Object o = null;
    try {
      Class<?> c = Class.forName(Remote_Interface_Name + "_stub");
      o = c.newInstance();
    } catch (ClassNotFoundException e) {
      o = null;
      System.out.println(e.getMessage());
    } catch (InstantiationException e) {
      o = null;
      System.out.println(e.getMessage());
    } catch (IllegalAccessException e) {
      o = null;
      System.out.println(e.getMessage());
    }
    return o;
  }

  @Override
  public int hashCode() {
    final int p = 33;
    final int hash_table_size = 1000;
    int sum = 0;

    sum = sum + IP_adr == null ? 0 : IP_adr.hashCode();
    sum = sum % hash_table_size;

    sum = sum * p + Obj_Key;
    sum = sum % hash_table_size;

    sum = sum * p + Port;
    sum = sum % hash_table_size;

    sum = sum * p + Remote_Interface_Name == null ? 0 : Remote_Interface_Name.hashCode();
    sum = sum % hash_table_size;

    return sum;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }

    RemoteObjectRef remoteObj = (RemoteObjectRef) obj;

    if ((this.IP_adr == null && remoteObj.IP_adr != null)
            || (this.IP_adr != null && !this.IP_adr.equals(remoteObj.IP_adr))
            || (this.Obj_Key != remoteObj.Obj_Key)
            || (this.Port != remoteObj.Port)
            || (this.Remote_Interface_Name == null && remoteObj.Remote_Interface_Name != null)
            || (this.Remote_Interface_Name != null && !this.Remote_Interface_Name
                    .equals(remoteObj.Remote_Interface_Name))) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("IP_adr: ").append(this.IP_adr).append("\tPort: ").append(this.Port)
            .append("\tObj_Key: ").append(this.Obj_Key).append("\tRemote_Interface_Name: ")
            .append(this.Remote_Interface_Name).append("\n");

    return sb.toString();
  }
}
