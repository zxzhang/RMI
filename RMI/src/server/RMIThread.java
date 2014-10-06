package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.Socket;

import ror.RORtbl;
import ror.RemoteObjectRef;
import util.Message;
import util.Util;

/**
 * @author Sanshuan Hung, Zhengxiong Zhang
 *
 *         The RMI thread handles the RMI request. Invoke the certain method the
 *         return the value.
 */
public class RMIThread implements Runnable {

	private Socket clientSoc = null;

	private RORtbl tbl = null;

	/**
	 * @param clientSoc
	 *            The client socket
	 * @param tbl
	 *            The RORtbl
	 */
	public RMIThread(Socket clientSoc, RORtbl tbl) {
		this.clientSoc = clientSoc;
		this.tbl = tbl;
	}

	@Override
	public void run() {

		try {
			ObjectInputStream in = new ObjectInputStream(
					clientSoc.getInputStream());
			Message message = (Message) in.readObject();

			if (message != null) {
				processMessage(message);
			}
		} catch (IOException e) {
			System.out.println("Can't open the socket!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Can't find the message class!");
			e.printStackTrace();
		}

	}

	/**
	 * @param message
	 *            The input message
	 */
	private void processMessage(Message message) {
		if (message.getType() != Util.MessageType.Client2Server) {
			System.out.println("Wrong type of message...");
			return;
		}

		// System.out.println(message.toString());

		RemoteObjectRef ror = message.getRor();
		Object object = this.tbl.findObj(ror);

		if (object == null) {
			processNullObject();
			return;
		}

		processObject(object, message);
	}

	private void processNullObject() {
		System.out.println("Can't find the object, what is wrong?");
		Message wrongMessage = new Message(Util.MessageType.WRONG,
				Util.CANTFINDOBJ);
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					clientSoc.getOutputStream());
			out.writeObject(wrongMessage);
			out.close();
		} catch (IOException e) {
			System.out.println("IO exception... Can't send out the message.");
			System.out.println(e.getMessage());
		}

		try {
			clientSoc.close();
		} catch (IOException e) {
			System.out
					.println("IO exception... Can't close the client socket.");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @param object
	 *            The object in RORtbl
	 * @param message
	 *            The input message
	 */
	private void processObject(Object object, Message message) {
		Message returnMessage = new Message(Util.MessageType.Server2Client,
				(Serializable) null);

		System.out.println(message);

		String methodName = message.getMethod();
		Object[] args = message.getArgs();
		String[] argsType = message.getArgsType();
		String returnType = message.getReturnType();

		String exceptionMessage = null;

		Method[] methods = object.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (!checkMethodName(methodName, method)) {
				continue;
			}
			if (!checkReturnType(returnType, method)) {
				continue;
			}
			if (!checkArgsType(argsType, method)) {
				continue;
			}

			method.setAccessible(true);

			try {
				Object newObj = method.invoke(object, args);

				/*
				 * if (returnType != null) {
				 * returnMessage.setObject((Serializable) newObj); }
				 */

				if (!returnType.equals("void")) {
					returnMessage.setObject((Serializable) newObj);
				}

			} catch (IllegalAccessException e) {
				exceptionMessage = e.getMessage();
				System.out.println("IllegalAccessException!!!");
				System.out.println(exceptionMessage);
			} catch (IllegalArgumentException e) {
				exceptionMessage = e.getMessage();
				System.out.println("IllegalArgumentException!!!");
				System.out.println(exceptionMessage);
			} catch (InvocationTargetException e) {
				exceptionMessage = e.getMessage();
				System.out.println("InvocationTargetException!!!");
				System.out.println(exceptionMessage);
			}

			break;
		}

		if (exceptionMessage != null) {
			returnMessage = new Message(Util.MessageType.WRONG,
					exceptionMessage);
		}

		try {
			ObjectOutputStream out = new ObjectOutputStream(
					clientSoc.getOutputStream());
			out.writeObject(returnMessage);
			out.close();
		} catch (IOException e) {
			System.out.println("IOException!!!");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @param methodName
	 *            The method name
	 * @param method
	 *            The method
	 * @return whether the method is right or not
	 */
	private boolean checkMethodName(String methodName, Method method) {
		String name = method.getName();
		if (name.equals(methodName)) {
			return true;
		}

		return false;
	}

	/**
	 * @param returnType
	 *            The return type
	 * @param method
	 *            The method
	 * @return whether the return type is right or not
	 */
	private boolean checkReturnType(String returnType, Method method) {
		if (returnType == null
				&& method.getGenericReturnType().toString() == null) {
			return true;
		}

		if (returnType != null
				&& returnType.equals(method.getGenericReturnType().toString())) {
			return true;
		}

		return false;
	}

	/**
	 * @param argsType
	 *            The arguments type
	 * @param method
	 *            The method
	 * @return whether the arguments type are right or not
	 */
	private boolean checkArgsType(String[] argsType, Method method) {
		Type[] aTypes = method.getGenericParameterTypes();

		if (aTypes.length != argsType.length) {
			return false;
		}

		for (int i = 0; i < aTypes.length; i++) {
			if (!(argsType[i].equals(aTypes[i].toString()))) {
				return false;
			}
		}

		return true;
	}

}
