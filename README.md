![Badge](https://img.shields.io/github/license/andersongomes/banksystem.svg)
![Badge](https://img.shields.io/github/stars/andersongomes/banksystem.svg)
![Badge](	https://img.shields.io/github/forks/andersongomes/banksystem.svg)
![Badge](	https://img.shields.io/github/watchers/andersongomes/banksystem.svg)


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
- Programming Language: JAVA ![Badge](	https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
- Framework: SPRING BOOT ![Badge](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
- SGBD: H2
- IDE: Spring Tools ![Badge](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)

## Configurando o Projeto

### Requisitos ![Badge](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)

### SGBD
- Utilizei um banco H2 configurado pelo próprio sistema para facilitar a execução do serviço.
- O bando de dados pode ser acessado através do seguinte link http://localhost:8000/h2-console

### JAVA ![Badge](	https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
- Seu sistema operacional deve ter instalado a versão do JAVA igual ou superior ao JAVA 11.

### Preparando o Projeto no Windows ![Badge](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)
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

- Para os endpoints que precisam de autenticação deve ser enviado no header o token assim como no exemplo abaixo:
![image](https://user-images.githubusercontent.com/1368056/140458564-3ad1c512-7089-4d3f-b112-eb29afc1bb92.png)


## Model
- Foi definido na modelagem que tanto os depósito de valores como as retiradas ficararão numa mesma tabela. O que muda é o fator onde para os depósitos o fato é 1 e para os saques o fator é -1. Asssim a consulta do saldo fica bem mais direta fazendo o somatório do valor multiplicato pelo fator da transação.

### ER diagram
![ER](https://user-images.githubusercontent.com/1368056/140454058-fe997224-36b6-4d7b-bbce-84c942042f10.png)

### Consultas de Exemplo
```
=== USER ===
SELECT * FROM USER;
DELETE FROM USER WHERE ID = 4

=== BANK ===
SELECT * FROM BANK;

=== ACCOUNT ===
SELECT * FROM ACCOUNT;

=== BANK_TRANSFER ===
SELECT * FROM BANK_TRANSFER;

=== BANK_TRANSACTION ===
SELECT * FROM BANK_TRANSACTION;
UPDATE BANK_TRANSACTION SET TRANSACTION_FACTOR = 1 WHERE ID = 10
UPDATE BANK_TRANSACTION SET TRANSACTION_REVERSAL_DATE = NULL WHERE ID =43

=== TOKEN ===
SELECT * FROM TOKEN WHERE TOKEN = '1635911757642-965ed631-b4a3-4591-8383-d190876a9fff'
UPDATE TOKEN SET EXPIRATION_DATE = '2021-11-06 00:55:57.643' WHERE TOKEN = '1635911757642-965ed631-b4a3-4591-8383-d190876a9fff'

```

### JSONs de exemplo de inserção nos endpoints
```
==== USER ====
{
    "name": "Anderson Gomes",
    "login": "anderson.gomes",
    "password": "123456",
    "email": "anderson.uece@gmail.com",
    "cpf": "04816804382",
    "rg": "2005010303776",
    "phone": "85988333287"
},
{
    "name": "Ryvane Maria",
    "login": "ryvane.maria",
    "password": "123456",
    "email": "ryvane@gmail.com",
    "cpf": "60417518390",
    "rg": "2005010303776",
    "phone": "85986169299"
}

==== BANK =====
{
  "name": "Caixa Econômica Federal",
  "bankCode": "001",
  "phone": "(85)3332-9876"
}

==== ACCOUNT ====
{
  "user": {
      "id": 1
  },
  "bank": {
    "id": 3
  },
  "agency": "2568",
  "accountNumber": "000787655-0",
  "operation": "013"
},
{
  "user": {
      "id": 2
  },
  "bank": {
    "id": 3
    "id": 3
  },
  "agency": "2124",
  "accountNumber": "0007876512-0",
  "operation": "001"
}

==== BANK_TRANSFER =====

{
  "originAccount": {
      "id": 5
  },
  "destinyAccount": {
    "id": 6
  },
  "transferValue": "450.0"
}

==== BANK TRANSACTION =====
{
  "account": {
      "id": 6
  },
  "transactionSchedulingDate": "2021-11-03T01:13:49.072Z",
  "transactionDate": "2021-11-03T01:13:49.072Z",
  "transactionFactor": -1,
  "transactionValue": 200.0
}

==== AUTHENTICATE ======

{
    "login": "anderson.gomes",
    "password": "123456"
}

==== DEPOSIT ====
{
    "transactionSchedulingDate": "2021-11-03T18:23:49.072Z",
    "transactionDate": "2021-11-03T18:23:49.072Z",
    "transactionValue": 150.0
}

==== WITHDRAW =====
{
    "transactionSchedulingDate": "2021-11-03T18:23:49.072Z",
    "transactionDate": "2021-11-03T18:23:49.072Z",
    "transactionValue": 100.0
}

=== BANK TRANSFER ===

{
  "originAccount": {
      "id": 4
  },
  "destinyAccount": {
    "id": 5
  },
  "transferValue": "450.0"
}
```


## Contato
![Badge](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white) 

E-mail: anderson.uece@gmail.com
