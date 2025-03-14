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

public class DebitCardVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private UserMinVO user;
    private String cardBrand;
    private AccountMinVO account;
    private List<DebitTransaction> transactions;

    public DebitCardVO(DebitCard entity) {
        id = entity.getId();
        cardBrand = entity.getCardBrand();
        user = new UserMinVO(entity.getUser());
        account = new AccountMinVO(entity.getAccount());
        transactions = entity.getTransactions();
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

    public AccountMinVO getAccount() {
        return account;
    }

    public void setAccount(AccountMinVO account) {
        this.account = account;
    }

    public List<DebitTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<DebitTransaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DebitCardVO that = (DebitCardVO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
