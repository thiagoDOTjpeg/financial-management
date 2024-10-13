import express from "express"
import cors from "cors"
import router from "./routes/defaultRoute";
import dotenv from "dotenv";

dotenv.config();

const app = express();

const { API_PORT, API_URL } = process.env;

app.use(express.json());
app.use(cors());

app.use('/', router)

app.listen(API_PORT, () => {
  console.log(`Servidor rodando em ${API_URL}:${API_PORT}`);
})