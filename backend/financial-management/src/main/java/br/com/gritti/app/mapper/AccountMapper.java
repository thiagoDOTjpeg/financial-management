package br.com.gritti.app.mapper;

import br.com.gritti.app.data.dto.account.AccountRequestDTO;
import br.com.gritti.app.data.dto.account.AccountResponseDTO;
import br.com.gritti.app.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account requestToAccount(AccountRequestDTO accountRequestDTO);
    AccountResponseDTO accountToResponseDTO(Account account);

}
