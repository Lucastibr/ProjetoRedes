package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

                // Passo 2: Aguardar confirmação do cliente
                input.readLine();

                // Passo 3: Perguntar o nome do cliente
                output.println("Por favor, digite seu nome:");
                String nomeCliente = input.readLine();

                // Passo 5:
                output.println("Olá, " + nomeCliente + "!");

                var menu = repository.getAll();
                var listIds = new ArrayList<Integer>();
                // Passo 5: Enviar itens disponíveis no menu
                output.println("Itens disponíveis no menu:\n");
                for (Menu m : menu) {
                    listIds.add(m.getId());
                    output.println(String.format("Nº: %d - Item: %s com o valor de %f", m.getId(), m.getNome(),
                            m.getPreco()));
                }

                // Passo 7: Solicitar e processar os pedidos do cliente
                double valorTotal = 0.0;
                StringBuilder pedidoCliente = new StringBuilder();
                boolean continuarPedido = true;

                while (continuarPedido) {
                    //Passo 6:
                    output.println("Por favor, digite um item do menu ou 'encerrar' para finalizar o pedido:");

                    output.println("");
                    // Solicitar pedido ao cliente

                    String pedido = input.readLine();

                    // Verificar se o cliente deseja continuar ou encerrar o pedido
                    if (pedido.equalsIgnoreCase("encerrar")) {
                        continuarPedido = false;
                    } else {
                        try {
                            int itemId = Integer.parseInt(pedido);

                            // Verificar se o ID do item é válido
                            if (listIds.contains(itemId)) {
                                // Encontrar o item pelo ID
                                Menu item = menu.stream().filter(m -> m.getId() == itemId).findFirst().orElse(null);

                                if (item != null) {
                                    // Adicionar o item ao valor total e ao registro do pedido
                                    valorTotal += item.getPreco();
                                    pedidoCliente.append(String.format("- %s (R$ %.2f)\n", item.getNome(),
                                            item.getPreco()));
                                }
                            } else {
                                output.println("Opção inválida. Por favor, digite novamente.");
                            }
                        } catch (NumberFormatException e) {
                            output.println("Opção inválida. Por favor, digite novamente.");
                        }
                    }
                }

                // Passo 8: Calcular o valor total do pedido

                // Passo 9: Enviar resultado do pedido para o cliente
                output.println("Pedido realizado:\n" + "Itens do pedido" + "\nValor total: " + "valorTotal" + " BRL");

                // Passo 10: Aguardar confirmação do cliente
                input.readLine();

                // Passo 11: Enviar mensagem de agradecimento ou reiniciar o fluxo
                output.println("Obrigado por utilizar nosso serviço, " + nomeCliente + "!");

                // Fechar recursos
                clientSocket.close();
                System.out.println("Cliente desconectado: " + clientSocket.getInetAddress().getHostAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
