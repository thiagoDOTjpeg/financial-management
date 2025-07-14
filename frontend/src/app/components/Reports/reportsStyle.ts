import styled from "styled-components";

export const Container = styled.div`
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
`;

export const Header = styled.div`
  margin-bottom: 30px;
`;

export const Title = styled.h1`
  font-size: 2rem;
  color: #062a58;
  font-weight: 600;
`;

export const FiltersContainer = styled.div`
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  align-items: end;
`;

export const FilterGroup = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Label = styled.label`
  margin-bottom: 8px;
  font-weight: 500;
  color: #333;
  font-size: 0.9rem;
`;

export const Select = styled.select`
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 1rem;
  background: white;
  transition: border-color 0.2s ease;

  &:focus {
    outline: none;
    border-color: #4CAF50;
  }
`;

export const Input = styled.input`
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.2s ease;

  &:focus {
    outline: none;
    border-color: #4CAF50;
  }
`;

export const FilterActions = styled.div`
  display: flex;
  align-items: end;
`;

export const GenerateButton = styled.button`
  background: #4CAF50;
  color: white;
  border: none;
  border-radius: 6px;
  padding: 12px 24px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.2s ease;

  &:hover:not(:disabled) {
    background: #45a049;
  }

  &:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }
`;

export const ReportContainer = styled.div`
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
`;

export const ReportHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24px;
  border-bottom: 1px solid #f0f0f0;
  flex-wrap: wrap;
  gap: 16px;
`;

export const ReportTitle = styled.h2`
  font-size: 1.5rem;
  color: #062a58;
  font-weight: 600;
  margin: 0;
`;

export const ReportPeriod = styled.span`
  color: #666;
  font-size: 0.9rem;
`;

export const ExportActions = styled.div`
  display: flex;
  gap: 12px;
`;

export const ExportButton = styled.button`
  display: flex;
  align-items: center;
  gap: 8px;
  background: #2196F3;
  color: white;
  border: none;
  border-radius: 6px;
  padding: 8px 16px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background 0.2s ease;

  &:hover {
    background: #1976D2;
  }
`;

export const ReportContent = styled.div`
  padding: 24px;
`;

export const SummaryCards = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
`;

export const SummaryCard = styled.div`
  background: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
  text-align: center;
`;

export const CardTitle = styled.h3`
  font-size: 0.9rem;
  color: #666;
  margin: 0 0 12px 0;
  font-weight: 500;
`;

export const CardValue = styled.div<{ positive: boolean }>`
  font-size: 1.5rem;
  font-weight: 700;
  color: ${({ positive }) => positive ? '#4CAF50' : '#FF5722'};
`;

export const ChartContainer = styled.div`
  margin-bottom: 30px;
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
  border: 2px dashed #ddd;
`;

export const CategoryList = styled.div`
  margin-bottom: 30px;
`;

export const CategoryItem = styled.div`
  display: grid;
  grid-template-columns: 1fr 200px 60px;
  align-items: center;
  gap: 16px;
  padding: 16px 0;
  border-bottom: 1px solid #f0f0f0;

  &:last-child {
    border-bottom: none;
  }

  @media (max-width: 768px) {
    grid-template-columns: 1fr;
    gap: 8px;
  }
`;

export const CategoryInfo = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const CategoryName = styled.span`
  font-weight: 500;
  color: #333;
`;

export const CategoryAmount = styled.span`
  font-weight: 600;
  color: #062a58;
`;

export const CategoryBar = styled.div`
  height: 8px;
  background: #f0f0f0;
  border-radius: 4px;
  overflow: hidden;
`;

export const CategoryFill = styled.div<{ percentage: number }>`
  height: 100%;
  width: ${({ percentage }) => percentage}%;
  background: linear-gradient(90deg, #4CAF50, #66BB6A);
  transition: width 0.3s ease;
`;

export const CategoryPercentage = styled.span`
  font-size: 0.9rem;
  color: #666;
  text-align: right;
`;

export const InsightsContainer = styled.div`
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
`;

export const InsightsTitle = styled.h2`
  font-size: 1.3rem;
  color: #062a58;
  margin-bottom: 20px;
  font-weight: 600;
`;

export const InsightsList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

export const InsightItem = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px;
  background: #f8f9fa;
  border-radius: 8px;
  border-left: 4px solid #4CAF50;
`;

export const InsightIcon = styled.div<{ positive: boolean }>`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: ${({ positive }) => positive ? '#E8F5E8' : '#FFF3F3'};
  color: ${({ positive }) => positive ? '#4CAF50' : '#FF5722'};
  flex-shrink: 0;
`;

export const InsightText = styled.p`
  margin: 0;
  color: #333;
  font-size: 0.95rem;
  line-height: 1.4;
`;