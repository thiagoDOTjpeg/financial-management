# App Backend
Este é o backend de um sistema gerencial financeiro utilizando Jakarta EE, Spring Boot, Spring Data JPA, e arquitetura em camadas. Este serviço provê funcionalidades robustas para gerenciamento de usuários, autenticação, papéis (roles), contas bancárias, cartões, transações, entre outros recursos financeiros.
## Tecnologias utilizadas
- **Java 23 (SDK)**
- **Jakarta EE** (com importações `jakarta.*`)
- **Spring Boot** (`spring-boot-starter-web`)
- **Spring Data JPA**
- **Spring MVC**
- **Hibernate**
- **HATEOAS**
- **JWT** (JSON Web Token) para autenticação e segurança
- **Swagger/OpenAPI** para documentação de API
- **Maven** para gerenciamento de dependências
- **Banco de Dados Relacional** (compatível com JPA)
- **Testes com JUnit**

## Estrutura do Projeto
``` 
backend/
├── src/
│   ├── main/
│   │   ├── java/br/com/gritti/app/
│   │   │   ├── application/      # Serviços de aplicação e DTOs
│   │   │   ├── domain/           # Domínio: modelos, enums, serviços de domínio, repositórios
│   │   │   ├── infra/            # Infraestrutura: configuração, entidades JPA, repositórios, segurança
│   │   │   ├── interfaces/       # Interfaces: controllers REST, documentação, tratamento global de exceções
│   │   │   └── shared/           # Componentes compartilhados/utilitários
│   │   └── resources/            # Configurações e resources
│   ├── test/
│   │   ├── java/...              # Testes unitários e de integração
│   │   └── resources/
├── pom.xml                       # Arquivo Maven de dependências
└── README.md
```
## Funcionalidades principais
- **Cadastro, edição, listagem, inativação e exclusão de usuários**
- **Atribuição de papéis a usuários**
- **Gerenciamento de contas bancárias, cartões, transações, invoices, categorias**
- **Autenticação e autorização baseada em JWT**
- **Paginação e ordenação de usuários**
- **Tratamento global de exceções**
- **Auditoria via JPA Auditing**
- **Documentação automática de API com Swagger/OpenAPI**

## Como rodar o projeto
1. **Pré-requisitos**
    - Java SDK 23
    - Maven 3.8+
    - Banco de dados compatível (Ex: PostgreSQL ou MySQL)

2. **Configuração do banco de dados**
    - Configure as propriedades do banco no arquivo `application.properties` ou `application.yml`

3. **Rodando localmente**
``` bash
   mvn spring-boot:run
```
1. **Acessando a documentação da API**
    - Após rodar, acesse: `http://localhost:8080/swagger-ui.html` ou `http://localhost:8080/swagger-ui/index.html`

## Testes
Execute os testes automatizados:
``` bash
mvn test
```
## Padrões adotados
- **DTO (Data Transfer Object):** Separação entre entidades de domínio e informações trafegadas na API
- **Arquitetura em camadas**: Application, Domain, Infra, Interfaces
- **Clean Code & SOLID**
- **Controle de versões via Git**

## Contribuição
1. Fork este repositório
2. Crie sua branch (`git checkout -b feature/minha-feature`)
3. Commit suas alterações (`git commit -am 'Adiciona nova feature'`)
4. Push na branch (`git push origin feature/minha-feature`)
5. Abra um Pull Request
