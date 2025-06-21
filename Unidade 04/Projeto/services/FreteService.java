package Projeto.services;

import java.util.List;
import java.util.Scanner;

import Projeto.models.Pedido;

/**
 * Service responsável por cálculos de frete
 * IMPLEMENTAÇÕES DA ATIVIDADE: Requisito 3 (Cálculo de Frete)
 */
public class FreteService {
    
    /**
     * ALTERAÇÃO IMPLEMENTADA: Calcula frete para pedidos existentes
     * Permite recalcular frete alterando a distância
     * Requisito 3 da atividade final
     */
    public static void calcularFreteEntrega(Scanner scanner, List<Pedido> listaPedidos) {
        System.out.println("=== CALCULAR FRETE DE ENTREGA ===");
        
        if (listaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }
        
        // Mostrar pedidos disponíveis
        System.out.println("\nPedidos disponíveis:");
        for (Pedido pedido : listaPedidos) {
            System.out.println("ID: " + pedido.getId() + 
                              " - Cliente: " + pedido.getCliente().getNome() + 
                              " - Valor pizzas: R$ " + String.format("%.2f", pedido.getValorTotal()) +
                              " - Frete atual: R$ " + String.format("%.2f", pedido.getFrete()));
        }
        
        System.out.print("\nDigite o ID do pedido: ");
        int idPedido = scanner.nextInt();
        scanner.nextLine();
        
        // Buscar pedido
        Pedido pedidoEncontrado = null;
        for (Pedido pedido : listaPedidos) {
            if (pedido.getId() == idPedido) {
                pedidoEncontrado = pedido;
                break;
            }
        }
        
        if (pedidoEncontrado == null) {
            System.out.println("Pedido não encontrado!");
            return;
        }
        
        System.out.print("Digite a nova distância até o destino (km): ");
        double novaDistancia = scanner.nextDouble();
        scanner.nextLine();
        
        // Calcular novo frete
        double novoFrete = pedidoEncontrado.calcularFrete(novaDistancia);
        
        // Atualizar pedido com nova distância e frete
        pedidoEncontrado.setDistancia(novaDistancia);
        pedidoEncontrado.setFrete(novoFrete);
        
        // Exibir resultados
        System.out.println("\n=== FRETE ATUALIZADO ===");
        System.out.println("Cliente: " + pedidoEncontrado.getCliente().getNome());
        System.out.println("Nova distância: " + novaDistancia + " km");
        System.out.println("Quantidade de pizzas: " + pedidoEncontrado.getPizzas().size());
        System.out.println("Valor das pizzas: R$ " + String.format("%.2f", pedidoEncontrado.getValorTotal()));
        System.out.println("Novo valor do frete: R$ " + String.format("%.2f", novoFrete));
        System.out.println("═══════════════════════════");
        System.out.println("VALOR TOTAL ATUALIZADO: R$ " + String.format("%.2f", pedidoEncontrado.getValorTotalComFrete()));
    }
}