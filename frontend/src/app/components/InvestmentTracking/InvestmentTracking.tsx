"use client";

import { useState } from "react";
import * as S from "./investmentTrackingStyle";
import { FiPlus, FiTrendingUp, FiTrendingDown, FiDollarSign, FiTarget, FiPieChart } from "react-icons/fi";

interface Investment {
  id: string;
  name: string;
  type: 'STOCKS' | 'BONDS' | 'FUNDS' | 'CRYPTO' | 'REAL_ESTATE';
  quantity: number;
  purchasePrice: number;
  currentPrice: number;
  purchaseDate: string;
}

interface InvestmentGoal {
  id: string;
  name: string;
  targetAmount: number;
  currentAmount: number;
  targetDate: string;
}

export default function InvestmentTracking() {
  const [activeTab, setActiveTab] = useState<'portfolio' | 'goals'>('portfolio');
  const [showInvestmentForm, setShowInvestmentForm] = useState(false);
  const [showGoalForm, setShowGoalForm] = useState(false);
  
  // Estados simulados - em produção viriam de APIs
  const [investments] = useState<Investment[]>([
    {
      id: '1',
      name: 'PETR4',
      type: 'STOCKS',
      quantity: 100,
      purchasePrice: 25.50,
      currentPrice: 28.75,
      purchaseDate: '2024-01-15'
    },
    {
      id: '2',
      name: 'Tesouro Selic 2029',
      type: 'BONDS',
      quantity: 1,
      purchasePrice: 10000,
      currentPrice: 10250,
      purchaseDate: '2024-02-01'
    },
    {
      id: '3',
      name: 'Bitcoin',
      type: 'CRYPTO',
      quantity: 0.1,
      purchasePrice: 200000,
      currentPrice: 220000,
      purchaseDate: '2024-03-10'
    }
  ]);

  const [goals] = useState<InvestmentGoal[]>([
    {
      id: '1',
      name: 'Reserva de Emergência',
      targetAmount: 50000,
      currentAmount: 32000,
      targetDate: '2024-12-31'
    },
    {
      id: '2',
      name: 'Casa Própria',
      targetAmount: 200000,
      currentAmount: 85000,
      targetDate: '2026-06-30'
    }
  ]);

  const getInvestmentTypeLabel = (type: string) => {
    const types = {
      'STOCKS': 'Ações',
      'BONDS': 'Renda Fixa',
      'FUNDS': 'Fundos',
      'CRYPTO': 'Criptomoedas',
      'REAL_ESTATE': 'Imóveis'
    };
    return types[type as keyof typeof types] || type;
  };

  const calculateReturn = (investment: Investment) => {
    const totalPurchase = investment.quantity * investment.purchasePrice;
    const totalCurrent = investment.quantity * investment.currentPrice;
    const absoluteReturn = totalCurrent - totalPurchase;
    const percentageReturn = (absoluteReturn / totalPurchase) * 100;
    
    return { absoluteReturn, percentageReturn };
  };

  const calculatePortfolioSummary = () => {
    let totalInvested = 0;
    let totalCurrent = 0;
    
    investments.forEach(investment => {
      totalInvested += investment.quantity * investment.purchasePrice;
      totalCurrent += investment.quantity * investment.currentPrice;
    });
    
    const totalReturn = totalCurrent - totalInvested;
    const totalReturnPercentage = totalInvested > 0 ? (totalReturn / totalInvested) * 100 : 0;
    
    return { totalInvested, totalCurrent, totalReturn, totalReturnPercentage };
  };

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value);
  };

  const portfolioSummary = calculatePortfolioSummary();

  return (
    <S.Container>
      <S.Header>
        <S.Title>Acompanhamento de Investimentos</S.Title>
        <S.TabContainer>
          <S.Tab 
            $active={activeTab === 'portfolio'} 
            onClick={() => setActiveTab('portfolio')}
          >
            Carteira
          </S.Tab>
          <S.Tab 
            $active={activeTab === 'goals'} 
            onClick={() => setActiveTab('goals')}
          >
            Metas
          </S.Tab>
        </S.TabContainer>
      </S.Header>

      {activeTab === 'portfolio' && (
        <>
          <S.SummaryCards>
            <S.SummaryCard>
              <S.CardHeader>
                <FiDollarSign size={24} color="#2196F3" />
                <span>Total Investido</span>
              </S.CardHeader>
              <S.CardValue positive={true}>
                {formatCurrency(portfolioSummary.totalInvested)}
              </S.CardValue>
            </S.SummaryCard>

            <S.SummaryCard>
              <S.CardHeader>
                <FiTrendingUp size={24} color="#4CAF50" />
                <span>Valor Atual</span>
              </S.CardHeader>
              <S.CardValue positive={true}>
                {formatCurrency(portfolioSummary.totalCurrent)}
              </S.CardValue>
            </S.SummaryCard>

            <S.SummaryCard>
              <S.CardHeader>
                <FiTrendingUp size={24} color={portfolioSummary.totalReturn >= 0 ? "#4CAF50" : "#FF5722"} />
                <span>Retorno Total</span>
              </S.CardHeader>
              <S.CardValue positive={portfolioSummary.totalReturn >= 0}>
                {formatCurrency(portfolioSummary.totalReturn)}
              </S.CardValue>
              <S.ReturnPercentage positive={portfolioSummary.totalReturnPercentage >= 0}>
                {portfolioSummary.totalReturnPercentage >= 0 ? '+' : ''}
                {portfolioSummary.totalReturnPercentage.toFixed(2)}%
              </S.ReturnPercentage>
            </S.SummaryCard>

            <S.SummaryCard>
              <S.CardHeader>
                <FiPieChart size={24} color="#9C27B0" />
                <span>Diversificação</span>
              </S.CardHeader>
              <S.CardValue positive={true}>
                {investments.length} ativos
              </S.CardValue>
            </S.SummaryCard>
          </S.SummaryCards>

          <S.ActionsContainer>
            <S.AddButton onClick={() => setShowInvestmentForm(true)}>
              <FiPlus size={20} />
              Novo Investimento
            </S.AddButton>
          </S.ActionsContainer>

          <S.InvestmentsList>
            <S.SectionTitle>Carteira de Investimentos</S.SectionTitle>
            <S.InvestmentsContainer>
              {investments.map((investment) => {
                const returns = calculateReturn(investment);
                
                return (
                  <S.InvestmentCard key={investment.id}>
                    <S.InvestmentHeader>
                      <S.InvestmentInfo>
                        <S.InvestmentName>{investment.name}</S.InvestmentName>
                        <S.InvestmentType>{getInvestmentTypeLabel(investment.type)}</S.InvestmentType>
                      </S.InvestmentInfo>
                      <S.InvestmentReturn positive={returns.absoluteReturn >= 0}>
                        {returns.absoluteReturn >= 0 ? <FiTrendingUp /> : <FiTrendingDown />}
                      </S.InvestmentReturn>
                    </S.InvestmentHeader>
                    
                    <S.InvestmentDetails>
                      <S.DetailRow>
                        <span>Quantidade:</span>
                        <span>{investment.quantity}</span>
                      </S.DetailRow>
                      <S.DetailRow>
                        <span>Preço de Compra:</span>
                        <span>{formatCurrency(investment.purchasePrice)}</span>
                      </S.DetailRow>
                      <S.DetailRow>
                        <span>Preço Atual:</span>
                        <span>{formatCurrency(investment.currentPrice)}</span>
                      </S.DetailRow>
                      <S.DetailRow>
                        <span>Total Investido:</span>
                        <span>{formatCurrency(investment.quantity * investment.purchasePrice)}</span>
                      </S.DetailRow>
                      <S.DetailRow>
                        <span>Valor Atual:</span>
                        <span>{formatCurrency(investment.quantity * investment.currentPrice)}</span>
                      </S.DetailRow>
                    </S.InvestmentDetails>
                    
                    <S.ReturnInfo>
                      <S.ReturnValue positive={returns.absoluteReturn >= 0}>
                        {returns.absoluteReturn >= 0 ? '+' : ''}
                        {formatCurrency(returns.absoluteReturn)}
                      </S.ReturnValue>
                      <S.ReturnPercentage positive={returns.percentageReturn >= 0}>
                        ({returns.percentageReturn >= 0 ? '+' : ''}
                        {returns.percentageReturn.toFixed(2)}%)
                      </S.ReturnPercentage>
                    </S.ReturnInfo>
                  </S.InvestmentCard>
                );
              })}
            </S.InvestmentsContainer>
          </S.InvestmentsList>
        </>
      )}

      {activeTab === 'goals' && (
        <>
          <S.ActionsContainer>
            <S.AddButton onClick={() => setShowGoalForm(true)}>
              <FiPlus size={20} />
              Nova Meta
            </S.AddButton>
          </S.ActionsContainer>

          <S.GoalsList>
            <S.SectionTitle>Metas de Investimento</S.SectionTitle>
            <S.GoalsContainer>
              {goals.map((goal) => {
                const progress = (goal.currentAmount / goal.targetAmount) * 100;
                const remaining = goal.targetAmount - goal.currentAmount;
                const daysRemaining = Math.ceil((new Date(goal.targetDate).getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
                
                return (
                  <S.GoalCard key={goal.id}>
                    <S.GoalHeader>
                      <S.GoalInfo>
                        <S.GoalName>{goal.name}</S.GoalName>
                        <S.GoalTarget>Meta: {formatCurrency(goal.targetAmount)}</S.GoalTarget>
                      </S.GoalInfo>
                      <S.GoalIcon>
                        <FiTarget size={24} />
                      </S.GoalIcon>
                    </S.GoalHeader>
                    
                    <S.GoalProgress>
                      <S.ProgressBar>
                        <S.ProgressFill percentage={Math.min(progress, 100)} />
                      </S.ProgressBar>
                      <S.ProgressText>
                        {progress.toFixed(1)}% concluído
                      </S.ProgressText>
                    </S.GoalProgress>
                    
                    <S.GoalDetails>
                      <S.DetailRow>
                        <span>Valor Atual:</span>
                        <span>{formatCurrency(goal.currentAmount)}</span>
                      </S.DetailRow>
                      <S.DetailRow>
                        <span>Faltam:</span>
                        <span>{formatCurrency(remaining)}</span>
                      </S.DetailRow>
                      <S.DetailRow>
                        <span>Prazo:</span>
                        <span>{new Date(goal.targetDate).toLocaleDateString('pt-BR')}</span>
                      </S.DetailRow>
                      <S.DetailRow>
                        <span>Dias restantes:</span>
                        <span>{daysRemaining > 0 ? `${daysRemaining} dias` : 'Prazo vencido'}</span>
                      </S.DetailRow>
                    </S.GoalDetails>
                  </S.GoalCard>
                );
              })}
            </S.GoalsContainer>
          </S.GoalsList>
        </>
      )}

      {(investments.length === 0 && activeTab === 'portfolio') && (
        <S.EmptyState>
          <FiTrendingUp size={48} color="#ccc" />
          <p>Nenhum investimento cadastrado</p>
          <span>Clique em "Novo Investimento" para começar</span>
        </S.EmptyState>
      )}

      {(goals.length === 0 && activeTab === 'goals') && (
        <S.EmptyState>
          <FiTarget size={48} color="#ccc" />
          <p>Nenhuma meta cadastrada</p>
          <span>Clique em "Nova Meta" para começar</span>
        </S.EmptyState>
      )}
    </S.Container>
  );
}