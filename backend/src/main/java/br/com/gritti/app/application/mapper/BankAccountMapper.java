package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.bankaccount.BankAccountCreateDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountResponseDTO;
import br.com.gritti.app.domain.model.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

  BankAccountResponseDTO accountToAccountResponseDTO(BankAccount account);

  BankAccount accountCreateDTOtoAccount(BankAccountCreateDTO accountCreateDTO);
}
