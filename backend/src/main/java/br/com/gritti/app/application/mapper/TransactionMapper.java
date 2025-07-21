package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.transaction.TransactionCreateDTO;
import br.com.gritti.app.application.dto.transaction.TransactionResponseDTO;
import br.com.gritti.app.domain.model.Transaction;
import br.com.gritti.app.domain.valueobject.TransactionProcessingData;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {

  TransactionResponseDTO transactionToTransactionResponseDTO(Transaction transaction);

  Transaction transactionCreateDTOtoTransaction(TransactionCreateDTO transactionCreateDTO);

  Transaction transactionProcessingDataToTransaction(TransactionProcessingData transactionProcessingData);

  TransactionProcessingData transactionCreateDtoToTransactionProcessingData(TransactionCreateDTO transactionCreateDTO);
}
