package arquivos;

import java.io.*;
import java.net.*;

public class ServidorArquivo {
    public static void main(String[] args) {
        int porta = 1515;
        String destinoPasta = "teste/"; // Pasta onde os arquivos serão salvos

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor esperando conexão...");

            while (true) { // Mantém o servidor ativo
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado!");

                try (DataInputStream in = new DataInputStream(socket.getInputStream());
                     DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

                    // Lê a quantidade de arquivos a serem recebidos
                    int numArquivos = in.readInt();
                    System.out.println("Número de arquivos a receber: " + numArquivos);

                    for (int i = 0; i < numArquivos; i++) {
                        // Recebe o nome do arquivo
                        String nomeArquivo = in.readUTF();
                        long tamanhoArquivo = in.readLong();
                        File destinoArquivo = new File(destinoPasta + nomeArquivo);

                        // Cria streams para salvar o arquivo
                        try (FileOutputStream fileOut = new FileOutputStream(destinoArquivo);
                             BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut)) {

                            byte[] buffer = new byte[4096];
                            long bytesRecebidos = 0;

                            // Lendo o arquivo do socket e salvando no destino
                            while (bytesRecebidos < tamanhoArquivo) {
                                int bytesLidos = in.read(buffer);
                                bufferedOut.write(buffer, 0, bytesLidos);
                                bytesRecebidos += bytesLidos;
                            }

                            bufferedOut.flush();
                            System.out.println("Arquivo recebido: " + nomeArquivo + " (" + bytesRecebidos + " bytes)");
                        }

                        // Confirma recepção
                        out.writeUTF("Arquivo " + nomeArquivo + " recebido com sucesso!");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                socket.close(); // Fecha conexão com o cliente
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
