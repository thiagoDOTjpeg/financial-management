package br.com.gritti.app.mapper;

import br.com.gritti.app.data.dto.account.AccountRequestDTO;
import br.com.gritti.app.data.dto.account.AccountResponseDTO;
import br.com.gritti.app.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
  AccountResponseDTO accountToResponseDTO(Account account);
  Account requestToAccount(AccountRequestDTO dto);
}
