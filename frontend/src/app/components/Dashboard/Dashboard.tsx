import Header from "../Header/Header";
import Image from "next/image";
import styles from "./styles.module.scss";
import Sidebar from "../Sidebar/Sidebar";
import menu from "../../assets/menu.svg";

export default function Dashboard() {
  return (
    <div>
      <main className={styles.wrapperDashboard}>
        <span>
          <Image src={menu} alt="menu" className={styles.image} />
          <Sidebar />
        </span>
        <Header />
      </main>
      <footer></footer>
    </div>
  );
}
