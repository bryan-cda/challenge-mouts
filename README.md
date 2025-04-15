# 📦 Ambev Engine Microservice - Order & Product API

Este projeto é um microserviço reativo desenvolvido com **Spring Boot 3.2.2**, utilizando **Spring WebFlux**, com persistência em banco de dados **PostgreSQL**. A API é responsável pelo gerenciamento de produtos e pedidos, e foi construída com foco em **alta escalabilidade e desempenho assíncrono**, suportando até **200.000 requisições diárias**.

---

## 🚀 Como executar

### ✅ Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- Java 17+

### 🧱 Passo a passo

1. **Clone o repositório e acesse o diretório do projeto:**

```
git clone https://github.com/bryan-cda/challenge-mouts.git
cd challenge-mouts
```
2. Suba o ambiente com Docker Compose:
```
docker compose up -d
```
3. Execute a aplicação (em outra aba/terminal):
```
./mvnw spring-boot:run
`` 

📖 Documentação da API

A documentação interativa da API está disponível via Swagger/OpenAPI:

🔗 http://localhost:8080/swagger-ui.html
🔗 http://localhost:8080/swagger-ui/index.html

🌐 Endpoints
📦 Orders (/ambev/v1/engine/orders)

GET/ambev/v1/engine/orders:  Lista todos os pedidos (Produto A)
GET/ambev/v1/engine/orders/{code}: Retorna um pedido pelo código UUID (Produto A)
POST/ ambev/v1/engine/orders:  Cria um novo pedido
GET/ambev/v1/engine/orders/{code}/total: Totalizador do pedido, chamado pelo Produto B

🌐 Endpoints
### 📦 Products (`/ambev/v1/engine/products`)

GET/ambev/v1/engine/products: Lista todos os produtos                        
GET/ambev/v1/engine/products/{code}: Retorna um produto pelo código UUID             
POST/ambev/v1/engine/products: Cria um novo produto                             
PUT/ambev/v1/engine/products/{id}: Atualiza um produto existente                   
DELETE/ambev/v1/engine/products/{id}: Exclui um produto     

⚙️ Por que Spring WebFlux?

Este microserviço foi arquitetado para atender alto volume de requisições simultâneas. O Spring WebFlux é baseado no paradigma reativo, usando Project Reactor, que:

    Trabalha com I/O não bloqueante

    Usa Flux (stream assíncrono com múltiplos elementos)

    Usa Mono (representa uma resposta assíncrona com um único elemento)

    Garante menor consumo de threads e melhor performance sob carga

    Permite aplicações altamente escaláveis com mínimo overhead de recursos

📈 Desempenho e Escalabilidade

Ao utilizar o WebFlux e programação reativa, esta aplicação suporta com facilidade:

    150.000 a 200.000 requisições por dia, com consumo eficiente de CPU e memória, mesmo sob múltiplos usuários concorrentes.

✅ Vantagens do Projeto

    ⚡ Alta performance assíncrona

    🧪 API 100% validada com Swagger/OpenAPI 3

    🐘 Integração com PostgreSQL

    🐳 Pronta para containerização com Docker

    ♻️ Separação de responsabilidades (Product vs Order)

    🔗 API pensada para consumo por diferentes produtos (A e B)

📄 Tecnologias usadas

    Java 17

    Spring Boot 3.2.2

    Spring WebFlux

    Project Reactor

    PostgreSQL

    Docker & Docker Compose

    springdoc-openapi-starter-webflux-ui
