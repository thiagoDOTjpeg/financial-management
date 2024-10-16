import { DataTypes, Model, Sequelize } from "sequelize";
import sequelize from "./index";


export class Card extends Model {
  static initModel(sequelize: Sequelize) {
    Card.init({
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
          max: 31,
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
      sequelize,
      modelName: "cartao",
      timestamps: false,
      tableName: "cartao"
    })

  }

}
