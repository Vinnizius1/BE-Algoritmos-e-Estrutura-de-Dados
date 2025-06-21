package Projeto.services;

import Projeto.models.Pedido;
import Projeto.models.Cliente;
import Projeto.models.Pizza;
import Projeto.models.Pizza.TamanhoPizza;
import Projeto.models.Cardapio; 
import java.util.*;

/**
 * Service responsável por operações relacionadas a pedidos
 * IMPLEMENTAÇÕES DA ATIVIDADE: Requisito 1 (Alterar Pedido)
 */
public class PedidoService {
    
    private static Cardapio cardapio = new Cardapio(); 
    
    /**
     * ALTERAÇÃO IMPLEMENTADA: Método para alterar pedidos existentes
     */
    public static void alterarPedido(Scanner scanner, List<Pedido> listaPedidos) {
        System.out.println("--- ALTERAR PEDIDO ---");
        
        if (listaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado para alterar.");
            return;
        }

        // Mostrar pedidos disponíveis
        System.out.println("\nPedidos disponíveis:");
        for (Pedido pedido : listaPedidos) {
            System.out.println("ID: " + pedido.getId() + " - Cliente: " + 
                             pedido.getCliente().getNome() + " - Total: R$ " + 
                             String.format("%.2f", pedido.getValorTotalComFrete()));
        }

        // Buscar pedido por ID ou nome do cliente
        System.out.print("\nDigite o ID do pedido ou nome do cliente: ");
        String busca = scanner.nextLine();

        Pedido pedidoEncontrado = buscarPedido(listaPedidos, busca);

        if (pedidoEncontrado == null) {
            System.out.println("Pedido não encontrado!");
            return;
        }

        System.out.println("\nPedido encontrado:");
        exibirDetalhesPedido(pedidoEncontrado);

        // Menu de alterações
        System.out.println("\nO que deseja fazer?");
        System.out.println("1. Adicionar pizza");
        System.out.println("2. Remover pizza");
        System.out.println("3. Alterar sabor de uma pizza");
        System.out.print("Escolha: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                adicionarPizzaAoPedido(scanner, pedidoEncontrado);
                break;
            case 2:
                removerPizzaDoPedido(scanner, pedidoEncontrado);
                break;
            case 3:
                alterarSaborPizza(scanner, pedidoEncontrado);
                break;
            default:
                System.out.println("Opção inválida!");
                return;
        }

        System.out.println("\nPedido alterado com sucesso!");
        exibirDetalhesPedido(pedidoEncontrado);
    }

    /**
     * Busca pedido por ID ou nome do cliente
     */
    private static Pedido buscarPedido(List<Pedido> listaPedidos, String busca) {
        try {
            // Busca por ID numérico
            int id = Integer.parseInt(busca); 
            for (Pedido pedido : listaPedidos) {
                if (pedido.getId() == id) {
                    return pedido;
                }
            }
        } catch (NumberFormatException e) {
            // Se não for um número, busca por nome do cliente
            for (Pedido pedido : listaPedidos) {
                if (pedido.getCliente().getNome().equalsIgnoreCase(busca)) {
                    return pedido;
                }
            }
        }
        return null;
    }

    /**
     * Adiciona nova pizza ao pedido existente
     */
    private static void adicionarPizzaAoPedido(Scanner scanner, Pedido pedido) {
        System.out.println("\n=== ADICIONAR PIZZA ===");
        
        System.out.println("1. Pizza Broto (1 sabor) - R$ 25.00");
        System.out.println("2. Pizza Grande (ate 2 sabores) - R$ 35.00");
        System.out.println("3. Pizza Giga (ate 3 sabores) - R$ 45.00");
        
        System.out.print("Escolha o tamanho da pizza (1-3): ");
        int tamanhoEscolhido = scanner.nextInt();
        scanner.nextLine();
        
        TamanhoPizza tamanho;
        int maxSabores;
        
        switch(tamanhoEscolhido) {
            case 1:
                tamanho = TamanhoPizza.BROTO;
                maxSabores = 1;
                break;
            case 2:
                tamanho = TamanhoPizza.GRANDE;
                maxSabores = 2;
                break;
            case 3:
                tamanho = TamanhoPizza.GIGA;
                maxSabores = 3;
                break;
            default:
                System.out.println("Opcao invalida!");
                return;
        }
        
        // MOSTRAR CARDAPIO
        exibirCardapio();
        
        List<String> sabores = new ArrayList<>();
        for (int i = 1; i <= maxSabores; i++) {
            if (i == 1 || (sabores.size() < maxSabores && confirmarMaisSabor(scanner))) {
                boolean saborValido = false;
                while (!saborValido) {
                    System.out.print("Escolha o " + i + " sabor: ");
                    String sabor = escolherSabor(scanner);
                    if (sabor != null) {
                        sabores.add(sabor);
                        saborValido = true;
                        System.out.println("Sabor '" + sabor + "' adicionado!");
                    } else {
                        System.out.println("Tente novamente.");
                        exibirCardapio(); // Mostrar cardápio novamente
                    }
                }
            }
        }
        
        // CALCULAR PREÇO BASEADO NO CARDÁPIO
        double precoJusto = cardapio.getPrecoJusto(sabores);
        double precoFinal = precoJusto + calcularTaxaTamanho(tamanho);
        
        Pizza novaPizza = new Pizza(sabores, precoFinal, tamanho);
        pedido.getPizzas().add(novaPizza);
        
        double novoValor = somarPizzas(pedido.getPizzas());
        pedido.setValorTotal(novoValor);
        
        System.out.println("Pizza adicionada com sucesso!");
        System.out.println("Preco da pizza: R$ " + String.format("%.2f", precoFinal));
    }

