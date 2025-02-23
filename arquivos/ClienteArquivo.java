package arquivos;

import java.io.*;
import java.net.*;

public class ClienteArquivo {
    public static void main(String[] args) {
        String enderecoServidor = "192.168.1.100"; // IP do servidor Windows
        int porta = 12345;
        String caminhoArquivo = "arquivo.txt";

        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            System.out.println("Erro: O arquivo não existe.");
            return;
        }

        try (Socket socket = new Socket(enderecoServidor, porta);
             OutputStream out = socket.getOutputStream();
             FileInputStream fileIn = new FileInputStream(arquivo);
             BufferedInputStream bufferedIn = new BufferedInputStream(fileIn)) {

            System.out.println("Conectado ao servidor. Enviando arquivo...");

            byte[] buffer = new byte[4096];
            int bytesLidos;

            while ((bytesLidos = bufferedIn.read(buffer)) != -1) {
                out.write(buffer, 0, bytesLidos);
            }

            out.flush(); // Garante que todos os dados são enviados
            System.out.println("Arquivo enviado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
