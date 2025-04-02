# Financial Management

Um sistema abrangente de gerenciamento financeiro que fornece autenticação de usuários, gerenciamento de contas, rastreamento de transações, faturamento e recursos de categorização.

## Visão Geral do Projeto

Financial Management é uma aplicação baseada em Java projetada para ajudar os usuários a gerenciar suas finanças de forma eficaz. O sistema oferece recursos robustos para rastrear transações, gerenciar contas bancárias, categorizar despesas, lidar com faturas e gerenciar pagamentos com cartão de crédito, incluindo parcelamentos.

## Arquitetura

Este projeto segue os princípios de Domain-Driven Design (DDD) e padrões de Clean Architecture para garantir a separação de responsabilidades e manutenibilidade.

### Estrutura do Projeto

```
java/br/com/gritti/app/
├── application/
│   ├── dto/                # Objetos de Transferência de Dados
│   │   └── UserCreateDTO
│   ├── mapper/             # Mapeadores de objetos
│   │   └── UserMapper
│   └── service/            # Serviços de aplicação
│       ├── AuthApplicationService
│       └── UserApplicationService
├── domain/
│   ├── enums/              # Enumerações de domínio
│   │   ├── AccountStatus
│   │   ├── CategoryType
│   │   ├── InvoiceStatus
│   │   └── PaymentType
│   ├── model/              # Modelos de domínio
│   │   ├── BankAccount
│   │   ├── Card
│   │   ├── Category
│   │   ├── Installment
│   │   ├── Invoice
│   │   ├── Role
│   │   ├── Transaction
│   │   └── User
│   ├── repository/         # Interfaces de repositório
│   │   └── UserRepository
│   ├── service/            # Serviços de domínio
│   │   ├── AuthDomainService
│   │   └── UserDomainService
│   └── valueobject/        # Objetos de valor
│       ├── AccountCredentials
│       ├── Email
│       └── Token
├── infra/
│   ├── config/             # Classes de configuração
│   │   ├── JpaConfig
│   │   ├── SecurityConfig
│   │   └── WebConfig
│   ├── entity/             # Entidades de persistência
│   │   └── Auditable
│   ├── persistence/        # Implementação de persistência
│   │   └── JpaUserRepository
│   ├── repository/         # Implementações de repositório
│   │   └── UserRepositoryImpl
│   └── security/           # Componentes de segurança
│       ├── jwt/
│       │   ├── TokenFilter
│       │   └── TokenProvider
│       └── AuditorAwareImpl
├── interfaces/
│   ├── controller/         # Controladores de API
│   │   ├── AuthController
│   │   └── UserController
│   └── handler/            # Manipuladores de exceção
│       └── CustomizedResponseEntityExceptionHandler
└── shared/                 # Componentes compartilhados
    └── exceptions/         # Classes de exceção
```

## Design do Banco de Dados

O sistema utiliza um banco de dados relacional com as seguintes tabelas principais:

- **users**: Contas de usuário com informações de autenticação
- **roles**: Papéis de usuário para controle de acesso
- **user_permissions**: Mapeamento de usuários para seus papéis
- **bank_account**: Informações de conta bancária do usuário
- **cards**: Informações de cartão de crédito/débito
- **categories**: Categorias de despesas e receitas
- **transactions**: Transações financeiras
- **invoices**: Faturas de cobrança
- **installments**: Parcelas de pagamento
- **transactions_installment**: Mapeamento de transações para parcelas
- **installments_invoices**: Mapeamento de parcelas para faturas

O esquema do banco de dados inclui campos abrangentes de auditoria (created_at, created_by, updated_at, updated_by) em todas as tabelas para rastreabilidade.

## Funcionalidades

- **Gerenciamento de Usuários**: Registro de usuário, autenticação e autorização
- **Gerenciamento de Contas**: Criar e gerenciar contas bancárias
- **Rastreamento de Transações**: Registrar e categorizar transações financeiras
- **Categorização de Despesas**: Organizar despesas por categorias personalizadas
- **Faturamento**: Gerar e gerenciar faturas
- **Pagamentos Parcelados**: Acompanhar pagamentos ao longo do tempo
- **Gerenciamento de Cartão de Crédito**: Acompanhar despesas e limites de cartão de crédito
- **Segurança**: Autenticação baseada em JWT e controle de acesso baseado em papéis

## Tecnologias

<table>
  <tr>
    <td>Backend:</td>
    <td>
      <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
      <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring">
      <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white" alt="Spring Security">
      <img src="https://img.shields.io/badge/Flyway-CC0200.svg?style=for-the-badge&logo=Flyway&logoColor=white" alt="Flyway">
      <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white" alt="Hibernate">
    </td>
  </tr>
  <tr>
    <td>Banco de Dados:</td>
    <td><img src="https://img.shields.io/badge/PostgreSQL-000?style=for-the-badge&logo=postgresql" alt="PostgreSQL"></td>
  </tr>
  <tr>
    <td>API:</td>
    <td>RESTful</td>
  </tr>
  <tr>
    <td>Frontend:</td>
    <td><img src="https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D" alt="Vue"></td>
  </tr>
</table>
  
## Começando

### Pré-requisitos

- Java JDK 11 ou superior
- PostgreSQL (ou banco de dados de sua escolha)
- Maven ou Gradle

### Instalação

1. Clone o repositório
2. Configure sua conexão de banco de dados nas propriedades da aplicação
3. Execute a aplicação

```bash
./mvnw spring-boot:run
```

## Endpoints da API

O sistema expõe vários endpoints RESTful, principalmente:

- `/auth`: Endpoints de autenticação
- `/users`: Gerenciamento de usuários
- `/accounts`: Gerenciamento de contas bancárias
- `/transactions`: Operações de transação
- `/categories`: Gerenciamento de categorias
- `/invoices`: Manipulação de faturas

## Migração de Banco de Dados

O projeto utiliza Flyway para versionamento e migração do banco de dados. Os scripts de migração são executados automaticamente quando a aplicação é iniciada, garantindo a consistência do esquema do banco de dados em todos os ambientes.

## Segurança

A segurança é implementada usando JWT (JSON Web Tokens) para autenticação. O sistema inclui:

- Autenticação baseada em token
- Controle de acesso baseado em papéis
- Criptografia de senha
- Gerenciamento de sessão

## Contribuindo

Por favor, leia as diretrizes de contribuição antes de enviar pull requests.

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).
