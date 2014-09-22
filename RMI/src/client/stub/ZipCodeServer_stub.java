package client.stub;

import client.testzip.ZipCodeList;
import client.testzip.ZipCodeServer;
import ror.RemoteObjectRef;

public class ZipCodeServer_stub implements ZipCodeServer {
  private RemoteObjectRef ror = null;

  @Override
  public void initialise(ZipCodeList newlist) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String find(String city) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ZipCodeList findAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void printAll() {
    // TODO Auto-generated method stub
    
  }
  
}
