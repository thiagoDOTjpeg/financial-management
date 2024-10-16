import { DataTypes, Model, Sequelize } from "sequelize";
import sequelize from "./index";

export class Invoice extends Model {
  static initModel(sequelize: Sequelize) {
    Invoice.init({
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
      sequelize,
      modelName: "fatura",
      timestamps: false,
      tableName: "fatura"
    });
  }
}
