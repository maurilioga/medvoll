# 🏥 Medvoll API

API REST de gerenciamento de consultas médicas, desenvolvida com **Java 17**, **Spring Boot**, seguindo boas práticas como arquitetura em camadas, uso de DTOs, validações, autenticação com JWT, exclusão lógica, paginação e testes.

---

## 🚀 Tecnologias utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- MySQL
- Maven
- JUnit 5

---

## 🛠 Funcionalidades

- Cadastro e gerenciamento de médicos
- Cadastro e gerenciamento de pacientes
- Agendamento e cancelamento de consultas
- Autenticação com JWT

---

## 🔐 Autenticação

A API utiliza autenticação baseada em **JWT**. Para acessar os endpoints protegidos:

1. Faça login enviando um `POST` para `/autenticacao` com e-mail e senha.
2. Receba o token de acesso.
3. Use o token no cabeçalho das requisições:

```http
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## 📦 Como rodar o projeto

1. **Clone o repositório:**
```bash
git clone https://github.com/maurilioga/medvoll.git
cd medvoll
```

2. **Configure o banco de dados:**
- Crie um banco de dados MySQL:
```sql
CREATE DATABASE medvoll;
```

3. **Ajuste o arquivo `application.properties`:**
```properties
spring.datasource.url=jdbc:mysql://localhost/med_voll
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
api.security.token.secret=chave_para_gerar_token
```

4. **Execute o projeto:**
```bash
./mvnw spring-boot:run
```
Ou:
```bash
mvn spring-boot:run
```

5. **Acesse a aplicação:**
- Base da API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`

---

## 🧪 Testes

Execute os testes com:
```bash
./mvnw test
```

---

## 👨‍💻 Desenvolvedor
| [<img loading="lazy" src="https://avatars.githubusercontent.com/u/74618958?v=4" width=115><br><sub>Maurilio Gonçalves Arantes</sub>](https://github.com/maurilioga) |
| :---: |
[GitHub](https://github.com/maurilioga) / [LinkedIn](https://www.linkedin.com/in/mauriliogoncarantes)|