package br.com.gritti.app.data.vo.credit_card;

import br.com.gritti.app.data.vo.account.AccountMinVO;
import br.com.gritti.app.data.vo.user.UserMinVO;
import br.com.gritti.app.model.Account;
import br.com.gritti.app.model.CreditCard;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class CreditCardMinVO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private UUID id;
  private String cardBrand;
  private AccountMinVO account;

  public CreditCardMinVO(CreditCard entity) {
    id = entity.getId();
    cardBrand = entity.getCardBrand();
    account = new AccountMinVO(entity.getAccount());
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getCardBrand() {
    return cardBrand;
  }

  public void setCardBrand(String cardBrand) {
    this.cardBrand = cardBrand;
  }

  public AccountMinVO getAccount() {
    return account;
  }

  public void setAccount(AccountMinVO account) {
    this.account = account;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    CreditCardMinVO that = (CreditCardMinVO) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
