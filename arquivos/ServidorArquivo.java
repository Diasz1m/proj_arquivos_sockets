package arquivos;

import java.io.*;
import java.net.*;

public class ServidorArquivo {
    public static void main(String[] args) {
        //cria a porta para o cliente ouvir e o arquivo que vai ser salvo
        int porta = 151515;
        String destinoArquivo = "recebido.txt";

        //cria o socket e o inputstream para receber o arquivo
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

                bufferedOut.flush();
            }

            System.out.println("Arquivo recebido e salvo em: " + destinoArquivo);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
