import styled from "styled-components";

interface WrapperProps {
  $sidebaropen: boolean;
}

export const WrapperSidebar = styled.article<WrapperProps>`
  display: flex;
  flex-direction: column;
  width: 250px;
  height: 100vh;

  margin-left:  ${({ $sidebaropen }) => $sidebaropen ? "0" : "-250px"};

  transition: margin 0.29s ;

  box-shadow: 4px 0 4px rgba(0,0,0,0.25);

  background-color: #d6dac8;
`;

export const WrapperImage = styled.span`
  svg {
    height: 50px;
    width: 50px;
    margin-top: 9px;
    margin-left: 170px;

    color: rgba(6, 42, 88, 0.35);
  }
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
  color: rgba(6, 42, 88, 0.5);

  border: none;
  border-radius: 20px;
  background-color: rgba(217, 217, 217, 0.25);
  box-shadow: 0 4px 4px 0 rgba(0,0,0,0.25);
`;