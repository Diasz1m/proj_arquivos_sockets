import java.io.*;
import java.net.*;

public class socket {
	public void stream() throws Exception {
		ServerSocket serverSocket = new ServerSocket(3009);		
		System.out.println("Servidor rodando");
		Socket socket = serverSocket.accept();
		
		System.out.println("CONexxao: " + socket.getInetAddress().getHostName());
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		int k = in.readInt();
		String s = in.readUTF();
		System.out.println("RECEBIDO " + k);
		System.out.println("RECEBIDO " + s);

		out.write(100);
		out.writeUTF("Ola");

		in.close();
		out.close();
		socket.close();
		serverSocket.close();
	}

	public static void main (String[] args) throws Exception {
		socket socket1 = new socket();
		socket1.stream();
	}	
}

