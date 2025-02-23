package arquivos;

import java.io.*;
import java.net.*;

public class ClienteArquivo {
    public static void main(String[] args) {
        String enderecoServidor = "127.0.0.1";
        int porta = 12345;
        String caminhoArquivo = "arquivo.txt"; // Arquivo a ser enviado

        try (Socket socket = new Socket(enderecoServidor, porta)) {
            System.out.println("Conectado ao servidor. Enviando arquivo...");

            // Streams de saída para enviar o arquivo
            OutputStream out = socket.getOutputStream();
            FileInputStream fileIn = new FileInputStream(caminhoArquivo);

            // Buffer de leitura
            byte[] buffer = new byte[4096];
            int bytesLidos;

            while ((bytesLidos = fileIn.read(buffer)) != -1) {
                out.write(buffer, 0, bytesLidos);
            }

            System.out.println("Arquivo enviado com sucesso!");

            // Fecha streams e socket
            fileIn.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
