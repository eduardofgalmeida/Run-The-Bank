![image](https://github.com/eduardofgalmeida/Run-The-Bank/assets/51132593/bcef0c5a-0b3a-4d36-bac7-30b6a1fb5b0d)

1.Cliente (CPF/CNPJ): Representa o tipo do usuário.


2.Servidor API: O servidor que lida com as requisições da API RESTful.


3.Conta Bancária: Armazena as informações da conta, como ID, Agência, Saldo, e Status.


4.Banco de Dados: Armazena permanentemente os dados do sistema, como clientes e contas.


5.Serviço de Notificação Externo: Um serviço externo usado para enviar notificações aos clientes após operações como pagamentos.


6.Mock API (Notificação): Um serviço falso usado para simular o envio de notificações externas.



No projeto, aplicamos os princípios SOLID e Clean Code para torná-lo mais organizado e legível. Integramos testes unitários para garantir a robustez do código. A documentação foi elaborada usando o Swagger. 
Tentei incorporar o Apache Camel para a integração, mas enfrentei um problema e, devido às restrições de tempo, não pude concluir. No entanto, deixei a configuração comentada para referência futura.

link swagger: http://localhost:8080/swagger-ui/index.html#/

Payload para criação de cliente/costumer:
POST(http://localhost:8080/customer)


{
  "id": 0,
  
  "document": "string",
  
  "name": "string",


  "address": "string",
  
  "password": "string"
  
}

Payload para criação de conta/account:

POST(http://localhost:8080/account)

{

  "id": 0,
  
  "agency": "string",
  
  "balance": 0,
  
  "active": true,
  
  "customer": {
    "id": 0
  }
  
}

Payload para realizar transferência de dinheiro entre as contas:

POST(http://localhost:8080/account/transfer)

{

  "sourceAccountAgency": "string",
  
  "targetAccountAgency": "string",
  
  "amount": 0,
  
  "sourceAccountId": 0
  
}


Proposta para melhorar a aplicação:

Modernizar a arquitetura do sistema com microserviços, containers, orquestração, e aprimorar integrações. Adotar CI/CD e reforçar segurança. Melhorar documentação e monitoramento para maior eficiência e escalabilidade.
