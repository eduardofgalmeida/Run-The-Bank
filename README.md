
<h1> Run-The-Bank </h1>

![image](https://github.com/eduardofgalmeida/Run-The-Bank/assets/51132593/bcef0c5a-0b3a-4d36-bac7-30b6a1fb5b0d)

<h1>Visão Geral</h1>

O Run-The-Bank é um sistema bancário robusto que gerencia clientes, contas bancárias, e interações por meio de uma API RESTful. Aqui estão os principais componentes do projeto:

Cliente (CPF/CNPJ): Representa o tipo do usuário.

Servidor API: Responsável por lidar com as requisições da API RESTful.

Conta Bancária: Armazena informações essenciais da conta, incluindo ID, Agência, Saldo e Status.

Banco de Dados: Permanentemente armazena dados cruciais do sistema, como clientes e contas.

Serviço de Notificação Externo: Envia notificações aos clientes após operações como pagamentos.

Mock API (Notificação): Um serviço falso usado para simular o envio de notificações externas.

------------------------------------------------------

<h1>Princípios e Metodologia:</h1>

No projeto, aplicamos os princípios SOLID e Clean Code para torná-lo mais organizado e legível. Integramos testes unitários para garantir a robustez do código. A documentação foi elaborada usando o Swagger. 
Tentei incorporar o Apache Camel para a integração, mas enfrentei um problema e, devido às restrições de tempo, não pude concluir. No entanto, deixei a configuração comentada para referência futura.

<b>Link Swagger para quando subir a aplicação local:</b> http://localhost:8080/swagger-ui/index.html#/

Payload para criação de cliente/costumer:
POST(http://localhost:8080/customer)


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


<h1>Proposta para melhorar a aplicação:</h1>

Modernizar a arquitetura do sistema com microserviços, containers, orquestração, e aprimorar integrações. Adotar CI/CD e reforçar segurança. Melhorar documentação e monitoramento para maior eficiência e escalabilidade.
