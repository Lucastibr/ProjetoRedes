package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 8080;

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Conectado ao servidor do restaurante em " + serverAddress + ":" + serverPort);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            // Passo 1: Receber mensagem de boas-vindas do servidor
            String mensagemBoasVindas = input.readLine();
            System.out.println(mensagemBoasVindas);

            // Passo 2: Enviar confirmação ao servidor
            output.println("Confirmar");

            // Passo 3: Receber pergunta do servidor
            String perguntaServidor = input.readLine();
            System.out.println(perguntaServidor);

            // Passo 4: Enviar nome do cliente
            String nomeCliente = scanner.nextLine();
            output.println(nomeCliente);

            // Passo 5: Receber saudação do servidor
            String saudacaoServidor = input.readLine();
            System.out.println(saudacaoServidor);

            // Passo 7: Receber itens disponíveis no menu
            String line;
            while (!(line = input.readLine()).isEmpty()) {
                System.out.println(line);
            }

            while (!(line = input.readLine()).isEmpty()) {
                System.out.println(line);
            }

            // Passo 9: Solicitar e enviar pedidos ao servidor
            boolean continuarPedido = true;
            String pedido = scanner.nextLine();

            while (continuarPedido) {
                output.println(pedido);
                // Aguardar confirmação do servidor
                System.out.println(input.readLine());
                pedido = scanner.nextLine();
            }

            // Passo 10: Receber resultado do pedido
            String resultadoPedido = input.readLine();
            System.out.println(resultadoPedido);

            // Passo 11: Enviar confirmação ao servidor
            output.println("Confirmar");

            // Passo 12: Receber mensagem de agradecimento ou reiniciar o fluxo
            String mensagemAgradecimento = input.readLine();
            System.out.println(mensagemAgradecimento);

            // Fechar recursos
            socket.close();
            System.out.println("Conexão com o servidor encerrada.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
