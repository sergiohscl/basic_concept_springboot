# Basic Concept Spring Boot

Este projeto demonstra uma aplicação Spring Boot com **DTOs** na versão 1 (v1), organizados na estrutura:

## Estrutura do projeto

```
src/
└── main/
    └── java/
        └── com/sergiohscl/basic_concept_springboot/
            ├── data/
            │   └── dto/
            │       └── v1/
            │           ├── ProductDTO.java
            │           └── UserDTO.java
            ├── model/
            │   ├── Product.java
            │   └── User.java
            ├── repository/
            ├── service/
            └── controller/
```

### Esse layout facilita:

- 📦 **Manutenção** – cada camada tem sua pasta, melhorando a navegabilidade.
- 🔒 **Segurança** – delimita onde omitir campos sensíveis (no DTO) e onde reside a lógica de persistência.
- 🔄 **Testabilidade** – facilita testes unitários isolados por camada.
- 🛠️ **Evolução** – permite adicionar novos DTOs (ex.: `ProdutoResumoDTO`) sem impactar entidades ou controllers existentes.

## Por que usar DTOs?

1. **Separação de responsabilidades**
   - **Entidades (DATA)** mapeiam tabelas e contêm lógica de persistência.
   - **DTOs** são objetos simples para transferir dados entre camadas, sem expor diretamente entidades JPA.

2. **Controle sobre a API**
   - Define exatamente quais campos serão expostos ao cliente, evitando vazamento de dados sensíveis.
   - Facilita validação e transformação de dados (ex.: formatos, mesclagens).

3. **Versionamento da API**
   - Pacotes `v1`, `v2` etc. permitem manter múltiplas versões simultâneas sem quebrar contratos.

4. **Desempenho e manutenibilidade**
   - Carrega apenas campos necessários (projections), reduzindo payloads e melhorando performance.
   - Isola mudanças internas das entidades das APIs externas.

## Vantagens de usar Lombok nos DTOs

- Elimina boilerplate de getters, setters, construtores e métodos `equals`/`hashCode`.
- Mantém o código limpo e focado nos campos relevantes.
- Reduz chance de erro humano ao editar métodos manualmente.
- Funciona perfeitamente com HATEOAS, controllers e services.

## Como funciona o logging

- `logging.level.root=WARN` exibe apenas WARN ou ERROR por padrão.
- É possível aumentar para `INFO` ou `DEBUG` em pacotes específicos (ex.: `com.sergiohscl`).
- Desativamos o SQL do Hibernate com `spring.jpa.show-sql=false` e `logging.level.org.hibernate.SQL=OFF`.
- `logging.pattern.console` torna a saída concisa (horário, nível, logger e mensagem).

## Migrações de banco de dados com Flyway

Para versionar e aplicar evoluções de schema de forma segura e confiável, este projeto utiliza o **Flyway**:

- 📑 **Versionamento de scripts**: cada alteração de schema é numerada e armazenada em arquivos `V[versão]__Descrição.sql`.
- 🔄 **Histórico controlado**: o Flyway mantém a tabela `flyway_schema_history` para rastrear quais migrações já foram aplicadas.
- ⚙️ **Integração com Spring Boot**: autoconfiguração simplifica o setup; basta adicionar a dependência e configurar propriedades.

A utilização do Flyway garante que todas as instâncias da aplicação apliquem as mesmas migrações na ordem correta, evitando inconsistências de schema.

[Documentação oficial do Flyway](https://flywaydb.org/documentation)
