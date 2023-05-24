package view;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import repository.RestaurantRepository;
import model.Menu;

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor do restaurante iniciado na porta " + port);
            var repository = new RestaurantRepository();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

                // Criando fluxo de entrada e saída para comunicação com o cliente
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                // Passo 1: Enviar mensagem de boas-vindas ao cliente
                output.println("Bem-vindo ao restaurante!");

                // Passo 2: Perguntar o nome do cliente
                output.println("Por favor, digite o seu nome:");

                // Passo 3: Receber o nome do cliente
                String nomeCliente = input.readLine();

                // Passo 4: Armazenar o nome e chamar o cliente pelo nome
                output.println("Olá, " + nomeCliente + "!");

                var menu = repository.getAll();
                var listIds = new ArrayList<Integer>();
                // Passo 5: Mostrar os itens disponíveis no menu
                output.println("Itens disponíveis no menu:\n");
                for (Menu m : menu) {
                    listIds.add(m.getId());
                    output.println(String.format("Nº: %d - Item: %s com o valor de %f", m.getId(), m.getNome(), m.getPreco()));
                }

                output.println("Por favor, digite a opção desejada: ");

                // Passo 6: Aguardar e processar os pedidos do cliente
                double valorTotal = 0.0;
                StringBuilder pedidoCliente = new StringBuilder();
                boolean continuarPedido = true;

                while (continuarPedido) {
                    // Receber o pedido do cliente
                    String pedido = input.readLine();

                    // Verificar se o cliente deseja continuar ou encerrar o pedido
                    if (pedido.equalsIgnoreCase("encerrar")) {
                        continuarPedido = false;
                    } else {
                        // Processar o pedido
                        // ...

                        // Adicionar o pedido ao valor total e ao registro do pedido
                        // ...
                    }
                }

                // Passo 7: Calcular o valor total do pedido

                // Passo 8: Mostrar o resultado do pedido
                output.println("Pedido realizado:\n" + pedidoCliente.toString());
                output.println("Valor total: " + valorTotal + " BRL");

                // Passo 9: Receber confirmação do pedido do cliente
                output.println("Por favor, confirme o seu pedido (sim/não):");
                String confirmacaoPedido = input.readLine();

                // Passo 10: Enviar mensagem de agradecimento ou reiniciar o fluxo
                if (confirmacaoPedido.equalsIgnoreCase("sim")) {
                    output.println("Pedido confirmado! Obrigado por escolher nosso restaurante.");
                } else {
                    output.println("O pedido foi cancelado. Por favor, refaça seu pedido.");
                    // Reiniciar o fluxo
                    continue;
                }

                // Fechar recursos
                clientSocket.close();
                System.out.println("Conexão com o cliente encerrada: " + clientSocket.getInetAddress().getHostAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}


