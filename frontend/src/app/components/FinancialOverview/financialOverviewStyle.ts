import styled from "styled-components";

export const OverviewContainer = styled.div`
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
`;

export const Title = styled.h1`
  font-size: 2rem;
  color: #062a58;
  margin-bottom: 30px;
  font-weight: 600;
`;

export const LoadingContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 400px;
  font-size: 1.2rem;
  color: #666;
`;

export const CardsGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
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

export const ChartsSection = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 30px;
  margin-bottom: 40px;

  @media (max-width: 768px) {
    grid-template-columns: 1fr;
  }
`;

export const ChartContainer = styled.div`
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
`;

export const ChartTitle = styled.h3`
  font-size: 1.2rem;
  color: #062a58;
  margin-bottom: 20px;
  font-weight: 600;
`;

export const ChartPlaceholder = styled.div`
  height: 300px;
  background: #f5f5f5;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  font-style: italic;
`;

export const RecentTransactions = styled.div`
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
`;

export const SectionTitle = styled.h3`
  font-size: 1.2rem;
  color: #062a58;
  margin-bottom: 20px;
  font-weight: 600;
`;

export const TransactionsList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 12px;
`;

export const TransactionItem = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;

  &:last-child {
    border-bottom: none;
  }
`;

export const TransactionInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4px;
`;

export const TransactionDescription = styled.span`
  font-weight: 500;
  color: #333;
`;

export const TransactionDate = styled.span`
  font-size: 0.85rem;
  color: #666;
`;

export const TransactionAmount = styled.span<{ positive: boolean }>`
  font-weight: 600;
  color: ${({ positive }) => positive ? '#4CAF50' : '#FF5722'};
`;