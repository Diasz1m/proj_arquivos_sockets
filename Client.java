import java.io.*;
import java.net.*;

public class Client {
	public void stream() throws Exception {
		Socket socket = new Socket("localhost", 3009);

		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		out.write(200);
		out.writeUTF("Ola");

		int k = in.readInt();
		String s = in.readUTF();
		System.out.println("RECEBIDO " + k);
		System.out.println("RECEBIDO " + s);

		in.close();
		out.close();
		socket.close();
	}

	public static void main (String[] args) throws Exception {
		Client client = new Client();
		client.stream();
	}	
}

