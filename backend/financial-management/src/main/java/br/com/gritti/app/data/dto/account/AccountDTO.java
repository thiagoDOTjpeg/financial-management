package br.com.gritti.app.data.dto.account;

import br.com.gritti.app.data.vo.credit_card.CreditCardMinVO;
import br.com.gritti.app.data.vo.debit_card.DebitCardVO;
import br.com.gritti.app.data.vo.user.UserMinVO;
import br.com.gritti.app.model.Account;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountDTO {
    private UUID id;
    private UserMinVO user;
    private String bankName;
    private Double balance;
    private List<CreditCardMinVO> creditCards;
    private List<DebitCardVO> debitCards;

    public AccountDTO() {}

    public AccountDTO(Account entity) {
        id = entity.getId();
        bankName = entity.getBankName();
        balance = entity.getBalance();
        creditCards = entity.getCreditCards().stream().map(CreditCardMinVO::new).collect(Collectors.toList());
    }
}
