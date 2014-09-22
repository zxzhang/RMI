package stub;

import ror.RemoteObjectRef;
import testzip.ZipCodeList;
import testzip.ZipCodeServer;

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
