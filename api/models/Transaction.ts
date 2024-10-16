import { DataTypes, Model, Sequelize } from "sequelize";
import sequelize from "./index";

export class Transaction extends Model {
  static initModel(sequelize: Sequelize) {
    Transaction.init({
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
      sequelize,
      modelName: "transacoes",
      timestamps: false,
      tableName: "transacoes"
    });

  }
}

