package ror;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import registry.SimpleRegistry;

// This is simple. ROR needs a new object key for each remote object (or its skeleton). 
// This can be done easily, for example by using a counter.
// We also assume a remote object implements only one interface, which is a remote interface.

public class RORtbl {
  // I omit all instance variables. you can use hash table, for example.
  // The table would have a key by ROR.
  private AtomicLong Obj_Key;

  private Map<RemoteObjectRef, Object> rorTable = null;
  
  private String serviceName = null;
  
  private SimpleRegistry registry = null;

  // make a new table.
  public RORtbl(String registryHost, int registryPort, String serviceName) {
    this.Obj_Key = new AtomicLong(0);
    this.rorTable = new HashMap<RemoteObjectRef, Object>();
    this.serviceName = serviceName;
    this.registry = new SimpleRegistry(registryHost, registryPort);
  }

  // add a remote object to the table.
  // Given an object, you can get its class, hence its remote interface.
  // Using it, you can construct a ROR.
  // The host and port are not used unless it is exported outside.
  // In any way, it is better to have it for uniformity.
  public void addObj(String host, int port, Object o) {
    if (o == null) {
      return;
    }

    for (Class<?> c : o.getClass().getInterfaces()) {
      RemoteObjectRef ror = new RemoteObjectRef(host, port, this.generateObjKey(),
              c.getName());
      rorTable.put(ror, o);
    }
  }

  // given ror, find the corresponding object.
  public Object findObj(RemoteObjectRef ror) {
    // if you use a hash table this is easy.
    return rorTable.get(ror);
  }

  private int generateObjKey() {
    return (int) Obj_Key.incrementAndGet();
  }
  
  public String getServiceName() {
    return this.serviceName;
  }
  
  public SimpleRegistry getRegistry() {
    return this.registry;
  }
}
