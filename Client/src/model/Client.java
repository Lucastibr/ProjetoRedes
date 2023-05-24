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

            // Passo 2: Enviar nome do cliente
            System.out.println(input.readLine()); // Imprimir a pergunta do servidor
            String nomeCliente = scanner.nextLine();
            output.println(nomeCliente);

            // Passo 4: Receber saudação do servidor
            String saudacaoServidor = input.readLine();
            System.out.println(saudacaoServidor);

            // Passo 5: Receber itens disponíveis no menu
            System.out.println("Itens disponíveis no menu:\n");
            String line;

            while ((line = input.readLine()).isEmpty()) {
                System.out.println(line);
            }

            // Passo 6: Solicitar e enviar pedidos ao servidor
            boolean continuarPedido = true;
            StringBuilder pedidoCliente = new StringBuilder();

            while (continuarPedido) {
                System.out.println("Por favor, digite um item do menu ou 'encerrar' para finalizar o pedido:");
                String pedido = scanner.nextLine();
                output.println(pedido);

                if (pedido.equalsIgnoreCase("encerrar")) {
                    break; // Sai do loop quando o pedido for "encerrar"
                }
            }


            // Passo 8: Receber e exibir resultado do pedido
            String resultadoPedido = input.readLine();
            System.out.println(resultadoPedido);

            // Passo 9: Confirmar o pedido
            System.out.println(input.readLine()); // Imprimir a pergunta do servidor
            String confirmacaoPedido = scanner.nextLine();
            output.println(confirmacaoPedido);

            // Passo 10: Receber mensagem de agradecimento ou reiniciar o fluxo
            String mensagemAgradecimento = input.readLine();
            System.out.println(mensagemAgradecimento);

            System.out.println("Conexão com o servidor encerrada.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
