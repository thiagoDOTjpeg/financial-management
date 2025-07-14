import styled from "styled-components";

export const Container = styled.div`
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
`;

export const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  flex-wrap: wrap;
  gap: 20px;
`;

export const Title = styled.h1`
  font-size: 2rem;
  color: #062a58;
  font-weight: 600;
`;

export const TabContainer = styled.div`
  display: flex;
  background: white;
  border-radius: 8px;
  padding: 4px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
`;

export const Tab = styled.button<{ $active: boolean }>`
  padding: 12px 24px;
  border: none;
  border-radius: 6px;
  background: ${({ $active }) => $active ? '#4CAF50' : 'transparent'};
  color: ${({ $active }) => $active ? 'white' : '#666'};
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;

  &:hover {
    background: ${({ $active }) => $active ? '#4CAF50' : '#f5f5f5'};
  }
`;

export const SummaryCards = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
`;

export const SummaryCard = styled.div`
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
  }
`;

export const CardHeader = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;

  span {
    font-size: 0.9rem;
    color: #666;
    font-weight: 500;
  }
`;

export const CardValue = styled.div<{ positive: boolean }>`
  font-size: 1.8rem;
  font-weight: 700;
  color: ${({ positive }) => positive ? '#4CAF50' : '#FF5722'};
`;

export const ReturnPercentage = styled.div<{ positive: boolean }>`
  font-size: 0.9rem;
  font-weight: 500;
  color: ${({ positive }) => positive ? '#4CAF50' : '#FF5722'};
  margin-top: 4px;
`;

export const ActionsContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-bottom: 30px;
`;

export const AddButton = styled.button`
  display: flex;
  align-items: center;
  gap: 8px;
  background: #4CAF50;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 12px 20px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.2s ease;

  &:hover {
    background: #45a049;
  }
`;

export const InvestmentsList = styled.div`
  margin-bottom: 40px;
`;

export const SectionTitle = styled.h2`
  font-size: 1.5rem;
  color: #062a58;
  margin-bottom: 20px;
  font-weight: 600;
`;

export const InvestmentsContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 20px;
`;

export const InvestmentCard = styled.div`
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
  }
`;

export const InvestmentHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 20px;
`;

export const InvestmentInfo = styled.div`
  flex: 1;
`;

export const InvestmentName = styled.h3`
  margin: 0 0 4px 0;
  font-size: 1.2rem;
  color: #333;
  font-weight: 600;
`;

export const InvestmentType = styled.span`
  font-size: 0.85rem;
  color: #666;
  background: #f0f0f0;
  padding: 4px 8px;
  border-radius: 4px;
`;

export const InvestmentReturn = styled.div<{ positive: boolean }>`
  color: ${({ positive }) => positive ? '#4CAF50' : '#FF5722'};
`;

export const InvestmentDetails = styled.div`
  margin-bottom: 20px;
`;

export const DetailRow = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
  font-size: 0.9rem;

  span:first-child {
    color: #666;
  }

  span:last-child {
    font-weight: 500;
    color: #333;
  }
`;

export const ReturnInfo = styled.div`
  border-top: 1px solid #f0f0f0;
  padding-top: 16px;
  text-align: center;
`;

export const ReturnValue = styled.div<{ positive: boolean }>`
  font-size: 1.3rem;
  font-weight: 700;
  color: ${({ positive }) => positive ? '#4CAF50' : '#FF5722'};
`;

export const GoalsList = styled.div`
  margin-bottom: 40px;
`;

export const GoalsContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 20px;
`;

export const GoalCard = styled.div`
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
  }
`;

export const GoalHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 20px;
`;

export const GoalInfo = styled.div`
  flex: 1;
`;

export const GoalName = styled.h3`
  margin: 0 0 4px 0;
  font-size: 1.2rem;
  color: #333;
  font-weight: 600;
`;

export const GoalTarget = styled.span`
  font-size: 0.9rem;
  color: #666;
`;

export const GoalIcon = styled.div`
  color: #4CAF50;
`;

export const GoalProgress = styled.div`
  margin-bottom: 20px;
`;

export const ProgressBar = styled.div`
  width: 100%;
  height: 8px;
  background: #f0f0f0;
  border-radius: 4px;
  overflow: hidden;
  margin-bottom: 8px;
`;

export const ProgressFill = styled.div<{ percentage: number }>`
  height: 100%;
  width: ${({ percentage }) => percentage}%;
  background: linear-gradient(90deg, #4CAF50, #66BB6A);
  transition: width 0.3s ease;
`;

export const ProgressText = styled.div`
  font-size: 0.85rem;
  color: #666;
  text-align: center;
`;

export const GoalDetails = styled.div`
  border-top: 1px solid #f0f0f0;
  padding-top: 16px;
`;

export const EmptyState = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  text-align: center;

  p {
    margin: 16px 0 8px 0;
    font-size: 1.2rem;
    color: #666;
  }

  span {
    color: #999;
    font-size: 0.9rem;
  }
`;