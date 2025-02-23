package arquivos;

import java.io.*;
import java.net.*;

public class ClienteArquivo {
    public static void main(String[] args) {
        // aqui vai o ip do servidor
        String enderecoServidor = "192.168.1.100";
        int porta = 151515;
        String caminhoArquivo = "arquivo.txt";

        // faz yna verificação se o arquivo existe no cliente
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            System.out.println("Erro: O arquivo não existe.");
            return;
        }

        //aqui crio o socket e o outputstream para enviar o arquivo
        try (Socket socket = new Socket(enderecoServidor, porta);
             OutputStream out = socket.getOutputStream();
             FileInputStream fileIn = new FileInputStream(arquivo);
             BufferedInputStream bufferedIn = new BufferedInputStream(fileIn)) {
            System.out.println("Conectado ao servidor. Enviando arquivo...");

            // cria e le o arquivo
            byte[] buffer = new byte[4096];
            int bytesLidos;

            while ((bytesLidos = bufferedIn.read(buffer)) != -1) {
                out.write(buffer, 0, bytesLidos);
            }

            out.flush();
            System.out.println("Arquivo enviado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