    /**
     * Remove pizza do pedido
     */
    private static void removerPizzaDoPedido(Scanner scanner, Pedido pedido) {
        if (pedido.getPizzas().isEmpty()) {
            System.out.println("O pedido não tem pizzas para remover.");
            return;
        }

        System.out.println("\nPizzas no pedido:");
        for (int i = 0; i < pedido.getPizzas().size(); i++) {
            Pizza pizza = pedido.getPizzas().get(i);
            System.out.println((i + 1) + ". " + pizza.getTamanho() + " - " + 
                             String.join(", ", pizza.getSabores()));
        }

        System.out.print("Digite o número da pizza para remover: ");
        int indice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (indice >= 0 && indice < pedido.getPizzas().size()) {
            pedido.getPizzas().remove(indice);
            
            double novoValor = somarPizzas(pedido.getPizzas());
            pedido.setValorTotal(novoValor);
            
            System.out.println("Pizza removida com sucesso!");
        } else {
            System.out.println("Número inválido!");
        }
    }

    /**
     * Altera sabor de uma pizza existente
     */
    private static void alterarSaborPizza(Scanner scanner, Pedido pedido) {
        if (pedido.getPizzas().isEmpty()) {
            System.out.println("O pedido não tem pizzas para alterar.");
            return;
        }

        System.out.println("\nPizzas no pedido:");
        for (int i = 0; i < pedido.getPizzas().size(); i++) {
            // Busca por índice
            Pizza pizza = pedido.getPizzas().get(i);
            System.out.println((i + 1) + ". " + pizza.getTamanho() + " - " + 
                             String.join(", ", pizza.getSabores()));
        }

        System.out.print("Digite o número da pizza para alterar: ");
        int indicePizza = scanner.nextInt() - 1;
        scanner.nextLine();

        if (indicePizza < 0 || indicePizza >= pedido.getPizzas().size()) {
            System.out.println("Número inválido!");
            return;
        }

        Pizza pizza = pedido.getPizzas().get(indicePizza);
        
        System.out.println("\nSabores atuais:");
        for (int i = 0; i < pizza.getSabores().size(); i++) {
            System.out.println((i + 1) + ". " + pizza.getSabores().get(i));
        }

        System.out.print("Digite o número do sabor para alterar: ");
        int indiceSabor = scanner.nextInt() - 1;
        scanner.nextLine();

        if (indiceSabor >= 0 && indiceSabor < pizza.getSabores().size()) {
            // MOSTRAR CARDÁPIO ANTES DE ESCOLHER NOVO SABOR
            exibirCardapio();
            
            // USAR O MÉTODO escolherSabor() PARA VALIDAÇÃO
            boolean saborValido = false;
            while (!saborValido) {
                String novoSabor = escolherSabor(scanner);
                if (novoSabor != null) {
                    // Modificação de sabor usando List.set()
                    pizza.getSabores().set(indiceSabor, novoSabor);
                    System.out.println("Sabor alterado com sucesso!");
                    saborValido = true;
                } else {
                    System.out.println("Tente novamente.");
                    exibirCardapio(); // Mostrar cardápio novamente em caso de erro
                }
            }
        } else {
            System.out.println("Número inválido!");
        }
    }

