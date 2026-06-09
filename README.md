# Microsserviço de Autenticação 

Este é o microsserviço responsável por centralizar as regras de segurança, validação de credenciais e geração de Tokens JWT da arquitetura distribuída.

## Tecnologias Utilizadas
* **Java 21+**
* **Spring Boot 3.2+**
* **gRPC Server Spring Boot Starter** (net.devh)
* **Spring Cloud Netflix Eureka Client** (Service Discovery)
* **Protobuf** (Contrato de comunicação)
* **Lombok**

##  Atendimento aos Requisitos Técnicos (Arquitetura)

Para atender aos critérios exigidos no design do sistema distribuído, este componente foca na segurança e na performance interna:

### 1. Invocação Remota (RPC) - Comunicação Síncrona
* **Implementação gRPC:** Este projeto atua estritamente como o **Servidor gRPC** da arquitetura. Visando segurança e o princípio de Responsabilidade Única (SRP), ele **não expõe portas HTTP REST** para requisições de clientes externos. Todo o tráfego ocorre de forma interna, síncrona e em formato binário (HTTP/2) através da porta TCP `8083`.
* **Mecânica:** Ele aguarda invocações dos *stubs* clientes. Ao receber a requisição RPC, ele aplica a lógica criptográfica para validar a senha digitada contra a senha do banco e devolve uma resposta imediata contendo o Token.

### 2. Serviço de Nomes (Service Discovery)
* **Implementação Eureka:** O serviço atua como um *Eureka Client*. Ao iniciar, ele se registra dinamicamente no catálogo central para ser descoberto por outros microsserviços sem o uso de IPs engessados.
* **Engenharia de Roteamento:** Como o Spring Boot levanta uma porta HTTP padrão (`8082`) e a porta RPC separadamente (`8083`), foi injetado um metadado crítico na configuração (`eureka.instance.metadata-map.gRPC.port=8083`). Isso garante que o Eureka forneça a porta correta para os clientes gRPC, evitando falhas de protocolo na rede.

##  Arquitetura e Fluxo

O fluxo de dados segue os seguintes passos:
1. Recebe a requisição (`LoginRequest`) encapsulada em Protobuf contendo o e-mail, a senha em texto plano (digitada pelo cliente) e a hash da senha (oriunda do banco de dados).
2. O serviço realiza a validação lógica e de segurança dessas credenciais.
3. Após a validação, devolve uma resposta síncrona (`LoginResponse`) pelo mesmo canal gRPC, contendo a flag de sucesso, uma mensagem de status e o Token JWT gerado.

##  Como Executar o Projeto

1. Certifique-se de ter o Java e o Maven instalados.
2. **Pré-requisito:** O **Eureka Server** (`localhost:8761`) deve estar em execução na máquina antes da inicialização deste serviço.
3. Clone este repositório.
4. Abra o terminal na raiz do projeto e execute:
   `.\mvnw spring-boot:run`
5. A aplicação iniciará sua interface web/métricas na porta **8082**.
6. O servidor gRPC iniciará escutando conexões na porta TCP **8083**.

##  Contrato gRPC (`auth.proto`)

A comunicação utiliza o seguinte contrato Protobuf compartilhado para garantir tipagem forte:

```protobuf
service AuthService {
  rpc ValidarLogin (LoginRequest) returns (LoginResponse);
}

message LoginRequest {
  string email = 1;
  string senhaDigitada = 2;
  string senhaDoBanco = 3;
}