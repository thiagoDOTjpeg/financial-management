import styled from "styled-components";

interface WrapperProps {
  $sidebaropen: boolean;
}

interface ButtonProps {
  $active?: boolean;
}

export const WrapperSidebar = styled.article<WrapperProps>`
  display: flex;
  flex-direction: column;
  width: 280px;
  height: 100vh;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 999;

  transform: translateX(${({ $sidebaropen }) => $sidebaropen ? "0" : "-100%"});
  transition: transform 0.3s ease;

  box-shadow: 4px 0 12px rgba(0,0,0,0.15);
  background: linear-gradient(180deg, #d6dac8 0%, #c8d0b8 100%);

  @media (max-width: 768px) {
    width: 100%;
    max-width: 280px;
  }
`;

export const WrapperImage = styled.span`
  display: flex;
  justify-content: flex-end;
  padding: 16px 20px;
  
  svg {
    height: 24px;
    width: 24px;
    color: rgba(6, 42, 88, 0.6);
    cursor: pointer;
    transition: color 0.2s ease;

    &:hover {
      color: rgba(6, 42, 88, 0.8);
    }
  }
`;

export const SidebarHeader = styled.div`
  padding: 0 20px 30px 20px;
  border-bottom: 1px solid rgba(6, 42, 88, 0.1);
`;

export const Logo = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
  color: #062a58;

  span {
    font-size: 1.5rem;
    font-weight: 700;
  }
`;

export const WrapperButtons = styled.nav`
  flex: 1;
  padding: 30px 20px;
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

export const Button = styled.button<ButtonProps>`
  display: flex;
  align-items: center;
  gap: 16px;
  width: 100%;
  padding: 16px 20px;
  border: none;
  border-radius: 12px;
  background: ${({ $active }) => $active ? 'rgba(6, 42, 88, 0.1)' : 'transparent'};
  color: rgba(6, 42, 88, 0.8);
  font-family: Poppins, sans-serif;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  text-align: left;

  svg {
    width: 20px;
    height: 20px;
    flex-shrink: 0;
  }

  &:hover {
    background: rgba(6, 42, 88, 0.08);
    transform: translateX(4px);
  }

  ${({ $active }) => $active && `
    background: rgba(6, 42, 88, 0.12);
    color: #062a58;
    font-weight: 600;
    box-shadow: 0 2px 8px rgba(6, 42, 88, 0.1);
  `}
`;

export const SidebarFooter = styled.div`
  padding: 20px;
  border-top: 1px solid rgba(6, 42, 88, 0.1);
`;

export const UserInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
`;

export const UserAvatar = styled.div`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #062a58;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
`;

export const UserDetails = styled.div`
  flex: 1;
`;

export const UserName = styled.div`
  font-weight: 600;
  color: #062a58;
  font-size: 0.9rem;
`;

export const UserEmail = styled.div`
  font-size: 0.8rem;
  color: rgba(6, 42, 88, 0.6);
`;