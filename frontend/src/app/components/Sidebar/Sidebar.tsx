"use client";

import { toggleSidebar } from "@/redux/slices/sidebarSlice";
import { setActiveSection } from "@/redux/slices/navigationSlice";
import { GoHomeFill, GoCalendar, GoLog, GoX, GoCreditCard, GoGraph, GoReport } from "react-icons/go";
import { FiDollarSign, FiTrendingUp, FiPieChart } from "react-icons/fi";
import { useDispatch, useSelector } from "react-redux";
import * as S from "./sidebarStyle";

export default function Sidebar() {
  const isOpen = useSelector((state: any) => state.sidebar.isOpen);
  const activeSection = useSelector((state: any) => state.navigation.activeSection);
  const dispatch = useDispatch();

  function handleCloseSidebar() {
    dispatch(toggleSidebar());
  }

  function handleSectionChange(section: string) {
    dispatch(setActiveSection(section));
    if (window.innerWidth <= 768) {
      dispatch(toggleSidebar());
    }
  }

  const menuItems = [
    { id: 'overview', label: 'Visão Geral', icon: GoHomeFill },
    { id: 'accounts', label: 'Contas', icon: FiDollarSign },
    { id: 'transactions', label: 'Transações', icon: GoCalendar },
    { id: 'budget', label: 'Orçamento', icon: FiPieChart },
    { id: 'investments', label: 'Investimentos', icon: FiTrendingUp },
    { id: 'reports', label: 'Relatórios', icon: GoReport }
  ];

  return (
    <S.WrapperSidebar $sidebaropen={isOpen}>
      <S.WrapperImage>
        <GoX onClick={handleCloseSidebar} />
      </S.WrapperImage>
      
      <S.SidebarHeader>
        <S.Logo>
          <FiDollarSign size={32} />
          <span>FinanceApp</span>
        </S.Logo>
      </S.SidebarHeader>

      <S.WrapperButtons>
        {menuItems.map((item) => {
          const IconComponent = item.icon;
          return (
            <S.Button
              key={item.id}
              $active={activeSection === item.id}
              onClick={() => handleSectionChange(item.id)}
            >
              <IconComponent />
              <span>{item.label}</span>
            </S.Button>
          );
        })}
      </S.WrapperButtons>

      <S.SidebarFooter>
        <S.UserInfo>
          <S.UserAvatar>U</S.UserAvatar>
          <S.UserDetails>
            <S.UserName>Usuário</S.UserName>
            <S.UserEmail>user@email.com</S.UserEmail>
          </S.UserDetails>
        </S.UserInfo>
      </S.SidebarFooter>
    </S.WrapperSidebar>
  );
}