# Sistema de Chat Java

Um sistema de chat multi-sala desenvolvido em Java com sockets, suportando múltiplos usuários simultâneos e diferentes níveis de permissão.

## Sobre o Projeto

Este projeto foi desenvolvido como atividade prática da disciplina de Linguagem de Programação II, implementando um sistema de chat em tempo real com arquitetura cliente-servidor utilizando sockets TCP.

### Funcionalidades Principais

- Múltiplas salas de chat simultâneas
- Diferentes tipos de usuário (Comum e Administrador)
- Mensagens em tempo real dentro das salas
- Comandos administrativos para gerenciamento
- Interface por linha de comando intuitiva
- Conexões simultâneas ilimitadas

## Arquitetura

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Cliente 1     │    │   Cliente 2     │    │   Cliente N     │
│                 │    │                 │    │                 │
└─────────┬───────┘    └─────────┬───────┘    └─────────┬───────┘
          │                      │                      │
          └──────────────────────┼──────────────────────┘
                                 │
          ┌─────────────────────────────────────────────┐
          │              SERVIDOR                       │
          │  ┌─────────────┐ ┌─────────────┐           │
          │  │   Salas     │ │    Auth     │           │
          │  │  Manager    │ │  Manager    │           │
          │  └─────────────┘ └─────────────┘           │
          └─────────────────────────────────────────────┘
```

## Como Executar

### Pré-requisitos
- Java 11 ou superior
- Git (para clonar o repositório)

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/sistema-chat-java.git
cd sistema-chat-java
```

### 2. Compile o projeto
```bash
# Compilar todas as classes
javac -d bin src/main/java/br/edu/chatsystem/**/*.java

# Ou usando o script de build (se disponível)
./build.sh
```

### 3. Execute o servidor
```bash
java -cp bin br.edu.chatsystem.server.ChatServer
```

### 4. Execute o(s) cliente(s)
```bash
# Em outro terminal
java -cp bin br.edu.chatsystem.client.ChatClient
```

## Comandos Disponíveis

### Comandos Básicos (Todos os usuários)
| Comando | Descrição | Exemplo |
|---------|-----------|---------|
| `/login <nome>` | Fazer login no sistema | `/login João` |
| `/salas` | Listar salas disponíveis | `/salas` |
| `/entrar <sala>` | Entrar em uma sala | `/entrar geral` |
| `/sair` | Sair da sala atual | `/sair` |
| `/msg <mensagem>` | Enviar mensagem na sala | `/msg Olá pessoal!` |
| `/sairServidor` | Desconectar do servidor | `/sairServidor` |

### Comandos Administrativos
| Comando | Descrição | Exemplo |
|---------|-----------|---------|
| `/criar <sala>` | Criar nova sala | `/criar programacao` |
| `/expulsar <usuario>` | Expulsar usuário da sala | `/expulsar João` |
| `/encerrar <sala>` | Encerrar uma sala | `/encerrar geral` |

## Estrutura do Projeto

```
src/main/java/br/edu/chatsystem/
├── server/          # Servidor e gerenciamento de conexões
│   ├── ChatServer.java
│   ├── ClientHandler.java
│   └── ConnectionManager.java
├── room/            # Gerenciamento de salas
│   ├── Room.java
│   ├── RoomManager.java
│   └── MessageBroadcaster.java
├── auth/            # Autenticação e permissões
│   ├── User.java
│   ├── AuthenticationManager.java
│   └── PermissionManager.java
├── client/          # Cliente e interface
│   ├── ChatClient.java
│   ├── ClientUI.java
│   └── CommandParser.java
├── common/          # Classes compartilhadas
│   ├── Message.java
│   ├── Protocol.java
│   └── Command.java
└── util/           # Utilitários
    ├── DateUtils.java
    └── ValidationUtils.java
```

## Equipe de Desenvolvimento

| Membro | Responsabilidade | Módulos |
|--------|------------------|---------|
| **Membro 1** | Arquitetura do Servidor | `server/`, conexões e threads |
| **Membro 2** | Gerenciamento de Salas | `room/`, mensagens e broadcast |
| **Membro 3** | Autenticação e Segurança | `auth/`, permissões e comandos admin |
| **Membro 4** | Interface do Cliente | `client/`, UI e comandos |

## Configuração

### Arquivo `server.properties`
```properties
# Configurações do Servidor
server.port=12345
server.max.connections=100
server.timeout=30000
server.log.level=INFO
```

### Arquivo `client.properties`
```properties
# Configurações do Cliente
server.host=localhost
server.port=12345
client.timeout=10000
client.auto.reconnect=true
```

## Exemplos de Uso

### Cenário 1: Usuário Comum
```bash
Cliente> /login Maria
Servidor> Login realizado com sucesso! Bem-vinda, Maria.

Cliente> /salas
Servidor> Salas disponíveis:
Servidor> - geral (3 usuários)
Servidor> - programacao (1 usuário)

Cliente> /entrar geral
Servidor> Você entrou na sala 'geral'.

Cliente> /msg Oi pessoal, como vocês estão?
[geral] Maria: Oi pessoal, como vocês estão?
```

### Cenário 2: Administrador
```bash
Cliente> /login Admin
Servidor> Login como administrador realizado com sucesso!

Cliente> /criar javascript
Servidor> Sala 'javascript' criada com sucesso.

Cliente> /entrar javascript
Servidor> Você entrou na sala 'javascript'.

Cliente> /expulsar UsuarioProblematico
Servidor> Usuário 'UsuarioProblematico' foi expulso da sala.
```

## Testes

### Executar testes unitários
```bash
# Compilar testes
javac -cp bin:lib/junit-4.13.2.jar -d test-bin src/test/java/**/*.java

# Executar testes
java -cp bin:test-bin:lib/junit-4.13.2.jar org.junit.runner.JUnitCore AllTests
```

### Cenários de teste implementados
- Conexão múltipla de clientes
- Criação e remoção de salas
- Envio de mensagens entre usuários
- Validação de permissões administrativas
- Tratamento de desconexões inesperadas

## Tratamento de Erros

O sistema implementa tratamento robusto para:
- **Desconexões inesperadas** de clientes
- **Comandos inválidos** ou mal formatados
- **Salas inexistentes** ou já ocupadas
- **Usuários não autorizados** para comandos admin
- **Problemas de rede** e timeouts

## Licença

Este projeto foi desenvolvido para fins acadêmicos como parte da disciplina de Linguagem de Programação II.

## Contato

Para dúvidas sobre o projeto, entre em contato com a equipe através dos commits do Git ou abra uma issue neste repositório.

---

**Desenvolvido com Java**

> Data de entrega: 02/07/2025  
> Disciplina: Linguagem de Programação II  
> Professor: Tiago Bonini
