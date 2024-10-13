import { Sequelize } from "sequelize"
import dotenv from "dotenv";

dotenv.config();

const { API_DATABASE, API_DATABASE_USER, API_DATABASE_PASSWORD, API_HOST } = process.env;

if (!API_DATABASE || !API_DATABASE_USER || !API_DATABASE_PASSWORD) {
  throw new Error("Missing environment variables for database configuration.");
}

const sequelize = new Sequelize(API_DATABASE, API_DATABASE_USER, API_DATABASE_PASSWORD, {
  host: API_HOST,
  dialect: "mysql"
})

export default sequelize;
