package arquivos;

import java.io.*;
import java.net.*;

public class ServidorArquivo {
    public static void main(String[] args) {
        int porta = 12345; // Porta de escuta
        String destinoArquivo = "recebido.txt"; // Nome do arquivo salvo

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor esperando conexão...");

            // Aguarda um cliente conectar
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado!");

            // Streams de entrada para receber o arquivo
            InputStream in = socket.getInputStream();
            FileOutputStream fileOut = new FileOutputStream(destinoArquivo);

            // Buffer para leitura
            byte[] buffer = new byte[4096];
            int bytesLidos;

            while ((bytesLidos = in.read(buffer)) != -1) {
                fileOut.write(buffer, 0, bytesLidos);
            }

            System.out.println("Arquivo recebido e salvo como: " + destinoArquivo);

            // Fecha streams e socket
            fileOut.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
