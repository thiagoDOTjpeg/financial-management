import styled from "styled-components";

interface WrapperProps {
  $sidebaropen: boolean;
}

export const Menu = styled.span`
  position: fixed;
  cursor: pointer;
  z-index: 1000;

  margin-left: 21px;
  margin-top: 12px;

  &:hover {
    opacity: 0.7;
  }
`;

export const WrapperDashboard = styled.main<WrapperProps>`
  background-color: #fbf3d5;
  min-height: 100vh;
  display: flex;

  > ${Menu} {
    opacity: ${({ $sidebaropen }) => ($sidebaropen ? "0" : "1")};
    transition: opacity 0.29s linear;
  }
`;

export const WrapperContent = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
  margin-left: ${({ theme }) => theme?.sidebarWidth || '0px'};
  transition: margin-left 0.29s ease;
`;

export const MainContent = styled.section`
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  max-height: calc(100vh - 200px);
`;