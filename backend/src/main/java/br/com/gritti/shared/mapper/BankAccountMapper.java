package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.BankAccount;
import br.com.gritti.shared.dto.response.BankAccountResponse;

public class BankAccountMapper {
  public static BankAccountResponse toResponse(BankAccount bankAccount) {
    BankAccountResponse response = new BankAccountResponse();
    response.setAccountNumber(bankAccount.getAccountNumber());
    response.setBankName(bankAccount.getBankName());
    response.setActive(bankAccount.getActive());
    response.setAccountType(bankAccount.getAccountType());
    response.setAgency(bankAccount.getAgency());
    response.setUser(UserMapper.toResponse(bankAccount.getUser()));
    return response;
  }
}
