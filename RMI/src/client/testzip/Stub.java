package client.testzip;

import ror.RemoteObjectRef;

/**
 * @author Sanshuan Hung, Zhengxiong Zhang
 * 
 *         The Stub class contains remote object reference and the methods to
 *         process ROR. The getRor() and setRor() methods.
 */
public abstract class Stub {
	protected RemoteObjectRef ror = null;

	/**
	 * @return RemoteObjectRef
	 */
	public RemoteObjectRef getRor() {
		return this.ror;
	}

	/**
	 * @param ror
	 *            Set the RemoteObjectRef to ror.
	 */
	public void setRor(RemoteObjectRef ror) {
		this.ror = ror;
	}
}
