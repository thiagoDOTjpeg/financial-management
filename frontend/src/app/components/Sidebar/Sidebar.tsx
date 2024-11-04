import { toggleSidebar } from "@/redux/slices/sidebarSlice";
import { GoHomeFill } from "react-icons/go";
import { GoCalendar } from "react-icons/go";
import { GoLog } from "react-icons/go";
import { GoX } from "react-icons/go";
import { useDispatch, useSelector } from "react-redux";
import * as S from "./sidebarStyle";

export default function Sidebar() {
  const isOpen = useSelector((state: any) => state.sidebar.isOpen);
  const dispatch = useDispatch();

  function handleCloseSidebar() {
    dispatch(toggleSidebar());
  }

  return (
    <S.WrapperSidebar SidebarOpen={isOpen}>
      <S.WrapperImage>
        <GoX onClick={handleCloseSidebar} />
      </S.WrapperImage>
      <S.WrapperButtons>
        <S.Button>
          <GoHomeFill />
          <span>Inicio</span>
        </S.Button>
        <S.Button>
          <GoCalendar />
          <span>Gerenciar</span>
        </S.Button>
        <S.Button>
          <GoLog />
          <span>Relatórios</span>
        </S.Button>
      </S.WrapperButtons>
    </S.WrapperSidebar>
  );
}
