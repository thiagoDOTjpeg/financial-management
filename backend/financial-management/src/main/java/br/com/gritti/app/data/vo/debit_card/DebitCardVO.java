package br.com.gritti.app.data.vo.debit_card;

import br.com.gritti.app.data.vo.account.AccountMinVO;
import br.com.gritti.app.model.Account;
import br.com.gritti.app.model.DebitTransaction;

import java.util.List;
import java.util.UUID;

public class DebitCardVO {
    private UUID id;
    private AccountMinVO account;
    private List<DebitTransaction> transactions;

}
