import { Sequelize } from "sequelize"
import dotenv from "dotenv";
import { Card } from "./Card";
import { Invoice } from "./Invoice";
import { Transaction } from "./transaction";
import { Account } from "./Account";

dotenv.config();

const { API_DATABASE, API_DATABASE_USER, API_DATABASE_PASSWORD, API_HOST } = process.env;

if (!API_DATABASE || !API_DATABASE_USER || !API_DATABASE_PASSWORD) {
  throw new Error("Missing environment variables for database configuration.");
}

const sequelize = new Sequelize(API_DATABASE, API_DATABASE_USER, API_DATABASE_PASSWORD, {
  host: API_HOST,
  dialect: "mysql"
})

Card.initModel(sequelize);
Invoice.initModel(sequelize);
Transaction.initModel(sequelize);
Account.initModel(sequelize);

async () => {
  try {
    await sequelize.sync({ force: true });
    console.log("Sincronização realizada com sucesso!");
  } catch (error) {
    console.error("Ocorreu um erro ao sincronizar", error);
  }
}

Card.hasMany(Invoice, {
  foreignKey: "id_cartao",
});

Invoice.belongsTo(Card, {
  foreignKey: "id_cartao",
});

Invoice.hasMany(Transaction, {
  foreignKey: "id_fatura",
});

Transaction.belongsTo(Invoice, {
  foreignKey: "id_fatura",
});

export default { sequelize, Card, Invoice, Transaction, Account };