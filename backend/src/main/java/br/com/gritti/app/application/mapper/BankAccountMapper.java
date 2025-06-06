package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.bankaccount.*;
import br.com.gritti.app.domain.model.BankAccount;
import br.com.gritti.app.domain.valueobject.Email;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BankAccountMapper extends DefaultMapper {

  BankAccountResponseDTO accountToAccountResponseDTO(BankAccount account);

  BankAccount accountCreateDTOtoAccount(BankAccountCreateDTO accountCreateDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateBankAccount(BankAccount updatedAccount, @MappingTarget BankAccount account);

  BankAccountDTO accountToAccountDTO(BankAccount account);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  BankAccount accountUpdateDTOtoAccount(BankAccountUpdateDTO accountUpdateDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  BankAccountCardsResponseDTO bankAccountToBankAccountCardsResponseDTO(BankAccount bankAccount);
}
