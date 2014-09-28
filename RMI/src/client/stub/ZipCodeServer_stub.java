package client.stub;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.testzip.ZipCodeList;
import client.testzip.ZipCodeServer;
import ror.RemoteObjectRef;
import util.Message;
import util.Util;

public class ZipCodeServer_stub implements ZipCodeServer {
  private RemoteObjectRef ror = null;

  private Message connect(Message m) throws Exception{
    Socket serverSoc = new Socket(ror.IP_adr, ror.Port);
	  
	  ObjectOutputStream out = new ObjectOutputStream(serverSoc.getOutputStream());
	  out.writeObject(m);
	  
	  ObjectInputStream in = new ObjectInputStream(serverSoc.getInputStream());
    Message ret_message = (Message) in.readObject();
      
    serverSoc.close();
   
    return ret_message;
  }
    
  @Override
  public void initialise(ZipCodeList newlist) {
    // TODO Auto-generated method stub
    Object[] args = new Object[]{(Object) newlist};
    String[] argsType = new String[]{"ZipCodeList"};
    String returnType = "void";
    Message m = new Message(
                  Util.MessageType.Client2Server, 
                  ror, 
                  "initializae", 
                  args,
                  argsType, 
                  returnType);
    try{
      Message ret = connect(m);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public String find(String city) {
    // TODO Auto-generated method stub
    Object[] args = new Object[]{(Object) city};
    String[] argsType = new String[]{"String"};
    String returnType = "String";
    Message m = new Message(
                  Util.MessageType.Client2Server, 
                  ror, 
                  "find", 
                  args,
                  argsType, 
                  returnType);
    
    try{
      Message ret = connect(m);
      return (String) ret.getObject();
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public ZipCodeList findAll() {
    // TODO Auto-generated method stub
    Object[] args = new Object[]{};
    String[] argsType = new String[]{};
    String returnType = "ZipCodeList";
    Message m = new Message(
                  Util.MessageType.Client2Server, 
                  ror, 
                  "findAll", 
                  args,
                  argsType, 
                  returnType);
    try{
      Message ret = connect(m);
      return (ZipCodeList) ret.getObject();
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void printAll() {
    // TODO Auto-generated method stub
    Object[] args = new Object[]{};
    String[] argsType = new String[]{};
    String returnType = "void";
    Message m = new Message(
                  Util.MessageType.Client2Server, 
                  ror, 
                  "printAll", 
                  args,
                  argsType, 
                  returnType);
    try{
      Message ret = connect(m);
    }catch(Exception e){
      e.printStackTrace();
    }  
  }
  
}
