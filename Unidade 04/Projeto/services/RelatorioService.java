package Projeto.services;

import java.util.*;

import Projeto.models.Pedido;
import Projeto.models.Pizza;

/**
 * Service responsável por geração de relatórios e análise de dados
 * IMPLEMENTAÇÕES DA ATIVIDADE: Requisito 2 (Relatórios com Grafos)
 */
public class RelatorioService {
    
    /**
     * ALTERAÇÃO IMPLEMENTADA: Gera relatório completo com análise de grafos
     * Inclui faturamento, ranking de sabores e conexões entre sabores
     */
    public static void gerarRelatorio(List<Pedido> listaPedidos) {
        System.out.println("\n=== RELATÓRIO DE VENDAS ===");
        
        if (listaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado para gerar relatório.");
            return;
        }
        
        // ==========================================
        // PARTE 1: CALCULAR FATURAMENTO TOTAL
        // ==========================================
        double faturamentoTotal = 0.0;
        int totalPedidos = listaPedidos.size();
        
        // Calcula faturamento total percorrendo a lista de pedidos
        for (Pedido pedido : listaPedidos) {
            faturamentoTotal += pedido.getValorTotalComFrete();
        }
        
        System.out.println("Faturamento Total: R$ " + String.format("%.2f", faturamentoTotal));
        System.out.println("Pedidos Realizados: " + totalPedidos);
        System.out.println();
        
        // ==========================================
        // PARTE 2: CONTAR SABORES MAIS PEDIDOS
        // ==========================================
        HashMap<String, Integer> contadorSabores = new HashMap<>();
        
        // Percorre cada pedido e suas pizzas para contar sabores
        for (Pedido pedido : listaPedidos) {
            for (Pizza pizza : pedido.getPizzas()) {
                for (String sabor : pizza.getSabores()) {
                    contadorSabores.put(sabor, contadorSabores.getOrDefault(sabor, 0) + 1);
                }
            }
        }
        
        System.out.println("=== SABORES MAIS PEDIDOS ===");
        for (String sabor : contadorSabores.keySet()) {
            // Acessa elemento por posição na lista
            // Complexidade: O(1) para ArrayList
            int quantidade = contadorSabores.get(sabor);
            System.out.println(sabor + ": " + quantidade + " vezes");
        }
        System.out.println();
        
        // ==========================================
        // PARTE 3: ANÁLISE DE GRAFOS (CONEXÕES)
        // ==========================================
        // Cria HashMap para armazenar conexões entre sabores (grafo)
        HashMap<String, Integer> conexoesSabores = new HashMap<>();

        // Percorre todos os pedidos do sistema
        for (Pedido pedido : listaPedidos) {
            // Percorre todas as pizzas de cada pedido
            for (Pizza pizza : pedido.getPizzas()) {
                // Obtém lista de sabores da pizza atual
                List<String> sabores = pizza.getSabores();
                
                // Só processa se pizza tem 2+ sabores (precisa de pelo menos 2 para fazer conexão)
                if (sabores.size() > 1) {
                    // Loop externo: percorre cada sabor
                    for (int i = 0; i < sabores.size(); i++) {
                        // Loop interno: percorre sabores restantes (evita duplicatas)
                        for (int j = i + 1; j < sabores.size(); j++) {
                            // Pega o primeiro sabor do par
                            String sabor1 = sabores.get(i);
                            // Pega o segundo sabor do par
                            String sabor2 = sabores.get(j);
                            
                            // Cria chave única para a conexão (ex: "Calabresa <--> Queijo")
                            String conexao = criarChaveConexao(sabor1, sabor2);
                            // Incrementa contador da conexão (se não existe, inicia com 0)
                            // Se existe, retorna o valor atual, depois soma +1
                            // getOrDefault(chave, valorPadrao) é um método nativo do HashMap
                            conexoesSabores.put(conexao, conexoesSabores.getOrDefault(conexao, 0) + 1);
                        }
                    }
                }
            }
        }
        
        System.out.println("=== CONEXÕES ENTRE SABORES (GRAFOS) ===");
        // isEmpty(): método nativo que verifica se HashMap está vazio (size() == 0)
        if (conexoesSabores.isEmpty()) {
            System.out.println("Nenhuma conexão encontrada (todas pizzas têm apenas 1 sabor).");
        } else {
            // keySet(): retorna um Set<String> com todas as chaves do HashMap
            // keySet() é usado para iterar pelas chaves sem os valores
            // É como pegar só as "etiquetas" sem os valores
            for (String conexao : conexoesSabores.keySet()) {
                // get(chave): busca o valor pela chave
                // Complexidade O(1) - "tempo de busca" é constante; acesso direto via hash
                int vezes = conexoesSabores.get(conexao);
                System.out.println(conexao + " (" + vezes + " conexões)");
            }
        }
        System.out.println();
    }

