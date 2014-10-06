package client.testzip;

import ror.RemoteObjectRef;

/**
 * @author Sanshuan Hung, Zhengxiong Zhang
 *
 */
public class Stub {
	protected RemoteObjectRef ror = null;

	/**
	 * @return
	 */
	public RemoteObjectRef getRor() {
		return this.ror;
	}

	/**
	 * @param ror
	 */
	public void setRor(RemoteObjectRef ror) {
		this.ror = ror;
	}
}
