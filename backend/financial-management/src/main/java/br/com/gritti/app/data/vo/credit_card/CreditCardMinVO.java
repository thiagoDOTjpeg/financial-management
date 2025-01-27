package br.com.gritti.app.data.vo.credit_card;

import br.com.gritti.app.data.vo.user.UserMinVO;
import br.com.gritti.app.model.Account;
import br.com.gritti.app.model.CreditCard;

import java.util.Objects;
import java.util.UUID;

public class CreditCardMinVO {

  private UUID id;
  private UserMinVO user;
  private String cardBrand;
  private Account account;

  public CreditCardMinVO(CreditCard entity) {
    id = entity.getId();
    user = new UserMinVO(entity.getUser());
    cardBrand = entity.getCardBrand();
    account = entity.getAccount();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UserMinVO getUser() {
    return user;
  }

  public void setUser(UserMinVO user) {
    this.user = user;
  }

  public String getCardBrand() {
    return cardBrand;
  }

  public void setCardBrand(String cardBrand) {
    this.cardBrand = cardBrand;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
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
