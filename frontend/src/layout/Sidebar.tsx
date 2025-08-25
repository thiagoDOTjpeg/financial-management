import {
  ArrowLeftRight,
  CreditCard,
  LayoutDashboard,
  Tags,
  Wallet,
} from "lucide-react";
import { NavLink } from "react-router-dom";
import styled from "styled-components";

const SidebarContainer = styled.aside`
  width: 256px;
  background-color: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
`;

const menuItems = [
  { path: "/", label: "Dashboard", icon: LayoutDashboard },
  { path: "/bankAccounts", label: "Contas de Banco", icon: Wallet },
  { path: "/invoices", label: "Faturas", icon: CreditCard },
  { path: "/transactions", label: "Transações", icon: ArrowLeftRight },
  { path: "/categories", label: "Categorias", icon: Tags },
];

const Sidebar: React.FC = () => {
  return (
    <SidebarContainer>
      <nav className="p-4">
        <ul className="list-none p-0 m-0">
          {menuItems.map((menuItem) => (
            <li key={menuItem.path}>
              <NavLink
                to={menuItem.path}
                className={({ isActive }) =>
                  `flex items-center p-2 text-sm font-medium rounded-md ${
                    isActive
                      ? "bg-gray-100 text-gray-900"
                      : "text-gray-600 hover:bg-gray-50"
                  }`
                }
              >
                <menuItem.icon size={20} className="mr-3" />
                {menuItem.label}
              </NavLink>
            </li>
          ))}
        </ul>
      </nav>
    </SidebarContainer>
  );
};

export default Sidebar;
