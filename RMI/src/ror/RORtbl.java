package ror;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import registry.SimpleRegistry;

// This is simple. ROR needs a new object key for each remote object (or its skeleton). 
// This can be done easily, for example by using a counter.
// We also assume a remote object implements only one interface, which is a remote interface.

/**
 * @author Sanshuan Hung, Zhengxiong Zhang
 * 
 *         The RORtbl has RemoteObjectReference table, service name and
 *         registry. You can add object into the table and find objects in the
 *         table.
 */
public class RORtbl {
	// I omit all instance variables. you can use hash table, for example.
	// The table would have a key by ROR.
	private AtomicLong Obj_Key;

	private Hashtable<RemoteObjectRef, Object> rorTable = null;

	private String serviceName = null;

	private SimpleRegistry registry = null;

	// make a new table.
	/**
	 * @param registryHost
	 *            The host name
	 * @param registryPort
	 *            The port number
	 * @param serviceName
	 *            The service name
	 */
	public RORtbl(String registryHost, int registryPort, String serviceName) {
		this.Obj_Key = new AtomicLong(0);
		this.rorTable = new Hashtable<RemoteObjectRef, Object>();
		this.serviceName = serviceName;
		this.registry = new SimpleRegistry(registryHost, registryPort);
	}

	// add a remote object to the table.
	// Given an object, you can get its class, hence its remote interface.
	// Using it, you can construct a ROR.
	// The host and port are not used unless it is exported outside.
	// In any way, it is better to have it for uniformity.
	/**
	 * @param host
	 *            The host name
	 * @param port
	 *            The port number
	 * @param o
	 *            The object
	 */
	public void addObj(String host, int port, Object o) {
		if (o == null) {
			return;
		}

		Class<?>[] interfaces = o.getClass().getInterfaces();

		if (interfaces.length != 1) {
			System.out.println("Interface error!");
			return;
		}

		RemoteObjectRef ror = new RemoteObjectRef(host, port,
				this.generateObjKey(), interfaces[0].getName());
		rorTable.put(ror, o);

		// rebind the serivce to the registry server
		try {
			this.registry.rebind(this.serviceName, ror);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// given ror, find the corresponding object.
	/**
	 * @param ror
	 *            The remote object reference
	 * @return object
	 */
	public Object findObj(RemoteObjectRef ror) {
		// if you use a hash table this is easy.
		return rorTable.get(ror);
	}

	/**
	 * @return generated object key number
	 */
	private int generateObjKey() {
		return (int) Obj_Key.incrementAndGet();
	}

	/**
	 * @return service name
	 */
	public String getServiceName() {
		return this.serviceName;
	}

	/**
	 * @return simple registry
	 */
	public SimpleRegistry getRegistry() {
		return this.registry;
	}
}