    /**
     * Método auxiliar para criar chave padronizada de conexão entre sabores
     * Garante que "A + B" = "B + A" (evita duplicatas)
     */
    private static String criarChaveConexao(String sabor1, String sabor2) {
        // compareTo(): método nativo String que compara alfabeticamente
        // Exemplo: "Calabresa".compareTo("Queijo") = negativo (C vem antes de Q)
        if (sabor1.compareTo(sabor2) < 0) {
            return sabor1 + " <--> " + sabor2;  // Ordem alfabética: A antes de B
        } else {
            return sabor2 + " <--> " + sabor1;  // Inverte: B antes de A
        }
    }
}

        /**
         * =======================================================================================
         * EXPLICAÇÃO DO GRAFO
         * =======================================================================================

         * Usamos estruturas do próprio Java para representar o conceito.
         *
         * 1. VÉRTICES (NÓS):
         * - Os vértices do grafo são os próprios SABORES de pizza (ex: "Calabresa", "Mussarela").
         *
         * 2. ARESTAS (CONEXÕES):
         * - Uma aresta representa um PAR de sabores que foi pedido JUNTO na mesma pizza.
         * - O grafo é PONDERADO: O "peso" da aresta é um contador de quantas vezes
         * aquela combinação específica apareceu. Quanto maior o peso, mais forte a conexão.
         * - O grafo é NÃO-DIRECIONADO: A conexão "Calabresa com Mussarela" é a mesma
         * que "Mussarela com Calabresa". O método `criarChaveConexao` garante isso.
         *
         * 3. COMO FUNCIONA PARA 3 SABORES (OU MAIS):
         * - Se uma pizza tem 3 sabores (A, B, C), ele não cria uma
         * "conexão tripla". Em vez disso, ele quebra em todos os pares de conexões:
         * - Aresta 1: A <--> B
         * - Aresta 2: A <--> C
         * - Aresta 3: B <--> C
         * - Isso é feito pelo `for` aninhado (o loop de `i` e `j`). Ele é o padrão clássico para
         * gerar todas as combinações de pares de uma lista, garantindo que o código funcione
         * para qualquer quantidade de sabores.
         *
         * 4. ARMAZENAMENTO:
         * - Usamos um `HashMap<String, Integer> conexoesSabores` para guardar o grafo:
         * - A CHAVE (String) é a ARESTA (ex: "Calabresa <--> Mussarela").
         * - O VALOR (Integer) é o PESO da aresta (a contagem).
         *
         * Em resumo: Este código constrói um grafo ponderado de sabores para encontrar
         * as combinações mais populares.
         *
         */


/*
 * Explicação dos loops para gerar combinações únicas de pares:
 *
 * Sabores = ["A","B","C"] → n = 3
 *
 * for (int i = 0; i < n; i++) {
 *     for (int j = i + 1; j < n; j++) {
 *         // processa par (i,j)
 *     }
 * }
 *
 * Passo a passo:
 * 
 * i = 0:
 *   j = 1 → 1 < 3? true  → processa (0,1)
 *   j = 2 → 2 < 3? true  → processa (0,2)
 *   j = 3 → 3 < 3? false → sai do loop j
 *
 * i = 1:
 *   j = 2 → 2 < 3? true  → processa (1,2)
 *   j = 3 → 3 < 3? false → sai do loop j
 *
 * i = 2:
 *   j = 3 → 3 < 3? false → não entra no loop j
 *
 * i = 3:
 *   3 < 3? false → sai do loop i e encerra tudo
 *
 * Resultado final: (0,1), (0,2), (1,2)
 * - Sem auto-pares (i,i)
 * - Sem inversões (j,i)
 */