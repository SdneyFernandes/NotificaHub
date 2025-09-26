# Sistema de Detecção de Fraudes em Tempo Real com Kafka e Kubernetes

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green.svg)
![Kafka](https://img.shields.io/badge/Apache_Kafka-blue.svg)
![Docker](https://img.shields.io/badge/Docker-blue.svg)
![Kubernetes](https://img.shields.io/badge/Kubernetes-blue.svg)

## 📖 Visão Geral do Projeto

Este projeto implementa um sistema distribuído de detecção de fraudes em transações financeiras, simulando um desafio comum do mundo real no setor de fintechs e bancos digitais. A arquitetura é baseada em **microserviços**, **orientada a eventos** e totalmente **containerizada**, projetada para ser resiliente, escalável e de alta performance.

O sistema processa um fluxo contínuo de transações em tempo real, aplicando regras de negócio para identificar atividades suspeitas e publicando alertas para notificação imediata.

---

## ✨ Principais Funcionalidades e Conceitos Demonstrados

-   **Processamento de Eventos em Tempo Real:** Análise de transações com baixa latência utilizando Apache Kafka como plataforma de streaming.
-   **Arquitetura de Microserviços Desacoplada:** Os serviços são independentes, se comunicam de forma assíncrona e não possuem conhecimento direto uns dos outros, aumentando a resiliência e a manutenibilidade.
-   **Comunicação Assíncrona com Kafka:** Uso de múltiplos tópicos para gerenciar o fluxo de dados, desde a ingestão de transações até a publicação de alertas de fraude.
-   **Containerização com Docker:** Cada microserviço é empacotado em uma imagem Docker otimizada (usando multi-stage builds) para garantir a portabilidade e consistência do ambiente.
-   **Orquestração com Kubernetes:** A aplicação completa, incluindo a infraestrutura do Kafka, é implantada e gerenciada em um cluster Kubernetes, demonstrando habilidades de deploy e gerenciamento de aplicações cloud-native.

---

## 🏛️ Arquitetura do Sistema

O fluxo de dados é totalmente orientado a eventos, com o Kafka atuando como o sistema nervoso central da arquitetura.
1.  **Gerador de Transações:** Simula um feed de pagamentos, gerando transações aleatórias e publicando-as no tópico `transacoes`.
2.  **Processador de Fraudes:** Consome do tópico `transacoes`, aplica regras de negócio (ex: valor > 5000) e, se uma fraude é detectada, publica a transação no tópico `fraudes-detectadas`.
3.  **Serviço de Notificação:** Consome do tópico `fraudes-detectadas` e simula o envio de um alerta para o usuário final.

---

## 🛠️ Tech Stack

-   **Backend:** Java 21, Spring Boot 3
-   **Mensageria & Streaming:** Apache Kafka, Spring for Apache Kafka
-   **DevOps & Orquestração:** Docker, Docker Compose, Kubernetes (Minikube), Helm
-   **Build & Dependências:** Maven, Lombok, JavaFaker

---

## 🚀 Como Executar o Projeto

Existem duas maneiras de executar a aplicação completa: a forma simples com Docker Compose ou a forma avançada com Kubernetes.

### Pré-requisitos

-   Git
-   JDK 21+
-   Maven 3.8+
-   Docker Desktop
-   Minikube (para a opção Kubernetes)
-   Helm (para a opção Kubernetes)

### Opção A: Executar com Docker Compose (Modo Simples)

1.  **Clone o repositório:**
    ```bash
    git clone (https://github.com/SdneyFernandes/sistema-deteccao-fraudes-kafka.git)
    cd sistema-deteccao-fraudes-kafka
    ```

2.  **Suba a aplicação:**
    O comando abaixo irá construir as imagens Docker para cada serviço e iniciar todos os containers.
    ```bash
    docker-compose up --build
    ```

3.  **Verifique os logs:**
    Você verá os logs de todos os serviços intercalados. Observe as mensagens do `gerador-transacoes` enviando, o `processador-fraudes` detectando fraudes e o `servico-notificacao` publicando os alertas.

4.  **Para parar a aplicação:**
    Pressione `Ctrl + C` no terminal e depois execute `docker-compose down`.

### Opção B: Executar com Kubernetes (Modo Avançado)

1.  **Clone o repositório** (se ainda não o fez).

2.  **Altere a configuração do Kafka:**
    Em cada um dos 3 projetos Java, altere o arquivo `src/main/resources/application.properties` de `spring.kafka.bootstrap-servers=localhost:9092` para `spring.kafka.bootstrap-servers=kafka:9092`.

3.  **Inicie seu cluster Minikube:**
    ```bash
    minikube start --memory=4g --cpus=2
    ```

4.  **Conecte seu terminal ao Docker do Minikube:**
    ```bash
    eval $(minikube -p minikube docker-env)
    ```

5.  **Construa as imagens Docker (dentro do Minikube):**
    ```bash
    # Gerador
    cd gerador-transacoes && docker build -t gerador-transacoes:1.0 . && cd ..
    # Processador
    cd processador-fraudes && docker build -t processador-fraudes:1.0 . && cd ..
    # Notificador
    cd servico-notificacao && docker build -t servico-notificacao:1.0 . && cd ..
    ```

6.  **Instale o Kafka no cluster:**
    ```bash
    helm repo add bitnami [https://charts.bitnami.com/bitnami](https://charts.bitnami.com/bitnami)
    helm install kafka bitnami/kafka
    ```

7.  **Implante seus microserviços:**
    ```bash
    kubectl apply -f k8s/
    ```

8.  **Verifique os logs:**
    Abra 3 terminais e espione cada serviço:
    ```bash
    # Terminal 1
    kubectl logs -f deployment/gerador-transacoes

    # Terminal 2
    kubectl logs -f deployment/processador-fraudes

    # Terminal 3
    kubectl logs -f deployment/servico-notificacao
    ```

---

## 📁 Estrutura do Projeto

```
.
├── gerador-transacoes/   # Microserviço que gera transações
│   ├── src/
│   └── Dockerfile
├── processador-fraudes/  # Microserviço que analisa as transações
│   ├── src/
│   └── Dockerfile
├── servico-notificacao/  # Microserviço que simula notificações
│   ├── src/
│   └── Dockerfile
├── k8s/                    # Manifestos de deploy para o Kubernetes
│   ├── gerador-deployment.yml
│   ├── processador-deployment.yml
│   └── notificacao-deployment.yml
├── docker-compose.yml      # Arquivo para orquestração com Docker Compose
└── README.md
```

---
