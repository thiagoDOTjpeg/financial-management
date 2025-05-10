package br.com.gritti.app.application.util;

import br.com.gritti.app.application.dto.user.UserBankAccountsResponseDTO;
import br.com.gritti.app.application.dto.user.UserResponseDTO;
import br.com.gritti.app.domain.model.User;

public class AdminUtil {
  public static <T> void applyAdminProperties(User user, T dto) {

    if(dto instanceof UserBankAccountsResponseDTO userBankAccountsResponseDTO) {

      userBankAccountsResponseDTO.setRoles(user.getPermissions());
      userBankAccountsResponseDTO.setCreatedAt(user.getCreatedAt());
      userBankAccountsResponseDTO.setUpdatedAt(user.getUpdatedAt());
      userBankAccountsResponseDTO.setCreatedBy(user.getCreatedBy());
      userBankAccountsResponseDTO.setUpdatedBy(user.getUpdatedBy());

    } else if(dto instanceof UserResponseDTO userResponseDTO) {

      userResponseDTO.setRoles(user.getPermissions());
      userResponseDTO.setCreatedAt(user.getCreatedAt());
      userResponseDTO.setCreatedBy(user.getCreatedBy());
      userResponseDTO.setUpdatedAt(user.getUpdatedAt());
      userResponseDTO.setUpdatedBy(user.getUpdatedBy());
      userResponseDTO.setRoles(user.getPermissions());

    }
  }
}
