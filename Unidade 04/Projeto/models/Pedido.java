package Projeto.models;

import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<Pizza> pizzas;
    private double valorTotal;  // SEMPRE apenas valor das pizzas
    private double frete;       // NOVO: armazenar frete separadamente
    private double distancia;   // NOVO: armazenar distância

    public Pedido(int id, Cliente cliente, List<Pizza> pizzas, double valorTotal){
        this.id = id;
        this.cliente = cliente;
        this.pizzas = pizzas;
        this.valorTotal = valorTotal;
        this.frete = 0.0;        // Inicia sem frete
        this.distancia = 0.0;    // Inicia sem distância
    }

    // Getters existentes
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<Pizza> getPizzas() { return pizzas; }
    public double getValorTotal() { return valorTotal; }
    
    // NOVOS getters
    public double getFrete() { return frete; }
    public double getDistancia() { return distancia; }

    // Setters
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    public void setFrete(double frete) { this.frete = frete; }
    public void setDistancia(double distancia) { this.distancia = distancia; }

    /**
     * Calcula frete baseado na distância e quantidade de pizzas
     */
    public double calcularFrete(double distanciaKm) {
        final double TAXA_BASE = 5.00;
        final double TAXA_POR_KM = 2.00;
        final double TAXA_POR_PIZZA = 1.50;
        
        int quantidadePizzas = this.pizzas.size();
        return TAXA_BASE + (distanciaKm * TAXA_POR_KM) + (quantidadePizzas * TAXA_POR_PIZZA);
    }

    /**
     * Retorna valor total final (pizzas + frete)
     */
    public double getValorTotalComFrete() {
        return this.valorTotal + this.frete;
    }
}
