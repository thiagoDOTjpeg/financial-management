package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.bankaccount.BankAccountCreateDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountResponseDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountUpdateDTO;
import br.com.gritti.app.domain.model.BankAccount;
import br.com.gritti.app.domain.valueobject.Email;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

  BankAccountResponseDTO accountToAccountResponseDTO(BankAccount account);

  BankAccount accountCreateDTOtoAccount(BankAccountCreateDTO accountCreateDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateBankAccount(BankAccount updatedAccount, @MappingTarget BankAccount account);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  BankAccount accountUpdateDTOtoAccount(BankAccountUpdateDTO accountUpdateDTO);

  default Email stringToEmail(String email) {
    return email != null ? new Email(email) : null;
  }

  default String emailToString(Email email) {
    return email != null ? email.getValue() : null;
  }
}
