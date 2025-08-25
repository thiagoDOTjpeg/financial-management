import { Outlet } from "react-router-dom";
import styled from "styled-components";
import Sidebar from "./Sidebar";
import Header from "./Header";

const AppContainer = styled.div`
  display: flex;
  height: 100dvh;
  width: 100vw;
  background-color: #f8f9fa;
`;

const MainContent = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: auto;
  padding: 1rem;
  margin-bottom: 1rem;
`;

const PageWrapper = styled.main`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  overflow: auto;
`;

const MainLayout: React.FC = () => {
  return (
    <AppContainer>
      <Sidebar />
      <MainContent>
        <Header />
        <PageWrapper className="no-scrollbar">
          <Outlet />
        </PageWrapper>
      </MainContent>
    </AppContainer>
  );
};

export default MainLayout;
