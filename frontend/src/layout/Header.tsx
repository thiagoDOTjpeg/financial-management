import styled from "styled-components";
import { useNavigationStore } from "@/store/navigationStore";
import { useAuthStore } from "@/store/authStore";
import { useNavigate } from "react-router-dom";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { Button } from "@/components/ui/button";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { LogOut, UserIcon } from "lucide-react";

const HeaderContainer = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem 2rem;
  color: black;
  background-color: white;
  border-bottom: 1px solid #e5e7eb;
  margin-bottom: 16px;
`;

const Header: React.FC = () => {
  const currentPageTitle = useNavigationStore(
    (state) => state.currentPageTitle
  );
  const { username, logout } = useAuthStore();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <HeaderContainer>
      <h1 className="text-2xl font-bold">{currentPageTitle}</h1>

      <Popover>
        <PopoverTrigger asChild>
          <Button variant="ghost" className="relative h-10 w-10 rounded-full">
            <Avatar className="h-10 w-10">
              <AvatarFallback>
                {username ? username.charAt(0).toUpperCase() : <UserIcon />}
              </AvatarFallback>
            </Avatar>
          </Button>
        </PopoverTrigger>
        <PopoverContent className="w-56" align="end" forceMount>
          <div className="flex flex-col space-y-1 p-2">
            <p className="text-sm font-medium leading-none">
              {username?.charAt(0).toUpperCase() + username?.slice(1)}
            </p>
          </div>

          <Button
            variant="ghost"
            className="w-full justify-start mt-2 text-white hover:text-white"
            onClick={handleLogout}
          >
            <LogOut className="mr-2 h-4 w-4" />
            Sair
          </Button>
        </PopoverContent>
      </Popover>
    </HeaderContainer>
  );
};

export default Header;
