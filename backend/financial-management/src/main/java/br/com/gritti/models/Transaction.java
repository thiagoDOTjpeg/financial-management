package br.com.gritti.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "transaction")
public class Transaction implements Serializable  {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id_transacoes;

  @ManyToOne
  @JoinColumn(name = "id_fatura")
  private long id_fatura;

  @ManyToOne
  @JoinColumn(name = "id_fatura")
  private long id_cartao;

  @ManyToOne
  @JoinColumn(name = "id_fatura")
  private long id_conta;

  @Column(name = "nome")
  private String nome;

  @Column(name = "valor")
  private Double valor;

  @Column(name = "data")
  private Date data;

  public long getId_transacoes() {
    return id_transacoes;
  }

  public void setId_transacoes(long id_transacoes) {
    this.id_transacoes = id_transacoes;
  }

  public long getId_fatura() {
    return id_fatura;
  }

  public void setId_fatura(long id_fatura) {
    this.id_fatura = id_fatura;
  }

  public long getId_cartao() {
    return id_cartao;
  }

  public void setId_cartao(long id_cartao) {
    this.id_cartao = id_cartao;
  }

  public long getId_conta() {
    return id_conta;
  }

  public void setId_conta(long id_conta) {
    this.id_conta = id_conta;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return id_transacoes == that.id_transacoes && id_fatura == that.id_fatura && id_cartao == that.id_cartao && id_conta == that.id_conta && Objects.equals(nome, that.nome) && Objects.equals(valor, that.valor) && Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id_transacoes, id_fatura, id_cartao, id_conta, nome, valor, data);
  }
}