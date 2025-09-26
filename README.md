# NotificaHub - Microsserviço de Gestão de Notificações Unificadas

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green.svg)
![Spring Security](https://img.shields.io/badge/Spring_Security-6.x-blue.svg)
![JWT](https://img.shields.io/badge/Security-JWT-purple.svg)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue.svg)
![Docker](https://img.shields.io/badge/Docker-blue.svg)

## 📖 Visão Geral do Projeto

NotificaHub é um microserviço centralizador construído em Java e Spring Boot, projetado para resolver um problema comum em arquiteturas de software modernas: a gestão de notificações para usuários. Em vez de ter lógicas de envio de e-mail, SMS ou push espalhadas por vários sistemas, o NotificaHub oferece uma API única, segura e resiliente para lidar com todas as comunicações.

O projeto demonstra a construção de um serviço de backend robusto, desde a modelagem dos dados e segurança da API até a integração com serviços externos e a **automação de tarefas agendadas**.

---

## 🧠 O Pensamento por Trás da Construção: Decisões de Arquitetura

Este projeto não foi apenas sobre escrever código, mas sobre tomar decisões de arquitetura que refletem as melhores práticas do mercado.

* **Por que um Microserviço Dedicado?**
    > Seguindo o Princípio da Responsabilidade Única (SRP), isolar a lógica de notificações em um serviço dedicado o torna reutilizável, fácil de manter e de escalar de forma independente. Qualquer outro serviço na empresa (pagamentos, logística, etc.) pode consumi-lo sem precisar conhecer os detalhes de implementação.

* **Por que Spring Security com JWT?**
    > Em um ecossistema de microserviços, a segurança precisa ser *stateless*. O JWT (JSON Web Token) é o padrão da indústria para isso. A implementação com Spring Security garante que a API seja protegida, onde cada requisição carrega seu próprio "crachá" de autorização, sem a necessidade de o servidor guardar o estado da sessão.

* **Por que OpenFeign para Integrações Externas?**
    > Para se comunicar com APIs externas (como o SendGrid para e-mails), em vez de usar um `RestTemplate` tradicional, optei pelo OpenFeign. Ele permite a criação de clientes REST de forma declarativa, com interfaces Java simples. Isso torna o código de integração muito mais limpo, legível, testável e menos propenso a erros.

* **Por que PostgreSQL (Relacional)?**
    > Os dados de notificações (logs, agendamentos, preferências de usuário) são altamente estruturados e possuem relacionamentos claros. Um banco de dados relacional como o PostgreSQL garante a integridade e a consistência transacional (ACID), que são cruciais para um serviço de missão crítica como este.

* **Por que uma Camada de Testes Robusta?**
    > Um serviço centralizador como o NotificaHub não pode falhar. Por isso, o projeto foi desenvolvido com uma forte ênfase em testes automatizados (JUnit 5 e Mockito), cobrindo tanto a lógica de negócio (testes unitários) quanto os fluxos da API (testes de integração), garantindo que novas funcionalidades não quebrem o comportamento existente.

---

## 🏛️ Arquitetura e Fluxo de Dados

O diagrama abaixo ilustra os dois principais fluxos da aplicação: o envio imediato e o agendamento de notificações.

```mermaid
graph TD
    subgraph "Atores Externos"
        A[Cliente da API com Token JWT]
        Scheduler["⏰ Tarefa Agendada (Scheduler)"]
    end

    subgraph "NotificaHub Microservice"
        B[API REST <br> (Controller)]
        C[Lógica de Negócio <br> (Service)]
        D[Persistência de Dados <br> (Repository)]
        F[Cliente Feign <br> (Integração)]
    end

    subgraph "Infraestrutura & Serviços Externos"
        E[Banco de Dados <br> (PostgreSQL / H2)]
        G[API Externa <br> (Ex: SendGrid)]
    end

    %% Fluxo de Envio Imediato
    A -- 1. Requisição /enviar --> B
    B -- 2. Chama serviço --> C
    C -- 3. Chama cliente Feign --> F
    F -- 4. Envia E-mail --> G
    C -- 5. Salva Log --> D
    D -- 6. Grava no Banco --> E

    %% Fluxo de Agendamento
    A -- "1. Requisição /agendar" --> B
    B -- "2. Chama serviço para agendar" --> C
    C -- "3. Salva Agendamento (Status: AGUARDANDO)" --> D

    Scheduler -- "A cada minuto" --> C
    C -- "Verifica agendamentos" --> D
    D -- "Retorna agendamentos pendentes" --> C
```

---

## 🛠️ Tech Stack

-   **Linguagem & Framework Principal:** Java 21, Spring Boot 3
-   **Segurança:** Spring Security 6, JSON Web Token (JWT)
-   **Banco de Dados:** Spring Data JPA / Hibernate, PostgreSQL (em Docker), H2 (para desenvolvimento/testes)
-   **Integração:** Spring Cloud OpenFeign
-   **Tarefas Agendadas:** Spring Scheduler (`@Scheduled`)
-   **Testes:** JUnit 5, Mockito, MockMvc, JaCoCo
-   **DevOps:** Docker & Docker Compose
-   **Build & Dependências:** Maven, Lombok

---

## 🚀 Como Executar e Testar

### Pré-requisitos
-   Git
-   JDK 21+
-   Maven 3.8+
-   Docker Desktop

### 1. Configuração Inicial

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/SdneyFernandes/NotificaHub.git](https://github.com/SdneyFernandes/NotificaHub.git)
    cd NotificaHub
    ```

2.  **Configure a API do SendGrid:**
    * Crie uma conta gratuita no [SendGrid](https://sendgrid.com/) e uma API Key.
    * Verifique um e-mail remetente ("Single Sender Verification").
    * Na pasta `src/main/resources/`, crie um arquivo `application-local.properties`.
    * Adicione suas chaves secretas a este arquivo:
        ```properties
        sendgrid.api-key=SUA_CHAVE_DE_API_DO_SENDGRID_AQUI
        jwt.secret.key=SUA_CHAVE_SECRETA_BASE64_AQUI
        ```
    * No arquivo `application.properties`, garanta que a linha `spring.profiles.active=local` está presente.

### 2. Executando a Aplicação
Você pode rodar a aplicação com um banco de dados PostgreSQL (via Docker) ou um H2 em memória.

* **Para rodar com PostgreSQL (Modo "Produção Local"):**
    1.  Inicie o banco de dados: `docker-compose up -d`
    2.  No `application.properties`, garanta que as linhas de configuração do PostgreSQL estão ativas.
    3.  Inicie a aplicação: `mvn spring-boot:run`

* **Para rodar com H2 (Modo de Desenvolvimento Rápido):**
    1.  No `application.properties`, comente as linhas do PostgreSQL e descomente as do H2.
    2.  Inicie a aplicação: `mvn spring-boot:run`
    3.  Acesse o console do H2 em: `http://localhost:8080/h2-console`

### 3. Testando a API com Postman

1.  **Obtenha um Token de Acesso:**
    * Faça uma requisição `POST` para `http://localhost:8080/api/auth/login`
    * `Body` (raw/JSON):
        ```json
        {
          "username": "user",
          "password": "password"
        }
        ```
    * Copie o `token` da resposta.

2.  **Envie uma Notificação Imediata:**
    * Faça uma requisição `POST` para `http://localhost:8080/api/notificacoes/enviar`
    * **Authorization:** Na aba `Authorization`, tipo `Bearer Token`, cole o token.
    * `Body` (raw/JSON):
        ```json
        {
          "destinatario": "seu-email-de-verdade@exemplo.com",
          "mensagem": "Teste da API NotificaHub!",
          "canal": "EMAIL"
        }
        ```

3.  **Agende uma Notificação:**
    * Faça uma requisição `POST` para `http://localhost:8080/api/notificacoes/agendar`
    * Use o mesmo **Bearer Token** na aba `Authorization`.
    * `Body` (raw/JSON), com uma data e hora no futuro:
        ```json
        {
          "destinatario": "seu-email-de-verdade@exemplo.com",
          "mensagem": "Esta é uma mensagem agendada!",
          "canal": "EMAIL",
          "dataAgendamento": "2025-09-26T14:30:00"
        }
        ```
    * Aguarde o horário agendado e verifique o recebimento do e-mail.

---

## 👤 Autor

**Sidney Fernandes**

-   [LinkedIn](https://www.linkedin.com/in/SEU-PERFIL-DO-LINKEDIN/)
-   [GitHub](https://github.com/SdneyFernandes)
