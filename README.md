# FINANCIAL MANAGEMENT BACKEND API

  API sendo criada utilizando as tecnologias: <br />
    MYSQL <br />
    SEQUELIZE <br />
    EXPRESS <br />

# API ROUTES

### Accounts:
  GET: /accounts
       /accounts/:id

  PUT: /accounts/:id

  DELETE: /accounts/:id

  POST: /accounts
    Como deve ser o body da requisição:
        {
          "id_banco": // É auto increment
          "banco": 
          "saldo": 
        }

### Transactions:
  GET: /transactions
       /transactions/:id

  PUT: /transactions/:id

  DELETE: /transactions/:id

  POST: /transactions
      Como deve ser o body da requisição:
      {
        "id_transacoes": // É auto increment
        "id_cartao":  // Colocar o id cartão caso seja crédito
        "id_conta":   // Senão coloca o id da conta caso seja débito
        "nome":
        "valor": 
        "data":
        "id_fatura":
      }

### Cards:
    GET: /cards
         /cards/:id
         /cards/invoices/:id

    PUT: /cards/:id

    DELETE: /cards/:id

    POST: /cards
      Como deve ser o body da requisição:
      {
        "id_cartao": // É auto increment
        "id_conta": 
        "data_vencimento":
        "data_fechamento":
      }

### Invoices:
    GET: /invoices
         /invoices/:id
         /invoices/transactions/:id

    PUT: /invoices/:id

    DELETE: /invoices/:id

    POST: /invoices
      Como deve ser o body da requisição:
      {
        "id_fatura": // É auto increment
        "id_cartao": 
        "mes":
        "pago":
      }
