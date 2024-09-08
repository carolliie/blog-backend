# blog-backend
<img src="https://img.shields.io/badge/estado-em_desenvolvimento-blue"/> <img src="https://img.shields.io/badge/maven_version-4.0.0-green"/>

## Descrição

Este projeto implementa uma aplicação back-end de blog, utilizando a linguagem Java e seu framework web SpringBoot. Além disso, o projeto também contém o módulo do framework Spring, chamado Spring Security, que fornece serviços segurança para aplicações Java.

## Estrutura
Visão geral dos principais diretórios e arquivos do projeto.

1. `blog-backend-main/`
    - Diretório raíz do projeto que contém todos os arquivos e subdiretórios relacionados ao backend do blog.
2. `.mvn/`
    - Contém arquivos relacionados à configuração do Maven Wrapper, que permite construir o projeto sem precisar de uma instalação prévia dos sistema.
3. `src/`
    - Diretório principal do código fonte que contém o código da aplicação e os testes.
    - `main/`
        - Contém o código principal da aplicação
        - `java/`
            - Contém os arquivos de código-fonte Java do projeto.
            - `dev/carolliie/BlogServer/`
                - `controller/`: Contém as classes que gerenciam as requisições HTTP e controlam o fluxo de dados entre cliente e o servidor.
                - `entity/`: Contém as classes que representam as entidades do banco de dados.
                - `repository/`: Contém as interfaces que interagem com o banco de dados para realizar as operações CRUD nas entidades.
        - `resources/`:
            - Contém os arquivos de configuração e recursos que são utilizados pelo código Java, como arquivos de propriedades, templates e scripts SQL.
    - `test/`
        - Contém os testes unitários e de integração do projeto, organizados de forma similar ao código principal.
4. `.gitignore`
    - Arquivo que especifica quais arquivos ou diretórios não devem ser enviados à branch principal, ou seja, arquivos que devem ser ignorados pelo Git.
5. `README.MD
    - Documento de leitura inicial que fornece a visão geral do projeto, instruções de instalação e como utilizar.
6. `mvn` e `mvnw.cmd`
    - Scripts do Maven Wrapper que permitem rodar o Maven sem que ele esteja instalado globalmente na máquina. O `mvnw` é utilizado em ambientes Unix/Linux, enquanto `mvnw.cmd` é para Windows.
7. `pom.xml`
    - Arquivo em que se concentram as configurações do Maven, que definem as dependências do projeto, plugins e outras configurações necessárias para construção e gerenciamento do projeto.

## Instalação

Para configurar o ambiente de desenvolvimento local para este projeto, siga as instruções abaixo:

1. **Clone o Repositório**
   - Clone o projeto para o seu ambiente local utilizando o comando:
     ```bash
     git clone https://github.com/carolliie/blog-backend.git
     ```
   - Isso criará uma cópia do projeto no seu computador.

2. **Verifique a Instalação do Maven**
   - Certifique-se de que o Maven está instalado corretamente no seu sistema executando:
     ```bash
     mvn --version
     ```
   - Você deverá ver a versão do Maven (recomendado: 4.0.0) e a versão do JDK (17) listadas. Essas versões são essenciais para o funcionamento do projeto.

3. **Instale o JDK 17**
   - O projeto requer o JDK 17. Certifique-se de que ele está instalado e configurado corretamente em seu ambiente. Você pode verificar a instalação do JDK usando o comando:
     ```bash
     java -version
     ```
   - Caso o JDK 17 não esteja instalado, você pode baixá-lo do [site oficial da Oracle](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) ou utilizar um gerenciador de pacotes como o SDKMAN! para instalar.

4. **Configure as Variáveis de Ambiente**
   - Antes de iniciar o projeto, você precisa definir as seguintes variáveis de ambiente:
     - **`DB_PASSWORD`**: A senha do banco de dados PostgreSQL.
     - **`JWT_SECRET`**: O segredo usado para assinar os tokens JWT.
   - No Linux ou macOS, você pode definir essas variáveis de ambiente no terminal:
     ```bash
     export DB_PASSWORD=your_database_password
     export JWT_SECRET=your_jwt_secret
     ```
   - No Windows, você pode definir essas variáveis através das Propriedades do Sistema > Variáveis de Ambiente.

5. **Configuração do Banco de Dados: PostgreSQL**
   - O projeto utiliza PostgreSQL como banco de dados. Certifique-se de que o PostgreSQL está instalado e em execução na sua máquina.
   - Crie um banco de dados para o projeto:
     ```sql
     CREATE DATABASE blog_backend;
     ```
   - Atualize as configurações de conexão com o banco de dados no arquivo `application.properties` ou `application.yml`, localizado no diretório `src/main/resources`, com as credenciais corretas do PostgreSQL.

6. **Instalação das Dependências**
   - Navegue até o diretório raiz do projeto e execute o comando abaixo para baixar todas as dependências do projeto:
     ```bash
     mvn clean install
     ```
   - Isso compilará o projeto e instalará todas as dependências necessárias.

7. **Executando o Projeto**
   - Após configurar tudo, você pode iniciar o servidor Spring Boot utilizando o seguinte comando:
     ```bash
     mvn spring-boot:run
     ```
   - O servidor estará acessível em `http://localhost:8080` por padrão.