    /**
     * Cria um novo pedido
     */
    public static void criarNovoPedido(Scanner scanner, List<Pedido> listaPedidos, List<Cliente> listaClientes) {
        System.out.println("--- FAZER PEDIDO ---");
        
        if (listaClientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado! Cadastre um cliente primeiro.");
            return;
        }

        // Selecionar cliente
        System.out.println("\nClientes disponíveis:");
        for (int i = 0; i < listaClientes.size(); i++) {
            System.out.println((i + 1) + ". " + listaClientes.get(i).getNome());
        }

        System.out.print("Escolha o cliente (número): ");
        int indiceCliente = scanner.nextInt() - 1;
        scanner.nextLine();

        if (indiceCliente < 0 || indiceCliente >= listaClientes.size()) {
            System.out.println("Cliente inválido!");
            return;
        }

        Cliente cliente = listaClientes.get(indiceCliente);
        List<Pizza> pizzas = new ArrayList<>();

        // Adicionar pizzas ao pedido
        boolean adicionarMaisPizzas = true;
        while (adicionarMaisPizzas) {
            System.out.println("\n=== ADICIONAR PIZZA ===");
            System.out.println("1. Pizza Broto (1 sabor) - R$ 25.00");
            System.out.println("2. Pizza Grande (até 2 sabores) - R$ 35.00");
            System.out.println("3. Pizza Giga (até 3 sabores) - R$ 45.00");
            
            System.out.print("Escolha o tamanho (1-3): ");
            int tamanho = scanner.nextInt();
            scanner.nextLine();

            TamanhoPizza tamanhoPizza;
            int maxSabores;

            switch (tamanho) {
                case 1:
                    tamanhoPizza = TamanhoPizza.BROTO;
                    maxSabores = 1;
                    break;
                case 2:
                    tamanhoPizza = TamanhoPizza.GRANDE;
                    maxSabores = 2;
                    break;
                case 3:
                    tamanhoPizza = TamanhoPizza.GIGA;
                    maxSabores = 3;
                    break;
                default:
                    System.out.println("Tamanho inválido!");
                    continue;
            }

            // MOSTRAR CARDÁPIO
            exibirCardapio();

            List<String> sabores = new ArrayList<>();
            for (int i = 1; i <= maxSabores; i++) {
                if (i == 1 || (sabores.size() < maxSabores && confirmarMaisSabor(scanner))) {
                    boolean saborValido = false;
                    while (!saborValido) {
                        System.out.print("Escolha o " + i + " sabor: ");
                        String sabor = escolherSabor(scanner);
                        if (sabor != null) {
                            sabores.add(sabor);
                            saborValido = true;
                            System.out.println("Sabor '" + sabor + "' adicionado!");
                        } else {
                            System.out.println("Tente novamente.");
                            exibirCardapio(); // Mostrar cardápio novamente
                        }
                    }
                }
            }

            // CALCULAR PREÇO BASEADO NO CARDÁPIO
            double precoJusto = cardapio.getPrecoJusto(sabores);
            double precoFinal = precoJusto + calcularTaxaTamanho(tamanhoPizza);

            pizzas.add(new Pizza(sabores, precoFinal, tamanhoPizza));

            System.out.print("Deseja adicionar outra pizza? (s/n): ");
            adicionarMaisPizzas = scanner.nextLine().toLowerCase().startsWith("s");
        }

        double valorTotal = somarPizzas(pizzas);
        int novoId = listaPedidos.size() + 1;
        Pedido pedido = new Pedido(novoId, cliente, pizzas, valorTotal);

        System.out.println("\n--- CÁLCULO DO FRETE ---");
        System.out.print("Digite a distância em KM para entrega: ");
        double distancia = scanner.nextDouble();
        scanner.nextLine();

        double frete = pedido.calcularFrete(distancia);
        pedido.setFrete(frete);
        pedido.setDistancia(distancia);

        listaPedidos.add(pedido);

        System.out.println("\n=== PEDIDO CRIADO ===");
        exibirDetalhesPedido(pedido);
        System.out.println("ID do Pedido: " + pedido.getId());
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================

    /**
     * Exibe cardápio de sabores disponíveis
     */
    private static void exibirCardapio() {
        System.out.println("\n=== SABORES DISPONIVEIS ===");
        Map<String, Double> cardapioMap = cardapio.getCardapio();
        int contador = 1;
        for (Map.Entry<String, Double> entrada : cardapioMap.entrySet()) {
            System.out.println(contador + ". " + entrada.getKey() + " - R$ " + 
                             String.format("%.2f", entrada.getValue()));
            contador++;
        }
        System.out.println("============================");
    }

    /**
     * Calcula taxa adicional por tamanho
     */
    private static double calcularTaxaTamanho(TamanhoPizza tamanho) {
        switch (tamanho) {
            case BROTO:
                return 5.00;  // Taxa base para broto
            case GRANDE:
                return 10.00; // Taxa para grande
            case GIGA:
                return 15.00; // Taxa para giga
            default:
                return 0.0;
        }
    }

    /**
     * Exibe detalhes completos do pedido
     */
    private static void exibirDetalhesPedido(Pedido pedido) {
        System.out.println("Cliente: " + pedido.getCliente().getNome());
        System.out.println("Pizzas:");
        for (Pizza pizza : pedido.getPizzas()) {
            System.out.println("  - " + pizza.getTamanho() + ": " + 
                             String.join(", ", pizza.getSabores()));
        }
        System.out.println("Valor das pizzas: R$ " + String.format("%.2f", pedido.getValorTotal()));
        System.out.println("Frete: R$ " + String.format("%.2f", pedido.getFrete()));
        System.out.println("Total com frete: R$ " + String.format("%.2f", pedido.getValorTotalComFrete()));
    }

    /**
     * Confirma se usuário quer adicionar mais sabor
     */
    private static boolean confirmarMaisSabor(Scanner scanner) {
        System.out.print("Deseja adicionar mais um sabor? (s/n): ");
        String resposta = scanner.nextLine().toLowerCase();
        return resposta.equals("s") || resposta.equals("sim");
    }

    /**
     * Calcula valor total das pizzas
     */
    private static double somarPizzas(List<Pizza> pizzas) {
        double total = 0.0;
        for (Pizza pizza : pizzas) {
        total += pizza.getPreco(); // USAR O PREÇO REAL DA PIZZA
        }
        return total;
    }

    /**
     * Permite escolher sabor por número ou nome
     */
    private static String escolherSabor(Scanner scanner) {
        System.out.print("Digite o numero ou nome do sabor: ");
        String entrada = scanner.nextLine().trim();
        
        // Tentar converter para número primeiro
        try {
            int numero = Integer.parseInt(entrada);
            List<String> saboresDisponiveis = new ArrayList<>(cardapio.getCardapio().keySet());
            
            if (numero >= 1 && numero <= saboresDisponiveis.size()) {
                return saboresDisponiveis.get(numero - 1);
            } else {
                System.out.println("Numero invalido! Escolha entre 1 e " + saboresDisponiveis.size());
                return null;
            }
        } catch (NumberFormatException e) {
            // Se não for número, verificar se é nome válido
            if (cardapio.getCardapio().containsKey(entrada)) {
                return entrada;
            } else {
                System.out.println("Sabor nao encontrado!");
                return null;
            }
        }
    }
}


/**
 * =======================================================================================
 * EXPLICAÇÃO DOS ALGORITMOS DE BUSCA E ALTERAÇÃO DE PEDIDOS
 * =======================================================================================
 * 
 * Esta classe implementa ALGORITMOS DE BUSCA EM CASCATA e MODIFICAÇÃO DE ESTRUTURAS
 * DINÂMICAS para permitir alterações flexíveis em pedidos existentes.
 * 
 * 1. ALGORITMO DE BUSCA HÍBRIDA (linhas 74-88):
 * - ENTRADA: String que pode ser ID numérico ou nome do cliente
 * - ESTRATÉGIA: Try-catch para determinar tipo de busca
 * - BUSCA NUMÉRICA: Integer.parseInt() + loop O(n) por ID
 * - BUSCA TEXTUAL: equalsIgnoreCase() + loop O(n) por nome
 * - VANTAGEM: Interface flexível para usuário final
 * 
 * 2. ESTRUTURA DE DADOS EM CASCATA:
 * - Nível 1: List<Pedido> (busca principal)
 * - Nível 2: Pedido.getPizzas() → List<Pizza> (busca secundária)
 * - Nível 3: Pizza.getSabores() → List<String> (modificação)
 * 
 * 
 * 3. ALGORITMOS DE MODIFICAÇÃO IMPLEMENTADOS:
 * 
 * A) adicionarPizzaAoPedido() (linha 92):
 *    - Usa ArrayList.add() para inserção 
 *    - Recalcula total com somarPizzas() O(m), onde m = número de pizzas
 * 
 * B) removerPizzaDoPedido() (linha 151):
 *    - Usa ArrayList.remove(index) O(n) para remoção por índice
 *    - Recalcula valores automaticamente
 * 
 * C) alterarSaborPizza() (linha 182):
 *    - Busca pizza por índice: getPizzas().get(indicePizza) 
 *    - Modifica sabor: getSabores().set(indiceSabor, novoSabor) 
 *    
 * 
 * 4. VALIDAÇÕES E INTEGRIDADE:
 * - Validação de índices (linhas 197-200, 208-211)
 * - Verificação de listas vazias (linhas 151-154, 184-187)
 * - Recálculo automático de valores após mudanças
 * - Tratamento de exceções NumberFormatException
 * 
 * 5. PADRÕES DE DESIGN APLICADOS:
 * - SERVICE LAYER: Separação de lógica de negócio
 * - DEFENSIVE PROGRAMMING: Validações extensivas
 * - METHOD EXTRACTION: Funções pequenas e específicas
 * - ERROR HANDLING: Try-catch para entradas inválidas
 * 
 * Este sistema permite modificações seguras em estruturas complexas,
 * mantendo consistência de dados em tempo real.
 */