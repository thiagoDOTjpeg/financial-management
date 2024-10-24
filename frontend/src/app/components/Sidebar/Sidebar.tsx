import styles from "./styles.module.scss";
import { GoHomeFill } from "react-icons/go";
import { GoCalendar } from "react-icons/go";
import { GoLog } from "react-icons/go";
import { GoX } from "react-icons/go";

export default function Sidebar() {
  return (
    <article className={styles.wrapperSidebar}>
      <GoX className={styles.image} />
      <div className={styles.wrapperButtons}>
        <button>
          <GoHomeFill />
          <span>Inicio</span>
        </button>
        <button>
          <GoCalendar />
          <span>Gerenciar</span>
        </button>
        <button>
          <GoLog />
          <span>Relatórios</span>
        </button>
      </div>
    </article>
  );
}
