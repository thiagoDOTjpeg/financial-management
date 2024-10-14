import { DataTypes } from "sequelize";
import sequelize from "../config/db";
import Transaction from "./transaction";
import Card from "./Card";

const Invoice = sequelize.define("fatura", {
  id_fatura: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    allowNull: false,
    primaryKey: true
  },
  id_cartao: {
    type: DataTypes.INTEGER,
    allowNull: false
  },
  mes: {
    type: DataTypes.STRING,
    allowNull: false
  },
  pago: {
    type: DataTypes.BOOLEAN,
    allowNull: false
  }
}, {
  tableName: "fatura",
  timestamps: false
});

Invoice.belongsTo(Card, {
  foreignKey: "id_cartao"
});


export default Invoice;