# Sistema de Gerenciamento de Pizzaria 🍕

## Sobre o Projeto

Sistema desenvolvido em Java para gerenciar pedidos de uma pizzaria, aplicando conceitos avançados de **Algoritmos e Estrutura de Dados**. Este projeto demonstra a implementação prática de estruturas de dados, algoritmos de grafos, programação orientada a objetos e interface de usuário interativa.

**Desenvolvido para:** Faculdade Unyleya - Disciplina de Algoritmos e Estrutura de Dados  
**Linguagem:** Java  
**Paradigma:** Programação Orientada a Objetos

## Como Executar

```bash
# Compilar todos os arquivos
javac Projeto/*.java

# Executar o sistema
java Projeto.Pizzaria
```

## Estrutura do Projeto

```
Projeto/
├── Pizzaria.java     # Classe principal com interface e controle
├── Cliente.java      # Modelo de dados do cliente
├── Pedido.java       # Modelo de dados do pedido com cálculo de frete
├── Pizza.java        # Modelo de dados da pizza com enum de tamanhos
└── Cardapio.java     # Gerenciamento dinâmico de preços
```

## Funcionalidades Implementadas

### 🏗️ **Funcionalidades Base**

- **Cadastro de Clientes:** Sistema completo de gerenciamento de clientes
- **Criação de Pedidos:** Interface intuitiva para criação de pedidos personalizados
- **Cardápio Dinâmico:** Sistema de preços baseado na quantidade de sabores
- **Listagem:** Visualização organizada de clientes e pedidos

### 🔧 **Alterações Implementadas**

#### **📝 Alteração 1: Encapsulamento da Classe Pedido**

**Conceitos aplicados:** Programação Orientada a Objetos, Encapsulamento

**Implementação:**

- Adicionados métodos getters para acesso seguro aos dados
- Implementado setter para valorTotal com controle de acesso
- Separação clara entre dados das pizzas e valor do frete

**Métodos adicionados:**

```java
public int getId()
public Cliente getCliente()
public List<Pizza> getPizzas()
public double getValorTotal()
public void setValorTotal(double valorTotal)
```

#### **🔄 Alteração 2: Sistema de Alteração de Pedidos**

**Conceitos aplicados:** Algoritmos de Busca, Validação de Dados, Estruturas de Repetição

**Funcionalidades:**

- Busca de pedidos por ID ou nome do cliente
- Adição/remoção dinâmica de pizzas
- Alteração de sabores existentes
- Recálculo automático do valor total
- Validação completa de entradas

**Algoritmos utilizados:**

- Busca linear para localização de pedidos
- Loops para navegação em estruturas aninhadas
- Algoritmos de validação e sanitização de dados

#### **📊 Alteração 3: Sistema de Relatórios com Análise de Grafos**

**Conceitos aplicados:** HashMap, Algoritmos de Grafos, Loops Aninhados, Análise Estatística

**Funcionalidades:**

- **Análise Financeira:** Cálculo de faturamento total e número de pedidos
- **Ranking de Sabores:** Contagem e classificação dos sabores mais pedidos
- **Análise de Grafos:** Mapeamento de conexões entre sabores que aparecem juntos
- **Relatórios Visuais:** Apresentação organizada dos dados analisados

**Estruturas de Dados:**

```java
HashMap<String, Integer> contadorSabores    // Contador de sabores
HashMap<String, Integer> conexoesSabores    // Grafo de conexões
```

**Algoritmos implementados:**

- Loops aninhados triplos para percorrer pedidos → pizzas → sabores
- Algoritmo de contagem com HashMap para eficiência O(1)
- Algoritmo de criação de grafos para análise de conexões
- Padronização de chaves para evitar duplicatas em conexões

#### **🚛 Alteração 4: Sistema de Cálculo de Frete**

**Conceitos aplicados:** Separação de Responsabilidades, Fórmulas Matemáticas, Estruturas de Dados

**Implementação:**

