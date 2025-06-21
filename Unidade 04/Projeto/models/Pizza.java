package Projeto.models;

import java.util.List;

public class Pizza {
    private List<String> sabores;
    private double preco;
    private TamanhoPizza tamanho;

    public enum TamanhoPizza{
        BROTO,
        GRANDE,
        GIGA;

        public static TamanhoPizza getByIndex(int index){
            TamanhoPizza[] tamanhos = TamanhoPizza.values();
            if (index >= 0 && index < tamanhos.length) {
                return tamanhos[index];
            }else{
                throw new IllegalArgumentException("Posição incorreta do index");
            }
        }
    }

    public Pizza(List<String> sabores, double preco, TamanhoPizza tamanho){
        this.sabores = sabores;
        this.preco = preco;
        this.tamanho = tamanho;
    }

    public List<String> getSabores(){
        return sabores;
    }

    public double getPreco(){
        return preco;
    }

    public TamanhoPizza getTamanho(){
        return tamanho;
    }

    public void setSabores(List<String> sabores){
        this.sabores = sabores;
    }

    public void setPreco(double preco){
        this.preco = preco;
    }

    public void setTamanho(TamanhoPizza tamanho){
        this.tamanho = tamanho;
    }
    
}


/**
 * =======================================================================================
 * EXPLICAÇÃO DA ESTRUTURA DE DADOS DA CLASSE PIZZA
 * =======================================================================================
 * 
 * Esta classe implementa uma ESTRUTURA DE DADOS COMPOSTA que combina diferentes
 * tipos de coleções e estruturas para representar uma pizza de forma eficiente.
 * 
 * 1. COMPONENTES DA ESTRUTURA:
 * - List<String> sabores: ESTRUTURA DINÂMICA para múltiplos sabores
 * - double preco: TIPO PRIMITIVO para eficiência em cálculos
 * - TamanhoPizza tamanho: ENUM para type safety e validação
 * 
 * 2. ENUM TamanhoPizza COM ALGORITMO DE CONVERSÃO:
 * - Implementa padrão ENUM-TO-INDEX para interface com usuário
 * - Algoritmo getByIndex(): converte entrada numérica em tipo seguro
 * - Validação automática: previne IndexOutOfBoundsException
 * - Complexidade: O(1) - acesso direto ao array de values()
 * 
 * 3. ALGORITMO DE BUSCA INTEGRADA:
 * - Quando usado em alterarSaborPizza():
 *   * Busca externa: localiza Pedido por ID
 *   * Busca interna: pedido.getPizzas().get(index)
 *   * Complexidade total: O(n) + O(1) = O(n)
 * 
 * 4. VANTAGENS DA ESTRUTURA ESCOLHIDA:
 * - FLEXIBILIDADE: List<String> permite n sabores por pizza
 * - PERFORMANCE: Acesso direto por índice O(1)
 * - SEGURANÇA: Enum previne valores inválidos
 * - MANUTENIBILIDADE: Fácil adicionar novos tamanhos
 * 
 * 5. PADRÃO DE DESIGN APLICADO:
 * - DATA CLASS: Encapsula dados relacionados
 * - IMMUTABLE ENUM: Constantes seguras e validadas
 * - GETTER/SETTER: Controle de acesso aos dados
 * 
 * Esta estrutura é amplamente usada em sistemas de delivery reais,
 * permitindo flexibilidade para customização de produtos.
 */