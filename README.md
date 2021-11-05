https://img.shields.io/github/license/{username}/{repo-name}.svg https://img.shields.io/github/forks/{username}/{repo-name}.svg https://img.shields.io/github/stars/{username}/{repo-name}.svg https://img.shields.io/github/watchers/{username}/{repo-name}.svg 

# Bank System
Este projeto provém uma api para gerenciamento e execuções de ações bancárias

## O Projeto
O projeto consiste em uma aplicação semelhante a um sistema bancário. O projeto deve permitir as seguintes operações:

- Consulta de saldo;
- Transferência entre contas;
- Reverter uma transferência;
- Programar uma transferência futura parcelada (Ex: Usuário informa que quer transferir 300,00 em 3x. Isso deve gerar 3 registros em outra entidade com cada transferência a ser realizada. Não é necessário implementar nada referente a efetivação dessas transferências 

## Próximos Passos
- Criar testes para os endpoints validando os status de retorno, exceções e dados retornados.
- Configurar o Spring security para poder utilizar token JWT em vez da abordagem utilizada atualmente.
- Implementar usando as actions do github o CI e o CD do projeto.
- Criar um novo projeto spring que seja schedulado para ficar olhando a base de dados a fim de executar as transações agendadas.
- Dockerizar o projeto criando um uma imagem docker para facilitar o deploy do sistema.
- Criar um arquivo de changelog para documentar as mudanças efetuadas.

## Tecnologias
- Programming Language: JAVA https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white
- Framework: SPRING BOOT 	https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
- SGBD: H2
- IDE: Spring Tools https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white

## Configurando o Projeto

### Requisitos https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white

### SGBD
- Utilizei um banco H2 configurado pelo próprio sistema para facilitar a execução do serviço.
- O bando de dados pode ser acessado através do seguinte link http://localhost:8000/h2-console

### JAVA
- Seu sistema operacional deve ter instalado a versão do JAVA igual ou superior ao JAVA 11.

### Preparando o Projeto no Windows https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white
- É preciso criar as seguintes variáveis de ambiente no sistema:
- - DB_USERNAME (Nome do usuário do banco de dados)
- - DB_PASSWORD (Senha de acesso ao banco de dados do usuário definido na variável DB_USERNAME)
- - SERVER_PORT (Número da porta em que o sistema deve rodar)

### IDE
- Como sugestão, recomendo a IDE SPRING TOOLS (https://spring.io/tools), entretanto outras IDEs como o IntelliJ (https://www.jetbrains.com/pt-br/idea/) ou o Eclipse (https://www.eclipse.org/downloads/) também são aplicáveis.


## Endpoints
- A informação dos endpoints pode ser obtida através do swagger configurado no sistema

``` http://localhost:8000/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config ```
![swagger](https://user-images.githubusercontent.com/1368056/140455693-523d52aa-7e24-46d8-b7a0-b5afe1d010ff.png)

## Model

### ER diagram
![ER](https://user-images.githubusercontent.com/1368056/140454058-fe997224-36b6-4d7b-bbce-84c942042f10.png)

## Contato
https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white E-mail: anderson.uece@gmail.com
