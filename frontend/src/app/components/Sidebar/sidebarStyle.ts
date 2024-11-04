import styled from "styled-components";

interface WrapperProps {
  SidebarOpen: boolean;
}

export const WrapperSidebar = styled.article<WrapperProps>`
  display: flex;
  flex-direction: column;
  width: 250px;
  height: 100vh;

  transform: ${({ SidebarOpen }) => SidebarOpen ? "translateX(0)" : "translateX(-100%)"};

  transition: transform 0.25s ease-in-out;

  box-shadow: 4px 0 4px rgba($color: #000000, $alpha: 0.25);

  background-color: #d6dac8;
`;

export const WrapperImage = styled.span`
  height: 50px;
  width: 50px;
  margin-top: 9px;
  margin-left: 170px;

  color: rgba($color: #062a58, $alpha: 0.35);

  cursor: pointer;
`;

export const WrapperButtons = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  margin: auto 0;

`;

export const Button = styled.button`
  display: grid;
  grid-template-columns: 46px 1fr;
  justify-content: center;
  align-items: center;

  svg {
    justify-self: center;
    width: 34px;
    height: 31px;
  }

  span {
    justify-self: start;
    margin-left: 22px;
  }

  width: 208px;
  height: 41px;
  margin-top: 33px;

  font-family: Poppins;
  font-size: 24px;
  color: rgba($color: #062a58, $alpha: 0.5);

  border: none;
  border-radius: 20px;
  background-color: rgba($color: #d9d9d9, $alpha: 0.25);
  box-shadow: 0 4px 4px 0 rgba($color: #000000, $alpha: 0.2);
`;