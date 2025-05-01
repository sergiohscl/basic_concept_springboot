# Basic Concept Spring Boot

Este projeto demonstra uma aplicação Spring Boot com **DTOs** na versão 1 (v1), organizados na estrutura:

## Estrutura do projeto

src/ └── main/ └── java/ └── com/sergiohscl/basic_concept_springboot/ ├── data/ │ └── dto/ │ └── v1/ │ ├── ProductDTO.java │ └── UserDTO.java ├── model/ │ ├── Product.java │ └── User.java ├── repository/ ├── service/ └── controller/

### Esse layout facilita:

📦 Manutenção – cada camada tem sua pasta, o que melhora a navegabilidade.

🔒 Segurança – é óbvio onde omitir campos sensíveis (no DTO) e onde está a lógica de persistência.

🔄 Testabilidade – facilita escrever testes unitários isolados por camada.

🛠️ Evolução – você pode adicionar novos DTOs (por ex., ProdutoResumoDTO) sem bagunçar as entidades nem os controllers existentes.

## Por que usar DTOs?

1. **Separação de responsabilidades**  
   - **Entidades (DATA)** mapeiam as tabelas do banco e contêm toda a lógica de persistência.  
   - **DTOs (Data Transfer Objects)** são objetos simples usados para transferir dados entre camadas (Controller ⇄ Service ⇄ Client), sem expor diretamente as entidades JPA.

2. **Controle sobre a API**  
   - Você escolhe exatamente que campos serão expostos ao cliente, evitando vazamento de informações sensíveis que possam existir na entidade.  
   - Facilita validação e transformação de dados (ex.: converter formatos, mesclar campos, etc.).

3. **Versionamento da API**  
   - Ao colocar DTOs em pacotes `v1`, `v2` etc., fica simples manter múltiplas versões da API simultaneamente, sem quebrar contratos existentes.

4. **Desempenho e manutenibilidade**  
   - DTOs permitem carregar apenas os campos necessários (projections), reduzindo payloads e melhorando performance.  
   - Isola mudanças internas da camada de persistência das APIs externas, tornando o código mais flexível e testável.

## Vantagens de usar Lombok nos DTOs
    - Elimina todo o boilerplate de getters, setters, construtores e métodos equals/hashCode.
    - Mantém o código limpo e focado apenas nos campos que importam.
    - Reduz a chance de erro humano ao editar manualmente esses métodos.
    - Funciona perfeitamente com HATEOAS e com seus controllers e services.

## Como funciona logging
    - logging.level.root=WARN faz com que apenas mensagens de nível WARN ou ERROR apareçam por padrão.
    - Você pode “rebaixar” (aumentar o nível) para INFO ou DEBUG em pacotes onde precise de mais detalhes, por exemplo com.sergiohscl. logging.level.com.sergiohscl=DEBUG
    - Desativamos o SQL do Hibernate tanto com spring.jpa.show-sql=false quanto com logging.level.org.hibernate.SQL=OFF para não poluir o console.
    - O logging.pattern.console torna a saída mais enxuta, exibindo apenas hora, nível, logger e mensagem. logging.pattern.console=%d{HH:mm:ss.SSS} %-5level [%logger{20}] - %msg%n


