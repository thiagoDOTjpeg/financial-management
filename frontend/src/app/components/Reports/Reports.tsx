"use client";

import { useState } from "react";
import * as S from "./reportsStyle";
import { FiDownload, FiCalendar, FiTrendingUp, FiPieChart, FiBarChart3 } from "react-icons/fi";

interface ReportFilter {
  startDate: string;
  endDate: string;
  reportType: 'CASH_FLOW' | 'EXPENSES' | 'INCOME' | 'BALANCE_SHEET' | 'INVESTMENT';
  category?: string;
  account?: string;
}

export default function Reports() {
  const [filters, setFilters] = useState<ReportFilter>({
    startDate: new Date(new Date().getFullYear(), new Date().getMonth(), 1).toISOString().split('T')[0],
    endDate: new Date().toISOString().split('T')[0],
    reportType: 'CASH_FLOW'
  });

  const [isGenerating, setIsGenerating] = useState(false);

  const reportTypes = [
    { value: 'CASH_FLOW', label: 'Fluxo de Caixa', icon: FiTrendingUp },
    { value: 'EXPENSES', label: 'Análise de Despesas', icon: FiPieChart },
    { value: 'INCOME', label: 'Análise de Receitas', icon: FiBarChart3 },
    { value: 'BALANCE_SHEET', label: 'Balanço Patrimonial', icon: FiCalendar },
    { value: 'INVESTMENT', label: 'Performance de Investimentos', icon: FiTrendingUp }
  ];

  // Dados simulados para demonstração
  const mockData = {
    CASH_FLOW: {
      totalIncome: 8500,
      totalExpenses: 6200,
      netFlow: 2300,
      monthlyData: [
        { month: 'Jan', income: 8000, expenses: 6000 },
        { month: 'Fev', income: 8200, expenses: 6100 },
        { month: 'Mar', income: 8500, expenses: 6200 }
      ]
    },
    EXPENSES: {
      totalExpenses: 6200,
      categories: [
        { name: 'Alimentação', amount: 1200, percentage: 19.4 },
        { name: 'Moradia', amount: 2000, percentage: 32.3 },
        { name: 'Transporte', amount: 800, percentage: 12.9 },
        { name: 'Lazer', amount: 600, percentage: 9.7 },
        { name: 'Outros', amount: 1600, percentage: 25.8 }
      ]
    },
    INCOME: {
      totalIncome: 8500,
      sources: [
        { name: 'Salário', amount: 7000, percentage: 82.4 },
        { name: 'Freelance', amount: 1000, percentage: 11.8 },
        { name: 'Investimentos', amount: 500, percentage: 5.9 }
      ]
    }
  };

  const handleGenerateReport = async () => {
    setIsGenerating(true);
    
    // Simular geração de relatório
    await new Promise(resolve => setTimeout(resolve, 2000));
    
    setIsGenerating(false);
  };

  const handleExportReport = (format: 'PDF' | 'EXCEL') => {
    // Simular exportação
    console.log(`Exportando relatório em ${format}`);
  };

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value);
  };

  const renderReportContent = () => {
    const data = mockData[filters.reportType as keyof typeof mockData];
    
    switch (filters.reportType) {
      case 'CASH_FLOW':
        const cashFlowData = data as typeof mockData.CASH_FLOW;
        return (
          <S.ReportContent>
            <S.SummaryCards>
              <S.SummaryCard>
                <S.CardTitle>Receitas Totais</S.CardTitle>
                <S.CardValue positive={true}>
                  {formatCurrency(cashFlowData.totalIncome)}
                </S.CardValue>
              </S.SummaryCard>
              
              <S.SummaryCard>
                <S.CardTitle>Despesas Totais</S.CardTitle>
                <S.CardValue positive={false}>
                  {formatCurrency(cashFlowData.totalExpenses)}
                </S.CardValue>
              </S.SummaryCard>
              
              <S.SummaryCard>
                <S.CardTitle>Fluxo Líquido</S.CardTitle>
                <S.CardValue positive={cashFlowData.netFlow >= 0}>
                  {formatCurrency(cashFlowData.netFlow)}
                </S.CardValue>
              </S.SummaryCard>
            </S.SummaryCards>
            
            <S.ChartContainer>
              <S.ChartTitle>Evolução Mensal</S.ChartTitle>
              <S.ChartPlaceholder>
                Gráfico de fluxo de caixa mensal será implementado aqui
              </S.ChartPlaceholder>
            </S.ChartContainer>
          </S.ReportContent>
        );
        
      case 'EXPENSES':
        const expensesData = data as typeof mockData.EXPENSES;
        return (
          <S.ReportContent>
            <S.SummaryCards>
              <S.SummaryCard>
                <S.CardTitle>Total de Despesas</S.CardTitle>
                <S.CardValue positive={false}>
                  {formatCurrency(expensesData.totalExpenses)}
                </S.CardValue>
              </S.SummaryCard>
            </S.SummaryCards>
            
            <S.CategoryList>
              <S.ChartTitle>Despesas por Categoria</S.ChartTitle>
              {expensesData.categories.map((category, index) => (
                <S.CategoryItem key={index}>
                  <S.CategoryInfo>
                    <S.CategoryName>{category.name}</S.CategoryName>
                    <S.CategoryAmount>{formatCurrency(category.amount)}</S.CategoryAmount>
                  </S.CategoryInfo>
                  <S.CategoryBar>
                    <S.CategoryFill percentage={category.percentage} />
                  </S.CategoryBar>
                  <S.CategoryPercentage>{category.percentage}%</S.CategoryPercentage>
                </S.CategoryItem>
              ))}
            </S.CategoryList>
          </S.ReportContent>
        );
        
      case 'INCOME':
        const incomeData = data as typeof mockData.INCOME;
        return (
          <S.ReportContent>
            <S.SummaryCards>
              <S.SummaryCard>
                <S.CardTitle>Total de Receitas</S.CardTitle>
                <S.CardValue positive={true}>
                  {formatCurrency(incomeData.totalIncome)}
                </S.CardValue>
              </S.SummaryCard>
            </S.SummaryCards>
            
            <S.CategoryList>
              <S.ChartTitle>Receitas por Fonte</S.ChartTitle>
              {incomeData.sources.map((source, index) => (
                <S.CategoryItem key={index}>
                  <S.CategoryInfo>
                    <S.CategoryName>{source.name}</S.CategoryName>
                    <S.CategoryAmount>{formatCurrency(source.amount)}</S.CategoryAmount>
                  </S.CategoryInfo>
                  <S.CategoryBar>
                    <S.CategoryFill percentage={source.percentage} />
                  </S.CategoryBar>
                  <S.CategoryPercentage>{source.percentage}%</S.CategoryPercentage>
                </S.CategoryItem>
              ))}
            </S.CategoryList>
          </S.ReportContent>
        );
        
      default:
        return (
          <S.ReportContent>
            <S.ChartPlaceholder>
              Relatório de {reportTypes.find(t => t.value === filters.reportType)?.label} será implementado aqui
            </S.ChartPlaceholder>
          </S.ReportContent>
        );
    }
  };

  return (
    <S.Container>
      <S.Header>
        <S.Title>Relatórios e Análises</S.Title>
      </S.Header>

      <S.FiltersContainer>
        <S.FilterGroup>
          <S.Label>Tipo de Relatório</S.Label>
          <S.Select
            value={filters.reportType}
            onChange={(e) => setFilters({ ...filters, reportType: e.target.value as any })}
          >
            {reportTypes.map((type) => (
              <option key={type.value} value={type.value}>
                {type.label}
              </option>
            ))}
          </S.Select>
        </S.FilterGroup>

        <S.FilterGroup>
          <S.Label>Data Inicial</S.Label>
          <S.Input
            type="date"
            value={filters.startDate}
            onChange={(e) => setFilters({ ...filters, startDate: e.target.value })}
          />
        </S.FilterGroup>

        <S.FilterGroup>
          <S.Label>Data Final</S.Label>
          <S.Input
            type="date"
            value={filters.endDate}
            onChange={(e) => setFilters({ ...filters, endDate: e.target.value })}
          />
        </S.FilterGroup>

        <S.FilterActions>
          <S.GenerateButton onClick={handleGenerateReport} disabled={isGenerating}>
            {isGenerating ? 'Gerando...' : 'Gerar Relatório'}
          </S.GenerateButton>
        </S.FilterActions>
      </S.FiltersContainer>

      <S.ReportContainer>
        <S.ReportHeader>
          <S.ReportTitle>
            {reportTypes.find(t => t.value === filters.reportType)?.label}
          </S.ReportTitle>
          <S.ReportPeriod>
            {new Date(filters.startDate).toLocaleDateString('pt-BR')} - {new Date(filters.endDate).toLocaleDateString('pt-BR')}
          </S.ReportPeriod>
          
          <S.ExportActions>
            <S.ExportButton onClick={() => handleExportReport('PDF')}>
              <FiDownload size={16} />
              PDF
            </S.ExportButton>
            <S.ExportButton onClick={() => handleExportReport('EXCEL')}>
              <FiDownload size={16} />
              Excel
            </S.ExportButton>
          </S.ExportActions>
        </S.ReportHeader>

        {renderReportContent()}
      </S.ReportContainer>

      <S.InsightsContainer>
        <S.InsightsTitle>Insights Automáticos</S.InsightsTitle>
        <S.InsightsList>
          <S.InsightItem>
            <S.InsightIcon positive={true}>
              <FiTrendingUp />
            </S.InsightIcon>
            <S.InsightText>
              Suas receitas aumentaram 5% em relação ao mês anterior
            </S.InsightText>
          </S.InsightItem>
          
          <S.InsightItem>
            <S.InsightIcon positive={false}>
              <FiTrendingUp />
            </S.InsightIcon>
            <S.InsightText>
              Gastos com lazer excederam o orçamento em 30%
            </S.InsightText>
          </S.InsightItem>
          
          <S.InsightItem>
            <S.InsightIcon positive={true}>
              <FiPieChart />
            </S.InsightIcon>
            <S.InsightText>
              Você conseguiu poupar 27% da sua renda este mês
            </S.InsightText>
          </S.InsightItem>
        </S.InsightsList>
      </S.InsightsContainer>
    </S.Container>
  );
}