import { useDispatch, useSelector } from "react-redux";
import { toggleSidebar } from "@/redux/slices/sidebarSlice";
import Header from "../Header/Header";
import Image from "next/image";
import Sidebar from "../Sidebar/Sidebar";
import menu from "../../assets/menu.svg";
import * as S from "./dashboardStyle";
import Chart from "../Chart/Chart";

export default function Dashboard() {
  const isOpen = useSelector((state: any) => state.sidebar.isOpen);
  const dispatch = useDispatch();

  function handleMenuClick() {
    dispatch(toggleSidebar());
  }

  return (
    <>
      <S.WrapperDashboard $sidebaropen={isOpen}>
        <S.Menu onClick={handleMenuClick}>
          <Image src={menu} alt="menu" />
        </S.Menu>

        <Sidebar />

        <S.WrapperContent>
          <Header />
          <Chart />
        </S.WrapperContent>
      </S.WrapperDashboard>
      <footer></footer>
    </>
  );
}
