package client.testzip;

import ror.RemoteObjectRef;

public class Stub {
  protected RemoteObjectRef ror = null;

  public RemoteObjectRef getRor(){
    return this.ror;
  }
  
  public void setRor(RemoteObjectRef ror){
    this.ror = ror;
  }
}
