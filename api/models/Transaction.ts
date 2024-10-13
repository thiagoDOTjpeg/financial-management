import { DataTypes } from "sequelize";
import sequelize from "../config/db";

const Transaction = sequelize.define("transacoes", {
  id_transacoes: {
    type: DataTypes.INTEGER,
    allowNull: false,
    primaryKey: true,
    autoIncrement: true
  },
  id_cartao: {
    type: DataTypes.INTEGER,
    allowNull: true
  },
  id_conta: {
    type: DataTypes.INTEGER,
    allowNull: true
  },
  nome: {
    type: DataTypes.STRING,
    allowNull: false,
    validate: {
      min: 4,
    }
  },
  valor: {
    type: DataTypes.DECIMAL(10, 2),
    allowNull: false,
    validate: {
      min: 1,
    }
  },
  data: {
    type: DataTypes.DATE,
    allowNull: false,
    validate: {
      isDate: true,
    }
  },
  id_fatura: {
    type: DataTypes.INTEGER,
    allowNull: true
  }
}, {
  tableName: "transacoes",
  timestamps: false
});

export default Transaction;

