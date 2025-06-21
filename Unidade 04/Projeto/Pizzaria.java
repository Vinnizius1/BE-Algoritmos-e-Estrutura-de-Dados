package Projeto;

import Projeto.models.Cliente;
import Projeto.models.Pedido;
import Projeto.services.*;
import java.util.*;

/**
 * SISTEMA DE GERENCIAMENTO DE PIZZARIA
 * Classe principal com menu e coordenação dos services
 * 
 * ALTERAÇÕES IMPLEMENTADAS:
 * - Requisito 1: Alterar Pedido (PedidoService)
 * - Requisito 2: Relatórios com Grafos (RelatorioService)  
 * - Requisito 3: Cálculo de Frete (FreteService)
 */
public class Pizzaria {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> listaClientes = new ArrayList<>();
        List<Pedido> listaPedidos = new ArrayList<>();
        boolean continuar = true;

        System.out.println("=== SISTEMA DE PIZZARIA ===");
        System.out.println("Bem-vindo ao sistema modularizado!");
        System.out.println();

        while (continuar) {
            // Exibir menu principal organizado
            exibirMenu();
            
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    // NOVO: Usando PedidoService
                    PedidoService.criarNovoPedido(scanner, listaPedidos, listaClientes);
                    break;
                    
                case 2:
                    // ALTERAÇÃO 1: Implementada - Alterar Pedido
                    PedidoService.alterarPedido(scanner, listaPedidos);
                    break;
                    
                case 3:
                    // Funcionalidade original mantida
                    listaClientes.add(adicionarCliente(scanner));
                    break;
                    
                case 4:
                    // ALTERAÇÃO 2: Implementada - Relatório com Grafos
                    RelatorioService.gerarRelatorio(listaPedidos);
                    break;
                    
                case 5:
                    // Funcionalidade original mantida
                    gerarListaClientes(listaClientes);
                    break;
                    
                case 6:
                    // ALTERAÇÃO 3: Implementada - Calcular Frete
                    FreteService.calcularFreteEntrega(scanner, listaPedidos);
                    break;
                    
                case 9:
                    System.out.println("Obrigado por usar nosso sistema!");
                    if (!listaPedidos.isEmpty()) {
                        System.out.println("Relatório final da sessão:");
                        RelatorioService.gerarRelatorio(listaPedidos);
                    }
                    System.out.println("Até amanhã!");
                    continuar = false;
                    break;
                    
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
            
            // Pequena pausa para melhor experiência
            if (continuar) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
                System.out.println("\n" + "=".repeat(50));
            }
        }
        
        scanner.close();
    }

    /**
     * Exibe menu principal organizado
     */
    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1 - Fazer um novo pedido");
        System.out.println("2 - Alterar um pedido [IMPLEMENTADO]");
        System.out.println("3 - Adicionar um cliente");
        System.out.println("4 - Gerar relatorio [IMPLEMENTADO]");
        System.out.println("5 - Listar clientes");
        System.out.println("6 - Calcular frete [IMPLEMENTADO]");
        System.out.println("9 - Sair do sistema");
        System.out.print("Escolha uma opcao: ");
    }

    /**
     * Adiciona novo cliente ao sistema
     * Funcionalidade original do projeto
     */
    private static Cliente adicionarCliente(Scanner scanner) {
        System.out.println("\n=== CADASTRO DE CLIENTE ===");
        
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        System.out.print("Endereco: ");
        String endereco = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        Cliente novoCliente = new Cliente(nome,endereco, telefone, email);
        
        System.out.println("Cliente '" + nome + "' cadastrado com sucesso!");
        
        return novoCliente;
    }

    /**
     * Exibe lista de clientes cadastrados
     * Funcionalidade original do projeto
     */
    private static void gerarListaClientes(List<Cliente> listaClientes) {
        System.out.println("\n=== LISTA DE CLIENTES ===");
        
        if (listaClientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        
        System.out.println("Total de clientes: " + listaClientes.size());
        for (int i = 0; i < listaClientes.size(); i++) {
            Cliente cliente = listaClientes.get(i);
            System.out.println((i + 1) + ". " + cliente.getNome() + 
                              " - " + cliente.getTelefone() + 
                              " - " + cliente.getEndereco());
        }

         System.out.println("Total de clientes: " + listaClientes.size());         
    }
}
