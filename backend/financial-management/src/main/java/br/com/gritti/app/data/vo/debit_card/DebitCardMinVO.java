package br.com.gritti.app.data.vo.debit_card;

import br.com.gritti.app.data.vo.account.AccountMinVO;
import br.com.gritti.app.data.vo.user.UserMinVO;
import br.com.gritti.app.model.Account;
import br.com.gritti.app.model.DebitCard;
import br.com.gritti.app.model.DebitTransaction;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DebitCardMinVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String cardBrand;
    private AccountMinVO account;

    public DebitCardMinVO(DebitCard entity) {
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
        DebitCardMinVO that = (DebitCardMinVO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
