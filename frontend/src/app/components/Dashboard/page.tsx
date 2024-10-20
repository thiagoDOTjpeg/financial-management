import Header from "../Header/page";
import styles from "./styles.module.scss";

export default function Dashboard() {
  return (
    <div>
      <main className={styles.wrapperDashboard}>
        <Header />
      </main>
      <footer></footer>
    </div>
  );
}
