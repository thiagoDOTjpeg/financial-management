import { DataTypes } from "sequelize";
import sequelize from "../config/db";

const Account = sequelize.define("conta", {
  id_conta: {
    type: DataTypes.INTEGER,
    allowNull: false,
    primaryKey: true,
    autoIncrement: true
  },
  banco: {
    type: DataTypes.STRING,
    allowNull: false,
    validate: {
      min: 3,
    }
  },
  saldo: {
    type: DataTypes.DECIMAL(30, 2),
    allowNull: false,
    validate: {
      is: /^[0-9]+(\.[0-9]{1,2})?$/,
      min: 0.01,
    }
  }
}, {
  tableName: "conta",
  timestamps: false
})


export default Account;

