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
`;

export const Title = styled.h1`
  font-size: 2rem;
  color: #062a58;
  font-weight: 600;
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

export const LoadingContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 400px;
  font-size: 1.2rem;
  color: #666;
`;

export const FiltersContainer = styled.div`
  display: flex;
  gap: 20px;
  margin-bottom: 30px;
  flex-wrap: wrap;
`;

export const SearchContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 12px 16px;
  flex: 1;
  min-width: 250px;

  svg {
    color: #666;
  }
`;

export const SearchInput = styled.input`
  border: none;
  outline: none;
  flex: 1;
  font-size: 1rem;
`;

export const FilterContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 12px 16px;

  svg {
    color: #666;
  }
`;

export const FilterSelect = styled.select`
  border: none;
  outline: none;
  background: transparent;
  font-size: 1rem;
  cursor: pointer;
`;

export const FormOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
`;

export const FormContainer = styled.div`
  background: white;
  border-radius: 12px;
  width: 90%;
  max-width: 600px;
  max-height: 90vh;
  overflow-y: auto;
`;

export const FormHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  border-bottom: 1px solid #eee;

  h3 {
    margin: 0;
    color: #062a58;
    font-size: 1.3rem;
  }
`;

export const CloseButton = styled.button`
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #666;
  padding: 0;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;

  &:hover {
    color: #333;
  }
`;

export const Form = styled.form`
  padding: 24px;
`;

export const FormRow = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  margin-bottom: 20px;

  @media (max-width: 600px) {
    grid-template-columns: 1fr;
  }
`;

export const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Label = styled.label`
  margin-bottom: 8px;
  font-weight: 500;
  color: #333;
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

export const FormActions = styled.div`
  display: flex;
  gap: 12px;
  justify-content: flex-end;
  margin-top: 30px;
`;

export const CancelButton = styled.button`
  padding: 12px 24px;
  border: 1px solid #ddd;
  background: white;
  color: #666;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s ease;

  &:hover {
    background: #f5f5f5;
  }
`;

export const SubmitButton = styled.button`
  padding: 12px 24px;
  background: #4CAF50;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
  transition: background 0.2s ease;

  &:hover:not(:disabled) {
    background: #45a049;
  }

  &:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }
`;

export const TransactionsContainer = styled.div`
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
`;

export const TransactionsList = styled.div`
  padding: 20px;
`;

export const TransactionItem = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 0;
  border-bottom: 1px solid #f0f0f0;

  &:last-child {
    border-bottom: none;
  }

  &:hover {
    background: #f9f9f9;
    margin: 0 -20px;
    padding: 16px 20px;
    border-radius: 8px;
  }
`;

export const TransactionIcon = styled.div<{ type: string }>`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: ${({ type }) => type === 'INCOME' ? '#E8F5E8' : '#FFF3F3'};
  color: ${({ type }) => type === 'INCOME' ? '#4CAF50' : '#FF5722'};
`;

export const TransactionInfo = styled.div`
  flex: 1;
`;

export const TransactionDescription = styled.div`
  font-weight: 500;
  color: #333;
  margin-bottom: 4px;
`;

export const TransactionDetails = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 0.85rem;
  color: #666;
`;

export const TransactionAmount = styled.div<{ type: string }>`
  font-weight: 600;
  font-size: 1.1rem;
  color: ${({ type }) => type === 'INCOME' ? '#4CAF50' : '#FF5722'};
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