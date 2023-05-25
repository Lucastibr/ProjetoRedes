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
            System.out.println("Servidor da Padinhos`s Lanchonete iniciado na porta " + port);
            var repository = new RestaurantRepository();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

                // Criando fluxo de entrada e saída para comunicação com o cliente
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                // Passo 1: Enviar mensagem de boas-vindas ao cliente
                output.println("Bem-vindo a Padinhos`s Lanchonete!");

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
                output.println("Abaixo, você verá os itens disponíveis no menu:\n");
                for (Menu m : menu) {
                    listIds.add(m.getId());
                    output.println(String.format("Nº: %d - Item: %s com o valor de (R$ %.2f)", m.getId(), m.getNome(),
                            m.getPreco()));
                }

                // Passo 7: Solicitar e processar os pedidos do cliente
                double valorTotal = 0.0;
                StringBuilder pedidoCliente = new StringBuilder();

                while (true) {
                    //Passo 6:
                    output.println("Por favor, digite o número do item do menu ou 'encerrar' para finalizar o pedido:");

                    if(valorTotal <= 0){
                        output.println("");
                    }
                    // Solicitar pedido ao cliente

                    String pedido = input.readLine();

                    // Verificar se o cliente deseja continuar ou encerrar o pedido
                    if (pedido.equalsIgnoreCase("encerrar")) {
                        break;
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

                                    valorTotal += item.getPreco();

                                    output.println(String.format("Você escolheu o item- %s no valor de (R$ %.2f)\n", item.getNome(),
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

                output.println("Total do Pedido:" + pedidoCliente);

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
