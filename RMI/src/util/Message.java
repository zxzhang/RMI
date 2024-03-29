package util;

import java.io.Serializable;

import ror.RemoteObjectRef;
import util.Util.MessageType;

/**
 * @author San-Chuan Hung, Zhengxiong Zhang
 * 
 *         The Message class sending between client and RMIserver.
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	MessageType type = null;

	private String method;

	private Object[] args;

	private String[] argsType;

	private String returnType;

	private Object object;

	private String mess;
	
	private Throwable exception;

	private RemoteObjectRef ror;

	/**
	 * @param type
	 *            Message type
	 * @param ror
	 *            RemoteObjectReference type
	 * @param method
	 *            Method
	 * @param args
	 *            Arguments
	 * @param argsType
	 *            Arguments type
	 * @param returnType
	 *            Return type
	 */
	public Message(Util.MessageType type, RemoteObjectRef ror, String method,
			Object[] args, String[] argsType, String returnType) {
		this.type = type;
		this.ror = ror;
		this.method = method;
		this.args = args;
		this.argsType = argsType;
		this.returnType = returnType;
	}

	/**
	 * @param type
	 *            Message type
	 * @param object
	 *            The object
	 */
	public Message(Util.MessageType type, Serializable object) {
		this.type = type;
		this.object = object;
	}

	/**
	 * @param type
	 *            Message type
	 * @param mess
	 *            Message
	 */
	public Message(Util.MessageType type, String mess) {
		this.type = type;
		this.mess = mess;
	}

	/**
	 * @param object
	 *            The object
	 */
	public void setObject(Serializable object) {
		this.object = object;
	}

	public void setException(Throwable exception) {
    this.exception = exception;
  }
	
	public Throwable getException() {
	  return this.exception;
	}
	
	public RemoteObjectRef getRor() {
		return this.ror;
	}

	public MessageType getType() {
		return this.type;
	}

	public String getMethod() {
		return this.method;
	}

	public Object[] getArgs() {
		return this.args;
	}

	public String[] getArgsType() {
		return this.argsType;
	}

	public String getReturnType() {
		return this.returnType;
	}

	public Object getObject() {
		return this.object;
	}

	public String getMess() {
		return this.mess;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: ").append(this.type).append("\t").append("Method: ")
				.append(this.method).append("\t");

		if (args != null && returnType != null && ror != null) {
			sb.append("Args: ");
			for (int i = 0; i < this.args.length; i++) {
				sb.append(argsType[i]).append(" ").append(args[i]).append(" ");
			}
			sb.append("\t");
			sb.append("ReturnType: ").append(this.returnType).append("\t");
		}

		sb.append("Ror: ").append(this.ror.toString());

		return sb.toString();
	}
}
