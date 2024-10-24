import Image from "next/image";
import styles from "./styles.module.scss";
import arrow from "../../assets/arrow.svg";

export default function Header() {
  return (
    <header className={styles.wrapperHeader}>
      <div className={styles.mes}>
        <span>Novembro</span>
        <Image src={arrow} alt="arrow" />
      </div>
      <div className={styles.entradas}>
        <span>Entradas</span>
        <p>R$: XXXX.XX</p>
      </div>
      <div className={styles.saidas}>
        <span>Saídas</span>
        <p>R$: XXXX.XX</p>
      </div>
      <div className={styles.saldoRestante}>
        <span>Saldo Restante</span>
        <p>R$: XXXX.XX</p>
      </div>
    </header>
  );
}
