package server;

/* This does not offer the code of the whole communication module 
 (CM) for RMI: but it gives some hints about how you can make 
 it. I call it simply "yourRMI". 

 For example, it  shows how you can get the host name etc.,
 (well you can hardwire it if you like, I should say),
 or how you can make a class out of classname as a string.

 This just shows one design option. Other options are
 possible. We assume there is a unique skeleton for each
 remote object class (not object) which is called by CM 
 by static methods for unmarshalling etc. We can do without
 it, in which case CM does marshalling/unmarhshalling.
 Which is simpler, I cannot say, since both have their
 own simpleness and complexity.
 */

import java.io.IOException;
import java.net.*;

import ror.RORtbl;

/**
 * @author Sanshuan Hung, Zhengxiong Zhang
 * 
 *         Initial the class, build the RORtbl and launch the server.
 */
public class RMIServer {
	static String host = "localhost";

	static int port = 12323;

	private volatile static boolean running = false;

	private static ServerSocket serverSoc = null;

	private static Socket clientSoc;

	// It will use a hash table, which contains ROR together with
	// reference to the remote object.
	// As you can see, the exception handling is not done at all.
	public static void main(String args[]) {
		if (args.length != 4) {
			System.out
					.println("Usage: RMIServer <InitialClassName> <registryHost> <registryPort> <serviceName>");
		}

		String InitialClassName = args[0];
		String registryHost = args[1];
		int registryPort = Integer.parseInt(args[2]);
		String serviceName = args[3];

		port = 12323;

		// it should have its own port. assume you hardwire it.
		try {
			host = InetAddress.getLocalHost().getHostName();
			// host = InetAddress.getLocalHost().getHostAddress();
			System.out.println("Use the hostname: " + host);
		} catch (UnknownHostException e) {
			System.out.println("Can't get the hostname: " + host);
			System.out.println(e.getMessage());
		}

		// it now have two classes from MainClassName:
		// (1) the class itself (say ZipCpdeServerImpl) and
		// (2) its skeleton.
		Class<?> initialclass = null;
		try {
			initialclass = Class.forName(InitialClassName);
			System.out.println("Initial the class: " + InitialClassName);
		} catch (ClassNotFoundException e) {
			System.out.println("Can't initial the class: " + InitialClassName);
			System.out.println(e.getMessage());
		}

		// you should also create a remote object table here.
		// it is a table of a ROR and a skeleton.
		// as a hint, I give such a table's interface as RORtbl.java.
		RORtbl tbl = new RORtbl(registryHost, registryPort, serviceName);

		// after that, you create one remote object of initialclass.
		Object o = null;
		try {
			o = initialclass.newInstance();
		} catch (InstantiationException e) {
			System.out.println("Can't instantiate the class: "
					+ InitialClassName);
			System.out.println(e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't access the class: " + InitialClassName);
			System.out.println(e.getMessage());
		}

		// then register it into the table.
		tbl.addObj(host, port, o);

		running = true;

		try {
			serverSoc = new ServerSocket(port);
			System.out.println("Open the port: " + port);
		} catch (IOException e) {
			System.out.println("Can't open the port: " + port);
			System.out.println(e.getMessage());
		}

		// Now we go into a loop.
		// Look at rmiregistry.java for a simple server programming.
		// The code is far from optimal but in any way you can get basics.
		// Actually you should use multiple threads, or this easily
		// deadlocks. But for your implementation I do not ask it.
		// For design, consider well.
		while (running) {
			// (1) receives an invocation request.
			// (2) creates a socket and input/output streams.
			// (3) gets the invocation, in martiallled form.
			// (4) gets the real object reference from tbl.
			// (5) Either:
			// -- using the interface name, asks the skeleton,
			// together with the object reference, to unmartial
			// and invoke the real object.
			// -- or do unmarshalling directly and involkes that
			// object directly.
			// (6) receives the return value, which (if not marshalled
			// you should marshal it here) and send it out to the
			// the source of the invoker.
			// (7) closes the socket.

			try {
				clientSoc = serverSoc.accept();
			} catch (IOException e) {
				System.out.println("Can't open the client socket: "
						+ clientSoc.toString());
				System.out.println(e.getMessage());
			}

			Thread server = new Thread(new Dispatcher(clientSoc, tbl));
			server.start();
		}

		try {
			serverSoc.close();
		} catch (IOException e) {
			System.out.println("Can't close the server socket!");
			System.out.println(e.getMessage());
		}
	}
}
