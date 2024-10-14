import { DataTypes } from "sequelize";
import sequelize from "../config/db";
import Invoice from "./Invoice";

const Card = sequelize.define("cartao", {
  id_cartao: {
    type: DataTypes.INTEGER,
    allowNull: false,
    primaryKey: true,
    autoIncrement: true
  },
  id_conta: {
    type: DataTypes.INTEGER,
    allowNull: false
  },
  data_vencimento: {
    type: DataTypes.INTEGER,
    allowNull: false,
    validate: {
      min: 1,
      max: 12,
      isInt: true
    }
  },
  data_fechamento: {
    type: DataTypes.INTEGER,
    allowNull: false,
    validate: {
      min: 1,
      max: 31,
      isInt: true
    }
  }
}, {
  tableName: "cartao",
  timestamps: false
});

Card.hasMany(Invoice, {
  foreignKey: "id_cartao"
});

export default Card;