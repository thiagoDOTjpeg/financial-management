"use client";

import { useDispatch, useSelector } from "react-redux";
import { useState } from "react";
import { toggleSidebar } from "@/redux/slices/sidebarSlice";
import Header from "../Header/Header";
import Image from "next/image";
import Sidebar from "../Sidebar/Sidebar";
import menu from "../../assets/menu.svg";
import * as S from "./dashboardStyle";
import FinancialOverview from "../FinancialOverview/FinancialOverview";
import AccountsManagement from "../AccountsManagement/AccountsManagement";
import TransactionsManagement from "../TransactionsManagement/TransactionsManagement";
import BudgetPlanning from "../BudgetPlanning/BudgetPlanning";
import InvestmentTracking from "../InvestmentTracking/InvestmentTracking";
import Reports from "../Reports/Reports";

export default function Dashboard() {
  const isOpen = useSelector((state: any) => state.sidebar.isOpen);
  const activeSection = useSelector((state: any) => state.navigation.activeSection);
  const dispatch = useDispatch();

  function handleMenuClick() {
    dispatch(toggleSidebar());
  }

  const renderActiveSection = () => {
    switch (activeSection) {
      case 'overview':
        return <FinancialOverview />;
      case 'accounts':
        return <AccountsManagement />;
      case 'transactions':
        return <TransactionsManagement />;
      case 'budget':
        return <BudgetPlanning />;
      case 'investments':
        return <InvestmentTracking />;
      case 'reports':
        return <Reports />;
      default:
        return <FinancialOverview />;
    }
  };

  return (
    <>
      <S.WrapperDashboard $sidebaropen={isOpen}>
        <S.Menu onClick={handleMenuClick}>
          <Image src={menu} alt="menu" />
        </S.Menu>

        <Sidebar />

        <S.WrapperContent>
          <Header />
          <S.MainContent>
            {renderActiveSection()}
          </S.MainContent>
        </S.WrapperContent>
      </S.WrapperDashboard>
    </>
  );
}