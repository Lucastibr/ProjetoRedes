package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

import model.Extensions;
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

                var validaNome = validarNomes(nomeCliente);

                if(validaNome == ""){
                    // Passo 4:
                    output.println("Olá, " + nomeCliente + "!");
                }
                else{
                    output.println(validaNome);
                }

                //Buscando os itens do BD
                var menu = repository.getAll();
                var listIds = new ArrayList<Integer>();

                // Passo 5: Enviar para o cliente os itens disponíveis no menu
                output.println("Abaixo, você verá os itens disponíveis no menu:\n");
                for (Menu m : menu) {
                    listIds.add(m.getId());
                    output.println(String.format("Número do Item: %d - Nome: %s com o valor de (R$ %.2f)", m.getId(), m.getNome(),
                            m.getPreco()));
                }

                // Passo 6: Solicitar e processar os pedidos do cliente
                double valorTotal = 0.0;
                StringBuilder pedidoCliente = new StringBuilder();
                Boolean selectedIndisponibleItem = false;

                while (true) {
                    output.println("Por favor, digite o número do item do menu ou 'encerrar' para finalizar o pedido:");

                    if(valorTotal <= 0 && !selectedIndisponibleItem){
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
                                selectedIndisponibleItem = false;
                                // Encontrar o item pelo ID
                                Menu item = menu.stream().filter(m -> m.getId() == itemId).findFirst().orElse(null);

                                if (item != null) {
                                    // Adicionar o item ao valor total e ao registro do pedido
                                    valorTotal += item.getPreco();
                                    pedidoCliente.append(String.format("- %s (R$ %.2f)\n", item.getNome(),
                                            item.getPreco()));

                                    output.println(String.format("Você escolheu o item- %s no valor de (R$ %.2f)\n ", item.getNome(),
                                            item.getPreco()));
                                }
                            } else {
                                selectedIndisponibleItem = true;
                                output.println("Opção inválida. Por favor, digite novamente o número que está disponível no menu!");
                            }
                        } catch (NumberFormatException e) {
                            selectedIndisponibleItem = true;
                            output.println("Opção inválida. Por favor, digite novamente somente o número do item!.");
                        }
                    }
                }

                output.println(String.format("Aqui estão os itens do seu pedido: \n %s - Valor Total: (R$ %.2f)", pedidoCliente, valorTotal));

                // Passo 7: Enviar mensagem de agradecimento
                output.println("Obrigado por utilizar nosso serviço, " + nomeCliente + "! \n" +
                        "Os padinho`s Lucas Pereira, Ruan Torres e Helton Rangel agradecem a preferência!");

                // Passo 8 :Fechar a conexão com o cliente
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

    private static String validarNomes(String nome){

        String mensagem = "";
        switch (nome.toLowerCase(Locale.ROOT)){
            case "geraldo":
                mensagem = String.format("Bem vindo %s, espero que você não faça nenhuma caquinha digitando algo " +
                        "que não deve pra tirar nossos pontos! \uD83D\uDE06 \uD83D\uDE06 \uD83D\uDE06", nome);
                break;
            case "professor":
                mensagem = String.format("Olha %s, seja bem vindo! Melhor professor dessa faculdade! " +
                        "\uD83E\uDD10 \uD83E\uDD10 \uD83E\uDD10 \uD83E\uDD10", nome);
                break;
            case "professor geraldo":
                mensagem = String.format("Ruan disse que tá chateado com você, porque além de nos avaliar na Fematec com toda aquela pressão, \n " +
                        "Agora ficamos receosos ao fazer esse trabalho \uD83D\uDE02 \uD83D\uDE02 \uD83D\uDE02 \uD83D\uDE02", nome);
                break;
        }

        return mensagem;
    }
}
