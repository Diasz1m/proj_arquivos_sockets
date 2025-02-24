package arquivos;

import java.io.*;
import java.net.*;

public class ClienteArquivo {
    public static void main(String[] args) {
        String enderecoServidor = "localhost"; // IP DO SERVIDOR
        int porta = 1515;

        // Lista de arquivos a serem enviados
        String[] caminhosArquivos = {"image.webp", "image2.jpg", "image3.jpeg"};

        try (Socket socket = new Socket(enderecoServidor, porta);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            System.out.println("Conectado ao servidor.");

            // Envia a quantidade de arquivos
            out.writeInt(caminhosArquivos.length);

            for (String caminho : caminhosArquivos) {
                File arquivo = new File(caminho);
                if (!arquivo.exists()) {
                    System.out.println("Arquivo não encontrado: " + caminho);
                    continue;
                }

                // Envia o nome e o tamanho do arquivo
                out.writeUTF(arquivo.getName());
                out.writeLong(arquivo.length());

                // Envia o conteúdo do arquivo
                try (FileInputStream fileIn = new FileInputStream(arquivo);
                     BufferedInputStream bufferedIn = new BufferedInputStream(fileIn)) {

                    byte[] buffer = new byte[4096];
                    int bytesLidos;
                    while ((bytesLidos = bufferedIn.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesLidos);
                    }
                }

                out.flush();
                System.out.println("Arquivo enviado: " + arquivo.getName());

                // Aguarda confirmação do servidor
                String resposta = in.readUTF();
                System.out.println("Servidor: " + resposta);
            }

            socket.shutdownOutput(); // Finaliza envio de dados

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
