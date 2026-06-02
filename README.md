# Microsserviço de Autenticação 🔐

Este é o microsserviço responsável por centralizar as regras de segurança, validação de credenciais e geração de Tokens JWT da arquitetura distribuída.

## 🛠 Tecnologias Utilizadas
* **Java 21+**
* **Spring Boot 3.2+**
* **gRPC Server Spring Boot Starter** (net.devh)
* **Protobuf** (Contrato de comunicação)
* **Lombok**

## ⚙️ Arquitetura e Fluxo
Este serviço **não expõe portas HTTP REST** para o cliente final. Ele opera exclusivamente como um **Servidor gRPC** aguardando chamadas internas na rede.
Ele recebe a requisição (`LoginRequest`) contendo o e-mail, a senha digitada pelo cliente e a senha criptografada/salva no banco de dados. Após a validação lógica, ele devolve uma resposta síncrona (`LoginResponse`) contendo o status e o Token.

## 🚀 Como Executar o Projeto

1. Certifique-se de ter o Java e o Maven instalados.
2. Clone este repositório.
3. Abra o terminal na raiz do projeto e execute:
   `.\mvnw spring-boot:run`
4. O servidor gRPC iniciará escutando na porta TCP **8083**.
5. A aplicação iniciará na porta **8082**

## 📡 Contrato gRPC (`auth.proto`)

A comunicação utiliza o seguinte contrato Protobuf compartilhado:

```protobuf
service AuthService {
  rpc ValidarLogin (LoginRequest) returns (LoginResponse);
}

message LoginRequest {
  string email = 1;
  string senhaDigitada = 2;
  string senhaDoBanco = 3;
}