package arquivos;

import java.io.*;
import java.net.*;

public class ServidorArquivo {
    public static void main(String[] args) {
        int porta = 12345;
        String destinoArquivo = "recebido.txt";

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor esperando conexão...");

            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado!");

            try (InputStream in = socket.getInputStream();
                 FileOutputStream fileOut = new FileOutputStream(destinoArquivo);
                 BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut)) {

                byte[] buffer = new byte[4096];
                int bytesLidos;

                while ((bytesLidos = in.read(buffer)) != -1) {
                    bufferedOut.write(buffer, 0, bytesLidos);
                }

                bufferedOut.flush(); // Garante que todos os dados são escritos
            }

            System.out.println("Arquivo recebido e salvo como: " + destinoArquivo);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