- **Fórmula de Frete:** Taxa base + (distância × taxa/km) + (quantidade × taxa/pizza)
- **Armazenamento Separado:** Valor das pizzas e frete mantidos independentemente
- **Recálculo Dinâmico:** Possibilidade de alterar distância e recalcular frete
- **Integração Completa:** Funcionalidade acessível via menu principal

**Estrutura de dados:**

```java
private double valorTotal;  // Apenas valor das pizzas
private double frete;       // Valor do frete separadamente
private double distancia;   // Distância para entrega
```

## Conceitos Técnicos Aplicados

### 🗃️ **Estruturas de Dados**

- **ArrayList:** Listas dinâmicas para clientes, pedidos e pizzas
- **HashMap:** Contadores eficientes e mapeamento chave-valor
- **Grafos:** Representação de conexões entre sabores de pizza
- **Enum:** Definição de tamanhos fixos de pizza (PEQUENA, MEDIA, GRANDE)

### 🔄 **Algoritmos**

- **Busca Linear:** Localização de pedidos e clientes
- **Loops Aninhados:** Percorrimento de estruturas hierárquicas
- **Algoritmos de Contagem:** Análise estatística com HashMap
- **Algoritmos de Grafos:** Mapeamento de conexões entre elementos

### 🏗️ **Programação Orientada a Objetos**

- **Encapsulamento:** Proteção de dados com getters/setters
- **Abstração:** Modelagem de entidades do mundo real
- **Composição:** Relacionamentos entre classes (Pedido tem Cliente e Pizzas)
- **Modularização:** Separação de responsabilidades em classes específicas

### 📊 **Análise de Complexidade**

- **HashMap operations:** O(1) para inserção, busca e atualização
- **Busca linear:** O(n) para localização de elementos
- **Loops aninhados:** O(n×m×k) para análise completa de dados
- **Geração de relatórios:** O(n²) para análise de conexões

## Demonstração de Uso

### 🎯 **Fluxo Completo de Teste**

1. **Cadastrar clientes** → Demonstra estruturas de dados básicas
2. **Criar pedidos** → Mostra composição de objetos e cálculos
3. **Alterar pedidos** → Ilustra algoritmos de busca e modificação
4. **Gerar relatório** → Exemplifica análise com grafos e HashMap
5. **Calcular frete** → Apresenta separação de responsabilidades

### 📈 **Exemplo de Saída do Relatório**

```
=== RELATÓRIO DE VENDAS ===
Faturamento Total: R$ 380.50
Pedidos Realizados: 8

=== SABORES MAIS PEDIDOS ===
Margherita: 12 vezes
Pepperoni: 8 vezes
Calabresa: 6 vezes

=== CONEXÕES ENTRE SABORES (GRAFOS) ===
Margherita <--> Pepperoni (5 conexões)
Calabresa <--> Bacon (3 conexões)
Margherita <--> Bacon (2 conexões)
```

## Tecnologias e Ferramentas

- **Java 8+** - Linguagem principal
- **Collections Framework** - ArrayList, HashMap
- **Scanner** - Interface de entrada de dados
- **String.format()** - Formatação de valores monetários
- **Enum** - Definição de constantes tipadas

## Estrutura de Aprendizado

Este projeto progressivamente introduz conceitos de programação:

1. **Básico:** Classes, objetos, métodos
2. **Intermediário:** Collections, loops, validações
3. **Avançado:** HashMap, algoritmos de grafos, análise de complexidade
4. **Expert:** Estruturas de dados complexas, otimização, design patterns

---

## 🎬 Apresentação em Vídeo

O projeto inclui demonstração completa em vídeo explicando:

- Implementação de cada funcionalidade
- Conceitos de estruturas de dados aplicados
- Algoritmos utilizados e sua complexidade
- Princípios de programação orientada a objetos
- Fluxo completo de uso do sistema

---

**Desenvolvido com 💜 para demonstrar a aplicação prática de Algoritmos e Estruturas de Dados em Java**

_Projeto Final - Algoritmos e Estrutura de Dados | Faculdade Unyleya_
