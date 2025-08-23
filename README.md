# API de Gestão de Produtos - Desafio Java

## 1. Visão Geral

Este documento fornece uma descrição detalhada da API RESTful de Gestão de Produtos. A API permite realizar um conjunto completo de operações CRUD (Criar, Ler, Atualizar, Deletar) num catálogo de produtos, com funcionalidades avançadas como filtragem, paginação e geração de relatórios.

A aplicação foi construída utilizando:

- **Linguagem:** Java  
- **Framework Web:** JAX-RS (Jersey)  
- **Persistência:** JPA (Hibernate)  
- **Banco de Dados:** PostgreSQL  
- **Servidor:** Grizzly (embutido)  

## 2. Arquitetura

A aplicação segue uma arquitetura em camadas para garantir a separação de responsabilidades, manutenibilidade e escalabilidade:

- **resource:** Camada de API. Responsável por expor os endpoints HTTP, receber requisições, validar dados de entrada (DTOs) e orquestrar as respostas.  
- **dao (Data Access Object):** Camada de persistência. Encapsula toda a lógica de comunicação com o banco de dados através do JPA/Hibernate.  
- **model:** Contém as classes que representam os dados, incluindo as entidades JPA (`Product`) e os Data Transfer Objects (`ProductDTO`, `Report`, `ErrorResponseDTO`).  
- **exception:** Centraliza o tratamento de erros, com exceções customizadas e `ExceptionMappers` para converter erros em respostas HTTP padronizadas.  
- **util:** Classes utilitárias, como o `JPAUtil` para gerir o `EntityManager`.  

## 3. Como Executar o Projeto

**Pré-requisitos:**

- Java JDK 11 (ou superior)  
- Maven 3.6 (ou superior)  
- PostgreSQL 14 (ou superior)  

**Passos para Configuração:**

1. **Clone o Repositório:**

```bash
git clone https://github.com/EricRafael-Dev/Product-Manager_API
```

2. **Configure o Banco de Dados:**

* Crie um banco de dados no PostgreSQL chamado `desafio_db`.
* Abra o arquivo `src/main/resources/META-INF/persistence.xml` e configure seu `username` e `password`.

3. **Compile o Projeto:**

```bash
mvn clean install
```

4. **Execute a Aplicação:**

* Execute o método `main` da classe `rest.productsmanager.Servidor.java`.
* O servidor será iniciado em [http://localhost:8080/](http://localhost:8080/).

## 4. Modelos de Dados (Schemas)

### Product (Entidade)

Representa um produto no banco de dados.

| Campo    | Tipo       | Descrição                                |
| -------- | ---------- | ---------------------------------------- |
| id       | Long       | Identificador único gerado pelo sistema. |
| name     | String     | O nome do produto.                       |
| valor    | BigDecimal | O preço do produto.                      |
| quantity | Integer    | A quantidade em estoque.                 |

### ProductDTO (Entrada)

Usado para criar ou atualizar um produto.

| Campo    | Tipo       | Regras de Validação                    |
| -------- | ---------- | -------------------------------------- |
| name     | String     | Obrigatório, entre 2 e 100 caracteres. |
| valor    | BigDecimal | Obrigatório, não pode ser negativo.    |
| quantity | Integer    | Obrigatório, não pode ser negativo.    |

## 5. Endpoints da API

**URL base para todos os endpoints:** `http://localhost:8080/products`

### 5.1. Produtos

### Listar Produtos

Retorna uma lista de produtos com suporte a filtro por nome e paginação.

* **Endpoint:** `GET /products`
* **Parâmetros de Query:**

  * `name` (String, opcional): Filtra produtos pelo nome exato.
  * `length` (Integer, opcional, padrão: 20): Define o número de itens por página.
  * `page` (Integer, opcional, padrão: 0): Define a página a ser retornada.

**Exemplo de Resposta de Sucesso (200 OK):**

```json
[
    {
        "id": 1,
        "name": "Teclado Mecânico",
        "valor": 350.50,
        "quantity": 50
    },
    {
        "id": 2,
        "name": "Mouse Gamer",
        "valor": 199.99,
        "quantity": 75
    }
]
```

### Buscar Produto por ID

Recupera um único produto pelo seu ID.

* **Endpoint:** `GET /products/{id}`

**Exemplo de Resposta de Sucesso (200 OK):**

```json
{
    "id": 1,
    "name": "Teclado Mecânico",
    "valor": 350.50,
    "quantity": 50
}
```

### Criar um Novo Produto

Adiciona um novo produto ao catálogo.

* **Endpoint:** `POST /products`

**Exemplo de Corpo da Requisição (ProductDTO):**

```json
{
    "name": "Monitor Ultrawide 29 polegadas",
    "valor": 1250.00,
    "quantity": 30
}
```

**Exemplo de Resposta de Sucesso (201 Created):**

```json
{
    "id": 3,
    "name": "Monitor Ultrawide 29 polegadas",
    "valor": 1250.00,
    "quantity": 30
}
```

### Atualizar um Produto

Atualiza os dados de um produto existente.

* **Endpoint:** `PUT /products/{id}`

**Exemplo de Corpo da Requisição (ProductDTO):**

```json
{
    "name": "Monitor Ultrawide 29 polegadas (Modelo Novo)",
    "valor": 1300.00,
    "quantity": 25
}
```

**Exemplo de Resposta de Sucesso (200 OK):**

```json
{
    "id": 3,
    "name": "Monitor Ultrawide 29 polegadas (Modelo Novo)",
    "valor": 1300.00,
    "quantity": 25
}
```

### Deletar um Produto

Remove um produto do catálogo.

* **Endpoint:** `DELETE /products/{id}`

**Resposta de Sucesso (204 No Content):**
Resposta vazia, indicando sucesso.

### 5.2. Relatórios

### Obter Relatório de Estoque

Retorna um relatório consolidado com dados sobre o catálogo.

* **Endpoint:** `GET /products/report`

**Exemplo de Resposta de Sucesso (200 OK):**

```json
{
  "cheapestProduct": [
    {
      "id": 38,
      "name": "Pelicula de Celular",
      "quantity": 50,
      "valor": 5
    }
  ],
  "expensiveProduct": [
    {
      "id": 37,
      "name": "Notebook Gamer de Ultima Geracao",
      "quantity": 5,
      "valor": 9900
    }
  ],
  "maxPrice": 554453,
  "totalQuantity": 24
}
```

## 6. Padrão de Resposta de Erro

Todas as respostas de erro da API seguem um formato JSON padronizado para facilitar o tratamento pelo cliente.

**Exemplo de Erro 404 (Não Encontrado):**

```json
{
  "message": "Produto com ID 28 não foi encontrado.",
  "status": 404,
  "timestamp": "2025-08-23T01:16:05.522"
}
```

**Exemplo de Erro 400 (Requisição Inválida / Validação):**

```json
{
  "details": {
    "entry": [
      {
        "key": "name",
        "value": "'name' obrigatorio."
      },
      {
        "key": "valor",
        "value": "'valor' nao pode ser negativo."
      }
    ]
  },
  "message": "Erro de validação nos dados de entrada.",
  "status": 400,
  "timestamp": "2025-08-23T01:15:44.591"
}
```