8. **Verificação da Instalação**
   - Acesse `http://localhost:8080` em seu navegador para verificar se o projeto está rodando corretamente.
   - Você pode utilizar ferramentas como o Postman ou cURL para interagir com as APIs disponibilizadas pelo backend.

Seguindo essas etapas, você terá a aplicação configurada e pronta para uso em seu ambiente local.

## Uso

### Autenticação e Autorização

1. **Autenticação do Usuário**:
   - Para realizar operações protegidas, como criar, editar ou deletar posts, o usuário precisa estar autenticado.
   - Use o endpoint `/api/auth/login` para autenticar um usuário e receber um token JWT:
     ```bash
     POST http://localhost:8080/api/auth/login
     ```
   - Corpo da requisição (JSON):
     ```json
     {
       "username": "seuUsuario",
       "password": "suaSenha"
     }
     ```
   - O token JWT recebido deve ser incluído no cabeçalho `Authorization` das requisições subsequentes:
     ```bash
     Authorization: Bearer <seuTokenJWT>
     ```

2. **Cadastro de Usuários**:
   - Caso o sistema necessite do cadastro de novos usuários, utilize o endpoint `/api/auth/register`:
     ```bash
     POST http://localhost:8080/api/auth/register
     ```
   - Corpo da requisição (JSON):
     ```json
     {
       "username": "novoUsuario",
       "email": "email@exemplo.com",
       "password": "novaSenha",
       "role": "admin" (or user)
     }
     ```

### Endpoints CRUD Principais

3. **Operações CRUD em Posts**:
   - **Criar Post**:
     ```bash
     POST http://localhost:8080/api/posts
     ```
     - Corpo da requisição (JSON):
       ```json
       {
         "name": "Título do Post",
         "content": "Conteúdo do Post",
         "img": "Imagem do Post",
         "tags": ["tag1", "tag2"]
       }
       ```
   - **Obter Todos os Posts**:
     ```bash
     GET http://localhost:8080/api/posts
     ```
   - **Obter um Post por ID**:
     ```bash
     GET http://localhost:8080/api/posts/{postId}
     ```
   - **Editar Post (exemplo)**:
     ```bash
     PATCH http://localhost:8080/api/posts/{postId}
     ```
     - Corpo da requisição (JSON):
       ```json
       {
         "name": "Novo Título do Post",
         "content": "Novo Conteúdo do Post",
         "img": "Imagem do Post (URL)",
         "tags": ["Tag1", "Tag2"],
       }
       ```
   - **Deletar Post**:
     ```bash
     DELETE http://localhost:8080/api/posts/{postId}
     ```

### Logs e Monitoramento

4. **Monitoramento de Logs**:
   - Durante o desenvolvimento ou em produção, é importante monitorar os logs gerados pela aplicação para identificar problemas.
   - Verifique os logs no terminal onde o servidor Spring Boot está rodando ou configure um sistema de logs persistente se estiver rodando em um ambiente de produção.

### Testes

5. **Executando Testes**:
   - O projeto inclui testes automatizados que podem ser executados para garantir que o sistema esteja funcionando corretamente após alterações no código.
   - Para rodar todos os testes, utilize o comando:
     ```bash
     mvn test
     ```

### Documentação Adicional

6. **Documentação de API**:
   - Documentação de API com o <a href="https://springdoc.org">Swagger</a>:
     ```bash
     http://localhost:8080/swagger-ui.html
     ```
   - Essa interface gráfica facilita o entendimento e o teste das requisições disponíveis.

### Segurança e Práticas Recomendadas

7. **Práticas de Segurança**:
   - Garanta que o token JWT seja mantido em segurança e que não seja exposto em locais não seguros, como em repositórios públicos ou em logs.
   - Revise regularmente as dependências do projeto e atualize-as para garantir que não haja vulnerabilidades conhecidas.

## Considerações finais

Com este projeto em desenvolvimento, é necessário apontar a gama de conhecimento que obtive a partir dos diversos conceitos que tive de estudar para configurar esta aplicação, embora o método de codificação esteja ao nível de iniciante. Existem outras funcionalidades a serem implementadas, uma vez que o projeto ainda está em desenvolvimento.

Ademais, gostaria de considerar o apoio de meu companheiro Lucas que, durante todo o percurso de desenvolvimento deste projeto, deu bons conselhos e ideias que facilitaram muito o entendimento sobre os requisitos de um blog. <3

