import styled from "styled-components";

interface WrapperProps {
  $sidebaropen: boolean;
}

export const Menu = styled.span`
  position: fixed;
  cursor: pointer;

  margin-left: 21px;
  margin-top: 12px;
`;

export const WrapperDashboard = styled.main<WrapperProps>`
  background-color: #fbf3d5;
  height: 100dvh;
  display: flex;

  > ${Menu} {
  opacity: ${({ $sidebaropen }) => ($sidebaropen ? "0" : "1")};

  transition: opacity 0.29s linear;
}
`;

export const WrapperContent = styled.div`
  display: flex;

  gap: 25px;
  flex-direction: column;
  margin: 0 auto
`;
