# FINANCIAL MANAGEMENT BACKEND API

  API sendo criada utilizando as tecnologias:
    MYSQL: <img src="https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngwing.com%2Fen%2Fsearch%3Fq%3Dmysql%2BLogo&psig=AOvVaw1JyGvn4WSSUSGzI4e09coG&ust=1729255921403000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMDR8Y66lYkDFQAAAAAdAAAAABAS">
    SEQUELIZE
    EXPRESS

# API ROUTES

## Accounts:
  GET: /accounts
      /accounts/:id

  PUT: /accounts/:id

  DELETE: /accounts/:id

  POST: /accounts

## Transactions:
  GET: /transactions
       /transactions/:id

PUT: /transactions/:id

DELETE: /transactions/:id

POST: /transactions

## Cards:
  GET: /cards
       /cards/:id
       /cards/invoices/:id

PUT: /cards/:id

DELETE: /cards/:id

POST: /cards

## Invoices:
  GET: /invoices
       /invoices/:id
       /invoices/transactions/:id

PUT: /invoices/:id

DELETE: /invoices/:id

POST: /invoices
