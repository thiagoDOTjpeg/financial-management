import { DataTypes, Model, Sequelize } from "sequelize";
import sequelize from "./index";

export class Account extends Model {
  static initModel(sequelize: Sequelize) {
    Account.init({
      id_conta: {
        type: DataTypes.INTEGER,
        allowNull: false,
        primaryKey: true,
        autoIncrement: true,
        unique: true,
      },
      banco: {
        type: DataTypes.STRING,
        allowNull: false,
        validate: {
          min: 3,
        },
        unique: true
      },
      saldo: {
        type: DataTypes.DECIMAL(30, 2),
        allowNull: false,
        validate: {
          is: /^[0-9]+(\.[0-9]{1,2})?$/,
          min: 0.01,
        },
        unique: false
      }
    }, {
      sequelize,
      modelName: "conta",
      timestamps: false,
      tableName: "conta",
    })

  }

}
