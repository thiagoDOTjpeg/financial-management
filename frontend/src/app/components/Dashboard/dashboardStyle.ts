import styled from "styled-components";

interface WrapperProps {
  SidebarOpen: boolean;
}

export const WrapperDashboard = styled.main<WrapperProps>`
  background-color: #fbf3d5;
  height: 100dvh;
  display: grid;
  grid-template-columns: ${({ SidebarOpen }) => SidebarOpen ? "250px 1fr" : "0 1fr"};
`;

export const Menu = styled.span`
  position: fixed;
  cursor: pointer;

  margin-left: 21px;
  margin-top: 12px;
`;