package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.installment.InstallmentCreateDTO;
import br.com.gritti.app.domain.model.Installment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface InstallmentMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Installment installmentCreateDTOtoInstallment(InstallmentCreateDTO installmentCreateDTO);
}
