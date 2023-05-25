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

            // Passo 6: Receber itens disponíveis no menu
            String line;
            while (!(line = input.readLine()).isEmpty()) {
                System.out.println(line);
            }

            while (!(line = input.readLine()).isEmpty()) {
                System.out.println(line);
            }

            // Passo 7: Solicitar e enviar pedidos ao servidor
            boolean continuarPedido = true;
            String pedido = scanner.nextLine();

            while (true) {
                if(pedido.equals("encerrar")){
                    output.println(pedido);
                    break;
                }
                else{
                    output.println(pedido);
                    // Aguardar confirmação do servidor
                    var mensagem = input.readLine();
                    System.out.println(mensagem);
                    System.out.println(input.readLine());
                    if(!mensagem.contains("Opção inválida.")){
                        System.out.println(input.readLine());
                    }
                    pedido = scanner.nextLine();
                }
            }

            //Passo 8: Mostrar o fim da compra
            String result;
            while ((result = input.readLine()) != null){
                System.out.println(result);
            }

            // Fechar recursos
            socket.close();
            System.out.println("Conexão com o servidor encerrada.");
        } catch (IOException e) {
            System.out.println("Te falar amigo, como você quer trabalhar na arquitetura cliente/servidor " +
                    "sem o servidor estar disponível? \uD83E\uDD10 ");
        }
    }
}
