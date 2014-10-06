package client.testzip;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import util.Message;
import util.Util;

/**
 * @author Sanshuan Hung, Zhengxiong Zhang
 *
 */
public class ZipCodeServer_stub extends Stub implements ZipCodeServer {

	private Message connect(Message m) throws Exception {
		Socket serverSoc = new Socket(getRor().getIPAdresss(), getRor()
				.getPort());

		ObjectOutputStream out = new ObjectOutputStream(
				serverSoc.getOutputStream());
		out.writeObject(m);

		ObjectInputStream in = new ObjectInputStream(serverSoc.getInputStream());
		Message ret_message = (Message) in.readObject();

		serverSoc.close();

		return ret_message;
	}

	@Override
	public void initialise(ZipCodeList newlist) {
		Object[] args = new Object[] { (Object) newlist };
		String[] argsType = new String[] { ZipCodeList.class.toString() };
		String returnType = "void"; // "void";
		Message m = new Message(Util.MessageType.Client2Server, ror,
				"initialise", args, argsType, returnType);
		try {
			Message ret = connect(m);

			if (checkWrongMessage(ret)) {
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String find(String city) {
		Object[] args = new Object[] { (Object) city };
		String[] argsType = new String[] { String.class.toString() };
		String returnType = String.class.toString();
		Message m = new Message(Util.MessageType.Client2Server, ror, "find",
				args, argsType, returnType);

		try {
			Message ret = connect(m);

			if (checkWrongMessage(ret)) {
				return null;
			}

			return (String) ret.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ZipCodeList findAll() {
		Object[] args = new Object[] {};
		String[] argsType = new String[] {};
		String returnType = ZipCodeList.class.toString();
		Message m = new Message(Util.MessageType.Client2Server, ror, "findAll",
				args, argsType, returnType);
		try {
			Message ret = connect(m);

			if (checkWrongMessage(ret)) {
				return null;
			}

			return (ZipCodeList) ret.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void printAll() {
		Object[] args = new Object[] {};
		String[] argsType = new String[] {};
		String returnType = "void"; // "void";
		Message m = new Message(Util.MessageType.Client2Server, ror,
				"printAll", args, argsType, returnType);
		try {
			Message ret = connect(m);

			if (checkWrongMessage(ret)) {
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkWrongMessage(Message message) {
		if (message.getType() == Util.MessageType.WRONG) {
			System.out.println("Exception: ");
			System.out.println(message.getMess());
			return true;
		}

		return false;
	}

}
